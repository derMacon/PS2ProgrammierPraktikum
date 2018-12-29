package logic.logicTransfer;

public class WrongBankSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Bank-Syntax";

    public WrongBankSyntaxException() {
        super(DEFAULT_MESSAGE);
    }

}
