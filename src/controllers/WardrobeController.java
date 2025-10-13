package controllers;

import models.AppDataManager;
import models.Garment;
import views.WardrobeView;

import javax.swing.*;
import java.util.List;

public class WardrobeController {


    private final WardrobeView view;
    private final AppDataManager dataManager;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    private String currentCategory = "Shirts"; // default selected category

    private final LaundryController laundryController;

    public WardrobeController(WardrobeView view, AppDataManager dataManager, LaundryController laundryController) {
        this.view = view;
        this.dataManager = dataManager;
        this.laundryController = laundryController;

        setupUI();
        setupListeners();
    }

        //helper
    public void reloadWardrobe() {
        loadCategoryItems();
    }

    //initial setup
    private void setupUI() {
        view.getItemList().setModel(listModel);
        loadCategoryItems();
    }

    private void loadCategoryItems() {
        listModel.clear();

        List<Garment> garments = dataManager.getGarmentData().getGarments();
        for (Garment g : garments) {
            if (g != null) {
            //if (g != null && g.toString().toLowerCase().contains(currentCategory.toLowerCase())) {
                listModel.addElement(g.toString());
            }
        }
    }

    //listener
    private void setupListeners() {

        //category buttons
        view.getShirtsButton().addActionListener(e -> {
            currentCategory = "Shirt";
            loadCategoryItems();
        });

        view.getPantsButton().addActionListener(e -> {
            currentCategory = "Pant";
            loadCategoryItems();
        });

        view.getShoesButton().addActionListener(e -> {
            currentCategory = "Shoe";
            loadCategoryItems();
        });

        // Add new item
        view.getAddButton().addActionListener(e -> addNewItem());

        //Edit selected item
        view.getEditButton().addActionListener(e -> editSelectedItem());

        // Delete selected item
        view.getDeleteButton().addActionListener(e -> deleteSelectedItem());

        //Send selected item to Laundry
        view.getLaundryButton().addActionListener(e -> moveToLaundry());
    }

    //action methods


    private void addNewItem() {
        String name = view.getInputField().getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please enter an item name.");
            return;
        }

        Garment garment = new Garment(name, "Unknown", listModel.size() + 1, currentCategory, "Casual");
        dataManager.getGarmentData().addGarment(garment);

        loadCategoryItems();
        view.getInputField().setText("");
    }

    private void editSelectedItem() {
        String selected = view.getItemList().getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an item to edit.");
            return;
        }

        String newName = JOptionPane.showInputDialog(view.getMainPanel(), "Enter new name:", selected);
        if (newName == null || newName.trim().isEmpty()) return;

        for (Garment g : dataManager.getGarmentData().getGarments()) {
            if (g.toString().equals(selected)) {
                dataManager.getGarmentData().removeGarment(g);
                dataManager.getGarmentData().addGarment(new Garment(newName, "Unknown", listModel.size() + 1, currentCategory, "Casual"));
                break;
            }
        }

        loadCategoryItems();
    }

    private void deleteSelectedItem() {
        String selected = view.getItemList().getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an item to delete.");
            return;
        }

        dataManager.getGarmentData().getGarments().removeIf(g -> g.toString().equals(selected));
        loadCategoryItems();
    }

    private void moveToLaundry() {
        String selected = view.getItemList().getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please select an item to move to Laundry.");
            return;
        }
        Garment movedGarment = null;
        for (Garment g : dataManager.getGarmentData().getGarments()) {
            if (g.toString().equals(selected)) {
                movedGarment = g;
                break;
            }
        }
        if (movedGarment != null) {
            dataManager.getGarmentData().removeGarment(movedGarment);
            laundryController.addToLaundry(movedGarment); //send garment to Laundry
            listModel.removeElement(selected);
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    movedGarment + " moved to Laundry!");
        }
    }


    //  dataManager.getGarmentData().getGarments().removeIf(g -> g.toString().equals(selected));

    /* decomment next line later*/
    //dataManager.getAppDataManager().getEventData(); // placeholder for laundry link
    //listModel.removeElement(selected);

}
