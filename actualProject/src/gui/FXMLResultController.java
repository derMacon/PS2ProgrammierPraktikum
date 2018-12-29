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
//        TreeItem<String> rootItem = new TreeItem<>("Inbox");
//
//        rootItem.setExpanded(true);
//        TreeItem<String> item = new TreeItem<>("Message");
//        
//        rootItem.getChildren().add(item); 
//
//        trVvResults = new TreeView<String>(rootItem);
//        
//        StackPane root = new StackPane();
//        root.getChildren().add(trVvResults);
//        
//        primaryStage.setScene(new Scene(root, 300, 250));
//        primaryStage.show();

    }

    public TreeItem<String> makeBranch(TreeItem<String> parent, String title) {
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }

}
