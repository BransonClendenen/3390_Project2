package controllers;

import models.AppDataManager;
import models.Event;
import models.Garment;
import views.MainWindow;
import views.OutfitGeneratorView;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class OutfitGeneratorController {

    private final OutfitGeneratorView view;
    private final AppDataManager dataManager;
    private final MainWindow mainWindow;

    private String selectedShirt;
    private String selectedPants;
    private String selectedShoes;

    private final Random random = new Random();




    private void setupUI() {
        //give the table a model with 3 columns
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Shirt", "Pants", "Shoes"}, 0);
        view.getOutfitTable().setModel(model);


        // style

        JTable table = view.getOutfitTable();

         //Center column headers
        ((JLabel) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

           //Use a bold font for headers
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

           //Set larger text for table cells
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

          //Increase row height for better spacing
        table.setRowHeight(28);

               // center the cell contents horizontally
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getTableHeader().setBackground(new Color(240, 240, 255));  // light blue-gray
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        //table is non-editable
        table.setDefaultEditor(Object.class, null);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


        //grid lines cleaner look
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);


    }


    public OutfitGeneratorController(OutfitGeneratorView view, AppDataManager dataManager, MainWindow mainWindow) {
        this.view = view;
        this.dataManager = dataManager;
        this.mainWindow = mainWindow;


        setupUI();
        setupListeners();
        resetOutfit();
    }

       //Setup listeners
    private void setupListeners() {
        //Random generator
        view.getGenerateButton().addActionListener(e -> generateRandomOutfit());

        //finalize outfit
        view.getFinalizeButton().addActionListener(e -> finalizeOutfit());

        //Back button
        view.getBackButton().addActionListener(e -> mainWindow.showPanel("mainMenu"));
    }


    private void generateRandomOutfit() {
        List<Garment> garments = dataManager.getGarmentData().getGarments();

        if (garments.isEmpty()) {
            JOptionPane.showMessageDialog(view.getMainPanel(), "Your wardrobe is empty!");
            return;
        }

        selectedShirt = getRandomByCategory(garments, "Shirt");
        selectedPants = getRandomByCategory(garments, "Pant");
        selectedShoes = getRandomByCategory(garments, "Shoe");

        if (selectedShirt == null || selectedPants == null || selectedShoes == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "Not enough items in wardrobe to form a complete outfit.");
            resetOutfit();
            return;
        }

        DefaultTableModel model = (DefaultTableModel) view.getOutfitTable().getModel();
        model.setRowCount(0);                //clear previous outfit
        model.addRow(new Object[]{selectedShirt, selectedPants, selectedShoes});


        view.getFinalizeButton().setEnabled(true);
    }

    private void finalizeOutfit() {
        if (selectedShirt == null || selectedPants == null || selectedShoes == null) {
            JOptionPane.showMessageDialog(view.getMainPanel(),
                    "Please generate an outfit first!");
            return;
        }

                     //Add to EventData (appears in Planner)
        String date = java.time.LocalDate.now().toString();
        int id = random.nextInt(10000);
        Event newEvent = new Event("Generated Outfit", date, id,
                selectedShirt, selectedPants, selectedShoes);
        dataManager.getEventData().addEvent(newEvent);

        JOptionPane.showMessageDialog(view.getMainPanel(),
                "Outfit finalized and added to your planner!");

        resetOutfit();
    }

                 //helpers
    private void resetOutfit() {
        DefaultTableModel model = (DefaultTableModel) view.getOutfitTable().getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{"-", "-", "-"});

        view.getFinalizeButton().setEnabled(false);
        selectedShirt = selectedPants = selectedShoes = null;
    }

    private String getRandomByCategory(List<Garment> garments, String keyword) {
        List<Garment> filtered = garments.stream()
                .filter(g -> g.getType().equalsIgnoreCase(keyword))
                .toList();

        if (filtered.isEmpty()) return null;

        Garment randomGarment = filtered.get(random.nextInt(filtered.size()));
        return randomGarment.getName(); // show only name in table
    }

}

