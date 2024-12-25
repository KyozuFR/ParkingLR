package lru.linmul;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SceneController {
    @FXML private AnchorPane anchorPane;
    @FXML private TextField parkingField;
    @FXML private Label filePathLabel;
    @FXML private TableView<Parking> parkingTableView;
    @FXML private TableColumn<Parking, String> nameColumn;
    @FXML private TableColumn<Parking, Integer> curPlaceColumn;
    @FXML private TableColumn<Parking, Integer> totalPlaceColumn;
    @FXML private RadioButton all;
    @FXML private RadioButton lessOccupied;
    @FXML private RadioButton mostOccupied;

    private File file = null;
    private Parking parkings = null;
    private String parkingNameToSort = "";

    @FXML
    public void initialize() {
        Platform.runLater( () -> anchorPane.requestFocus() ); // Attend que la page soit prête

        nameColumn.setCellValueFactory( new PropertyValueFactory<Parking, String>( "name" ) );
        curPlaceColumn.setCellValueFactory( new PropertyValueFactory<Parking, Integer>( "occupancy" ) );
        totalPlaceColumn.setCellValueFactory( new PropertyValueFactory<Parking, Integer>( "capacity" ) );
    }

    @FXML
    private void handleParkingNameFilter() {
        if ( file == null ) { return; }

        parkingNameToSort = parkingField.getText();

        all.setSelected( true );
        lessOccupied.setSelected( false );
        mostOccupied.setSelected( false );

        handleTableView( parkingNameToSort ); // vérifie si des radios boutons sont selectionnés pour afficher les parkings correspondants
    }

    @FXML
    private void handleRadioFilter() {
        if ( file == null ) { return; }

        parkingField.setText( "" );
        parkingNameToSort = "";

        if ( all.isSelected() ) {
            handleTableView( parkingNameToSort );
        } else if ( lessOccupied.isSelected() ) {
            Parking lessOccupiedParking = parkings.getLessOccupied();
            handleTableView( lessOccupiedParking.getName() );
        } else if ( mostOccupied.isSelected() ) {
            Parking mostOccupiedParking = parkings.getMostOccupied();
            handleTableView( mostOccupiedParking.getName() );
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

    private void handleTableView(String parkingNameToSort) {
        if ( file == null ) { return; }

        ObservableList<Parking> viewTableParkingsList = FXCollections.observableArrayList();

        Pattern pattern = Pattern.compile( "^" + parkingNameToSort, Pattern.CASE_INSENSITIVE );

        for ( Parking parking : Parking.getParkings() ) {
            Matcher matcher = pattern.matcher( parking.getName() );

            if ( parkingNameToSort.isEmpty() || matcher.find() ) {
                viewTableParkingsList.add( parking );
            }
        }

        parkingTableView.setItems( viewTableParkingsList );
    }
}