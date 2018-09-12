package logic.playerState;

import logic.token.Domino;
import logic.logicTransfer.GUIConnector;
import logic.token.Pos;
import logic.token.SingleTile;

public class Board {

    public static final int DEFAULT_BOARD_SIZE = 5;

    private SingleTile[][] cells;

    public Board(GUIConnector gui, int sizeX, int sizeY) {
    }

    public Board() {
        cells = new SingleTile[DEFAULT_BOARD_SIZE][DEFAULT_BOARD_SIZE];
    }

    public int getCols() {
        return cells.length;
    }

    public int getRows() {
        return this.cells[0].length;
    }

    public boolean isValidPos(Pos pos) {
        return 0 < pos.x() && DEFAULT_BOARD_SIZE > pos.x() && 0 < pos.y() && DEFAULT_BOARD_SIZE > pos.y();
    }

    public boolean fits(Domino domino) {
        return false;
    }

    public void lay(Domino domino) {
        assert null != domino;
            Pos posFstTile = domino.getFstPos();
            Pos posSndTile = domino.getFstPos();
        if (isValidPos(posFstTile) && isValidPos(posSndTile)) {
            this.cells[posFstTile.x()][posFstTile.y()] = domino.getFstVal();
            this.cells[posSndTile.x()][posSndTile.y()] = domino.getSndVal();
        }
    }
}
