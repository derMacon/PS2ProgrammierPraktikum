package logic.playerState;

import java.util.List;

/**
 * Class that compares the results of all players and generates the winner.
 */
public class Result {

    /**
     * Players to be evaluated
     */
    private Player[] players;

    /**
     * List of winners
     */
    private List<Player> winner;

    /**
     * int array corresponding with the points of the respective players
     */
    private int[] playersPoints;

    public Result(Player[] players) {
        this.players = players;
        // TODO getWinner()
    }

    public List<Player> getWinner() {
        // TODO insert code
        return null;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.winner.size(); i++) {
            output.append("Result{" + "points=" + this.winner.get(i).getBoardPoints() + ", winner=" + winner + '}' + "\n");
        }
        return output.toString();
    }

}
