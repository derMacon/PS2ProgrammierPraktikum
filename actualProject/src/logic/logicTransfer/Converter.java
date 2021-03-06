package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.differentPlayerTypes.PlayerType;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.token.Domino;
import logic.token.SingleTile;
import logic.token.Tiles;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Class that implements the Conversion from plain text to all game components
 */
public class Converter {

    /**
     * Index for the Description in the two-dim String array
     */
    public static final int DESCRIPTION_IDX = 0;

    /**
     * Index for the Data in the two-dim String array
     */
    public static final int DATA_IDX = 1;

    /**
     * Identifier for the boards
     */
    public static final String BOARD_IDENTIFIER = "Spielfeld";

    /**
     * Identifier for both banks
     */
    public static final String BANK_IDENTIFIER = "Bänke";

    /**
     * Identifier for the stack
     */
    public static final String STACK_IDENTIFIER = "Beutel";

    /**
     * String message for displaying an unsuccessful read from the given data
     */
    public static final String UNSUCCESSFUL_READ_MESSAGE = "Loading unsuccessful";

    /**
     * String message for displaying a successful read from the given data
     */
    public static final String SUCCESSFUL_READ_MESSAGE = "Loading successful";

    /**
     * Opening char of a valid tag identifier
     */
    public static final String TAG_OPENER = "<";

    /**
     * Closing char of a valid tag identifier
     */
    public static final String TAG_CLOSER = ">";

    /**
     * Constant defining a not already initialized state (used in the checkBankSyntax-Method)
     */
    private static final int NOT_INITIALIZED = -1;

    // --- fields that will be filled by this class -> Will be transfered to the game via Getter
    /**
     * Players of the game
     */
    private List<Player> players = new LinkedList<>();

    /**
     * The current player position on the current round bank (slot idx)
     */
    private int currBankPos;

    /**
     * The current round's bank
     */
    private Bank currentBank;

    /**
     * The next round's bank
     */
    private Bank nextBank;

    /**
     * The stack of dominos of the game
     */
    private List<Domino> stack;


    /**
     * getter for the players
     *
     * @return the player array
     */
    public Player[] getPlayers() {
        return players.toArray(new Player[0]);
    }

    /**
     * Getter for the current bank position (currPlayerIdx)
     *
     * @return the current bank position (currPlayerIdx)
     */
    public int getCurrBankPos() {
        return currBankPos;
    }

    /**
     * Getter for the current bank
     *
     * @return reference to the current bank
     */
    public Bank getCurrentBank() {
        return currentBank;
    }

    /**
     * Getter for the next bank
     *
     * @return reference to the next bank
     */
    public Bank getNextBank() {
        return nextBank;
    }

    /**
     * Getter for the stack
     *
     * @return reference to the stack
     */
    public List<Domino> getStack() {
        return stack;
    }


