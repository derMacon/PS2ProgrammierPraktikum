package logic.playerState;

import logic.bankSelection.Bank;
import logic.token.Pos;
import logic.logicTransfer.GUIConnector;
import logic.token.Domino;

import java.util.LinkedList;
import java.util.List;

public abstract class Player {

    /**
     * Board of the player
     */
    protected Board board;
    
    /**
     * List of districts containing the gamestatistics
     */
    protected List<District> gameStats;
    
    /**
     * Gui reference to show 
     */
    protected GUIConnector gui;

    /**
     * Constructor setting up the gui
     * @param gui reference to update gui
     */
    public Player(GUIConnector gui) {
        this(gui, new Board()); 
    }
    
    /**
     * Constructor to set up the gui reference / board, only used for teeting
     * @param gui reference used for updating the gui
     * @param board to set dominos / SingleTiles to 
     */
    public Player(GUIConnector gui, Board board) {
        this.gameStats = new LinkedList<>();
        this.gui = gui; 
        this.board = board;
    }

    /**
     * Getter for the board
     * @return 
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Each player selects a domino from a given bank
     * @param bank bank from which the player selects a domino
     */
    public abstract void selectFromBank(Bank bank);
    
    /**
     * Helping Method - Player updates the domino position to fit on the board
     * @return the a valid position to fit the domino on the board
     */
    protected abstract Pos genDominoPos(Domino domino);

    /**
     * Displays the given domino on the players board
     * @param playerSelectedDomino domino to display
     */
    public void showOnBoard(Domino playerSelectedDomino) {
        assert null != playerSelectedDomino; 
        this.board.lay(playerSelectedDomino);
    }

}
