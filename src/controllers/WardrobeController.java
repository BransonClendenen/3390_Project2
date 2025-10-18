package controllers;

import models.AppDataManager;
import models.Garment;
import views.WardrobeView;
import views.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WardrobeController {


    private final WardrobeView view;
    private final AppDataManager dataManager;
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    private String currentCategory = "Shirts"; // default selected category

    private final LaundryController laundryController;

    private final MainWindow mainWindow;



    public WardrobeController(WardrobeView view, AppDataManager dataManager,
                              LaundryController laundryController, MainWindow mainWindow) {



        this.view = view;
        this.dataManager = dataManager;
        this.laundryController = laundryController;
        this.mainWindow = mainWindow;

        setupUI();
        setupListeners();
    }

    //helper
    public void reloadWardrobe() {
        currentCategory = "Shirt";
        loadCategoryItems();
    }

    private void styleActionButton(JButton button, Color baseColor) {
        button.setFont(new Font("MV Boli", Font.BOLD, 20));
        button.setBackground(baseColor);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor.brighter());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(baseColor);
            }
        });


}
    private void styleCategoryButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBackground(new Color(230, 230, 250)); // light lavender
        button.setFocusPainted(false);
    }


    //initial setup
    private void setupUI() {
        JList<String> list = view.getItemList();
        list.setModel(listModel);

        //styling
        list.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        list.setFixedCellHeight(26);                 // consistent spacing
        list.setSelectionBackground(new Color(200, 220, 255));  // light blue highlight
        list.setSelectionForeground(Color.BLACK);
        list.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180))); // subtle outline
        list.setBackground(Color.WHITE);
        list.setForeground(Color.DARK_GRAY);
        list.setVisibleRowCount(-1); // let scrollpane decide height

        //Center the text in each cell
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply colorful button styling
        styleActionButton(view.getAddButton(), new Color(235, 235, 255));    // light green
        styleActionButton(view.getEditButton(), new Color(235, 235, 255));   // light yellow
        styleActionButton(view.getDeleteButton(), new Color(235, 235, 255)); // light red
        styleActionButton(view.getLaundryButton(), new Color(111, 199, 217)); // light blue

        // Optional: same style for category buttons
        styleActionButton(view.getShirtsButton(), new Color(200, 250, 200));
        styleActionButton(view.getPantsButton(), new Color(255, 250, 200));
        styleActionButton(view.getShoesButton(), new Color(204, 153, 255));


        dataManager.getGarmentData().getGarments().removeIf(g -> g == null);

        loadCategoryItems();
    }

    private void loadCategoryItems() {
        listModel.clear();

        List<Garment> garments = dataManager.getGarmentData().getGarments();
        for (Garment g : garments) {
            if (g != null && g.getType().equalsIgnoreCase(currentCategory)) {
                listModel.addElement(g.getName());
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

        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));


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

    private void saveGarments() {
        try {
            dataManager.getGarmentData().saveData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void addNewItem() {
        String name = view.getInputField().getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Please enter an item name.");
            return;
        }

        Garment garment = new Garment(name, "Unknown", listModel.size() + 1, currentCategory, "Casual");
        dataManager.getGarmentData().addGarment(garment);
        saveGarments();

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

        dataManager.getGarmentData().getGarments()
                .removeIf(g -> g != null && g.getName().equals(selected));
        saveGarments();
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
            if (g != null && g.getName().equals(selected)) {
                movedGarment = g;
                break;
            }
        }

        if (movedGarment != null) {
            dataManager.getGarmentData().removeGarment(movedGarment);
            laundryController.addToLaundry(movedGarment); // send garment to Laundry

            mainWindow.getPlannerController().reloadPlanner();
            mainWindow.getMainMenuController().updateLaundryWarnings();

            listModel.removeElement(selected);
            saveGarments();
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    movedGarment.getName() + " moved to Laundry!");
        } else {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "Could not find the selected garment. Please refresh the list.");
        }
    }



    //  dataManager.getGarmentData().getGarments().removeIf(g -> g.toString().equals(selected));

    /* decomment next line later*/
    //dataManager.getAppDataManager().getEventData(); // placeholder for laundry link
    //listModel.removeElement(selected);

}
