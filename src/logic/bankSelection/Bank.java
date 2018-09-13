package logic.bankSelection;

import logic.token.Domino;
import logic.playerState.Player;

import java.util.List;
import java.util.Random;

public class Bank {
    public static final int CURRENT_BANK_IDX = 0;
    public static final int NEXT_BANK_IDX = 1;
    public static final int BANK_COUNT = 2;

    public final int bankSize;

    /**
     * Random object needed to draw new dominos from the stack
     */
    private Random rand;

    /**
     * Array of entries which contain the domino as well as a flag for which player selected this domino.
     */
    private Entry[] entries;

    public Bank(int playerCnt) {
        this.entries = new Entry[playerCnt];
        this.bankSize = playerCnt;
        this.rand = new Random();
    }

    /**
     * Constructor setting the entries. Used for testing.
     *
     * @param entries entries of the
     */
    public Bank(Entry[] entries) {
        this.entries = entries;
        this.rand = new Random();
        this.bankSize = entries.length;
    }

    /**
     * Getter for the Entry array
     *
     * @return entries of the bank
     */
    public Entry[] getEntries() {
        return entries;
    }

    /**
     * Generates an array of dominos from objects entry array
     *
     * @return dominos on this bank, null for empty slots
     */
    public Domino[] getAllDominos() {
        Domino[] domFromBank = new Domino[bankSize];
        for (int i = 0; i < bankSize; i++) {
            domFromBank[i] = null != this.entries[i] ? this.entries[i].getDomino() : null;
        }
        return domFromBank;
    }

    /**
     * Getter for a specific domino on the bank
     *
     * @param idx index of the domino
     * @return the domino at the given index, null if index is invalid or the slot is empty.
     */
    public Domino getDomino(int idx) {
        return isValidBankIdx(idx) && null != this.entries[idx] ? this.entries[idx].getDomino() : null;
    }

    /**
     * Generates the player who selected the domino at a given position
     * @param idx idx of the domino which was selected
     * @return
     */
    public Player getSelectedPlayer(int idx) {
        return isValidBankIdx(idx) && !isNotSelected(idx) ? this.entries[idx].getSelectedPlayer() : null;
    }

    /**
     * Checks if a slot on the bank at the given index isn't already selected by another player
     * @param domIdx index for the domino that needs to be checked
     * @return true if bank index isn't already selected by another player
     */
    public boolean isNotSelected(int domIdx) {
        return isValidBankIdx(domIdx)
                && null != this.entries[domIdx]
                && null == this.entries[domIdx].getSelectedPlayer();
    }

    /**
     * Selects a entry at a given bank index
     * @param player reference of the player to select the entry at the given index
     * @param bankIdx index for the domino on the bank
     *
     * TODO find out how to tag a precondition
     * @precondition player must be unequal to null, bankIdx must be a valid bank index, and the slot which the player
     * wants to select hold a domino (you can't select an empty slot)
     */
    public void selectEntry(Player player, int bankIdx) {
        assert null != player && isValidBankIdx(bankIdx) && null != this.entries && null != this.entries[bankIdx];
        this.entries[bankIdx].selectEntry(player);
    }

    /**
     * Gets the domino the given player selected earlier on in the game
     * @param player player which selected a domino
     * @return the domino which the player selected, null if there is no such domino.
     */
    public Domino getPlayerSelectedDomino(Player player) {
        Domino output = null;
        int counter = 0;
        while (counter < bankSize && null == output) {
            if (this.entries[counter].getSelectedPlayer().equals(player)) {
                output =  null != this.entries[counter] ? this.entries[counter].getDomino() : null;
            }
        }
        return output;
    }


    public void drawFromStack(List<Domino> stack) {
        for (int i = 0; i < bankSize; i++) {
            fill(stack.get(rand.nextInt(stack.size())), i);
        }
    }

    public void fill(Domino domino, int idx) {
        if (0 <= idx && bankSize > idx) {
            this.entries[idx] = new Entry(domino);
        }
    }

    public Bank copy() {
        Bank output = new Bank(this.bankSize);
        for (int i = 0; i < bankSize; i++) {
            output.entries[i] = this.entries[i];
        }
        return output;
    }


    /**
     * Checks if a given index is a valid index on the bank
     * @param idx index of a domino on the bank
     * @return true if index is a valid bank index
     */
    private boolean isValidBankIdx(int idx) {
        return 0 <= idx && this.entries.length > idx;
    }
}
