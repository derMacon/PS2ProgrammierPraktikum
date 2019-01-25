package logic.differentPlayerTypes;

import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.Player;

/**
 * Class that holds the board / gamestats for the human player. Doesn't actually call any functions, but human player
 * must have the same supertype as the Bots to make it possible for the human player to select a domino from the bank as
 * well as to be in the same array as the AI.
 */
public class HumanPlayer extends Player {

    /**
     * Prefix that will be displayed before each call of the printing Method of the Logger instance
     */
    public static final String NAME_PREFIX = "HUMAN";

    /**
     * Constructor used in the game, sets the gui and the board dimensions for the player.
     *
     * @param gui        gui reference
     * @param idx        index of the player in the player array of the game
     * @param boardSizeX width of the board
     * @param boardSizeY height of the board
     */
    public HumanPlayer(GUIConnector gui, int idx, int boardSizeX, int boardSizeY) {
        super(gui, idx, boardSizeX, boardSizeY);
    }

    /**
     * Constructor setting the gui and board
     *
     * @param gui   reference to the GUIConnector
     * @param idx   index of the player in the player array of the game
     * @param board board to play on
     */
    public HumanPlayer(GUIConnector gui, int idx, Board board) {
        super(gui, idx, board);
    }

    /**
     * Constructor setting the gui and the board (in String formatting), used for testing / reloading.
     *
     * @param gui      reference to the GUIConnector
     * @param idx      index of the player in the player array of the game
     * @param strBoard String representation of the board
     */
    public HumanPlayer(GUIConnector gui, int idx, String strBoard) {
        super(gui, idx, strBoard);
    }

    /**
     * Getter for the full name of the player
     *
     * @return the full name of the player
     */
    @Override
    public String getName() {
        return NAME_PREFIX;
    }

    /**
     * Setter for the board. only human player needs this setter, since each Bot handles the movement locally
     *
     * @param board board that will be set
     */
    public void updateBoard(Board board) {
        this.board = board;

    }

    /**
     * Moves and updates the players' board with a given direction
     * @param dir direction to which the board will be shifted
     */
    public void moveUpdateBaord(Board.Direction dir) {
        // update board representation
        this.board = this.board.moveBoard(dir);
        this.gui.updatePlayer(this);

        // update districts
        for (int i = 0; i < this.districts.size(); i++) {
            this.districts.set(i, this.districts.get(i).shiftDirection(dir));
        }
    }

}
