package logic.playerTypes;

import logic.bankSelection.Bank;
import logic.dataPreservation.Logger;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.token.Domino;
import logic.token.Pos;

/**
 * Class that holds the board / gamestats for the human player. Doesn't actually call any functions, but human player
 * must have the same supertype as the Bots to make it possible for the human player to select a domino from the bank as
 * well as to be in the same array as the AI.
 */
public class HumanPlayer extends Player {

    /**
     * Constructor used in the game, sets the gui and the board dimensions for the player.
     * @param gui gui reference
     * @param boardSizeX width of the board
     * @param boardSizeY height of the board
     */
    public HumanPlayer(GUIConnector gui, int idx, int boardSizeX, int boardSizeY) {
        super(gui, idx, boardSizeX, boardSizeY);
    }

    /**
     * Constructor setting the gui and board
     * @param gui reference to the GUIConnector
     * @param board board to play on
     */
    public HumanPlayer(GUIConnector gui, int idx, Board board) {
        super(gui, idx, board);
    }

    /**
     * Constructor setting the gui and the board (in String formatting), used for testing / reloading.
     * @param gui reference to the GUIConnector
     * @param strBoard String representation of the board
     */
    public HumanPlayer(GUIConnector gui, int idx, String strBoard) {
        super(gui, idx, strBoard);
    }

}
