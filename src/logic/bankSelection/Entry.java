package logic.bankSelection;

import logic.playerState.Player;
import logic.token.Domino;
import logic.token.Tiles;

import java.util.List;

/**
 * Class that implements an Entry of a bank. An entry consists out of a reference to a domino and a reference to the
 * player which selected this specific domino. Both fields may be null.
 */
public class Entry {

    private static final String BLANK_SEPERATOR = " ";
    private static final String EMPTY_SLOT = "-";
    private static final int SELECTED_PLAYER_IDX = 0;
    private static final int DOMINO_IDX = 1;


    /**
     * Reference to the domino of the bank entry
     */
    private Domino domino;

    /**
     * Reference to the player which selected the specific domino of the bank entry
     */
    private Player selectedPlayer;

    /**
     * Constructor of this class
     * @param domino domino to be stored in the bank entry and later be selected by a player participating in the game.
     * @param selectedPlayer player who selected the domino of the bank entry
     */
    public Entry(Domino domino, Player selectedPlayer) {
        this.domino = domino;
        this.selectedPlayer = selectedPlayer;
    }

    /**
     * Constructor used in the actual game. Its setting a the domino reference but leaves out the player reference
     * @param domino domino stored in the bank entry
     */
    public Entry(Domino domino) {
        this(domino, null);
    }

    public Entry(String preallocation, List<Player> players) {
        String[] components = preallocation.split(BLANK_SEPERATOR);

        // Set selected player -> only set selected player if entry is not NOT selected
        if (!components[SELECTED_PLAYER_IDX].equals(EMPTY_SLOT)) {
            int ordPlayer = Integer.parseInt(components[SELECTED_PLAYER_IDX]);
            this.selectedPlayer = players.get(ordPlayer);
        }

        // Set domino
        this.domino = new Domino(Tiles.fromString(components[DOMINO_IDX]));
    }

    /**
     * Getter for the domino of this entry
     * @return
     */
    public Domino getDomino() {
        return domino;
    }

    /**
     * Getter for the player which selected this bank entry
     * @return reference to the which selected this bank entry
     */
    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    /**
     * Setter for the domino
     * @param dom domino to be setted
     */
    public void setDomino(Domino dom) {
        this.domino = dom;
    }

    /**
     * Setter for the player reference of this bank entry
     * @param player player to select this bank entry
     */
    public void selectEntry(Player player) {
        this.selectedPlayer = player;
    }

    /**
     * Generates a String that can be saved in a .txt document
     * @return String representation of this object
     */
    @Override
    public String toString() {
        return this.selectedPlayer.getIdxInPlayerArray() + " " + this.domino.toString();
    }

    /**
     * Generates a Entry from a given String
     * @param input input String from which a new Entry will be generated
     * @return Entry object
     */
    public static Entry fromString(String input) {
        // TODO insert code
        return null;
    }

    /**
     * Provides a copyWithoutSelection, where the domino is copied with a real deep copyWithoutSelection but the player is not. It is only necessary to
     * copyWithoutSelection the domino, because it will later on be modified.
     * @return copyWithoutSelection of the entry
     */
    public Entry copy() {
        return new Entry(this.domino.copy(), this.selectedPlayer);
    }

}
