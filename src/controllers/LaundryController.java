package controllers;

import views.MainWindow;
import models.AppDataManager;
import models.Garment;
import views.LaundryView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LaundryController {


    private final LaundryView view;
    private final AppDataManager dataManager;
    private final DefaultListModel<String> laundryListModel = new DefaultListModel<>();
    private final MainWindow mainWindow;

    private WardrobeController wardrobeController;

    //temporary list for laundry items in memory
    private final List<Garment> laundryItems = new ArrayList<>();

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


    //SETUP

    private void setupUI() {
        view.getLaundryList().setModel(laundryListModel);
        loadLaundryItems();
    }

    private void loadLaundryItems() {
        laundryListModel.clear();

        //if you later have a real laundry list in GarmentData, pull from there
        for (Garment g : laundryItems) {
            laundryListModel.addElement(g.toString());
        }
    }

    //LISTENERS

    private void setupListeners() {
        view.getCheckOffButton().addActionListener(e -> checkOffSelectedItem());
        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));
    }

    //ACTIONS

    private void checkOffSelectedItem() {
        String selected = view.getLaundryList().getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an item to check off.");
            return;
        }

        // Find the matching garment in laundryItems
        Garment garmentToReturn = null;
        for (Garment g : laundryItems) {
            if (g.toString().equals(selected)) {
                garmentToReturn = g;
                break;
            }
        }

        if (garmentToReturn != null) {
            laundryItems.remove(garmentToReturn);
            dataManager.getGarmentData().addGarment(garmentToReturn); // return to wardrobe

                        //Send garment back to wardrobe controller so its list refreshes
            if (wardrobeController != null) {
                wardrobeController.reloadWardrobe();
            }

            loadLaundryItems();
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    garmentToReturn + " returned to Wardrobe!");
        }
    }

    //API for other controllers

    /** Called from WardrobeController when user sends something to Laundry */

    public void addToLaundry(Garment garment) {
        laundryItems.add(garment);
        loadLaundryItems();
    }
}
