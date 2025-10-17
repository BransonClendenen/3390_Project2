package controllers;

import models.Event;
import models.Garment;
import views.MainMenuView;
import views.MainWindow;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class MainMenuController {

    private final MainMenuView view;
    private final MainWindow mainWindow;
    private final WardrobeController wardrobeController;
    private final PlannerController plannerController;

    //constructor now receives both controllers
    public MainMenuController(MainMenuView view,
                              MainWindow mainWindow,
                              WardrobeController wardrobeController,
                              PlannerController plannerController) {
        this.view = view;
        this.mainWindow = mainWindow;
        this.wardrobeController = wardrobeController;
        this.plannerController = plannerController;
        setupListeners();
    }

    private void setupListeners() {
        // Wardrobe
        view.getWardrobeButton().addActionListener(e -> {
            wardrobeController.reloadWardrobe();
            mainWindow.showPanel("wardrobe");
        });

        //Planner - now refreshes when opened
        view.getPlannerButton().addActionListener(e -> {
            plannerController.reloadPlanner();
            mainWindow.showPanel("planner");
        });

        // Other buttons unchanged
        view.getLaundryButton().addActionListener(e ->
                mainWindow.showPanel("laundry"));
        view.getStatisticsButton().addActionListener(e ->
                mainWindow.showPanel("statistics"));
        view.getOutfitGeneratorButton().addActionListener(e ->
                mainWindow.showPanel("outfitGenerator"));

        updateLaundryWarnings();
    }

    //Automatic periodic refresh
    private final javax.swing.Timer autoRefreshTimer =
            new javax.swing.Timer(10_000, e -> updateLaundryWarnings()); // every 10 sec

    public void startAutoRefresh() {
        autoRefreshTimer.start();
    }

    public void stopAutoRefresh() {
        autoRefreshTimer.stop();
    }


    // --- Laundry conflict warning -----------------------------------------------
    public void updateLaundryWarnings() {
        JTextArea warningArea = view.getWarningArea();
        if (warningArea == null) return;

        warningArea.setText(""); // clear old warnings

        // Gather laundry items
        List<Garment> laundryItems = mainWindow.getAppDataManager()
                .getLaundryData().getLaundryItems();

        // Gather all events (plans)
        List<Event> events = mainWindow.getAppDataManager()
                .getEventData().getEvents();

        if (laundryItems.isEmpty() || events.isEmpty()) return;

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter cleanFmt = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        DateTimeFormatter legacyFmt =
                DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

        StringBuilder warnings = new StringBuilder();

        for (Event e : events) {
            if (e == null) continue;

            if (e.getName().toLowerCase().startsWith("generated outfit")) continue;

            LocalDate eventDate = parseDateSafely(e.getDate(), cleanFmt, legacyFmt);
            if (eventDate == null || !eventDate.equals(tomorrow)) continue;

            // Check if any laundry item matches this planned item
            for (Garment g : laundryItems) {
                if (g == null) continue;
                if (e.getName().equalsIgnoreCase(g.getName())) {
                    warnings.append(" ").append(g.getName()).append("\n");
                }
            }
        }

        if (warnings.length() == 0) {
            warningArea.setText("No conflicts. All planned items are clean");
        } else {
            warningArea.setText("" + warnings);
        }
    }

    // helper for date parsing
    private LocalDate parseDateSafely(String s, DateTimeFormatter clean, DateTimeFormatter legacy) {
        if (s == null || s.isBlank()) return null;
        try {
            return LocalDate.parse(s, clean);
        } catch (Exception ex1) {
            try {
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                java.util.Date d = sdf.parse(s);
                return d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            } catch (Exception ex2) {
                return null;
            }
        }
    }


}
