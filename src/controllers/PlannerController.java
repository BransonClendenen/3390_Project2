package controllers;

import models.AppDataManager;
import models.Event;
import views.PlannerView;
import views.MainWindow;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Random;

public class PlannerController {


    private final PlannerView view;
    private final AppDataManager dataManager;
    private final MainWindow mainWindow;


    public PlannerController(PlannerView view, AppDataManager dataManager, MainWindow mainWindow) {
        this.view = view;
        this.dataManager = dataManager;
        this.mainWindow = mainWindow;

        setupUI();
        setupListeners();
    }

    //UI Setup
    private void setupUI() {
        // Table should already have two columns: Item | Date
        view.getPlannerTable().setModel(new DefaultTableModel(new Object[]{"Item", "Date"}, 0));
        refreshTable();
    }

    //Event Listeners
    private void setupListeners() {
        view.getAddButton().addActionListener(e -> addEvent());
        view.getDeleteButton().addActionListener(e -> deleteEvent());
        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));
    }

    //add Event
    private void addEvent() {
        String item = view.getSelectedItem();
        String date = view.getSelectedDate();

        if (item == null || item.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an item.");
            return;
        }
        if (date == null || date.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select a date.");
            return;
        }

        // Check for duplicates
        List<Event> events = dataManager.getEventData().getEvents();
        for (Event e : events) {
            if (e.toString().contains(item) && e.toString().contains(date)) {
                JOptionPane.showMessageDialog(view.getMainPanel(),
                        "This item is already scheduled for that date.");
                return;
            }
        }

        // Add new Event
        Random rand = new Random();
        int id = rand.nextInt(10000);
        Event newEvent = new Event(item, date, id, "Shirt", "Pants", "Shoes");
        dataManager.getEventData().addEvent(newEvent);

        refreshTable();

}

             //delete Event
    private void deleteEvent() {
        int selectedRow = view.getPlannerTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an event to delete.");
            return;
        }

        String item = view.getPlannerTable().getValueAt(selectedRow, 0).toString();
        String date = view.getPlannerTable().getValueAt(selectedRow, 1).toString();

        dataManager.getEventData().getEvents()
                .removeIf(e -> e.toString().contains(item) && e.toString().contains(date));

        refreshTable();
    }

         //refresh Table
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) view.getPlannerTable().getModel();
        model.setRowCount(0);

        for (Event e : dataManager.getEventData().getEvents()) {
            model.addRow(new Object[]{e.toString().split(" ")[0], e.toString().split(" ")[1]});
        }
    }
}