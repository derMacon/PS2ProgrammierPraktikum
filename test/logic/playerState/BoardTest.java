package logic.playerState;

import logic.bankSelection.Choose;
import logic.playerState.Board;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;

public class BoardTest {

    // --- 1. Constructor - used for game ---
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

    /**
     * Baord:
     * CC
     */
    @Test
    public void testConstructor_WithCCInMiddle_OneCell() {
        Board board = new Board(1, 1);
        assertArrayEquals(new SingleTile[][]{{SingleTile.CC}}, board.getCells());
    }

    /**
     * Baord:
     * CC EC
     * EC EC
     */
    @Test
    public void testConstructor_WithCCInMiddle_SymmetricalDimensionsEven_SmallesPossibleValue() {
        Board board = new Board(2, 2);
        SingleTile[][] expectedOutput = new SingleTile[][]{{SingleTile.CC, SingleTile.EC}, {SingleTile.EC, SingleTile.EC}};
        SingleTile[][] actualOutput = board.getCells();
        assertArrayEquals(expectedOutput, actualOutput);
    }

    /**
     * Baord:
     * EC EC EC EC EC
     * EC EC EC EC EC
     * EC EC CC EC EC
     * EC EC EC EC EC
     * EC EC EC EC EC
     */
    @Test
    public void testConstructor_WithCCInMiddle_SymmetricalDimensionsEven_DefaultBaordSize() {
        Board board = new Board(5, 5);
        SingleTile[][] expectedOutput = new SingleTile[][]{
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.CC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC}};
        SingleTile[][] actualOutput = board.getCells();
        assertArrayEquals(expectedOutput, actualOutput);
    }

    /**
     * Baord:
     * EC EC EC
     * EC CC EC
     * EC EC EC
     */
    @Test
    public void testConstructor_WithCCInMiddle_SymmetricalDimensionsUneven() {
        Board board = new Board(3, 3);
        SingleTile[][] expectedOutput = new SingleTile[][]{
                {SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.CC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.EC}};
        SingleTile[][] actualOutput = board.getCells();
        assertArrayEquals(expectedOutput, actualOutput);
    }


    // --- 1. Constructor - only used for testing ---
