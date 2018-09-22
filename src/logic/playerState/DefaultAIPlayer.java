package logic.playerState;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.token.Domino;

import java.util.List;

public class DefaultAIPlayer extends Player implements BotBehavior {

    public DefaultAIPlayer(GUIConnector gui, int boardSizeX, int boardSizeY) {
        super(gui, boardSizeX, boardSizeY);
    }

    public DefaultAIPlayer(GUIConnector gui, Board board) {
        super(gui, board);
    }

    public DefaultAIPlayer(GUIConnector gui, String strBoard) {
        super(gui, strBoard);
    }

    @Override
    public Bank selectFromBank(Bank bank) {
        // TODO insert code
        return null;
    }

    @Override
    public Domino updateDominoPos(Domino domino) {
        // use in selectFromBank
        // TODO insert code
        return null;
    }

    
}
