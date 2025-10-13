package models;

public class Garment {
    private final String name;
    private final String color;
    private final int garmentID;
    private final String type;
    private final String style;
    //note for sam: maybe instead of having stats be a seperate data model we could just add it onto garments,
    //that way the stats are connected to the garment itself and makes it less of a hassle to try to
    //connect the two seperate models in the controller

    public Garment(String name, String color, int garmentID, String type, String style){
        this.name=name;
        this.color=color;
        this.garmentID=garmentID;
        this.type=type;
        this.style=style;
    }

    @Override
    public String toString() {
        return name;


    /*
    public String toString(){
        return String.format("%s %s (%d)", name, color, garmentID);

     */
    }
}
