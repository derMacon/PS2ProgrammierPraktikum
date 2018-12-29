package logic.dataPreservation;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.logicTransfer.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class that implements the procedures needed to work with the filechooser
 */
public class Loader {


    public static final String UTF8_BOM = "\uFEFF";
    /**
     * Single instance of the logger. Initialized with null, can be returned with the corresponding getter.
     */
    private static Loader singleInstance = null;
    /**
     * Default directory for the filechooser
     * //TODO must create directory if not existent
     */
    private static String DEFAULT_DIRECTORY = "./test/fileTests/expected_results";
    /**
     * Filechoose that will be used to to save or read the file from
     */
    private final FileChooser fChooser;
    /**
     * File to save to or read from
     */
    private File file = null;
    /**
     * Stage that will be used to display the filechoose
     */
    private Stage stage;

//    public static getTestingInstance() {
//        if(null == this.singleInstance) {
//            this.singleInstance =
//        }
//    }
//
//    /**
//     * Testing Constructor, Junit does not make it possible to set stages during testing (normal constructor)
//     */
//    public Loader() {
//
//    }


    /**
     * Constructor for the Loader class. Sets the initial dirctory for the loader as well as the stage for the
     * and filechooser itself
     */
    public Loader() {
        fChooser = new FileChooser();
        fChooser.setTitle("Open Resource File");
        fChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY));
//        stage = new Stage(); // TODO entfernen -> mag er beim Testen nicht
    }

    /**
     * Getter for the logger instance
     */
    public static Loader getInstance() {
        if (null == singleInstance) {
            singleInstance = new Loader();
        }
        return singleInstance;
    }

    /**
     * Opens an output stream, writes the text into the file and closes the stream afterwards
     *
     * @param output file to which the data will be written
     * @param text   data that will be written
     */
    private static void actualSavingProcess(File output, String text) {
        try {
            PrintWriter outputStream = new PrintWriter(output);
            outputStream.print(text);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Logger.getInstance().printAndSafe("Could not save file");
            e.printStackTrace();
        }
    }

    public static void saveWithoutGUI(String filename, Game game) {
        actualSavingProcess(new File(filename), game.toFile());
    }

    public static String openGivenFile(String filePath) throws FileNotFoundException {
        return openGivenFile(new File(filePath));
    }

    public static String openGivenFile(File file) throws FileNotFoundException {
        assert file.isFile();
        Scanner in = null;
        StringBuilder sb = new StringBuilder();
        try {
            // has to be UTF8 to read german "Umlaute"
            in = new Scanner(file, "UTF8");
            while (in.hasNextLine()) {
                sb.append(in.nextLine() + "\n");
            }
            in.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Platzhalter");
        }
        return removeUTF8BOM(sb.toString());
    }

    public static boolean equalsSavedFile(Game game) {
        String temp = null;
        File file = null;
        try {
            file = Loader.getInstance().file;
            if(null == file) {
                return false;
            }
            temp = openGivenFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return temp.equals(game.toFile());
    }

//    public static String openSavedFile() {
//        String temp = null;
//        File file = null;
//        try {
//            file = Loader.getInstance().file;
//            if(null == file) {
//                return null;
//            }
//            temp = openGivenFile(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return temp;
//    }


    public static String openSavedFile() {
        String temp = null;
        File file = null;
        try {
            file = Loader.getInstance().file;
            if(null != file) {
                temp = openGivenFile(file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * https://stackoverflow.com/questions/21891578/removing-bom-characters-using-java
     *
     * @param s
     * @return
     */
    private static String removeUTF8BOM(String s) {
        if (s.startsWith(UTF8_BOM)) {
            s = s.substring(1);
        }
        return s;
    }

    /**
     * Saves a given input to an already chosen directory. If no directory was selected
     * beforehand the method saveFileAs(...) will be called.
     *
     * @param input text that will be saved
     */
    //TODO insert following method into xsml with scenebuilder
    public void saveFile(String input) {
        if (null == this.file) {
            saveFileAs(input);
        } else {
            actualSavingProcess(this.file, input);
        }
    }

    /**
     * Opens a Filechooser and saves the given input to the desired location with a given name.
     *
     * @param input text that will be saved
     */
    public void saveFileAs(String input) {
        if (null == stage) {
            this.stage = new Stage();
        }
        if (null != this.file) {
            this.fChooser.setInitialDirectory(this.file.getParentFile());
        }
        this.file = fChooser.showSaveDialog(stage);
        if (null == this.file) {
            Logger.getInstance().printAndSafe(Logger.ERROR_DELIMITER +
                    "\nUser aborted the saving process\n" + Logger.ERROR_DELIMITER + "\n");
        } else {
            actualSavingProcess(this.file, input);
            Logger.getInstance().printAndSafe("User saved the game as \"" + this.file.getName() + "\" to "
                    + this.file.getPath() + "\n");
        }

    }

    /**
     * Method to convert a file to a String
     * Opens a filechooser and the user can then select the file he want to load. Besides
     * returning the String value, the file attribut in the Loader class will be updated
     * <p>
     * Source: https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
     *
     * @return String value saved in the selected file
     */
    public String openFileChooser() throws FileNotFoundException {
        this.file = fChooser.showOpenDialog(stage);
        if (null == this.file) {
            Logger.getInstance().printAndSafe("\nUser aborted reading process\n");
            return "";
        } else {
            Logger.getInstance().printAndSafe("User opended the game \"" + this.file.getName() + "\" from "
                    + this.file.getAbsolutePath() + "\n");
            return openGivenFile(this.file);
        }
    }


}
