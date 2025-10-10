import models.EventData;
import models.GarmentData;
import views.*;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello I am working");

        //create views
        MainMenuView mainMenuView = new MainMenuView();
        LaundryView laundryView = new LaundryView();
        OutfitGeneratorView outfitGeneratorView = new OutfitGeneratorView();
        PlannerView plannerView = new PlannerView();
        StatisticsView statisticsView = new StatisticsView();
        WardrobeView wardrobeView = new WardrobeView();

        //create models
        GarmentData garmentData = new GarmentData();
        EventData eventData = new EventData();
    }
}
