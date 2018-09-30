package logic.playerState;

import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;

import java.util.LinkedList;
import java.util.List;

public class Board {

    /**
     * String representation for an empty cell
     */
    private static final String STRING_EMPTY_CELL = "--";

    /**
     * String representation for the city hall
     */
    private static final String STRING_CITY_HALL = "CC";

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
            inputCells[i] = lines[i].trim().split("\\s+");
        }

        // Setting the actual SingleTile cells field
        this.sizeX = inputCells[0].length;
        this.sizeY = inputCells.length;
        this.cells = new SingleTile[sizeX][sizeY];
        for (int y = 0; y < sizeY; y++) {
            assert inputCells[y].length == sizeX;
            for (int x = 0; x < sizeX; x++) {
                String currentElement = inputCells[y][x];
                if (currentElement.equals(STRING_EMPTY_CELL)) {
                    cells[x][y] = SingleTile.EC;
                } else {
                    this.cells[x][y] = SingleTile.valueOf(currentElement);
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
     * Initializes the list containing the String represenetation of all SingleTile elements
     */
    private List<String> initListWithStrForSingleTiles() {
        List<String> output = new LinkedList<>();
        for (SingleTile currTile : SingleTile.values()) {
            output.add(currTile.name());
        }
        return output;
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

    // --- loading / saving ---

    /**
     * Generates a string for this object
     * @return string representation for this object
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < this.sizeY; y++) {
            for (int x = 0; x < this.sizeX; x++) {
                output.append(this.cells[x][y]);
                if(x == this.sizeX - 1) {
                    output.append(" ");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }

    public Board fromString(String input) {
        // TODO insert code
        return null;
    }


}
