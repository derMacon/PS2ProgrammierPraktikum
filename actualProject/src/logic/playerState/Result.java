package logic.playerState;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that compares the results of all players and generates the winner.
 */
public class Result {

    /**
     * List of winners
     */
    private List<ResultRanking> ranking;

    /**
     * Constructor takes an array of players participating in the game. Converts it to list
     * containing the result ranking. Since it is possible to have multiple winners / it is
     * possible for the players to share the same ranking a special class must be implemented
     * containing both the ranking and the list of players having the same score.
     * <p>
     * Constructor used in the actual game.
     *
     * @param players players participating in the game
     * @pre null != players
     */
    public Result(Player[] players) {
        assert null != players;
        this.ranking = genResultRankingList(players);
    }

    /**
     * Constructor used for testing
     *
     * @param ranking ranking of the players
     * @pre null != ranking
     */
    public Result(List<ResultRanking> ranking) {
        assert null != ranking;
        this.ranking = ranking;
    }

    /**
     * Getter for the ranked list
     *
     * @return the ranked list of the game
     */
    public List<ResultRanking> getRankedList() {
        return this.ranking;
    }

    /**
     * Generates a list of ResultRanking objects.
     *
     * @param players player array that will be sorted
     * @return ordered list containing ResultRanking objects
     * @pre players.length > 0;
     */
    private List<ResultRanking> genResultRankingList(Player[] players) {
        assert players.length > 0;
        LinkedList<Player> rankedWithoutEqualTemperedPlayers = new LinkedList<>(Arrays.asList(players));
        Collections.sort(rankedWithoutEqualTemperedPlayers);

        return orderRanking(new LinkedList<>(), rankedWithoutEqualTemperedPlayers);
    }

    /**
     * Converts a conventional ordered player list into a list of ResultRanking objects. Equally Ranked Players will
     * be put into the same ranking index.
     *
     * @param output  output list of ResultRanking objects
     * @param players ordered list of players
     * @return output list containing ranked players
     * @pre null != output
     * @pre null != players
     */
    private LinkedList<ResultRanking> orderRanking(LinkedList<ResultRanking> output,
                                                   LinkedList<Player> players) {
        assert null != output && null != players;
        // exit condition -> sorted when no players are left
        if (players.isEmpty()) {
            return output;
        }
        // output is empty -> first initialize Result Ranking in List,
        if (output.isEmpty()) {
            ResultRanking resRank = new ResultRanking(1);
            output.add(resRank);
            return orderRanking(output, players);
        }
        // Put highest ranking player in ResultRanking
        if (output.getLast().isEmpty()) {
            ResultRanking resRank = output.getLast();
            resRank.addPlayer(players.removeLast());
            return orderRanking(output, players);
        }
        // last player always most valuable one, matches current ranking
        if (output.getLast().matchesRank(players.getLast())) {
            output.getLast().addPlayer(players.removeLast());
            return orderRanking(output, players);
        }
        // last player doesn't match last rank in list
        output.add(new ResultRanking(output.getLast().getRankingPosition() + 1));
        return orderRanking(output, players);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Ordered Ranking:\n");
        Player currPlayer = null;
        for (int i = 0; i < this.ranking.size(); i++) {
            output.append(i + ". Spot");
            for (int j = 0; j < this.ranking.get(i).getRankedPlayers().size(); j++) {
                currPlayer = this.ranking.get(i).getRankedPlayers().get(j);
                output.append("\t" + currPlayer.getName() + " -> Result{" + "points="
                        + currPlayer.getBoardPoints() + '}' + "\n");
            }
        }
        return output.toString();
    }

    /**
     * Generates a treeView object (used by the gui to show result to the user)
     *
     * @return a treeView object (used by the gui to show result to the user)
     */
    public TreeView<String> toTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Ranking");
        rootItem.setExpanded(true);
        for (ResultRanking currRanking : this.ranking) {
            rootItem.getChildren().add(currRanking.toTreeItem());
        }
        return new TreeView<>(rootItem);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Result other = (Result) obj;
        int i = 0;
        int thisSize = this.ranking.size();
        boolean equals = thisSize == other.ranking.size();
        while (equals && i < thisSize) {
            equals = this.ranking.get(i).equals(other.ranking.get(i));
            i++;
        }
        return equals;
    }

}
