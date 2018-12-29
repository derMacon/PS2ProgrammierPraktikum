package logic.logicTransfer;

/**
 * Eigene Exception. Zeigt an, dass beim Einlesen einer Tabellen-Datei ein Fehler in der Syntax der
 * Datei festgestellt wurde.
 * <p>
 * Bei dieser Fehlermeldung soll die Defaultnachricht beim AuslĂ¶sen der Exception um eine
 * sinnvolle Fehlerbeschreibung erweitert werden. (Dies ist zudem sehr hilfreich beim Debuggen).
 *
 * @author kar, mhe
 */
public class WrongSyntaxException extends Exception {

    /**
     * Standardfehlermeldung.
     */
    private static final String DEFAULTMESSAGE = "Exception caused by Syntax-Error: ";

    /**
     * Exception mit individueller Fehlermeldung.
     *
     * @param message Message
     */
    public WrongSyntaxException(String message) {
        super(DEFAULTMESSAGE + message);
    }

}

