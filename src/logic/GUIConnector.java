package logic;

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

    /**
     * sets a domino to the players bank at index. Empties it if domino is null.
     * @param index
     * @param domino
     */
    void setToBank(int ordBank, int index, Domino domino);

    /**
     * shows the domino in the choose-box. Clears it if dominoRotated is null.
     * @param dominoRotated
     */
    void showInChooseBox(Domino dominoRotated);

    /**
     * updates the grids cells.
     * @param board
     */
    void updateGrid(Board board);

    /**
     * shows the images corresponding to the two values on grid.
     * @param ordPlayer
     * @param fstPos
     * @param fstValue
     * @param sndPos
     * @param sndValue
     */
    void showOnGrid(int ordPlayer, Pos fstPos, int fstValue, Pos sndPos, int sndValue);

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
