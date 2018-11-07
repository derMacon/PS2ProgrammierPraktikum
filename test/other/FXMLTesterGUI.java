package other;

import gui.FXMLDocumentController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.playerState.Result;
import logic.token.Domino;

import java.io.IOException;

public class FXMLTesterGUI implements GUIConnector {

    @Override
    public void showWhosTurn(int ordPlayer) {

    }

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
    public void showPointsForPlayer(int ordPlayer, int boardPoints) {

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
    public void displayResult(Result res) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("FXMLResult.fxml"));
            loader.load();

            Parent root = loader.getRoot();
            Stage mainStage = new Stage();
            mainStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
            mainStage.setTitle("PS2 Programmierpraktikum: City-Domino");
            mainStage.setScene(new Scene(root, 1200, 900));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
