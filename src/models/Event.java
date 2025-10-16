package models;

import java.io.Serializable;

public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final String date;
    private final int eventID;
    private final String eventShirt;
    private final String eventPants;
    private final String eventShoes;

    public Event(String name, String date, int eventID,
                 String eventShirt, String eventPants, String eventShoes) {
        this.name = name;
        this.date = date;
        this.eventID = eventID;
        this.eventShirt = eventShirt;
        this.eventPants = eventPants;
        this.eventShoes = eventShoes;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", name, date, eventID);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getEventID() {
        return eventID;
    }

    public String getEventShirt() {
        return eventShirt;
    }

    public String getEventPants() {
        return eventPants;
    }

    public String getEventShoes() {
        return eventShoes;
    }
}
