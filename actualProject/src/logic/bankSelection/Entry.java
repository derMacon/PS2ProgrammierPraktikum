package logic.bankSelection;

import logic.playerState.Player;
import logic.token.Domino;
import logic.token.Tiles;

import java.util.List;

/**
 * Class that implements an Entry of a bank. An entry consists out of a reference to a domino and a
 * reference to the player which selected this specific domino. Both fields may be null.
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
     *
     * @param domino         domino to be stored in the bank entry and later be selected by a player
     *                       participating in the game.
     * @param selectedPlayer player who selected the domino of the bank entry
     */
    public Entry(Domino domino, Player selectedPlayer) {
        this.domino = domino;
        this.selectedPlayer = selectedPlayer;
    }

    /**
     * Constructor used in the actual game. Its setting a the domino reference but leaves out the
     * player reference
     *
     * @param domino domino stored in the bank entry
     */
    public Entry(Domino domino) {
        this(domino, null);
    }

    /**
     * Entry for setting up preallocation in form of a String
     *
     * @param preallocation the preallocation follows the the following structure: "<idx of
     *                      player in player array> <domino in String format>"
     * @param players       list of players needed to set the reference in the entry
     */
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
     *
     * @return domino reference
     */
    public Domino getDomino() {
        return domino;
    }

    /**
     * Setter for the domino
     *
     * @param dom domino to be setted
     */
    public void setDomino(Domino dom) {
        this.domino = dom;
    }

    /**
     * Getter for the player which selected this bank entry
     *
     * @return reference to the which selected this bank entry
     */
    public Player getSelectedPlayer() {
        return selectedPlayer;
    }

    /**
     * Setter for the player reference of this bank entry
     *
     * @param player player to select this bank entry
     */
    public void selectEntry(Player player) {
        this.selectedPlayer = player;
    }

    /**
     * Generates a String that can be saved in a .txt document
     *
     * @return String representation of this object
     */
    @Override
    public String toString() {
        // TODO tertiary operator
        String selectedPlayer;
        if (null == this.selectedPlayer) {
            selectedPlayer = "-";
        } else {
            selectedPlayer = String.valueOf(this.selectedPlayer.getIdxInPlayerArray());
        }
        return selectedPlayer + " " + this.domino.toFile();
    }

    /**
     * Provides a copyWithoutSelection, where the domino is copied with a real deep
     * copyWithoutSelection but the player is not. It is only necessary to copyWithoutSelection the
     * domino, because it will later on be modified.
     *
     * @return copyWithoutSelection of the entry
     */
    public Entry copy() {
        return new Entry(this.domino.copy(), this.selectedPlayer);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Entry other = (Entry) obj;

        // TODO delete before final commit
        if (null != this.selectedPlayer && null != other.selectedPlayer) {
            boolean debugplayeridx = this.selectedPlayer.getIdxInPlayerArray()
                    == other.selectedPlayer.getIdxInPlayerArray();
            boolean debugDom = this.domino.equals(other.domino);
        }

        // Players can be empty if entry wasn't selected in the previous turns
        // TODO mit Vorsicht zu geniessen -> Nicht Nullpointer resistent.
        return (null == this.selectedPlayer && null == other.selectedPlayer)
                || null != this.selectedPlayer && null != other.selectedPlayer
                // players have to be the same
                && this.selectedPlayer.getIdxInPlayerArray()
                == other.selectedPlayer.getIdxInPlayerArray()
                // domino tiles have to be match their token not the reference
                && this.domino.equals(other.domino);
//                && this.domino.getTile() == other.domino.getTile();
    }

}
