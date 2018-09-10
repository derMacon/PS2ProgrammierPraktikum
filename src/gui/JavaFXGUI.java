package gui;

import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.*;


public class JavaFXGUI implements GUIConnector {

    //25 images for each face of a half (0..24)
    private static int IMG_COUNT = 25;
    protected static final Image EMPTY_IMG = new Image("Stuff/EmptyV2.png");

    private Image[] imgs;

    private static final int IDX_FST = 0;
    private static final int IDX_SND = 1;

    private Domino currDomino = null;

    private Pane pnSelected;
    private Label lblTurn;
    private ImageView[][][] imgVwsBoards;
    private ImageView[][][] imgVwsProvidedBank;


    public JavaFXGUI(Pane pnSelected, Label lblTurn, ImageView[][][] imgVwsGame, ImageView[][][] imgVwsBank) {
        this.pnSelected = pnSelected;
        this.lblTurn = lblTurn;
        this.imgVwsBoards = imgVwsGame;
        this.imgVwsProvidedBank = imgVwsBank;

        //loadAllImages
        imgs = new Image[IMG_COUNT];
        for (int i = 0; i < imgs.length; ++i) {
            this.imgs[i] = this.loadImage(i);
        }
    }

    @Override
    public void showWhosTurn(String name) {

    }

    @Override
    public void setToBank(int ordBank, int index, Domino domino) {

    }

    @Override
    public void showInChooseBox(Domino dominoRotated) {

    }

    @Override
    public void updateGrid(Board board) {

    }

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
            this.imgVwsBoards[pos.x()][pos.y()].setEffect(effect);
            if (this.currDomino.getRot() % 2 == 0) {
                this.imgVwsBoards[pos.x() + 1][pos.y()].setEffect(effect);
            } else {
                this.imgVwsBoards[pos.x()][pos.y() + 1].setEffect(effect);
            }
        }
    }

}
