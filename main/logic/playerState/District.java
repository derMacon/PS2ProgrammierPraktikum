package logic.playerState;

import java.util.*;

import logic.token.Pos;
import logic.token.SingleTile;

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
    List<Pos> tilePositions;

    /**
     * All SingleTiles of this district
     */
    List<SingleTile> singleTiles;

    /**
     * constructor for this class
     *
     * @param fstDistrictMember
     * @param pos
     */
    public District(SingleTile fstDistrictMember, Pos pos) {
        assert null != fstDistrictMember && null != pos;
        this.tilePositions = new LinkedList<>();
        this.singleTiles = new LinkedList<>();
        add(fstDistrictMember, pos);
    }

    /**
     * Constructor taking in a list of districts and forms a new district containing all given districts
     * @param districts list of districts that will be merged
     */
    public District(List<District> districts) {
        assert null != districts;
        this.singleTiles = new LinkedList<>();
        this.tilePositions = new LinkedList<>();
        for(District currDistrict : districts) {
            this.singleTiles.addAll(currDistrict.singleTiles);
            this.tilePositions.addAll(currDistrict.tilePositions);
        }
    }

    /**
     * Used for testing
     *
     * @param singleTiles
     * @param pos
     */
    public District(List<SingleTile> singleTiles, List<Pos> pos) {
        assert null != singleTiles && null != pos;
        this.singleTiles = singleTiles;
        this.tilePositions = pos;
    }

    /**
     * Constructor used for testing
     * @param testDistricts districts that will be merged
     */
    public District(District[] testDistricts) {
        this(Arrays.asList(testDistricts));
    }

    /**
     * Getter for the list of positions
     *
     * @return
     */
    public List<Pos> getTilePositions() {
        return this.tilePositions;
    }

    /**
     * Getter for the list of single tiles
     *
     * @return
     */
    public List<SingleTile> getSingleTiles() {
        return this.singleTiles;
    }

    /**
     * Adds another single tile at the given position to the district
     *
     * @param newTile new tile added to the district
     * @param newPos new position added to the district, local pos list cannot already contain given pos
     */
    public void add(SingleTile newTile, Pos newPos) {
        assert null != newTile && null != newPos && !this.tilePositions.contains(newPos);
        this.singleTiles.add(newTile);
        this.tilePositions.add(newPos);
    }



    /**
     * Merges two districts if a tile is
     *
     * @param other district that will be merged with current one
     * @return reference to the object (so that the newly merged district can
     * call the add method for the remaining tile that put those two together
     * (in the same line -> d1.merge(d2).add(tileX)))
     */
    public District merge(District other) {
        assert null != other && Collections.disjoint(this.tilePositions, other.tilePositions);

        // TODO delete next line before final commit
        boolean temp = Collections.disjoint(this.singleTiles, other.singleTiles);

        this.singleTiles.addAll(other.singleTiles);
        this.tilePositions.addAll(other.tilePositions);
        other = null; // clear other district -> never used again (just to make sure the reference is clear)
        return this;
    }

    /**
     * Generates the value of points this district is worth
     *
     * @return points of the district (member count x number of token)
     */
    public int genPoints() {
        int tokenCnt = 0;
        for(SingleTile currTile : this.singleTiles) {
            tokenCnt += currTile.getTokenCnt();
        }
        return tokenCnt * this.singleTiles.size();
    }

    /**
     * Checks if a given tile with a position is next to one of the district
     * members
     *
     * @param tile tile that will be checked
     * @param pos pos that will be checked
     * @return true if one district member is next to the given pos and the
     * district type matches, position cannot be already contained in the district (AssertionError)
     */
    public boolean typeAndPosMatchCurrDistrict(SingleTile tile, Pos pos) {
        assert null != tile && null != pos;

        // TODO delete next lines before final commit
        boolean typeMatching = matchingDistrictTypes(tile);
        boolean posMatching = elemPosIsNextToExistingElem(pos);

        return matchingDistrictTypes(tile) && elemPosIsNextToExistingElem(pos);
    }

    /**
     * Checks if a given Tiles district type matches the overall district type of the current district
     * @param tile
     * @return
     */
    private boolean matchingDistrictTypes(SingleTile tile) {
        return tile.getDistrictType() == this.singleTiles.get(0).getDistrictType();
    }

    /**
     * Iterates through the district positions and check if any element has the appropriate group of neighbors
     * @param pos Position to examine
     * @return true if pos is next to existing district member pos
     */
    private boolean elemPosIsNextToExistingElem(Pos pos) {
        boolean isNextToDistrictMember;
        int i = 0;
        do {
            isNextToDistrictMember = this.tilePositions.get(i).getNeighbours().contains(pos);
            i++;
        } while(!isNextToDistrictMember && i < this.tilePositions.size());
        return isNextToDistrictMember;
    }

    /**
     * TODO Mainly used for testing, maybe consider to delete this method
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

        // TODO delete next two lines- only for testing
        boolean sameTiles = null != currDistrict.getSingleTiles() && this.singleTiles.equals(currDistrict.getSingleTiles());
        boolean samePos = null != currDistrict.getTilePositions() && this.tilePositions.equals(currDistrict.getTilePositions());

        return null != currDistrict.getSingleTiles() && this.singleTiles.equals(currDistrict.getSingleTiles())
            && null != currDistrict.getTilePositions() && this.tilePositions.equals(currDistrict.getTilePositions());

    }

    /**
     * Makes a deep copy of the current District
     * @return a deep copy of the current District
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


}
