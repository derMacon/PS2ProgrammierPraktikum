import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Logic {

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

    public Logic() {
        fChooser = new FileChooser();
        fChooser.setTitle("Open Resource File");
        fChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        stage = new Stage();
    }


    public void saveFile(String txtFldInput) {
        file = fChooser.showOpenDialog(stage);
        try {
            PrintWriter outputStream = new PrintWriter(file);
            outputStream.println(txtFldInput);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }




    public void test() {
        System.out.println("------------------FileIOTestProject ------------------\nEnter a file " +
                "content you want so safe");

        Scanner sc = new Scanner(System.in);
        String userInput = sc.next();
    }
}
