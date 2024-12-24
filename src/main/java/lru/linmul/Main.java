package lru.linmul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class Main extends Application {
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
      
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lru/linmul/scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}