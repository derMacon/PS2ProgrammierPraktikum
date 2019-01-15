package logic.playerState;

import logic.bankSelection.Bank;

/**
 * Interface implementing the bots abillity to make a valid turn in the game
 */
public interface BotBehavior {

    /**
     * Bot selects a domino from a given bank, and returns the selected bank index
     * Approach: - Find domino with highest number of possible points - If
     * multtiple dominos share the highest score the one with the most efficient
     * domino will be selected
     *
     * @param bank         the bank that the player will select from
     * @param ordBank      ordinal value of the bank
     * @param displayOnGui flag to determine if the selection will be displayed on the gui
     * @return the selected bank index
     */
    Bank selectFromBank(Bank bank, int ordBank, boolean displayOnGui);

    /**
     * Bot selects a domino on the next bank and lays the previous selected domino on his board
     *
     * @param currBank the current bank from which the domino will be provided
     * @param nextBank the next bank from which the player will select the next domino
     */
    void doStandardTurn(Bank currBank, Bank nextBank);

    /**
     * Bot only lays down the previous selected domino without selecting anything from the next bank since it is the
     * end of the current round and there is nothing to select from.
     *
     * @param currBank the current bank from which the domino will be provided
     */
    void doLastTurn(Bank currBank);

    /**
     * The initial select from the current bank will be performed
     *
     * @param currBank the current bank from which the player will select from
     * @param bankOrd  ordinal value of the bank
     * @return a modified bank on which the player has selected a domino
     */
    Bank doInitialSelect(Bank currBank, int bankOrd);

    /**
     * In Order to make a valid selection a player has to know which dominos are located where on the board, a
     * already selected domino kind of already has a place to which the player intended to lay it in the next move.
     * After loading in a file the player has no information about where the player who selected the domino wanted to
     * put it. To clear things up, the player finds a new position on the board and already lays it internally while
     * this is not shown on the gui.
     *
     * @param bank bank from which the domino will be layed
     */
    void updateSelectedDom(Bank bank);


}
