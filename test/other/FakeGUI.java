package other;

import gui.FXMLDocumentController;
import gui.JavaFXGUI;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.playerState.Result;
import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.token.Domino;

public class FakeGUI implements GUIConnector {

    @Override
    public void setToBank(int ordBank, Bank bank) {

    }

    @Override
    public void showInChooseBox(Domino dominoRotated) {

    }

    @Override
    public void updatePlayer(Player player, int ordPlayer) {

    }

    @Override
    public void showOnGrid(int ordPlayer, Domino domino) {

    }

    @Override
    public void showResult(Result result) {

    }

    @Override
    public void updateGrid(int playerOrd, Board board) {

    }

    @Override
    public void selectDomino(int ordBank, int idxDom, int ordPlayer) {

    }

    @Override
    public void greyOutBank(int ordBank) {

    }

    @Override
    public void deleteDomFromBank(int ordBank, int idx) {

    }

    @Override
    public void setColorForArrows(int idx, int mode) {

    }

    @Override
    public void blurBank(int ordBank) {

    }

    @Override
    public void showWhosTurn(int ordPlayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showPointsForPlayer(int ordPlayer, int boardPoints) {

    }

    @Override
    public void displayResult(Result res) {

//            System.out.println("Bis hier hin und nicht weiter");
//            TreeItem<String> rootItem = new TreeItem<>("Spieler nach Plaetzen sortiert");
//            rootItem.setExpanded(true);
//            TreeItem<String> item = new TreeItem<>("Message");
//
//            rootItem.getChildren().add(item);
//
//            TreeView<String> trVvResults = new TreeView<>(rootItem);
//
//            StackPane root = new StackPane();
//            root.getChildren().add(trVvResults);
//
//            Stage resultStage = new Stage();
//            resultStage.setScene(new Scene(root, 300, 250));
//            resultStage.show();

    }
}
