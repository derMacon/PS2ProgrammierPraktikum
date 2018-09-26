package logic.logicTransfer;

import com.sun.jndi.toolkit.url.Uri;
import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.dataPreservation.Logger;
import logic.playerState.*;
import logic.playerTypes.HumanPlayer;
import logic.playerTypes.PlayerType;
import logic.token.Pos;
import logic.token.Domino;
import logic.token.Tiles;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game implements GUI2Game {

    /**
     * Index for the current round's bank
     */
    public final static int CURRENT_BANK_IDX = 0;

    /**
     * Index for the next round's bank
     */
    public final static int NEXT_BANK_IDX = 1;

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
     * Blueprint for the player types participating in the game, generated by intro screen
     */
    private PlayerType[] playerTypes;

    /**
     * Array containing all players (HumanPl inclusive)
     */
    private Player[] players;

    /**
     * Index of the current player
     */
    private int currPlayerIdx;

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
        this.playerTypes = genDefaultPlayerTypeArray(playerCnt);
        this.players = new Player[playerCnt];
        this.standardBoardSizeX = sizeX;
        this.standardBoardSizeY = sizeY;
        this.currentRoundBank = new Bank(playerCnt);
        this.nextRoundBank = new Bank(playerCnt);
        this.stack = new LinkedList<>();
        this.currPlayerIdx = 0;
        this.currDomino = null;
        this.logger = new Logger(playerCnt);
    }



    /**
     * Constructor for testing, setting specific players (with their appropriate Board), current / next round bank, and
     * the domino in the rotate box of the human player. Current player MUST hold a valid Board.
     * <p>
     * Used to skip startGame method with a specific game
     *
     * @param gui              gui for the game
     * @param types            types of the participating players
     * @param players          players participating in this game
     * @param currPlayerIdx    index of the current player
     * @param currentRoundBank current round bank
     * @param nextRoundBank    next round bank
     * @param stack            stack of dominos used to fill banks
     * @param roundCount       number of round already played, used for case differention at beginning of the game
     * @param currDomino       domino in the rotation box of the human player
     */
    public Game(GUIConnector gui, PlayerType[] types, Player[] players, int currPlayerIdx, Bank currentRoundBank, Bank nextRoundBank,
                List<Domino> stack, int roundCount, Domino currDomino) {
        this.gui = gui;
        this.playerTypes = types;
        this.players = players;
        this.currentRoundBank = currentRoundBank;
        this.nextRoundBank = nextRoundBank;
        this.stack = stack;
        this.currDomino = currDomino;
        this.currPlayerIdx = currPlayerIdx;
        this.standardBoardSizeX = null == players[currPlayerIdx] ? 0 : players[currPlayerIdx].getBoard().getSizeX();
        this.standardBoardSizeY = null == players[currPlayerIdx] ? 0 : players[currPlayerIdx].getBoard().getSizeY();
    }

    /**
     * Constructor used to laod game out of a file with a given path
     *
     * @param filePath path of the file from which the game will be loaded
     */
    public Game(Uri filePath) {
        // TODO insert code - load String from text file and initialize new objects with their constructors with String
        // parameters
        this.standardBoardSizeX = 5;
        this.standardBoardSizeY = 5;
    }

    @Override
    public void safeGame(Uri filePath) {
        // TODO insert code - get String representation from necessary objects by calling toString()
    }

    @Override
    public void setPlayerTypes(PlayerType[] playerTypes) {
        this.playerTypes = playerTypes;
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
        Player[] output = new Player[this.playerTypes.length];
        for (int i = 0; i < this.playerTypes.length; i++) {
            output[i] = PlayerType.getPlayerInstanceWithGivenType(this.playerTypes[i], i, this.gui,
                    this.standardBoardSizeX, this.standardBoardSizeY);
        }
        return output;
    }

    @Override
    public void startGame() {
        // instanciate players with given playertypes
        this.players = createNewPlayers();

        // fill stack
        this.stack = Domino.fill(this.stack);

        /*
        use of methods that will be used later on in the game. No need to implement a extra function just to initialize
        the first draw to the current bank.
         */
        drawNewDominosForNextRound();
        copyAndRemoveNextRoundBankToCurrentBank();

        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);

        // TODO insert code - update all players

        this.currPlayerIdx = 0;
    }

    /**
     * Human selects a domino on the gui: It's the beginning of the game
     * - Human Player selects a domino from the current round bank (given idx)
     * - Players after Human in the array also select a domino, the label to show who's turn is always updated
     * - Players who selected lower value cards each select a domino on the next round bank and display it on the gui
     * <p>
     * @param idx Index of the domino selected by the Human-Player
     */
    @Override
    public void selectDomOnCurrBank(int idx) {
        // update human player selection
        this.currentRoundBank.selectEntry(this.players[currPlayerIdx], idx);
        this.currDomino = this.currentRoundBank.getDomino(idx);

        // update rest of the players -> Example how it could look (not final state)
        for (int i = 1; i < this.players.length; i++) {
            // rest of the players HAVE to be bots
            ((BotBehavior) this.players[i]).selectFromBank(this.currentRoundBank);
        }
        this.gui.selectDomino(CURRENT_BANK_IDX, idx, this.currPlayerIdx);
        this.gui.showInChooseBox(this.currDomino);
    }

    /**
     * Human selects a domino on the gui: Game has already started
     * - Human Player selects a domino from the next round bank (given idx)
     * - Human Player get's his selected domino from the current round bank and puts it as the current domino
     * - Gui displays current domino
     *
     * @param idx index of the domino the human player wants to select on the next round bank
     */
    @Override
    public void selectDomOnNextBank(int idx) {

    }

    /**
     * - Player lays domino on the board
     * - Iterate through current round bank getting the players, as long as it's not the human player:
     * - display whos turn
     * - current player selects a domino for the next round
     * - current player gets the domino he selected in the round before
     * - current player does his turn (internally: updatesDomPos(), layOnBoard(), displays it on gui)
     * - if iterated to the end of the current bank the next round will be set up by nextRound()
     *
     * @param pos position where the Human player wants to display his current domino
     */
    @Override
    public void setOnBoard(Pos pos) {
        currDomino.setPos(new Pos(pos.x(), pos.y()));
        this.players[this.currPlayerIdx].showOnBoard(currDomino);
        /*
        TODO insert code
            - other AI players do turn
            - other AI players select
         */
    }

    /**
     * Ends game or setts up the banks for the next round:
     * - draw new dominos,
     * - copy banks
     */
    private void nextRound() {
        // TODO insert code
    }

    @Override
    public void moveBoard(int dir) {
        // TODO insert code
    }

    /**
     * Generates a PlayerType array with a Human player type on the first slot and the default player type on the rest
     * of the slots. The output will be used as a default setting for the user decided playertypes. With this method it
     * is possible to skip the intro FXML if desired and start with the standard blueprint for the playertypes.
     * @param playerCnt number of players participating in the game
     * @return Playertype array containing a Human player type on the first slot and the default pllayer type on all
     * other slots.
     */
    private PlayerType[] genDefaultPlayerTypeArray(int playerCnt) {
        assert 0 < playerCnt;
        PlayerType[] output = new PlayerType[playerCnt];
        output[0] = PlayerType.HUMAN;
        for (int i = 0; i < playerCnt; i++) {
            output[i] = PlayerType.DEFAULT;
        }
        return output;
    }
}
