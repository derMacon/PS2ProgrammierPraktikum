package gui;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.logicTransfer.Game;
import other.FakeGUI;

public class ResultPopUpWindowGUITest {

    // Loading main game window
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
//        primaryStage.setTitle("PS2 Programmierpraktikum: City-Domino");
//        primaryStage.setScene(new Scene(root, 1150, 900));
////        primaryStage.setScene(new Scene(root, 800 , 700)); // Dimensions for starting program on a laptop
//        primaryStage.show();
//    }

    public static void main(String[] args) {
//        launch(args);

        String testGameRepresentation
                = "<Spielfeld>\n"
                + "-- -- -- -- --\n"
                + "-- -- H1 P0 --\n"
                + "-- -- CC -- --\n"
                + "-- -- -- -- --\n"
                + "-- -- -- -- --\n"
                + "<Spielfeld>\n"
                + "-- -- -- -- --\n"
                + "-- -- H1 P0 P1\n"
                + "-- -- CC -- --\n"
                + "-- -- -- -- --\n"
                + "-- -- -- -- --\n"
                + "<Spielfeld>\n"
                + "-- -- -- P1 --\n"
                + "-- -- H1 P0 --\n"
                + "-- -- CC -- --\n"
                + "-- -- -- -- --\n"
                + "-- -- -- -- --\n"
                + "<Spielfeld>\n"
                + "-- -- -- -- --\n"
                + "-- -- H1 P0 --\n"
                + "-- -- CC P1 --\n"
                + "-- -- -- -- --\n"
                + "-- -- -- -- --\n"
                + "<Bänke>\n"
                + "\n"
                + ""
                + "<Beutel>\n"
                + "";

        Game game = new Game(new FakeGUI(), testGameRepresentation);
        game.endRound();
        System.out.println("Bis hier hin kommt er");
    }
}
