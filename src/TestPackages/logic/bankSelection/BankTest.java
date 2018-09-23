package TestPackages.logic.bankSelection;

import TestPackages.other.FakeGUI;
import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.playerTypes.DefaultAIPlayer;
import logic.playerState.Player;
import logic.token.Domino;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import logic.token.Tiles;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {

    public static final int DEFAULT_TEST_BANK_SIZE = 4;

    //<editor-fold defaultstate="collapsed" desc="Helping Method - Setting up tests">

    /**
     * Fills a given list with the stack of dominos and generates a bank from the first len dominos of the stack. Dominos
     * are NOT randomized.
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
//        Entry[] entries = new Entry[]{new Entry(stack.get(0)), new Entry(stack.get(1)), new Entry(stack.get(2)), new Entry(stack.get(3))};
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


    // --- select / getSelectedPlayer ---
    @Test
    public void testSelectGetSelectedPlayer_SelectValidDomino() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 5, 5);
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
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 5, 5);
        bank.selectEntry(fstPlayer, 0);

        assertFalse(bank.isNotSelected(0));
        assertTrue(bank.isNotSelected(1));
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelected_InvalidIdxTooSmall() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 5, 5);
        bank.selectEntry(fstPlayer, -1);
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelected_InvalidIdxTooBig() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI(), 5, 5);
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
        Domino domino = new Domino(Tiles.A0_A0_Val7);
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
        Player player = new DefaultAIPlayer(new FakeGUI(), 5, 5);
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
        // only init stack
        genBankFromStack(Tiles.TILES_CNT, stack);
        Bank bank = new Bank(Tiles.TILES_CNT);
        bank.drawFromStack(stack);
        Domino[] domOnBank = bank.getAllDominos();
        boolean containsAllStackDoms = true;
        for (Domino currDom : domOnBank) {
            containsAllStackDoms &= stack.contains(currDom);
        }
        assertEquals(stack.size(), domOnBank.length);
        assertTrue(containsAllStackDoms);
    }


    @Test(expected = AssertionError.class)
    public void testDrawFromStack_NullParam() {
        new Bank(1).drawFromStack(null);
    }


    // --- fill ---
    @Test
    public void testFill_Valid() {
        List<Domino> stack = new LinkedList<>();
        Bank bank = genBankFromStack(stack);
        // Just to make sure... checking before setting
        assertNotEquals(stack.get(4), bank.getDomino(3));

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
        new Bank(4).fill(new Domino(Tiles.A0_A0_Val7), -1);
    }

    @Test(expected = AssertionError.class)
    public void testFill_InvalidIdxLarge() {
        new Bank(4).fill(new Domino(Tiles.A0_A0_Val7), 4);
    }


    // --- copy ---
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
        // same reference for actual dominos
        assertSame(bank.getAllDominos()[0], bank.copy().getAllDominos()[0]);
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
        assertSame(bank.getAllDominos()[0], bank.copy().getAllDominos()[0]);
    }

}
