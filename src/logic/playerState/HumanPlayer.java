package logic.playerState;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.token.Pos;

public class HumanPlayer extends Player {

    public HumanPlayer(GUIConnector gui) {
        super(gui);
    }

    @Override
    void selectFromBank(Bank[] bank) {

    }

    @Override
    Pos genDominoPos() {
        return null;
    }
}
