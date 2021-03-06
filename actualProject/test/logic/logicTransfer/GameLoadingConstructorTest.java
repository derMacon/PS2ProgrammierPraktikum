package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.token.Domino;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;
import other.FakeGUI;

import static org.junit.Assert.*;


public class GameLoadingConstructorTest {
    @Test
    public void testStringConstructor_GetBoard() {
        String testGameRepresentation =
                "<Spielfeld 1>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld >\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 P1\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld 3>\n" +
                        "-- -- -- P1 --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld 4>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC P1 --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Bänke>\n" +
                        "0 H1P0,2 P0O1,3 I1P0\n" +
                        "- P0P0,- A0A0,1 H0A0,- P1H0\n" +
                        "<Beutel>\n" +
                        "P0P0,P0P0,A1H0,I2P0";

        Game gameTestingConstr = new Game(new FakeGUI(), testGameRepresentation);

        // - actual tests -

        // Human player board
        SingleTile[][] expectedCells = new SingleTile[][]{
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.H1, SingleTile.CC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.P0, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC}
        };

        SingleTile[][] actualCells = gameTestingConstr.getPlayers()[0].getBoard().getCells();
        assertArrayEquals(expectedCells[0], actualCells[0]);
        assertArrayEquals(expectedCells[1], actualCells[1]);
        assertArrayEquals(expectedCells[2], actualCells[2]);
        assertArrayEquals(expectedCells[3], actualCells[3]);

        // Bot 3 board
        expectedCells = new SingleTile[][]{
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.H1, SingleTile.CC, SingleTile.EC, SingleTile.EC},
                {SingleTile.P0, SingleTile.P0, SingleTile.P1, SingleTile.EC, SingleTile.EC},
                {SingleTile.EC, SingleTile.I1, SingleTile.EC, SingleTile.EC, SingleTile.EC}
        };

        actualCells = gameTestingConstr.getPlayers()[3].getBoard().getCells();
        assertArrayEquals(expectedCells[0], actualCells[0]);
        assertArrayEquals(expectedCells[1], actualCells[1]);
        assertArrayEquals(expectedCells[2], actualCells[2]);
        assertArrayEquals(expectedCells[3], actualCells[3]);

        assertEquals(4, gameTestingConstr.getNumberOfPlayers());
        assertEquals(4, gameTestingConstr.getCurrentRoundBank().getBankSize());
        assertEquals(4, gameTestingConstr.getNextRoundBank().getBankSize());
    }

    @Test
    public void testStringConstructor_GetBank() {
        String testGameRepresentation =
                "<Spielfeld 1>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 P1\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld>\n" +
                        "-- -- -- P1 --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC P1 --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Bänke>\n" +
                        "0 H1P0,2 P0O1,3 I1P0\n" +
                        "- P0P0,- A0A0,1 H0A0,- P1H0" +
                        "<Beutel>\n" +
                        "P0P0,P0P0,A1H0,I2P0";
        Game gameTestingConstr = new Game(new FakeGUI(), testGameRepresentation);

        // banks initialized
        assertNotNull(gameTestingConstr.getCurrentRoundBank().getEntries());
        assertNotNull(gameTestingConstr.getNextRoundBank().getEntries());

        // current round bank
        Bank outputCurrentBank = gameTestingConstr.getCurrentRoundBank();
        // slot 1
        Domino dom0Actual = outputCurrentBank.getDomino(0);
        assertNull(dom0Actual);
        assertNull(outputCurrentBank.getSelectedPlayer(0));
        // slot 2
        Domino dom1Expected = new Domino(Tiles.genTile(SingleTile.H1, SingleTile.P0));
        Domino dom1Actual = outputCurrentBank.getDomino(1);
        assertEquals(dom1Expected, dom1Actual);
        assertEquals(0, outputCurrentBank.getSelectedPlayer(1).getIdxInPlayerArray());
        // slot 3
        Domino dom2Expected = new Domino(Tiles.genTile(SingleTile.P0, SingleTile.O1));
        Domino dom2Actual = outputCurrentBank.getDomino(2);
        assertEquals(dom2Expected, dom2Actual);
        assertEquals(2, outputCurrentBank.getSelectedPlayer(2).getIdxInPlayerArray());
        // slot 4
        Domino dom3Expected = new Domino(Tiles.genTile(SingleTile.I1, SingleTile.P0));
        Domino dom3Actual = outputCurrentBank.getDomino(3);
        assertEquals(dom3Expected, dom3Actual);
        assertEquals(3, outputCurrentBank.getSelectedPlayer(3).getIdxInPlayerArray());
    }

    @Test
    public void testStringConstructor_GetStack() {
        String testGameRepresentation =
                "<Spielfeld 1>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld 2>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 P1\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld 3>\n" +
                        "-- -- -- P1 --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Spielfeld 4>\n" +
                        "-- -- -- -- --\n" +
                        "-- -- H1 P0 --\n" +
                        "-- -- CC P1 --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "<Bänke>\n" +
                        "0 H1P0,2 P0O1,3 I1P0\n" +
                        "- P0P0,- A0A0,1 H0A0,- P1H0" +
                        "<Beutel>\n" +
                        "P0P0,P0P0,A1H0,I2P0";
        Game gameTestingConstr = new Game(new FakeGUI(), testGameRepresentation);

        assertEquals(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.P0)), gameTestingConstr.getStack().get(0));
        assertEquals(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.P0)), gameTestingConstr.getStack().get(1));
        assertEquals(new Domino(Tiles.genTile(SingleTile.A1, SingleTile.H0)), gameTestingConstr.getStack().get(2));
        assertEquals(new Domino(Tiles.genTile(SingleTile.I2, SingleTile.P0)), gameTestingConstr.getStack().get(3));
    }

}
