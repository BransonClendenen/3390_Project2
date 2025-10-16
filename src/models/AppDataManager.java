package models;

public class AppDataManager {

      //The two existing model managers
    private final GarmentData garmentData;
    private final EventData eventData;

   //related to LaundryData manager
   private final LaundryData laundryData = new LaundryData();


    public AppDataManager() {
        garmentData = new GarmentData();
        eventData = new EventData();
    }

                 // accessors/getters
    public GarmentData getGarmentData() {
        return garmentData;
    }

    public EventData getEventData() {
        return eventData;
    }

    public LaundryData getLaundryData() {
        return laundryData;  //LaundryData save
    }



    //optional helper methods if you want to centralize saving/loading
    public void saveAll() {
        try {
            garmentData.saveData();
            eventData.saveData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAll() {
        try {
            garmentData.loadData();
            eventData.loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

/* Keeps one instance of each existing model.
Allows you to save/load both datasets together.
you can use 'AppDataManager' instead of creating
 both data managers separately.
 */