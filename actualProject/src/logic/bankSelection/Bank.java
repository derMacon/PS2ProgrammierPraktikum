package logic.bankSelection;

import logic.playerState.Player;
import logic.randomizer.PseudoRandAlwaysHighestVal;
import logic.token.Domino;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Bank holding an array of entries (information about the selected dominos)
 */
public class Bank {

    /**
     * Constant that is used to show an invalid int output
     */
    public static final int INVALID_OUTPUT = -1;
    /**
     * Seperator constant that's used to read from a file and write into it
     */
    private static final String SEPERATOR_STRING_REPRESENTATION = ",";
    /**
     * Number of entries in this bank
     */
    private final int bankSize;

    /**
     * Random object needed to draw new dominos from the stack
     */
    private Random rand;

    /**
     * Array of entries which contain the domino as well as a flag for which player selected this
     * domino.
     */
    private Entry[] entries;

    /**
     * Constructor setting the player count, used in the game.
     *
     * @param playerCnt number of players participating in the game is equivalent to the number
     *                  of bank slots required
     */
    public Bank(int playerCnt) {
        this.entries = new Entry[playerCnt];
        this.bankSize = playerCnt;
        this.rand = new Random();
        // Alternative random objects used for manual testing
        //this.rand = new PseudoRandAlwaysHighestVal();
        // this.rand = new PseudoRandZeroResidueClass(2);
    }

    /**
     * Constructor setting the entries. Used for testing.
     *
     * @param entries      entries of the bank
     * @param pseudoRandom random object of the bank (for testing purpose)
     */
    public Bank(Entry[] entries, Random pseudoRandom) {
        this.entries = entries;
        this.rand = pseudoRandom;
        this.bankSize = entries.length;
    }

    /**
     * Constructor used for testing / fileIO.
     *
     * @param preallocation String representation of the dominos in the bank
     * @param players       List of players to which the entries will be linked. Number of players
     *                      determines the bank size.
     * @param rand          random object used when drawing dominos for the next round
     * @pre null != preallocation
     * @pre null != players
     * @pre null != rand
     */
    public Bank(String preallocation, List<Player> players, Random rand) {
        assert null != preallocation && null != players && null != rand;
        this.bankSize = players.size();
        this.entries = new Entry[this.bankSize];
        this.rand = rand;
        if (0 < preallocation.length()) {
            String[] singleEntries = preallocation.split(SEPERATOR_STRING_REPRESENTATION);
            int offset = this.bankSize - singleEntries.length;
            for (int i = singleEntries.length - 1; i >= 0; i--) {
                this.entries[i + offset] = new Entry(singleEntries[i], players);
            }
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

    /**
     * Getter for a specific Entry
     *
     * @param entryIdx index of the entry that will be returned
     * @return entry at the given index
     */
    public Entry getSpecificEntry(int entryIdx) {
        return this.entries[entryIdx];
    }

    /**
     * Getter for a specific domino selected by the given player
     *
     * @param player player who selected the desired domino
     * @return domino which the given player selected
     */
    public int getSelectedDominoIdx(Player player) {
        int i = 0;
        do {
            // actually checking for reference
            if (null != this.entries[i] && this.entries[i].getSelectedPlayer() == player) {
                return i;
            }
            i++;
        } while (i < this.entries.length);
        return INVALID_OUTPUT;
    }


    /**
     * Generates an array of dominos from objects entry array. Used to for copying banks. Empty
     * slots are denoted by a nullpointer. An array might look like this: new Domino[] {null, new
     * Domino(...), null, null};
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
        return isValidBankIdx(idx) && null != this.entries[idx] ? this.entries[idx].getDomino()
                : null;
    }

    /**
     * Generates the player who selected the domino at a given position
     *
     * @param idx idx of the domino which was selected
     * @return selected player from a given index
     */
    public Player getSelectedPlayer(int idx) {
        return isValidBankIdx(idx) && !isNotSelected(idx) && null != this.entries[idx]
                ? this.entries[idx].getSelectedPlayer() : null;
    }

    /**
     * Setter for the random object (used in tests)
     *
     * @param rand random that will be put as the instance's random object
     */
    public void setRand(Random rand) {
        this.rand = rand;
    }

    /**
     * Setter for a given Domino at the given bank index
     *
     * @param bankIdx bank index at which the domino will be updated
     * @param dom     domino reference to override the current domino instance
     */
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
     * @param entryIdx index of the entry that will deleted
     * @pre isValidBankIdx(entryIdx)
     * @pre this.entries != null
     */
    public void deleteEntry(int entryIdx) {
        assert isValidBankIdx(entryIdx) && this.entries != null;
        this.entries[entryIdx] = null;
    }

    /**
     * Deleted a speceific entry of a given player
     *
     * @param player refernce of a player whos entry will be deleted
     */
    public void deleteEntry(Player player) {
        if (this.entries != null) {
            for (int i = 0; i < bankSize; i++) {
                if (this.entries[i] != null && this.entries[i].getSelectedPlayer() == player) {
                    this.entries[i] = null;
                }
            }
        }
    }

    /**
     * Selects a entry at a given bank index
     *
     * @param player  reference of the player to select the entry at the given index
     * @param bankIdx index for the domino on the bank
     *                <p>
     * @pre player must be unequal to null, bankIdx must be a valid bank index, and the
     * slot which the player
     * wants to select hold a domino (you can't select an empty slot)
     * @pre null != player
     * @pre isValidBankIdx(bankIdx)
     * @pre null != this.entries
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
     * @pre null != player
     */
    public Domino getPlayerSelectedDomino(Player player) {
        assert null != player;
        if (this.isEmpty()) {
            return null;
        }
        Domino output = null;
        int counter = 0;
        while (counter < bankSize && null == output) {
            if (null != this.entries[counter] && player.equals(
                    this.entries[counter].getSelectedPlayer())) {
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
     * @return List of dominos equivalent to the banksize. (Dominos will be sorted and removed
     * from the input stack)
     * @pre null != stack
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
     *
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
     * @pre 0 <= idx
     * @pre bankSize > idx
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
     * Generates a String that can be saved in a file
     *
     * @return String representation of this object
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.entries.length; i++) {
            output.append(this.entries[i] == null ? "" : this.entries[i].toString());
            if (i < this.entries.length - 1 && this.entries[i] != null) {
                output.append(",");
            }
        }
        return output.toString();
    }

    /**
     * Makes a copy of the bank, references to the individual entries stay the same. Used in game class when the next
     * round bank entries are copied to the current round bank
     *
     * @return a copy of the bank, references to the individual entries stay the
     * same
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Bank other = (Bank) obj;
        boolean isEqual = null != this.entries && null != other.entries && this.bankSize == other.bankSize;
        int i = 0;
        while (isEqual && i < this.entries.length) {
            isEqual = (null == this.entries[i] && null == other.entries[i])
                    || (null != this.entries[i] && null != other.entries[i]
                    && this.entries[i].equals(other.entries[i]));
            i++;
        }
        return isEqual;
    }

    /**
     * Getter for the random object
     *
     * @return the random object of the bank
     */
    public Random getRand() {
        return rand;
    }

}
