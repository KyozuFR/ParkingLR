package lru.linmul;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        CSV csv = new CSV("data.csv");
        for (String[] row : csv.readAll()) {
            //String[] tempdate = row[6].split(" ")[0].split("-");
            Parking parking = new Parking(
                    row[0],
                    row[1],
                    Double.parseDouble(row[2]),
                    Double.parseDouble(row[3]),
                    Double.parseDouble(row[4]),
                    Double.parseDouble(row[5]),
                    //new Date(tempdate[0], tempdate[1], tempdate[2]),
                    Integer.parseInt(row[7]),
                    Integer.parseInt(row[8])
            );
        }

        for (Parking parking : Parking.getParkings()) {
            System.out.println(parking);
        }
        Parking parking0 = Parking.getParkings().getFirst();
        System.out.println(parking0.getLessOccupied());

        System.out.println(parking0.getMostOccupied());

        System.out.println(parking0.searchParking("Les Salines"));
    }
}