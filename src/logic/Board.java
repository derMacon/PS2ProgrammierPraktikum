package logic;

public class Board {

    private GUIConnector gui;
    public static final int DEFAULT_BOARD_SIZE = 5;

    private SingleTile[][] cells;

    public Board(GUIConnector gui, int sizeX, int sizeY) {
    }

    public Board(GUIConnector gui) {
        this.gui = gui;
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
        if (isValidPos(domino.getFstPos())) {
            gui.updateGrid(this);
        }
    }
}
