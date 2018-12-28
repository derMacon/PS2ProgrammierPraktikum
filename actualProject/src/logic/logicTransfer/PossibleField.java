package logic.logicTransfer;

public enum PossibleField {
    NEXT_BANK, CURR_BANK, CURR_DOM;

    public PossibleField incField() {
        PossibleField[] values = values();
        return values[(this.ordinal() + 1) % values.length];
    }
}
