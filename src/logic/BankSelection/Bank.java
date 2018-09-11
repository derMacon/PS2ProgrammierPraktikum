package logic.BankSelection;

import logic.Token.Domino;
import logic.Token.Entry;
import logic.PlayerState.Player;

import java.util.List;
import java.util.Random;

public class Bank {
    public static final int BANK_SIZE = 4;
    public static final int CURRENT_BANK_IDX = 0;
    public static final int NEXT_BANK_IDX = 1;

    private Random rand = new Random();
    private Entry[] entries;

    public Bank(Entry[] entries) {
        this.entries = entries;
    }

    public void selectEntry(Player player, int domIdx) {

    }

    public Domino getPlayerSelectedDomino(Player player) {
        Domino output = null;
        int counter = 0;
        while(counter < BANK_SIZE && null == output) {
            if(this.entries[counter].getSelectedPlayer().equals(player)) {
                output = this.entries[counter].getDomino();
            }
        }
        return output;
    }


    public void fill(List<Domino> stack) {
        for (int i = 0; i < BANK_SIZE; i++) {
            fill(stack.get(rand.nextInt(stack.size())), i);
        }
    }

    public void fill(Domino domino, int idx) {
        if(0 <= idx && BANK_SIZE > idx) {
            this.entries[idx] = new Entry(domino);
        }
    }
}
