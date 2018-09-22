package logic.playerState;

import java.util.LinkedList;
import java.util.List;
import logic.token.Domino;
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
        this.tilePositions = new LinkedList<>();
        this.singleTiles = new LinkedList<>();
        add(fstDistrictMember, pos);
    }

    /**
     * Used for testing
     *
     * @param singleTiles
     * @param pos
     */
    public District(List<SingleTile> singleTiles, List<Pos> pos) {
        this.singleTiles = singleTiles;
        this.tilePositions = pos;
    }

    /**
     * Getter for the list of positions
     *
     * @return
     */
    public List<Pos> getTilePositions() {
        return tilePositions;
    }

    /**
     * Getter for the list of single tiles
     *
     * @return
     */
    public List<SingleTile> getSingleTiles() {
        return singleTiles;
    }

    /**
     * Adds another single tile at the given position to the district
     *
     * @param newTile new tile added to the district
     * @param newPos new position added to the district
     */
    public void add(SingleTile newTile, Pos newPos) {
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
        assert null != other;
        this.singleTiles.addAll(other.singleTiles);
        this.tilePositions.addAll(other.tilePositions);
        return this;
    }

    /**
     * Generates the value of points this district is worth
     *
     * @return points of the district (member count x number of token)
     */
    public int genPoints() {
        // TODO insert code
        return 0;
    }

    /**
     * Checks if a given tile with a position is next to one of the district
     * members
     *
     * @param tile tile that will be checked
     * @param pos pos that will be checked
     * @return true if one district member is next to the given pos and the
     * district type matches
     */
    private boolean isNextToDistrictMember(SingleTile tile, Pos pos) {
        if (tile.getDistrictType() == this.singleTiles.get(0).getDistrictType()) {
            // TODO insert code
        }
        return false;
    }

}
