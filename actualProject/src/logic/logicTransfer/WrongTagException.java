package logic.logicTransfer;

public class WrongTagException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Wrong tag identifier: ";

    /**
     * Exception mit individueller Fehlermeldung.
     *
     * @param message Message
     */
    public WrongTagException(String message) {
        super(DEFAULT_MESSAGE + message);
    }

}
