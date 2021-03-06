package logic.bankSelection;

import logic.differentPlayerTypes.DefaultAIPlayer;
import logic.differentPlayerTypes.HumanPlayer;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Player;
import logic.randomizer.PseudoRandAlwaysHighestVal;
import logic.randomizer.PseudoRandZeroResidueClass;
import logic.token.Domino;
import logic.token.Tiles;
import org.junit.Test;
import other.FakeGUI;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class BankTest {

    public static final int DEFAULT_TEST_BANK_SIZE = 4;

    //<editor-fold defaultstate="collapsed" desc="Helping Method - Setting up tests">

    /**
     * Fills a given list with the stack of dominos and generates a bank from
     * the first len dominos of the stack. Dominos are NOT randomized.
     *
     * @param len   size of the bank
     * @param stack input list serving as the stack
     * @return bank with the first 4 dominos from the newly filled stack.
     */
    private Bank genBankFromStack(int len, List<Domino> stack) {
        stack = Domino.fill(stack);
        Entry[] entries = new Entry[len];
        for (int i = 0; i < len; i++) {
            entries[i] = new Entry(stack.get(i));
        }
        return new Bank(entries, new Random());
    }

    private Bank genBankFromStack(List<Domino> stack) {
        //<editor-fold defaultstate="collapsed" desc="Alternative">
//        stack = Domino.fill(stack);
//        Entry[] entries = new Entry[]{new Entry(stack.get(0)), new Entry(stack.get(1)), new Entry(stack.get(2)),
// new Entry(stack.get(3))};
//        Bank bank = new Bank(entries);
//        return bank;
        //</editor-fold>
        return genBankFromStack(DEFAULT_TEST_BANK_SIZE, stack);
    }

    private Bank genBankFromStack(int len) {
        return genBankFromStack(len, new LinkedList<Domino>());
    }

    private Bank genBankFromStack() {
        return genBankFromStack(DEFAULT_TEST_BANK_SIZE, new LinkedList<Domino>());
    }
    //</editor-fold>

    // --- Constructor (String input) ---
    @Test(expected = AssertionError.class)
    public void testConstructor_NullParam1() {
        GUIConnector fakeGui = new FakeGUI();
        List<Player> players = Arrays.asList(new Player[]{
                new HumanPlayer(fakeGui, 0, 5, 5),
                new DefaultAIPlayer(fakeGui, 1, 5, 5),
                new DefaultAIPlayer(fakeGui, 2, 5, 5),
                new DefaultAIPlayer(fakeGui, 3, 5, 5),
        });
        new Bank(null, players, new Random());
    }

    @Test(expected = AssertionError.class)
    public void testConstructor_NullParam2() {
        GUIConnector fakeGui = new FakeGUI();
        new Bank("<Bänke>\n" +
                "0 S0O1,2 I1P0\n" +
                "3 A1H0,- A1H0,- A1H0,1 P0S1",
                null, new Random());
    }

    @Test(expected = AssertionError.class)
    public void testConstructor_NullParam3() {
        GUIConnector fakeGui = new FakeGUI();
        List<Player> players = Arrays.asList(new Player[]{
                new HumanPlayer(fakeGui, 0, 5, 5),
                new DefaultAIPlayer(fakeGui, 1, 5, 5),
                new DefaultAIPlayer(fakeGui, 2, 5, 5),
                new DefaultAIPlayer(fakeGui, 3, 5, 5),
        });
        new Bank("<Bänke>\n" +
                "0 S0O1,2 I1P0\n" +
                "3 A1H0,- A1H0,- A1H0,1 P0S1", players, null);
    }

    @Test
    public void testConstructor_CurrBankHalfFull() {
        GUIConnector fakeGui = new FakeGUI();
        List<Player> players = Arrays.asList(new Player[]{
                new HumanPlayer(fakeGui, 0, 5, 5),
                new DefaultAIPlayer(fakeGui, 1, 5, 5),
                new DefaultAIPlayer(fakeGui, 2, 5, 5),
                new DefaultAIPlayer(fakeGui, 3, 5, 5),
        });
        Bank bank = new Bank("0 S0O1,2 I1P0", players, new Random());
        Entry[] expEntries = new Entry[]{
                null, null,
                new Entry(new Domino(Tiles.S0O1_Val39), players.get(0)),
                new Entry(new Domino(Tiles.I1P0_Val40), players.get(2))
        };
        assertArrayEquals(expEntries, bank.getEntries());
    }

    @Test
    public void testConstructor_CurrBankFull() {
        GUIConnector fakeGui = new FakeGUI();
        List<Player> players = Arrays.asList(new Player[]{
                new HumanPlayer(fakeGui, 0, 5, 5),
                new DefaultAIPlayer(fakeGui, 1, 5, 5),
                new DefaultAIPlayer(fakeGui, 2, 5, 5),
                new DefaultAIPlayer(fakeGui, 3, 5, 5),
        });
        Bank bank = new Bank("0 S0O1,2 I1P0,3 P0P0,1 P0P0", players, new Random());
        Entry[] expEntries = new Entry[]{
                new Entry(new Domino(Tiles.S0O1_Val39), players.get(0)),
                new Entry(new Domino(Tiles.I1P0_Val40), players.get(2)),
                new Entry(new Domino(Tiles.P0P0_Val2), players.get(3)),
                new Entry(new Domino(Tiles.P0P0_Val2), players.get(1))
        };
        assertArrayEquals(expEntries, bank.getEntries());
    }

    @Test
    public void testConstructor_OneElem() {
        GUIConnector fakeGui = new FakeGUI();
        List<Player> players = Arrays.asList(new Player[]{
                new HumanPlayer(fakeGui, 0, 5, 5),
                new DefaultAIPlayer(fakeGui, 1, 5, 5),
                new DefaultAIPlayer(fakeGui, 2, 5, 5),
                new DefaultAIPlayer(fakeGui, 3, 5, 5),
        });
        Bank bank = new Bank("0 S0O1", players, new Random());
        Entry[] expEntries = new Entry[]{
                null, null, null,
                new Entry(new Domino(Tiles.S0O1_Val39), players.get(0))
        };
        assertArrayEquals(expEntries, bank.getEntries());
    }

    @Test
    public void testConstructor_NoElems() {
        GUIConnector fakeGui = new FakeGUI();
        List<Player> players = Arrays.asList(new Player[]{
                new HumanPlayer(fakeGui, 0, 5, 5),
                new DefaultAIPlayer(fakeGui, 1, 5, 5),
                new DefaultAIPlayer(fakeGui, 2, 5, 5),
                new DefaultAIPlayer(fakeGui, 3, 5, 5),
        });
        Bank bank = new Bank("", players, new Random());
        Entry[] expEntries = new Entry[]{
                null, null, null, null
        };
        assertArrayEquals(expEntries, bank.getEntries());
    }


    // --- select / getSelectedPlayer ---
    @Test
    public void testSelectGetSelectedPlayer_SelectValidDomino() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 1, 5, 5);
        bank.selectEntry(fstPlayer, 0);

        assertSame(fstPlayer, bank.getSelectedPlayer(0));
    }

    @Test(expected = AssertionError.class)
    public void testSelect_NullParam() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        bank.selectEntry(null, 0);
    }

    @Test
    public void testGetSelectedPlayer_GettingNotSelectedDomino() {
        List<Domino> stack = new LinkedList<>();
        genBankFromStack(stack);
        Entry[] entries = new Entry[]{new Entry(stack.get(0)), null};
        Bank bank = new Bank(entries, new Random());
        assertNull(bank.getSelectedPlayer(0));
        assertNull(bank.getSelectedPlayer(1));
    }

    // --- isNotSelected ---
    @Test
    public void testIsNotSelected_ValidIdx() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 1, 5, 5);
        bank.selectEntry(fstPlayer, 0);

        assertFalse(bank.isNotSelected(0));
        assertTrue(bank.isNotSelected(1));
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelected_InvalidIdxTooSmall() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 1, 5, 5);
        bank.selectEntry(fstPlayer, -1);
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelected_InvalidIdxTooBig() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 1, 5, 5);
        bank.selectEntry(fstPlayer, 4);
    }

    // --- getDomino / getDominos ---
    @Test
    public void testGetDomino_Valid() {
        List<Domino> stack = new LinkedList<>();
        Bank bank = genBankFromStack(stack);
        assertEquals(stack.get(0), bank.getDomino(0));
        assertEquals(stack.get(1), bank.getDomino(1));
        assertEquals(stack.get(2), bank.getDomino(2));
        assertEquals(stack.get(3), bank.getDomino(3));
    }

    @Test
    public void testGetDomino_Invalid() {
        Bank bank = new Bank(4);
        assertNull(bank.getDomino(0));
        assertNull(bank.getDomino(-1));
        assertNull(bank.getDomino(4));
    }

    @Test
    public void testGetAllDominos_Filled() {
        List<Domino> stack = new LinkedList<>();
        Bank bank = genBankFromStack(stack);
        Domino[] expOutput = new Domino[]{stack.get(0), stack.get(1), stack.get(2), stack.get(3)};
        assertArrayEquals(expOutput, bank.getAllDominos());
    }

    @Test
    public void testGetAllDominos_Empty() {
        Bank bank = new Bank(4);
        Domino[] expOutput = new Domino[4];
        assertArrayEquals(expOutput, bank.getAllDominos());
    }

    @Test
    public void testGetAllDominos_Mixed() {
        Domino domino = new Domino(Tiles.A0A0_Val7);
        Entry[] entries = new Entry[]{null, new Entry(domino)};
        Bank bank = new Bank(entries, new Random());
        Domino[] expOutput = new Domino[]{null, domino};
        assertArrayEquals(expOutput, bank.getAllDominos());
    }

    // --- getPlayerSelectedDomino ---
    @Test
    public void testGetPlayerSelectedDomino_MixedBank() {
        List<Domino> stack = new LinkedList<>();
        genBankFromStack(stack);
        Entry[] entries = new Entry[]{new Entry(stack.get(0)), new Entry(stack.get(1)), null};
        Bank bank = new Bank(entries, new Random());
        Player player = new DefaultAIPlayer(new FakeGUI(), 1, 5, 5);
        // Domino selected by no player
        Domino outputDom = bank.getPlayerSelectedDomino(player);
        assertNull(outputDom);

        // Domino selected by given player
        bank.selectEntry(player, 0);
        assertSame(stack.get(0), bank.getPlayerSelectedDomino(player));
    }

    @Test(expected = AssertionError.class)
    public void testGetPlayerSelectedDomino_NullParam() {
        new Bank(4).getPlayerSelectedDomino(null);
    }

    // --- drawFromStack ---
    @Test
    public void testDrawFromStack_DrawFullStack() {
        List<Domino> stack = new LinkedList<>();
        stack = Domino.fill(stack);
        Bank bank = new Bank(Tiles.TILES_CNT);
        bank.randomlyDrawFromStack(stack);
        Domino[] domOnBank = bank.getAllDominos();
        boolean containsAllStackDoms = true;
        List<Domino> freshStack = Domino.fill(new LinkedList<>());
        for (Domino currDom : domOnBank) {
            containsAllStackDoms &= freshStack.contains(currDom);
        }
        assertEquals(Tiles.TILES_CNT, domOnBank.length);
        assertTrue(containsAllStackDoms);
    }

    @Test(expected = AssertionError.class)
    public void testDrawFromStack_NullParam() {
        new Bank(1).randomlyDrawFromStack(null);
    }

    // --- fill ---
    @Test
    public void testFill_Valid() {
        List<Domino> stack = new LinkedList<>();
        Bank bank = genBankFromStack(stack);

        bank.fill(stack.get(4), 3);
        assertEquals(stack.get(4), bank.getDomino(3));
    }

    @Test
    public void testFill_NullParam() {
        List<Domino> stack = new LinkedList<>();
        Bank bank = genBankFromStack(stack);
        Entry fstEntry = bank.getEntries()[0];
        // before
        assertSame(stack.get(0), fstEntry.getDomino());
        assertNull(fstEntry.getSelectedPlayer());

        bank.fill(null, 0);
        // after
        fstEntry = bank.getEntries()[0];
        assertNull(fstEntry.getDomino());
        assertNull(fstEntry.getSelectedPlayer());
    }

    @Test(expected = AssertionError.class)
    public void testFill_InvalidIdxSmall() {
        new Bank(4).fill(new Domino(Tiles.A0A0_Val7), -1);
    }

    @Test(expected = AssertionError.class)
    public void testFill_InvalidIdxLarge() {
        new Bank(4).fill(new Domino(Tiles.A0A0_Val7), 4);
    }

    // --- copyWithoutSelection ---
    @Test
    public void testCopy_Valid() {
        Bank bank = genBankFromStack(1);
        // new reference
        assertNotSame(bank, bank.copy());
        // new entries
        assertNotSame(bank.getEntries(), bank.copy().getEntries());
        // new domino array reference
        assertNotSame(bank.getAllDominos(), bank.copy().getAllDominos());
        // same domino array values
        assertArrayEquals(bank.getAllDominos(), bank.copy().getAllDominos());
        // new reference for actual dominos
        assertNotSame(bank.getAllDominos()[0], bank.copy().getAllDominos()[0]);
    }

    @Test
    public void testCopy_EmptyBank() {
        Bank bank = new Bank(1);
        assertNotSame(bank, bank.copy());
        assertNotSame(bank.getEntries(), bank.copy().getEntries());
        assertNotSame(bank.getAllDominos(), bank.copy().getAllDominos());
        assertArrayEquals(bank.getAllDominos(), bank.copy().getAllDominos());
        assertSame(bank.getAllDominos()[0], bank.copy().getAllDominos()[0]);
    }

    @Test
    public void testCopy_MixedBank() {
        List<Domino> stack = new LinkedList<>();
        genBankFromStack(stack);
        Entry[] entries = new Entry[]{new Entry(stack.get(0)), new Entry(stack.get(1)), null};
        Bank bank = new Bank(entries, new Random());
        assertNotSame(bank, bank.copy());
        assertNotSame(bank.getEntries(), bank.copy().getEntries());
        assertNotSame(bank.getAllDominos(), bank.copy().getAllDominos());
        assertArrayEquals(bank.getAllDominos(), bank.copy().getAllDominos());
        assertNotSame(bank.getAllDominos()[0], bank.copy().getAllDominos()[0]);
    }

    // --- randomlyDrawFromStack ---
    @Test
    public void testRandomlyDrawFromStack_CheckLeftoverStack() {
        List<Domino> expectedOutput = Domino.fill(new LinkedList<>());
        // expected left over stack
        int fstValue = expectedOutput.remove(0).getTile().getValue();
        int sndValue = expectedOutput.remove(3).getTile().getValue();
        int thrdValue = expectedOutput.remove(6).getTile().getValue();
        int frthValue = expectedOutput.remove(9).getTile().getValue();
        List<Domino> bankInputStack = Domino.fill(new LinkedList<>());
        Bank bank = new Bank(new Entry[4], new PseudoRandZeroResidueClass(3));
        List<Domino> actualOutput = bank.randomlyDrawFromStack(bankInputStack);
        assertArrayEquals(expectedOutput.toArray(new Domino[0]), actualOutput.toArray(new Domino[0]));
    }

    @Test
    public void testRandomlyDrawFromStack_CheckBankValues() {
        List<Domino> expectedOutput = Domino.fill(new LinkedList<>());
        int fstValue = expectedOutput.remove(0).getTile().getValue();
        int sndValue = expectedOutput.remove(3).getTile().getValue();
        int thrdValue = expectedOutput.remove(6).getTile().getValue();
        int frthValue = expectedOutput.remove(9).getTile().getValue();
        List<Domino> bankInputStack = Domino.fill(new LinkedList<>());
        Bank bank = new Bank(new Entry[4], new PseudoRandZeroResidueClass(3));
        List<Domino> actualStack = bank.randomlyDrawFromStack(bankInputStack);
        assertEquals(fstValue, bank.getSpecificEntry(0).getDomino().getTile().getValue());
        assertEquals(sndValue, bank.getSpecificEntry(1).getDomino().getTile().getValue());
        assertEquals(thrdValue, bank.getSpecificEntry(2).getDomino().getTile().getValue());
        assertEquals(frthValue, bank.getSpecificEntry(3).getDomino().getTile().getValue());
    }

    @Test
    public void testRandomlyDrawFromStack_CorrectOrder() {
        List<Domino> overallStack = Domino.fill(new LinkedList<>());
        // expected dominos in correct order
        Domino[] dominos = new Domino[]{
                overallStack.get(overallStack.size() - 4),
                overallStack.get(overallStack.size() - 3),
                overallStack.get(overallStack.size() - 2),
                overallStack.get(overallStack.size() - 1),
        };
        Bank bank = new Bank(new Entry[4], new PseudoRandAlwaysHighestVal());
        overallStack = bank.randomlyDrawFromStack(overallStack);
        Domino[] bankDom = bank.getAllDominos();
        // check if right dominos were selected 
        assertFalse(overallStack.contains(bankDom[0]));
        assertFalse(overallStack.contains(bankDom[1]));
        assertFalse(overallStack.contains(bankDom[2]));
        assertFalse(overallStack.contains(bankDom[3]));
        // check order
        assertEquals(dominos[0], bankDom[0]);
        assertEquals(dominos[1], bankDom[1]);
        assertEquals(dominos[2], bankDom[2]);
        assertEquals(dominos[3], bankDom[3]);
    }

    // --- equals ---
    @Test
    public void testEquals_DifferentSizes() {
        assertFalse(new Bank(4).equals(new Bank(3)));
        assertFalse(new Bank(3).equals(new Bank(4)));
    }

    @Test
    public void testEquals_OneBankContainsEmptySlots() {
        GUIConnector fakeGui = new FakeGUI();
        List<Player> players = Arrays.asList(new Player[]{
                new DefaultAIPlayer(fakeGui, 0, 1, 2),
                new DefaultAIPlayer(fakeGui, 1, 1, 2),
                new DefaultAIPlayer(fakeGui, 2, 1, 2),
                new DefaultAIPlayer(fakeGui, 3, 1, 2)
        });

        Bank bank1 = new Bank("0 S0O1,2 I1P0", players, new Random());
        Bank bank2 = new Bank("0 S0O1,2 I1P0,1 P0S1", players, new Random());

        Entry[] entries = new Entry[]{
                null, null,
                new Entry(new Domino(Tiles.S0O1_Val39), players.get(0)),
                new Entry(new Domino(Tiles.I1P0_Val40), players.get(2))
        };
        Bank bank3 = new Bank(entries, new Random());

        assertEquals(bank1, bank3);
        assertNotEquals(bank1, bank2);
    }

}
