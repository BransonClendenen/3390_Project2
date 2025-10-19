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



    private void stylePlannerButton(JButton button, Color baseColor) {
        button.setFont(new Font("MV Boli", Font.BOLD, 20));
        button.setBackground(baseColor);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
    }


    //UI setup
    private void setupUI() {
        // table
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Item", "Date"}, 0);
        JTable table = view.getPlannerTable();
        table.setModel(model);

        //nice table styling (same as Outfit Generator)
        ((JLabel) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));


        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

            // right-side wardrobe list
        view.getWardrobeList().setModel(wardrobeListModel);

        stylePlannerButton(view.getAddButton(), new Color(182, 230, 194));     // mint green
        stylePlannerButton(view.getDeleteButton(), new Color(255, 204, 188));  // soft coral
        stylePlannerButton(view.getShirtsButton(), new Color(200, 250, 200));  // light blue
        stylePlannerButton(view.getPantsButton(), new Color(255, 250, 200));   // pale yellow
        stylePlannerButton(view.getShoesButton(), new Color(204, 153, 255));   // lilac
        stylePlannerButton(view.getBackButton(), new Color(253, 173, 0));

       stylePlannerButton(view.getBackButton(), new Color(253,173,0));

        view.getBackButton().setPreferredSize(new Dimension(80, 5));
            //stop table edit
        table.setDefaultEditor(Object.class, null);

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

                //no past dates acceptable
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate selectedDate;
        try {
            selectedDate = LocalDate.parse(date, fmt);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Invalid date format.");
            return;
        }

        LocalDate today = LocalDate.now();
        if (selectedDate.isBefore(today)) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "You cannot plan for a past date!");
            return;
        }

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
        removePastEvents();   // clean up outdated plans first

        //default to shirts, or remember the last selected category if you want
        loadCategoryItems("Shirt");
        refreshTable();
    }


    public void refreshTable() {
        removePastEvents();   // clean up outdated plans first

        DefaultTableModel model = (DefaultTableModel) view.getPlannerTable().getModel();
        model.setRowCount(0);

        //use the same date format you save in your app
        DateTimeFormatter cleanFmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter legacyFmt =
                DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);


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
