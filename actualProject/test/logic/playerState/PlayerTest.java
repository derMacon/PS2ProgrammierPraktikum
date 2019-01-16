package logic.playerState;

import logic.differentPlayerTypes.DefaultAIPlayer;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;
import other.FakeGUI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;

public class PlayerTest {

    // --- Setting up districts for testing ---

    /**
     * Checks if lists contain same elements independent of order
     * https://stackoverflow.com/questions/1075656/simple-way-to-find-if-two-different-lists-contain-exactly-the-same
     * -elements
     *
     * @param list1 first list to compare with
     * @param list2 second list to compare with
     * @param <T>   type of the list elements
     * @return true if lists contain same elements independent of order
     */
    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    /**
     * Setts up a district from a given SingleTile array and another Pos array
     *
     * @param tiles tiles of the district
     * @param pos   positions of the district
     * @return a district from a given SingleTile array and another Pos array
     * @pre null != tiles && null != pos && tiles.length == pos.length;
     */
    private District setupDistrict(SingleTile[] tiles, Pos[] pos) {
        assert null != tiles && null != pos && tiles.length == pos.length;
        return new District(Arrays.asList(tiles), Arrays.asList(pos));
    }

    /**
     * Checks if two districts match with their respective content
     *
     * @param fstDistrict first district to check against
     * @param sndDistrict second district to check against
     * @return true if districts match
     */
    private boolean districtsMatch(District fstDistrict, District sndDistrict) {
        return listEqualsIgnoreOrder(fstDistrict.getSingleTiles(), sndDistrict.getSingleTiles())
                && listEqualsIgnoreOrder(fstDistrict.getTilePositions(), fstDistrict.getTilePositions());
    }

