package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import logic.playerState.Player;
import logic.playerTypes.PlayerType;

import java.net.URL;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.choiceBoxItemNames = initListOfItems();

        initChoiceBox(this.chcBxPlayer2, this.chcBxPlayer3, this.chcBxPlayer4);
    }

    /**
     * Initialize list without first (Human) player
     * @return list without first (Human) player
     */
    private ObservableList initListOfItems() {
        ObservableList<String> strRepresentations = FXCollections.observableArrayList();
        PlayerType[] possibleEnemyTypes = PlayerType.values();
        for (int i = 1; i < possibleEnemyTypes.length; i++) {
            strRepresentations.add(possibleEnemyTypes[i].getStringRepresentation());
        }
        return  strRepresentations;
    }

    /**
     * Adds an item for every type of player in the game, also sets the first item as the default item
     *
     * https://docs.oracle.com/javafx/2/ui_controls/choice-box.htm
     * https://stackoverflow.com/questions/9605346/how-to-make-javafx-2-0-choicebox-to-select-its-first-element
     */
    private void initChoiceBox(ChoiceBox... inputCBoxArray) {
        for(ChoiceBox cBox : inputCBoxArray) {
            cBox.setItems(choiceBoxItemNames);
            cBox.getSelectionModel().selectFirst();
        }
    }


    @FXML
    private void startGame() {
        PlayerType[] choosenTypes = evalChoiceBox(this.chcBxPlayer2, this.chcBxPlayer3, this.chcBxPlayer4);

        for (PlayerType curr : choosenTypes) {
            System.out.println(curr.getStringRepresentation());
        }

        // TODO insert code: open another scene with the main game and the choosenTypes array

    }

    private PlayerType[] evalChoiceBox(ChoiceBox... boxArray) {
        PlayerType[] output = new PlayerType[boxArray.length];
        PlayerType[] allPlayerTypes = PlayerType.values();
        for (int i = 0; i < boxArray.length; i++) {
            // Only enemies are allowed to be selected -> +1 so exclude (first) Human PlayerType
            int selectedItemIdx = boxArray[i].getSelectionModel().getSelectedIndex() + 1;
            output[i] =allPlayerTypes[selectedItemIdx];
        }
        return output;
    }

}
