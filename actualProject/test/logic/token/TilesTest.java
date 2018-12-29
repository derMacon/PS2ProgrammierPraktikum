package logic.token;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TilesTest {

    // --- genTile ---
    @Test
    public void testGenTile_FromTileValue() {
        Tiles expectedOutput = Tiles.values()[0];
        Tiles actualOutput = Tiles.genTile(SingleTile.P0, SingleTile.P0);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test(expected = AssertionError.class)
    public void testGenTile_NullParam1() {
        Tiles.genTile(null, SingleTile.P0);
    }

    @Test(expected = AssertionError.class)
    public void testGenTile_NullParam2() {
        Tiles.genTile(SingleTile.P0, null);
    }
}
