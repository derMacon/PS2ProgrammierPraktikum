package logic.bankSelection;

import static org.junit.Assert.*;

import logic.token.Tiles;
import org.junit.Test;


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


}
