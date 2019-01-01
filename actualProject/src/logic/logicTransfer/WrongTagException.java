package logic.logicTransfer;

/**
 * Exception that will be thrown if the tag syntax was wrong
 */
public class WrongTagException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Bezeichner-Syntax";

    /**
     * Individual error message
     *
     * @param message Message
     */
    public WrongTagException(String message) {
        super(DEFAULT_MESSAGE + message);
    }

    /**
     * Standard constructor for this class
     */
    public WrongTagException() {
        super(DEFAULT_MESSAGE);
    }

}
