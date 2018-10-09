package TestPackages.logic.logicTransfer;

import TestPackages.other.FakeGUI;
import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.logicTransfer.GUIConnector;
import logic.logicTransfer.Game;
import logic.playerTypes.DefaultAIPlayer;
import logic.playerTypes.HumanPlayer;
import logic.playerState.Player;
import logic.playerTypes.PlayerType;
import logic.token.Domino;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class GameTest {

    // --- 4. Constructor - tests ---

    // --- Constructor used by FXMLController ---
    @Test
    public void testConstuctor_gui_int_int_int_BeforeStart() {
        Game game = new Game(new FakeGUI(), 2);

        assertEquals(2, game.getNumberOfPlayers());
        assertEquals(2, game.getCurrentRoundBank().getBankSize());
        assertEquals(2, game.getNextRoundBank().getBankSize());

        // banks initialized
        assertNotNull(game.getCurrentRoundBank().getEntries());
        assertNotNull(game.getNextRoundBank().getEntries());
        // but no content yet
        assertTrue(game.getCurrentRoundBank().isEmpty());
        assertTrue(game.getNextRoundBank().isEmpty());
        assertNull(game.getCurrentRoundBank().getEntries()[0]);
        assertNull(game.getNextRoundBank().getEntries()[0]);

        // players array is initialized
        assertNotNull(game.getPlayers());
        // but content not initialized until call of method game.startGame()
        assertNull(game.getPlayers()[0]);
        assertNull(game.getPlayers()[1]);
    }

    @Test
    public void testConstructor_gui_int_int_int_AfterStart() {
        Game game = new Game(new FakeGUI(), 2);
        game.startGame(new PlayerType[]{PlayerType.HUMAN}, 5, 5);

        // banks initialized -> current bank now with content
        assertFalse(game.getCurrentRoundBank().isEmpty());
        assertTrue(game.getNextRoundBank().isEmpty());
        // two dominos drawn from stack (2 Players -> 2 doms needed)
        assertEquals(46, game.getStack().size());
        assertNotNull(game.getCurrentRoundBank().getEntries()[0]);
        assertTrue(game.getNextRoundBank().isEmpty());
    }


    // --- Constructor - only used for testing ---
    @Test
    public void testTestConstructor_gui_players_int_Bank_Bank_Domino_BeforeStart() {
        // Standard Game - Build with testing constructor
        GUIConnector fakeGui = new FakeGUI();

        Player[] players = new Player[4];
        Bank currentBank = new Bank(4);
        Bank nextBank = new Bank(4);
        List<Domino> stack = new LinkedList<>();

        Game gameTestingConstr = new Game(fakeGui, players, 0, currentBank, nextBank, stack, null);

        assertEquals(4, gameTestingConstr.getNumberOfPlayers());
        assertEquals(4, gameTestingConstr.getCurrentRoundBank().getBankSize());
        assertEquals(4, gameTestingConstr.getNextRoundBank().getBankSize());

        // banks initialized
        assertNotNull(gameTestingConstr.getCurrentRoundBank().getEntries());
        assertNotNull(gameTestingConstr.getNextRoundBank().getEntries());
        // but no content yet
        assertTrue(gameTestingConstr.getCurrentRoundBank().isEmpty());
        assertTrue(gameTestingConstr.getNextRoundBank().isEmpty());
        assertNull(gameTestingConstr.getCurrentRoundBank().getEntries()[0]);
        assertNull(gameTestingConstr.getNextRoundBank().getEntries()[0]);

        // stack is empty
        assertEquals(0, gameTestingConstr.getStack().size());

        // players array is initialized
        assertNotNull(gameTestingConstr.getPlayers());
        // but content not initialized until call of method game.startGame()
        assertNull(gameTestingConstr.getPlayers()[0]);
        assertNull(gameTestingConstr.getPlayers()[1]);
        assertNull(gameTestingConstr.getPlayers()[2]);
        assertNull(gameTestingConstr.getPlayers()[3]);
    }

    @Test
    public void testTestConstructor_gui_players_int_Bank_Bank_Domino_AfterStart() {
        // - Standard Game (after start game) - Build with testing constructor -

        GUIConnector fakeGui = new FakeGUI();
        int sizeX = 5;
        int sizeY = 5;

        Player pl1 = new HumanPlayer(fakeGui, 0, sizeX, sizeY);
        Player pl2 = new DefaultAIPlayer(fakeGui, 1, sizeX, sizeY);
        Player pl3 = new DefaultAIPlayer(fakeGui, 2, sizeX, sizeY);
        Player pl4 = new DefaultAIPlayer(fakeGui, 3, sizeX, sizeY);
        Player[] players = new Player[]{pl1, pl2, pl3, pl4};

        Entry entry1 = new Entry(new Domino(Tiles.P0H0_Val13));
        Entry entry2 = new Entry(new Domino(Tiles.A0A0_Val7));
        Entry entry3 = new Entry(new Domino(Tiles.A0S2_Val42));
        Entry entry4 = new Entry(new Domino(Tiles.A0S1_Val37));
        Entry[] entries = new Entry[]{entry1, entry2, entry3, entry4};
        Bank currentBank = new Bank(entries, new Random());
        Bank nextBank = new Bank(4);

        List<Domino> stack = new LinkedList<>();
        stack = Domino.fill(stack);

        Game gameTestingConstr = new Game(fakeGui, players, 0, currentBank, nextBank, stack, null);

        // - actual tests -

        assertEquals(4, gameTestingConstr.getNumberOfPlayers());
        assertEquals(4, gameTestingConstr.getCurrentRoundBank().getBankSize());
        assertEquals(4, gameTestingConstr.getNextRoundBank().getBankSize());

        // banks initialized
        assertNotNull(gameTestingConstr.getCurrentRoundBank().getEntries());
        assertNotNull(gameTestingConstr.getNextRoundBank().getEntries());
        // now with content
        assertArrayEquals(entries, gameTestingConstr.getCurrentRoundBank().getEntries());

        // filled stack
        assertEquals(48, gameTestingConstr.getStack().size());
        // players array is initialized
        assertArrayEquals(new Player[]{pl1, pl2, pl3, pl4}, gameTestingConstr.getPlayers());
        // now with content
        assertEquals(pl1.getBoard(), gameTestingConstr.getPlayers()[0].getBoard());
        assertEquals(pl2.getBoard(), gameTestingConstr.getPlayers()[1].getBoard());
        assertEquals(pl3.getBoard(), gameTestingConstr.getPlayers()[2].getBoard());
        assertEquals(pl4.getBoard(), gameTestingConstr.getPlayers()[3].getBoard());
    }



}
