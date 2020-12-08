package Parking;

import Parking.Views.ParkingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/Parking.fxml"));
        AnchorPane root = loader.load();
        ParkingController controller = loader.getController();
        controller.setMain(this);
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Simulación de estacionamiento");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) { launch(args); }


}
