package lru.linmul;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static void main( String[] args ) {
        launch(args);
    }

    @Override
    public void start( Stage mainStage ) throws Exception {
        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/lru/linmul/scenes/Scene.fxml" ) );

        Parent root = loader.load();

        Scene scene = new Scene(root);

        mainStage.getIcons().add( new Image( Objects.requireNonNull( getClass().getResourceAsStream( "/lru/linmul/lru.png" ) ) ) );
        mainStage.setTitle( "ParkingLR" );
        mainStage.setScene( scene );
        mainStage.setResizable( false );
        mainStage.show();
    }
}