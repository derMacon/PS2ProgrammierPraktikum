package TestPackages.logic.playerState;

import logic.token.SingleTile.*;
import logic.playerState.Board;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Assert;
import org.junit.Test;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;

public class BoardTest {


    //<editor-fold defaultstate="collapsed" desc="Domino generator">

    /**
     * Genererates a domino from the given occupancy, position and rotation
     *
     * @param fstOrd ordinal value of the first tile
     * @param sndOrd ordinal value of the second tile
     * @param pos    position of the first tile
     * @param rot    rotation of the domino
     * @return a domino from the given occupancy
     */
    private Domino genDominoFromTileOrd(int fstOrd, int sndOrd, Pos pos, int rot) {
        Tiles tiles = Tiles.genTile(fstOrd, sndOrd);
        assert null != tiles;
        return new Domino(tiles, pos, rot);
    }

    /**
     * Genererates a domino from the given occupancy, position
     *
     * @param fstOrd ordinal value of the first tile
     * @param sndOrd ordinal value of the second tile
     * @param pos    position of the first tile
     * @return a domino from the given occupancy
     */
    private Domino genDominoFromTileOrd(int fstOrd, int sndOrd, Pos pos) {
        Tiles tiles = Tiles.genTile(fstOrd, sndOrd);
        assert null != tiles;
        return new Domino(tiles, pos);
    }

    /**
     * Genererates a domino from the given occupancy, position and rotation
     *
     * @param fstOrd ordinal value of the first tile
     * @param sndOrd ordinal value of the second tile
     * @return a domino from the given occupancy
     */
    private Domino genDominoFromTileOrd(int fstOrd, int sndOrd) {
        return genDominoFromTileOrd(fstOrd, sndOrd, new Pos(0, 0), 0);
    }

    /**
     * Genererates a domino from the given occupancy and rotation
     *
     * @param fstOrd ordinal value of the first tile
     * @param sndOrd ordinal value of the second tile
     * @param rot    rotation of the domino
     * @return a domino from the given occupancy
     */
    private Domino genDominoFromTileOrd(int fstOrd, int sndOrd, int rot) {
        return genDominoFromTileOrd(fstOrd, sndOrd, new Pos(0, 0), rot);
    }

    /**
     * Genereates a domino from a given ordinal value of one of the singletiles
     *
     * @param tileOrd ordinal value of one of the singletiles
     * @return a domino from a given ordinal value of one of the singletiles
     */
    private Domino genDominoFromTileOrd(int tileOrd) {
        Domino domino = genDominoFromFstTile(tileOrd);
        if (domino == null) {
            domino = genDominoFromSndTile(tileOrd);
        }
        assert null != domino;
        return domino;
    }

    /**
     * Generates a domino from the ordinal value of the first singletile
     *
     * @param fstOrd ordinal value of the first singletile
     * @return a domino from the ordinal value of the first singletile
     */
    private Domino genDominoFromFstTile(int fstOrd) {
        Tiles tiles;
        int counter = 0;
        do {
            tiles = Tiles.genTile(fstOrd, counter);
            counter++;
        } while (null == tiles && counter < SingleTile.values().length);
        return new Domino(tiles);
    }

    /**
     * Generates a domino from the ordinal value of the second singletile
     *
     * @param sndOrd ordinal value of the second singletile
     * @return a domino from the ordinal value of the second singletile
     */
    private Domino genDominoFromSndTile(int sndOrd) {
        Tiles tiles;
        int counter = 0;
        do {
            tiles = Tiles.genTile(counter, sndOrd);
            counter++;
        } while (null == tiles && counter < SingleTile.values().length);
        return new Domino(tiles);
    }
    //</editor-fold>


    // --- Constructor - used for game ---
    @Test(expected = AssertionError.class)
    public void testConstructor_ValidEmpty() {
        Board board = new Board(0, 0);
    }

    @Test(expected = AssertionError.class)
    public void testConstructor_Invalid1() {
        Board board = new Board(-1, 1);
    }

    @Test(expected = AssertionError.class)
    public void testConstructor_Invalid2() {
        Board board = new Board(1, -1);
    }

    @Test
    public void testConstructor_Valid() {
        Board board = new Board(1, 1);
        assertArrayEquals(new SingleTile[1][1], board.getCells());
    }


