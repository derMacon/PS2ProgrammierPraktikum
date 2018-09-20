package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import logic.logicTransfer.GUI2Game;
import logic.logicTransfer.Game;
import logic.token.Pos;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    public static final int DEFAULT_PLAYER_COUNT = 4;

    // Gui textures
    public static final Image BANK_BOX_TEXTURE = new Image("gui/textures/LargeBoxV1Alpha.png");
    public static final Image BACKGROUND_TEXTURE = new Image("gui/textures/BackgroundV3.png");
    public static final Image BOARD_BACKGROUND_TEXTURE = new Image("gui/textures/SelectedBoxV5Alpha.png");
    public static final Image ROTBOX_TEXTURE = new Image("gui/textures/SelectBoxV4Alpha.png");
    public static final Image SEPERATOR_TEXTURE = new Image("gui/textures/SeperatorV2Alpha.png");
    public static final Image SEPERATOR_TEXTURE_ROTATED = new Image("gui/textures/SeperatorV2AlphaRotated.png");


    @FXML
    private Button btnLeft;

    @FXML
    private GridPane grdPnHumanBoard;

    @FXML
    private GridPane grdPnBot1Board;

    @FXML
    private Pane pnDiscard;

    @FXML
    private Button btnUp;

    @FXML
    private Button btnRight;

    @FXML
    private Pane pnSelected;

    @FXML
    private GridPane grdPnOverallBackgroundTexture;

    @FXML
    private GridPane grdPnNextBankTexture;

    @FXML
    private GridPane grdPnPlayer2Texture;

    @FXML
    private Button btnDown;

    @FXML
    private GridPane grdPnPlayer1Texture;

    @FXML
    private GridPane grdPnPlayer3Texture;

    @FXML
    private GridPane grdPnPlayer4Texture;

    @FXML
    private Label lblPlayer1;

    @FXML
    private Label lblPlayer2;

    @FXML
    private Label lblTurn;

    @FXML
    private GridPane grdPnBot2Board;

    @FXML
    private Label lblPlayer3;

    @FXML
    private Label lblPlayer4;

    @FXML
    private GridPane grdPnCurrentBankTexture2;

    @FXML
    private GridPane grdPnDisposeTexture;

    @FXML
    private GridPane grdPnBot3Board;

    @FXML
    private GridPane grdPnCurrentSelectiveGroup1;

    @FXML
    private GridPane grdPnCurrentSelectiveGroup;

    @FXML
    private GridPane grdPnRotBoxTexture;

    @FXML
    private GridPane grdPnPlayersAI;

    @FXML
    private GridPane grdPnFutureselectiveGroup;

    @FXML
    private GridPane grdPnCurrentBankTexture;

    @FXML
    private GridPane grdPnSeperator1Texture;

    @FXML
    private GridPane grdPnSeperator2Texture;

    @FXML
    private GridPane grdPnSeperator3Texture;



    private GUI2Game game;
    private JavaFXGUI gui;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUpGuiTextures();

        ImageView[][] imgVwsHumanBoard = addImageViewsToGrid(grdPnHumanBoard);
        this.addDragAndDropHandlers(imgVwsHumanBoard);

        ImageView[][][] imgVwsAIBoards = new ImageView[][][] {addImageViewsToGrid(grdPnBot1Board), addImageViewsToGrid(grdPnBot2Board), addImageViewsToGrid(grdPnBot3Board)};

        this.gui = new JavaFXGUI(pnSelected, lblTurn, imgVwsHumanBoard, imgVwsAIBoards, addImageViewsToGrid(grdPnCurrentSelectiveGroup), addImageViewsToGrid(grdPnFutureselectiveGroup));
        this.game = new Game(gui, Game.DEFAULT_PLAYER_CNT, this.grdPnHumanBoard.getColumnConstraints().size(), this.grdPnHumanBoard.getRowConstraints().size());
    }
    
    private void setUpGuiTextures() {
        setAPnWithImageAsBackground(grdPnCurrentBankTexture, BANK_BOX_TEXTURE);
        setAPnWithImageAsBackground(grdPnNextBankTexture, BANK_BOX_TEXTURE);

        setAPnWithImageAsBackground(grdPnPlayer1Texture, BOARD_BACKGROUND_TEXTURE);
        setAPnWithImageAsBackground(grdPnPlayer2Texture, BOARD_BACKGROUND_TEXTURE);
        setAPnWithImageAsBackground(grdPnPlayer3Texture, BOARD_BACKGROUND_TEXTURE);
        setAPnWithImageAsBackground(grdPnPlayer4Texture, BOARD_BACKGROUND_TEXTURE);

        setAPnWithImageAsBackground(this.grdPnRotBoxTexture, BOARD_BACKGROUND_TEXTURE);
        setAPnWithImageAsBackground(this.grdPnDisposeTexture, BOARD_BACKGROUND_TEXTURE);

        setAPnWithImageAsBackground(this.grdPnSeperator1Texture, SEPERATOR_TEXTURE);
        setAPnWithImageAsBackground(this.grdPnSeperator2Texture, SEPERATOR_TEXTURE);
        setAPnWithImageAsBackground(this.grdPnSeperator3Texture, SEPERATOR_TEXTURE_ROTATED);

        setAPnWithImageAsBackground(grdPnOverallBackgroundTexture, BACKGROUND_TEXTURE);
    }

    private void setAPnWithImageAsBackground(Pane pane, Image image) {
        setAPnWithImage(pane, image, false);
    }

    private void setAPnWithImageAsForeground(Pane pane, Image image) {
        setAPnWithImage(pane, image, true);
    }

    /**
     * https://stackoverflow.com/questions/42172015/javafxscenebuilder-how-can-i-automatically-resize-the-scene-when-the-window?rq=1
     * @param pane
     * @param image
     */
    private void setAPnWithImage(Pane pane, Image image, boolean addAsForeground) {
        ImageView imgVW = new ImageView(image);
        imgVW.setPreserveRatio(false);
//        imgVW.setX(pane.getHeight());
//        System.out.println(pane.getProperties());
//        imgVW.setFitWidth(20);
//        imgVW.fitHeightProperty().bind(pane.translateYProperty());
        imgVW.fitHeightProperty().bind(pane.heightProperty());
        
//        imgVW.fitWidthProperty().bind(pane.translateXProperty());
        imgVW.fitWidthProperty().bind(pane.widthProperty());

//        imgVwsGame[x][y].fitWidthProperty().bind(grdPn.widthProperty().divide(colcount).subtract(grdPn.getHgap()));
        if(addAsForeground) {
            pane.getChildren().add(imgVW);
        } else {
            pane.getChildren().add(0, imgVW);
        }
    }

    @FXML
    private void onClickStartGame(ActionEvent event) {
        this.game.startGame();
    }

    @FXML
    private void onClickGrdPnBank(MouseEvent event) {
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

    @FXML
    private void onClickPnSelected(MouseEvent event) {
        game.boxClicked();
        System.out.println("Auswahlbox auswgewaehlt.");
    }

    @FXML
    private void onDragDetectedPnSelected(MouseEvent event) {
        System.out.println("Drag");
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
     * creates an array of imageviews corresponding to the gridPane. Each
     * imageView becomes a child of the gridPane and fills a cell. For proper
     * resizing it is binded to the gridPanes width and height.
     *
     * @return an array of imageviews added to the gridPane
     */
    private ImageView[][] addImageViewsToGrid(GridPane grdPn) {
        int colcount = grdPn.getColumnConstraints().size();
        int rowcount = grdPn.getRowConstraints().size();

        ImageView[][] imgVwsGame = new ImageView[colcount][rowcount];

        for (int x = 0; x < colcount; x++) {
            for (int y = 0; y < rowcount; y++) {
                //creates an imageview with the empty image (for some reasons there needs to an image there for the drag and drop to work :()
                imgVwsGame[x][y] = new ImageView(JavaFXGUI.EMPTY_IMG);

                //add the imageview to the cell and
                //assign the correct indicees for this imageview, so you later can use GridPane.getColumnIndex(Node)
                grdPn.add(imgVwsGame[x][y], x, y);

                //the image shall resize when the cell resizes
                imgVwsGame[x][y].fitWidthProperty().bind(grdPn.widthProperty().divide(colcount).subtract(grdPn.getHgap()));
                imgVwsGame[x][y].fitHeightProperty().bind(grdPn.heightProperty().divide(rowcount).subtract(grdPn.getVgap()));
            }
        }
        return imgVwsGame;
    }


    /**
     * Moves Board upwards
     */
    @FXML
    private void moveBoardUp() {

    }

    /**
     * Moves Board to the right
     */
    @FXML
    private void moveBoardRight() {

    }

    /**
     * Moves Board downwards
     */
    @FXML
    private void moveBoardDown() {
        System.out.println("move board down");
    }

    /**
     * Moves Board to the left
     */
    @FXML
    private void moveBoardLeft() {

    }


}
