package logic.logicTransfer;

/**
 * Exception that will be thrown if the stack syntax was wrong
 */
public class WrongStackSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Beutel-Syntax";

    /**
     * Standard constructor for this class
     */
    public WrongStackSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
