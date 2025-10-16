package controllers;

import views.MainMenuView;
import views.MainWindow;

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
    }
}
