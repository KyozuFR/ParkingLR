package lru.linmul;

import java.util.ArrayList;

public class Parking {
    private String id;
    private String name;
    private double ylat;
    private double xlong;
    private double coordX;
    private double coordY;
    //private Date date;
    private int capacity;
    private int occupancy;
    private static ArrayList<Parking> parkings = new ArrayList<>();

    public Parking(String id, String name, double ylat, double xlong, double coordX, double coordY, int capacity, int occupancy) {
        this.id = id;
        this.name = name;
        this.ylat = ylat;
        this.xlong = xlong;
        this.coordX = coordX;
        this.coordY = coordY;
        //this.date = date;
        this.capacity = capacity;
        this.occupancy = occupancy;
        parkings.add(this);
    }

    public static ArrayList<Parking> getParkings() {
        return parkings;
    }

    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ylat=" + ylat +
                ", xlong=" + xlong +
                ", coordX=" + coordX +
                ", coordY=" + coordY +
                //", date=" + date +
                ", capacity=" + capacity +
                ", occupancy=" + occupancy +
                '}';
    }

    private void setParking(String id, String name, double ylat, double xlong, double coordX, double coordY, int capacity, int occupancy) {
        this.id = id;
        this.name = name;
        this.ylat = ylat;
        this.xlong = xlong;
        this.coordX = coordX;
        this.coordY = coordY;
        //this.date = date;
        this.capacity = capacity;
        this.occupancy = occupancy;
        parkings.add(this);
    }
}
