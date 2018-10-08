package logic.playerState;

import logic.logicTransfer.GUIConnector;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class Player {

    /**
     * Board of the player
     */
    protected Board board;

    /**
     * List of districts containing the game statistics
     */
    protected List<District> districts;

    /**
     * Gui reference to show
     */
    protected GUIConnector gui;

    /**
     * Index of player in player array of the game. Used when game will be loaded / saved / displayed on the gui, so
     * that it is possible to display with its simple array index.
     */
    protected final int idxInPlayerArray;

    /**
     * Constructor setting up the gui
     *
     * @param gui              reference to update gui
     * @param idxInPlayerArray index of the player in the games player array
     * @param boardSizeX       x - dimension of the players board
     * @param boardSizeY       y - dimension of the players board
     */
    public Player(GUIConnector gui, int idxInPlayerArray, int boardSizeX, int boardSizeY) {
        this(gui, idxInPlayerArray, new Board(boardSizeX, boardSizeY));
    }

    /**
     * Constructor to set up the gui reference / board, only used for testing
     *
     * @param gui              reference used for updating the gui
     * @param idxInPlayerArray index in the game's player array.
     * @param board            to set dominos / SingleTiles to
     */
    protected Player(GUIConnector gui, int idxInPlayerArray, Board board) {
        this.idxInPlayerArray = idxInPlayerArray;
        this.districts = new LinkedList<>();
        this.gui = gui;
        this.board = board;
    }

    /**
     * Constructor used for loading a specific board (String or File). Also generates the appropriate districts.
     *
     * @param gui              Reference to the gui
     * @param idxInPlayerArray index in the game's player array.
     * @param strBoard         String representation of the board
     */
    public Player(GUIConnector gui, int idxInPlayerArray, String strBoard) {
        this(gui, idxInPlayerArray, new Board(strBoard));
        this.districts = genDistrictsFromBoard(this.board);
    }

    /**
     * Getter for the board
     *
     * @return board reference
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Getter for the player index
     *
     * @return
     */
    public int getIdxInPlayerArray() {
        return this.idxInPlayerArray;
    }

    /**
     * Gtter for the gamestats
     *
     * @return gamestats
     */
    public List<District> getDistricts() {
        return this.districts;
    }

    /**
     * Generates the sum of points the districts represent
     * //TODO delete this method -> use genDistrictPoints
     * @return
     */
    public int getBoardPoints() {
        int sum = 0;
        for (District currDistrict : this.districts) {
            sum += currDistrict.genPoints();
        }
        return sum;
    }


    public int genDistrictPoints(List<District> districts) {
        int sum = 0;
        for (District currDistrict : districts) {
            sum += currDistrict.genPoints();
        }
        return sum;
    }


    /**
     * Generates the corresponding districts from a given Board
     *
     * @param board Board to generate the districts from
     * @return List of districts from the board
     */
    private List<District> genDistrictsFromBoard(Board board) {
        List<District> futureDistrictList = new LinkedList<>();
        SingleTile[][] cells = board.getCells();
        for (int y = 0; y < board.getSizeY(); y++) {
            for (int x = 0; x < board.getSizeX(); x++) {
                futureDistrictList = addToAppropriateDistrict(cells[x][y], new Pos(x, y), futureDistrictList);
            }
        }
        return futureDistrictList;
    }

    /**
     * Searches for an appropriate District to which a given tile and its position can be added. If no district is
     * found, a new one will be created. If the tile merges two districts, those two will be merged to one new district
     * and the tile / pos will be added afterwards.
     *
     * @param tile      tile to find a district for
     * @param pos       pos to find a district for
     * @param districts list of district to be examined
     * @return an updated list of districts
     */
    private List<District> addToAppropriateDistrict(SingleTile tile, Pos pos, List<District> districts) {
        if (SingleTile.EC != tile && SingleTile.CC != tile) {
            // find possible districts
            List<District> possibleDistricts = findOrCreatePossibleDistricts(tile, pos, districts);
            // delete possible districts from current district list (to avoid duplicates)
            districts.removeAll(possibleDistricts);
            // unit (merge) possible districts
            District updatedDistrict = new District(possibleDistricts);
            updatedDistrict.add(tile, pos);
            // Add updated district to district list
            districts.add(updatedDistrict);
        }
        // return result (future this.districts)
        return districts;
    }

    /**
     * Finds one or more possible districts to which the tile / pos can be added. If no district was found, an empty
     * district list will be returned
     *
     * @param tile      tile to find a district for
     * @param pos       pos to find a district for
     * @param districts list of districts to chose from
     * @return the possible districts from the given district list
     */
    private List<District> findOrCreatePossibleDistricts(SingleTile tile, Pos pos, final List<District> districts) {
        List<District> filteredDistrictList = new LinkedList<>();
        for (District currDistrict : districts) {
            if (currDistrict.typeAndPosMatchCurrDistrict(tile, pos)) {
                filteredDistrictList.add(currDistrict);
            }
        }
        return filteredDistrictList;
    }

    /**
     * Displays the given domino on the players board
     *
     * @param playerSelectedDomino domino to display
     */
    public void showOnBoard(Domino playerSelectedDomino) {
        assert null != playerSelectedDomino;
        // update board
        this.board.lay(playerSelectedDomino);
        // update districts
        this.districts = updatedDistricts(this.districts, playerSelectedDomino);

//        this.districts = addToAppropriateDistrict(playerSelectedDomino.getFstVal(),
//                playerSelectedDomino.getFstPos(), this.districts);
//        this.districts = addToAppropriateDistrict(playerSelectedDomino.getSndVal(),
//                playerSelectedDomino.getSndPos(), this.districts);
        // update gui
        this.gui.showOnGrid(this.idxInPlayerArray, playerSelectedDomino);
    }

    /**
     * Generates a deep copy of the given districts and adds the domino to it at the appropriate slot
     *
     * @param districts districts to add the domino to
     * @param domino    domino to add to the districts
     * @return an updated list of districts
     */
    protected List<District> updatedDistricts(final List<District> districts, Domino domino) {
        // deep copy of whole district list
        List<District> output = new LinkedList<>();
        for (District currDistrict : districts) {
            output.add(currDistrict);
        }
        // adding domino to slots
        output = addToAppropriateDistrict(domino.getFstVal(), domino.getFstPos(), output);
        output = addToAppropriateDistrict(domino.getSndVal(), domino.getSndPos(), output);
        return output;
    }


    @Override
    public String toString() {
        return this.board.toString();
    }


}
