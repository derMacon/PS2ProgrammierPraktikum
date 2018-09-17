package logic.bankSelection;

import logic.token.Domino;
import logic.playerState.Player;

import java.util.List;
import java.util.Random;

public class Bank {
    public static final int CURRENT_BANK_IDX = 0;
    public static final int NEXT_BANK_IDX = 1;
    public static final int BANK_COUNT = 2;

    private final int bankSize;

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
     * Getter for the bank size
     * @return
     */
    public int getBankSize() {
        return this.bankSize;
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
     * Generates an array of dominos from objects entry array. Used to for copying banks. Empty slots are denoted by a
     * nullpointer. An array might look like this: new Domino[] {null, new Domino(...), null, null};
     *
     * @return dominos on this bank, null for empty slots
     */
    public Domino[] getAllDominos() {
        Domino[] domFromBank = new Domino[this.entries.length];
        for (int i = 0; i < this.entries.length; i++) {
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
     *
     * @param idx idx of the domino which was selected
     * @return
     */
    public Player getSelectedPlayer(int idx) {
        return isValidBankIdx(idx) && !isNotSelected(idx) && null != this.entries[idx] ? this.entries[idx].getSelectedPlayer() : null;
    }

    /**
     * Checks if a slot on the bank at the given index isn't already selected by another player
     *
     * @param domIdx index for the domino that needs to be checked
     * @return true if bank index isn't already selected by another player
     */
    public boolean isNotSelected(int domIdx) {
        return isValidBankIdx(domIdx)
                && null != this.entries[domIdx]
                && null == this.entries[domIdx].getSelectedPlayer();
    }

    public boolean isEmpty() {
        int counter = 0;
        for(Entry currentEntry : this.entries) {
            if(null != currentEntry){
                counter++;
            }
        }
        return 0 == counter;
    }

    public void clearAllEntries() {
        for (int i = 0; i < this.bankSize; i++) {
            this.entries[i] = null;
        }
    }

    /**
     * Selects a entry at a given bank index
     *
     * @param player  reference of the player to select the entry at the given index
     * @param bankIdx index for the domino on the bank
     *                <p>
     *                TODO find out how to tag a precondition
     * @precondition player must be unequal to null, bankIdx must be a valid bank index, and the slot which the player
     * wants to select hold a domino (you can't select an empty slot)
     */
    public void selectEntry(Player player, int bankIdx) {
        assert null != player && isValidBankIdx(bankIdx) && null != this.entries && null != this.entries[bankIdx];
        this.entries[bankIdx].selectEntry(player);
    }

    /**
     * Gets the domino the given player selected earlier on in the game
     *
     * @param player player which selected a domino
     * @return the domino which the player selected, null if there is no such domino.
     */
    public Domino getPlayerSelectedDomino(Player player) {
        assert null != player;
        Domino output = null;
        int counter = 0;
        while (counter < bankSize && null == output) {
            if (null != this.entries[counter] && player.equals(this.entries[counter].getSelectedPlayer())) {
                output = this.entries[counter].getDomino();
            }
            counter++;
        }
        return output;
    }

    /**
     * Draws a random domino from the given stack
     *
     * @param stack list of dominos currently in the stack
     */
    public void drawFromStack(List<Domino> stack) {
        assert null != stack;
        for (int i = 0; i < bankSize; i++) {
             fill(stack.remove(rand.nextInt(stack.size())), i);
        }
    }



    /**
     * Setter for a given entry index
     *
     * @param domino domino to be set in the given entry, domino may be null
     * @param idx    index of the entry that will be modified
     */
    public void fill(Domino domino, int idx) {
        assert 0 <= idx && bankSize > idx;
        this.entries[idx] = new Entry(domino);
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
     *
     * @param idx index of a domino on the bank
     * @return true if index is a valid bank index
     */
    private boolean isValidBankIdx(int idx) {
        return 0 <= idx && this.entries.length > idx;
    }
}
