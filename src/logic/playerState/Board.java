package logic.playerState;

import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;

public class Board {

    /**
     * Default Board size for the board
     */
    public final int DEFAULT_BOARD_SIZE = 5;

    /**
     * Number of rows on this board
     */
    private final int sizeX;

    /**
     * Number of rows on this board
     */
    private final int sizeY;

    /**
     * Board cells containing SingleTiles
     */
    private SingleTile[][] cells;

//    /**
//     * Standard constructor for this class
//     */
//    public Board() {
//        cells = new SingleTile[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
//    }

    /**
     * Constructor setting bank dimensions
     *
     * @param sizeX
     * @param sizeY
     */
    public Board(int sizeX, int sizeY) {
        assert 0 < sizeX && 0 < sizeY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.cells = new SingleTile[sizeX][sizeY];
    }

    /**
     * Constructor setting a string as the board. Single chars represent the
     * ordinal values of the SingleTiles in the individual cells. Used for
     * testing. Empty String is not allowed (Assertion Error)
     *
     * @param input String representing the ordinal values of the individual
     *              board elements. -1 for an empty cell.
     */
    public Board(String input) {
        assert input != null && input.length() > 0;
        // filled input String
        String[] lines = input.split("\n");
        String[][] inputCells = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            inputCells[i] = lines[i].trim().split("\\s");
        }
        this.sizeX = inputCells[0].length;
        this.sizeY = inputCells.length;
        this.cells = new SingleTile[sizeX][sizeY];
        for (int y = 0; y < sizeY; y++) {
            assert sizeX == sizeY;
            for (int x = 0; x < sizeX; x++) {
                if (inputCells[x][y].equals("-1")) {
                    cells[x][y] = null;
                } else {
                    int ordTile = Integer.parseInt(inputCells[x][y]);
                    this.cells[x][y] = SingleTile.values()[ordTile];
                }
            }
        }
    }

    /**
     * Getter for x dimension
     *
     * @return number of columns
     */
    public int getSizeX() {
        return this.sizeX;
    }

    /**
     * Getter for the y dimension
     *
     * @return number of rows
     */
    public int getSizeY() {
        return this.sizeY;
    }

    /**
     * Getter for the cells
     */
    public SingleTile[][] getCells() {
        return this.cells;
    }

    /**
     * Checks if a given position is a valid position on the board.
     *
     * @param pos given position that will be checked.
     * @return true if pos is on board and unequal null
     */
    public boolean isValidPos(Pos pos) {
        return null != pos
                && isValidXValue(pos.x())
                && isValidYValue(pos.y());
    }

    /**
     * Checks if the given x value is valid
     *
     * @param x x value to be checked
     * @return true if x value is smaller than x value of the cells
     */
    private boolean isValidXValue(int x) {
        return 0 <= x && this.sizeX > x;
    }

    /**
     * Checks if the given x value is valid
     *
     * @param y y value to be checked
     * @return true if y value is smaller than y value of the cells
     */
    private boolean isValidYValue(int y) {
        return 0 <= y && this.sizeY > y;
    }

    /**
     * Checks if a domino with a given position fits on the board
     *
     * @param domino to check
     * @return true if the domino fits at the given position on the board
     */
    public boolean fits(Domino domino) {
        // TODO insert code
        return true;
    }

    /**
     * Lays a domino on the board
     *
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