    // --- find proper district distribution ---
    @Test
    public void testConstructor_MergingConstructor_Valid() {
        // expected output
        List<SingleTile> tileInput = Arrays.asList(new SingleTile[]{SingleTile.P0, SingleTile.P1, SingleTile.P2});
        List<Pos> posInput = Arrays.asList(new Pos[]{new Pos(0, 0), new Pos(1, 0), new Pos(2, 0)});
        District expectedOutput = new District(tileInput, posInput);
        // actual output
        List<District> districtList = new LinkedList<>();
        districtList.add(new District(new District[]{
                new District(SingleTile.P0, new Pos(0, 0)),
                new District(SingleTile.P1, new Pos(1, 0)),
                new District(SingleTile.P2, new Pos(2, 0))
        }));
        District actualOutput = new District(districtList);
        // testing
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testConstructor_TouchingDistricts_TwoDistrictsContainingOneElem() {
        // expected districts
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(SingleTile.P1, new Pos(2, 1)));
        expectedDistricts.add(new District(SingleTile.S0, new Pos(2, 2)));
        // actual districts
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC P1\n"
                        + "-- -- S0\n");
        List<District> actualDistricts = player.getDistricts();
        // testing
        assertEquals(expectedDistricts, actualDistricts);
    }

    @Test
    public void testConstructor_NotTouchingSameType_TwoDistrictsContainingOneElem() {
        // expected districts
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(SingleTile.P0, new Pos(0, 0)));
        expectedDistricts.add(new District(SingleTile.P1, new Pos(2, 1)));
        // actual districts
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "P0 -- --\n"
                        + "-- CC P1\n"
                        + "-- -- --\n");
        List<District> actualDistricts = player.getDistricts();
        // testing
        assertEquals(expectedDistricts, actualDistricts);
    }

    @Test
    public void testConstructor_OneDistrictTwoElements() {
        // expected districts
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(new District[]{
                new District(SingleTile.P0, new Pos(0, 0)),
                new District(SingleTile.P1, new Pos(1, 0))
        }));
        // actual districts
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "P0 P1 --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        List<District> actualDistricts = player.getDistricts();
        // testing
        assertEquals(expectedDistricts, actualDistricts);
    }


    // --- lay ---
    @Test
    public void testLay_DisposableDomino() {
        // expected output
        Board expectedBoard = new Board(
                "-- -- --\n"
                        + "-- CC P1\n"
                        + "-- -- S0\n");
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(SingleTile.P1, new Pos(2, 1)));
        expectedDistricts.add(new District(SingleTile.S0, new Pos(2, 2)));

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC P1\n"
                        + "-- -- S0\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.O0, SingleTile.I2), null, 1));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertEquals(expectedDistricts, actualDistricts);
    }

    /**
     * Board EC EC EC EC CC P1 EC EC S0
     */
    @Test
    public void testLay_TwoDistrictsContainingOneElem() {
        // expected output
        Board expectedBoard = new Board(
                "-- -- --\n"
                        + "-- CC P1\n"
                        + "-- -- S0\n");
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(SingleTile.P1, new Pos(2, 1)));
        expectedDistricts.add(new District(SingleTile.S0, new Pos(2, 2)));

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.P1, SingleTile.S0), new Pos(2, 1), 1));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertEquals(expectedDistricts, actualDistricts);
    }

    @Test
    public void testLay_MergingTwoDistricts_OneDistrictsInTotal() {
        // expected output
        Board expectedBoard = new Board(
                "-- S0 S0\n"
                        + "-- CC S1\n"
                        + "-- -- S0\n");
        SingleTile[] district1Tiles = new SingleTile[]{SingleTile.S1, SingleTile.S0, SingleTile.S0, SingleTile.S0};
        Pos[] district1Pos = new Pos[]{new Pos(2, 1), new Pos(2, 2), new Pos(1, 0), new Pos(2, 0)};
        District expectedDistric = setupDistrict(district1Tiles, district1Pos);
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(expectedDistric);

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC S1\n"
                        + "-- -- S0\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.S0, SingleTile.S0), new Pos(1, 0)));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertEquals(expectedDistricts, actualDistricts);
    }

    @Test
    public void testLay_MergingTwoDistricts_TwoDistrictsInTotal() {
        // expected output
        Board expectedBoard = new Board(
                "S0 P0 --\n"
                        + "S1 CC --\n"
                        + "S2 -- --\n");
        SingleTile[] district1Tiles = new SingleTile[]{SingleTile.S1, SingleTile.S2, SingleTile.S0};
        Pos[] district1Pos = new Pos[]{new Pos(0, 1), new Pos(0, 2), new Pos(0, 0)};
        District expectedSingleDistric = setupDistrict(district1Tiles, district1Pos);
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(expectedSingleDistric);
        expectedDistricts.add(new District(SingleTile.P0, new Pos(1, 0)));

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "S1 CC --\n"
                        + "S2 -- --\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.S0), new Pos(0, 0), 2));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertEquals(expectedDistricts, actualDistricts);
    }

    @Test
    public void testLay_CannotLayWrongType() {
        String input =
                "-- -- --\n"
                        + "S1 CC --\n"
                        + "S2 -- --\n";
        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                input);
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.S0), 0)); // difference to previous test
        assertEquals(input, player.getBoard().toString());
    }

    @Test
    public void testLay_MergeThreeDistricts() {
        // expected output
        Board expectedBoard = new Board(
                "S0 S0 S0\n"
                        + "S1 CC S0\n"
                        + "S2 -- S1\n");
        SingleTile[] district1Tiles = new SingleTile[]{SingleTile.S0, SingleTile.S1, SingleTile.S2, SingleTile.S0,
                SingleTile.S1, SingleTile.S0, SingleTile.S0};
        Pos[] district1Pos = new Pos[]{new Pos(0, 0), new Pos(0, 1), new Pos(0, 2), new Pos(2, 1),
                new Pos(2, 2), new Pos(1, 0), new Pos(2, 0)};
        District expectedSingleDistric = setupDistrict(district1Tiles, district1Pos);
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(expectedSingleDistric);

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "S0 -- --\n"
                        + "S1 CC S0\n"
                        + "S2 -- S1\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.S0, SingleTile.S0), new Pos(1, 0)));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertTrue(districtsMatch(((LinkedList<District>) expectedDistricts).get(0), actualDistricts.get(0)));
    }

    @Test
    public void testLay_MergeTwoDistricts_WholeField() {
        // expected output
        Board expectedBoard = new Board(
                "S0 S0 S0\n"
                        + "S1 CC S0\n"
                        + "S2 S0 S1\n");
        SingleTile[] district1Tiles = new SingleTile[]{SingleTile.S0, SingleTile.S1, SingleTile.S2, SingleTile.S0,
                SingleTile.S1, SingleTile.S0, SingleTile.S0, SingleTile.S0};
        Pos[] district1Pos = new Pos[]{new Pos(0, 0), new Pos(0, 1), new Pos(0, 2), new Pos(2, 1),
                new Pos(2, 2), new Pos(1, 0), new Pos(2, 0), new Pos(1, 2)};
        District expectedSingleDistric = setupDistrict(district1Tiles, district1Pos);
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(expectedSingleDistric);

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "S0 -- --\n"
                        + "S1 CC S0\n"
                        + "S2 S0 S1\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.S0, SingleTile.S0), new Pos(1, 0)));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertTrue(districtsMatch(((LinkedList<District>) expectedDistricts).get(0), actualDistricts.get(0)));
    }

    @Test
    public void testLay_MergeFourDistricts_WholeField() {
        // expected output
        Board expectedBoard = new Board(
                "-- -- -- S0 --\n"
                        + "-- -- P0 S0 S1\n"
                        + "-- -- CC S2 --\n"
                        + "-- -- -- -- --\n"
                        + "-- -- -- -- --\n");
        SingleTile[] district1Tiles = new SingleTile[]{SingleTile.S0, SingleTile.S1, SingleTile.S2, SingleTile.S0};
        Pos[] district1Pos = new Pos[]{new Pos(3, 0), new Pos(3, 1), new Pos(4, 1), new Pos(4, 2)};
        District expectedSingleDistrict = setupDistrict(district1Tiles, district1Pos);
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(SingleTile.P0, new Pos(2, 1)));
        expectedDistricts.add(expectedSingleDistrict);

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- S0 --\n"
                        + "-- -- -- -- S1\n"
                        + "-- -- CC S2 --\n"
                        + "-- -- -- -- --\n"
                        + "-- -- -- -- --\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.S0), new Pos(2, 1)));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertTrue(districtsMatch(((LinkedList<District>) expectedDistricts).get(0), actualDistricts.get(0)));
        assertTrue(districtsMatch(((LinkedList<District>) expectedDistricts).get(1), actualDistricts.get(1)));
    }

    // --- equals ---
    @Test
    public void testEquals_NullParam() {
        Board board = new Board(
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        assertFalse(board.equals(null));
    }

    @Test
    public void testEquals_Invalid() {
        Board board1 = new Board(
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        Board board2 = new Board(
                "-- -- --\n"
                        + "-- CC P0\n"
                        + "-- -- --\n");
        assertNotEquals(board1, board2);
    }

    @Test
    public void testEquals_Valid() {
        Board board1 = new Board(
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        Board board2 = new Board(
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        assertEquals(board1, board2);
    }

    // --- 3. getBoardPoints ---
    // --- 3.1: Touching districts without points ---
    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_NoDominosOnBoard() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- -- --\n"
                        + "-- -- --\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_OnlyCityHall() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_OneCell() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- H0 --\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_TwoCellsDifferentType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- H0 S0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_TwoDistrictsOneWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC S0\n"
                        + "-- H0 S0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_TwoDistrictsBothWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC S0\n"
                        + "H0 H0 S0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_DistrictsFillingWholeBoard_TwoTouches() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "H0 S0 S0\n"
                        + "H0 CC S0\n"
                        + "H0 H0 S0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_OneCellSourroundedByDistrict() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "S0 S0 S0\n"
                        + "H0 CC S0\n"
                        + "S0 S0 S0\n");
        assertEquals(0, player.getBoardPoints());
    }

    // --- 3.1: Touching districts with points ---
    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_OneCell() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- H1 --\n");
        assertEquals(1, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_TwoCellsDifferentType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- H1 S1\n");
        assertEquals(2, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_TwoDistrictsOneWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC S0\n"
                        + "-- H3 S1\n");
        assertEquals(5, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_TwoDistrictsBothWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC S2\n"
                        + "H0 H2 S1\n");
        assertEquals(10, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_DistrictsFillingWholeBoard_OneDistrictWithoutPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "H0 S1 S1\n"
                        + "H0 CC S0\n"
                        + "H0 H0 S0\n");
        assertEquals(8, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_DistrictsFillingWholeBoard_BothDistrictsHavePoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "H1 S1 S1\n"
                        + "H0 CC S0\n"
                        + "H0 H0 S0\n");
        assertEquals(12, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_OneCellSourroundedByDistrict() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "S0 S0 S1\n"
                        + "H0 CC S0\n"
                        + "S0 S0 S0\n");
        assertEquals(7, player.getBoardPoints());
    }

    // --- 3.3 Not touching districts ---
    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_NoDominosOnBoard() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- -- --\n"
                        + "-- -- --\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_OnlyCityCenter() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDominosInOneDistrict() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- H0 H0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDistrictsOneWithMultipleCellsSameType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- H0 --\n"
                        + "-- CC --\n"
                        + "-- H0 H0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDistrictsOneWithMultipleCellsDifferentType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S0 --\n"
                        + "-- CC --\n"
                        + "-- H0 H0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDistrictsBothWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S0 S0\n"
                        + "-- CC --\n"
                        + "-- H0 H0\n");
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithSymbols_TwoDistrictsOneWithPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S0\n"
                        + "-- CC --\n"
                        + "-- H0 H0\n");
        assertEquals(2, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithSymbols_TwoDistrictsBothWithPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S0\n"
                        + "-- CC --\n"
                        + "-- H1 H0\n");
        assertEquals(4, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithSymbols_TwoDistrictsCompletelyFilledWithPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S1\n"
                        + "-- CC --\n"
                        + "-- H1 H2\n");
        assertEquals(10, player.getBoardPoints());
    }

    // --- compareTo ---
    @Test
    public void testCompareTo_OnePlayerMaxPoints() {
        Player player1 = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S1\n"
                        + "-- CC --\n"
                        + "-- H1 --\n");
        Player player2 = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S1\n"
                        + "-- CC --\n"
                        + "-- H1 H0\n");
        assertTrue(player1.compareTo(player2) < 0);
        assertTrue(player2.compareTo(player1) > 0);
    }

    @Test
    public void testCompareTo_BothPlayersEqualPoints_EqualDistrictsSizes() {
        Player player1 = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S1\n"
                        + "-- CC --\n"
                        + "-- H1 H0\n");
        Player player2 = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S1\n"
                        + "-- CC --\n"
                        + "-- H1 H0\n");
        assertTrue(player1.compareTo(player2) == 0);
        assertTrue(player2.compareTo(player1) == 0);
    }

    @Test
    public void testCompareTo_BothPlayersEqualPoints_NotEqualDistrictsSizes() {
        Player player1 = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S1 S1\n"
                        + "-- CC --\n"
                        + "-- H1 H0\n");
        Player player2 = new DefaultAIPlayer(new FakeGUI(), 1,
                "S1 S1 S1\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        assertTrue(player1.compareTo(player2) < 0);
        assertTrue(player2.compareTo(player1) > 0);
    }

    // --- updateDistricts ---
    @Test
    public void testUpdateDistricts_FirstMove() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        List<District> expectedDistricts = new LinkedList<>();
        List<District> actualDistricts = player.getDistricts();
        assertEquals(expectedDistricts, actualDistricts);

        Tiles domTiles = Tiles.genTile(P0, A0);
        Domino domino = new Domino(domTiles, new Pos(0, 0));


        expectedDistricts.add(new District(Arrays.asList(new SingleTile[]{P0}),
                Arrays.asList(new Pos[]{new Pos(0, 0)})));
        expectedDistricts.add(new District(Arrays.asList(new SingleTile[]{A0}),
                Arrays.asList(new Pos[]{new Pos(1, 0)})));
        actualDistricts = player.updateDistricts(actualDistricts, domino);
        assertEquals(expectedDistricts, actualDistricts);
    }

    @Test
    public void testUpdateDistricts_SecondMove() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "P0 A0 --\n"
                        + "-- CC --\n"
                        + "-- -- --\n");
        List<District> expectedDistricts = new LinkedList<>();
        List<District> actualDistricts = player.getDistricts();

        Tiles domTiles = Tiles.genTile(P1, H0);
        Domino domino = new Domino(domTiles, new Pos(0, 1), Pos.DOWN_ROT);
        expectedDistricts.add(new District(Arrays.asList(new SingleTile[]{A0}),
                Arrays.asList(new Pos[]{new Pos(1, 0)})));
        expectedDistricts.add(new District(Arrays.asList(new SingleTile[]{P0, P1}),
                Arrays.asList(new Pos[]{new Pos(0, 0), new Pos(0, 1)})));
        expectedDistricts.add(new District(Arrays.asList(new SingleTile[]{H0}),
                Arrays.asList(new Pos[]{new Pos(0, 2)})));
        actualDistricts = player.updateDistricts(actualDistricts, domino);
        assertEquals(expectedDistricts, actualDistricts);
    }

}
