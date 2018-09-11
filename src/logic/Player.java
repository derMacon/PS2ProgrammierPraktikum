package logic;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {
    protected Board board;
    protected Bank[] banks;
    protected List<District> gameStats;
    protected GUIConnector gui;

    public Player(GUIConnector gui) {
        this.board = new Board();
        this.gameStats = new LinkedList<>();
        this.gui = gui;
    }

    abstract void selectFromBank(Bank[] bank);

    abstract Pos genDominoPos();

    public void setToBoard() {
        Domino playerSelectedDomino = this.banks[Bank.CURRENT_BANK_IDX].getPlayerSelectedDomino(this);
        this.board.lay(playerSelectedDomino);

    }

}
