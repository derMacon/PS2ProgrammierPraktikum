package logic.logicTransfer;

import logic.*;
import logic.bankSelection.Bank;
import logic.playerState.Player;
import logic.token.Pos;
import logic.token.Domino;

/**
 * method the logic needs to display current gamestatus on gui.
 * @author GeritKaleck
 */
public interface GUIConnector {

    /**
     * displays the given name as has to turn.
     * @param name
     */
    void showWhosTurn(String name);

    void selectDomino(int idx);

    /**
     * sets a domino to the players bank at index. Empties it if domino is null.
     * @param index
     * @param domino
     */
    void setToBank(int ordBank, Bank bank);

    /**
     * shows the domino in the choose-box. Clears it if dominoRotated is null.
     * @param dominoRotated
     */
    void showInChooseBox(Domino dominoRotated);

    /**
     * updates the grids cells.
     * @param player
     */
    void updatePlayer(Player player);

    void updateAllPlayers(Player[] players);


    /**
     * shows the images corresponding to the two values on grid.
     * @param ordPlayer ordinal value of the current player
     * @param domino choosen domino to display on board
     */
    void showOnGrid(int ordPlayer, Domino domino);

    /**
     * presents the results.
     * @param result
     */
    void showResult(Result result);


    /**
     * Shows players points.
     * @param pl
     * @param points
     */
    void showPointsForPlayer(Player pl, int points);


    void setDominoOnGui(Player pl, Domino dom);



}
