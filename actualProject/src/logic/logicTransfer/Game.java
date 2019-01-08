package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.dataPreservation.Logger;
import logic.differentPlayerTypes.HumanPlayer;
import logic.differentPlayerTypes.PlayerType;
import logic.playerState.Board;
import logic.playerState.BotBehavior;
import logic.playerState.Player;
import logic.playerState.Result;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;

import java.util.LinkedList;
import java.util.List;

/**
 * Class to organize the whole game procedure
 */
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
     * current field in focus
     */
    private PossibleField currField;


    /**
     * creates a game, used for the actual game
     *
     * @param gui       gui to display the game changes on
     * @param playerCnt number of players participating in the game
     */
    public Game(GUIConnector gui, int playerCnt) {
        this.gui = gui;
        this.players = new Player[playerCnt];
        this.currentRoundBank = new Bank(playerCnt);
        this.nextRoundBank = new Bank(playerCnt);
        this.stack = new LinkedList<>();
        this.currBankIdx = 0;
        this.currDomino = null;
        this.currField = PossibleField.CURR_BANK;
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

    /**
     * Constructor used for Loading
     *
     * @param gui   gui reference to the game
     * @param input String input setting up the whole game
     */
    public Game(GUIConnector gui, String input) {
        this.gui = gui;
        Converter gameContent = new Converter();
        String returnMessage = gameContent.readStr(gui, input);
        System.out.println(returnMessage);
        // Checking the actual reference
        if (Converter.SUCCESSFUL_READ_MESSAGE == returnMessage) {
            initTestingLoadingConstructor(gui, gameContent.getPlayers(), gameContent.getCurrBankPos(),
                    gameContent.getCurrentBank(),
                    gameContent.getNextBank(), gameContent.getStack(), null);


            Board humanBoard = this.players[HUMAN_PLAYER_IDX].getBoard();
            loadGuiAfterLoadingFile(genDefaultPlayerTypeArray(this.players.length), humanBoard.getSizeX(),
                    humanBoard.getSizeY());
            // Selected Doms in Bank don't have any set position on each player's board -> must
            // be set through calling updateSelectedDom(...)
            for (Player player : this.players) {
                if (player instanceof BotBehavior) {
                    ((BotBehavior) player).updateSelectedDom(this.currentRoundBank);
                    ((BotBehavior) player).updateSelectedDom(this.nextRoundBank);
                }
            }
        } else {
            this.gui.showPopUp(returnMessage);
        }
        Logger.getInstance().printAndSafe(Logger.GAME_SEPARATOR + "\nLoading process: " + returnMessage);
    }

    /**
     * Loads up gui after loading file //todo ueberarbeiten
     *
     * @param playerTypes
     * @param sizeX
     * @param sizeY
     */
    private void loadGuiAfterLoadingFile(PlayerType[] playerTypes, int sizeX, int sizeY) {
        // update boards
        for (int i = 0; i < this.players.length; i++) {
            this.gui.updatePlayer(this.players[i]);
            this.gui.showPointsForPlayer(i, this.players[i].getBoardPoints());
        }
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
        this.gui.setToBank(NEXT_BANK_IDX, this.nextRoundBank);
        this.gui.showWhosTurn(HUMAN_PLAYER_IDX);
    }

    /**
     * Helping method, called for initializing the Testing / Loading
     * constructor. Necessary for avoiding code doubling in the Loading
     * constructor.
     * // TODO javadoc parameters
     *
     * @param gui              gui for the game
     * @param players          players participating in this game
     * @param currBankIdx      index of the current bank
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
        this.currBankIdx = currBankIdx;
        this.currDomino = currDomino;

        evaluateCurrDom();
        evaluateCurrField();
        this.gui.blurOtherFields(this.currField);
        setToChooseBox(this.currDomino);
        return Converter.SUCCESSFUL_READ_MESSAGE;
    }

    /**
     * Evaluates current domino from looking at the two banks
     */
    private void evaluateCurrDom() {
        Player human = this.players[0];
        // next bank is filled -> either player already selected dom on it or not. Either way dom will be set
        if (!this.nextRoundBank.isEmpty()) {
            this.currDomino = this.nextRoundBank.getPlayerSelectedDomino(human);
        } else if (!this.currentRoundBank.isEmpty()) {
            this.currDomino = this.currentRoundBank.getPlayerSelectedDomino(human);
        } else {
            this.currDomino = null;
        }
    }

    /**
     * Initializes the curr field in focus
     */
    private void evaluateCurrField() {
        if (this.currDomino != null) {
            this.currField = PossibleField.CURR_DOM;
        } else if (!this.nextRoundBank.isEmpty()) {
            this.currField = PossibleField.NEXT_BANK;
        } else {
            this.currField = PossibleField.CURR_BANK;
        }
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
     * Getter for the player array
     *
     * @return the player array
     */
    public Player[] getPlayers() {
        return this.players;
    }

    /**
     * Getter for the number of players (including HumanPlayer)
     *
     * @return number of players participating of in the game
     */
    public int getNumberOfPlayers() {
        return this.players.length;
    }

    /**
     * Getter for the domino stack
     *
     * @return the domino stack
     */
    public List<Domino> getStack() {
        return this.stack;
    }

    // ---------------------------------- init and play beginning of the game ----------------------------------
    @Override
    public void startGame(PlayerType[] playerTypes, int sizeX, int sizeY) {
        // instanciate players with given playertypes
        this.players = createNewPlayers(playerTypes, sizeX, sizeY);

        for (int i = 0; i < this.players.length; i++) {
            this.gui.updatePlayer(this.players[i]);
        }

        // fill stack
        this.stack = Domino.fill(this.stack);

        // fill current bank
        this.stack = this.currentRoundBank.randomlyDrawFromStack(this.stack);
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);

        this.currBankIdx = 0;
        this.gui.showWhosTurn(HUMAN_PLAYER_IDX);

        Logger.getInstance().printAndSafe(Logger.GAME_SEPARATOR + "\nStarted new game\n");
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
        if (PossibleField.CURR_BANK == this.currField && this.currentRoundBank.isNotSelected(idx)) {
            // update human player selection
            this.currentRoundBank.selectEntry(this.players[HUMAN_PLAYER_IDX], idx);
            this.gui.selectDomino(CURRENT_BANK_IDX, idx, HUMAN_PLAYER_IDX);
            Logger.getInstance().printAndSafe(String.format(Logger.SELECTION_LOGGER_FORMAT,
                    this.players[HUMAN_PLAYER_IDX].getName(), this.currentRoundBank.getDomino(idx), idx,
                    "current"));

            botsDoInitialSelect();
            randomlyDrawNewDominosForNextRound();
            this.currBankIdx = botsDoTheirTurn(this.currBankIdx);
            this.currField = PossibleField.NEXT_BANK;
            this.gui.blurOtherFields(this.currField);
        } else {
            Logger.getInstance().printAndSafe(Logger.ERROR_DELIMITER
                    + "\nHUMAN tried to select a domino from the " + "current bank\n" + Logger.ERROR_DELIMITER + "\n");
        }
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
        if (PossibleField.NEXT_BANK == this.currField && this.nextRoundBank.isNotSelected(idx)) {
            assert null == this.currDomino;
            Player humanPlayer = this.players[HUMAN_PLAYER_IDX];
            // Human player selects domino on next bank
            this.nextRoundBank.selectEntry(humanPlayer, idx);
            this.gui.selectDomino(NEXT_BANK_IDX, idx, HUMAN_PLAYER_IDX);
            setToChooseBox(this.currentRoundBank.getPlayerSelectedDomino(humanPlayer));
            this.currField = PossibleField.CURR_DOM;
            this.gui.blurOtherFields(this.currField);
            Logger.getInstance().printAndSafe("\n" + String.format(Logger.SELECTION_LOGGER_FORMAT,
                    humanPlayer.getName(), this.nextRoundBank.getDomino(idx).toString(),
                    idx, "next"));
        } else {
            Logger.getInstance().printAndSafe(Logger.ERROR_DELIMITER
                    + "\nHUMAN tried to make an impossible bank " + "selection\n" + Logger.ERROR_DELIMITER + "\n");
        }
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
        if (PossibleField.CURR_DOM == this.currField) {
            this.currDomino.setPos(new Pos(pos.x(), pos.y()));
            this.players[HUMAN_PLAYER_IDX].showOnBoard(currDomino);
            this.currField = PossibleField.NEXT_BANK;
            this.gui.blurOtherFields(this.currField);
            setupCurrDomAndBotsDoTurn();
        } else {
            Logger.getInstance().printAndSafe(Logger.ERROR_DELIMITER + "HUMAN tried to make an impossible bank "
                    + "selection\n" + Logger.ERROR_DELIMITER + "\n");
        }
    }

    // ---------------------------------- Further interactions with the board / game ----------------------------------
    @Override
    public void boxClicked() {
        if (null != this.currDomino) {
            this.currDomino.incRot();
            this.gui.showInChooseBox(this.currDomino);
        }
    }

    @Override
    public boolean fits(Pos pos) {
        return this.players[HUMAN_PLAYER_IDX].getBoard().fits(this.currDomino.setPos(pos));
    }

    @Override
    public void moveBoard(Board.Direction dir) {
        Player humanPlayer = this.players[HUMAN_PLAYER_IDX];
        if (humanPlayer.getBoard().canMoveBoardToDir(dir) && (humanPlayer instanceof HumanPlayer)) {
            HumanPlayer humanInstance = (HumanPlayer) humanPlayer; // only Human player has a
            // setter for the board -> needs to cast
            humanInstance.updateBoard(humanInstance.getBoard().moveBoard(dir));
            this.gui.updatePlayer(players[HUMAN_PLAYER_IDX]);
            Logger.getInstance().printAndSafe(String.format(Logger.CC_DRAG_LOGGER_FORMAT,
                    humanInstance.getName(), humanInstance.getBoard().findPos(SingleTile.CC)));
        } else {
            Logger.getInstance().printAndSafe(Logger.ERROR_DELIMITER
                    + "\nHUMAN tried to move board in an impossible direction\n" + Logger.ERROR_DELIMITER + "\n");
        }

    }

    @Override
    public void disposeCurrDomino() {
        if (PossibleField.CURR_DOM == this.currField) {
            Logger.getInstance().printAndSafe(String.format(Logger.DISMISSAL_LOGGER_FORMAT, "HUMAN",
                    this.currDomino.toString()));
            setToChooseBox(null);
            this.currField = PossibleField.NEXT_BANK;
            setupCurrDomAndBotsDoTurn();
            this.gui.blurOtherFields(currField);
        } else {
            Logger.getInstance().printAndSafe(Logger.ERROR_DELIMITER + "\nHUMAN tried to dispose the current "
                    + "domino\n" + Logger.ERROR_DELIMITER + "\n");
        }
    }

    // ---------------------------------- Helping methods ----------------------------------
    // --- Init game ---

    /**
     * Creates a new Player array. Used to initialize the players field.
     *
     * @param playerTypes instanciates the players of the current field for the game
     * @param sizeX       width if the players' boards
     * @param sizeY       height of the players' boards
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

    /**
     * Bots do their initial select on the current bank
     */
    private void botsDoInitialSelect() {
        for (int i = 1; i < this.players.length; i++) {
            // rest of the players HAVE to be bots
            this.currentRoundBank = ((BotBehavior) this.players[i]).doInitialSelect(currentRoundBank, CURRENT_BANK_IDX);
            // TODO insert code -> show Who's Turn
        }
        Logger.getInstance().printAndSafe("\n");
    }

    // --- Do necessary turns / Setting up banks for next round ---

    /**
     * the current domino will be setup and the bots do their turn
     */
    private void setupCurrDomAndBotsDoTurn() {
        setToChooseBox(null);
        // bots do turns until round is over
        if (this.nextRoundBank.isEmpty()) {
            if (!this.nextRoundBank.isEmpty()) {
                copyAndRemoveNextRoundBankToCurrentBank();
                this.gui.setToBank(NEXT_BANK_IDX, this.nextRoundBank);
            }
            this.currField = PossibleField.CURR_DOM;
            this.currBankIdx = botsDoLastTurn(this.currBankIdx + 1);
        } else {
            this.currBankIdx = botsDoTheirTurn(this.currBankIdx + 1);
        }
    }

    /**
     * Iterates through the selected players from the current bank until the
     * selected player is the Human player. If the round is finished (the last
     * bank slot was evaluated) the next round will be setup (banks will be
     * loaded)
     *
     * @param bankIdx index of the current bank slot
     * @return next bank slot
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
            return botsDoTheirTurn(bankIdx + 1);
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
        copyAndRemoveNextRoundBankToCurrentBank();
        if (!this.stack.isEmpty()) {
            randomlyDrawNewDominosForNextRound();
        } else {
            this.nextRoundBank.clearAllEntries();
            this.gui.setToBank(NEXT_BANK_IDX, nextRoundBank);
            setToChooseBox(this.currentRoundBank.getPlayerSelectedDomino(this.players[0]));
            this.currField = PossibleField.CURR_DOM;
        }

    }

    /**
     * The bots do their last turn (not selecting anything on the next bank, since it is empty)
     *
     * @param bankIdx index of the current bank index
     * @return the next bank slot
     */
    private int botsDoLastTurn(int bankIdx) {
        if (bankIdx >= this.currentRoundBank.getBankSize()) {
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

    /**
     * Copies the next rounds' bank to the current rounds' bank
     */
    private void copyAndRemoveNextRoundBankToCurrentBank() {
        this.currentRoundBank = this.nextRoundBank.copy();
        this.nextRoundBank.clearAllEntries();
        this.gui.setToBank(CURRENT_BANK_IDX, this.currentRoundBank);
    }

    /**
     * Randomly draws a domino for the next round
     */
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

        Result res = new Result(this.players);
        this.gui.showResult(res);
        Logger.getInstance().printAndSafe("round ended\n" + res.toString());
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
        this.gui.deleteDomFromBank(CURRENT_BANK_IDX,
                this.currentRoundBank.getSelectedDominoIdx(this.players[HUMAN_PLAYER_IDX]));
    }


    @Override
    public String toString() {
        return genString(false);
    }

    /**
     * generates the string representation of the game used in the file format
     *
     * @return string representation used in the file formation
     */
    public String toFile() {
        return genString(true);
    }

    /**
     * generates String representation of the game
     *
     * @param forFileRepresentation flag that determines if the board will be displayed in file format or in 'normal'
     *                              string format // todo explain difference
     * @return string representation of the game
     */
    private String genString(boolean forFileRepresentation) {
        StringBuilder strbOutput = new StringBuilder();
        // all player boards to String
        for (Player currPlayer : this.players) {
            strbOutput.append("<" + Converter.BOARD_IDENTIFIER + " " + (currPlayer.getIdxInPlayerArray() + 1) + ">\n");
            if (forFileRepresentation) {
                strbOutput.append(currPlayer.getBoard().toFile(currPlayer,
                        this.currentRoundBank,
                        this.nextRoundBank));
            } else {
                strbOutput.append(currPlayer.getBoard().toString());
            }
        }
        // all banks to String (current round Bank first)
        strbOutput.append("<" + Converter.BANK_IDENTIFIER + ">\n");
        strbOutput.append(this.currentRoundBank.toString() + "\n");
        if (this.nextRoundBank == null) {
            strbOutput.append("\n");
        } else {
            strbOutput.append(this.nextRoundBank.toString() + "\n");
        }
        // stack to String
        strbOutput.append("<" + Converter.STACK_IDENTIFIER + ">\n");
        for (int i = 0; i < this.stack.size(); i++) {
            strbOutput.append(this.stack.get(i).toFile());
            if (i < this.stack.size() - 1) {
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
        final Game other = (Game) obj;
        boolean equals;
        int i = 0;
        do {
            equals = this.players[i].equals(other.players[i]);
            i++;
        } while (equals && i < this.players.length);

        equals = this.stack.size() == other.stack.size();
        i = 0;
        while (equals && i < this.stack.size()) {
            equals = this.stack.get(i).equals(other.stack.get(i));
            i++;
        }

        // TODO Delete before final commit
        boolean debugBankCurr = this.currentRoundBank.equals(other.currentRoundBank);
        boolean debugBankNext = this.nextRoundBank.equals(other.nextRoundBank);

        return equals && this.currentRoundBank.equals(other.currentRoundBank)
                && this.nextRoundBank.equals(other.nextRoundBank)
                && (this.currDomino == null && other.currDomino == null || this.currDomino.equals(other.currDomino))
                && this.currField.equals(other.currField);
    }


    @Override
    public boolean equalsStr(String input) {
        if (null == input) {
            return false;
        }
        System.out.println("this: " + this.toFile());
        System.out.println("input: " + input);
        return (this.toFile() + "\n").equals(input);
    }
}
