package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.playerState.Board;
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

    private int currPlayerIdx;

    private int roundCount;

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
        currDomino = null;
        this.roundCount = 0;
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
    public Game(GUIConnector gui, int sizeX, int sizeY, Bank[] banks, Domino currDomino, Domino startDomino) {
        this(gui, sizeX, sizeY);
        this.currDomino = currDomino;
        this.currentRoundBank = banks[0].copy();
        this.nextRoundBank = banks[1].copy();
        setToChooseBox(currDomino);
    }

    /**
     * for testing: gets the banks and the board of a player with a given index.
     *
     * @param gui
     * @param banks
     * @param board
     * @param openEnds
     */
    public Game(GUIConnector gui, Bank[] banks, String board, int ordPlayer, int playerType) {
        this(gui, board.split("\n")[0].length(), board.split("\n").length, banks, null, null);
        this.players[ordPlayer] = getPlayerFromType(playerType, gui);
        gui.updateGrid(ordPlayer, this.players[ordPlayer].getBoard());
    }

    public Game(Bank currentBank, Bank nextRoundBank, Domino currDomino) {
        this.nextRoundBank = nextRoundBank;
        this.currDomino = currDomino;
        this.currentRoundBank = currentBank;
    }

    private Player getPlayerFromType(int playerType, GUIConnector gui) {
        switch (playerType) {
            default:
                return new DefaultAIPlayer(gui);
        }
    }

    /**
     * Sets a given domino on as the new current domino and shows it in the
     * rotation box of the gui.
     *
     * @param currDomino domino that will be setted as the new current domino
     */
    private void setToChooseBox(Domino currDomino) {
        this.gui.showInChooseBox(currDomino);
        this.currDomino = currDomino;
    }

    @Override
    public void boxClicked() {
        if (null != this.currDomino) {
            System.out.println("Rot box clicked");
            this.currDomino.incRot();
            this.gui.showInChooseBox(this.currDomino);
        }
    }

    @Override
    public boolean fits(Pos pos) {
        // TODO insert code
        return true;
    }

    @Override
    public boolean domFits(Domino dom, Board board) {
        // TODO insert code
        return false;
    }

    @Override
    public void setOnBoard(Pos pos) {
//        if (currDomino != null && board.fits(currDomino, posFst)) {
        System.out.println("setOnBoard -> Game");
        currDomino.setPos(new Pos(pos.x(), pos.y()));
        this.players[this.currPlayerIdx].showOnBoard(currDomino);
        this.gui.showOnGrid(this.currPlayerIdx, this.currDomino);
//            setToChooseBox(null);
//            nextPlayer();
//        }
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
        this.roundCount = 0;
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

    /**
     * Creates a new Player array. Used to initialize the players field.
     *
     * @return new Player array with the human player on the first index and the
     * default player on the remaining array slots.
     */
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
        if (0 == this.roundCount) { // initial selection on first bank
            // update human player selection
            this.currentRoundBank.selectEntry(this.players[currPlayerIdx], idx);
            this.currDomino = this.currentRoundBank.getDomino(idx);

            // update rest of the players 
            for (int i = 1; i < this.players.length; i++) {
                this.players[i].selectFromBank(this.currentRoundBank);
            }

            // players which selected lower dominos can select earlier
            int counter = 0;
            Player temp = this.currentRoundBank.getSelectedPlayer(counter);
            while (!(temp instanceof HumanPlayer)) {
                temp.selectFromBank(this.nextRoundBank);
            }

        } else {
            // selection after first round, always from second bank
            // TODO insert code
        }
        this.gui.showInChooseBox(this.currDomino);
    }

    private void nextRound() {
        /* TODO insert code for ending game / setting up next round 
            - draw new dominos, 
            - copy banks 
         */
    }
}
