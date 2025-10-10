package models;

public class Garment {
    private final String name;
    private final String color;
    private final int garmentID;
    private final String type;
    private final String style;

    public Garment(String name, String color, int garmentID, String type, String style){
        this.name=name;
        this.color=color;
        this.garmentID=garmentID;
        this.type=type;
        this.style=style;
    }
    public String toString(){
        return String.format("%s %s (%d)", name, color, garmentID);
    }
}
