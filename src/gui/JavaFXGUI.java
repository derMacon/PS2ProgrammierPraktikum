package gui;

import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import logic.*;
import logic.bankSelection.Bank;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.token.Pos;
import logic.logicTransfer.GUIConnector;
import logic.token.Domino;
import logic.token.Tiles;


public class JavaFXGUI implements GUIConnector {

    //25 images for each face of a half (0..24)
    private static int IMG_COUNT = 25;
    protected static final Image EMPTY_IMG = new Image("other/EmptyV2.png");

    private Image[] imgs;

    private static final int IDX_FST = 0;
    private static final int IDX_SND = 1;

    private Domino currDomino = null;

    private Pane pnSelected;
    private Label lblTurn;
    private ImageView[][] imgVwsPlayerBoard;
    private ImageView[][][] imgWwsAIBoards;
    //    private ImageView[][][] imgVwsProvidedBank;
    private ImageView[][] imgVwsCurrentBank;
    private ImageView[][] imgVwsNextBank;

    public JavaFXGUI(Pane pnSelected, Label lblTurn, ImageView[][] imgWssPlayerBoard, ImageView[][][] imgWwsAIBoards,
                     ImageView[][] imgVwsCurrentBank, ImageView[][] imgVwsNextBank) {
        this.pnSelected = pnSelected;
        this.lblTurn = lblTurn;
        this.imgVwsPlayerBoard = imgWssPlayerBoard;
        this.imgWwsAIBoards = imgWwsAIBoards;
//        this.imgVwsProvidedBank = imgVwsProvidedBank;
        this.imgVwsCurrentBank = imgVwsCurrentBank;
        this.imgVwsNextBank = imgVwsNextBank;

        //loadAllImages
        imgs = new Image[IMG_COUNT];
        for (int i = 0; i < imgs.length; ++i) {
            this.imgs[i] = this.loadImage(i);
        }
    }


    @Override
    public void setToBank(int ordBank, Bank bank) {
        Domino[] dominos = bank.getAllDominos();
        for (int i = 0; i < dominos.length; i++) {
            setToBank(ordBank, i, dominos[i]);
        }
    }


    public void setToBank(int ordBank, int index, Domino domino) {
        ImageView[][] imgVwsBank = ordBank == Bank.CURRENT_BANK_IDX ? this.imgVwsCurrentBank : this.imgVwsNextBank;
        if (index >= 0 && index < imgVwsBank[IDX_FST].length) {
            Image[] imgs = (domino != null) ? this.getImagesForTile(domino.getTile()) : this.getImagesForTile(null);
            imgVwsBank[IDX_FST][index].setImage(imgs[IDX_FST]);
            imgVwsBank[IDX_SND][index].setImage(imgs[IDX_SND]);
        }
    }

    /**
     * Returns the appropriate images for the tile. The images are pre-loaded.
     *
     * @param tile the tile for which the image should be determined
     * @return an array with two images (first and second of the tile)
     */
    private Image[] getImagesForTile(Tiles tile) {
        Image[] imgs = (tile != null) ? new Image[]{
                getImage(tile.getFst().ordinal()), getImage(tile.getSnd().ordinal())}
                : new Image[]{EMPTY_IMG, EMPTY_IMG};
        return imgs;
    }

    /**
     * Returns the appropriate pre-loaded image for the given value.
     *
     * @param value value for the image should be returned
     * @return image if the value was valid, otherwise the empty image
     */
    private Image getImage(int value) {
        return (value >= 0 && value <= this.imgs.length)
                ? this.imgs[value]
                : EMPTY_IMG;
    }


    @Override
    public void showWhosTurn(String name) {

    }

    @Override
    public void showPointsForPlayer(Player pl, int points) {

    }


    @Override
    public void showInChooseBox(Domino dominoRotated) {
        this.currDomino = dominoRotated;
        this.pnSelected.getChildren().clear();

        if (dominoRotated != null) {
            int idxFst = IDX_FST;
            int idxSnd = IDX_SND;
            ImageView[] imgVwsSelected = new ImageView[]{new ImageView(), new ImageView()};

            GridPane grdPn = new GridPane();
            grdPn.getColumnConstraints().add(this.getColConstraints());
            grdPn.getRowConstraints().add(this.getRowConstraints());

            if (dominoRotated.getRot() % 2 == 0) { //domino is horizontal
                grdPn.getColumnConstraints().add(getColConstraints());

                for (int i = 0; i <= 1; ++i) {
                    grdPn.add(imgVwsSelected[i], i, 0);
                    imgVwsSelected[i].fitWidthProperty().bind(grdPn.widthProperty().divide(2));
                    imgVwsSelected[i].fitHeightProperty().bind(grdPn.heightProperty());
                }
                setPnSelectedHorizontal();
            } else {
                grdPn.getRowConstraints().add(this.getRowConstraints());

                for (int i = 0; i <= 1; ++i) {
                    grdPn.add(imgVwsSelected[i], 0, i);
                    imgVwsSelected[i].fitWidthProperty().bind(grdPn.widthProperty());
                    imgVwsSelected[i].fitHeightProperty().bind(grdPn.heightProperty().divide(2));
                }
                setPnSelectedVertical();
            }

            if (dominoRotated.getRot() >= 2) {
                idxFst = IDX_SND;
                idxSnd = IDX_FST;
            }

            this.pnSelected.getChildren().add(grdPn);
            grdPn.prefWidthProperty().bind(pnSelected.widthProperty());
            grdPn.prefHeightProperty().bind(pnSelected.heightProperty());

            imgVwsSelected[idxFst].setImage(imgs[dominoRotated.getTile().getFst().ordinal()]);
            imgVwsSelected[idxSnd].setImage(imgs[dominoRotated.getTile().getSnd().ordinal()]);
        }
    }

