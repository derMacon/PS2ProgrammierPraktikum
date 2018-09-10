package logic;

public enum Player {
    HUMAN, BOT1, BOT2, BOT3;

    public Player next() {
        int ord = this.ordinal();
        ord = (ord + 1) % Player.values().length;
        return Player.values()[ord];
    }



}
