package logic.bankSelection;

import logic.playerState.Player;
import logic.token.Domino;

/**
 * Class that implements an Entry of a bank. An entry consists out of a reference to a domino and a reference to the
 * player which selected this specific domino. Both fields may be null.
 */
public class Entry {

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
     * Setter for the player reference of this bank entry
     * @param player player to select this bank entry
     */
    public void selectEntry(Player player) {
        this.selectedPlayer = player;
    }
}
