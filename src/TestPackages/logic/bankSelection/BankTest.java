package TestPackages.logic.bankSelection;

import gui.JavaFXGUI;
import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.logicTransfer.Game;
import logic.playerState.DefaultAIPlayer;
import logic.playerState.Player;
import logic.token.Domino;

import java.util.LinkedList;
import java.util.List;

import logic.token.Tiles;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import static org.junit.Assert.*;

public class BankTest {

    //<editor-fold defaultstate="collapsed" desc="Helping Method - Setting up tests">
    /**
     * Fills a given list with the stack of dominos and generates a bank from the first 4 dominos of the stack. Dominos
     * are not randomized.
     *
     * @param stack input list serving as the stack
     * @return bank with the first 4 dominos from the newly filled stack.
     */
    private Bank genBankFromStack(List<Domino> stack) {
        stack = Domino.fill(stack);
        Entry[] entries = new Entry[]{new Entry(stack.get(0)), new Entry(stack.get(1)), new Entry(stack.get(2)), new Entry(stack.get(3))};
        Bank bank = new Bank(entries);
        return bank;
    }

    private Bank genBankFromStack() {
        return genBankFromStack(new LinkedList<Domino>());
    }
    //</editor-fold>


    // --- select / getSelectedPlayer ---
    @Test
    public void testSelectGetSelectedPlayer_SelectValidDomino() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI());
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
        Bank bank = genBankFromStack();
        assertNull(bank.getSelectedPlayer(0));
    }


    // --- isNotSelected ---
    @Test
    public void testIsNotSelected_ValidIdx() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI());
        bank.selectEntry(fstPlayer, 0);

        assertFalse(bank.isNotSelected(0));
        assertTrue(bank.isNotSelected(1));
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelected_InvalidIdxTooSmall() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI());
        bank.selectEntry(fstPlayer, -1);
    }

    @Test(expected = AssertionError.class)
    public void testIsNotSelected_InvalidIdxTooBig() {
        List<Domino> stack = null;
        Bank bank = genBankFromStack(stack);
        Player fstPlayer = new DefaultAIPlayer(new FakeGUI());
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
        Domino[] expOutput = new Domino[] {stack.get(0), stack.get(1), stack.get(2), stack.get(3)};
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
        Entry[] entries = new Entry[] {null, new Entry(domino)};
        Bank bank = new Bank(entries);
        Domino[] expOutput = new Domino[] {null, domino};
        assertArrayEquals(expOutput, bank.getAllDominos());
    }


    // --- getPlayerSelectedDomino ---
//    @Test
//    public void testgetPlayerSelectedDomino_MixedBank() {
//        List<Domino> stack = new LinkedList<>();
//        genBankFromStack(stack);
//        Entry[] entries = new Entry[]{new Entry(stack.get(0)), new Entry(stack.get(1)), null};
//        Bank bank = new Bank(entries);
//        Player player = new DefaultAIPlayer(new FakeGUI());
//        bank.selectEntry(player, 0);
//        assertSame(player, bank.getSelectedPlayer(0));
//        assertNull(bank.getSelectedPlayer(1));
//        assertNull(bank.getSelectedPlayer(2));
//    }

}
