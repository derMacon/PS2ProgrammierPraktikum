package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import logic.differentPlayerTypes.PlayerType;

/**
 * Main Class laoding up the gui and starting the game.
 */
public class Main extends Application {

    //<editor-fold defaultstate="collapsed" desc="Alternative loading screen">
    // Loading intro screen
//    @Override
//    public void start(Stage introStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLIntro.fxml"));
//        introStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
//        introStage.setTitle("Auswahl: Gegnertypen");
//        introStage.setScene(new Scene(root));
//        introStage.show();
//    }
    //</editor-fold>

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Anweisung in Application.start():
        // Anwendung beenden wenn eine Ausnahme nicht abgefangen worden ist.
        Thread.currentThread().setUncaughtExceptionHandler((Thread th, Throwable ex)-> {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Unerwarteter Fehler");
            alert.setContentText("Entschuldigung, das hätte nicht passieren dürfen!");
            alert.showAndWait();
        });

        // Normal startup
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLGame.fxml"));
        loader.load();

        // Setting the choosen PlayerTypes
        FXMLDocumentController gameController = loader.getController();
        PlayerType[] defaultTypes = new PlayerType[]{PlayerType.HUMAN, PlayerType.DEFAULT, PlayerType.DEFAULT,
                PlayerType.DEFAULT};
        gameController.startGame(defaultTypes);

        Parent root = loader.getRoot();
        Stage mainStage = new Stage();
        mainStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
        mainStage.setTitle("PS2 Programmierpraktikum: City-Domino");
        Scene scene = new Scene(root, 1200, 900);
        mainStage.setScene(scene);

        // Optimal size: 1202 x 932
        mainStage.setMinWidth(1128);
        mainStage.setMinHeight(877);
        mainStage.setMaxWidth(1259);
        mainStage.setMaxHeight(1028);

        mainStage.setWidth(1202);
        mainStage.setHeight(932);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