    /**
     * Creates a new column constraints for a grid pane. The column has always
     * the width of 100%.
     *
     * @return new column constraints for grid pane
     */
    private ColumnConstraints getColConstraints() {
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        column.setHgrow(Priority.ALWAYS);
        column.setMinWidth(10.0);
        return column;
    }

    /**
     * Changes the properties of the pane where the selected domino is presented
     * to present a horizontal domino.
     */
    private void setPnSelectedHorizontal() {
        this.pnSelected.setLayoutX(17.0);
        this.pnSelected.setLayoutY(44.0);
        this.pnSelected.setPrefHeight(42.0);
        this.pnSelected.setPrefWidth(72.0);

        AnchorPane.setTopAnchor(pnSelected, 40.0);
        AnchorPane.setBottomAnchor(pnSelected, 27.0);
        AnchorPane.setLeftAnchor(pnSelected, 21.0);
        AnchorPane.setRightAnchor(pnSelected, 26.0);
    }

    /**
     * Changes the properties of the pane where the selected domino is presented
     * to present a vertical domino.
     */
    private void setPnSelectedVertical() {
        this.pnSelected.setLayoutX(45.0);
        this.pnSelected.setLayoutY(21.0);
        this.pnSelected.setPrefHeight(71.0);
        this.pnSelected.setPrefWidth(42.0);

        AnchorPane.setTopAnchor(pnSelected, 29.0);
        AnchorPane.setBottomAnchor(pnSelected, 10.0);
        AnchorPane.setLeftAnchor(pnSelected, 40.0);
        AnchorPane.setRightAnchor(pnSelected, 45.0);

    }

    /**
     * Creates a new row constraints for a grid pane. The row has always the
     * height of 100%.
     *
     * @return new row constraints for grid pane
     */
    private RowConstraints getRowConstraints() {
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(100);
        row.setVgrow(Priority.ALWAYS);
        row.setMinHeight(10.0);
        return row;
    }


    @Override
    public void selectDomino(int idx) {

    }

    @Override
    public void updateAllPlayers(Player[] players) {
        for (Player pl : players) {
            updatePlayer(pl);
        }
    }

    @Override
    public void updatePlayer(Player player) {
//        Board board = player.getBoard();
//        int width = board.getCols();
//        int height = board.getRows();
//        for (int i = 0; i < width; ++i) {
//            for (int j = 0; j < height; ++j) {
//                Pos pos = new Pos(i, j);
//                showCellOnGrid(pos, board.getCell(pos));
//            }
//        }
    }

    @Override
    public void setDominoOnGui(Player pl, Domino dom) {
//        if(pl instanceof HumanPlayer) {
//            Pos domPos = dom.getFstPos();
//            this.imgVwsPlayerBoard[domPos.x()][domPos.y()] = dom.getFstVal();
//        }
    }

//
//    private boolean isValidPosOnGameGrid(Pos pos) {
//        return pos.x() >= 0
//                && pos.x() < this.imgVewsGame.length
//                && pos.y() >= 0
//                && pos.y() < this.imgVewsGame[pos.x()].length;
//    }


    @Override
    public void showOnGrid(int ordPlayer, Domino domino) {
        System.out.println("show on grid -> javafxgui");
        ImageView[][] board = 0 == ordPlayer ? this.imgVwsPlayerBoard : this.imgWwsAIBoards[ordPlayer];
        showCellOnGrid(board, domino.getFstPos(), domino.getFstVal().ordinal());
        showCellOnGrid(board, domino.getSndPos(), domino.getSndVal().ordinal());
    }


    /**
     * Shows the image with the given value at the given position on the game
     * grid.
     *
     * @param pos   position on the game grid
     * @param value value for which the image should be displayed at pos
     */
    private void showCellOnGrid(ImageView[][] imgVwBoard, Pos pos, int value) {
        if (isValidPosOnGameGrid(imgVwBoard, pos)) {
            imgVwBoard[pos.x()][pos.y()].setImage(getImage(value));
        }
    }

