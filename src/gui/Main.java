package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    // Loading intro screen
//    @Override
//    public void start(Stage introStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLResult.fxml"));
//        introStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
//        introStage.setTitle("Auswahl: Gegnertypen");
//        introStage.setScene(new Scene(root));
//        introStage.show();
//    }
    
    // Test Result output
    @Override
    public void start(Stage introStage) throws Exception {
        TreeItem<String> rootItem = new TreeItem<>("Spieler nach Plaetzen sortiert");
        rootItem.setExpanded(true);
        TreeItem<String> item = new TreeItem<>("Message");

        rootItem.getChildren().add(item);

        TreeView<String> trVvResults = new TreeView<>(rootItem);
        
        StackPane root = new StackPane();
        root.getChildren().add(trVvResults);

        Stage resultStage = new Stage(); 
        resultStage.setScene(new Scene(root, 300, 250));
        resultStage.show();
    }

//    // Loading main game window
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLGame.fxml"));
//        primaryStage.setTitle("PS2 Programmierpraktikum: City-Domino");
//        primaryStage.setScene(new Scene(root, 1150 , 900));
////        primaryStage.setScene(new Scene(root, 800 , 700)); // Dimensions for starting program on a laptop
//        primaryStage.show();
//    }
    public static void main(String[] args) {
        launch(args);
    }
}
