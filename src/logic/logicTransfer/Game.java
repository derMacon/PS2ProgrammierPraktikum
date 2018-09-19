package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.dataPreservation.Logger;
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
    public final static int DEFAULT_PLAYER_CNT = 4;


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
    private List<Domino> stack;

    /**
     * Array containing all players (HumanPl inclusive)
     */
    private Player[] players;

    /**
     * Index of the current player
     */
    private int currPlayerIdx;

    /**
     * number of rounds already played
     */
    private int roundCount;

    /**
     * the current domino of the human player (displayed in the rotation box)
     */
    private Domino currDomino;

    /**
     * the gui to display on
     */
    private GUIConnector gui;

    /**
     * Stardard board size (x - dimension)
     */
    private final int standardBoardSizeX;

    /**
     * Stardard board size (y - dimension)
     */
    private final int standardBoardSizeY;

    /**
     * Logger for this game
     */
    private Logger logger;

    /**
     * creates a game
     *
     * @param gui   gui to display on
     * @param sizeX x-size of the board
     * @param sizeY y-size of the board
     */
    public Game(GUIConnector gui, int playerCnt, int sizeX, int sizeY) {
        this.gui = gui;
        this.players = new Player[playerCnt];
        this.standardBoardSizeX = sizeX;
        this.standardBoardSizeY = sizeY;
        this.currentRoundBank = new Bank(playerCnt);
        this.nextRoundBank = new Bank(playerCnt);
        this.stack = new LinkedList<>();
        this.currPlayerIdx = 0;
        this.currDomino = null;
        this.roundCount = 0;
        this.logger = new Logger(playerCnt);
    }


    /**
     * Constructor for testing, setting specific players (with their appropriate Board), current / next round bank, and
     * the domino in the rotate box of the human player. Current player MUST hold a valid Board.
     * <p>
     * Used to skip startGame method with a specific game
     *
     * @param gui              gui for the game
     * @param players          players participating in this game
     * @param currPlayerIdx    index of the current player
     * @param currentRoundBank current round bank
     * @param nextRoundBank    next round bank
     * @param stack            stack of dominos used to fill banks
     * @param roundCount       number of round already played, used for case differention at beginning of the game
     * @param currDomino       domino in the rotation box of the human player
     */
    public Game(GUIConnector gui, Player[] players, int currPlayerIdx, Bank currentRoundBank, Bank nextRoundBank,
                List<Domino> stack, int roundCount, Domino currDomino) {
        this.gui = gui;
        this.players = players;
        this.currentRoundBank = currentRoundBank;
        this.nextRoundBank = nextRoundBank;
        this.stack = stack;
        this.roundCount = roundCount;
        this.currDomino = currDomino;
        this.currPlayerIdx = currPlayerIdx;
        this.standardBoardSizeX = null == players[currPlayerIdx] ? 0 : players[currPlayerIdx].getBoard().getSizeX();
        this.standardBoardSizeY = null == players[currPlayerIdx] ? 0 : players[currPlayerIdx].getBoard().getSizeY();
    }


    private Player getPlayerFromType(int playerType, GUIConnector gui, int boardSizeX, int boardSizeY) {
        switch (playerType) {
            default:
                return new DefaultAIPlayer(gui, boardSizeX, boardSizeY);
        }
    }

    /**
     * Getter for the current bank
     *
     * @return the reference for the current bank
     */
    public Bank getCurrentRoundBank() {
        return currentRoundBank;
    }

    /**
     * Getter for the next bank
     *
     * @return reference for the next bank
     */
    public Bank getNextRoundBank() {
        return nextRoundBank;
    }

    /**
     * Getter for the players array
     *
     * @return
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     * Getter for the number of players (including HumanPlayer)
     */
    public int getNumberOfPlayers() {
        return this.players.length;
    }

    public List<Domino> getStack() {
        return this.stack;
    }

    /**
     * Getter for a specific players board
     *
     * @param playerIdx player index
     * @return a specific players board
     */
    public Board getPlayersBoard(int playerIdx) {
        Board output = null;
        if (isValidPlayerIdx(playerIdx) && null != this.players && null != this.players[playerIdx]) {
            output = this.players[playerIdx].getBoard();
        }
        return output;
    }

    /**
     * Checks if a player index is valid
     *
     * @param idx index that will be checked
     * @return true if given index for a player is valid
     */
    private boolean isValidPlayerIdx(int idx) {
        return 0 <= idx && this.players.length > idx;
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



    private void drawNewDominosForNextRound() {
        this.nextRoundBank.drawFromStack(this.stack);
    }

    private void copyAndRemoveNextRoundBankToCurrentBank() {
        this.currentRoundBank = this.nextRoundBank.copy();
        this.nextRoundBank.clearAllEntries();
    }

    /**
     * Creates a new Player array. Used to initialize the players field.
     *
     * @return new Player array with the human player on the first index and the
     * default player on the remaining array slots.
     */
    private Player[] createNewPlayers() {
        Player[] output = new Player[DEFAULT_PLAYER_CNT];
        output[0] = new HumanPlayer(gui, this.standardBoardSizeX, this.standardBoardSizeY);
        for (int i = 1; i < this.players.length; i++) {
            output[i] = new DefaultAIPlayer(gui, this.standardBoardSizeX, this.standardBoardSizeY);
        }
        return output;
    }

    @Override
    public void startGame() {
        // renew players (containing boards)
        this.players = createNewPlayers();
        this.gui.updateAllPlayers(this.players);

        // fill stack
        this.stack = Domino.fill(this.stack);

        /*
        use of methods that will be used later on in the game. No need to implement a extra function just to initialize
        the first draw to the current bank.
         */
        drawNewDominosForNextRound();
        copyAndRemoveNextRoundBankToCurrentBank();

        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);

        this.currPlayerIdx = 0;
        this.roundCount = 0;
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

            //TODO insert code to update selection on gui

            // players which selected lower dominos can select earlier
            int counter = 0;
            Player temp = this.currentRoundBank.getSelectedPlayer(counter);

            // will only work when selectFromBank is implemented (all players have to selecto something.
//            while (!(temp instanceof HumanPlayer)) {
//                temp.selectFromBank(this.nextRoundBank);
//                counter = (counter + 1) % this.players.length;
//                temp = this.currentRoundBank.getSelectedPlayer(counter);
//            }

        } else {
            /* selection after first round, always from second bank
            TODO insert code
                - player selects
                - Ai players after player select
                - Players before human do turn
                - human calls setOnBoard / dispose
            */
        }
        this.gui.showInChooseBox(this.currDomino);
    }

    @Override
    public void setOnBoard(Pos pos) {
//        if (currDomino != null && board.fits(currDomino, posFst)) {
        currDomino.setPos(new Pos(pos.x(), pos.y()));
        this.players[this.currPlayerIdx].showOnBoard(currDomino);
        this.gui.showOnGrid(this.currPlayerIdx, this.currDomino);
//            setToChooseBox(null);
//            nextPlayer();
//        }
        /*
        TODO insert code
            - other AI players do turn
            - other AI players select
         */
    }

    private void nextRound() {
        /* TODO insert code for ending game / setting up next round 
            - draw new dominos, 
            - copy banks 
         */
    }

    public boolean won(Player player) {
        // TODO insert code
        return true;
    }
}
