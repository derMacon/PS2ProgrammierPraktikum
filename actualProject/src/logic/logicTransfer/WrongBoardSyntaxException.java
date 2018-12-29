package logic.logicTransfer;

public class WrongBoardSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Brett-Syntax";

    public WrongBoardSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
