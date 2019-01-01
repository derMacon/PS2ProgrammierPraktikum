package logic.logicTransfer;

/**
 * Exception that will be thrown if the bank syntax was wrong
 */
public class WrongBankSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Bank-Syntax";

    public WrongBankSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
