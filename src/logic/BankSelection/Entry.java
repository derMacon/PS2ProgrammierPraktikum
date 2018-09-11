package logic.BankSelection;

import logic.PlayerState.Player;
import logic.Token.Domino;

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
