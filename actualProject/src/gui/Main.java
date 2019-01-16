package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.differentPlayerTypes.PlayerType;

/**
 * Main Class laoding up the gui and starting the game.
 */
public class Main extends Application {

    // TODO max. / min. Groessen festlegen.
    // Optimal size: 1202 x 932

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

    // Normal startup
    @Override
    public void start(Stage primaryStage) throws Exception {
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

        // TODO min size
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
