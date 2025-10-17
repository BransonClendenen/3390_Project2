package controllers;

import models.AppDataManager;
import models.Event;
import models.Garment;
import views.MainWindow;
import views.PlannerView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PlannerController {

    private final PlannerView view;
    private final AppDataManager dataManager;
    private final MainWindow mainWindow;

    private final DefaultListModel<String> wardrobeListModel = new DefaultListModel<>();


    public PlannerController(PlannerView view, AppDataManager dataManager, MainWindow mainWindow) {
        this.view = view;
        this.dataManager = dataManager;
        this.mainWindow = mainWindow;

        setupUI();
        setupListeners();

        //show Shirts by default
        loadCategoryItems("Shirt");
        refreshTable();
    }

    //UI setup
    private void setupUI() {
        // table
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Item", "Date"}, 0);
        JTable table = view.getPlannerTable();
        table.setModel(model);

        // nice table styling (same as Outfit Generator)
        ((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // right-side wardrobe list
        view.getWardrobeList().setModel(wardrobeListModel);
        view.getWardrobeList().setFont(new Font("SansSerif", Font.PLAIN, 14));
        view.getWardrobeList().setFixedCellHeight(24);
    }

    //listeners
    private void setupListeners() {
        view.getAddButton().addActionListener(e -> addEvent());
        view.getDeleteButton().addActionListener(e -> deleteEvent());
        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));

        //category filter buttons
        view.getShirtsButton().addActionListener(e -> loadCategoryItems("Shirt"));
        view.getPantsButton().addActionListener(e -> loadCategoryItems("Pant"));
        view.getShoesButton().addActionListener(e -> loadCategoryItems("Shoe"));
    }

    //Load wardrobe items
    private void loadCategoryItems(String category) {
        wardrobeListModel.clear();

        //clean garments
        for (Garment g : dataManager.getGarmentData().getGarments()) {
            if (g != null && g.getType().equalsIgnoreCase(category)) {
                wardrobeListModel.addElement(g.getName());
            }
        }

        //also show laundry items
        for (Garment g : dataManager.getLaundryData().getLaundryItems()) {
            if (g != null && g.getType().equalsIgnoreCase(category)) {
                wardrobeListModel.addElement(g.getName() + " (in laundry)");
            }
        }
    }

    private void saveEvents() {
        try {
            dataManager.getEventData().saveData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //add/delete /refresh events
    private void addEvent() {
        String item = view.getWardrobeList().getSelectedValue();
        String date = view.getSelectedDate();

        if (item == null || item.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an item.");
            return;
        }
        if (date == null || date.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select a date.");
            return;
        }

        //prevent duplicates
        for (Event e : dataManager.getEventData().getEvents()) {
            if (e != null &&
                    e.getName().equals(item) &&
                    e.getDate().equals(date)) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "This item is already scheduled for that date.");
                return;
            }
        }

        Random rand = new Random();
        int id = rand.nextInt(10000);
        Event newEvent = new Event(item, date, id, "Shirt", "Pant", "Shoe");
        dataManager.getEventData().addEvent(newEvent);
        saveEvents();
        refreshTable();

        mainWindow.getMainMenuController().updateLaundryWarnings();
    }
    private void deleteEvent() {
        int selectedRow = view.getPlannerTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "Please select an event to delete.");
            return;
        }

        String item = view.getPlannerTable().getValueAt(selectedRow, 0).toString();
        String date = view.getPlannerTable().getValueAt(selectedRow, 1).toString();

        dataManager.getEventData().getEvents().removeIf(e -> e != null &&
                e.getName().equals(item) && e.getDate().equals(date));
        saveEvents();
        refreshTable();
        mainWindow.getMainMenuController().updateLaundryWarnings();

    }


    //remove past events
    private void removePastEvents() {
        List<Event> events = dataManager.getEventData().getEvents();
        if (events == null || events.isEmpty()) return;

        LocalDate today = LocalDate.now();
        DateTimeFormatter cleanFmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter legacyFmt = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);

        events.removeIf(e -> {
            if (e == null) return false;
            LocalDate eventDate = parseDateSafely(e.getDate(), cleanFmt, legacyFmt);
            return eventDate != null && eventDate.isBefore(today);
        });

        try {
            dataManager.getEventData().saveData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public void reloadPlanner() {
        // default to shirts, or remember the last selected category if you want
        loadCategoryItems("Shirt");
        refreshTable();
    }


    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) view.getPlannerTable().getModel();
        model.setRowCount(0);

        // --- Use the same date format you save in your app ---
        DateTimeFormatter cleanFmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter legacyFmt =
                DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);

        // --- Collect all planner events (skip Generated Outfit [StatsOnly]) ---
        List<Event> sortedEvents = dataManager.getEventData().getEvents().stream()
                .filter(e -> e != null)
                .filter(e -> !e.getName().toLowerCase().startsWith("generated outfit")) // ignore stats events
                .sorted((e1, e2) -> {
                    LocalDate d1 = parseDateSafely(e1.getDate(), cleanFmt, legacyFmt);
                    LocalDate d2 = parseDateSafely(e2.getDate(), cleanFmt, legacyFmt);

                    // Handle nulls safely
                    if (d1 == null && d2 == null) return 0;
                    if (d1 == null) return 1;   // nulls go to bottom
                    if (d2 == null) return -1;
                    return d1.compareTo(d2);    //earliest first
                })
                .toList();

        // --- Populate the table ---
        for (Event e : sortedEvents) {
            model.addRow(new Object[]{e.getName(), e.getDate()});
        }
    }


    //parse date safely regardless of format
    private LocalDate parseDateSafely(String s,
                                      DateTimeFormatter clean,
                                      DateTimeFormatter legacy) {
        if (s == null || s.isBlank()) return null;
        try {
            return LocalDate.parse(s, clean);
        } catch (Exception ex1) {
            try {
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);
                java.util.Date d = sdf.parse(s);
                return d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            } catch (Exception ex2) {
                return null;
            }
        }
    }


    //show all dates as MM-dd-yyyy even if legacy
    private String formatDateDisplay(String dateStr) {
        try {
            LocalDate d = parseDateSafely(dateStr,
                    DateTimeFormatter.ofPattern("MM-dd-yyyy"),
                    DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH));
            return (d != null) ? d.format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) : dateStr;
        } catch (Exception e) {
            return dateStr;
        }
    }
}
