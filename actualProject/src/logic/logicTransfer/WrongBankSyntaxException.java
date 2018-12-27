package logic.logicTransfer;

public class WrongBankSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalid bank syntax";

    public WrongBankSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
