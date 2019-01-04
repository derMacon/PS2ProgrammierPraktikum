package logic.playerState;

import logic.bankSelection.Bank;
import logic.token.DistrictType;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;

import java.util.LinkedList;
import java.util.List;

/**
 * Class implements a players board containing cells with Tiles where the dominos can be layed on
 * to.
 */
public class Board {

    /**
     * Directions the board can be dragged to
     */
    public enum Direction {
        LEFT_MOVE,
        UP_MOVE,
        RIGHT_MOVE,
        DOWN_MOVE;
    }

    public static final String EMPTY_CELL = "--";
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
     * Copy constructor, used for deep copyWithoutSelection for generating the potential points on
     * the board (DefaultAIPlayer)
     *
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
     *
     * @return cells of the board
     */
    public SingleTile[][] getCells() {
        return this.cells;
    }

    /**
     * Fills the empty fields with the corresponding tile
     *
     * @param input input array that will be filled with the empty tile
     * @return input array filled with empty tiles
     */
    private SingleTile[][] fillEmptyCellsWithTile(SingleTile[][] input) {
        for (int y = 0; y < this.sizeY; y++) {
            for (int x = 0; x < this.sizeX; x++) {
                if (null == input[x][y]) {
                    input[x][y] = SingleTile.EC;
                }
            }
        }
        return input;
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
     * - gets all neighbors
     * - checks if neighbors are valid (Same type)
     *
     * @param domino to check
     * @return true if the domino fits at the given position on the board
     */
    public boolean fits(Domino domino) {
        assert null != domino;
        Pos posFst = domino.getFstPos();
        Pos posSnd = domino.getSndPos();
        if (isValidPos(posFst) && isValidPos(posSnd) && isEmpty(posFst) && isEmpty(posSnd)) {
            List<Pos> fstTouchingNeighbour = genTouchingCells(posFst);
            List<Pos> sndTouchingNeighbour = genTouchingCells(posSnd);

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
     * @return true if all neighbor positions hold a singletile value that is compatible with the
     * given singletile
     */
    private boolean checkIfNeighborsAreValid(SingleTile domTile, List<Pos> touchingDominos) {
        if (touchingDominos.size() == 0) {
            return true;
        }
        boolean onlyValidNeighbors = true;
        DistrictType currTileDistrictType;
        int counter = 0;
        do {
            Pos currPos = touchingDominos.get(counter);
            if (isValidPos(currPos)) {
                currTileDistrictType = this.cells[currPos.x()][currPos.y()].getDistrictType();
                onlyValidNeighbors = domTile.getDistrictType() == currTileDistrictType
                        || currTileDistrictType == DistrictType.CENTER
                        || currTileDistrictType == DistrictType.EMPTY_CELL;
            }
            counter++;
        } while (onlyValidNeighbors && counter < touchingDominos.size());
        return onlyValidNeighbors;
    }

    /**
     * Lays a domino on the board, if position equals null or if domino doesn't fit at the given
     * position it will not be layed / disposed
     *
     * @param domino domino to lay on the board
     */
    public void lay(Domino domino) {
        assert null != domino;
        Pos posFstTile = domino.getFstPos();
        Pos posSndTile = domino.getSndPos();
        if (isValidPos(posFstTile) && isValidPos(posSndTile) && fits(domino)) {
            this.cells[posFstTile.x()][posFstTile.y()] = domino.getFstVal();
            this.cells[posSndTile.x()][posSndTile.y()] = domino.getSndVal();
        }
    }

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
                if (this.cells[x][y] == SingleTile.EC) {
                    output.append(EMPTY_CELL);
                } else {
                    output.append(this.cells[x][y]);
                }
                if (x != this.sizeX - 1) {
                    output.append(" ");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }

    // --- loading / saving ---

    /**
     * Generates a String representation of the board to be presented in the file. Since the board already stores a
     * players selected domino in its cells the moment a player selects the particular domino it is necessary to
     * remove it before displaying it in a file.
     *
     * @param player        player instance to which the board belongs, used as key for the selected domino in the bank
     * @param currRoundBank bank for the current round which is needed to remove the selected domino from the
     *                      internal board representation. (This board does not equal the displayed board on the gui
     *                      because every selected domino is already layed immediately after selection to avoid
     *                      multiple dominos on one spot. )
     * @param nextRoundBank Since the selected domino can be on either one of the banks, both banks need to be passed
     *                      in order to check both of them.
     * @return a String representation used for the file in which the board does not contain any previous selected
     * dominos who have not been explicitly put / layed on the board by the player.
     */
    public String toFile(Player player, Bank currRoundBank, Bank nextRoundBank) {
        assert null != player && null != currRoundBank && null != nextRoundBank;
        if (!(player instanceof BotBehavior)) {
            return toString();
        } else {
            // find player selected domino from both banks -> null if not found
            Domino playerSelectedDom = currRoundBank.getPlayerSelectedDomino(player);
            playerSelectedDom = null == playerSelectedDom ? nextRoundBank.getPlayerSelectedDomino(player)
                    : playerSelectedDom;
            return new Board(this).remove(playerSelectedDom).toString();
        }
    }

    /**
     * Removes a given domino from the board. The domino may be empty, then nothing will be deleted
     *
     * @param dom domino to remove from the bank
     * @return the board instance without the given domino
     */
    private Board remove(Domino dom) {
        if (null != dom) {
            Pos fstPos = dom.getFstPos();
            Pos sndPos = dom.getSndPos();
            this.cells[fstPos.x()][fstPos.y()] = SingleTile.EC;
            this.cells[sndPos.x()][sndPos.y()] = SingleTile.EC;
        }
        return this;
    }

    /**
     * Checks if a given objects holds the same data as the current instance (modified from ueb09)
     *
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

    // --- gui related stuff ---

    /**
     * Moves Board to desired position. Returns true if board movement was done successful.
     *
     * @param direction direction to move the board to
     * @return new board reference (easier for other Bots to evaluate which dir to choose to
     * score max. points)
     */
    public Board moveBoard(Direction direction) {
        assert canMoveBoardToDir(direction);
        Board outputBoard = new Board(this.sizeX, this.sizeY);
        switch (direction) {
            case LEFT_MOVE:
                for (int x = 0; x < this.sizeX; x++) {
                    outputBoard.cells[x] = this.cells[(x + 1) % this.sizeX].clone();
                }
                break;
            case UP_MOVE:
                for (int x = 0; x < this.sizeX; x++) {
                    for (int y = 0; y < this.sizeY; y++) {
                        outputBoard.cells[x][y] = this.cells[x][(y + 1) % this.sizeY];
                    }
                }
                break;
            case RIGHT_MOVE:
                for (int x = 0; x < this.sizeX; x++) {
                    outputBoard.cells[x] = this.cells[(x == 0) ? this.sizeX - 1 : (x - 1)].clone();
                }
                break;
            case DOWN_MOVE:
                for (int x = 0; x < this.sizeX; x++) {
                    for (int y = 0; y < this.sizeY; y++) {
                        outputBoard.cells[x][y] = this.cells[x][(y == 0) ? this.sizeY - 1 : y - 1];
                    }
                }
                break;
            default:
                System.err.println("Wrong direction");

        }
        return outputBoard;
    }

    /**
     * Returns the position of a given tile, null if board does not contain it. Mainly used to display Logger message
     * when moving the board on the gui.
     *
     * @param tile tile to search the board for
     * @return the position of a given tile, null if board does not contain it
     */
    public Pos findPos(SingleTile tile) {
        assert null != tile;
        Pos output = null;
        for (int x = 0; x < this.getSizeX(); x++) {
            for (int y = 0; y < this.getSizeY(); y++) {
                if (this.cells[x][y] == tile) {
                    output = new Pos(x, y);
                }
            }
        }
        return output;
    }

    /**
     * Determines if it is possible for a player to move his board to the given direction.
     *
     * @param direction direction to check the bound of the board for
     * @return true if it is possible for the board to move to the given direction.
     */
    public boolean canMoveBoardToDir(Direction direction) {
        return horizontalCheckPossibleBoardMove(direction) || verticalCheckPossibleBoardMove(direction);
    }

    /**
     * Check if the board can be moved horizontally in the given direction.
     *
     * @param direction direction to check the boundary for
     * @return true if it is possible to move the board to the given direction
     */
    private boolean horizontalCheckPossibleBoardMove(Direction direction) {
        if (direction.ordinal() % 2 != 0) {
            return false;
        }
        boolean columnIsEmpty;
        int xIndex;
        // determine which column should be examined
        xIndex = Direction.LEFT_MOVE == direction ? 0 : this.sizeX - 1;
        // actually examine the generated column
        int y = 0;
        do {
            columnIsEmpty = this.cells[xIndex][y] == SingleTile.EC;
            y++;
        } while (columnIsEmpty && y < this.sizeX);
        return columnIsEmpty;
    }

    /**
     * Check if the board can be moved vertically in the given direction.
     *
     * @param direction direction to check the boundary for
     * @return true if it is possible to move the board to the given direction
     */
    private boolean verticalCheckPossibleBoardMove(Direction direction) {
        if (direction.ordinal() % 2 == 0) {
            return false;
        }
        boolean rowIsEmpty;
        int yIndex;
        // determine which row should be examined
        yIndex = Direction.UP_MOVE == direction ? 0 : this.sizeY - 1;
        // actually examine the generated column
        int x = 0;
        do {
            rowIsEmpty = this.cells[x][yIndex] == SingleTile.EC;
            x++;
        } while (rowIsEmpty && x < this.sizeX);
        return rowIsEmpty;
    }
}
