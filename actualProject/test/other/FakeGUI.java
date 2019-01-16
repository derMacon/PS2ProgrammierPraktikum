package other;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.logicTransfer.PossibleField;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.playerState.Result;
import logic.token.Domino;

/**
 * Fake gui only used for testing
 */
public class FakeGUI implements GUIConnector {

    @Override
    public void setToBank(int ordBank, Bank bank) {
        System.out.println("setToBank(ordBank : " + ordBank + ", bank" + bank.toString() + ")");
    }

    @Override
    public void showInChooseBox(Domino dominoRotated) {
        if (dominoRotated == null) {
            System.out.println("showInChooseBox(Domino : " + null + ")");
        } else {
            System.out.println("showInChooseBox(Domino : " + dominoRotated.toString() + ")");
        }
    }

    @Override
    public void updatePlayer(Player player) {
        System.out.println("updatePlayer(Player : " + player.toString() + ")");
    }

    @Override
    public void showOnGrid(int ordPlayer, Domino domino) {
        System.out.println("showOnGrid(ordPlayer : " + ordPlayer + "domino : " + domino + ")");
    }

    @Override
    public void showResult(Result result) {
        System.out.println("showResult(result : " + result + ")");
    }

    @Override
    public void selectDomino(int ordBank, int idxDom, int ordPlayer) {
        System.out.println("selectDomino(ordBank : " + ordBank + ", idxDom : " + idxDom + ", ordPlayer : " + ordPlayer + ")");
    }

    @Override
    public void deleteDomFromBank(int ordBank, int idx) {
        System.out.println("delteDomFromBank(ordBank : " + ordBank + ", idx : " + idx + ")");
    }

    @Override
    public void showPopUp(String text) {
        System.out.println("showPopUp(text : " + text);
    }

    @Override
    public void blurOtherFields(PossibleField saturatedField) {
        System.out.println("blurOutFields(saturatedField : " + saturatedField + ")");
    }

    @Override
    public void showWhosTurn(int ordPlayer) {
        System.out.println("showWhosTurn(ordPlayer : " + ordPlayer + ")");
    }

    @Override
    public void showPointsForPlayer(int ordPlayer, int boardPoints) {
        System.out.println("showPointsForPlayer(ordPlayer : " + ordPlayer + ", boardPoints : " + boardPoints);
    }
}
