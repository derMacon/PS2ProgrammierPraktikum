package logic.logicTransfer;

public class WrongTagException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    public static final String DEFAULT_MESSAGE = "Invalide Bezeichner-Syntax";

    /**
     * Exception mit individueller Fehlermeldung.
     *
     * @param message Message
     */
    public WrongTagException(String message) {
        super(DEFAULT_MESSAGE + message);
    }

    public WrongTagException() {
        super(DEFAULT_MESSAGE);
    }

}
