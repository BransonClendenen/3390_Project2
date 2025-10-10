package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GarmentData {
    private final List<Garment> garments;
    private final String filename;

    public GarmentData(){
        filename = "./student.data";
        garments = new ArrayList<>();
    }

    public void addGarment(Garment garment){
        garments.add(garment);
    }

    public void removeGarment(Garment garment){
        garments.remove(garment);
    }

    public List<Garment> getGarments(){
        return garments;
    }

    //persistant data stuff
    public void saveData() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(garments);
    }

    public void loadData() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));

        @SuppressWarnings("unchecked")
        List<Garment> loaded = (List<Garment>) in.readObject();

        garments.clear();
        garments.addAll(loaded);
    }
}