//    @Test(expected = AssertionError.class)
//    public void testConstructor_NullParam() {
//        Board board = new Board(null);
//    }

    @Test(expected = AssertionError.class)
    public void testConstructor_EmptyString() {
        Board board = new Board("");
    }

    @Test
    public void testConstructor_OneCharStringEmpty() {
        Board board = new Board("--");
        assertEquals(1, board.getSizeX());
        assertEquals(1, board.getSizeY());
        SingleTile[][] expectedCells = new SingleTile[][]{{SingleTile.EC}};
        assertArrayEquals(expectedCells, board.getCells());
    }

    @Test
    public void testConstructor_OneCharStringFilled() {
        Board board = new Board("CC");
        assertEquals(1, board.getSizeX());
        assertEquals(1, board.getSizeY());
        SingleTile[][] expectedCells = new SingleTile[][]{{SingleTile.CC}};
        assertArrayEquals(expectedCells, board.getCells());
    }

    @Test
    public void testConstructor_FilledString() {
        Board board = new Board("-- -- -- \n -- -- CC ");
        assertEquals(3, board.getSizeX());
        assertEquals(2, board.getSizeY());
        SingleTile[][] expectedCells = new SingleTile[][]{
                {SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.CC}};
        assertArrayEquals(expectedCells, board.getCells());
    }

    @Test(expected = AssertionError.class)
    public void testConstructor_FilledString_Asymmetric() {
        new Board("-- -- -- \n -- CC");
    }


    //<editor-fold defaultstate="collapsed" desc="2. Modified tests from ueb09 (Contains test for fits)">
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
                "-- -- -- -- -- --\n" +
                        "-- -- S0 P0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0));
        // expected P0_A0_Val14
        assertEquals(14, dom.getTile().getValue());
        assertTrue(board.fits(dom.setPos(new Pos(3, 0))));
        assertTrue(board.fits(dom.setPos(new Pos(4, 1))));
        assertTrue(board.fits(dom.setPos(new Pos(3, 2))));
    }

    @Test
    public void testFits_rotated0_notOnBoard() {
        Board board = new Board(
                "-- -- -- -- --\n" +
                        "-- -- S0 P0 --\n" +
                        "-- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0));
        // expected P0_A0_Val14
        assertEquals(14, dom.getTile().getValue());
        assertFalse(board.fits(dom.setPos(new Pos(4, 1))));
    }

    @Test
    public void testFits_rotated0_notTouching() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- S0 P0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0));
        // expected P0_A0_Val14
        assertEquals(14, dom.getTile().getValue());
        assertFalse(board.fits(dom.setPos(new Pos(0, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 3))));
    }

    @Test
    public void testFits_rotated0_ValuesDontFit() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- S0 H0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0));
        // expected P0_A0_Val14
        assertEquals(14, dom.getTile().getValue());
        assertFalse(board.fits(dom.setPos(new Pos(3, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 1))));
        assertFalse(board.fits(dom.setPos(new Pos(3, 2))));
        dom = new Domino(Tiles.genTile(H0, S0));
        assertFalse(board.fits(dom.setPos(new Pos(2, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(2, 2))));
    }


    /*--- rotated 1 ------------------------------------------*/

    @Test
    public void testFits_rotated1() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- A0 P0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, S0), 1);
        assertTrue(board.fits(dom.setPos(new Pos(4, 1))));

        dom = new Domino(Tiles.genTile(P0, A0), 1);
        // expected P0_A0_Val14, rot == 1
        assertEquals(14, dom.getTile().getValue());
        assertEquals(1, dom.getRot());
        assertTrue(board.fits(dom.setPos(new Pos(1, 0))));
        assertTrue(board.fits(dom.setPos(new Pos(4, 1))));
    }

    @Test
    public void testFits_rotated1_notOnBoard() {
        Board board = new Board(
                "-- -- -- -- --\n" +
                        "-- -- S0 P0 --\n" +
                        "-- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), 1);
        assertFalse(board.fits(dom.setPos(new Pos(3, 2))));
    }

    @Test
    public void testFits_rotated1_notTouching() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- S0 P0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), 1);
        assertFalse(board.fits(dom.setPos(new Pos(0, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(5, 0))));
    }

    @Test
    public void testFits_rotated1_ValuesDontFit() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- S0 H1 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), 1);
        assertFalse(board.fits(dom.setPos(new Pos(1, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(1, 1))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 1))));

        dom = new Domino(Tiles.genTile(H0, S0), 1);
        assertFalse(board.fits(dom.setPos(new Pos(1, 1))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
    }

    /*--- rotated 2 ------------------------------------------*/
    @Test
    public void testFits_rotated2() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- P0 S0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), new Pos(1, 0), 2);
        // expected P0_A0_Val14
        assertEquals(14, dom.getTile().getValue());
        assertEquals(A0, dom.getFstVal());
        assertEquals(P0, dom.getSndVal());
        assertEquals(new Pos(1, 0), dom.getFstPos());
        assertEquals(new Pos(2, 0), dom.getSndPos());
        assertTrue(board.fits(dom));
        assertTrue(board.fits(dom.setPos(new Pos(0, 1))));
        assertTrue(board.fits(dom.setPos(new Pos(1, 2))));

        dom = new Domino(Tiles.genTile(P0, S0), 2);
        assertTrue(board.fits(dom.setPos(new Pos(3, 0))));
        assertTrue(board.fits(dom.setPos(new Pos(4, 1))));
        assertTrue(board.fits(dom.setPos(new Pos(3, 2))));
    }

    @Test
    public void testFits_rotated2_notOnBoard() {
        Board board = new Board(
                "-- -- -- --\n" +
                        "-- S0 A0 --\n" +
                        "-- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), 2);
        assertFalse(board.fits(dom.setPos(new Pos(3, 1))));
    }

    @Test
    public void testFits_rotated2_notTouching() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- S0 P1 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), 2);
        assertFalse(board.fits(dom.setPos(new Pos(0, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(0, 2))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 2))));
    }

    @Test
    public void testFits_rotated2_ValuesDontFit() {
        Board board = new Board(
                "-- -- -- -- -- --\n" +
                        "-- -- A1 S0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, A0), 2);
        assertFalse(board.fits(dom.setPos(new Pos(2, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(4, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(1, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(1, 2))));
    }

    // findPosFor() tests in testclasses for players who implement the BotBehavior interface, but here named
    // updateDominoPos() - tests since the domino holds the information about the position of the first tile and only
    // will be UPDATED.

    //</editor-fold>


    // --- Game specific tests ---
    @Test
    public void testFits_DomInCorner() {
        Board board = new Board(
                "-- -- -- -- --\n" +
                        "-- -- -- P1 --\n" +
                        "-- -- CC A0 --\n" +
                        "-- -- -- -- --\n");
        Domino dom = new Domino(Tiles.genTile(P0, I3), 0);
        assertTrue(board.fits(dom.setPos(new Pos(3, 0))));
    }


    // --- isEfficient ---
    @Test
    public void testIsEfficient_InCorner_NotEfficient() {
        Board board = new Board(
                        "-- -- P1\n" +
                        "-- CC A0\n");
        Domino dom = new Domino(Tiles.genTile(P0, I3), 3);
        Choose choose = new Choose(dom, 6, 0);
        assertTrue(board.fits(dom));
        assertFalse(board.isEfficient(choose));
    }

    @Test
    public void testIsEfficient_InCorner_Efficient() {
        Board board = new Board(
                        "-- -- P1\n" +
                        "-- CC A0\n");
        Domino dom = new Domino(Tiles.genTile(P0, I3), Pos.DOWN_ROT); // rotation has been modified
        Choose choose = new Choose(dom, 6, 0);
        assertTrue(board.fits(dom));
        assertTrue(board.isEfficient(choose));
    }

    @Test
    public void testIsEfficient_InCorner_Efficient_MultipleCell() {
        Board board = new Board(
                        "-- -- P1\n" +
                        "-- CC A0\n" +
                        "-- I1 --");
        Domino dom = new Domino(Tiles.genTile(P0, I3), Pos.DOWN_ROT); // rotation has been modified
        Choose choose = new Choose(dom, 6, 0);
        assertTrue(board.fits(dom));
        assertTrue(board.isEfficient(choose));
    }

    @Test
    public void testIsEfficient_InCorner_NotEfficient_MultipleCell() {
        Board board = new Board(
                        "-- -- P1\n" +
                        "-- CC A0\n" +
                        "-- I1 --");
        Domino dom = new Domino(Tiles.genTile(P0, I3), Pos.UP_ROT); // rotation has been modified
        Choose choose = new Choose(dom, 6, 0);
        assertTrue(board.fits(dom));
        assertFalse(board.isEfficient(choose));
    }

    // --- checkIfNeighborsAreValid ---
    @Test
    public void testcheckIfNeighborsAreValid() {

    }


}
