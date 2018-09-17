package TestPackages.logic.token;

import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import static org.junit.Assert.*;
public class TilesTest {

    @Test
    public void testGenTile_FromTileValue() {
        Tiles expectedOutput = Tiles.values()[0];
        Tiles actualOutput = Tiles.genTile(SingleTile.P0, SingleTile.P0);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGenTile_FromOrdinalValue() {
        Tiles expectedOutput = Tiles.values()[0];
        Tiles actualOutput = Tiles.genTile(13, 13);
        assertEquals(expectedOutput, actualOutput);
    }

}
