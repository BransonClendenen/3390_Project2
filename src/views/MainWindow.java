package views;


import controllers.LaundryController;
import controllers.StatisticsController;
import controllers.WardrobeController;
import models.AppDataManager;
import controllers.MainMenuController;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{

    private final JPanel mainPanel = new JPanel(new CardLayout());
    private final CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

    //Shared data manager (contains GarmentData + EventData)
    private final AppDataManager appDataManager = new AppDataManager();

    //Views (six forms)
    private final MainMenuView mainMenuView = new MainMenuView();
    private final WardrobeView wardrobeView = new WardrobeView();
    private final LaundryView laundryView = new LaundryView();
    private final PlannerView plannerView = new PlannerView();
    private final StatisticsView statisticsView = new StatisticsView();
    private final OutfitGeneratorView outfitGeneratorView = new OutfitGeneratorView();

    private final StatisticsController statisticsController;
    private final MainMenuController mainMenuController;

    public MainWindow() {
        setTitle("Wardrobe Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // center window

        addViews();

               // Controllers
        statisticsController = new StatisticsController(statisticsView, appDataManager);
        mainMenuController = new MainMenuController(mainMenuView, this);


        LaundryController laundryController = new LaundryController(laundryView, appDataManager);
        WardrobeController wardrobeController = new WardrobeController(wardrobeView, appDataManager, laundryController);
        laundryController.setWardrobeController(wardrobeController); // Link laundry back to wardrobe


        StatisticsController statisticsController = new StatisticsController(statisticsView, appDataManager);
        MainMenuController mainMenuController = new MainMenuController(mainMenuView, this);

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




             // screen switching
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

       //Gives other classes access to data
    public AppDataManager getAppDataManager() {
        return appDataManager;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }

}

/*
first construct all views.
Then you create the controller.
make the window visible.

 */



