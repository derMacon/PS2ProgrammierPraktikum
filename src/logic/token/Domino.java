package logic.token;

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
public class Domino {

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
    public static final Pos DEFAULT_POS = new Pos(0, 0);

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
     * rotation 0 - horizontally - not rotated         - snd is right of fst
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
     * bottom right position of the domino
     */
    private Pos posSnd;

    /**
     * creates a tiles with the given tile, position and rotation
     *
     * @param tiles tiles of the domino
     * @param posFst first position of the domino
     * @param rotation rotation of the domino
     */
    public Domino(Tiles tiles, Pos posFst, int rotation) {
        assert null != tiles && isValidRot(rotation);
        this.rotation = rotation;
        this.tiles = tiles;
        this.posFst = posFst;
        updatePosSnd();
    }

    /**
     * creates a domino with the given tile, position and rotation 0
     *
     * @param tiles to set
     * @param pos  position of the first domino tile (top left)
     */
    public Domino(Tiles tiles, Pos pos) {
        this(tiles, pos, DEFAULT_ROT);
    }

    /**
     * creates a domino with a given tile and rotation
     * @param tiles
     * @param rot
     */
    public Domino(Tiles tiles, int rot) {
        this(tiles, DEFAULT_POS, rot);
    }

    /**
     * creates a domino with a given tile
      * @param tiles to set
     */
    public Domino(Tiles tiles) {
        this(tiles, DEFAULT_POS, DEFAULT_ROT);
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
     * gets the rotation
     *
     * @return the rotation
     */
    public int getRot() {
        return rotation;
    }

    public void setPos(Pos posFst) {
        this.posFst = posFst;
        updatePosSnd();
    }

    /**
     * Generates the district type for the first or second tile of the domino
      * @param idx index of the tile
     * @return the district type of the tile at the given index, null if index is invalid
     */
    public DistrictType genTileDistrictType(int idx) {
        DistrictType output = null;
        if (FST_TILE_IDX == idx || SND_TILE_IDX == idx) {
            output = FST_TILE_IDX == idx ? this.tiles.getFst().getDistrictType() : this.tiles.getSnd().getDistrictType();
        }
        return output;
    }

    /**
     * increments the rotation by one. The roation is 0..3.
     * updated the second position.
     */
    public void incRot() {
        this.rotation++;
        this.rotation %= 4;
        updatePosSnd();
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
     * @return
     */
    public Pos getFstPos() {
        return this.posFst;
    }

    /**
     * Getter for the second position of the domino (bottom / right tile)
     * @return
     */
    public Pos getSndPos() {
        return this.posSnd;
    }

    /**
     * updates postion of the second half of the domino when the first half is
     * on the this.posFst position. If this.posFst equals null nothing will happen
     *
     * @return position of the second half
     */
    private void updatePosSnd() {
        if(null != this.posFst) {
            int x = posFst.x();
            int y = posFst.y();
            switch (rotation) {
                case 0:
                case 2:
                    x = posFst.x() + 1;
                    y = posFst.y();
                    break;
                case 1:
                case 3:
                    x = posFst.x();
                    y = posFst.y() + 1;
                    break;
                default:
                    assert false : "rotation has to be 0..3 but was " + rotation;
            }
            this.posSnd = new Pos(x, y);
        }
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
            list = new LinkedList<Domino>();
        } else {
            list.clear();
        }
        for (Tiles tile : Tiles.values()) {
            list.add(new Domino(tile, null));
        }
        return list;
    }


    /**
     * dominos are equal if the have the same tile (rotation may differ)
     *
     * @param obj
     * @return
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
        return this.tiles == other.tiles;
    }


    @Override
    public String toString() {
        return tiles.toString() + rotation;
    }

    private boolean isValidRot(int rot) {
        return 0 <= rot && ROTATION_CNT > rot;
    }


}
