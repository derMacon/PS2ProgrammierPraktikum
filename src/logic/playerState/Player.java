package logic.playerState;

import logic.bankSelection.Bank;
import logic.token.Pos;
import logic.logicTransfer.GUIConnector;
import logic.token.Domino;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {
    protected Board board;
//    protected Bank[] banks;
    protected List<District> gameStats;
    protected GUIConnector gui;

    public Player(GUIConnector gui) {
//        this.board = new Board();
        this.gameStats = new LinkedList<>();
        this.gui = gui;
        this.board = new Board();
    }

    public Board getBoard() {
        return this.board;
    }

    abstract void selectFromBank(Bank[] bank);

    abstract Pos genDominoPos();

    public void showOnBoard(Domino playerSelectedDomino) {
        this.board.lay(playerSelectedDomino);
    }

}
