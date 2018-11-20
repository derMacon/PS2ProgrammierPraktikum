import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Logic {

	/**
	 * Default directory for the filechooser
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
     * Konstructor sets the instance for the Filechooser and opens the necessary stage needed to
     * display the dialog window
     */
    public Logic() {
        fChooser = new FileChooser();
        fChooser.setTitle("Open Resource File");
        fChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        fChooser.setInitialDirectory(new File(DEFAULT_DIRECTORY));
        stage = new Stage();
    }

    /**
     * Opens an output stream, writes the text into the file and closes the stream afterwards
     * @param output file to which the data will be written
     * @param text data that will be written
     */
    private void actualSavingProcess(File output, String text) {
        try {
            PrintWriter outputStream = new PrintWriter(file);
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
     * Opens a filechooser and the user can then select the file he want to load. Besides
     * returning the String value, the file attribut in the Logic class will be updated
     *
     * Source: https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
     *
     * @return String value saved in the selected file
     */
    public String openFile() {
        this.file = fChooser.showOpenDialog(stage);

        Scanner in = null;
        StringBuilder sb = new StringBuilder();
        try {
            in = new Scanner(new FileReader(this.file));
            while(in.hasNext()) {
                sb.append(in.next());
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


}
