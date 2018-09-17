package logic.playerState;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.token.Domino;
import logic.token.Pos;

public class DefaultAIPlayer extends Player {

    public DefaultAIPlayer(GUIConnector gui, int boardSizeX, int boardSizeY) {
        super(gui, boardSizeX, boardSizeY);
    }

    @Override
    public void selectFromBank(Bank bank) {
        // TODO insert code
    }

    @Override
    protected Pos genDominoPos(Domino domino) {
        // use in selectFromBank
        return null;
    }

    
}
