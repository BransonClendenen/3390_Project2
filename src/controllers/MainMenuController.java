package controllers;

import views.MainMenuView;

import views.MainWindow;

public class MainMenuController {

    private final MainMenuView view;
    private final MainWindow mainWindow;

    public MainMenuController(MainMenuView view, MainWindow mainWindow) {
        this.view = view;
        this.mainWindow = mainWindow;

        setupListeners();
    }

    private void setupListeners() {
        view.getWardrobeButton().addActionListener(e ->
                mainWindow.showPanel("wardrobe"));

        view.getLaundryButton().addActionListener(e ->
                mainWindow.showPanel("laundry"));

        view.getPlannerButton().addActionListener(e ->
                mainWindow.showPanel("planner"));

        view.getStatisticsButton().addActionListener(e ->
                mainWindow.showPanel("statistics"));

        view.getOutfitGeneratorButton().addActionListener(e ->
                mainWindow.showPanel("outfitGenerator"));
    }

}
