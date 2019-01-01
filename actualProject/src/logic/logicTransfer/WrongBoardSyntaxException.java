package logic.logicTransfer;

/**
 * Exception that will be thrown if the bank syntax was wrong
 */
public class WrongBoardSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Brett-Syntax";

    /**
     * Standard constructor for this class
     */
    public WrongBoardSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
