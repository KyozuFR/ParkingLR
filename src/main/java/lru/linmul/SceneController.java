package lru.linmul;

import com.sun.javafx.webkit.WebConsoleListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SceneController {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField parkingField;
    @FXML private RadioButton all;
    @FXML private RadioButton lessOccupied;
    @FXML private RadioButton mostOccupied;
    @FXML private WebView mapView;
    @FXML private TableView<Parking> parkingTableView;
    @FXML private TableColumn<Parking, String> nameColumn;
    @FXML private TableColumn<Parking, Integer> curPlaceColumn;
    @FXML private TableColumn<Parking, Integer> totalPlaceColumn;
    @FXML private Label filePathLabel;

    private WebEngine webEngine;
    private File file = null;
    private Parking parkings = null;
    private String parkingNameToSort = "";

    @FXML
    public void initialize() {
        Platform.runLater(() -> anchorPane.requestFocus());

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        curPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        totalPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        // charger la carte
        webEngine = mapView.getEngine();
        webEngine.load(Objects.requireNonNull(getClass().getResource("/lru/linmul/map.html")).toExternalForm());

        webEngine.setOnAlert(event -> {
            String data = event.getData();
            if (data.startsWith("markerClick:")) {
                String parkingName = data.split(":")[1];
                selectTableRowByCoordinates( parkingName );
            } else {
                System.out.println("Console log: " + data);
            }
        });

        // Injecter un script pour rediriger console.log
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                webEngine.executeScript(
                        "console.log = function(message) {" +
                                "    alert(message);" +
                                "};"
                );
            }
        });

        // quand l'utilisateur clique sur une ligne du tableau
        parkingTableView.getSelectionModel().selectedItemProperty().addListener(( observable, oldValue, newValue ) -> {
            if (newValue != null) {
                handleLineDataView( newValue );
            }
        });
    }

    @FXML
    private void handleParkingNameFilter() {
        parkingNameToSort = parkingField.getText();
        handleRadioFilter(); // vérifie si des radios boutons sont selectionnés pour afficher les parkings correspondants
    }

    @FXML
    private void handleRadioFilter() {
        if ( file == null ) { return; }

        if ( all.isSelected() ) {
            handleDataView( parkingNameToSort );
        } else if ( lessOccupied.isSelected() ) {
            Parking lessOccupiedParking = parkings.getLessOccupied();
            handleDataView( lessOccupiedParking.getName() );
        } else if ( mostOccupied.isSelected() ) {
            Parking mostOccupiedParking = parkings.getMostOccupied();
            handleDataView( mostOccupiedParking.getName() );
        }
    }

    @FXML
    private void importCSVFile() {
        if ( file != null ) { return; } // Pour éviter les problèmes de duplications (à modifier si on veut importer d'autres fichiers)

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "CSV Files", "*.csv" ) );

        file = fileChooser.showOpenDialog( null );

        if ( file != null ) {
            String filePath = file.getPath();
            filePathLabel.setText( "Fichier selectionné: " + filePath );

            // peupler la liste des parkings
            CSV csv = new CSV( filePath );
            for ( String[] row : csv.readAll() ) {
                parkings = new Parking(
                        row[0],
                        row[1],
                        Double.parseDouble(row[2]),
                        Double.parseDouble(row[3]),
                        Double.parseDouble(row[4]),
                        Double.parseDouble(row[5]),
                        Integer.parseInt(row[7]),
                        Integer.parseInt(row[8])
                );
            }

            handleRadioFilter(); // vérifie si des radios boutons sont selectionnés pour afficher les parkings correspondants
        } else {
            filePathLabel.setText( "Aucun fichier sélectionné" );
        }
    }

    private void handleDataView(String parkingNameToSort) {
        if ( file == null ) { return; }

        ObservableList<Parking> viewTableParkingsList = FXCollections.observableArrayList();

        Pattern pattern = Pattern.compile( "^" + parkingNameToSort, Pattern.CASE_INSENSITIVE );

        clearParkingsMarkers();
        for ( Parking parking : Parking.getParkings() ) {
            Matcher matcher = pattern.matcher( parking.getName() );

            if ( parkingNameToSort.isEmpty() || matcher.find() ) {
                viewTableParkingsList.add( parking );
                addParkingMarkerToMap( parking );
            }
        }

        parkingTableView.setItems(viewTableParkingsList);
    }

    private void handleLineDataView( Parking parking ) {
        if ( file == null ) { return; }

        String script = String.format(
                Locale.US,
                "selectMarker(%f, %f)",
                parking.getYlat(),
                parking.getXlong()
        );
        webEngine.executeScript( script );
    }

    private void addParkingMarkerToMap(Parking parking) {
        String script = String.format(
                Locale.US,
                "addMarker(%f, %f, '%s: (%d/%d) places')",
                parking.getYlat(),
                parking.getXlong(),
                parking.getName(),
                parking.getOccupancy(),
                parking.getCapacity()
        );
        webEngine.executeScript(script);
    }

    @FXML
    private void clearParkingsMarkers() {
        String script = String.format(
                Locale.US,
                "clearMarkers()"
        );
        webEngine.executeScript( script );
    }

    private void selectTableRowByCoordinates(String parkingName) {
        Parking parking = parkings.searchParking( parkingName );

        if ( parkings.searchParking( parkingName ) != null ) {
            parkingTableView.getSelectionModel().select( parking );
        }
    }
}