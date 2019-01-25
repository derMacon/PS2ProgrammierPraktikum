package gui;

//import other.FakeGUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.dataPreservation.Loader;
import logic.dataPreservation.Logger;
import logic.differentPlayerTypes.PlayerType;
import logic.logicTransfer.GUI2Game;
import logic.logicTransfer.Game;
import logic.playerState.Board;
import logic.token.Pos;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class to handle the gui gui interaction with the user
 */
public class FXMLDocumentController implements Initializable {

    /**
     * Default number of players
     */
    public static final int DEFAULT_PLAYER_COUNT = 4;

    //<editor-fold defaultstate="collapsed" desc="Gui textures">
    /**
     * Icon for the game
     */
    public static final Image LOGO_ICON_TEXTURE = new Image("gui/textures/LogoIconLargeV2.png");

    /**
     * Background for the selection box containing the current- / nextRoundBank
     */
    public static final Image BANK_BOX_TEXTURE = new Image("gui/textures/LargeBoxV1Alpha.png");

    public static final Image LARGE_BOX_OUTLAY_TEXTURE =
            new Image("gui/textures/LargeBoxV3Alpha.png");

    /**
     * Background for the selection box containing the current- / nextRoundBank (rotated)
     */
    public static final Image BANK_BOX_TEXTURE_ROTATED =
            new Image("gui/textures/LargeBoxV1AlphaRotated.png");

    /**
     * Overall Background for the whole main window
     */
    public static final Image BACKGROUND_TEXTURE = new Image("gui/textures/BackgroundV6.png");

    /**
     * Background for the individual player boards
     */
    public static final Image BOARD_BACKGROUND_TEXTURE =
            new Image("gui/textures/SelectedBoxV5Alpha.png");

    /**
     * Background for the rotation box
     */
    public static final Image ROTBOX_TEXTURE = new Image("gui/textures/SelectBoxV4Alpha.png");

    /**
     * Texture for the vertical seperatorors between the different secotions of the window
     */
    public static final Image SEPERATOR_TEXTURE_VERTICAL =
            new Image("gui/textures/SeperatorV2Alpha.png");

    /**
     * Texture for the horizontal seperator between the different secotions of the window
     */
    public static final Image SEPERATOR_TEXTURE_HORIZONTAL =
            new Image("gui/textures/SeperatorV2AlphaRotated.png");

    /**
     * Texture for the chip representing the selection of player 1
     */
    public static final Image SELECTION_PLAYER_1_TEXTURE = new Image("gui/textures/ChipV1.png");

    /**
     * Overlay texture for the dispose field
     */
    public static final Image DISPOSE_TEXTURE = new Image("gui/textures/TrashIconV1.png");

    /**
     * Overlay texture for the dispose field
     */
    public static final Image ROTATION_TEXTURE = new Image("gui/textures/RotationIconV1.png");

    /**
     * Overlay texture for the arrow keys
     */
    public static final Image ARROW_TEXTURE = new Image("gui/textures/ArrowDownV2.png");

    //</editor-fold>

    private static final String RULES_LINK= "http://intern.fh-wedel.de/mitarbeiter/klk/programmierpraktikum-java" +
            "/aufgabentermine/ss18-citydomino/";


    // --- Boards ---
    /**
     * Grid pane representing the board of the human player
     */
    @FXML
    private GridPane grdPnHumanBoard;

    /**
     * Grid pane representing the board of the first bot
     */
    @FXML
    private GridPane grdPnBot1Board;

    /**
     * Grid pane representing the board of the second bot
     */
    @FXML
    private GridPane grdPnBot2Board;

    /**
     * Grid pane representing the board of the third bot
     */
    @FXML
    private GridPane grdPnBot3Board;

    /**
     * Label to display the name of the current player
     */
    @FXML
    private Label lblTurn;

    /**
     * Label to display the name and the current number of points of the first player
     */
    @FXML
    private Label lblPlayer1;

