package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.differentPlayerTypes.DefaultAIPlayer;
import logic.differentPlayerTypes.HumanPlayer;
import logic.differentPlayerTypes.PlayerType;
import logic.randomizer.PseudoRandAlwaysHighestVal;
import logic.token.Domino;
import logic.token.Tiles;
import org.junit.Test;
import other.FakeGUI;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ValidFileReadTests {

    @Test
    public void test_newGame_noNextBankYet() throws IOException {
        Game expOutput = new Game(new FakeGUI(), 4);
        // test only possible if random obj. in banks are pseudorandom
        expOutput.getCurrentRoundBank().setRand(new PseudoRandAlwaysHighestVal());
        expOutput.startGame(new PlayerType[]{PlayerType.HUMAN, PlayerType.DEFAULT, PlayerType.DEFAULT,
                PlayerType.DEFAULT}, 5, 5);
        Game actOutput = TestToolkit.read("val_newGame_noNextBankYet");
        assertEquals(expOutput, actOutput);
        TestToolkit.writeAndAssert(actOutput, "exp_newGame_noNextBankYet");
    }


    @Test
    public void test_fullCurrBank_fullStack() throws IOException {
        // setting up game
        GUIConnector fakeGui = new FakeGUI();
        Board board1 = new Board(
                "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- CC P0 S2\n" +
                        "-- -- I2 P0 --\n" +
                        "-- -- -- -- --");
        Board board2 = new Board(
                "-- -- P0 -- --\n" +
                        "-- -- I3 -- --\n" +
                        "A0 S1 CC -- --\n" +
                        "-- S1 P0 -- --\n" +
                        "-- -- -- -- --");
        Board board3 = new Board(
                "-- -- O0 -- --\n" +
                        "-- -- I2 I1 P0\n" +
                        "A0 S2 CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --");
        Board board4 = new Board(
                "P0 O1 O0 -- --\n" +
                        "-- -- I2 -- --\n" +
                        "A1 H0 CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --");

        Player[] expPlayers = new Player[]{
                new HumanPlayer(fakeGui, 0, board1),
                new DefaultAIPlayer(fakeGui, 1, board2),
                new DefaultAIPlayer(fakeGui, 2, board3),
                new DefaultAIPlayer(fakeGui, 3, board4)
        };

        Entry[] nextRoundEntries = new Entry[]{
                new Entry(new Domino(Tiles.P0P0_Val1)),
                new Entry(new Domino(Tiles.P0P0_Val2)),
                new Entry(new Domino(Tiles.S0O1_Val39)),
                new Entry(new Domino(Tiles.I1P0_Val40))
        };
        Bank expNextBank = new Bank(nextRoundEntries, new Random());

        Entry[] currRoundEntries = new Entry[]{
                new Entry(new Domino(Tiles.A1H0_Val32), expPlayers[3]),
                new Entry(new Domino(Tiles.A1H0_Val33), expPlayers[0]),
                new Entry(new Domino(Tiles.A1H0_Val34), expPlayers[2]),
                new Entry(new Domino(Tiles.P0S1_Val36), expPlayers[1])
        };
        Bank expCurrBank = new Bank(currRoundEntries, new Random());

        List<Domino> stack = new LinkedList<>();
        stack.add(new Domino(Tiles.P0P0_Val1));
        stack.add(new Domino(Tiles.P0P0_Val2));
        stack.add(new Domino(Tiles.H0H0_Val3));
        stack.add(new Domino(Tiles.H0H0_Val3));

        Game expOutput = new Game(new FakeGUI(), expPlayers, 0, expCurrBank, expNextBank,
                stack, null);
        Game actOutput = TestToolkit.read("val_fullCurrBank_fullStack");

        // actual tests
        assertEquals(expOutput, actOutput);
        TestToolkit.writeAndAssert(actOutput, "exp_fullCurrBank_fullStack");
    }

    @Test
    public void test_noStack() throws IOException {
        // setting up game
        GUIConnector fakeGui = new FakeGUI();
        Board board1 = new Board(
                "-- -- -- -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- CC P0 S2\n" +
                        "-- -- I2 P0 --\n" +
                        "-- -- -- -- --");
        Board board2 = new Board(
                "-- -- P0 -- --\n" +
                        "-- -- I3 -- --\n" +
                        "A0 S1 CC -- --\n" +
                        "-- S1 P0 -- --\n" +
                        "-- -- -- -- --");
        Board board3 = new Board(
                "-- -- O0 -- --\n" +
                        "-- -- I2 I1 P0\n" +
                        "A0 S2 CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --");
        Board board4 = new Board(
                "P0 O1 O0 -- --\n" +
                        "-- -- I2 -- --\n" +
                        "A1 H0 CC -- --\n" +
                        "-- -- -- -- --\n" +
                        "-- -- -- -- --");

        Player[] expPlayers = new Player[]{
                new HumanPlayer(fakeGui, 0, board1),
                new DefaultAIPlayer(fakeGui, 1, board2),
                new DefaultAIPlayer(fakeGui, 2, board3),
                new DefaultAIPlayer(fakeGui, 3, board4)
        };

        Entry[] currRoundEntries = new Entry[]{
                null, null,
                new Entry(new Domino(Tiles.S0O1_Val39), expPlayers[0]),
                new Entry(new Domino(Tiles.I1P0_Val40), expPlayers[2])
        };
        Bank expCurrBank = new Bank(currRoundEntries, new Random());

        Entry[] nextRoundEntries = new Entry[]{
                new Entry(new Domino(Tiles.A1H0_Val32), expPlayers[3]),
                new Entry(new Domino(Tiles.A1H0_Val33)),
                new Entry(new Domino(Tiles.A1H0_Val34)),
                new Entry(new Domino(Tiles.P0S1_Val36), expPlayers[1])
        };
        Bank expNextBank = new Bank(nextRoundEntries, new Random());

        Game expOutput = new Game(new FakeGUI(), expPlayers, 0, expCurrBank, expNextBank,
                new LinkedList<>(), null);
        Game actOutput = TestToolkit.read("val_noStack");

        // actual tests
        assertEquals(expOutput, actOutput);
        TestToolkit.writeAndAssert(actOutput, "exp_noStack");
    }

    @Test
    public void test_lastTurnInRotBox() throws IOException {
        // setting up game
        GUIConnector fakeGui = new FakeGUI();
        Board board1 = new Board(
                "-- -- -- -- A1\n" +
                        "P0 -- I1 P0 P0\n" +
                        "H1 CC I2 P0 --\n" +
                        "H0 A1 -- P0 P1\n" +
                        "H0 A0 -- S2 S0");
        Board board2 = new Board(
                "A0 -- P0 P0 H1\n" +
                        "S1 -- I3 -- --\n" +
                        "S0 O2 CC A1 H0\n" +
                        "S1 -- -- -- H0\n" +
                        "P0 P1 O0 -- P1");
        Board board3 = new Board(
                "P0 O1 O0 -- P0\n" +
                        "-- -- I2 -- S0\n" +
                        "A0 S2 CC H1 S0\n" +
                        "A1 -- P0 H1 --\n" +
                        "H0 H1 P0 -- --");
        Board board4 = new Board(
                "P0 O2 O0 O1 S0\n" +
                        "-- -- I2 -- --\n" +
                        "A1 H0 CC A1 P0\n" +
                        "A0 H1 H0 -- P1\n" +
                        "-- -- S0 -- I0");

        Player[] expPlayers = new Player[]{
                new HumanPlayer(fakeGui, 0, board1),
                new DefaultAIPlayer(fakeGui, 1, board2),
                new DefaultAIPlayer(fakeGui, 2, board3),
                new DefaultAIPlayer(fakeGui, 3, board4)
        };

        Entry[] currRoundEntries = new Entry[]{
                new Entry(new Domino(Tiles.P0P0_Val1), expPlayers[0]),
                new Entry(new Domino(Tiles.P0P0_Val2), expPlayers[1]),
                new Entry(new Domino(Tiles.H0H0_Val3), expPlayers[3]),
                new Entry(new Domino(Tiles.H0H0_Val4), expPlayers[2])
        };
        Bank expCurrBank = new Bank(currRoundEntries, new Random());

        Entry[] nextRoundEntries = new Entry[]{
                null, null, null, null
        };
        Bank expNextBank = new Bank(nextRoundEntries, new Random());

        Game expOutput = new Game(new FakeGUI(), expPlayers, 0, expCurrBank, expNextBank,
                new LinkedList<>(), null);
        Game actOutput = TestToolkit.read("val_lastTurnInRotBox");

        // actual tests
        assertEquals(expOutput, actOutput);
    }

}
