package logic;

import java.util.LinkedList;
import java.util.List;

import static logic.Player.HUMAN;

public class Game implements GUI2Game {

    public final static int BANK_SIZE = 4;
    public final static int PLAYER_CNT = Player.values().length;

    /**
     * Current round, drawn dominos.
     */
    private final Domino[][] currentRoundBank;

    /**
     * Current round, drawn dominos.
     */
    private final Domino[][] nextRoundBank;

    /**
     * the stack with all dominos at the beginning.
     */
    private final List<Domino> stack;

    /**
     * table to lay dominos on
     */
    private Board[] boards;

    /**
     * the current player
     */
    private Player currPlayer;
    /**
     * the current domino
     */
    private Domino currDomino;

    /**
     * the gui to display on
     */
    private GUIConnector gui;

    /**
     * creates a game
     *
     * @param gui gui to display on
     * @param sizeX x-size of the board
     * @param sizeY y-size of the board
     */
    public Game(GUIConnector gui, int sizeX, int sizeY) {
        this.gui = gui;
        this.currentRoundBank = new Domino[Player.values().length][BANK_SIZE];
        this.nextRoundBank = new Domino[Player.values().length][BANK_SIZE];
        this.stack = new LinkedList<>();
        this.board = new Board(gui, sizeX, sizeY);
        currPlayer = HUMAN;
        currDomino = null;
    }


    /**
     * for testing: gets the banks of the players and the current domino.
     *
     * @param gui
     * @param sizeX
     * @param sizeY
     * @param banks
     * @param currDomino
     * @param startDomino
     */
    public Game(GUIConnector gui, int sizeX, int sizeY, Domino[][] banks, Domino currDomino, Domino startDomino) {
        this(gui, sizeX, sizeY);
        this.currDomino = currDomino;
        for (Player p : Player.values()) {
            for (int i = 0; i < banks[p.ordinal()].length; i++) {
                setToBank(p, i, banks[p.ordinal()][i]);
            }
        }
        setToChooseBox(currDomino);
        if (startDomino != null) {
            board.setStarter(startDomino);
        }
    }

    /**
     * for testing: gets the banks of the players and the current board with its
     * open ends.
     *
     * @param gui
     * @param banks
     * @param board
     * @param openEnds
     */
    public Game(GUIConnector gui, Domino[][] banks, String board, Pos[] openEnds) {
        this(gui, board.split("\n")[0].length(), board.split("\n").length, banks, null, null);
        this.board = new Board(gui, board, openEnds);
        gui.updateGrid(this.board);
    }






    @Override
    public void boxClicked() {

    }

    @Override
    public boolean fits(Pos pos) {
        return false;
    }

    @Override
    public void setOnBoard(Pos posFst) {

    }

    @Override
    public void startGame() {

    }

    @Override
    public void clickOnChooseBox() {

    }

}