    /**
     * Label to display the name and the current number of points of the second player
     */
    @FXML
    private Label lblPlayer2;

    /**
     * Label to display the name and the current number of points of the third player
     */
    @FXML
    private Label lblPlayer3;

    /**
     * Label to display the name and the current number of points of the fourth player
     */
    @FXML
    private Label lblPlayer4;


    // --- Interactive Items ---
    /**
     * Grid pane representing the current round's bank
     */
    @FXML
    private GridPane grdPnCurrentSelectiveGroup;

    /**
     * Grid pane displaying the corresponding chip showing which player selected the domino for the current round
     */
    @FXML
    private GridPane grdPnCurrentRoundSelection;

    /**
     * Grid pane displaying the corresponding chip showing which player selected the domino for the next round
     */
    @FXML
    private GridPane grdPnNextRoundSelection;

    /**
     * Grid pane representing the next round's bank
     */
    @FXML
    private GridPane grdPnFutureselectiveGroup;

    /**
     * Pane used for rotating the current domino from the human player
     */
    @FXML
    private Pane pnSelected;

    /**
     * Pane to discard the human players current domino
     */
    @FXML
    private Pane pnDiscard;

    /**
     * Button to move the whole board leftward
     */
    @FXML
    private Button btnLeft;

    /**
     * Button to move the whole board upward
     */
    @FXML
    private Button btnUp;

    /**
     * Button to move the whole board rightward
     */
    @FXML
    private Button btnRight;

    /**
     * Button to move the whole board downward
     */
    @FXML
    private Button btnDown;

    /**
     * MenuBar containing the menuitem opening the controll panel to select the enemy player type. It is not possible
     * to get the stage from an menuitem so the Menubar must be signed to detect the stage to make it possible to
     * close it.
     */
    @FXML
    private MenuBar mnBAnchorForStage;


    // --- Grid panes for setting up textures ---
    /**
     * Grid pane setting up the overall background texture
     */
    @FXML
    private GridPane grdPnOverallBackgroundTexture;

    /**
     * Grid pnae setting up the texture for the label showing who's turn it is
     */
    @FXML
    private GridPane grdPnTurnLblTexture;

    /**
     * Grid pane setting up the texture for the current round bank
     */
    @FXML
    private GridPane grdPnCurrentBankTexture;

    /**
     * Grid pane setting up the texture for the next round bank
     */
    @FXML
    private GridPane grdPnNextBankTexture;

    /**
     * Grid pane setting up the texture for the outlay of the large box
     */
    @FXML
    private GridPane grdPnLargeBoxOutlay;

    /**
     * Grid pane setting up the texture for the board from player1
     */
    @FXML
    private GridPane grdPnPlayer1Texture;

    /**
     * Grid pane setting up the texture for the board from player2
     */
    @FXML
    private GridPane grdPnPlayer2Texture;

    /**
     * Grid pane setting up the texture for the board from player3
     */
    @FXML
    private GridPane grdPnPlayer3Texture;

    /**
     * Grid pane setting up the texture for the board from player4
     */
    @FXML
    private GridPane grdPnPlayer4Texture;


    /**
     * Grid pane setting up the texture for the rotation box
     */
    @FXML
    private GridPane grdPnRotBoxTexture;

    /**
     * Grid pane setting up the texture for the dispose box
     */
    @FXML
    private GridPane grdPnDisposeTexture;


    /**
     * Grid pane setting up the texture for first seperator
     */
    @FXML
    private GridPane grdPnSeperator1Texture;

    /**
     * Grid pane setting up the texture for second seperator
     */
    @FXML
    private GridPane grdPnSeperator2Texture;

    /**
     * Grid pane setting up the texture for third seperator
     */
    @FXML
    private GridPane grdPnSeperator3Texture;


    @FXML
    private GridPane grdPnPlayersAI;

    // --- actual fields ---
    /**
     * Reference to the game that will be played
     */
    private GUI2Game game;

