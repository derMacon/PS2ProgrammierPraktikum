package logic.logicTransfer;

public class WrongStackSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalid stack syntax";

    public WrongStackSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
