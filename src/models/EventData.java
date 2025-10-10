package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventData {
    private final List<Event> events;
    private final String filename;

    public EventData(){
        filename = "./events.data";
        events = new ArrayList<>();
    }

    public void addEvent(Event event){
        events.add(event);
    }

    public void removeEvent(Event event){
        events.remove(event);
    }

    public List<Event> getEvents(){
        return events;
    }

    //persistant data stuff
    public void saveData() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(events);
    }

    public void loadData() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));

        @SuppressWarnings("unchecked")
        List<Event> loaded = (List<Event>) in.readObject();

        events.clear();
        events.addAll(loaded);
    }
}
