package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.dataPreservation.Loader;
import logic.dataPreservation.Logger;
import logic.playerState.*;
import logic.playerTypes.HumanPlayer;
import logic.playerTypes.PlayerType;
import logic.token.Pos;
import logic.token.Domino;

import java.io.File;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game implements GUI2Game {

    /**
     * Index for the current round's bank
     */
    public static final int CURRENT_BANK_IDX = 0;

    /**
     * Index for the next round's bank
     */
    public static final int NEXT_BANK_IDX = 1;

    /**
     * Index for the human player in the local players array
     */
    public static final int HUMAN_PLAYER_IDX = 0;

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
     * Current slot und notice
     */
    private int currBankIdx;

    /**
     * the current domino of the human player (displayed in the rotation box)
     */
    private Domino currDomino;

    /**
     * the gui to display on
     */
    private GUIConnector gui;

    /**
     * Logger for this game
     * // TODO delete - not necessary due to singleton pattern
     */
    private Logger logger;

    /**
     * creates a game, used for the actual game
     *
     * @param gui gui to display on
     */
    public Game(GUIConnector gui, int playerCnt) {
        this.gui = gui;
        this.players = new Player[playerCnt];
        this.currentRoundBank = new Bank(playerCnt);
        this.nextRoundBank = new Bank(playerCnt);
        this.stack = new LinkedList<>();
        this.currPlayerIdx = 0;
        this.currBankIdx = 0;
        this.currDomino = null;
        this.logger = Logger.getInstance();
    }

    /**
     * Constructor for testing, setting specific players (with their appropriate
     * Board), current / next round bank, and the domino in the rotate box of
     * the human player. Current player MUST hold a valid Board.
     * <p>
     * Used to skip startGame method with a specific game
     *
     * @param gui              gui for the game
     * @param players          players participating in this game
     * @param currPlayerIdx    index of the current player
     * @param currentRoundBank current round bank
     * @param nextRoundBank    next round bank
     * @param stack            stack of dominos used to fill banks
     * @param currDomino       domino in the rotation box of the human player
     */
    public Game(GUIConnector gui, Player[] players, int currPlayerIdx, Bank currentRoundBank, Bank nextRoundBank,
                List<Domino> stack, Domino currDomino) {
        initTestingLoadingConstructor(gui, players, currPlayerIdx, currentRoundBank, nextRoundBank, stack, currDomino);
    }

    // --- saving / loading game ---
/*
    /**
     * Constructor used to laod game out of a file with a given path
     *
     * @param file file from which the game will be loaded
    public Game(GUIConnector gui, File file) {
        // TODO insert code - load String from text file and initialize new objects with their constructors with String
        this(gui, Loader.loadFile(file));
        // parameters
    }

    /**
     * Constructor used for Loading
     *
     * @param gui   gui reference to the game
     * @param input String input setting up the whole game
     */
    public Game(GUIConnector gui, String input) {
        Converter gameContent = new Converter();
        // TODO use error message - error message used for treatment, maybe with a new Pop-Up Window or just in the log-File.
        String returnMessage = gameContent.readStr(gui, input);
        System.out.println(returnMessage);
        if(Converter.SUCCESSFUL_READ_MESSAGE == returnMessage) {
            initTestingLoadingConstructor(gui, gameContent.getPlayers(), gameContent.getCurrBankPos(), gameContent.getCurrentBank(),
                    gameContent.getNextBank(), gameContent.getStack(), null);


            Board humanBoard = this.players[HUMAN_PLAYER_IDX].getBoard();
            loadGuiAfterLoadingFile(genDefaultPlayerTypeArray(this.players.length), humanBoard.getSizeX(), humanBoard.getSizeY());
            // Selected Doms in Bank don't have any set position on each player's board -> must
            // be set through calling updateSelectedDom(...)
            for(Player player : this.players) {
                if(player instanceof BotBehavior) {
                    ((BotBehavior) player).updateSelectedDom(this.currentRoundBank);
                    ((BotBehavior) player).updateSelectedDom(this.nextRoundBank);
                }
            }
        }
    }

    private void loadGuiAfterLoadingFile(PlayerType[] playerTypes, int sizeX, int sizeY) {
        // update boards
        for (int i = 0; i < this.players.length; i++) {
            this.gui.updatePlayer(this.players[i], i);
        }
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
        this.gui.setToBank(NEXT_BANK_IDX, this.nextRoundBank);
    }

    /**
     * Helping method, called for initializing the Testing / Loading
     * constructor. Necessary for avoiding code doubling in the Loading
     * constructor.
     * // TODO javadoc parameters
     * @param gui              gui for the game
     * @param players          players participating in this game
     * @param currentRoundBank current round bank
     * @param nextRoundBank    next round bank
     * @param stack            stack of dominos used to fill banks
     * @param currDomino       domino in the rotation box of the human player
     * @return String message containing the error messages,
     * SUCCESSFUL_READ_MESSAGE if reading String was successful
     */
    private String initTestingLoadingConstructor(GUIConnector gui, Player[] players, int currBankIdx,
                                                 Bank currentRoundBank, Bank nextRoundBank, List<Domino> stack,
                                                 Domino currDomino) {
        this.gui = gui;
        this.players = players;
        this.currentRoundBank = currentRoundBank;
        this.nextRoundBank = nextRoundBank;
        this.stack = stack;
        this.currPlayerIdx = 0;
        this.currBankIdx = currBankIdx;
        this.currDomino = currDomino;
        setToChooseBox(currDomino);

        // TODO check if setting values was successful
        return Converter.SUCCESSFUL_READ_MESSAGE;
    }

    @Override
    public void safeGame(URI filePath) {
        // TODO insert code - get String representation from necessary objects by calling toString()
    }

    // --- Setter / Getter ---

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

    /**
     * Getter for the domino stack
     *
     * @return
     */
    public List<Domino> getStack() {
        return this.stack;
    }

    // ---------------------------------- init and play beginning of the game ----------------------------------
    @Override
    public void startGame(PlayerType[] playerTypes, int sizeX, int sizeY) {
        // instanciate players with given playertypes
        this.players = createNewPlayers(playerTypes, sizeX, sizeY);

        // TODO delete next line before final commit
//        this.gui.updatePlayer(this.players[HUMAN_PLAYER_IDX], HUMAN_PLAYER_IDX);
        for (int i = 0; i < this.players.length; i++) {
            this.gui.updatePlayer(this.players[i], i);
        }

        // fill stack
        // TODO Problem beim Laden, Stack wird einfach ueberschrieben
        this.stack = Domino.fill(this.stack);

        // fill current bank
        this.stack = this.currentRoundBank.randomlyDrawFromStack(this.stack);
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);

        this.currPlayerIdx = 0;
        this.currBankIdx = 0;
        this.gui.showWhosTurn(HUMAN_PLAYER_IDX);

        // TODO insert code - update all players
        // TODO blur out boxes which are not accessible when user selects the first domino from the current bank
    }

    /**
     * Human selects a domino on the gui: It's the beginning of the game - Human
     * Player selects a domino from the current round bank (given idx) - Players
     * after Human in the array also select a domino, the label to show who's
     * turn is always updated - Players who selected lower value cards each
     * select a domino on the next round bank and display it on the gui
     * <p>
     *
     * @param idx Index of the domino selected by the Human-Player
     */
    @Override
    public void selectDomOnCurrBank(int idx) {

        // TODO check if it's the first round or not (since this method only can be called if it is)
        // update human player selection
        this.currentRoundBank.selectEntry(this.players[HUMAN_PLAYER_IDX], idx);
        this.gui.selectDomino(CURRENT_BANK_IDX, idx, HUMAN_PLAYER_IDX);

        botsDoInitialSelect();
        randomlyDrawNewDominosForNextRound();
//        this.currPlayerIdx = idx + 1;
//        this.currPlayerIdx = botsDoTheirTurn(this.currPlayerIdx);
        this.currBankIdx = botsDoTheirTurn(this.currBankIdx);

        // TODO delete lines before final commit
        // Only for demonstrative purpose -> will be deleted before final commit (belongs in selectDomOnNextBank()
        // beacause the player has to select a domino on the next round before it is possible to rotate the preselected one.)
//        this.currDomino = this.currentRoundBank.getDomino(idx);
//        this.gui.showInChooseBox(this.currDomino);
//        this.gui.deleteDomFromBank(0, idx);
        // TODO blur out boxes which are not accessible when user participates in the upcomming standard round
    }

    // ---------------------------------- Standard round ----------------------------------
    // |Human: selectDomOnNextBank| -> |Human: setOnBoard| -> |Bots do their turn| -> |Banks will be drawn from stack|
    //  => Repeat as long as there are dominos in the stack, then endRound()

    /**
     * Human selects a domino on the gui: Game has already started - Human
     * Player selects a domino from the next round bank (given idx) - Human
     * Player get's his selected domino from the current round bank and puts it
     * as the current domino - Gui displays current domino
     *
     * @param idx index of the domino the human player wants to select on the
     *            next round bank
     */
    @Override
    public void selectDomOnNextBank(int idx) {
        assert null == this.currDomino;
        // Human player selects domino on next bank
        this.nextRoundBank.selectEntry(this.players[HUMAN_PLAYER_IDX], idx);
        this.gui.selectDomino(NEXT_BANK_IDX, idx, HUMAN_PLAYER_IDX);
        setToChooseBox(this.currentRoundBank.getPlayerSelectedDomino(this.players[HUMAN_PLAYER_IDX]));
        this.currPlayerIdx++;

    }

    /**
     * - Player lays domino on the board - Iterate through current round bank
     * getting the players, as long as it's not the human player: - display whos
     * turn - current player selects a domino for the next round - current
     * player gets the domino he selected in the round before - current player
     * does his turn (internally: updatesDomPos(), showOnBoard(), displays it on
     * gui) - if iterated to the end of the current bank the next round will be
     * set up by nextRound()
     *
     * @param pos position where the Human player wants to display his current
     *            domino
     */
    @Override
    public void setOnBoard(Pos pos) {
        this.currDomino.setPos(new Pos(pos.x(), pos.y()));
        this.players[HUMAN_PLAYER_IDX].showOnBoard(currDomino);

        Logger.getInstance().printAndSafe("HUMAN put " + this.currDomino.toString() + " to " + pos.toString());
        setupCurrDomAndBotsDoTurn();
    }

    // ---------------------------------- Further interactions with the board / game ----------------------------------
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
        return this.players[HUMAN_PLAYER_IDX].getBoard().fits(this.currDomino.setPos(pos));
    }

    @Override
    public void moveBoard(int dir) {
        Player humanPlayer = this.players[HUMAN_PLAYER_IDX];
        if (humanPlayer.getBoard().canMoveBoardToDir(dir) && (humanPlayer instanceof HumanPlayer)) {
            HumanPlayer humanInstance = (HumanPlayer) humanPlayer; // only Human player has a
            // setter for the board -> need to cast
            humanInstance.updateBoard(humanInstance.getBoard().moveBoard(dir));
//            this.gui.updateGrid(HUMAN_PLAYER_IDX, humanBoard); // TODO evaluate if updateGrid
            // method is really necessray
            this.gui.updatePlayer(players[HUMAN_PLAYER_IDX], HUMAN_PLAYER_IDX);
        } else {
            // TODO implement gui response to invalid move
        }

    }

    @Override
    public void disposeCurrDomino() {
        setToChooseBox(null);
//        Logger.getInstance().printAndSafe("HUMAN disposed " + this.currDomino.toString());
        setupCurrDomAndBotsDoTurn();
    }

    // ---------------------------------- Helping methods ----------------------------------
    // --- Init game ---

    /**
     * Creates a new Player array. Used to initialize the players field.
     *
     * @return new Player array with the human player on the first index and the
     * default player on the remaining array slots.
     */
    private Player[] createNewPlayers(PlayerType[] playerTypes, int sizeX, int sizeY) {
        Player[] output = new Player[playerTypes.length];
        for (int i = 0; i < playerTypes.length; i++) {
            output[i] = PlayerType.getPlayerInstanceWithGivenType(playerTypes[i], i, this.gui, sizeX, sizeY);
        }
        return output;
    }

    /**
     * Generates a PlayerType array with a Human player type on the first slot
     * and the default player type on the rest of the slots. The output will be
     * used as a default setting for the user decided playertypes. With this
     * method it is possible to skip the intro FXML if desired and start with
     * the standard blueprint for the playertypes.
     *
     * @param playerCnt number of players participating in the game
     * @return Playertype array containing a Human player type on the first slot
     * and the default pllayer type on all other slots.
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

    private void botsDoInitialSelect() {
        for (int i = 1; i < this.players.length; i++) {
            // rest of the players HAVE to be bots
//            this.currentRoundBank = ((BotBehavior) this.players[i]).selectFromBank(this.currentRoundBank, CURRENT_BANK_IDX);
            this.currentRoundBank = ((BotBehavior) this.players[i]).doInitialSelect(currentRoundBank, CURRENT_BANK_IDX);
            
            // TODO insert code -> show Who's Turn
        }
    }

    // --- Do necessary turns / Setting up banks for next round ---

    
    private void setupCurrDomAndBotsDoTurn() {
        setToChooseBox(null);
        // bots do turns until round is over
//        if(this.nextRoundBank.isEmpty()) {
        if(this.nextRoundBank.isEmpty()) {
            if(!this.nextRoundBank.isEmpty()) {
                copyAndRemoveNextRoundBankToCurrentBank();
                this.gui.setToBank(NEXT_BANK_IDX, this.nextRoundBank);
            }
            this.currBankIdx = botsDoLastTurn(this.currBankIdx + 1);
//            this.currentRoundBank.clearAllEntries();
//            this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
//            if(this.currBankIdx >= this.currentRoundBank.getBankSize()) {
//                endRound();
//            }
        } else {
//            this.currPlayerIdx = botsDoTheirTurn(this.currPlayerIdx);
            this.currBankIdx = botsDoTheirTurn(this.currBankIdx + 1);
        }
    }

    /**
     * Iterates through the selected players from the current bank until the
     * selected player is the Human player. If the round is finished (the last
     * bank slot was evaluated) the next round will be setup (banks will be
     * loaded)
     */
    private int botsDoTheirTurn(int bankIdx) {
        if (!isValidPlayerIdx(bankIdx)) {
            setupNextRound();
            return botsDoTheirTurn(0);
        }
        Player currPlayerInstance = this.currentRoundBank.getSelectedPlayer(bankIdx);
        if (currPlayerInstance instanceof BotBehavior) {
            this.gui.showWhosTurn(currPlayerInstance.getIdxInPlayerArray());
            ((BotBehavior) currPlayerInstance).doStandardTurn(this.currentRoundBank, this.nextRoundBank);
            bankIdx++;
            return botsDoTheirTurn(bankIdx);
        }
        // selected player on bankIdx is the human player
        this.gui.showWhosTurn(HUMAN_PLAYER_IDX);
        return bankIdx;
    }

    /**
     * Ends game or setts up the banks for the next round: - draw new dominos, -
     * copyWithoutSelection banks
     */
    private void setupNextRound() {
        this.currPlayerIdx = 0;
        System.out.println("Test output: " + this.stack.size() + " cards in stack");
//        if(this.stack.isEmpty()) {
//            if(this.nextRoundBank.isEmpty() && this.currentRoundBank.isEmpty()) {
//                endRound();
//            } else {
//                copyAndRemoveNextRoundBankToCurrentBank();
//                // TODO delete nextBank - preferably in method
//                this.nextRoundBank = new Bank(this.players.length);
//                this.gui.setToBank(NEXT_BANK_IDX, nextRoundBank);
//                // TODO vielleicht botsdolas... mit Player als Rueckgabewert?
//                this.currPlayerIdx = botsDoLastTurn(this.currPlayerIdx);
//                System.out.println("debug setup next round" + this.currentRoundBank.getPlayerSelectedDomino(this.players[currPlayerIdx]));
//                setToChooseBox(this.currentRoundBank.getPlayerSelectedDomino(this.players[currPlayerIdx]));
//            }
//        } else {
        copyAndRemoveNextRoundBankToCurrentBank();
        if(!this.stack.isEmpty()) {
            randomlyDrawNewDominosForNextRound();
        } else {
            this.nextRoundBank.clearAllEntries();
            this.gui.setToBank(NEXT_BANK_IDX, nextRoundBank);
            setToChooseBox(this.currentRoundBank.getPlayerSelectedDomino(this.players[currPlayerIdx]));
        }

    }


    private int botsDoLastTurn(int bankIdx) {
        if(bankIdx >= this.currentRoundBank.getBankSize()) {
            endRound();
        }
        Player currPlayerInstance = this.currentRoundBank.getSelectedPlayer(bankIdx);
        // Bots iterate until human player has to do his turn or the end of the bank is reached
        if (currPlayerInstance instanceof BotBehavior) {
            this.gui.showWhosTurn(currPlayerInstance.getIdxInPlayerArray());
            ((BotBehavior) currPlayerInstance).doLastTurn(this.currentRoundBank);
            return botsDoLastTurn(bankIdx + 1);
        } else {
            // selected player on bankIdx is the human player
            this.gui.showWhosTurn(HUMAN_PLAYER_IDX);
            setToChooseBox(this.currentRoundBank.getPlayerSelectedDomino(this.players[HUMAN_PLAYER_IDX]));
        }
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
        return bankIdx + 1;
    }

    private void copyAndRemoveNextRoundBankToCurrentBank() {
        this.currentRoundBank = this.nextRoundBank.copy();
        this.nextRoundBank.clearAllEntries();
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
    }

    private void randomlyDrawNewDominosForNextRound() {
        this.stack = this.nextRoundBank.randomlyDrawFromStack(this.stack);
        this.gui.setToBank(NEXT_BANK_IDX, this.nextRoundBank);
    }

    /**
     * Ends round and displays the result of all players
     */
    // TODO make private
    public void endRound() {
        this.currentRoundBank.clearAllEntries();
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
        this.nextRoundBank.clearAllEntries();
        this.gui.setToBank(NEXT_BANK_IDX, this.nextRoundBank);
        this.currDomino = null;

        Logger.getInstance().printAndSafe("end round");

        StackPane root = new StackPane();
        root.getChildren().add(new Result(this.players).toTreeView());
        Stage primaryStage = new Stage(); 
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Ergebnisse");
        primaryStage.show();
    }

    // --- Human interaction ---

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
        this.gui.deleteDomFromBank(CURRENT_BANK_IDX, this.currentRoundBank.getSelectedDominoIdx(this.players[HUMAN_PLAYER_IDX]));
        Logger.getInstance().printAndSafe(currDomino + " put to rotation box");
    }


    @Override
    public String toString() {
        StringBuilder strbOutput = new StringBuilder();
        // all player boards to String
        for(Player currPlayer : this.players) {
            strbOutput.append("<" + Converter.BOARD_IDENTIFIER + " " + (currPlayer.getIdxInPlayerArray() + 1) + ">\n");
            strbOutput.append(currPlayer.getBoard().toString());
        }
        // all banks to String (current round Bank first)
        strbOutput.append("<" + Converter.BANK_IDENTIFIER + ">\n");
        strbOutput.append(this.currentRoundBank.toString() + "\n");
        strbOutput.append(this.nextRoundBank.toString() + "\n");
        // stack to String
        strbOutput.append("<" + Converter.STACK_IDENTIFIER + ">\n");
        for (int i = 0; i < this.stack.size(); i++) {
            System.out.println("int i : " + i);
            strbOutput.append(this.stack.get(i));
            if(i <= this.stack.size() - 1) {
                strbOutput.append(",");
            }
        }
        return strbOutput.toString();
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game)obj;
        boolean hasEqualPlayers;
        int i = 0;
        do {
            hasEqualPlayers = this.players[i].equals(other.players[i]);
        } while(hasEqualPlayers && i < this.players.length);

        return hasEqualPlayers && this.currentRoundBank.equals(other.currentRoundBank)
                && this.nextRoundBank.equals(other.nextRoundBank)
                && this.stack.equals(other.stack);
    }
}
