package controllers;

import models.AppDataManager;
import models.Event;
import views.MainWindow;
import views.StatisticsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class StatisticsController {

    private final StatisticsView view;
    private final AppDataManager dataManager;
    private final MainWindow mainWindow;

    public StatisticsController(StatisticsView view, AppDataManager dataManager, MainWindow mainWindow) {
        this.view = view;
        this.dataManager = dataManager;
        this.mainWindow = mainWindow;

        setupUI();
        setupListeners();
        loadThisMonth();   // default
    }

    // ---------- UI ----------
    private void setupUI() {
        JTable table = view.getStatsTable();
        table.setModel(new DefaultTableModel(new Object[]{"Item", "Uses"}, 0));
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
    }

    private void setupListeners() {
        view.getThisMonthButton().addActionListener(e -> loadThisMonth());
        view.getThreeMonthButton().addActionListener(e -> loadLastThreeMonths());
        view.getAllTimeButton().addActionListener(e -> loadAllTime());
        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));
    }

    // ---------- Period loaders ----------
    private void loadThisMonth() {
        view.getMonthLabel().setText("This Month");
        fillCountsAndTable(1);
    }

    private void loadLastThreeMonths() {
        view.getMonthLabel().setText("Last 3 Months");
        fillCountsAndTable(3);
    }

    private void loadAllTime() {
        view.getMonthLabel().setText("All Time");
        fillCountsAndTable(0); // 0 = no time limit
    }

    //Core logic

    private void fillCountsAndTable(int monthsBack) {
        DefaultTableModel model = (DefaultTableModel) view.getStatsTable().getModel();
        model.setRowCount(0);

        List<Event> events = dataManager.getEventData().getEvents();
        if (events == null || events.isEmpty()) return;

        LocalDate now = LocalDate.now();
        DateTimeFormatter cleanFmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter legacyFmt = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);

        Map<String, Integer> counts = new HashMap<>();

        for (Event e : events) {
            if (e == null) continue;

            // Count ONLY finalized outfits
            if (!e.getName().toLowerCase().startsWith("generated outfit")) continue;


            LocalDate d = parseDateSafely(e.getDate(), cleanFmt, legacyFmt);
            if (d == null) continue;

            if (monthsBack == 0 || !d.isBefore(now.minusMonths(monthsBack))) {
                // Count each garment occurrence
                inc(counts, e.getEventShirt());
                inc(counts, e.getEventPants());
                inc(counts, e.getEventShoes());
            }
        }

        // Sort entries by count desc, then by item name asc
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(counts.entrySet());
        sorted.sort((a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            return (cmp != 0) ? cmp : a.getKey().compareToIgnoreCase(b.getKey());
        });

        // Fill table
        for (Map.Entry<String, Integer> entry : sorted) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    private void inc(Map<String, Integer> map, String key) {
        if (key == null || key.isBlank()) return;
        map.put(key, map.getOrDefault(key, 0) + 1);
    }

    // Handles both "yyyy-MM-dd" and legacy "Sat Oct 18 00:00:00 PDT 2025"
    private LocalDate parseDateSafely(String s, DateTimeFormatter clean, DateTimeFormatter legacy) {
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

    //external refresh for other controllers
    public void reloadStatistics() {
        // keep same period currently displayed
        String label = view.getMonthLabel().getText();
        switch (label) {
            case "Last 3 Months" -> loadLastThreeMonths();
            case "All Time" -> loadAllTime();
            default -> loadThisMonth(); // default
        }
    }

}


/*
ActionListener: cleaner and fires once per click
Automatic table setup: ensures the table always has a working model
Commented and modular: easy to expand when you implement real statistics logic
Works immediately with your existing StatisticsView getters
 */