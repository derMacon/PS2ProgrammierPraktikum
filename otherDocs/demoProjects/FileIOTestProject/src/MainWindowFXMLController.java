import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowFXMLController implements Initializable {

    @FXML
    private TextArea txtFlMainWindow;

    /**
     * Interface with the FileIO processing
     */
    Logic logic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logic = new Logic();
    }

    @FXML
    void saveFile(ActionEvent event) {
        logic.saveFile(txtFlMainWindow.getText());
    }

    @FXML
    public void saveFileAs(ActionEvent event) {
        logic.saveFileAs(txtFlMainWindow.getText());
    }

    @FXML
    void openFile(ActionEvent event) {
        this.txtFlMainWindow.setText(logic.openFile());
    }

}
