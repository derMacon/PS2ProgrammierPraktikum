package logic.bankSelection;

import logic.token.Domino;
import logic.playerState.Player;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bank {

    /**
     * Seperator constant that's used to read from a file and write into it
     */
    private final String SEPERATOR_STRING_REPRESENTATION = ",";

    /**
     * Constant that is used to show an invalid int output
     */
    public final int INVALID_OUTPUT = -1;

    /**
     * Number of entries in this bank
     */
    private final int bankSize;

    /**
     * Random object needed to draw new dominos from the stack
     */
    private Random rand;

    /**
     * Array of entries which contain the domino as well as a flag for which player selected this domino.
     */
    private Entry[] entries;

    /**
     * Constructor setting the player count, used in the game.
     *
     * @param playerCnt
     */
    public Bank(int playerCnt) {
        this.entries = new Entry[playerCnt];
        this.bankSize = playerCnt;
        this.rand = new Random();
    }

    /**
     * Constructor setting the entries. Used for testing.
     *
     * @param entries entries of the bank
     */
    public Bank(Entry[] entries, Random pseudoRandom) {
        this.entries = entries;
        this.rand = pseudoRandom;
        this.bankSize = entries.length;
    }

    public Bank(String preallocation, List<Player> players) {
        String[] singleEntries = preallocation.split(SEPERATOR_STRING_REPRESENTATION);
        this.bankSize = players.size();
        int offsetEmptySlots = this.bankSize > singleEntries.length ? this.bankSize - singleEntries.length : 0;

        // convert to String array to entries array -> length to this.entries may differ
        Entry[] temp = new Entry[singleEntries.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new Entry(singleEntries[i], players);
        }

        // if there are empty slots in the given preallocation, they will be put in front of the other bank entries
        if (0 < offsetEmptySlots) {
            this.entries = new Entry[this.bankSize];
            System.arraycopy(temp, 0, this.entries, offsetEmptySlots, temp.length);
        } else {
            this.entries = temp;
        }

    }

    /**
     * Getter for the bank size
     *
     * @return bank size
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
        return this.entries;
    }

    public Entry getSpecificEntry(int entryIdx) {
        return this.entries[entryIdx];
    }

    /**
     * Returns the index of a given domino reference in this bank
     * @param domino domino reference to check the bank for
     * @return the index of a given domino reference in this bank, -1 (const: INVALID_OUTPUT) if not found.
     */
//    public int getDominoIdx(Domino domino) {
//        // TODO write tests
//        Domino currEntryDom;
//        int i = 0;
//        do {
//            currEntryDom = this.entries[i].getDomino();
//            if(currEntryDom == domino) { // actually checking for reference
//                return i;
//            }
//            i++;
//        } while (i < this.entries.length);
//        return INVALID_OUTPUT;
//    }
    
    public int getSelectedDominoIdx(Player player) {
        // TODO write tests
        int i = 0;
        do {
            if(this.entries[i].getSelectedPlayer() == player) { // actually checking for reference
                return i;
            }
            i++;
        } while (i < this.entries.length);
        return INVALID_OUTPUT;
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

    public void updateDomino(int bankIdx, Domino dom) {
        this.entries[bankIdx].setDomino(dom);
    }




    /**
     * Determinies if the bank is empty or not
     *
     * @return true when all entries are null pointers
     */
    public boolean isEmpty() {
        int counter = 0;
        for (Entry currentEntry : this.entries) {
            if (null != currentEntry) {
                counter++;
            }
        }
        return 0 == counter;
    }

    /**
     * Sets all entries equal to null
     */
    public void clearAllEntries() {
        for (int i = 0; i < this.bankSize; i++) {
            deleteEntry(i);
        }
    }

    /**
     * Deletes a specific entry with a given index
     *
     * @param entryIdx
     */
    public void deleteEntry(int entryIdx) {
        assert isValidBankIdx(entryIdx) && this.entries != null;
        this.entries[entryIdx] = null;
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
        assert null != player && isValidBankIdx(bankIdx) && null != this.entries
                && null != this.entries[bankIdx] && isNotSelected(bankIdx);
        this.entries[bankIdx].selectEntry(player);
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
    public List<Domino> randomlyDrawFromStack(List<Domino> stack) {
        assert null != stack;
        List<Domino> picks = new LinkedList<>();
        if (0 < stack.size()) {
            for (int i = 0; i < bankSize; i++) {
                picks.add(stack.remove(rand.nextInt(stack.size())));
            }
        }
        Collections.sort(picks);
        fill(picks);
        return stack; // just to show that stack is updated (not really necessary)
    }

    /**
     * Fills all entries with a given list
     * @param domList list to set the entries with
     */
    public void fill(List<Domino> domList) {
        for (int i = 0; i < domList.size(); i++) {
            fill(domList.get(i), i);
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

    /**
     * Checks if a given index is a valid index on the bank
     *
     * @param idx index of a domino on the bank
     * @return true if index is a valid bank index
     */
    private boolean isValidBankIdx(int idx) {
        return 0 <= idx && this.entries.length > idx;
    }

    /**
     * Generates a String that can be saved in a .txt document
     * @return String representation of this object
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(Entry currEntry : this.entries) {
            output.append(currEntry.toString());
        }
        return output.toString();
    }

    /**
     * Generates a Bank from a given String
     * @param input input String from which a new Bank will be generated
     * @return Bank object
     */
    public static Bank fromString(String input) {
        // TODO insert code
        return null;
    }

    /**
     * Makes a copyWithoutSelection of the bank, references to the individual entries stay the same
     * @return a copyWithoutSelection of the bank, references to the individual entries stay the same
     */
    public Bank copy() {
        Entry[] copyEntries = new Entry[this.entries.length];
        Entry temp; 
        for (int i = 0; i < this.entries.length; i++) {
            temp = this.entries[i];
            copyEntries[i] = null == temp ? null : temp.copy();
        }
        return new Bank(copyEntries, this.rand);
    }

}
