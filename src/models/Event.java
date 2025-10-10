package models;

public class Event {
    private final String name;
    private final String date;
    private final int eventID;
    private final String eventShirt;
    private final String eventPants;
    private final String eventShoes;

    public Event(String name, String date, int eventID, String eventShirt, String eventPants, String eventShoes){
        this.name=name;
        this.date=date;
        this.eventID=eventID;
        this.eventShirt=eventShirt;
        this.eventPants=eventPants;
        this.eventShoes=eventShoes;

    }
    public String toString(){
        return String.format("%s %s (%d)", name, date, eventID );
    }
}
