package logic.playerState;

import java.util.*;

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
     */
    public Result(Player[] players) {
        assert null != players;
        this.ranking = genResultRankingList(players);
    }

    /**
     * Constructor used for testing
     * @param ranking
     */
    public Result(List<ResultRanking> ranking) {
        assert null != ranking;
        this.ranking = ranking;
    }

    /**
     * Getter for the ranked list
     * @return the ranked list of the game
     */
    public List<ResultRanking> getRankedList() {
        return this.ranking;
    }

    /**
     * https://stackoverflow.com/questions/18410035/ways-to-iterate-over-a-list-in-java
     *
     * @param player
     * @return
     */
    private List<ResultRanking> genResultRankingList(Player[] player) {
        assert player.length > 0;
        LinkedList<Player> rankedWithoutEqualTemperedPlayers =
                new LinkedList<>(Arrays.asList(player));
        Collections.sort(rankedWithoutEqualTemperedPlayers);

        return orderRanking(new LinkedList<>(), rankedWithoutEqualTemperedPlayers);




        // list now sorted descending
//        Collections.reverse(rankedWithoutEqualTemperedPlayers);

//        int rank = 1;
////        int idxPlayer = 0;
////        do {
////            ResultRanking currRank = new ResultRanking(rank);
////            currRank.addPlayer();
////        }
//        List<ResultRanking> equallyTemperedOutput = new LinkedList<>();
//        Player lastPlayer = null;
//        ResultRanking currRanking = null;
////        for (Iterator<Player> iter = rankedWithoutEqualTemperedPlayers.iterator(); iter.hasNext(); ) {
//
//
////        Iterator<Player> iter = rankedWithoutEqualTemperedPlayers.iterator();
////        Player currPlayer = null;
////        while (iter.hasNext()) {
////            while (iter.hasNext() && (null == currPlayer || currPlayer.compareTo(lastPlayer) == 0)) {
////                // TODO check if allowed
////                if (null == currPlayer || currPlayer.compareTo(lastPlayer) != 0) {
////                    currRanking = new ResultRanking(rank);
////                    rank++;
////                }
////                currRanking.addPlayer(currPlayer);
////                lastPlayer = currPlayer;
////                currPlayer = iter.next();
////            }
////            equallyTemperedOutput.add(currRanking);
////        }
////        return equallyTemperedOutput;


//        return null;

    }

    private LinkedList<ResultRanking> orderRanking(LinkedList<ResultRanking> output,
                                              LinkedList<Player> players) {
        assert null != output && null != players;
        // exit condition -> sorted when no players are left
        if(players.isEmpty()) {
            return output;
        }
        // output is empty -> first initialize Result Ranking in List,
        if(output.isEmpty()) {
            ResultRanking resRank = new ResultRanking(1);
            output.add(resRank);
            return orderRanking(output, players);
        }
        // Put highest ranking player in ResultRanking
        if(output.getLast().isEmpty()) {
            ResultRanking resRank = output.getLast();
            resRank.addPlayer(players.removeLast());
            return orderRanking(output, players);
        }
        // last player always most valuable one, matches current ranking
        if(output.getLast().matchesRank(players.getLast())) {
            output.getLast().addPlayer(players.removeLast());
            return orderRanking(output, players);
        }
        // last player doesn't match last rank in list
        output.add(new ResultRanking(output.getLast().getRankingPosition() + 1));
        return orderRanking(output, players);


    }


    public List<Player> getWinner() {
        // TODO insert code
        return null;
    }

    @Override
    public String toString() {
        // TODO adjust method
//        StringBuilder output = new StringBuilder();
//        for (int i = 0; i < this.winner.size(); i++) {
//            output.append("Result{" + "points=" + this.winner.get(i).getBoardPoints() + ", winner=" + winner + '}' + "\n");
//        }
//        return output.toString();
        return null;
    }

}
