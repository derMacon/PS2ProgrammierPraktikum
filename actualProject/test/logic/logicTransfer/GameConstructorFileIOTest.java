package logic.logicTransfer;

import static org.junit.Assert.*;

import logic.bankSelection.Entry;
import logic.dataPreservation.Loader;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.playerTypes.HumanPlayer;
import org.junit.Assert;
import other.FakeGUI;
import logic.bankSelection.Bank;
import logic.dataPreservation.Logger;
import logic.logicTransfer.Game;
import logic.token.Domino;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameConstructorFileIOTest {

    @Test
    public void testFileInputConstructor() throws IOException, WrongSyntaxException {
        FakeGUI fakeGui = new FakeGUI();
//        String fileInput = Loader.openGivenFile(new File("test1.txt"));
        Game game = TestToolkit.read("val_test2WithComment");

        // Boards
        Board expBoard1 = new Board(
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- CC P0 S2\n" +
                "-- -- I2 P0 --\n" +
                "-- -- -- -- --\n");
        Board expBoard2 = new Board(
            "-- -- P0 -- --\n" +
                    "-- -- I3 -- --\n" +
                    "A0 S1 CC -- --\n" +
                    "-- -- -- -- --\n" +
                    "-- -- -- -- --\n");
        Board expBoard3 = new Board(
                "-- -- O0 -- --\n" +
                "-- -- I2 -- --\n" +
                "A0 S2 CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n");
        Board expBoard4 = new Board(
                "P0 O1 O0 -- --\n" +
                "-- -- I2 -- --\n" +
                "-- -- CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n");
        assertEquals(expBoard1, game.getPlayers()[0].getBoard());
        assertEquals(expBoard2, game.getPlayers()[1].getBoard());
        assertEquals(expBoard3, game.getPlayers()[2].getBoard());
        assertEquals(expBoard4, game.getPlayers()[3].getBoard());

        // banks
        Player p0 = new HumanPlayer(fakeGui, 0, expBoard1);
        Player p1 = new HumanPlayer(fakeGui, 1, expBoard2);
        Player p2 = new HumanPlayer(fakeGui, 2, expBoard3);
        Player p3 = new HumanPlayer(fakeGui, 3, expBoard4);
//        1 A0S1,3 P0O1,0 S0O1,2 I1P0
//        3 A1H0,- A1H0,- A1H0,1 P0S1
        Entry[] testBankInput = new Entry[] {
                null, null,
                new Entry(new Domino(Tiles.S0O1_Val39), p0),
                new Entry(new Domino(Tiles.I1P0_Val40), p2)
        };
        Bank expCurrentBank = new Bank(testBankInput, new Random());
        Bank actCurrentBank = game.getCurrentRoundBank();
        assertEquals(expCurrentBank, actCurrentBank);

        // Stack
        List<Domino> expStack = Arrays.asList( new Domino[] {
                new Domino(Tiles.fromString("P0P0")),
                new Domino(Tiles.fromString("P0P0"))
        });
        List<Domino> stack = game.getStack();
        Assert.assertEquals(expStack, game.getStack());
    }

    @Test
    public void testFileNoStack() throws WrongSyntaxException, IOException {
        FakeGUI fakeGui = new FakeGUI();
//        String fileInput = Loader.openGivenFile(new File("test1.txt"));
//        String fileInput = Loader.openGivenFile(new File("test3NoStack.txt"));
        Game game = TestToolkit.read("val_test3NoStack");
//        Game game = new Game(fakeGui, fileInput);

        // Boards
        Board expBoard1 = new Board(
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- CC P0 S2\n" +
                "-- -- I2 P0 --\n" +
                "-- -- -- -- --\n");
        Board expBoard2 = new Board(
            "-- -- P0 -- --\n" +
                    "-- -- I3 -- --\n" +
                    "A0 S1 CC -- --\n" +
                    "-- -- -- -- --\n" +
                    "-- -- -- -- --\n");
        Board expBoard3 = new Board(
                "-- -- O0 -- --\n" +
                "-- -- I2 -- --\n" +
                "A0 S2 CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n");
        Board expBoard4 = new Board(
                "P0 O1 O0 -- --\n" +
                "-- -- I2 -- --\n" +
                "-- -- CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n");
        assertEquals(expBoard1, game.getPlayers()[0].getBoard());
        assertEquals(expBoard2, game.getPlayers()[1].getBoard());
        assertEquals(expBoard3, game.getPlayers()[2].getBoard());
        assertEquals(expBoard4, game.getPlayers()[3].getBoard());

        // banks
        Player p0 = new HumanPlayer(fakeGui, 0, expBoard1);
        Player p1 = new HumanPlayer(fakeGui, 1, expBoard2);
        Player p2 = new HumanPlayer(fakeGui, 2, expBoard3);
        Player p3 = new HumanPlayer(fakeGui, 3, expBoard4);
//        1 A0S1,3 P0O1,0 S0O1,2 I1P0
//        3 A1H0,- A1H0,- A1H0,1 P0S1
        Entry[] testBankInput = new Entry[] {
                null,
                null,
                new Entry(new Domino(Tiles.S0O1_Val39), p0),
                new Entry(new Domino(Tiles.I1P0_Val40), p2)
        };
        Bank expCurrentBank = new Bank(testBankInput, new Random());
        Bank actCurrentBank = game.getCurrentRoundBank();
        assertEquals(expCurrentBank, actCurrentBank);

        // Stack
        List<Domino> expStack = Arrays.asList( new Domino[] {
                new Domino(Tiles.fromString("P0P0")),
                new Domino(Tiles.fromString("P0P0"))
        });
        assertTrue(game.getStack().isEmpty());
    }
}
