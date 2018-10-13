package logic.playerState;

import logic.token.DistrictType;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;

import java.util.Arrays;
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
     * @param sizeX x dimension of the board
     * @param sizeY y dimension of the board
     */
    public Board(int sizeX, int sizeY) {
        assert 0 < sizeX && 0 < sizeY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.cells = initCCinMiddleOfBoard(sizeX, sizeY);
        this.cells = fillEmptyCellsWithTile(this.cells);
    }


    /**
     * Copy constructor, used for deep copyWithoutSelection for generating the potential points on the board (DefaultAIPlayer)
     * @param other board to copyWithoutSelection
     */
    public Board(Board other) {
        assert null != other;
        this.sizeX = other.sizeX;
        this.sizeY = other.sizeY;
        this.cells = new SingleTile[this.sizeX][this.sizeY];
        for (int y = 0; y < this.sizeY; y++) {
            for (int x = 0; x < this.sizeX; x++) {
                this.cells[x][y] = other.cells[x][y];
            }
        }
    }

    /**
     * Initializes the board with a city center in the middle of the board
     *
     * @param xSize x dimension of the board
     * @param ySize y dimension of the board
     * @return initialized board with city center in the middle
     */
    private SingleTile[][] initCCinMiddleOfBoard(int xSize, int ySize) {
        SingleTile[][] output = new SingleTile[sizeX][sizeY];
        int xMiddle = xSize / 2;
        int yMiddle = ySize / 2;
        if (xSize % 2 == 0 && 0 < xMiddle) {
            xMiddle--;
        }
        if (ySize % 2 == 0 && 0 < yMiddle) {
            yMiddle--;
        }
        output[xMiddle][yMiddle] = SingleTile.CC;
        return output;
    }

    /**
     * Fills the empty fields with the corresponding tile
     *
     * @param input input array that will be filled with the empty tile
     * @return input array filled with empty tiles
     */
    private SingleTile[][] fillEmptyCellsWithTile(SingleTile[][] input) {
        System.out.println(input[0].length);
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length; x++) {
                if (null == input[x][y]) {
                    input[x][y] = SingleTile.EC;
                }
            }
        }
        return input;
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
     * - gets all neighbors
     * - checks if neighbors are valid (Same type)
     *
     * @param domino to check
     * @return true if the domino fits at the given position on the board
     */
    public boolean fits(Domino domino) {
        // TODO insert code
        assert null != domino;
        Pos posFst = domino.getFstPos();
        Pos posSnd = domino.getSndPos();
        if (isValidPos(posFst) && isValidPos(posSnd) && isEmpty(posFst) && isEmpty(posSnd)) {
            List<Pos> fstTouchingNeighbour = genTouchingCells(posFst);
            List<Pos> sndTouchingNeighbour = genTouchingCells(posSnd);

            boolean sum = !fstTouchingNeighbour.isEmpty() || !sndTouchingNeighbour.isEmpty();
            boolean fstNeighbours = checkIfNeighborsAreValid(domino.getFstVal(), fstTouchingNeighbour);
            boolean sndNeighbours = checkIfNeighborsAreValid(domino.getSndVal(), sndTouchingNeighbour);


            return (!fstTouchingNeighbour.isEmpty() || !sndTouchingNeighbour.isEmpty())
                    && checkIfNeighborsAreValid(domino.getFstVal(), fstTouchingNeighbour)
                    && checkIfNeighborsAreValid(domino.getSndVal(), sndTouchingNeighbour);
        } else {
            return false;
        }
    }


    /**
     * Collects the touching cells from a given position.
     *
     * @param position position to examine
     * @return a list containing all neighbors who are not out of bound and not empty
     */
    private List<Pos> genTouchingCells(Pos position) {
        List<Pos> touchedCells = new LinkedList<>();
        for (Pos currentNeighbour : position.getNeighbours()) {
            if (isValidPos(currentNeighbour) && !isEmpty(currentNeighbour)) {
                touchedCells.add(currentNeighbour);
            }
        }
        return touchedCells;
    }

    /**
     * Checks if the board has empty at the given position
     *
     * @param inputPos position to check
     * @return true if board is empty at the given position
     */
    private boolean isEmpty(Pos inputPos) {
        return SingleTile.EC == cells[inputPos.x()][inputPos.y()];
    }

    /**
     * Checks if a given list of touching cell positions match types with a given singletile
     *
     * @param domTile         single tile from a domino to examine
     * @param touchingDominos list of neighbor positions to examine
     * @return true if all neighbor positions hold a singletile value that is compatible with the given singletile
     */
    private boolean checkIfNeighborsAreValid(SingleTile domTile, List<Pos> touchingDominos) {
        if(touchingDominos.size() == 0) {
            return true;
        }
        boolean onlyValidNeighbors;
        DistrictType currTileDistrictType;
        int counter = 0;
        do {
            Pos currPos = touchingDominos.get(counter);
            currTileDistrictType = this.cells[currPos.x()][currPos.y()].getDistrictType();
            onlyValidNeighbors = domTile.getDistrictType() == currTileDistrictType || currTileDistrictType == DistrictType.CENTER;
            counter++;
        } while (onlyValidNeighbors && counter < touchingDominos.size());
        return onlyValidNeighbors;
    }


    /**
     * Lays a domino on the board
     *
     * @param domino domino to lay on the board
     */
    public void lay(Domino domino) {
        assert null != domino && fits(domino);
        Pos posFstTile = domino.getFstPos();
        Pos posSndTile = domino.getSndPos();
        if (isValidPos(posFstTile) && isValidPos(posSndTile)) {
            this.cells[posFstTile.x()][posFstTile.y()] = domino.getFstVal();
            this.cells[posSndTile.x()][posSndTile.y()] = domino.getSndVal();
        }
    }

    // --- loading / saving ---

    /**
     * Generates a string for this object
     *
     * @return string representation for this object
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < this.sizeY; y++) {
            for (int x = 0; x < this.sizeX; x++) {
                output.append(this.cells[x][y]);
                if (x == this.sizeX - 1) {
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


    /**
     * Checks if a given objects holds the same data as the current instance (modified from ueb09)
     * @param obj object to be examined
     * @return true if equals, false if not or if given obj is a null pointer
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // check individual cells
        final Board other = (Board) obj;
        boolean areSame = true;
        for (int y = 0; y < this.sizeY && areSame; y++) {
            for (int x = 0; x < this.sizeX && areSame; x++) {
                areSame = this.cells[x][y] == other.cells[x][y];
            }
        }
        return areSame;
    }


}
