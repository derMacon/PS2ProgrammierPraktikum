package gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class FXMLResultController extends Application {


    @FXML
    private TreeView trVvResults;


    @Override
    public void start(Stage primaryStage) throws Exception {
        TreeItem<String> rootItem = new TreeItem<>();
        rootItem.setExpanded(true);

        trVvResults = new TreeView<String>(rootItem);
    }

    public TreeItem<String> makeBranch(TreeItem<String> parent, String title) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }



}
