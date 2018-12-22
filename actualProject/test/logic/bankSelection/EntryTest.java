package logic.bankSelection;

import static org.junit.Assert.*;

import logic.playerState.Player;
import logic.playerTypes.DefaultAIPlayer;
import logic.token.Domino;
import logic.token.Tiles;
import org.junit.Test;
import other.FakeGUI;


public class EntryTest {

    // --- from String ---
    @Test
    public void testFromString_FirstTile() {
        Tiles expectedOutput = Tiles.P0P0_Val1;
        Tiles actualOutput = Tiles.fromString("P0P0");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromString_MiddleTile() {
        Tiles expectedOutput = Tiles.S0S0_Val10;
        Tiles actualOutput = Tiles.fromString("S0S0");
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testFromString_LastTile() {
        Tiles expectedOutput = Tiles.P0I3_Val48;
        Tiles actualOutput = Tiles.fromString("P0I3");
        assertEquals(expectedOutput, actualOutput);
    }


    // --- equals ---
    @Test
    public void testEquals_NullParam1() {
        assertFalse(new Entry(new Domino(Tiles.P0H0_Val13)).equals(null));
    }

    @Test
    public void testEquals_OnePlayerZero_DifferentTokenValues() {
        assertFalse(new Entry(new Domino(Tiles.H0H0_Val4),
                new DefaultAIPlayer(new FakeGUI(), 1, 2,3)).equals(new Entry(new Domino(Tiles.H0H0_Val3))));
    }

    @Test
    public void testEquals_PlayersEqual_DifferentTokenValues() {
        Player inputPlayer = new DefaultAIPlayer(new FakeGUI(), 1, 2,3);
        assertTrue(new Entry(new Domino(Tiles.H0H0_Val4),
                inputPlayer).equals(new Entry(new Domino(Tiles.H0H0_Val3), inputPlayer)));
    }

    @Test
    public void testEquals_SamePlayer_DifferentToken() {
        assertFalse(new Entry(new Domino(Tiles.P0I3_Val48),
                new DefaultAIPlayer(new FakeGUI(), 1, 2,3)).equals(new Entry(new Domino(Tiles.H0H0_Val3))));
    }



}
