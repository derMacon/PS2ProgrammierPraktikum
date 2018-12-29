package logic.playerState;

import logic.bankSelection.Bank;
import logic.token.Domino;

/**
 * Interface implementing the bots abillity to make a valid turn in the game
 */
public interface BotBehavior {

    /**
     * Bot selects a domino from a given bank, and returns the selected bank index
     *
     * @param bank the bank that the player will select from
     * @return the selected bank index
     */
    Bank selectFromBank(Bank bank, int ordBank, boolean displayOnGui);

    /**
     * Bot searches for a position on the board where it is possible to lay a domino.
     *
     * @param domino domino with an updated position where it is possible to lay a domino on the
     *               board. If he chooses to dispose the domino or if there is no valid position
     *               possible a null pointer will be returned.
     */
    Domino updateDominoPos(Domino domino);

    void doStandardTurn(Bank currBank, Bank nextBank);

    void doLastTurn(Bank currBank);

    Bank doInitialSelect(Bank currBank, int bankOrd);

    void updateSelectedDom(Bank currBank);


}
