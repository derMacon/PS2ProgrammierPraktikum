package logic.playerState;

import logic.bankSelection.Bank;
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
    protected List<District> districts;
    
    /**
     * Gui reference to show 
     */
    protected GUIConnector gui;

    /**
     * Index of player in player array of the game
     */
    protected final int idxInPlayerArray;

    /**
     * Constructor setting up the gui
     * @param gui reference to update gui
     */
    public Player(GUIConnector gui, int idxInPlayerArray, int boardSizeX, int boardSizeY) {
        this(gui, idxInPlayerArray, new Board(boardSizeX, boardSizeY));
    }
    
    /**
     * Constructor to set up the gui reference / board, only used for teeting
     * @param gui reference used for updating the gui
     * @param board to set dominos / SingleTiles to
     */
    public Player(GUIConnector gui, int idxInPlayerArray, Board board) {
        this.idxInPlayerArray = idxInPlayerArray;
        this.districts = new LinkedList<>();
        this.gui = gui; 
        this.board = board;
    }

    /**
     * Constructor used for testing with a specific board. Empty slots are represented by
     * @param gui Reference to the gui
     * @param strBoard String representation of the board
     */
    public Player(GUIConnector gui, int idxInPlayerArray, String strBoard) {
        this(gui, idxInPlayerArray, new Board(strBoard));
        this.districts = genGamestatsFromBoard(this.board);
    }

    private List<District> genGamestatsFromBoard(Board board) {
        // TODO insert code
        return null;
    }

    /**
     * Getter for the board
     * @return board reference
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Getter for the player index
     * @return
     */
    public int getIdxInPlayerArray() {
        return this.idxInPlayerArray;
    }

    /**
     * Gtter for the gamestats
     * @return gamestats
     */
    public List<District> getDistricts() {
        return this.districts;
    }

    /**
     * Generates the sum of points the districts represent
     * @return
     */
    public int getBoardPoints() {
        int sum = 0;
        for(District currDistrict : this.districts) {
            sum += currDistrict.genPoints();
        }
        return sum;
    }

    /**
     * Displays the given domino on the players board
     * @param playerSelectedDomino domino to display
     */
    public void showOnBoard(Domino playerSelectedDomino) {
        assert null != playerSelectedDomino; 
        this.board.lay(playerSelectedDomino);
        this.gui.showOnGrid(this.idxInPlayerArray, playerSelectedDomino);
    }

    @Override
    public String toString() {
        return this.board.toString();
    }



}
