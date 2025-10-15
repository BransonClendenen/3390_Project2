package controllers;

import views.MainMenuView;

import views.MainWindow;

public class MainMenuController {


    private final MainMenuView view;
    private final MainWindow mainWindow;
    private final WardrobeController wardrobeController;

    public MainMenuController(MainMenuView view, MainWindow mainWindow, WardrobeController wardrobeController) {
        this.view = view;
        this.mainWindow = mainWindow;
        this.wardrobeController = wardrobeController;

        setupListeners();
    }

    private void setupListeners() {

        view.getWardrobeButton().addActionListener(e -> {
            wardrobeController.reloadWardrobe();
            mainWindow.showPanel("wardrobe");
        });


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
