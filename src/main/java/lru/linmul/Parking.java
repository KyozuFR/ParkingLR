package lru.linmul;

import java.util.ArrayList;
import java.util.Date;

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
    public String getName() {
        return this.name;
    }
    public int getCapacity() {
        return this.capacity;
    }
    public int getOccupancy() {
        return this.occupancy;
    }
    public double getYlat() {
        return this.ylat;
    }
    public double getXlong() {
        return this.xlong;
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

    public Parking getLessOccupied() {
        Parking lessOccupied = parkings.get(0);
        for (Parking parking : parkings) {
            if (parking.occupancy < lessOccupied.occupancy) {
                lessOccupied = parking;
            }
        }
        return lessOccupied;
    }

    public Parking getMostOccupied() {
        Parking mostOccupied = parkings.get(0);
        for (Parking parking : parkings) {
            if (parking.occupancy > mostOccupied.occupancy) {
                mostOccupied = parking;
            }
        }
        return mostOccupied;
    }

    public Parking searchParking(String name) {
        for (Parking parking : parkings) {
            if (parking.name.equals(name)) {
                return parking;
            }
        }
        return null;
    }


}
