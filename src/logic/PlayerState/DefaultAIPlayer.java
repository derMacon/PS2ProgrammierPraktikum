package logic.PlayerState;

import logic.BankSelection.Bank;
import logic.LogicTransfer.GUIConnector;
import logic.Token.Pos;

public class DefaultAIPlayer extends Player {

    public DefaultAIPlayer(GUIConnector gui) {
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