    /**
     * Reference to the internal gui controller (setting values generated by the logic)
     */
    private JavaFXGUI gui;

    /**
     * Array serving as blueprint for the playertypes in the game
     */
    private PlayerType[] chosenPlayerTypes;


    // --- Setting up game ---
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUpGuiTextures();

        ImageView[][] imgVwsHumanBoard = addImageViewsToGrid(grdPnHumanBoard);
        this.addDragAndDropHandlers(imgVwsHumanBoard);

        ImageView[][][] imgVwsAIBoards = new ImageView[][][]{addImageViewsToGrid(grdPnBot1Board),
                addImageViewsToGrid(grdPnBot2Board), addImageViewsToGrid(grdPnBot3Board)};

        this.gui = new JavaFXGUI(pnSelected, lblTurn, imgVwsHumanBoard, imgVwsAIBoards,
                addImageViewsToGrid(grdPnCurrentSelectiveGroup), addImageViewsToGrid(grdPnCurrentRoundSelection),
                addImageViewsToGrid(grdPnFutureselectiveGroup), addImageViewsToGrid(grdPnNextRoundSelection),
                new Label[]{this.lblPlayer1, this.lblPlayer2, this.lblPlayer3, this.lblPlayer4});
        this.game = new Game(gui, DEFAULT_PLAYER_COUNT);
    }

    /**
     * Setter for the PlayerTypes to pass chosen PlayerTypes from other controller
     *
     * @param chosenPlayerTypes array of playertypes which the player players of the game will have
     */
    public void startGame(PlayerType[] chosenPlayerTypes) {
        this.game.startGame(chosenPlayerTypes, this.grdPnHumanBoard.getColumnConstraints().size(),
                this.grdPnHumanBoard.getRowConstraints().size());
    }


    // --- Setting up gui look (textures) ---

    /**
     * Sets up the gui with all necessary textures
     */
    private void setUpGuiTextures() {
        setPnWithImageAsBackground(this.grdPnTurnLblTexture, BANK_BOX_TEXTURE_ROTATED);
        setPnWithImageAsBackground(this.grdPnCurrentBankTexture, BANK_BOX_TEXTURE);
        setPnWithImageAsBackground(this.grdPnNextBankTexture, BANK_BOX_TEXTURE);
        setPnWithImageAsBackground(this.grdPnLargeBoxOutlay, LARGE_BOX_OUTLAY_TEXTURE);

        setPnWithImageAsBackground(this.grdPnPlayer1Texture, BOARD_BACKGROUND_TEXTURE);
        setPnWithImageAsBackground(this.grdPnPlayer2Texture, BOARD_BACKGROUND_TEXTURE);
        setPnWithImageAsBackground(this.grdPnPlayer3Texture, BOARD_BACKGROUND_TEXTURE);
        setPnWithImageAsBackground(this.grdPnPlayer4Texture, BOARD_BACKGROUND_TEXTURE);

        setPnWithImageAsBackground(this.grdPnRotBoxTexture, ROTATION_TEXTURE);
        setPnWithImageAsBackground(this.grdPnRotBoxTexture, BOARD_BACKGROUND_TEXTURE);
        setPnWithImageAsBackground(this.grdPnDisposeTexture, DISPOSE_TEXTURE);
        setPnWithImageAsBackground(this.grdPnDisposeTexture, BOARD_BACKGROUND_TEXTURE);

        setPnWithImageAsBackground(this.grdPnSeperator1Texture, SEPERATOR_TEXTURE_VERTICAL);
        setPnWithImageAsBackground(this.grdPnSeperator2Texture, SEPERATOR_TEXTURE_VERTICAL);
        setPnWithImageAsBackground(this.grdPnSeperator3Texture, SEPERATOR_TEXTURE_HORIZONTAL);

        this.btnDown.setGraphic(new ImageView(ARROW_TEXTURE));
        this.btnLeft.setGraphic(new ImageView(ARROW_TEXTURE));
        this.btnLeft.setRotate(90);
        this.btnUp.setGraphic(new ImageView(ARROW_TEXTURE));
        this.btnUp.setRotate(180);
        this.btnRight.setGraphic(new ImageView(ARROW_TEXTURE));
        this.btnRight.setRotate(270);

        setPnWithImageAsBackground(this.grdPnOverallBackgroundTexture, BACKGROUND_TEXTURE);
    }

    /**
     * Adds a given Image as Foreground to a given pane
     *
     * @param pane  pane to add the ImageView to
     * @param image Image to add
     */
    private void setPnWithImageAsForeground(Pane pane, Image image) {
        setPnWithImage(pane, image, true);
    }

    /**
     * Adds an Imageview Node to a given Pane, when addAsForeground == true the image will be added on top of all nodes
     * in the pane, otherwise it will be added in the background.
     *
     * @param pane            Pane to add the ImageView to
     * @param image           Image to add
     * @param addAsForeground determines if the image should be added in the fore- or background
     */
    private void setPnWithImage(Pane pane, Image image, boolean addAsForeground) {
        ImageView imgVW = new ImageView(image);
        imgVW.setPreserveRatio(false);
        imgVW.fitHeightProperty().bind(pane.heightProperty());
        imgVW.fitWidthProperty().bind(pane.widthProperty());
        if (addAsForeground) {
            pane.getChildren().add(imgVW);
        } else {
            pane.getChildren().add(0, imgVW);
        }
    }

    /**
     * Adds a given Image as Background to a given pane
     *
     * @param pane  pane to add the ImageView to
     * @param image Image to add
     */
    private void setPnWithImageAsBackground(Pane pane, Image image) {
        setPnWithImage(pane, image, false);
    }


    // --- Setting up interactive gui ---

    /**
     * Drag and drop handler used to highlight position of domino while hovering over the board
     *
     * @param imgVws multidimensional array holding the images of the board
     */
    private void addDragAndDropHandlers(ImageView[][] imgVws) {
        for (int x = 0; x < imgVws.length; x++) {
            for (int y = 0; y < imgVws[x].length; y++) {
                final int fx = x;
                final int fy = y;
                imgVws[x][y].setOnDragOver((EventHandler<DragEvent>) (DragEvent event) -> {
                    if (event.getGestureSource() == pnSelected) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
                    event.consume();
                });
                imgVws[x][y].setOnDragEntered((EventHandler<DragEvent>) (DragEvent event) -> {
                    Pos pos = new Pos(fx, fy);
                    if (this.game.fits(pos)) {
                        this.gui.highlightDominoPosGreen(pos);
                    } else {
                        this.gui.highlightDominoPosRed(pos);
                    }
                    event.consume();
                });
                imgVws[x][y].setOnDragExited((EventHandler<DragEvent>) (DragEvent event) -> {
                    this.gui.removeHighlightDominoPos(new Pos(fx, fy));
                    event.consume();
                });
                imgVws[x][y].setOnDragDropped((EventHandler<DragEvent>) (DragEvent event) -> {
                    boolean success = false;
                    Pos pos = new Pos(fx, fy);
                    if (this.game.fits(pos)) {
                        success = true;
                        this.gui.removeHighlightDominoPos(pos);
                        this.game.setOnBoard(pos);
                    }
                    event.setDropCompleted(success);
                    event.consume();
                });
            }
        }
    }

    /**
     * creates an array of imageviews corresponding to the gridPane. Each
     * imageView becomes a child of the gridPane and fills a cell. For proper
     * resizing it is binded to the gridPanes width and height.
     *
     * @param grdPn parent of the output array
     * @return an array of imageviews added to the gridPane
     */
    private ImageView[][] addImageViewsToGrid(GridPane grdPn) {
        int colcount = grdPn.getColumnConstraints().size();
        int rowcount = grdPn.getRowConstraints().size();

        ImageView[][] imgVwsGame = new ImageView[colcount][rowcount];

        for (int x = 0; x < colcount; x++) {
            for (int y = 0; y < rowcount; y++) {
                //creates an imageview with the empty image (for some reasons there needs to an image there for the
                // drag and drop to work :()
                imgVwsGame[x][y] = new ImageView(JavaFXGUI.EMPTY_IMG);

                //add the imageview to the cell and
                //assign the correct indicees for this imageview, so you later can use GridPane.getColumnIndex(Node)
                grdPn.add(imgVwsGame[x][y], x, y);

                //the image shall resize when the cell resizes
                imgVwsGame[x][y].fitWidthProperty().bind(grdPn.widthProperty().divide(colcount).
                        subtract(grdPn.getHgap()));
                imgVwsGame[x][y].fitHeightProperty().bind(grdPn.heightProperty().divide(rowcount).
                        subtract(grdPn.getVgap()));
            }
        }
        return imgVwsGame;
    }


    // --- Menu interaction ---

    /**
     * Possible Intro Window to determine playertypes, not used in this version of the game since there is only one
     * type of Bot playertype
     *
     * @param event user action on the gui
     * @throws IOException will be thrown if fxml doc cannot be loaded
     */
    @FXML
    private void onClickSelectPlayerTypes(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLIntro.fxml"));
        Stage introStage = new Stage();
        introStage.setTitle("Auswahl: Gegnertypen");
        introStage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
        introStage.setScene(new Scene(root));
        introStage.show();
        // Casts to stage to be able to close the intro stage with code
        // https://stackoverflow.com/questions/13246211/javafx-how-to-get-stage-from-controller-during-initialization
        ((Stage) this.mnBAnchorForStage.getScene().getWindow()).close();
    }

    /**
     * opens a new game window with a new game instance
     *
     * @param event user action on the gui
     * @throws Exception will be thrown if it is not possible to open a new game instance
     */
    @FXML
    private void openNewGameInstance(ActionEvent event) throws Exception {
        if (!this.game.equalsStr(Loader.openSavedFile())) {
            showConfirmationDialog(event);
        }
        Loader.getInstance().setFile(null);
        Main main = new Main();
        main.start(new Stage());
        // Casts to stage to be able to close the intro stage with code
        // https://stackoverflow.com/questions/13246211/javafx-how-to-get-stage-from-controller-during
        // -initialization
        ((Stage) this.mnBAnchorForStage.getScene().getWindow()).close();
    }

    /**
     * Saves the current game instance to the already specified location. If no location was specified earlier on a
     * new Filechooser will be opened so that the user can select a new location.
     *
     * @param event user action on the gui
     */
    @FXML
    private void mnTmSaveGame(ActionEvent event) {
        Loader.getInstance().saveFile(this.game.toFile());
    }

    /**
     * Opens a filechooser and saves the game to the selected location
     *
     * @param event user action on the gui
     */
    @FXML
    private void mnTmSaveGameAs(ActionEvent event) {
        Loader.getInstance().saveFileAs(this.game.toFile());
    }

    /**
     * Loads a game from the specified location
     *
     * @param event user action on the gui
     * @throws FileNotFoundException will be thrown if the specified file was not found
     */
    @FXML
    private void mnTmLoadGame(ActionEvent event) throws FileNotFoundException {
        if (!this.game.equalsStr(Loader.openSavedFile())) {
            showConfirmationDialog(event);
        }
        Loader.getInstance().setFile(null);

        this.game = new Game(this.gui, Loader.getInstance().openFileChooser());
    }

    /**
     * https://code.makery.ch/blog/javafx-dialogs-official/
     * Show Confirmationdialog window, click on ok to save the file / abbrechen to cancel the whole process
     *
     * @param event event which was passed from the calling mathod to make it possible to call the method to save the
     *              file
     */
    private void showConfirmationDialog(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Verwerfen des Spielstandes");
        alert.setHeaderText("Sie haben den aktuellen Spielstand noch nicht abgespeichert.");
        alert.setContentText("Wollen Sie dies jetzt nachholen?");
        // Get the Stage.
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(FXMLDocumentController.LOGO_ICON_TEXTURE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            mnTmSaveGameAs(event);
        }
    }

    /**
     * Exits game
     *
     * @param event user action on the gui
     */
    @FXML
    private void exitGame(ActionEvent event) {
        if (!this.game.equalsStr(Loader.openSavedFile())) {
            showConfirmationDialog(event);
        }
        Loader.getInstance().setFile(null);

        System.exit(0);
    }


    // --- Game interactions ---

    /**
     * @param event user action on the gui
     */
    @FXML
    private void onClickGrdPnCurrentBank(MouseEvent event) {
        int x = -1;
        int y = -1;
        boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
        boolean rightClicked = event.getButton() == MouseButton.SECONDARY;

        //determine the imageview of the grid that contains the coordinates of the mouseclick
        //to determine the board-coordinates
        for (Node node : grdPnCurrentSelectiveGroup.getChildren()) {
            if (node instanceof ImageView) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    //to use following methods the columnIndex and rowIndex
                    //must have been set when adding the imageview to the grid
                    x = GridPane.getColumnIndex(node);
                    y = GridPane.getRowIndex(node);
                }
            }
        }

        if (x >= 0 && y >= 0 && leftClicked) {
            this.game.selectDomOnCurrBank(y);
        }
    }

    /**
     * @param event user action on the gui
     */
    @FXML
    private void onClickGrdPnNextBank(MouseEvent event) {
        int x = -1;
        int y = -1;
        boolean leftClicked = event.getButton() == MouseButton.PRIMARY;
        boolean rightClicked = event.getButton() == MouseButton.SECONDARY;

        //determine the imageview of the grid that contains the coordinates of the mouseclick
        //to determine the board-coordinates
        for (Node node : grdPnFutureselectiveGroup.getChildren()) {
            if (node instanceof ImageView) {
                if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                    //to use following methods the columnIndex and rowIndex
                    //must have been set when adding the imageview to the grid
                    x = GridPane.getColumnIndex(node);
                    y = GridPane.getRowIndex(node);
                }
            }
        }

        if (x >= 0 && y >= 0 && leftClicked) {
            this.game.selectDomOnNextBank(y);
        }
    }

    /**
     * @param event user action on the gui
     */
    @FXML
    private void onClickPnSelected(MouseEvent event) {
        game.boxClicked();
    }

    /**
     * @param event user action on the gui
     */
    @FXML
    private void onDragDetectedPnSelected(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
        Dragboard db = this.pnSelected.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();

        content.putString("");
        db.setContent(content);

        db.setDragView(this.pnSelected.snapshot(new SnapshotParameters(), null), 10, 10);

        event.consume();

    }

    /**
     * @param event user action on the gui
     */
    @FXML
    private void dispose(MouseEvent event) {
        this.game.disposeCurrDomino();
    }

    /**
     * Moves Board upwards
     */
    @FXML
    private void moveBoardUp() {
        this.game.moveBoard(Board.Direction.UP_MOVE);
    }

    /**
     * Moves Board to the right
     */
    @FXML
    private void moveBoardRight() {
        this.game.moveBoard(Board.Direction.RIGHT_MOVE);
    }

    /**
     * Moves Board downwards
     */
    @FXML
    private void moveBoardDown() {
        this.game.moveBoard(Board.Direction.DOWN_MOVE);
    }

    /**
     * Moves Board to the left
     */
    @FXML
    private void moveBoardLeft() {
        this.game.moveBoard(Board.Direction.LEFT_MOVE);
    }

    /**
     * Opens the rules of the game in the default browser
     */
    @FXML
    private void openRulesInPdfReader() {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(RULES_LINK));
            } catch (IOException | URISyntaxException e) {
                Logger.getInstance().printAndSafe("Not possible to open the following link:\n" + RULES_LINK);
            }
        }
    }


}
