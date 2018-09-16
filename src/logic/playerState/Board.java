package logic.playerState;

import logic.token.Domino;
import logic.logicTransfer.GUIConnector;
import logic.token.Pos;
import logic.token.SingleTile;

public class Board {

    /**
     * Default Board size for the board
     */
    public static final int BOARD_SIZE = 5;

    /**
     * Board cells containing SingleTiles
     */
    private SingleTile[][] cells;

    /**
     * Standard constructor for this class
     */
    public Board() {
        cells = new SingleTile[BOARD_SIZE][BOARD_SIZE];
    }

    /**
     * Constructor setting bank dimensions, only used for testing
     *
     * @param sizeX
     * @param sizeY
     */
    public Board(int sizeX, int sizeY) {
        this.cells = new SingleTile[sizeX][sizeY];
    }

    /**
     * Constructor setting a string as the board. Single chars represent the
     * ordinal values of the SingleTiles in the individual cells. Used for
     * testing.
     *
     * @param input String representing the ordinal values of the individual
     * board elements
     */
    public Board(String input) {
        String[] lines = input.split("\n");
        int sizeX = lines[0].length();
        int sizeY = lines.length;
        this.cells = new SingleTile[sizeX][sizeY];
        for (int y = 0; y < sizeY; y++) {
            assert lines[y].length() == sizeX;
            for (int x = 0; x < sizeX; x++) {
                if (lines[y].charAt(x) == 0) {
                    cells[x][y] = null;
                } else {
                    this.cells[x][y] = SingleTile.values()[lines[y].charAt(x)];
                }
            }
        }
    }

    /**
     * Getter for x dimension
     * @return number of columns
     */
    public int getCols() {
        return cells.length;
    }

    /**
     * Getter for the y dimension
     * @return number of rows
     */
    public int getRows() {
        return this.cells[0].length;
    }

    /**
     * Checks if a given position is a valid position on the board.
     * @param pos given position that will be checked.
     * @return true if pos is on board and unequal null
     */
    public boolean isValidPos(Pos pos) {
        return null != pos && 0 < pos.x() && this.cells.length > pos.x()
                && 0 < pos.y() && this.cells[0].length > pos.y();
    }

    /**
     * Checks if a domino with a given position fits on the board
     * @param domino to check
     * @return true if the domino fits at the given position on the board
     */
    public boolean fits(Domino domino) {
        // TODO insert code
        return true;
    }

    /**
     * Lays a domino on the board
     * @param domino domino to lay on the board
     */
    public void lay(Domino domino) {
        assert null != domino && fits(domino);
        Pos posFstTile = domino.getFstPos();
        Pos posSndTile = domino.getFstPos();
        if (isValidPos(posFstTile) && isValidPos(posSndTile)) {
            this.cells[posFstTile.x()][posFstTile.y()] = domino.getFstVal();
            this.cells[posSndTile.x()][posSndTile.y()] = domino.getSndVal();
        }
    }
}
