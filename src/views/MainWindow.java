package views;

import controllers.*;
import models.AppDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private final JPanel mainPanel = new JPanel(new CardLayout());
    private final CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

    // Shared data manager (contains GarmentData + EventData)
    private final AppDataManager appDataManager = new AppDataManager();

    // Views (six forms)
    private final MainMenuView mainMenuView = new MainMenuView();
    private final WardrobeView wardrobeView = new WardrobeView();
    private final LaundryView laundryView = new LaundryView();
    private final PlannerView plannerView = new PlannerView();
    private final StatisticsView statisticsView = new StatisticsView();
    private final OutfitGeneratorView outfitGeneratorView = new OutfitGeneratorView();

    // Controllers
    private PlannerController plannerController;
    private StatisticsController statisticsController;
    private MainMenuController mainMenuController;

    public MainWindow() {
        setTitle("Wardrobe Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        //lock the size
        setResizable(false);
        setLocationRelativeTo(null); // center window

        addViews();


        //load data on start
        try {
            appDataManager.getGarmentData().loadData();
            appDataManager.getEventData().loadData();
            appDataManager.getLaundryData().loadData();
        } catch (Exception ignored) {}

          // Create controllers
        LaundryController laundryController =
                new LaundryController(laundryView, appDataManager, this);

        plannerController = new PlannerController(plannerView, appDataManager, this);


        WardrobeController wardrobeController =
                new WardrobeController(wardrobeView, appDataManager, laundryController, this);



        laundryController.setWardrobeController(wardrobeController);
        laundryController.setPlannerController(plannerController);


        statisticsController =
                new StatisticsController(statisticsView, appDataManager, this);

        mainMenuController =
                new MainMenuController(mainMenuView, this, wardrobeController, plannerController);

        OutfitGeneratorController outfitController =
                new OutfitGeneratorController(outfitGeneratorView, appDataManager, this, statisticsController);


        //Save data when window closes
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    appDataManager.getGarmentData().saveData();
                    appDataManager.getEventData().saveData();
                    appDataManager.getLaundryData().saveData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private void addViews() {
        mainPanel.add(mainMenuView.getMainPanel(), "mainMenu");
        mainPanel.add(wardrobeView.getMainPanel(), "wardrobe");
        mainPanel.add(laundryView.getMainPanel(), "laundry");
        mainPanel.add(plannerView.getMainPanel(), "planner");
        mainPanel.add(statisticsView.getMainPanel(), "statistics");
        mainPanel.add(outfitGeneratorView.getMainPanel(), "outfitGenerator");
    }


        //screen switching
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);

        if ("mainMenu".equals(name)) {
            mainMenuController.startAutoRefresh();
        } else {
            mainMenuController.stopAutoRefresh();
        }

    }

    // Give other classes access to data
    public AppDataManager getAppDataManager() {
        return appDataManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }

    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }
    public PlannerController getPlannerController() {
        return plannerController;
    }


}
