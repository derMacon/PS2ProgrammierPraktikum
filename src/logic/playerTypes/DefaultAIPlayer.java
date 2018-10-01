package logic.playerTypes;

import logic.bankSelection.Bank;
import logic.dataPreservation.Logger;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.BotBehavior;
import logic.playerState.Player;
import logic.token.Domino;

import java.util.List;

public class DefaultAIPlayer extends Player implements BotBehavior {

    public DefaultAIPlayer(GUIConnector gui, Logger logger, int idx, int boardSizeX, int boardSizeY) {
        super(gui, logger, idx, boardSizeX, boardSizeY);
    }

    public DefaultAIPlayer(GUIConnector gui, Logger logger, int idx, Board board) {
        super(gui, logger, idx, board);
    }

    public DefaultAIPlayer(GUIConnector gui, Logger logger, int idx, String strBoard) {
        super(gui, logger, idx, strBoard);
    }

    @Override
    public int selectFromBank(Bank bank) {
        // TODO insert code
        return 0;
    }

    @Override
    public Domino updateDominoPos(Domino domino) {
        // use in selectFromBank
        // TODO insert code
        return null;
    }


}
