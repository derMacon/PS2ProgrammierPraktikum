package logic.playerTypes;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.BotBehavior;
import logic.playerState.Player;
import logic.token.Domino;

import java.util.List;

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

    @Override
    public Domino updateDominoPos(Domino domino) {
        // use in selectFromBank
        // TODO insert code
        return null;
    }


}
