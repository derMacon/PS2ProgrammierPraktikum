/*
 * To change this license header, clickOnPlayersBank License Headers in Project Properties.
 * To change this template file, clickOnPlayersBank Tools | Templates
 * and open the template in the editor.
 */
package logic.logicTransfer;

import logic.differentPlayerTypes.PlayerType;
import logic.playerState.Board;
import logic.token.Pos;

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
     *
     * @param pos position to which the human players current domino will be put
     */
    void setOnBoard(Pos pos);

    /**
     * clears the board. deals the dominos to the banks. <br>
     * gets one of the stacks dominos and sets it to the middle of the board. <br>
     * human player starts.
     *
     * @param playerTypes array of playertypes that will be instanciated during the starting process of the game
     * @param sizeX       width of every players' board
     * @param sizeY       height of the players' board
     */
    void startGame(PlayerType[] playerTypes, int sizeX, int sizeY);

    /**
     * Selects a domino from the current bank, for human player the domino will be put into the special box to rotate
     * if necessary.
     *
     * @param idx index of the domino on the current bank that will be selected
     */
    void selectDomOnCurrBank(int idx);

    /**
     * Selects a domino from the next bank, for human player the domino will be put into the special box to rotate
     * if necessary.
     *
     * @param idx index of the domino on the next bank that will be selected
     */
    void selectDomOnNextBank(int idx);

    /**
     * Moves Board one box in the given direction
     *
     * @param dir direction to which the board will be moved
     */
    void moveBoard(Board.Direction dir);

    /**
     * Needed to check if the saved file is on the most recent state or if it needs to be updated before any new
     * instance can be loaded.
     *
     * @param input input to check
     * @return true  if the saved file is on the most recent state or if it needs to be updated before any new
     * instance can be loaded.
     */
    boolean equalsStr(String input);

    /**
     * Generates a String representation where the selected player dominos are not already put on the board.
     *
     * @return a String representation where the selected player dominos are not already put on the board.
     */
    String toFile();


}