    private boolean isValidPosOnGameGrid(ImageView[][] board, Pos pos) {
        return null != board && null != pos && Board.BOARD_SIZE == board.length
                && null != board[0] && Board.BOARD_SIZE == board[0].length;
    }

    @Override
    public void showResult(Result result) {

    }


    /**
     * Loads one image for one half of a domino tile.
     *
     * @param value of the current tile; should be between 0 and 6, if is not the
     *              empty image will be returned
     * @return image with appropriate number of eyes; empty image if there is
     * not image available for desired number of eyes
     */
    private Image loadImage(int value) {
        Image img = null;
        switch (value) {
            case 0:
                img = new Image("gui/img/CityHall_Empty.png");
                break;
            case 1:
                img = new Image("gui/img/Amusement_0.png");
                break;
            case 2:
                img = new Image("gui/img/Amusement_1.png");
                break;
            case 3:
                img = new Image("gui/img/Amusement_2.png");
                break;
            case 4:
                img = new Image("gui/img/Amusement_3.png");
                break;
            case 5:
                img = new Image("gui/img/Industry_0.png");
                break;
            case 6:
                img = new Image("gui/img/Industry_1.png");
                break;
            case 7:
                img = new Image("gui/img/Industry_2.png");
                break;
            case 8:
                img = new Image("gui/img/Industry_3.png");
                break;
            case 9:
                img = new Image("gui/img/Office_0.png");
                break;
            case 10:
                img = new Image("gui/img/Office_1.png");
                break;
            case 11:
                img = new Image("gui/img/Office_2.png");
                break;
            case 12:
                img = new Image("gui/img/Office_3.png");
                break;
            case 13:
                img = new Image("gui/img/Park_0.png");
                break;
            case 14:
                img = new Image("gui/img/Park_1.png");
                break;
            case 15:
                img = new Image("gui/img/Park_2.png");
                break;
            case 16:
                img = new Image("gui/img/Park_3.png");
                break;
            case 17:
                img = new Image("gui/img/Shopping_0.png");
                break;
            case 18:
                img = new Image("gui/img/Shopping_1.png");
                break;
            case 19:
                img = new Image("gui/img/Shopping_2.png");
                break;
            case 20:
                img = new Image("gui/img/Shopping_3.png");
                break;
            case 21:
                img = new Image("gui/img/Home_0.png");
                break;
            case 22:
                img = new Image("gui/img/Home_1.png");
                break;
            case 23:
                img = new Image("gui/img/Home_2.png");
                break;
            case 24:
                img = new Image("gui/img/Home_3.png");
                break;
            default:
                img = EMPTY_IMG;
        }
        ;
        return img;
    }


    /**
     * Highlights two cells (= place for a domino) on the game grid in green.
     *
     * @param pos position of the top-left half of the domino.
     */
    protected void highlightDominoPosGreen(Pos pos) {
        ColorAdjust changeToGreen = new ColorAdjust();
        changeToGreen.setHue(0.75);
        changeToGreen.setSaturation(1.0);
        changeToGreen.setBrightness(0.5);

        addEffectToDominoPos(pos, changeToGreen);
    }

    /**
     * Highlights two cells (= place for a domino) on the game grid in red.
     *
     * @param pos position of the top-left half of the domino.
     */
    protected void highlightDominoPosRed(Pos pos) {
        ColorAdjust changeToGreen = new ColorAdjust();
        changeToGreen.setSaturation(1.0);
        changeToGreen.setBrightness(0.5);

        addEffectToDominoPos(pos, changeToGreen);
    }

    /**
     * Removes the highlight of a domino position on the game grid.
     *
     * @param pos position of the top-left half of the domino.
     */
    protected void removeHighlightDominoPos(Pos pos) {
        addEffectToDominoPos(pos, null);
    }

    /**
     * Marks the position of the domino on the game grid with the given effect.
     * A current domino has to be available. If there is no current domino (so,
     * no domino is the selected box, nothing happens).
     *
     * @param pos    position of the first half of the domino; the current domino
     *               is used to determine whether the cell to the right or below it is marked
     *               as well
     * @param effect how the color of the cells given through pos is changed. If
     *               is value is null any previous effects are removed.
     */
    private void addEffectToDominoPos(Pos pos, ColorAdjust effect) {
        if (this.currDomino != null) {
            this.imgVwsPlayerBoard[pos.x()][pos.y()].setEffect(effect);
            if (this.currDomino.getRot() % 2 == 0) {
                this.imgVwsPlayerBoard[pos.x() + 1][pos.y()].setEffect(effect);
            } else {
                this.imgVwsPlayerBoard[pos.x()][pos.y() + 1].setEffect(effect);
            }
        }
    }

}
