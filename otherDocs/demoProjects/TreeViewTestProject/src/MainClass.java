import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainClass extends Application {

    @Override
    public void start(Stage introStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        introStage.setTitle("Auswahl: Gegnertypen");
        introStage.setScene(new Scene(root));
        introStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
