/*
 * To change this license header, clickOnPlayersBank License Headers in Project Properties.
 * To change this template file, clickOnPlayersBank Tools | Templates
 * and open the template in the editor.
 */
package logic.logicTransfer;

import logic.playerState.Board;
import logic.playerTypes.PlayerType;
import logic.token.Pos;

import java.net.URI;

/**
 * defines the methods needed by the FXMLController for reacting to the users input.
 *
 * @author klk
 */
public interface GUI2Game {

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
     * Disposes the Human player's selected domino (this.currDomino)
     */
    void disposeCurrDomino();

    /**
     * puts the current Domino to the given position on board and removes it
     * from the choose-box and switches to the next player.
     * <p>
     * //     * @param dom domino to be set on board
     */
    void setOnBoard(Pos pos);

    /**
     * clears the board. deals the dominos to the banks. <br>
     * gets one of the stacks dominos and sets it to the middle of the board. <br>
     * human player starts.
     */
    void startGame(PlayerType[] playerTypes, int sizeX, int sizeY);

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
    void moveBoard(Board.Direction dir);

    /**
     * Saves the current game in a .txt file at the selected path
     */
    void safeGame(URI filePath);

    boolean isInBoundHumanBoard(Pos pos);

    /**
     * Needed to check if the saved file is on the most recent state or if it needs to be updated before any new
     * instance can be loaded. //TODO ueberarbeiten.
     * @param input
     * @return
     */
    boolean equalsStr(String input);



//    /**
//     * loads / generates a game out of the .txt file at the selected path
//     */
//    static void loadGame(Uri filePath);

}
