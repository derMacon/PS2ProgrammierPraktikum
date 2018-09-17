package TestPackages.logic.logicTransfer;

import TestPackages.other.FakeGUI;
import logic.logicTransfer.Game;
import logic.token.Tiles;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    // --- Constructor used by FXMLController ---
    @Test
    public void testConstuctor_gui_int_int_int_BeforeStart() {
        Game game = new Game(new FakeGUI(), 2,3, 5);

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
        assertNull(game.getPlayersBoard(0));
        assertNull(game.getPlayersBoard(1));
    }

    @Test
    public void testConstructor_gui_int_int_int_AfterStart() {
        Game game = new Game(new FakeGUI(), 2,3, 5);
        game.startGame();

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
    public void testTestConstructor_gui_players_int_Bank_Bank_Domino() {
//        Game game = new Game(new FakeGUI(), )
    }

}
