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
                 String eventShirts, String eventPants, String eventShoes) {
        this.name = name;
        this.date = date;
        this.eventID = eventID;
        this.eventShirt = eventShirts;
        this.eventPants = eventPants;
        this.eventShoes = eventShoes;

    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", name, date, eventID);
    }
}

