package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.playerState.Result;
import logic.token.Domino;

/**
 * method the logic needs to display current gamestatus on gui.
 *
 * @author GeritKaleck
 */
public interface GUIConnector {

    /**
     * displays the given name as has to turn.
     *
     * @param ordPlayer ordinal value of the player who's currently doing his turn
     */
    void showWhosTurn(int ordPlayer);

    /**
     * sets a domino to the players bank at index. Empties it if domino is null.
     *
     * @param ordBank ordinal value of the bank that will be updated (0 for current, 1 for next bank)
     * @param bank    reference to the bank that will be displayed / updated on the gui
     */
    void setToBank(int ordBank, Bank bank);

    /**
     * shows the domino in the choose-box. Clears it if dominoRotated is null.
     *
     * @param dominoRotated current domino of the human player
     */
    void showInChooseBox(Domino dominoRotated);

    /**
     * updates all data concerning a certain player (board, districts, points, etc.)
     *
     * @param player    player to update gui section for
     */
    void updatePlayer(Player player);

    /**
     * shows the images corresponding to the two values on grid.
     *
     * @param ordPlayer ordinal value of the current player
     * @param domino    choosen domino to display on board
     */
    void showOnGrid(int ordPlayer, Domino domino);

    /**
     * presents the results.
     *
     * @param result Result that will be shown in another window
     */
    void showResult(Result result);


    /**
     * Shows players points.
     *
     * @param pl        reference to the player
     * @param ordPlayer ordinal value of the player
     */
    void showPointsForPlayer(int ordPlayer, int boardPoints);

    /**
     * Selects a domino from a given bank with a given index and player
     *
     * @param ordBank   ordinal value of the bank
     * @param idxDom    index of the domino that will be selected
     * @param ordPlayer ordinal value of the player the domino will be selected with
     */
    void selectDomino(int ordBank, int idxDom, int ordPlayer);

    /**
     * Deletes a domino from a bank
     *
     * @param ordBank ordinal value of the bank from which the domino will be deleted
     * @param idx     index of the domino that will be deleted
     */
    void deleteDomFromBank(int ordBank, int idx);

    /**
     * Shows popup window with the given message
     * @param text masseage to display in the popup window
     */
    void showPopUp(String text);

    /**
     * Given Field stays focused but all other fields are blured out to indicate where the human player is supposed
     * to make his next move
     * @param saturatedField
     */
    void blurOtherFields(PossibleField saturatedField);

}
