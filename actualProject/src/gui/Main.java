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

    // TODO max. / min. Groessen festlegen.

    // Loading intro screen
    @Override
    public void start(Stage introStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLIntro.fxml"));
        introStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
        introStage.setTitle("Auswahl: Gegnertypen");
        introStage.setScene(new Scene(root));
        introStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
