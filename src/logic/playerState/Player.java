package logic.playerState;

import logic.dataPreservation.Logger;
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
     * Index of player in player array of the game. Used when game will be loaded / saved / displayed on the gui, so
     * that it is possible to display with its simple array index.
     */
    protected final int idxInPlayerArray;

    /**
     * Constructor setting up the gui
     *
     * @param gui reference to update gui
     * @param idxInPlayerArray index of the player in the games player array
     * @param boardSizeX x - dimension of the players board
     * @param boardSizeY y - dimension of the players board
     */
    public Player(GUIConnector gui, int idxInPlayerArray, int boardSizeX, int boardSizeY) {
        this(gui, idxInPlayerArray, new Board(boardSizeX, boardSizeY));
    }

    /**
     * Constructor to set up the gui reference / board, only used for testing
     *
     * @param gui              reference used for updating the gui
     * @param idxInPlayerArray index in the game's player array.
     * @param board            to set dominos / SingleTiles to
     */
    public Player(GUIConnector gui, int idxInPlayerArray, Board board) {
        this.idxInPlayerArray = idxInPlayerArray;
        this.districts = new LinkedList<>();
        this.gui = gui;
        this.board = board;
    }

    /**
     * Constructor used for loading a specific board (String or File). Also generates the appropriate districts.
     *
     * @param gui              Reference to the gui
     * @param idxInPlayerArray index in the game's player array.
     * @param strBoard         String representation of the board
     */
    public Player(GUIConnector gui, int idxInPlayerArray, String strBoard) {
        this(gui, idxInPlayerArray, new Board(strBoard));
        this.districts = genDistrictsFromBoard(this.board);
    }

    /**
     * Getter for the board
     *
     * @return board reference
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Getter for the player index
     *
     * @return
     */
    public int getIdxInPlayerArray() {
        return this.idxInPlayerArray;
    }

    /**
     * Gtter for the gamestats
     *
     * @return gamestats
     */
    public List<District> getDistricts() {
        return this.districts;
    }

    /**
     * Generates the sum of points the districts represent
     *
     * @return
     */
    public int getBoardPoints() {
        int sum = 0;
        for (District currDistrict : this.districts) {
            sum += currDistrict.genPoints();
        }
        return sum;
    }

    /**
     * Generates the corresponding districts from a given Board
     * @param board Board to generate the districts from
     * @return List of districts from the board
     */
    private List<District> genDistrictsFromBoard(Board board) {
        // TODO insert code
        return null;
    }

    /**
     * Displays the given domino on the players board
     *
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
