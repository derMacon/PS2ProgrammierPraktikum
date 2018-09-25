/*
 * To change this license header, clickOnPlayersBank License Headers in Project Properties.
 * To change this template file, clickOnPlayersBank Tools | Templates
 * and open the template in the editor.
 */
package logic.logicTransfer;

import com.sun.jndi.toolkit.url.Uri;
import logic.playerState.Board;
import logic.playerTypes.PlayerType;
import logic.token.Domino;
import logic.token.Pos;

/**
 * defines the methods needed by the FXMLController for reacting to the users input.
 *
 * @author klk
 */
public interface GUI2Game {

    /**
     * Setter for the player types, can't be setted in constructor beacause the init method of the docController is called
     * before the introController has the chance to set the chosen values
     * @param playerTypes types of the participating players
     */
    void setPlayerTypes(PlayerType[] types);

    /**
     * the current Domino (that is shown in choose-box) is rotated
     * by 90 degrees clockwise and shown. If the clickOnPlayersBank-box is
     * empty and a cell of the current players bank is empty a new domino is
     * dealt and shown in the box. If the choose-box is empty and the players
     * bank full, the next player is to move.
     */
    void boxClicked();

    /**
     * checks if the current chosen domino fits at the position.
     *
     * @param pos position describes the top-left halfs position
     * @return true, if the current chosen domino fits at the position.
     */
    boolean fits(Pos pos);

    /**
     * Checks if a given domino fits on a given board
     * @param dom domino who's position will be checked
     * @param board board to check for dominos position
     * @return true if domino fits on the board, null one of the parameters equals null or if the domino's position
     * equals null.
     */
    boolean domFits(Domino dom, Board board);

    /**
     * puts the current Domino to the given position on board and removes it
     * from the choose-box and switches to the next player.
     *
//     * @param dom domino to be set on board
     */
    void setOnBoard(Pos pos);

    /**
     * clears the board. deals the dominos to the banks. <br>
     * gets one of the stacks dominos and sets it to the middle of the board. <br>
     * human player starts.
     */
    void startGame();

    /**
     * Selects a domino from the current bank, for human player the domino will be put into the special box to rotate
     * if necessary.
     */
    void selectDomOnCurrBank(int idx);

    /**
     * Selects a domino from the next bank, for human player the domino will be put into the special box to rotate
     * if necessary.
     */
    void selectDomOnNextBank(int idx);

    /**
     * Moves Board one box in the given direction
     */
    void moveBoard(int dir);

    /**
     * Saves the current game in a .txt file at the selected path
     */
    void safeGame(Uri filePath);

//    /**
//     * loads / generates a game out of the .txt file at the selected path
//     */
//    static void loadGame(Uri filePath);

}
