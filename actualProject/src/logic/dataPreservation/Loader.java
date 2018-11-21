package logic.dataPreservation;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class that implements the procedures needed to work with the filechooser
 */
public class Loader {


    /**
     * Single instance of the logger. Initialized with null, can be returned with the corresponding getter.
     */
    private static Loader singleInstance = null;


    /**
     * Default directory for the filechooser
     * //TODO must create directory if not existent
     */
    private static String DEFAULT_DIRECTORY = "./dataOutput";

    /**
     * File to save to or read from
     */
    private File file = null;

    /**
     * Filechoose that will be used to to save or read the file from
     */
    private final FileChooser fChooser;

    /**
     * Stage that will be used to display the filechoose
     */
    private final Stage stage;


    /**
     * Constructor for the Loader class. Sets the initial dirctory for the loader as well as the stage for the
     * and filechooser itself
     */
    public Loader() {
        fChooser = new FileChooser();
        fChooser.setTitle("Open Resource File");
        fChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY));
        stage = new Stage();
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
     * @param output file to which the data will be written
     * @param text data that will be written
     */
    private void actualSavingProcess(File output, String text) {
        try {
            PrintWriter outputStream = new PrintWriter(output);
            outputStream.println(text);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a given input to an already chosen directory. If no directory was selected
     * beforehand the method saveFileAs(...) will be called.
     * @param txtFldInput text that will be saved
     */
    public void saveFile(String txtFldInput) {
        if(null == this.file) {
            saveFileAs(txtFldInput);
        } else {
            actualSavingProcess(this.file, txtFldInput);
        }
    }


    /**
     * Opens a Filechooser and saves the given input to the desired location with a given name.
     * @param txtFldInput text that will be saved
     */
    public void saveFileAs(String txtFldInput) {
        this.file = fChooser.showSaveDialog(stage);
        actualSavingProcess(this.file, txtFldInput);
    }

    /**
     * Method to convert a file to a String
     * Opens a filechooser and the user can then select the file he want to load. Besides
     * returning the String value, the file attribut in the Loader class will be updated
     *
     * Source: https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
     *
     * @return String value saved in the selected file
     */
    public String openFileChooser() {
        this.file = fChooser.showOpenDialog(stage);
        return openGivenFile(this.file);
    }

    public String openGivenFile(File file) {
        Scanner in = null;
        StringBuilder sb = new StringBuilder();
        try {
            // has to be UTF8 to read german "Umlaute"
            in = new Scanner(this.file, "UTF8");
            while(in.hasNextLine()) {
                sb.append(in.nextLine() + "\n");
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(sb);
        return sb.toString();
    }


}
