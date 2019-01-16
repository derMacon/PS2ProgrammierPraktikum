package logic.playerState;

import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.logicTransfer.GUIConnector;
import logic.logicTransfer.Game;
import logic.differentPlayerTypes.DefaultAIPlayer;
import logic.differentPlayerTypes.HumanPlayer;
import logic.token.DistrictType;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Assert;
import org.junit.Test;
import other.FakeGUI;

import java.util.LinkedList;
import java.util.Random;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;

public class BoardTest {

    // --- moveBoard ---
    @Test
    public void testMoveBoard_CheckReferences() {
        Board inputBoard = new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- -- --\n" +
                        "-- -- --\n"
        );
        Board actualOutputBoard = inputBoard.moveBoard(Board.Direction.LEFT_MOVE);
        // new board reference
        assertNotSame(inputBoard, actualOutputBoard);
        // new cell reference
        assertNotSame(inputBoard.getCells(), actualOutputBoard.getCells());
        // new cell column reference
        assertNotSame(inputBoard.getCells()[0], actualOutputBoard.getCells()[0]);
        assertNotSame(inputBoard.getCells()[2], actualOutputBoard.getCells()[2]);
        // inputBoard not touched
        assertEquals(DistrictType.CENTER, inputBoard.getCells()[1][1].getDistrictType());
    }

    // --- valid board moves ---
    @Test
    public void testMoveBoard_MoveLeft_ValidMove() {
        Board inputBoard = new Board(
                "-- H0 --\n" +
                        "-- CC H1\n" +
                        "-- -- H2\n"
        );
        Board expectedOutputBoard = new Board(
                "H0 -- --\n" +
                        "CC H1 --\n" +
                        "-- H2 --\n"
        );
        Board actualOutputBoard = inputBoard.moveBoard(Board.Direction.LEFT_MOVE);
        assertEquals(expectedOutputBoard, actualOutputBoard);
    }

    @Test
    public void testMoveBoard_MoveUp_ValidMove() {
        Board inputBoard = new Board(
                "-- -- --\n" +
                        "H1 CC H0\n" +
                        "H2 -- --\n"
        );
        Board expectedOutputBoard = new Board(
                "H1 CC H0\n" +
                        "H2 -- --\n" +
                        "-- -- --\n"
        );
        Board actualOutputBoard = inputBoard.moveBoard(Board.Direction.UP_MOVE);
        assertEquals(expectedOutputBoard, actualOutputBoard);
    }

    @Test
    public void testMoveBoard_MoveRight_ValidMove() {
        Board inputBoard = new Board(
                "-- H0 --\n" +
                        "H1 CC --\n" +
                        "H2 -- --\n"
        );
        Board expectedOutputBoard = new Board(
                "-- -- H0\n" +
                        "-- H1 CC\n" +
                        "-- H2 --\n"
        );
        Board actualOutputBoard = inputBoard.moveBoard(Board.Direction.RIGHT_MOVE);
        assertEquals(expectedOutputBoard, actualOutputBoard);
    }

    @Test
    public void testMoveBoard_MoveDown_ValidMove() {
        Board inputBoard = new Board(
                "-- H1 H2\n" +
                        "H0 CC --\n" +
                        "-- -- --\n"
        );
        Board expectedOutputBoard = new Board(
                "-- -- --\n" +
                        "-- H1 H2\n" +
                        "H0 CC --\n"
        );
        Board actualOutputBoard = inputBoard.moveBoard(Board.Direction.DOWN_MOVE);
        assertEquals(expectedOutputBoard, actualOutputBoard);
    }


    // --- canMoveBoardToDir ---
    @Test
    public void testCanMoveBoardToDir_ValidAllDirections() {
        Board inputBoard = new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- -- --\n"
        );
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.LEFT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.UP_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.RIGHT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.DOWN_MOVE));
    }

    @Test
    public void testCanMoveBoardToDir_InvalidDir_LEFT() {
        Board inputBoard = new Board(
                "-- -- --\n" +
                        "H0 CC --\n" +
                        "-- -- --\n"
        );
        assertFalse(inputBoard.canMoveBoardToDir(Board.Direction.LEFT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.UP_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.RIGHT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.DOWN_MOVE));
    }

    @Test
    public void testCanMoveBoardToDir_InvalidDir_UP() {
        Board inputBoard = new Board(
                "-- H0 --\n" +
                        "-- CC --\n" +
                        "-- -- --\n"
        );
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.LEFT_MOVE));
        assertFalse(inputBoard.canMoveBoardToDir(Board.Direction.UP_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.RIGHT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.DOWN_MOVE));
    }

    @Test
    public void testCanMoveBoardToDir_InvalidDir_RIGHT() {
        Board inputBoard = new Board(
                "-- -- --\n" +
                        "-- CC H0\n" +
                        "-- -- --\n"
        );
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.LEFT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.UP_MOVE));
        assertFalse(inputBoard.canMoveBoardToDir(Board.Direction.RIGHT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.DOWN_MOVE));
    }

    @Test
    public void testCanMoveBoardToDir_InvalidDir_DOWN() {
        Board inputBoard = new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- H0 --\n"
        );
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.LEFT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.UP_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.RIGHT_MOVE));
        assertFalse(inputBoard.canMoveBoardToDir(Board.Direction.DOWN_MOVE));
    }

    @Test
    public void testCanMoveBoardToDir_OneValidHor_OneValidVerti() {
        Board inputBoard = new Board(
                "-- -- --\n" +
                        "-- CC H1\n" +
                        "-- H0 --\n"
        );
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.LEFT_MOVE));
        assertTrue(inputBoard.canMoveBoardToDir(Board.Direction.UP_MOVE));
        assertFalse(inputBoard.canMoveBoardToDir(Board.Direction.RIGHT_MOVE));
        assertFalse(inputBoard.canMoveBoardToDir(Board.Direction.DOWN_MOVE));
    }

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
        SingleTile[][] expectedOutput = new SingleTile[][]{{SingleTile.CC, SingleTile.EC}, {SingleTile.EC,
                SingleTile.EC}};
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
        assertFalse(board.fits(dom.setPos(new Pos(6, 0))));
        assertFalse(board.fits(dom.setPos(new Pos(6, 6))));
        // expected hori P0_A0_Val14
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

    // --- findPos ---
    @Test(expected = AssertionError.class)
    public void testFindPos_NullParam() {
        new Board("CC").findPos(null);
    }

    @Test
    public void testFindPos_Valid() {
        Pos expOutput = new Pos(2, 1);
        Pos actOutput = new Board("-- -- -- \n -- -- CC").findPos(CC);
        assertEquals(expOutput, actOutput);
    }


    @Test
    public void testFindPos_Invalid() {
        Pos expOutput = null;
        Pos actOutput = new Board("-- -- -- \n -- -- CC").findPos(P0);
        assertEquals(expOutput, actOutput);
    }


    // --- toFile / Copy Constructor ---

    @Test
    public void testToFile() {
        // Setting up banks
        GUIConnector fakeGui = new FakeGUI();
        Board board1 = new Board(
                "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- CC P0 S2\n" +
                        "-- -- I2 P0 --\n" +
                        "-- -- -- -- --");
        Board board2 = new Board(
                "-- -- P0 -- --\n" +
                        "-- -- I3 -- --\n" +
                        "A0 S1 CC -- --\n" +
                        "-- S1 P0 -- --\n" +
                        "-- -- -- -- --");
        Board board3 = new Board(
                "-- -- O0 -- --\n" +
                        "-- -- I2 I1 P0\n" +
                        "A0 S2 CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --");
        Board board4 = new Board(
                "P0 O1 O0 -- --\n" +
                        "-- -- I2 -- --\n" +
                        "A1 H0 CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --");

        Player[] expPlayers = new Player[]{
                new HumanPlayer(fakeGui, 0, board1),
                new DefaultAIPlayer(fakeGui, 1, board2),
                new DefaultAIPlayer(fakeGui, 2, board3),
                new DefaultAIPlayer(fakeGui, 3, board4)
        };

        Entry[] currRoundEntries = new Entry[]{
                null, null,
                new Entry(new Domino(Tiles.S0O1_Val39), expPlayers[0]),
                new Entry(new Domino(Tiles.I1P0_Val40), expPlayers[2])
        };
        Bank currBank = new Bank(currRoundEntries, new Random());

        Entry[] nextRoundEntries = new Entry[]{
                new Entry(new Domino(Tiles.A1H0_Val32), expPlayers[3]),
                new Entry(new Domino(Tiles.A1H0_Val33)),
                new Entry(new Domino(Tiles.A1H0_Val34)),
                new Entry(new Domino(Tiles.P0S1_Val36), expPlayers[1])
        };
        Bank nextBank = new Bank(nextRoundEntries, new Random());

        // actual tests
        String exp = "-- -- P0 -- --\n" +
                "-- -- I3 -- --\n" +
                "A0 S1 CC -- --\n" +
                "-- S1 P0 -- --\n" +
                "-- -- -- -- --\n";

        assertEquals(exp, board2.toString());
        assertEquals(exp, board2.toFile(expPlayers[1], currBank, nextBank));
    }

    // --- isEmptySingleCell ---
    @Test
    public void testAllNeighboursOccupied_EmptyBoard() {
        Board board = new Board(
                "-- H0 --\n" +
                        "H0 CC --\n" +
                        "-- P0 P0\n" +
                        "P0 -- --");
        Assert.assertTrue(board.isEmptySingleCell(new Pos(0, 0)));
        Assert.assertTrue(board.isEmptySingleCell(new Pos(0, 2)));
        Assert.assertFalse(board.isEmptySingleCell(new Pos(0, 1)));
        Assert.assertFalse(board.isEmptySingleCell(new Pos(2, 0)));
        Assert.assertFalse(board.isEmptySingleCell(new Pos(0, 1)));

    }


}
