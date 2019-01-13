package logic.token;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A Domino has a tile, a rotation and a position. Rotation 0 shows a horizontal orientated
 * domino with the first value on the left and the second on the right.
 * <p>
 * Rotation:
 * the first value of the domino is the one under the cursor.
 * rotation 0 - horizontally - not rotated         - snd is right of fst
 * 1 - vertically   - rotated  90 degrees - snd is bottom of fst
 * 2 - horizontally - rotated 180 degrees - snd is left of fst
 * 3 - vertically   - rotated 270 degrees - snd is top of fst
 *
 * @author GeritKaleck
 */
public class Domino implements Comparable {

    /**
     * Delimiter to define starting point of the the string representation from the domino
     */
    public static final String STR_OPENING_TAG = "[";

    /**
     * Delimiter to define end point of the the string representation from the domino
     */
    public static final String STR_CLOSING_TAG = "]";

    /**
     * Separator to isolate the first and second tile of the domino.
     */
    public static final String STR_SEPARATOR = ",";

    /**
     * Delimiter zu display a vertical allignment in a common String representation
     */
    public static final String STR_VERTI = "vertical";

    /**
     * Delimiter zu display a horizontal allignment in a common String representation
     */
    public static final String STR_HORI = "horizontal";

    /**
     * Number of possiblities a domino can be rotated
     */
    public static final int ROTATION_CNT = 4;

    /**
     * Index of the first tile
     */
    public static final int FST_TILE_IDX = 0;

    /**
     * Index of the second tile
     */
    public static final int SND_TILE_IDX = 1;

    /**
     * Default Pos for a domino
     */
//    public static final Pos DEFAULT_POS = new Pos(0, 0);
    public static final Pos DEFAULT_POS = new Pos(-1, -1);

    /**
     * Default rotation for a domino
     */
    public static final int DEFAULT_ROT = 0;

    /**
     * the tile
     */
    private final Tiles tiles;
    /**
     * the rotation of the tile
     * rotation 0 - horizontally - not rotated - snd is right of fst
     * 1 - vertically   - rotated  90 degrees - snd is bottom of fst
     * 2 - horizontally - rotated 180 degrees - snd is left of fst
     * 3 - vertically   - rotated 270 degrees - snd is top of fst
     */
    private int rotation;

    /**
     * top left position of the domino
     */
    private Pos posFst;


    /**
     * creates a tiles with the given tile, position and rotation
     *
     * @param tiles    tiles of the domino
     * @param posFst   first position of the domino
     * @param rotation rotation of the domino
     * @pre null != tiles
     * @pre isValidRot(rotation)
     */
    public Domino(Tiles tiles, Pos posFst, int rotation) {
        // pos may be null, will be later on disposed if pos is not filled
        assert null != tiles && isValidRot(rotation);
        this.rotation = rotation;
        this.tiles = tiles;
        this.posFst = posFst;
    }

    /**
     * creates a domino with the given tile, position and rotation 0
     *
     * @param tiles to set
     * @param pos   position of the first domino tile (top left)
     */
    public Domino(Tiles tiles, Pos pos) {
        this(tiles, pos, DEFAULT_ROT);
    }

    /**
     * creates a domino with a given tile and rotation
     *
     * @param tiles tiles of the domino
     * @param rot   rotation of the domino
     */
    public Domino(Tiles tiles, int rot) {
        this(tiles, DEFAULT_POS, rot);
    }

    /**
     * creates a domino with a given tile
     *
     * @param tiles to set
     */
    public Domino(Tiles tiles) {
        this(tiles, DEFAULT_POS, DEFAULT_ROT);
//        this(tiles, null, DEFAULT_ROT);
    }

    /**
     * gets the tile
     *
     * @return the tile
     */
    public Tiles getTile() {
        return tiles;
    }

    /**
     * Getter for the rotation
     *
     * @return the rotation
     */
    public int getRot() {
        return rotation;
    }

    /**
     * Setter for the position
     *
     * @param posFst new position
     * @return reference to this domino
     */
    public Domino setPos(Pos posFst) {
        this.posFst = posFst;
        return this;
    }

