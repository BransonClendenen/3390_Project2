package controllers;

import models.AppDataManager;
import views.StatisticsView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import views.MainWindow;

public class StatisticsController {

    private final StatisticsView view;
    private final AppDataManager dataManager;
    private final MainWindow mainWindow;


    public StatisticsController(StatisticsView view, AppDataManager dataManager, MainWindow mainWindow) {
        this.view = view;
        this.dataManager = dataManager;
        this.mainWindow = mainWindow;

        setupListeners();
        updateTableForThisMonth();   // show something on start
    }

    private void setupListeners() {

        view.getThisMonthButton().addActionListener(e -> updateTableForThisMonth());
        view.getThreeMonthButton().addActionListener(e -> updateTableForThreeMonths());

        view.getAllTimeButton().addActionListener(e -> updateTableForAllTime());
        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));

    }

    private void updateTableForThisMonth() {
        fillTable(dataManager.getEventData().getEvents(), "This Month");
    }

    private void updateTableForThreeMonths() {
        fillTable(dataManager.getEventData().getEvents(), "Last 3 Months");
    }

    private void updateTableForAllTime() {
        fillTable(dataManager.getEventData().getEvents(), "All Time");
    }

    /** Generic filler method – replace this with real filtering later */

    private void fillTable(java.util.List<?> events, String label) {
        JTable table = view.getStatsTable();

              //to define a table with at least 2 columns
        if (!(table.getModel() instanceof DefaultTableModel)) {
            table.setModel(new DefaultTableModel(new Object[]{"Period", "Events"}, 0));
        }


        DefaultTableModel model = (DefaultTableModel) table.getModel();
         model.setRowCount(0);

        // show which filter is active
        model.addRow(new Object[]{label, events.size() + " events"});
    }
}
/*
ActionListener: cleaner and fires once per click
Automatic table setup: ensures the table always has a working model
Commented and modular: easy to expand when you implement real statistics logic
Works immediately with your existing StatisticsView getters
 */