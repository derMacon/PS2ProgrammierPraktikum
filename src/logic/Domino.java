package logic;

import java.util.List;

/**
 * A Domino has a tile and a rotation. Rotation 0 shows a horizontal orientated
 * domino with the first value on the left and the second on the right.
 *
 * Rotation:
 *   the first value of the domino is the one under the cursor.
 *   rotation 0 - horizontally - not rotated         - snd is right of fst
 *            1 - vertically   - rotated  90 degrees - snd is bottom of fst
 *            2 - horizontally - rotated 180 degrees - snd is left of fst
 *            3 - vertically   - rotated 270 degrees - snd is top of fst
 * @author GeritKaleck
 */
public class Domino {

    /** the tile */
    private final Tiles tile;
    /** the rotation of the tile
     *   rotation 0 - horizontally - not rotated         - snd is right of fst
     *            1 - vertically   - rotated  90 degrees - snd is bottom of fst
     *            2 - horizontally - rotated 180 degrees - snd is left of fst
     *            3 - vertically   - rotated 270 degrees - snd is top of fst
     */
    private int rotation;

    /**
     * creates a domino with the given tile and rotation 0
     * @param tile to set
     */
    public Domino(Tiles tile) {
        this.tile = tile;
        this.rotation = 0;
    }

    /**
     * creates a domino with the given tile and rotation
     * @param domino
     * @param rotation
     */
    public Domino(Tiles domino, int rotation) {
        this(domino);
        this.rotation = rotation;
    }

    /**
     * gets the tile
     * @return the tile
     */
    public Tiles getTile() {
        return tile;
    }

    /**
     * gets the roation
     * @return the rotation
     */
    public int getRot() {
        return rotation;
    }

    /**
     * increments the rotation by one. The roation is 0..3.
     */
    public void incRot() {
        rotation++;
        rotation %= 4;
    }

    /**
     * gets the value at the top-left side: fst for rotation 0 and 1 else snd
     * @return the value at the top-left side
     */
    int getFst() {
        return rotation < 2 ? tile.getFst() : tile.getSnd();
    }

    /**
     * gets the value at the right-bottom side
     * @return the value at the right-bottom side
     */
    int getSnd() {
        return rotation < 2 ? tile.getSnd() : tile.getFst();
    }

    /**
     * gets postion of the second half of the domino when the first half is
     * on the given position.
     * @param fst posititon of the first half
     * @return position of the second half
     */
    public Pos getSnd(Pos fst) {
        int x = fst.x();
        int y = fst.y();
        switch (rotation) {
            case 0:
            case 2: x = fst.x() + 1;
                y = fst.y();
                break;
            case 1:
            case 3: x = fst.x();
                y = fst.y() + 1;
                break;
            default:
                assert false : "rotation has to be 0..3 but was " + rotation;
        }
        return new Pos(x, y);
    }


    /**
     * gets all values of Tiles in a list. All existing dominos in list have
     * to be removed so each domino is single in list.
     * @param list list to be cleared and added to
     * @return same list with all dominos once each
     */
    public static List<Domino> fill(List<Domino> list) {
        list.clear();
        for (Tiles tile : Tiles.values()) {
            list.add(new Domino(tile));
        }
        return list;
    }


    /**
     * dominos are equal if the have the same tile (rotation may differ)
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
        return this.tile == other.tile;
    }


    @Override
    public String toString() {
        return tile.toString() + rotation;
    }


}
