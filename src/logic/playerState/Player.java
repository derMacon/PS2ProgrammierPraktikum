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
     * List of districts containing the game statistics
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
    public Player(GUIConnector gui, int boardSizeX, int boardSizeY) {
        this(gui, new Board(boardSizeX, boardSizeY));
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
     * Constructor used for testing with a specific board. Empty slots are represented by
     * @param gui Reference to the gui
     * @param strBoard String representation of the board
     */
    public Player(GUIConnector gui, String strBoard) {
        this(gui, new Board(strBoard));
    }

    /**
     * Getter for the board
     * @return board reference
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Gtter for the gamestats
     * @return gamestats
     */
    public List<District> getGameStats() {
        return this.gameStats;
    }

    /**
     * Generates the sum of points the districts represent
     * @return
     */
    public int getBoardPoints() {
        int sum = 0;
        for (int i = 0; i < this.gameStats.size(); i++) {
            sum += this.gameStats.get(i).genPoints();
        }
        return sum;
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
    protected abstract void updateDominoPos(Domino domino);

    /**
     * Displays the given domino on the players board
     * @param playerSelectedDomino domino to display
     */
    public void showOnBoard(Domino playerSelectedDomino) {
        assert null != playerSelectedDomino; 
        this.board.lay(playerSelectedDomino);
    }

}
