package controllers;

import views.MainWindow;
import models.AppDataManager;
import models.Garment;
import views.LaundryView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LaundryController {

    private final LaundryView view;
    private final AppDataManager dataManager;
    private final DefaultListModel<String> laundryListModel = new DefaultListModel<>();
    private final MainWindow mainWindow;
    private WardrobeController wardrobeController;

    public LaundryController(LaundryView view, AppDataManager dataManager, MainWindow mainWindow) {
        this.view = view;
        this.dataManager = dataManager;
        this.mainWindow = mainWindow;

        setupUI();
        setupListeners();
    }


    public void setWardrobeController(WardrobeController wardrobeController) {
        this.wardrobeController = wardrobeController;
    }

    private PlannerController plannerController;

    public void setPlannerController(PlannerController plannerController) {
        this.plannerController = plannerController;
    }


    //setup

    private void setupUI() {
        view.getLaundryList().setModel(laundryListModel);


        loadLaundryItems();
    }

    private void loadLaundryItems() {
        laundryListModel.clear();
        List<Garment> items = dataManager.getLaundryData().getLaundryItems();

        for (Garment g : items) {
            laundryListModel.addElement(g.getName());
        }
    }

               // Listeners

    private void setupListeners() {
        view.getCheckOffButton().addActionListener(e -> checkOffSelectedItem());
        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));
    }

     //actions


    private void saveLaundry() {
        try {
            dataManager.getLaundryData().saveData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkOffSelectedItem() {
        String selected = view.getLaundryList().getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an item to check off.");
            return;
        }

        Garment garmentToReturn = null;
        for (Garment g : dataManager.getLaundryData().getLaundryItems()) {
            if (g.getName().equals(selected)) {
                garmentToReturn = g;
                break;
            }
        }


        if (garmentToReturn != null) {
                      //Remove from laundry
            dataManager.getLaundryData().removeGarment(garmentToReturn);


            // Add back to wardrobe
            dataManager.getGarmentData().addGarment(garmentToReturn);

            if (plannerController != null) {
                plannerController.reloadPlanner();  //refreshes the list immediately
            }

            mainWindow.getMainMenuController().updateLaundryWarnings();

              // Refresh both lists
            if (wardrobeController != null) {
                wardrobeController.reloadWardrobe();
            }
            saveLaundry();
            loadLaundryItems();
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    garmentToReturn.getName() + " returned to Wardrobe!");
        }
    }

            // API for other controllers
    public void addToLaundry(Garment garment) {
        dataManager.getLaundryData().addGarment(garment);
        mainWindow.getMainMenuController().updateLaundryWarnings();

        saveLaundry();
        loadLaundryItems();
    }
}

