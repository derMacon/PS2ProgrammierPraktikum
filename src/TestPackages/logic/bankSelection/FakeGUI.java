package TestPackages.logic.bankSelection;

import logic.Result;
import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Player;
import logic.token.Domino;
import logic.token.Pos;

public class FakeGUI implements GUIConnector {
    @Override
    public void showWhosTurn(String name) {

    }

    @Override
    public void selectDomino(int idx) {

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
    public void showOnGrid(int ordPlayer, Pos fstPos, int fstValue, Pos sndPos, int sndValue) {

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
}
