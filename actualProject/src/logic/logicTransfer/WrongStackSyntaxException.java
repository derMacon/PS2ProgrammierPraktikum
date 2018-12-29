package logic.logicTransfer;

public class WrongStackSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Beutel-Syntax";

    public WrongStackSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
