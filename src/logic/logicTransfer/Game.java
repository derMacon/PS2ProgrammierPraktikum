package logic.logicTransfer;

import com.sun.jndi.toolkit.url.Uri;
import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.dataPreservation.Logger;
import logic.playerState.*;
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
     * Default number of players participating in the game
     */
    public final static int DEFAULT_PLAYER_CNT = 4;

    /**
     * Default player types of the participating players
     */
    public final static PlayerType[] DEFAULT_PLAYER_TYPES =
            new PlayerType[]{PlayerType.HUMAN, PlayerType.DEFAULT, PlayerType.DEFAULT, PlayerType.DEFAULT};

    /**
     * Constant for first look of bank selection -> will be deleted for final commit
     * // TODO delete this constant before final commit
     */
    public final static Bank PSEUDO_CURRENT_ROUND_BANK = new Bank(new Entry[]{
            new Entry(new Domino(Tiles.values()[0])),
            new Entry(new Domino(Tiles.values()[1])),
            new Entry(new Domino(Tiles.values()[2])),
            new Entry(new Domino(Tiles.values()[3]))
    }, new Random());

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
        this.playerTypes = DEFAULT_PLAYER_TYPES;
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
        this.roundCount = roundCount;
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

//        // fill stack
//        this.stack = Domino.fill(this.stack);
//
//        /*
//        use of methods that will be used later on in the game. No need to implement a extra function just to initialize
//        the first draw to the current bank.
//         */
//        drawNewDominosForNextRound();
//        copyAndRemoveNextRoundBankToCurrentBank();

        // for first look -> Constant for bank
        this.currentRoundBank = PSEUDO_CURRENT_ROUND_BANK;

        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);

        this.currPlayerIdx = 0;
        this.roundCount = 0;
    }

    @Override
    public void selectDomOnCurrBank(int idx) {
        System.out.println(idx);
        if (0 == this.roundCount) { // initial selection on first bank
            // update human player selection
            this.currentRoundBank.selectEntry(this.players[currPlayerIdx], idx);
            this.currDomino = this.currentRoundBank.getDomino(idx);

            // update rest of the players
            for (int i = 1; i < this.players.length; i++) {
                // rest of the players HAVE to be bots
                ((BotBehavior) this.players[i]).selectFromBank(this.currentRoundBank);
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
//        this.gui.showOnGrid(this.currPlayerIdx, this.currDomino);
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

    @Override
    public void selectDomOnNextBank(int idx) {

    }

    @Override
    public void moveBoard(int dir) {

    }
}
