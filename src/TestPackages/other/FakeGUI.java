package TestPackages.other;

import logic.playerState.Result;
import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.token.Domino;

public class FakeGUI implements GUIConnector {
    @Override
    public void showWhosTurn(String name) {

    }

    @Override
    public void setToBank(int ordBank, Bank bank) {

    }

    @Override
    public void showInChooseBox(Domino dominoRotated) {

    }

    @Override
    public void updatePlayer(Player player) {

    }

    @Override
    public void updateAllPlayers(Player[] players) {

    }

    @Override
    public void showOnGrid(int ordPlayer, Domino domino) {

    }

    @Override
    public void showResult(Result result) {

    }

    @Override
    public void showPointsForPlayer(Player pl, int points) {

    }

    @Override
    public void setDominoOnGui(Player pl, Domino dom) {

    }

    @Override
    public void updateGrid(int playerOrd, Board board) {

    }
}