    /**
     * Reads string input and converts it into the appropriate game instance
     * fields
     *
     * @param gui   gui implementation to display a players action. Necessary in order to instanciate new Players since
     *              bots are required to hold a field containing a implementation of the gui interface in order to show
     *              their moves without cooperating with the main game class.
     * @param input input to convert
     * @return String message containing the error messages,
     * SUCCESSFUL_READ_MESSAGE if reading String was successful
     */
    public String readStr(GUIConnector gui, String input) {
        try {
            if (input == null || input.length() == 0) {
                throw new IOException(UNSUCCESSFUL_READ_MESSAGE);
            }
            // Tag syntax roughly checked -> further analysis further down the line
            if (!input.matches("(<Spielfeld[^>]*>\n(?s)[^<,>]*)*<Bänke>\n(?s)[^<>]*<Beutel>\n[^<>]*")) {
                System.out.println(input);
                throw new WrongTagException();
            }
            String[][] descriptionBlocks = genDescriptiveField(input);
            fillFieldsWithDescriptiveBlocks(descriptionBlocks, gui);
            return SUCCESSFUL_READ_MESSAGE;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Takes a given String and extracts the information about the game. The
     * data structure can be described as follows:
     * <p>
     * &lt;Spielfeld&gt;\n text
     * &lt;Spielfeld&gt;\n text
     * &lt;Spielfeld&gt;\n text
     * &lt;Spielfeld&gt;\n text
     * &lt;Baenke&gt;\n text
     * &lt;Beutel&gt;\n text
     * <p>
     * Procedure: - Genereate a 2-dim String array containing a the tag for the
     * information in the first field of a array entry and the actual data in
     * the second. - Data is seperated from the corresponding identifier
     *
     * @param input String to extract data from
     * @return String array containing the name of the field in the first slot
     * and the actual data in the second
     * @throws WrongTagException Exception that will be thrown if the tag syntax was not followed by the input String
     */
    public String[][] genDescriptiveField(String input) throws WrongTagException {
        List<String> blocks = new LinkedList<>();
        // overall sections (board/banks/stack) are seperated
        for (String currBlock : input.split("<")) {
            blocks.add(currBlock);
        }
        blocks.remove(0); // First element may be empty because of split()

        // Data seperated from Identifier
        String[][] output = new String[blocks.size()][2];
        for (int i = 0; i < blocks.size(); i++) {
            output[i][DESCRIPTION_IDX] = genTag(blocks.get(i));
            output[i][DATA_IDX] = genData(blocks.get(i));
        }

        return output;
    }

    /**
     * Convert the tag for the given game component into a descriptive tag that will be later used to generate the
     * values of the component. A tag follows the following syntax: "<" + tagname + ">...
     *
     * @param input String containing values with which the output component will be filled later on in the
     *              conversion process
     * @return the tagname if the syntax is correct
     * @throws WrongTagException Exception that will be thrown if the tag syntax was not followed by the input String
     */
    private String genTag(String input) throws WrongTagException {
        if (null == input) {
            return null;
        }
        String modifiedInput = input.replaceAll(TAG_CLOSER + "(?s).*", "");

        if (modifiedInput.matches(BOARD_IDENTIFIER + ".*")) {
            return BOARD_IDENTIFIER;
        }
        if (modifiedInput.equals(BANK_IDENTIFIER) || modifiedInput.equals(STACK_IDENTIFIER)) {
            return modifiedInput;
        }
        throw new WrongTagException();
    }

    /**
     * Splits the data for the components from a given string. Checks if the correct syntax was followed
     *
     * @param input input from which the data for the components will be split apart from
     * @return data component of the given string
     * @throws WrongTagException Syntax containing an error message if syntax was not followed
     */
    protected String genData(String input) throws WrongTagException {
        // (?s).* to match all chars (including the linebreak)
        String pattern = BOARD_IDENTIFIER + "(?s).*" + TAG_CLOSER + "\n(?s)" + ".*"
                + "|" + BANK_IDENTIFIER + TAG_CLOSER + "\n(?s).*"
                + "|" + STACK_IDENTIFIER + TAG_CLOSER + "\n(?s).*";
        if (null != input && input.matches(pattern)) {
            String[] tempBlock = input.split(TAG_CLOSER + "\n");
            return tempBlock.length > 1 ? tempBlock[1] : "";
        } else {
            throw new WrongTagException();
        }
    }


    /**
     * Generates the data for the fields. Iterates through the given description
     * blocks, checks the title to chose which conversion method will be called
     *
     * @param descriptionBlocks multidimensional array containing a tag name in the respective first field and the
     *                          data for the component fitting this tag in the second one.
     * @param gui               gui reference needed for the player to be instanciated
     * @throws WrongTagException         Exception that will be thrown if the tag syntax was not matched by any given
     *                                   tag in
     *                                   the descriptive field array
     * @throws WrongBoardSyntaxException Exception that will be thrown if the board syntax was not matched by any
     *                                   given tag in the descriptive field array
     * @throws WrongBankSyntaxException  Exception that will be thrown if the bank syntax was not matched by any
     *                                   given tag in the descriptive field array
     * @throws WrongStackSyntaxException Exception that will be thrown if the stack syntax was not matched by any
     *                                   given tag in the descriptive field array
     */
    public void fillFieldsWithDescriptiveBlocks(String[][] descriptionBlocks, GUIConnector gui)
            throws WrongTagException, WrongBoardSyntaxException, WrongBankSyntaxException,
            WrongStackSyntaxException {
        int[] dimensions = new int[]{NOT_INITIALIZED, NOT_INITIALIZED};
        for (int i = 0; i < descriptionBlocks.length; i++) {
            switch (descriptionBlocks[i][DESCRIPTION_IDX]) {
                case BOARD_IDENTIFIER:
                    dimensions = checkBoardSyntax(dimensions, descriptionBlocks[i][DATA_IDX]);
                    this.players.add(i, convertStrToPlayerWithDefaultOccupancy(
                            descriptionBlocks[i][DATA_IDX], i, gui));
                    break;
                case BANK_IDENTIFIER:
                    checkBankSyntax(descriptionBlocks[i][DATA_IDX], this.players.size());
                    Bank[] banks = convertStrToBanks(descriptionBlocks[i][DATA_IDX]);
                    this.currentBank = banks[Game.CURRENT_BANK_IDX];
                    this.nextBank = banks[Game.NEXT_BANK_IDX];
                    this.currBankPos = 4 - descriptionBlocks[Game.CURRENT_BANK_IDX].length;
                    break;
                case STACK_IDENTIFIER:
                    checkStackSyntax(descriptionBlocks[i][DATA_IDX]);
                    this.stack = convertStrToStack(descriptionBlocks[i][DATA_IDX]);
                    break;
                default:
                    throw new WrongTagException(String.format(WrongTagException.DEFAULT_MESSAGE,
                            descriptionBlocks[i][DESCRIPTION_IDX]));
            }
        }
    }

    /**
     * Checks if the bank syntax is correct, otherwise a WrongBankSyntaxException will be thrown
     *
     * @param banks     string that will be checked
     * @param playerCnt number of players participating in the game (is equivalent to the number of needed bank slots)
     * @throws WrongBankSyntaxException Syntax containing an error message if syntax was not followed
     */
    private void checkBankSyntax(String banks, int playerCnt) throws WrongBankSyntaxException {
        try {
            String[] individualBanks = banks.split("\n");
            String[] elems = null;
            String[] currSlotArr = null;


            Set<Integer> playerIdxs = new HashSet<>();
            // check if every player has selected something
            for (int i = 1; i < playerCnt; i++) {
                if (!banks.contains(i + " ")) {
                    playerIdxs.add(i);
                }
            }
            if (playerIdxs.size() != playerCnt - 1 && playerIdxs.size() != 0) {
                throw new WrongBankSyntaxException();
            }

            if (individualBanks.length > 1 && individualBanks[0].contains("-")) {
                throw new WrongBankSyntaxException();
            }

            int temp;
            int banksize;
            String currBank;
            for (int i = 0; i < individualBanks.length; i++) {
                currBank = individualBanks[i];
                banksize = 0;
                elems = currBank.split(",");
                for (String currElem : elems) {
                    if (currElem.startsWith(" ") || currElem.endsWith(" ")) {
                        throw new WrongBankSyntaxException();
                    }

                    // check playerreference (index)
                    currSlotArr = currElem.split(" ");
                    if (!currSlotArr[0].equals("-")) {
                        temp = Integer.parseInt(currSlotArr[0]);
                        if (temp < 0 || temp >= playerCnt) {
                            throw new WrongBankSyntaxException();
                        }
                    }
                    // check domino
                    if (!Tiles.contains(currSlotArr[1])) {
                        throw new WrongBankSyntaxException();
                    }

                    banksize++;
                }

                //check banksize for next round
                if (i != 0 && banksize != 0 && banksize != 4) {
                    throw new WrongBankSyntaxException();
                }

            }
        } catch (Exception e) {
            throw new WrongBankSyntaxException();
        }
    }

    /**
     * Checks if a given string matches the necessary stack syntax
     *
     * @param stack string that will be checked
     * @throws WrongStackSyntaxException Syntax containing an error message if syntax was not followed
     */
    private void checkStackSyntax(String stack) throws WrongStackSyntaxException {
        // Stack may be empty
        if (0 < stack.length()) {
            String[] elems = stack.split(",");
            for (String currElem : elems) {
                if (!Tiles.contains(currElem.replaceAll("\n", ""))) {
                    throw new WrongStackSyntaxException();
                }
            }
        }
    }


    /**
     * Checks if Board syntax matches the following pattern:
     *
     * @param dimensions array contaning the width and height of the board that will be checked
     * @param board      board to check for syntax errors
     * @return dimensions of the checked board
     * @throws WrongBoardSyntaxException exception that will be thrown if anything was found.
     */
    private int[] checkBoardSyntax(int[] dimensions, String board) throws WrongBoardSyntaxException {
        String[] lines = board.split("\n");
        String[] elems = null;
        if (dimensions[1] != lines.length) {
            if (dimensions[1] == NOT_INITIALIZED) {
                dimensions[1] = lines.length;
            } else {
                throw new WrongBoardSyntaxException();
            }
        }
        for (String currLine : lines) {
            elems = currLine.split(" ");
            if (dimensions[0] != elems.length) {
                if (dimensions[0] == NOT_INITIALIZED) {
                    dimensions[0] = elems.length;
                } else {
                    throw new WrongBoardSyntaxException();
                }
            }
            for (String currElem : elems) {
                if (!Board.EMPTY_CELL.equals(currElem) && !SingleTile.contains(currElem)) {
                    throw new WrongBoardSyntaxException();
                }
            }
        }
        return dimensions;
    }


    // --- convert players ----

    /**
     * Generates the default playertype from a given playertype and calls the
     * convertStrToPlayer method to convert the given String to the players
     * board.
     *
     * @param boardStr       board for the player
     * @param idxPlayerArray index of the player that later on will be
     *                       instanciated
     * @param gui            reference to the gui
     * @return a fully instanciated Player containing a board and the
     * corresponding districts
     */
    private Player convertStrToPlayerWithDefaultOccupancy(String boardStr, int idxPlayerArray,
                                                          GUIConnector gui) {
        PlayerType defaultPlayerTypeRelativeToIdx = 0 == idxPlayerArray ? PlayerType.HUMAN
                : PlayerType.DEFAULT;
        return PlayerType.loadPlayerInstanceWithGivenTypeAndBoard(defaultPlayerTypeRelativeToIdx, boardStr,
                idxPlayerArray, gui);
    }

    // --- convert bank ---

    /**
     * Converts a String to two bank types.
     *
     * @param input String representation of the boards content
     * @return both Bank types in a Bank array
     * @pre null != input
     * @pre input.contains(" \ n ")
     */
    private Bank[] convertStrToBanks(String input) {
        assert null != input && input.contains("\n");
        // both banks empty
        if (input.length() == 0 || "\n".equals(input)) {
            return new Bank[]{new Bank(this.players.size()),
                    new Bank(this.players.size())};
        }
        String[] bothBanks = input.split("\n");
        Bank[] output = new Bank[2];
        output[Game.CURRENT_BANK_IDX] = new Bank(bothBanks[0], this.players, new Random());
        // determines if the next round bank is filled
        if (bothBanks.length > 1) {
            output[Game.NEXT_BANK_IDX] = new Bank(bothBanks[1], this.players, new Random());
        } else {
            output[Game.NEXT_BANK_IDX] = new Bank(this.players.size());
        }
        return output;
    }

    // --- convert stack ---

    /**
     * Converts a String to the stack of the game
     *
     * @param input String representation of the stacks content
     * @return a fully instanciated stack
     */
    private List<Domino> convertStrToStack(String input) {
        // Stay maybe empty -> must be checked
        List<Domino> output = new LinkedList<>();
        if (0 < input.length()) {
            String[] dominosStr = input.split(",");
            // Last element is \n doesn't have to be evaluated
            String temp;
            for (int i = 0; i < dominosStr.length; i++) {
                temp = dominosStr[i].substring(0, 4);
                output.add(new Domino(Tiles.fromString(temp)));
            }
        }
        return output;
    }

}