    /**
     * gets all values of Tiles in a list. All existing dominos in list have
     * to be removed so each domino is single in list.
     *
     * @param list list to be cleared and added to
     * @return same list with all dominos once each
     */
    public static List<Domino> fill(List<Domino> list) {
        if (null == list) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }
        for (Tiles tile : Tiles.values()) {
            list.add(new Domino(tile, DEFAULT_POS));
//            list.add(new Domino(tile, null));
        }
        return list;
    }

    /**
     * Generates a String representing the rotation of a domino
     *
     * @return STR_HORI when domino is not rotated or is rotated twice, STR_VERTI if the domino is
     * rotated once or three times.
     */
    public String getStrAllignment() {
        return this.rotation % 2 == 0 ? STR_HORI : STR_VERTI;
    }

    /**
     * gets the value at the top-left side: fst for rotation 0 and 1 else snd
     *
     * @return the value at the top-left side
     */
    public SingleTile getFstVal() {
        return rotation < 2 ? tiles.getFst() : tiles.getSnd();
    }

    /**
     * gets the value at the right-bottom side
     *
     * @return the value at the right-bottom side
     */
    public SingleTile getSndVal() {
        return rotation < 2 ? tiles.getSnd() : tiles.getFst();
    }

    /**
     * Getter for the first position of the domino (top / left tile)
     *
     * @return the position of the first domino tile
     */
    public Pos getFstPos() {
        return this.posFst;
    }

    /**
     * Getter for the second position of the domino (bottom / right tile)
     *
     * @return the position of the second domino
     */
    public Pos getSndPos() {
        if (null == this.posFst) {
            return null;
        }

        int x = this.posFst.x();
        int y = this.posFst.y();
        switch (rotation) {
            case 0:
            case 2:
                x = this.posFst.x() + 1;
                y = this.posFst.y();
                break;
            case 1:
            case 3:
                x = this.posFst.x();
                y = this.posFst.y() + 1;
                break;
            default:
                assert false : "rotation has to be 0..3 but was " + rotation;
        }
        return new Pos(x, y);
    }

    /**
     * Generates the district type for the first or second tile of the domino
     *
     * @param idx index of the tile
     * @return the district type of the tile at the given index, null if index is invalid
     */
    public DistrictType genTileDistrictType(int idx) {
        DistrictType output = null;
        if (FST_TILE_IDX == idx || SND_TILE_IDX == idx) {
            output = FST_TILE_IDX == idx ? this.tiles.getFst().getDistrictType()
                    : this.tiles.getSnd().getDistrictType();
        }
        return output;
    }

    /**
     * increments the rotation by one. The roation is 0..3.
     * updated the second position.
     *
     * @return reference to the domino
     */
    public Domino incRot() {
        this.rotation++;
        this.rotation %= ROTATION_CNT;
        return this;
    }

    /**
     * dominos are equal if the have the same tile (rotation may differ), exact reference as well
     * as the token value does not matter.
     * <p>
     * Meaning:
     * - domino a with the reference -> #123; value -> HOHO_VAL13; rotation -> 1
     * - domino a with the reference -> #124; value -> HOHO_VAL14; rotation -> 0
     * Both dominoes are equal
     *
     * @param obj object to evaluate
     * @return true if the given arg is a domino with the same tile comibination (rotation may
     * differ)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Domino other = (Domino) obj;
        return (this.tiles.getFst() == other.tiles.getFst()
                || this.tiles.getFst() == other.tiles.getSnd())
                && (this.tiles.getSnd() == other.tiles.getFst()
                || this.tiles.getSnd() == other.tiles.getSnd());
    }

    @Override
    public String toString() {
        return STR_OPENING_TAG + this.getFstVal() + STR_SEPARATOR + this.getSndVal()
                + STR_CLOSING_TAG;
    }

    /**
     * String representation used in the fileIO.
     * Depending on the rotation the upper / left tile will be displayed as the first singletile
     * before the separator, and the right / lower tile as the second.
     *
     * @return String representation of the domino used in the fileIO
     */
    public String toFile() {
        return tiles.toString();
    }

    /**
     * Evaluates if the given int value represents a valid rotation count
     *
     * @param rot rotation to be evaluated
     * @return true if given value represents a valid rotation
     */
    private boolean isValidRot(int rot) {
        return 0 <= rot && ROTATION_CNT > rot;
    }

    /**
     * Setter for the rotation
     *
     * @param rotation rotation to be set for the current domino
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Provides a deep copyWithoutSelection of this domino
     *
     * @return a deep copyWithoutSelection of this domino
     */
    public Domino copy() {
        return new Domino(this.tiles, new Pos(this.posFst.x(), this.posFst.y()), this.rotation);
    }


    @Override
    public int compareTo(Object o) {
        assert null != o && (o instanceof Domino);
        Domino other = (Domino) o;
        return this.tiles.getValue() - other.tiles.getValue();
    }

}
