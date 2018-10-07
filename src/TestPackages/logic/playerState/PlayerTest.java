package TestPackages.logic.playerState;

import TestPackages.other.FakeGUI;
import logic.dataPreservation.Logger;
import logic.playerState.Board;
import logic.playerState.District;
import logic.playerState.Player;
import logic.playerTypes.DefaultAIPlayer;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;

public class PlayerTest {


    // --- find proper district distribution ---

    @Test
    public void testConstructor_TouchingDistricts_TwoDistrictsContainingOneElem() {
        // expected districts
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(SingleTile.P1, new Pos(2, 1)));
        expectedDistricts.add(new District(SingleTile.S0, new Pos(2, 2)));
        // actual districts
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                        "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- S0\n");
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
                        "P0 -- --\n" +
                        "-- CC P1\n" +
                        "-- -- --\n");
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
                        "P0 P1 --\n" +
                        "-- CC --\n" +
                        "-- -- --\n");
        List<District> actualDistricts = player.getDistricts();
        // testing
        assertEquals(expectedDistricts, actualDistricts);
    }


    // --- lay ---

    /**
     * Board
     * EC EC EC
     * EC CC P1
     * EC EC S0
     */
    @Test
    public void testLay_TwoDistrictsContainingOneElem() {
        // expected output
        Board expectedBoard = new Board(
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- S0\n");
        List<District> expectedDistricts = new LinkedList<>();
        expectedDistricts.add(new District(SingleTile.P1, new Pos(2, 1)));
        expectedDistricts.add(new District(SingleTile.S0, new Pos(2, 2)));

        // actual output
        Player player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- -- --\n");
        player.showOnBoard(new Domino(Tiles.genTile(SingleTile.P1, SingleTile.S0), 1));
        Board actualBoard = player.getBoard();
        List<District> actualDistricts = player.getDistricts();

        assertEquals(expectedBoard, actualBoard);
        assertEquals(expectedDistricts, actualDistricts);
    }


    // --- 3. getBoardPoints ---

    // --- 3.1: Touching districts without points ---
    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_NoDominosOnBoard() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- -- --\n" +
                        "-- -- --\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_OnlyCityHall() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- -- --\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_OneCell() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- H0 --\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_TwoCellsDifferentType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- H0 S0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_TwoDistrictsOneWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC S0\n" +
                        "-- H0 S0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_TwoDistrictsBothWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC S0\n" +
                        "H0 H0 S0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_DistrictsFillingWholeBoard_TwoTouches() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-H0 S0 S0\n" +
                        "H0 CC S0\n" +
                        "H0 H0 S0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithoutSymbols_OneCellSourroundedByDistrict() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-S0 S0 S0\n" +
                        "H0 CC S0\n" +
                        "S0 S0 S0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    // --- 3.1: Touching districts with points ---
    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_OneCell() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- H1 --\n"));
        assertEquals(1, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_TwoCellsDifferentType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- H1 S1\n"));
        assertEquals(2, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_TwoDistrictsOneWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC S0\n" +
                        "-- H3 S1\n"));
        assertEquals(5, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_TwoDistrictsBothWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC S2\n" +
                        "H0 H2 S1\n"));
        assertEquals(10, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_DistrictsFillingWholeBoard_OneDistrictWithoutPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-H0 S1 S1\n" +
                        "H0 CC S0\n" +
                        "H0 H0 S0\n"));
        assertEquals(8, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_DistrictsFillingWholeBoard_BothDistrictsHavePoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-H1 S1 S1\n" +
                        "H0 CC S0\n" +
                        "H0 H0 S0\n"));
        assertEquals(12, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_TouchingDistrictsWithSymbols_OneCellSourroundedByDistrict() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-S0 S0 S1\n" +
                        "H0 CC S0\n" +
                        "S0 S0 S0\n"));
        assertEquals(7, player.getBoardPoints());
    }


    // --- 3.3 Not touching districts ---
    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_NoDominosOnBoard() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- -- --\n" +
                        "-- -- --\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_OnlyCityCenter() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- -- --\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDominosInOneDistrict() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- -- --\n" +
                        "-- CC --\n" +
                        "-- H0 H0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDistrictsOneWithMultipleCellsSameType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- H0 --\n" +
                        "-- CC --\n" +
                        "-- H0 H0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDistrictsOneWithMultipleCellsDifferentType() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- S0 --\n" +
                        "-- CC --\n" +
                        "-- H0 H0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithoutSymbols_TwoDistrictsBothWithMultipleCells() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- S0 S0\n" +
                        "-- CC --\n" +
                        "-- H0 H0\n"));
        assertEquals(0, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithSymbols_TwoDistrictsOneWithPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- S1 S0\n" +
                        "-- CC --\n" +
                        "-- H0 H0\n"));
        assertEquals(2, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithSymbols_TwoDistrictsBothWithPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- S1 S0\n" +
                        "-- CC --\n" +
                        "-- H1 H0\n"));
        assertEquals(2, player.getBoardPoints());
    }

    @Test
    public void testGetBoardPoints_NotTouchingDistrictsWithSymbols_TwoDistrictsCompletelyFilledWithPoints() {
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, new Board(
                "-- S1 S1\n" +
                        "-- CC --\n" +
                        "-- H1 H2\n"));
        assertEquals(10, player.getBoardPoints());
    }

}
