package logic.bankSelection;

import logic.playerState.Player;
import logic.token.Domino;

public class Entry {
    private Domino domino;
    private Player selectedPlayer;

    public Entry(Domino domino, Player selectedPlayer) {
        this.domino = domino;
        this.selectedPlayer = selectedPlayer;
    }


    public Entry(Domino domino) {
        this(domino, null);
    }

    public Domino getDomino() {
        return domino;
    }

    public Player getSelectedPlayer() {
        return selectedPlayer;
    }
}
