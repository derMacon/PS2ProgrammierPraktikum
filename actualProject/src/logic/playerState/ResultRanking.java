package logic.playerState;

import javafx.scene.control.TreeItem;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a specific rank (e.g. first place, second place, etc.) Necessary since it's possible for
 * multiple players to score the same rank.
 */
public class ResultRanking {

    /**
     * Ranking of the players in the list item
     */
    private final int rankingPosition;

    /**
     * List of players sharing the same ranking
     */
    private List<Player> rankedPlayers;

    /**
     * Constructor setting the ranking position. Used in the Result class (actual game)
     *
     * @param rankingPosition number that represents the rank of this instance
     */
    public ResultRanking(int rankingPosition) {
        this.rankingPosition = rankingPosition;
        this.rankedPlayers = new LinkedList<>();
    }

    /**
     * Constructor setting the ranking position, as well as a List of already sorted players.
     * Used for testing
     *
     * @param rankingPosition number that represents the rank of this instance
     * @param rankedPlayers players which socred this rank in the result of the game
     * @pre 0 < rankingPosition
     * @pre null != rankedPlayers;
     */
    public ResultRanking(int rankingPosition, Player[] rankedPlayers) {
        assert 0 < rankingPosition && null != rankedPlayers;
        this.rankingPosition = rankingPosition;
        this.rankedPlayers = Arrays.asList(rankedPlayers);
    }

    /**
     * Getter for the number which represents this rnak
     * @return the number which represents this rnak
     */
    public int getRankingPosition() {
        return this.rankingPosition;
    }

    /**
     * Getter for the ranked players
     * @return ranked players
     */
    public List<Player> getRankedPlayers() {
        return this.rankedPlayers;
    }

    /**
     * Adds a player to this rank
     * @param player player to set
     */
    public void addPlayer(Player player) {
        this.rankedPlayers.add(player);
    }

    /**
     * Checks if a given player matches this rank
     * @param player player to check
     * @return true if a given player matches this rank
     */
    public boolean matchesRank(Player player) {
        return null != player && !this.rankedPlayers.isEmpty() && this.rankedPlayers.get(0).compareTo(player) == 0;
    }

    /**
     * Checks if rank is empty
     * @return true if rank is empty
     */
    public boolean isEmpty() {
        return null == this.rankedPlayers || this.rankedPlayers.isEmpty();
    }

    /**
     * Checks if the rank is initialized but empty
     * @return if the rank is initialized but empty
     */
    public boolean rankInitializedButNoPlayerAddedYet() {
        return null == this.rankedPlayers || this.rankedPlayers.isEmpty();
    }

    /**
     * Generates a treeItem for the treeView from the result
     * @return a treeItem for the treeView from the result
     */
    public TreeItem<String> toTreeItem() {
        TreeItem<String> parent = new TreeItem<>(this.rankingPosition + ". Platz");
        parent.setExpanded(true);
        for (Player currPlayer : this.rankedPlayers) {
            parent.getChildren().add(currPlayer.toTreeItem());
        }
        return parent;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ResultRanking other = (ResultRanking) obj;
        if (this.rankedPlayers.size() != other.getRankedPlayers().size()) {
            return false;
        }
        if (this.rankingPosition == other.rankingPosition) {
            boolean samePlayers = true;
            int i = 0;
            do {
                samePlayers = this.rankedPlayers.contains(other.rankedPlayers.get(i))
                        && other.rankedPlayers.contains(this.rankedPlayers.get(i));
                i++;
            } while (samePlayers && i < this.rankedPlayers.size());
            return samePlayers;
        }
        return false;
    }


}
