package logic.playerState;

import javafx.scene.control.TreeItem;
import logic.token.Pos;
import logic.token.SingleTile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * class representing the players points. A district holds a number of cells
 * containing SingleTiles of the same District type. This class also provides a
 * method to generate the corresponding points.
 *
 * @author silas
 */
public class District {

    /**
     * Tile positions of the district members
     */
    private List<Pos> tilePositions;

    /**
     * All SingleTiles of this district
     */
    private List<SingleTile> singleTiles;

    /**
     * District to generate an output for the shifting operation. Needed to initialize an empty district to be able
     * to fill with copies.
     */
    public District() {
        this.tilePositions = new ArrayList<>();
        this.singleTiles = new ArrayList<>();
    }

    /**
     * constructor for this class
     *
     * @param fstDistrictMember first tile of this district
     * @param pos               first position of this district
     * @pre null != fstDistrictMember && null != pos
     */
    public District(SingleTile fstDistrictMember, Pos pos) {
        assert null != fstDistrictMember && null != pos;
        this.tilePositions = new LinkedList<>();
        this.singleTiles = new LinkedList<>();
        add(fstDistrictMember, pos);
    }

    /**
     * Constructor taking in a list of districts and forms a new district containing all given districts
     *
     * @param districts list of districts that will be merged
     */
    public District(List<District> districts) {
        assert null != districts;
        this.singleTiles = new LinkedList<>();
        this.tilePositions = new LinkedList<>();
        for (District currDistrict : districts) {
            assert null != currDistrict;
            this.singleTiles.addAll(currDistrict.singleTiles);
            this.tilePositions.addAll(currDistrict.tilePositions);
        }
    }

    /**
     * Cosntructor used for testing
     *
     * @param singleTiles list of singleTiles which form the district
     * @param pos         List of positions from the district
     */
    public District(List<SingleTile> singleTiles, List<Pos> pos) {
        assert null != singleTiles && null != pos;
        this.singleTiles = singleTiles;
        this.tilePositions = pos;
    }

    /**
     * Constructor used for testing
     *
     * @param testDistricts districts that will be merged
     */
    public District(District[] testDistricts) {
        this(Arrays.asList(testDistricts));
    }

    /**
     * Getter for the list of positions
     *
     * @return list of positions saved in the district
     */
    public List<Pos> getTilePositions() {
        return this.tilePositions;
    }

    /**
     * Getter for the list of single tiles
     *
     * @return list of singleTiles saved in the district
     */
    public List<SingleTile> getSingleTiles() {
        return this.singleTiles;
    }

    /**
     * Adds another single tile at the given position to the district
     *
     * @param newTile new tile added to the district
     * @param newPos  new position added to the district, local pos list cannot already contain given pos
     */
    public void add(SingleTile newTile, Pos newPos) {
        assert null != newTile && null != newPos && !this.tilePositions.contains(newPos);
        this.singleTiles.add(newTile);
        this.tilePositions.add(newPos);
    }

    /**
     * Generates the value of points this district is worth
     *
     * @return points of the district (member count x number of token)
     */
    public int genPoints() {
        int tokenCnt = 0;
        for (SingleTile currTile : this.singleTiles) {
            tokenCnt += currTile.getTokenCnt();
        }
        return tokenCnt * this.singleTiles.size();
    }

    /**
     * Checks if a given tile with a position is next to one of the district
     * members
     *
     * @param tile tile that will be checked
     * @param pos  pos that will be checked
     * @return true if one district member is next to the given pos and the
     * district type matches, position cannot be already contained in the district (AssertionError)
     */
    public boolean typeAndPosMatchCurrDistrict(SingleTile tile, Pos pos) {
        assert null != tile && null != pos;
        return matchingDistrictTypes(tile) && elemPosIsNextToExistingElem(pos);
    }

    /**
     * Checks if a given Tiles district type matches the overall district type of the current district
     *
     * @param tile tile to check the district type with
     * @return true if the given tile type matches with the district type
     */
    private boolean matchingDistrictTypes(SingleTile tile) {
        return tile.getDistrictType() == this.singleTiles.get(0).getDistrictType();
    }

    /**
     * Iterates through the district positions and check if any element has the appropriate group of neighbors
     *
     * @param pos Position to examine
     * @return true if pos is next to existing district member pos
     */
    private boolean elemPosIsNextToExistingElem(Pos pos) {
        boolean isNextToDistrictMember;
        int tileCnt = this.tilePositions.size();
        int i = 0;
        do {
            isNextToDistrictMember = this.tilePositions.get(i).getNeighbours().contains(pos);
            i++;
        } while (!isNextToDistrictMember && i < tileCnt);
        return isNextToDistrictMember;
    }

    /**
     * Mainly used for testing, checks if a given object equals this district
     *
     * @param obj other object to examine
     * @return true if both districts hold the same information
     */
    @Override
    public boolean equals(Object obj) {
        assert null != obj;
        if ((null == obj) || !(obj instanceof District)) {
            return false;
        }
        District currDistrict = (District) obj;
        return null != currDistrict.getSingleTiles() && this.singleTiles.equals(currDistrict.getSingleTiles())
                && null != currDistrict.getTilePositions()
                && this.tilePositions.equals(currDistrict.getTilePositions());

    }

    /**
     * Makes a deep copyWithoutSelection of the current District
     *
     * @return a deep copyWithoutSelection of the current District
     */
    public District copy() {
        List<SingleTile> copyTiles = new LinkedList<>();
        List<Pos> copyPos = new LinkedList<>();
        for (int i = 0; i < this.singleTiles.size(); i++) {
            copyTiles.add(this.singleTiles.get(i));
            copyPos.add(this.tilePositions.get(i));
        }
        return new District(copyTiles, copyPos);
    }

    /**
     * Generates a treeItem which is neeeded when the result of the game should be displayed
     *
     * @return treeItem containing all the district type and points this district is worth
     */
    public TreeItem<String> toTreeItem() {
        return new TreeItem<>(genPoints() + " Punkte" + "\t"
                + this.singleTiles.get(0).getDistrictType());
    }

    /**
     * Shifts all district positions to a given direction. Generates a new instance to make it possible for future
     * bot types to use this method to evaluate a domino from a bank without shifting their actual districts if the
     * given domino is not suitable for their case.
     *
     * @param dir direction to which the district positions should be shifted
     * @return new district reference with updated / shifted positions
     */
    public District shiftDirection(Board.Direction dir) {
        District shiftedDistrict = new District();
        // copy singletiles
        for (SingleTile currTile : this.singleTiles) {
            shiftedDistrict.singleTiles.add(currTile);
        }

        // copy and modify positions
        switch (dir) {
            case UP_MOVE:
                for (Pos currPos : this.tilePositions) {
                    shiftedDistrict.tilePositions.add(new Pos(currPos.x(), currPos.y() - 1));
                }
                break;
            case LEFT_MOVE:
                for (Pos currPos : this.tilePositions) {
                    shiftedDistrict.tilePositions.add(new Pos(currPos.x() - 1, currPos.y()));
                }
                break;
            case DOWN_MOVE:
                for (Pos currPos : this.tilePositions) {
                    shiftedDistrict.tilePositions.add(new Pos(currPos.x(), currPos.y() + 1));
                }
                break;
            case RIGHT_MOVE:
                for (Pos currPos : this.tilePositions) {
                    shiftedDistrict.tilePositions.add(new Pos(currPos.x() + 1, currPos.y()));
                }
                break;
            default:
                new AssertionError();
        }

        return shiftedDistrict;
    }
}
