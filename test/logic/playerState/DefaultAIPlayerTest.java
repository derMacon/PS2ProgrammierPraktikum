package logic.playerState;

import other.FakeGUI;
import logic.dataPreservation.Logger;
import logic.playerTypes.DefaultAIPlayer;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.Tiles;

import static logic.token.SingleTile.A0;
import static logic.token.SingleTile.P0;

import org.junit.Test;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;


public class DefaultAIPlayerTest {

    // --- 5. updateDominoPos, Modified tests from ueb09 ---
    @Test
    public void testUpdateDominoPos_rot0_above() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- -- -- --\n" +
                        "-- -- S0 P0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(3, 0), 0);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_below() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- A1 A1 -- --\n" +
                        "-- -- S0 P0 -- A1\n" +
                        "-- -- -- -- -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(3, 2), 0);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_rightOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- A1 A1 -- --\n" +
                        "-- -- S0 P0 -- --\n" +
                        "-- -- H0 H0 -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(4, 1), 0);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_aboveleft() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- -- -- -- --\n" +
                        "S0 -- A0 P0 -- --\n" +
                        "-- -- -- -- -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(4, 1), 0);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_belowleft() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- S0 S0 -- --\n" +
                        "S0 -- A0 P0 -- S0\n" +
                        "-- -- -- -- -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(1, 2), 0);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot0_leftOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- S0 S1 -- --\n" +
                        "-- -- A0 P0 -- --\n" +
                        "-- -- H0 H0 -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(0, 1), 0);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot1_leftOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- S0 P0 -- --\n" +
                        "-- -- -- -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(0, 0), 1);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot2_leftOf() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- H1 H1 -- --\n" +
                        "-- -- P0 S0 -- --\n" +
                        "-- -- H1 H1 -- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(4, 1), 2);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUpdateDominoPos_rot2_bottomleft() {
        DefaultAIPlayer player = new DefaultAIPlayer(new FakeGUI(), 1,
                "-- -- H1 H1 -- --\n" +
                        "H1 -- P0 S0 -- --\n" +
                        "-- -- -- ---- --\n");
        Domino expectedOutput = new Domino(Tiles.genTile(P0, A0), new Pos(1, 2), 2);
        Domino actualOutput = player.updateDominoPos(new Domino(Tiles.genTile(P0, A0)));
        assertEquals(expectedOutput, actualOutput);
    }

}
