package logic.playerState;

import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import other.FakeGUI;
import logic.dataPreservation.Logger;
import logic.playerTypes.DefaultAIPlayer;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.Tiles;

import static logic.token.SingleTile.A0;
import static logic.token.SingleTile.P0;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;

/**
 * Testclass for the default bot
 * - Checks if position for the next domino
 * - Checks if the right domino was choosen
 * - Further tests that appeared during manual testing
 */
public class DefaultAIPlayerTest {

    // --- 5. updateDominoPos, Modified tests from ueb09 ---
    // only one domino in bank -> no decision has to be made, just check if position for the next domino was modified
    // correctly
    @Test
    public void testUpdateDominoPos_EmptyBank() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- -- -- --\n"
                        + "-- -- S0 P0 -- --\n"
                        + "-- -- -- -- -- --\n");
        Bank nextBank = new Bank(new Entry[]{}, new Random());
        Bank emptyBank = player.selectFromBank(nextBank, 1);
        assertSame(nextBank, emptyBank);
        assertNull(emptyBank.getPlayerSelectedDomino(player));
    }

    @Test
    public void testUpdateDominoPos_rot0_above() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- -- -- --\n"
                        + "-- -- S0 P0 -- --\n"
                        + "-- -- -- -- -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(3, 0), 0);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_below() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "H0 -- A1 A1 -- H0\n"
                        + "-- -- S0 P0 -- A0\n"
                        + "-- -- -- -- -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(3, 2), 0);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_rightOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "H0 -- A1 A1 -- --\n"
                        + "-- -- S0 P0 -- --\n"
                        + "-- -- H0 H0 -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(4, 0), 2);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_aboveleft() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- -- -- --\n"
                        + "S0 -- A0 P0 -- --\n"
                        + "-- -- -- -- -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(1, 0), 0);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_belowleft() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- S0 S0 -- --\n"
                        + "S0 -- A0 P0 -- S0\n"
                        + "-- -- -- -- -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(1, 2), 0);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_leftOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- S0 S1 -- --\n"
                        + "-- -- A0 P0 -- --\n"
                        + "-- -- H0 H0 -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(0, 1), 0);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot1_leftOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S0 P0 -- --\n"
                        + "-- -- -- -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(3, 0), 0);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot2_leftOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- H1 H1 -- --\n"
                        + "-- -- P0 S0 -- --\n"
                        + "-- -- H1 H1 -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(0, 1), 2);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot2_bottomleft() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- H1 H1 -- --\n"
                        + "H1 -- P0 S0 -- --\n"
                        + "-- -- -- -- -- --\n");
        Tiles domTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(domTiles, new Pos(1, 2), 2);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }


    // Checks if the right domino was chosen
    // --- selectFromBank ---
    // 1. tests for most points
    @Test
    public void testSelectFromBank_TwoDomsOnBank_UniquePosToLayDominos() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- P0 O0\n"
                        + "-- CC O0\n"
                        + "-- A0 O0\n");
        Tiles mostValuableTiles = Tiles.genTile(A1, P0);
        Bank nextBank = new Bank(new Entry[]{
                new Entry(new Domino(Tiles.genTile(P0, A0))),
                new Entry(new Domino(mostValuableTiles))
        }, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(mostValuableTiles, new Pos(0, 1), Pos.UP_ROT);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSelectFromBank_TwoDomsOnBank_DomWithTokenMakesMorePointsThanDomWithoutToken() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- P0 P1\n"
                        + "-- CC P0\n"
                        + "-- A0 O0\n");
        Tiles mostValuableTiles = Tiles.genTile(A1, P0);
        Bank nextBank = new Bank(new Entry[]{
                new Entry(new Domino(Tiles.genTile(P0, A0))),
                new Entry(new Domino(mostValuableTiles))
        }, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(mostValuableTiles, new Pos(0, 0), Pos.UP_ROT);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSelectFromBank_TwoDomsOnBank_DomWithTokenMakesLessPointsThanDomWithoutToken() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- P0 P1\n"
                        + "-- CC P1\n"
                        + "-- A0 O0\n");
        Tiles mostValuableTiles = Tiles.genTile(P0, A0);
        Bank nextBank = new Bank(new Entry[]{
                new Entry(new Domino(mostValuableTiles)),
                new Entry(new Domino(Tiles.genTile(A1, H0)))
        }, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(mostValuableTiles, new Pos(0, 0), Pos.DOWN_ROT);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    // tests for tie
    // TODO write tests for draw / tie
    @Test
    public void testSelectFromBank_TieBetweenTwoDominos_BothInefficient() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- P0 P1\n"
                        + "-- CC O0\n"
                        + "-- A0 A1\n");
        Tiles mostValuableTiles = Tiles.genTile(P1, H0);
        Bank nextBank = new Bank(new Entry[]{
                new Entry(new Domino(mostValuableTiles)),
                new Entry(new Domino(Tiles.genTile(A1, H0)))
        }, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(mostValuableTiles, new Pos(0, 0), Pos.DOWN_ROT);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSelectFromBank_TieBetweenTwoDominos_FstEfficient() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S0 P1\n"
                        + "-- CC O0\n"
                        + "-- P0 A1\n");
        Tiles mostValuableTiles = Tiles.genTile(P0, S2);
        Bank nextBank = new Bank(new Entry[]{
                new Entry(new Domino(mostValuableTiles)),
                new Entry(new Domino(Tiles.genTile(A0, S2)))
        }, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(mostValuableTiles, new Pos(0, 0), Pos.UP_ROT);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSelectFromBank_TieBetweenTwoDominos_SndEfficient() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S0 P1\n"
                        + "-- CC O0\n"
                        + "-- A0 A1\n");
        Tiles mostValuableTiles = Tiles.genTile(A0, S2);
        Bank nextBank = new Bank(new Entry[]{
                new Entry(new Domino(Tiles.genTile(P0, S2))),
                new Entry(new Domino(mostValuableTiles))
        }, new Random());
        Domino selectedDom = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        Domino expectedOutput = new Domino(mostValuableTiles, new Pos(0, 0), Pos.UP_ROT);
        Domino actualOutput = player.updateDominoPos(selectedDom);
        assertEquals(expectedOutput, actualOutput);
    }

    // TODO more tests for tie



    // --- error occurred during manual testing ---
    // All screenshots can be found in the documentation
    // TODO put Screenshots in docu
    @Test
    public void testSelectFromBank_Screenshot1() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- -- --\n"
                        + "-- H0 H0 -- --\n"
                        + "-- -- CC P0 O1\n"
                        + "-- --  -- -- --\n"
                        + "-- --  -- -- --\n");
        Tiles domTiles = Tiles.genTile(H1, P0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());

        Domino wrongOutput = new Domino(domTiles, new Pos(0, 0), 0);
        Domino expectedOutput = new Domino(domTiles, new Pos(0, 0), 2);
        Domino actualOutput = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        assertNotEquals(wrongOutput, actualOutput);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSelectFromBank_Screenshot2() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- -- --\n"
                        + "-- I1 P0  -- --\n"
                        + "-- -- CC -- --\n"
                        + "-- --  -- -- --\n"
                        + "-- --  -- -- --\n");
        Tiles domTiles = Tiles.genTile(H1, P0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());

        Domino wrongOutput = new Domino(domTiles, new Pos(0, 0), 0);
        Domino expectedOutput = new Domino(domTiles, new Pos(2, 0), 2);
        Domino actualOutput = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        assertNotEquals(wrongOutput, actualOutput);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSelectFromBank_Screenshot3() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- P0 -- --\n"
                        + "-- -- A0  -- --\n"
                        + "-- -- CC -- --\n"
                        + "-- --  -- -- --\n"
                        + "-- --  -- -- --\n");
        Tiles domTiles = Tiles.genTile(S0, S0);
        Bank nextBank = new Bank(new Entry[]{new Entry(new Domino(domTiles))}, new Random());

        Domino wrongOutput1 = new Domino(domTiles, new Pos(0, 1), 0);
        Domino wrongOutput2 = new Domino(domTiles, new Pos(0, 1), 2);
        Domino expectedOutput = new Domino(domTiles, new Pos(0, 2), 0);
        Domino actualOutput = player.selectFromBank(nextBank, 1).getPlayerSelectedDomino(player);
        assertNotEquals(wrongOutput1, actualOutput);
        assertNotEquals(wrongOutput2, actualOutput);
        assertEquals(expectedOutput, actualOutput);
    }

}
