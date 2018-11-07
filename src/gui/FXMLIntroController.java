package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import logic.playerTypes.PlayerType;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class FXMLIntroController implements Initializable {

    /**
     * String representation of the choice box items, corresponds with the PlayerType enum
     */
    private ObservableList<String> choiceBoxItemNames;

    /**
     * ChoiceBox for the the type of player2
     */
    @FXML
    private ChoiceBox chcBxPlayer2;

    /**
     * ChoiceBox for the the type of player3
     */
    @FXML
    private ChoiceBox chcBxPlayer3;

    /**
     * ChoiceBox for the the type of player4
     */
    @FXML
    private ChoiceBox chcBxPlayer4;

    /**
     * Button that will be clicked to start a new game with the selected player types
     */
    @FXML
    private Button btnStartGame;

    /**
     * Initializes the list of choice box items and afterwards sets it to the actual choice boxes.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.choiceBoxItemNames = initListOfItems();
        initChoiceBox(this.chcBxPlayer2, this.chcBxPlayer3, this.chcBxPlayer4);
    }

    /**
     * Initialize list without first (Human) player
     *
     * @return list without first (Human) player
     */
    private ObservableList initListOfItems() {
        ObservableList<String> strRepresentations = FXCollections.observableArrayList();
        PlayerType[] possibleEnemyTypes = PlayerType.values();
        for (int i = 1; i < possibleEnemyTypes.length; i++) {
            strRepresentations.add(possibleEnemyTypes[i].getStringRepresentation());
        }
        return strRepresentations;
    }

    /**
     * Adds an item for every type of player in the game, also sets the first item as the default item
     * <p>
     * https://docs.oracle.com/javafx/2/ui_controls/choice-box.htm
     * https://stackoverflow.com/questions/9605346/how-to-make-javafx-2-0-choicebox-to-select-its-first-element
     */
    private void initChoiceBox(ChoiceBox... inputCBoxArray) {
        for (ChoiceBox cBox : inputCBoxArray) {
            cBox.setStyle("-fx-font: 12px \"Papyrus\";");
            cBox.setItems(choiceBoxItemNames);
            cBox.getSelectionModel().selectFirst();
        }
    }

    /**
     * Loads the main game window
     */
    @FXML
    private void startGame() {
        PlayerType[] choosenTypes = evalChoiceBox(this.chcBxPlayer2, this.chcBxPlayer3, this.chcBxPlayer4);
        closeCurrentStageAndOpenNewStageFromFXML("FXMLGame.fxml", choosenTypes);
    }

    /**
     * Closes current stage (determined by casting the window of the first choice box to a stage) and closes it with the
     * corresponding method. Also opens another window from a given fxml file and passes the chossen types to the
     * controller.
     * <p>
     * https://www.youtube.com/watch?v=x3UlAwS6dEE
     *
     * @param fxml         fxml file to open
     * @param choosenTypes choosen player types selected by the user
     */
    private void closeCurrentStageAndOpenNewStageFromFXML(String fxml, PlayerType[] choosenTypes) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            loader.load();

            // Setting the choosen PlayerTypes
            FXMLDocumentController gameController = loader.getController();
            gameController.startGame(choosenTypes);

            Parent root = loader.getRoot();
            Stage mainStage = new Stage();
            mainStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
            mainStage.setTitle("PS2 Programmierpraktikum: City-Domino");
            mainStage.setScene(new Scene(root, 1200, 900));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Casts to stage to be able to close the intro stage with code
        // https://stackoverflow.com/questions/13246211/javafx-how-to-get-stage-from-controller-during-initialization
        ((Stage) this.btnStartGame.getScene().getWindow()).close();
    }

    
    public static void openRootPane() {
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
    
    
    /**
     * Converts a given array of choose boxes to an array of the different PlayerTypes
     *
     * @param boxArray given array of choose boxes
     * @return array of the different PlayerTypes selected by the user
     */
    private PlayerType[] evalChoiceBox(ChoiceBox... boxArray) {
        PlayerType[] allPlayerTypes = PlayerType.values();
        List<PlayerType> output = new LinkedList<>();
        output.add(allPlayerTypes[0]);
        for (int i = 0; i < boxArray.length; i++) {
            // Only enemies are allowed to be selected -> +1 so exclude (first) Human PlayerType
            int selectedItemIdx = boxArray[i].getSelectionModel().getSelectedIndex() + 1;
            output.add(allPlayerTypes[selectedItemIdx]);
        }
        return output.toArray(new PlayerType[0]);
    }

}
