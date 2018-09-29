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
    int selectFromBank(Bank bank);

    /**
     * Bot searches for a position on the board where it is possible to lay a domino.
     *
     * @param domino domino with an updated position where it is possible to lay a domino on the board. If he chooses
     *               to dispose the domino or if there is no valid position possible a null pointer will be returned.
     */
    Domino updateDominoPos(Domino domino);

}
