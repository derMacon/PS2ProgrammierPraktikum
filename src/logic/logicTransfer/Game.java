package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.playerState.DefaultAIPlayer;
import logic.playerState.HumanPlayer;
import logic.playerState.Player;
import logic.token.Pos;
import logic.token.Domino;

import java.util.LinkedList;
import java.util.List;

//import static logic.Player.HUMAN;

public class Game implements GUI2Game {

    public final static int CURRENT_BANK_IDX = 0;
    public final static int NEXT_BANK_IDX = 1;
    public final static int PLAYER_CNT = 4;

    /**
     * Current round, drawn dominos.
     */
    private Bank currentRoundBank;

    /**
     * Current round, drawn dominos.
     */
    private Bank nextRoundBank;

    /**
     * the stack with all dominos at the beginning.
     */
//    private final List<Domino> stack;

    private Player[] players;

    /**
     * the current player
     */
    private Player currPlayerReference;

    private int currPlayerIdx;

    private List<Domino> stack;

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
        this.currentRoundBank = new Bank(PLAYER_CNT);
        this.nextRoundBank = new Bank(PLAYER_CNT);
        this.stack = new LinkedList<>();
        this.players = new Player[PLAYER_CNT];
        this.currPlayerIdx = 0;
        currPlayerReference = null;
        currDomino = null;
    }


//    /**
//     * for testing: gets the banks of the players and the current domino.
//     *
//     * @param gui
//     * @param sizeX
//     * @param sizeY
//     * @param banks
//     * @param currDomino
//     * @param startDomino
//     */
//    public Game(GUIConnector gui, int sizeX, int sizeY, Domino[][] banks, Domino currDomino, Domino startDomino) {
//        this(gui, sizeX, sizeY);
//        this.currDomino = currDomino;
//        for (Player p : Player.values()) {
//            for (int i = 0; i < banks[p.ordinal()].length; i++) {
//                setToBank(p, i, banks[p.ordinal()][i]);
//            }
//        }
//        setToChooseBox(currDomino);
//        if (startDomino != null) {
//            board.setStarter(startDomino);
//        }
//    }

//    /**
//     * for testing: gets the banks of the players and the current board with its
//     * open ends.
//     *
//     * @param gui
//     * @param banks
//     * @param board
//     * @param openEnds
//     */
//    public Game(GUIConnector gui, Domino[][] banks, String board, Pos[] openEnds) {
//        this(gui, board.split("\n")[0].length(), board.split("\n").length, banks, null, null);
//        this.board = new Board(gui, board, openEnds);
//        gui.updateGrid(this.board);
//    }


    public Game(Bank currentBank, Bank nextRoundBank, Domino currDomino) {
        this.nextRoundBank = nextRoundBank;
        this.currDomino = currDomino;
        this.currentRoundBank = currentBank;
    }

    private void nextRound() {
        // TODO insert code for ending game.

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
        // renew players (containing boards)
        this.players = createNewPlayers();
        this.gui.updateAllPlayers(this.players);

        // fill stack
        this.stack = Domino.fill(this.stack);
        updateBanksDominos();


        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
//        this.gui.setToBank(NEXT_BANK_IDX, this.nextRoundBank);

        this.currPlayerIdx = 0;
    }

    private void updateBanksDominos() {
        drawNewDominosForNextRound();
        getNewBankDominos();
    }

    private void getNewBankDominos() {
        this.currentRoundBank = this.nextRoundBank.copy();
    }

    private void drawNewDominosForNextRound() {
        this.nextRoundBank.drawFromStack(this.stack);
    }



    private Player[] createNewPlayers() {
        Player[] output = new Player[PLAYER_CNT];
        output[0] = new HumanPlayer(gui);
        for (int i = 1; i < PLAYER_CNT; i++) {
            output[i] = new DefaultAIPlayer(gui);
        }
        return output;
    }

    @Override
    public void selectDom(int idx) {
        System.out.println(idx);
        this.currentRoundBank.selectEntry(this.players[currPlayerIdx], idx);
        // TODO rest selects dominos
        this.currDomino = this.currentRoundBank.getDomino(idx);
        this.gui.showInChooseBox(this.currDomino);
    }

}
