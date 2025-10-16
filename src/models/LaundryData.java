package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LaundryData implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Garment> laundryItems;
    private final String filename;

    public LaundryData() {
        filename = "./laundry.data";
        laundryItems = new ArrayList<>();
    }

    public void addGarment(Garment garment) {
        laundryItems.add(garment);
    }

    public void removeGarment(Garment garment) {
        laundryItems.remove(garment);
    }

    public List<Garment> getLaundryItems() {
        return laundryItems;
    }

    //Persistence methods
    public void saveData() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(laundryItems);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() throws IOException, ClassNotFoundException {
        File file = new File(filename);
        if (!file.exists()) return;  // nothing to load yet

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            List<Garment> loaded = (List<Garment>) in.readObject();
            laundryItems.clear();
            laundryItems.addAll(loaded);
        }
    }
}

