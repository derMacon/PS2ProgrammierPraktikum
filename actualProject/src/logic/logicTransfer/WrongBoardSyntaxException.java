package logic.logicTransfer;

public class WrongBoardSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalid board syntax";

    public WrongBoardSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
