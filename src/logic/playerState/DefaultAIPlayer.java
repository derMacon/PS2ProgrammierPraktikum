package logic.playerState;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.token.Domino;
import logic.token.Pos;

import java.util.List;

public class DefaultAIPlayer extends Player {

    public DefaultAIPlayer(GUIConnector gui, int boardSizeX, int boardSizeY) {
        super(gui, boardSizeX, boardSizeY);
    }

    public DefaultAIPlayer(GUIConnector gui, String strBoard) {
        super(gui, strBoard);
        this.gameStats = genGamestatsFromBoard(this.board);
    }

    private List<District> genGamestatsFromBoard(Board board) {
        // TODO insert code
        return null;
    }

    @Override
    public void selectFromBank(Bank bank) {
        // TODO insert code
    }

    @Override
    protected void updateDominoPos(Domino domino) {
        // use in selectFromBank
        // TODO insert code
    }

    
}
