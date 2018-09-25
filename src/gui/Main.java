package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    // Loading intro screen
    @Override
    public void start(Stage introStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLIntro.fxml"));
        introStage.setTitle("Auswahl: Gegnertypen");
        introStage.setScene(new Scene(root));
        introStage.show();
    }

//    // Loading main game window
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
//        primaryStage.setTitle("PS2 Programmierpraktikum: City-Domino");
//        primaryStage.setScene(new Scene(root, 1100 , 900));
////        primaryStage.setScene(new Scene(root, 800 , 700)); // Dimensions for starting program on a laptop
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
