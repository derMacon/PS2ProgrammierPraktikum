package gui;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import logic.Domino;
import logic.Pos;

public class JavaFXGUI {

    private ImageView[][] imgVewsGame;

    protected static final Image EMPTY_IMG = new Image("gui/img/Empty.png");

    private Domino currDomino = null;

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
            this.imgVewsGame[pos.x()][pos.y()].setEffect(effect);
            if (this.currDomino.getRot() % 2 == 0) {
                this.imgVewsGame[pos.x() + 1][pos.y()].setEffect(effect);
            } else {
                this.imgVewsGame[pos.x()][pos.y() + 1].setEffect(effect);
            }
        }
    }

}
