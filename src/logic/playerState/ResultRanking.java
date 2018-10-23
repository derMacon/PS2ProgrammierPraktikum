package logic.playerState;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ResultRanking {

    private final int rankingPosition;

    private List<Player> rankedPlayers;

    /**
     * Constructor setting the ranking position. Used in the Result class (actual game)
     *
     * @param rankingPosition
     */
    public ResultRanking(int rankingPosition) {
        this.rankingPosition = rankingPosition;
        this.rankedPlayers = new LinkedList<>();
    }

    /**
     * Constructor setting the ranking position, as well as a List of already sorted players.
     * Used for testing
     *
     * @param rankingPosition
     * @param rankedPlayers
     */
    public ResultRanking(int rankingPosition, Player[] rankedPlayers) {
        assert 0 < rankingPosition && null != rankedPlayers;
        this.rankingPosition = rankingPosition;
        this.rankedPlayers = Arrays.asList(rankedPlayers);
    }

    public void addPlayer(Player player) {
        this.rankedPlayers.add(player);
    }


    public int getRankingPosition() {
        return this.rankingPosition;
    }

    public List<Player> getRankedPlayers() {
        return this.rankedPlayers;
    }

    public boolean matchesRank(Player player) {
        return null != player && !this.rankedPlayers.isEmpty() && this.rankedPlayers.get(0).compareTo(player) == 0;
    }

    public boolean isEmpty() {
        return null == this.rankedPlayers || this.rankedPlayers.isEmpty();
    }

    public boolean rankInitializedButNoPlayerAddedYet() {
        return null == this.rankedPlayers || this.rankedPlayers.isEmpty();
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
        if(this.rankedPlayers.size() != other.getRankedPlayers().size()) {
            return false;
        }
        if(this.rankingPosition == other.rankingPosition) {
            boolean samePlayers = true;
            int i = 0;
            do {
//                samePlayers = this.rankedPlayers.get(i).equals(other.rankedPlayers.get(i));
                samePlayers = this.rankedPlayers.contains(other.rankedPlayers.get(i))
                        && other.rankedPlayers.contains(this.rankedPlayers.get(i));
                i++;
            } while (samePlayers && i < this.rankedPlayers.size());
            return samePlayers;
        }
        return false;
    }


}
