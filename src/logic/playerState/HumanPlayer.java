package logic.playerState;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.token.Domino;
import logic.token.Pos;

public class HumanPlayer extends Player {

    public HumanPlayer(GUIConnector gui, int boardSizeX, int boardSizeY) {
        super(gui, boardSizeX, boardSizeY);
    }

    @Override
    public void selectFromBank(Bank bank) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Pos genDominoPos(Domino domino) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
