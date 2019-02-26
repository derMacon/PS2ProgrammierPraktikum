import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Assert;

import db.DBTable;
import fileio.FileUtil;
import fileio.FileUtilImpl;
import fileio.WrongSyntaxException;

/**
 * Klasse mit (statischen) (Hilfs-)methoden für JUnit-Tests. Die Klasse
 * beinhaltet unter anderem Methoden zum Vergleichen von Dateien und zum
 * Ermitteln des Stack-Trace einer Exception als Zeichenkette.
 * 
 * Dateivergleich funktioniert nativ nur unter Linux.
 * 
 * @author eg, ur, kar
 */
public class TestToolkit {

    /**
     * Ermittelt, ob der Inhalt der Dateien fileName1 und fileName2 identisch
     * ist.
     * 
     * @param fileName1 erste Datei.
     * @param fileName2 zweite Datei.
     * 
     * @return boolscher Wert, der angibt, ob der Inhalt der Dateien fileName1
     *         und fileName2 identisch ist.
     * 
     * @throws InterruptedException Externer Fehler
     * @throws IOException Dateifehler
     */

    public static boolean filesAreEqual(String fileName1, String fileName2)
            throws InterruptedException, IOException {
        final Runtime runtime = Runtime.getRuntime();
        final Process proc =
                runtime.exec("diff  --brief " + fileName1 + " " + fileName2);
        final int exitValue = proc.waitFor();
        return exitValue == 0;
    }

    /**
     * Liefert den StackTrace der Exception e.
     * 
     * @param e Exception, deren StackTrace zurückgegeben werden soll.
     * 
     * @return der StackTrace der Exception e.
     */
    public static String getStackTrace(Exception e) {
        try {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw);

            e.printStackTrace(pw);

            return sw.toString();

        } catch (Exception e2) {
            return "(Konnte Stacktrace nicht erzeugen.)";
        }
    }

    /**
     * Liest eine Datei mit dem Namen filename aus dem test-Verzeichnis ein.
     * 
     * @param filename Dateiname
     * @return DBTable Datenbanktabelle
     * 
     * @throws IOException Dateifehler
     * @throws WrongSyntaxException Syntaktischer Fehler beim Einlesen der Datei
     * 
     */
    public static DBTable read(String filename) throws IOException,
            WrongSyntaxException {
        final FileUtil u = new FileUtilImpl();
        return u.readTableFromFile("test/testdata/" + filename);
    }

    /**
     * Vergleicht zwei Dateien mit dem gleichen Namen (ohne Endung) aus den
     * festgelegten Ordnern miteinander und prüft sie auf Gleichheit.
     * 
     * @param filename Dateiname ohne Endung
     */
    public static void assertFilesEqual(String filename) {
        try {
            Assert.assertTrue(
                    "Dateien sind nicht gleich! : " + filename,
                    filesAreEqual("test/results/" + filename + ".out",
                            "test/expected_results/" + filename + ".exp"));
        } catch (InterruptedException e) {
            System.err.println("Teilnehmer sind unschuldig!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Datei nicht gefunden");
            e.printStackTrace();
        }
    }

    /**
     * Erzeugt eine Ausgabedatei und vergleicht sich mit der erwarteten Datei
     * auf Gleichheit. Wenn der Ordner results in dem die Ausgabedateien liegen
     * sollen noch nicht vorhanden ist, wird er erzeugt.
     * 
     * @param table Datenbanktabelle 
     * @param filename Dateiname ohne Endung
     * @throws IOException Dateifehler
     */
    public static void writeAndAssert(DBTable table, String filename) throws IOException {
        final FileUtil u = new FileUtilImpl();

        File resultsDir = new File("test/results");
        resultsDir.mkdir();

        u.writeTableToFile("test/results/" + filename + ".out", table);
       
        assertFilesEqual(filename);
    }

}
