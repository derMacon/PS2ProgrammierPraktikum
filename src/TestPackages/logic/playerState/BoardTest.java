package TestPackages.logic.playerState;

import logic.playerState.Board;
import logic.token.Domino;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    //<editor-fold defaultstate="collapsed" desc="Domino generator">

    /**
     * Genererates a domino from the given occupancy
     * @param fstOrd ordinal value of the first tile
     * @param sndOrd ordinal value of the second tile
     * @return a domino from the given occupancy
     */
    private Domino genDominoFromTileOrd(int fstOrd, int sndOrd) {
        Tiles tiles = Tiles.genTile(fstOrd, sndOrd);
        assert null != tiles;
        return new Domino(tiles);
    }

    //</editor=fold>


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


    // --- Constructor - only used for testing ---
    @Test(expected = AssertionError.class)
    public void testConstructor_NullParam() {
        Board board = new Board(null);
    }

    @Test(expected = AssertionError.class)
    public void testConstructor_EmptyString() {
        Board board = new Board("");
    }

    @Test
    public void testConstructor_OneCharStringEmpty() {
        Board board = new Board("-1");
        assertEquals(1, board.getSizeX());
        assertEquals(1, board.getSizeY());
        SingleTile[][] expectedCells = new SingleTile[][]{{null}};
        assertArrayEquals(expectedCells, board.getCells());
    }

    @Test
    public void testConstructor_OneCharStringFilled() {
        Board board = new Board("0");
        assertEquals(1, board.getSizeX());
        assertEquals(1, board.getSizeY());
        SingleTile[][] expectedCells = new SingleTile[][]{{SingleTile.CITY_HALL}};
        assertArrayEquals(expectedCells, board.getCells());
    }

    @Test
    public void testConstructor_FilledString() {
        Board board = new Board("-1 -1 \n -1 0");
        assertEquals(2, board.getSizeX());
        assertEquals(2, board.getSizeY());
        SingleTile[][] expectedCells = new SingleTile[][]{{null, null}, {null, SingleTile.CITY_HALL}};
        assertArrayEquals(expectedCells, board.getCells());
    }


    //<editor-fold defaultstate="collapsed" desc="Modified tests from ueb09">
    // --- fits ---
    @Test
    public void testFits_Touches3Sides() {
//        Board board = new Board(
//                "eeeeeee\n" +
//                        "eeeeeee\n" +
//                        "ee4ee6e\n" +
//                        "ee4ee3e\n" +
//                        "ee4333e\n" +
//                        "eeeeeee\n");
//        Domino dom = genDominoFromTileOrd(1, 4);

    }


    //</editor-fold>

}
