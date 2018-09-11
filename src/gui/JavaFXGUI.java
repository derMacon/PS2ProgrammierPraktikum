package gui;

import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.*;
import logic.BankSelection.Bank;
import logic.PlayerState.Player;
import logic.Token.Pos;
import logic.LogicTransfer.GUIConnector;
import logic.Token.Domino;


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
    private ImageView[][][] imgVwsProvidedBank;

    public JavaFXGUI(Pane pnSelected, Label lblTurn, ImageView[][] imgWssPlayerBoard, ImageView[][][] imgWwsAIBoards, ImageView[][][] imgVwsProvidedBank) {
        this.pnSelected = pnSelected;
        this.lblTurn = lblTurn;
        this.imgVwsPlayerBoard = imgWssPlayerBoard;
        this.imgWwsAIBoards = imgWwsAIBoards;
        this.imgVwsProvidedBank = imgVwsProvidedBank;

        //loadAllImages
        imgs = new Image[IMG_COUNT];
        for (int i = 0; i < imgs.length; ++i) {
            this.imgs[i] = this.loadImage(i);
        }
    }

    @Override
    public void setToBank(int ordBank, Bank bank) {
        Image[][] newBank = genImagesFromBank(bank);
        updateCurrentBankWithNewBank(newBank);
    }

    private Image[][] genImagesFromBank(Bank bank) {
        Image[][] output = new Image[4][2];
        Image[] imgDominos = null;

        Domino[] domFromBank = bank.getDominos();
        for (int i = 0; i < domFromBank.length; i++) {
            imgDominos = genDominoImg(i, domFromBank[i]);
            output[i] = imgDominos;
        }
        return output;
    }

    private Image[] genDominoImg(int domIdx, Domino domino) {
        Image[] output = new Image[2];
        output[0] = getImage(domino.getFstVal().ordinal());
        output[1] = getImage(domino.getSndVal().ordinal());
        return output;
    }

    private void updateCurrentBankWithNewBank(Image[][] newBank) {
        for (int i = 0; i < newBank.length; i++) {
            for (int j = 0; j < 2; j++) {
                this.imgVwsProvidedBank[Bank.CURRENT_BANK_IDX][i][j].setImage(newBank[i][j]);
            }
        }
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

    }

    @Override
    public void selectDomino(int idx) {

    }

    @Override
    public void updateAllPlayers(Player[] players) {
        for(Player pl : players) {
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

    /**
     * Shows the image with the given value at the given position on the game
     * grid.
     *
     * @param pos position on the game grid
     * @param value value for which the image should be displayed at pos
     */
    private void showCellOnGrid(Pos pos, int value) {
//        if (isValidPosOnGameGrid(pos)) {
//            if (value == Board.EMPTY) {
//                this.imgVewsGame[pos.x()][pos.y()].setImage(EMPTY_IMG);
//            } else {
//                this.imgVewsGame[pos.x()][pos.y()].setImage(getImage(value));
//            }
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
    public void showOnGrid(int ordPlayer, Pos fstPos, int fstValue, Pos sndPos, int sndValue) {

    }

    @Override
    public void showResult(Result result) {

    }



    /**
     * Loads one image for one half of a domino tile.
     *
     * @param value of the current tile; should be between 0 and 6, if is not the
     * empty image will be returned
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
        };
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
     * @param pos position of the first half of the domino; the current domino
     * is used to determine whether the cell to the right or below it is marked
     * as well
     * @param effect how the color of the cells given through pos is changed. If
     * is value is null any previous effects are removed.
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
