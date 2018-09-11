package logic.BankSelection;

import logic.Token.Domino;
import logic.PlayerState.Player;

import java.util.List;
import java.util.Random;

public class Bank {
    public static final int BANK_SIZE = 4;
    public static final int CURRENT_BANK_IDX = 0;
    public static final int NEXT_BANK_IDX = 1;
    public static final int BANK_COUNT = 2;

    private Random rand = new Random();
    private Entry[] entries;

    public Bank(Entry[] entries) {
        this.entries = entries;
    }

    public Bank() {
        this(BANK_SIZE);
    }

    public Bank(int count) {
        entries = new Entry[count];
    }

    public Entry[] getEntries() {
        return entries;
    }

    public Domino[] getDominos() {
        Domino[] domFromBank = new Domino[BANK_SIZE];
        for (int i = 0; i < BANK_SIZE; i++) {
            domFromBank[i] = this.entries[i].getDomino();
        }
        return domFromBank;
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


    public void drawFromStack(List<Domino> stack) {
        for (int i = 0; i < BANK_SIZE; i++) {
            fill(stack.get(rand.nextInt(stack.size())), i);
        }
    }

    public void fill(Domino domino, int idx) {
        if(0 <= idx && BANK_SIZE > idx) {
            this.entries[idx] = new Entry(domino);
        }
    }

    public Bank copy() {
        Bank output = new Bank();
        for (int i = 0; i < BANK_SIZE; i++) {
            output.entries[i] = this.entries[i];
        }
        return output;
    }
}
