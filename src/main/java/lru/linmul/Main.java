package lru.linmul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        /**CSV csv = new CSV("C:/users/amaur/Downloads/od_parking_dispo.csv");
        Parking parking = null;
        for (String[] row : csv.readAll()) {
            //String[] tempdate = row[6].split(" ")[0].split("-");
            parking = new Parking(
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

        for (Parking p : Parking.getParkings()) {
            System.out.println(p);
        }

        System.out.println(parking.getLessOccupied());

        System.out.println(parking.getMostOccupied());

        System.out.println(parking.searchParking("Les Salines"));*/
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lru/linmul/Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        mainStage.setTitle("ParkingLR");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }
}