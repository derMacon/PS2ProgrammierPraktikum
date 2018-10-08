package logic.playerTypes;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.BotBehavior;
import logic.playerState.Player;
import logic.token.Domino;

import java.util.List;

/**
 * Default bot logic rules:
 * TODO fill in the bots logic rules
 */
public class DefaultAIPlayer extends Player implements BotBehavior {

    public DefaultAIPlayer(GUIConnector gui, int idx, int boardSizeX, int boardSizeY) {
        super(gui, idx, boardSizeX, boardSizeY);
    }

    public DefaultAIPlayer(GUIConnector gui, int idx, Board board) {
        super(gui, idx, board);
    }

    public DefaultAIPlayer(GUIConnector gui, int idx, String strBoard) {
        super(gui, idx, strBoard);
    }

    @Override
    public int selectFromBank(Bank bank) {
        // TODO insert code
        return 0;
    }

    /**
     * Generates the max. points available on the board for a given domino.
     * Iterates through board -> sets copy on every pos -> gets the board points -> find max
     * @param domino
     * @return
     */
    private int potentialPointsForDomino(final Domino domino) {
        Domino copy = new Domino(domino); 
        int maxPoints = 0;

    }



    @Override
    public Domino updateDominoPos(Domino domino) {
        // use in selectFromBank
        // TODO insert code
        return null;
    }

    private Domino trySpecificDomino(Domino domino) {
        return null;
    }


}
