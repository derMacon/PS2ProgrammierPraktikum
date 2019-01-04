package logic.logicTransfer;

import logic.dataPreservation.Loader;
import org.junit.Assert;
import other.FakeGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Klasse mit (statischen) (Hilfs-)methoden für JUnit-Tests. Die Klasse
 * beinhaltet unter anderem Methoden zum Vergleichen von Dateien und zum
 * Ermitteln des Stack-Trace einer Exception als Zeichenkette.
 * <p>
 *
 * @author eg, ur, kar, silas
 */
public class TestToolkit {

    /**
     * Standard Dateityp welcher im Kontext der Dateiverarbeitung genutzt wird
     */
    private static final String FILE_ENDING = ".txt";

    /**
     * Pfad zum uebergeordnetem Pfad
     */
    private static final String PATH_FORMAT =
            "test" + File.separator + "fileTests" + File.separator + "testdata" + File.separator + "%s" + FILE_ENDING;

    /**
     * Ermittelt, ob der Inhalt der Dateien fileName1 und fileName2 identisch
     * ist.
     *
     * @param fileName1 erste Datei.
     * @param fileName2 zweite Datei.
     * @return boolscher Wert, der angibt, ob der Inhalt der Dateien fileName1
     * und fileName2 identisch ist.
     * @throws InterruptedException Externer Fehler
     * @throws IOException          Dateifehler
     */
    public static boolean filesAreEqual(String fileName1, String fileName2)
            throws InterruptedException, IOException {
        if (null == fileName1 || null == fileName2) {
            throw new IOException("arg nullpointer");
        }
        return new File(fileName1).equals(new File(fileName2));
    }

    /**
     * Liest eine Datei mit dem Namen filename aus dem test-Verzeichnis ein.
     *
     * @param filename Dateiname
     * @throws IOException          Dateifehler
     * @throws WrongSyntaxException Syntaktischer Fehler beim Einlesen der Datei
     */
    public static Game read(String filename) {
        return new Game(new FakeGUI(), readAsString(filename));
    }

    /**
     * Liest eine Datei mit dem Namen filename as dem test-Verzeichnis in Form einer Zeichenkette aus
     * @param filename Dateiname
     * @return Inhalt der Datei als Zeichenfolge
     */
    public static String readAsString(String filename) {
        try {
            File file = new File(String.format(PATH_FORMAT, filename));
            return Loader.getInstance().openGivenFile(file.getPath());
        } catch (FileNotFoundException e) {
            return e.getMessage();
        }
    }

    /**
     * Vergleicht zwei Dateien mit dem gleichen Namen (ohne Endung) aus den
     * festgelegten Ordnern miteinander und prüft sie auf Gleichheit.
     *
     * @param filename Dateiname ohne Endung
     */
    public static void assertFilesEqual(String filename) {
        try {
            File fileResult = new File("test" + File.separator + "fileTests" + File.separator
                    + "results" + File.separator + filename + ".txt");
            File fileExpectedResult = new File("test" + File.separator + "fileTests" + File.separator
                    + "expected_results" + File.separator + filename + ".txt");
            Assert.assertEquals(Loader.openGivenFile(fileExpectedResult), Loader.openGivenFile(fileResult));
        } catch (FileNotFoundException e) {
            assertTrue(false);
        }
    }

    /**
     * Erzeugt eine Ausgabedatei und vergleicht sich mit der erwarteten Datei
     * auf Gleichheit. Wenn der Ordner results in dem die Ausgabedateien liegen
     * sollen noch nicht vorhanden ist, wird er erzeugt.
     *
     * @param filename Dateiname ohne Endung
     * @throws IOException Dateifehler
     */
    public static void writeAndAssert(Game game, String filename) throws IOException {
        File resultsDir = new File("test" + File.separator + "filetests" + File.separator + "results");
        resultsDir.mkdir();

        Loader.saveWithoutGUI("test" + File.separator + "fileTests" + File.separator + "results" + File.separator + filename + ".txt", game);

        assertFilesEqual(filename);
    }

}
