package logic.playerState;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
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
    public HumanPlayer(GUIConnector gui, int boardSizeX, int boardSizeY) {
        super(gui, boardSizeX, boardSizeY);
    }

    @Override
    public void selectFromBank(Bank bank) {
        throw new UnsupportedOperationException("Method should not be called: bank selection will be updated in the game class");
    }

    @Override
    protected void updateDominoPos(Domino domino) {
        throw new UnsupportedOperationException("Method should not be called: domino pos will be updated in the game class");
    }


}