    //    // --- Constructor - only used for testing ---
//    @Test(expected = AssertionError.class)
//    public void testConstructor_NullParam() {
//        Board board = new Board(null);
//    }
//
//    @Test(expected = AssertionError.class)
//    public void testConstructor_EmptyString() {
//        Board board = new Board("");
//    }
//
//    @Test
//    public void testConstructor_OneCharStringEmpty() {
//        Board board = new Board("e");
//        assertEquals(1, board.getSizeX());
//        assertEquals(1, board.getSizeY());
//        SingleTile[][] expectedCells = new SingleTile[][]{{null}};
//        assertArrayEquals(expectedCells, board.getCells());
//    }
//
//    @Test
//    public void testConstructor_OneCharStringFilled() {
//        Board board = new Board("0");
//        assertEquals(1, board.getSizeX());
//        assertEquals(1, board.getSizeY());
//        SingleTile[][] expectedCells = new SingleTile[][]{{SingleTile.CITY_HALL}};
//        assertArrayEquals(expectedCells, board.getCells());
//    }
//
//    @Test
//    public void testConstructor_FilledString() {
//        Board board = new Board("e e \n e 0");
//        assertEquals(2, board.getSizeX());
//        assertEquals(2, board.getSizeY());
//        SingleTile[][] expectedCells = new SingleTile[][]{{null, null}, {null, SingleTile.CITY_HALL}};
//        assertArrayEquals(expectedCells, board.getCells());
//    }
//
//
    //<editor-fold defaultstate="collapsed" desc="Modified tests from ueb09">
    // --- fits ---
    @Test
    public void testFits_Touches3Sides() {
        Board board = new Board(
                "-- -- -- -- -- -- --\n" +
                        "-- -- -- -- -- -- --\n" +
                        "-- -- P0 -- -- S0 --\n" +
                        "-- -- P0 -- -- A1 --\n" +
                        "-- -- P0 -- -- A1 --\n" +
                        "-- -- A1 A1 A1 A1 --\n" +
                        "-- -- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0));
        // expected horiz P0_A0_Val14
        assertEquals(14, dom.getTile().getValue());
        assertTrue(board.fits(dom.setPos(new Pos(2, 1))));  // above end (P0)
        assertFalse(board.fits(dom.setPos(new Pos(3, 2)))); // between P0 - S0

        dom.incRot();                                             // vert 13 over 1
        assertFalse(board.fits(dom.setPos(new Pos(3, 2)))); // right of 13 - 4, 3 sides touch
        assertFalse(board.fits(dom.setPos(new Pos(1, 2)))); // left of 13 - 4, each half touches

        dom.incRot();                                             // horiz 1 - 13
        assertFalse(board.fits(dom.setPos(new Pos(2, 1)))); // above end (13 / P0)
        assertTrue(board.fits(dom.setPos(new Pos(1, 1))));  // left of end

        dom.incRot();                                             // vert 1 over 13
        assertTrue(board.fits(dom.setPos(new Pos(1, 1))));  // left of end
        assertTrue(board.fits(dom.setPos(new Pos(2, 0))));  // above of end (13)
        assertTrue(board.fits(dom.setPos(new Pos(3, 1))));  // right of end
        assertFalse(board.fits(dom.setPos(new Pos(1, 2)))); // 2 sides touch
        assertFalse(board.fits(dom.setPos(new Pos(3, 2)))); // 3 sides touch
    }


    /*--- rotated 0 ------------------------------------------*/

    @Test
    public void testFits_rotated0() {
        Board board = new Board(
                "e e e e e e\n" +
                        "e e 4 13 e e\n" +
                        "e e e e e e\n");
        Domino dom = genDominoFromTileOrd(13, 1); // 13 - 1 -> P0_A0_Val14
        assertTrue(board.fits(dom.setPos(new Pos(3, 0))));
        assertTrue(board.fits(dom.setPos(new Pos(4, 1))));
        assertTrue(board.fits(dom.setPos(new Pos(3, 2))));
    }

    @Test
    public void testFits_rotated0_notOnBoard() {
        Board board = new Board(
                "e e e  e e\n" +
                        "e e 4 13 e\n" +
                        "e e e e e\n");
        Domino dom = genDominoFromTileOrd(13, 1); // 13 - 1 -> P0_A0_Val14
        assertFalse(board.fits(dom.setPos(new Pos(4, 1))));
    }

    @Test
    public void testFits_rotated0_notTouching() {
        Board board = new Board(
                "e e e e e e\n" +
                        "e e 4 13 e e\n" +
                        "e e e e e e\n");
        Domino dom = genDominoFromTileOrd(13, 1); // 13 - 1 -> P0_A0_Val14
        assertFalse(board.fits(dom.setPos(new Pos(0, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 3))));
    }

    @Test
    public void testFits_rotated0_ValuesDontFit() {
        Board board = new Board(
                "e e e e e e\n" +
                        "e e 4 13 e e\n" +
                        "e e e e e e\n");
        Domino dom = genDominoFromTileOrd(1, 1); // 13 - 1 -> P0_P0_Val1
        assertFalse(board.fits(dom.setPos(new Pos(3, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 1))));
        assertFalse(board.fits(dom.setPos(new Pos(3, 2))));

        dom = genDominoFromTileOrd(13, 1); // 13 - 1 -> P0_A0_Val14
        assertFalse(board.fits(dom.setPos(new Pos(2, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(2, 2))));
    }


    /*--- rotated 1 ------------------------------------------*/

    @Test
    public void testFits_rotated1() {
        Board board = new Board(
                "e e e e e e\n" +
                        "e e 1 13 e e\n" +
                        "e e e e e e\n");
        Domino dom = genDominoFromTileOrd(13, 1, 1); // 13 - 1 -> P0_A0_Val14
        assertTrue(board.fits(dom.setPos(new Pos(4, 1))));
        assertTrue(board.fits(dom.setPos(new Pos(1, 0))));
    }

    @Test
    public void testFits_rotated1_notOnBoard() {
        Board board = new Board(
                "e e e e e\n" +
                        "e e 1 13 e\n" +
                        "e e e e e\n");
        Domino dom = genDominoFromTileOrd(13, 1); // 13 - 1 -> P0_A0_Val14
        assertFalse(board.fits(dom.setPos(new Pos(3, 2))));
    }

    @Test
    public void testFits_rotated1_notTouching() {
        Board board = new Board(
                "e e e e e e\n" +
                        "e e 1 13 e e\n" +
                        "e e e e e e\n");
        Domino dom = genDominoFromTileOrd(13, 1); // 13 - 1 -> P0_A0_Val14
        assertFalse(board.fits(dom.setPos(new Pos(0, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(5, 0))));
    }

    @Test
    public void testFits_rotated1_ValuesDontFit() {
        Board board = new Board(
                "e e e e e e\n" +
                        "e e 1 13 e e\n" +
                        "e e e e e e\n");
        Domino dom = genDominoFromTileOrd(21, 21, 1); // 13 - 1 -> P0_A0_Val14
        assertFalse(board.fits(dom.setPos(new Pos(1, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(1, 1))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 1))));

        dom = genDominoFromTileOrd(13, 1); // 13 - 1 -> P0_A0_Val14
        assertFalse(board.fits(dom.setPos(new Pos(1, 1))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
    }

    /*--- rotated 2 ------------------------------------------*/
    @Test
    public void testFits_rotated2() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- P0 A0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), new Pos(1, 0));
        // expected P0_A0_Val14
        assertEquals(14, dom.getTile().getValue());
        assertEquals(P0, dom.getFstVal());
        assertEquals(A0, dom.getSndVal());
        assertEquals(new Pos(1, 0), dom.getFstPos());
        assertEquals(new Pos(2, 0), dom.getSndPos());
        assertTrue(board.fits(dom));
        assertTrue(board.fits(dom.setPos(new Pos(0, 1))));
        assertTrue(board.fits(dom.setPos(new Pos(1, 2))));

        dom = genDominoFromTileOrd(13, 21);
        assertTrue(board.fits(dom.setPos(new Pos(3, 0))));
        assertTrue(board.fits(dom.setPos(new Pos(4, 1))));
        assertTrue(board.fits(dom.setPos(new Pos(3, 2))));
    }
//
//    @Test
//    public void testFits_rotated2_notOnBoard() {
//        Board board = new Board(
//                "e e e e\n" +
//                        "e 13 1 e\n" +
//                        "e e e e\n");
//        Domino dom = genDominoFromTileOrd(13, 1, 2); // 13 - 1 -> P0_A0_Val14
//        assertFalse(board.fits(dom.setPos(new Pos(3, 1))));
//    }
//
//    @Test
//    public void testFits_rotated2_notTouching() {
//        Board board = new Board(
//                "e e e e e e\n" +
//                        "e e 4 13 e e\n" +
//                        "e e e e e e\n");
//        Domino dom = genDominoFromTileOrd(13, 1, 2); // 13 - 1 -> P0_A0_Val14
//        assertFalse(board.fits(dom.setPos(new Pos(0, 0))));
//        assertFalse(board.fits(dom.setPos(new Pos(0, 2))));
//        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
//        assertFalse(board.fits(dom.setPos(new Pos(4, 2))));
//    }
//
//    @Test
//    public void testFits_rotated2_ValuesDontFit() {
//        Board board = new Board(
//                "e e e e e e\n" +
//                        "e e 1 3 e e\n" +
//                        "e e e e e e\n");
//        Domino dom = genDominoFromTileOrd(13, 1, 2); // 13 - 1 -> P0_A0_Val14
//        assertFalse(board.fits(dom.setPos(new Pos(2, 0))));
//        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
//        assertFalse(board.fits(dom.setPos(new Pos(1, 0))));
//        assertFalse(board.fits(dom.setPos(new Pos(1, 2))));
//    }


    /*------- findPosfor() ---------------------------------------------------*/

//    @Test
//    public void testFindPosFor_rot0_above() {
//        Board board = new Board(
//                "e e e e e e\n" +
//                        "e e 5 6 e e\n" +
//                        "e e e e e e\n");
//        assertEquals(new Pos(3, 0), board.findPosFor(new Domino(SIX_ONE)));
//    }
//
//    @Test
//    public void testFindPosFor_rot0_below() {
//        Board board = new Board(new FakeGUI(),
//                "ee11ee\n" +
//                        "ee56e1\n" +
//                        "eeeeee\n",
//                new Pos[]{new Pos(2, 1), new Pos(3, 1)});
//        assertEquals(new Pos(3, 2), board.findPosFor(new Domino(SIX_ONE)));
//    }
//
//    @Test
//    public void testFindPosFor_rot0_rightOf() {
//        Board board = new Board(new FakeGUI(),
//                "ee11ee\n" +
//                        "ee56ee\n" +
//                        "ee22ee\n",
//                new Pos[]{new Pos(2, 1), new Pos(3, 1)});
//        assertEquals(new Pos(4, 1), board.findPosFor(new Domino(SIX_ONE)));
//    }
//
//    @Test
//    public void testFindPosFor_rot0_aboveleft() {
//        Board board = new Board(new FakeGUI(),
//                "eeeeee\n" +
//                        "1e56ee\n" +
//                        "e22eee\n",
//                new Pos[]{new Pos(2, 1), new Pos(3, 1)});
//        assertEquals(new Pos(1, 0), board.findPosFor(new Domino(SIX_FIVE)));
//    }
//
//    @Test
//    public void testFindPosFor_rot0_belowleft() {
//        Board board = new Board(new FakeGUI(),
//                "ee11ee\n" +
//                        "1e56e1\n" +
//                        "eeeeee\n",
//                new Pos[]{new Pos(2, 1), new Pos(3, 1)});
//        assertEquals(new Pos(1, 2), board.findPosFor(new Domino(SIX_FIVE)));
//    }
//
//    @Test
//    public void testFindPosFor_rot0_leftOf() {
//        Board board = new Board(new FakeGUI(),
//                "ee11ee\n" +
//                        "ee56ee\n" +
//                        "ee22ee\n",
//                new Pos[]{new Pos(2, 1), new Pos(3, 1)});
//        assertEquals(new Pos(0, 1), board.findPosFor(new Domino(SIX_FIVE)));
//    }
//
//    @Test
//    public void testFindPosFor_rot1_leftOf() {
//        Board board = new Board(new FakeGUI(),
//                "e56ee\n" +
//                        "eeeee\n",
//                new Pos[]{new Pos(1, 0), new Pos(2, 0)});
//        assertEquals(new Pos(0, 0), board.findPosFor(new Domino(SIX_FIVE, 0)));
//    }
//
//    @Test
//    public void testFindPosFor_rot2_leftOf() {
//        Board board = new Board(new FakeGUI(),
//                "ee11ee\n" +
//                        "ee56ee\n" +
//                        "ee11ee\n",
//                new Pos[]{new Pos(2, 1), new Pos(3, 1)});
//        assertEquals(new Pos(0, 1), board.findPosFor(new Domino(FIVE_TWO, 0)));
//    }
//
//    @Test
//    public void testFindPosFor_rot2_bottomleft() {
//        Board board = new Board(new FakeGUI(),
//                "ee11ee\n" +
//                        "1e56ee\n" +
//                        "eeeeee\n",
//                new Pos[]{new Pos(2, 1), new Pos(3, 1)});
//        assertEquals(new Pos(1, 2), board.findPosFor(new Domino(FIVE_TWO, 0)));
//    }


    //</editor-fold>

}
