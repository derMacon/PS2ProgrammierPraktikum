package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GuiDoodle extends Application {

//    // Loading intro screen
//    @Override
//    public void start(Stage introStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("FXMLResult.fxml"));
//        introStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
//        introStage.setTitle("Auswahl: Gegnertypen");
//        introStage.setScene(new Scene(root));
//        introStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        String catJava = new String("JAVA-00");
        String catJSP = new String("JAVA-01");
        String catSpring = new String("JAVA-02");

        // Root Item
        TreeItem<String> rootItem = new TreeItem<String>(catJava);
        rootItem.setExpanded(true);

        // JSP Item
        TreeItem<String> itemJSP = new TreeItem<String>(catJSP);

        // Spring Item
        TreeItem<String> itemSpring = new TreeItem<>(catSpring);

        // Add to Root
        rootItem.getChildren().addAll(itemJSP, itemSpring);

        TreeView<String> tree = new TreeView<String>(rootItem);

        StackPane root = new StackPane();
//        root.setPadding(new Insets(5));
        root.getChildren().add(tree);

        primaryStage.setTitle("JavaFX TreeView (o7planning.org)");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }


}
