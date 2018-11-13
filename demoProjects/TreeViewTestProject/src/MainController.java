import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    TreeView<String> treeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);

        TreeItem<String> nodeA = new TreeItem<>("Node A");
        TreeItem<String> nodeB = new TreeItem<>("Node B");
        TreeItem<String> nodeC = new TreeItem<>("Node C");

        root.getChildren().addAll(nodeA, nodeB, nodeC);
        treeView.setRoot(root);
    }
}
