package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;

public class GUIFXMLDocumentController implements Initializable {

    private static final String MESSAGE_PREFIX = "****************************\n#";
    private static String PARENT = "./dataOutput/";
    private static String NAME = "logfile.txt";

    private File dir = null;

    @FXML
    private TextArea txtFldUserInput;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new File(PARENT).mkdir();
        this.dir = new File(PARENT + NAME);
    }

    @FXML
    private void logMessage(ActionEvent event) {
        System.out.println(txtFldUserInput.getText());
        appenLogFile(txtFldUserInput.getText());
        this.txtFldUserInput.setText("");
    }

    private void appenLogFile(String input) {
        try (Writer outputStream = new BufferedWriter(new FileWriter(this.dir, true))) {
            outputStream.write(MESSAGE_PREFIX + input + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
