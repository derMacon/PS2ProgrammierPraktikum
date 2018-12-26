package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.playerState.Player;
import logic.playerTypes.PlayerType;
import logic.token.Domino;
import logic.token.Tiles;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Converter {

    private static final int NOT_INITIALIZED = -1;

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
    // TODO Problem mit dem ä beim Einlesen
    public static final String BANK_IDENTIFIER = "Bänke"; //"B�nke";

    /**
     * Identifier for the stack
     */
    public static final String STACK_IDENTIFIER = "Beutel";

    /**
     * String message for displaying an unsuccessful read from the given data
     */
    public static final String UNSUCCESSFUL_READ_MESSAGE = "Laden nicht erfolgreich";
    /**
     * String message for displaying a successful read from the given data
     */
    public static final String SUCCESSFUL_READ_MESSAGE = "Laden erfolgreich";

    public static final String TAG_OPENER = "<";
    public static final String TAG_CLOSER = ">";

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

    // stack of possible dominos, only for internal purposes
    // -> no gettter, every method has to delete the used dominos
    private List<Domino> possibleDominos = Domino.fill(new LinkedList<Domino>());

    private int boardScale;

    /**
     * Reads string input and converts it into the appropriate game instance
     * fields
     *
     * @param input input to convert
     * @return String message containing the error messages,
     * SUCCESSFUL_READ_MESSAGE if reading String was successful
     */
    public String readStr(GUIConnector gui, String input) {
        // TODO Fehlerbehandlung erweitern
        try {

            if (input == null || input.length() == 0) {
                throw new IOException(UNSUCCESSFUL_READ_MESSAGE);
            }
            if (!input.startsWith("<")) {
                System.out.println(input);
                throw new WrongTagException("Document does not start with <");
            }

            String[][] descriptionBlocks = genDescriptiveField(input);
            fillFieldsWithDescriptiveBlocks(descriptionBlocks, gui);
            return SUCCESSFUL_READ_MESSAGE;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

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
     * @return
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

    private String genTag(String input) throws WrongTagException {
        if (null == input) {
            return null;
        }
        String modifiedInput = input.replaceAll(TAG_CLOSER + "(?s).*", "");
        // Only boards with comment on who own it (regex: ".*" meaning take all
//        if (modifiedInput.matches(BOARD_IDENTIFIER + ".*" + TAG_CLOSER + ".*")) {
//            modifiedInput = BOARD_IDENTIFIER;
//        } else if (modifiedInput.matches(BANK_IDENTIFIER + TAG_CLOSER + ".*")) {
//            modifiedInput = BANK_IDENTIFIER;
//        } else if (modifiedInput.matches(STACK_IDENTIFIER + TAG_CLOSER + ".*")) {
//            modifiedInput = STACK_IDENTIFIER;
//        } else {
//            throw new WrongTagException(input.replaceAll("\n.*", ""));
//        }
        if (modifiedInput.matches(BOARD_IDENTIFIER + ".*")) {
            return BOARD_IDENTIFIER;
        }
        if(modifiedInput.equals(BANK_IDENTIFIER)
                || modifiedInput.equals(STACK_IDENTIFIER)) {
            return modifiedInput;
        }
        throw new WrongTagException();
    }

    protected String genData(String input) throws WrongTagException {
        // (?s).* to match all chars (including the linebreak)
        String pattern1 = "[" + BOARD_IDENTIFIER + "(?s).*|" + BANK_IDENTIFIER + "|"
                + STACK_IDENTIFIER + "]" + TAG_CLOSER + "(?s).*";
        String pattern = BOARD_IDENTIFIER + "(?s).*" + TAG_CLOSER + "\n(?s)" + ".*" +
                "|" + BANK_IDENTIFIER + TAG_CLOSER + "\n(?s).*" +
                "|" + STACK_IDENTIFIER + TAG_CLOSER + "\n(?s).*";
        ;
        if (null != input && input.matches(pattern)) {
            String[] tempBlock = input.split(TAG_CLOSER + "\n");
            return tempBlock.length > 1 ? tempBlock[1] : "";
        } else {
            throw new WrongTagException();
        }
    }

    /**
     * Takes a given String and extracts the information about the game. The
     * data structure can be described as follows:
     *
     * <Spielfeld>\n text
     * <Spielfeld>\n text
     * <Spielfeld>\n text
     * <Spielfeld>\n text
     * <Bänke>\n text\n
     * <Beutel>\n text
     * <p>
     * Procedure: - Genereate a 2-dim String array containing a the tag for the
     * information in the first field of a array entry and the actual data in
     * the second. - Data is seperated from the corresponding identifier
     *
     * @param input String to extract data from
     * @return String array containing the name of the field in the first slot
     * and the actual data in the second
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
     * Generates the data for the fields. Iterates through the given description
     * blocks, checks the title to chose which conversion method will be called
     *
     * @param descriptionBlocks
     * @param gui
     */
    public void fillFieldsWithDescriptiveBlocks(String[][] descriptionBlocks, GUIConnector gui)
            throws WrongTagException, WrongBoardSyntaxException {
        // TODO delete before final commit
        Integer xDimension = NOT_INITIALIZED;
        Integer yDimension = NOT_INITIALIZED;
        for (int i = 0; i < descriptionBlocks.length; i++) {
            switch (descriptionBlocks[i][DESCRIPTION_IDX]) {
                case BOARD_IDENTIFIER:
//                    checkBoardSyntax(xDimension, yDimension, descriptionBlocks[i][DATA_IDX]);
                    this.players.add(i, convertStrToPlayerWithDefaultOccupancy(
                            descriptionBlocks[i][DATA_IDX], i, gui));
                    break;
                case BANK_IDENTIFIER:
                    Bank[] banks = convertStrToBanks(descriptionBlocks[i][DATA_IDX]);
                    this.currentBank = banks[Game.CURRENT_BANK_IDX];
                    this.nextBank = banks[Game.NEXT_BANK_IDX];
                    // TODO Konstante verwenden (4 ist eine magic number)
                    this.currBankPos = 4 - descriptionBlocks[Game.CURRENT_BANK_IDX].length;
                    break;
                case STACK_IDENTIFIER:
                    this.stack = convertStrToStack(descriptionBlocks[i][DATA_IDX]);
                    break;
                default:
                    throw new WrongTagException(String.format(WrongTagException.DEFAULT_MESSAGE,
                            descriptionBlocks[i][DESCRIPTION_IDX]));
            }
        }
    }

    /**
     * Checks if Board syntax matches the following pattern:
     * // TODO pattern angeben.
     * @param xDim x-Dimension of the first Board. All other boards have to have the same
     *             dimension. Integer pointer to return value for future boards.
     * @param yDim y-Dimension
     * @param board board to check for syntax errors
     * @throws WrongBoardSyntaxException exception that will be thrown if anything was found.
     */
    private void checkBoardSyntax(Integer xDim, Integer yDim, String board) throws WrongBoardSyntaxException {
        String[] lines = board.split("\n");
        String[] elems = null;
        if (yDim != lines.length) {
            if (yDim == NOT_INITIALIZED) {
                yDim = lines.length;
            } else {
                throw new WrongBoardSyntaxException();
            }
        }
        for (String currLine : lines) {
            elems = currLine.split(" ");
            if (xDim != elems.length) {
                if (xDim == NOT_INITIALIZED) {
                    xDim = elems.length;
                } else {
                    throw new WrongBoardSyntaxException();
                }
            }
            for(String currElem : elems) {
                if (!Tiles.isValidTile(currElem)) {
                    throw new WrongBoardSyntaxException();
                }
            }
        }

    }


    // --- convert players ----

    /**
     * Generates the default playertype from a given playertype and calls the
     * convertStrToPlayer method to convert the given String to the players
     * board.
     *
     * @param input          board for the player
     * @param idxPlayerArray index of the player that later on will be
     *                       instanciated
     * @param gui            reference to the gui
     * @return a fully instanciated Player containing a board and the
     * corresponding districts
     */
    private Player convertStrToPlayerWithDefaultOccupancy(String input, int idxPlayerArray,
                                                          GUIConnector gui) {
        PlayerType defaultPlayerTypeRelativeToIdx = 0 == idxPlayerArray ? PlayerType.HUMAN
                : PlayerType.DEFAULT;
        return convertStrToPlayer(input, defaultPlayerTypeRelativeToIdx, idxPlayerArray, gui);
    }

    /**
     * Method calls the static factory method to instanciate a the desired
     * player with the given information.
     *
     * @param input          board for the player
     * @param type           type of the player that will be instanciated
     * @param idxPlayerArray index of the player that later on will be
     *                       instanciated
     * @param gui            reference to the gui
     * @return a fully instanciated Player containing a board and the
     * corresponding districts
     */
    private Player convertStrToPlayer(String input, PlayerType type, int idxPlayerArray, GUIConnector gui) {
        // TODO update possible dominos list
        return PlayerType.loadPlayerInstanceWithGivenTypeAndBoard(type, input, idxPlayerArray, gui);
    }

    // --- convert bank ---

    /**
     * Converts a String to two bank types.
     *
     * @param input String representation of the boards content
     * @return both Bank types in a Bank array
     */
    private Bank[] convertStrToBanks(String input) {
        if (input.length() == 0 || input.equals("\n")) {
            return new Bank[]{new Bank(this.players.size()),
                    new Bank(this.players.size())};
        }
        // TODO ueberarbeiten
        String[] bothBanks = input.split("\n");
        Bank[] output = new Bank[2];
        // TODO eventuell Schnittstelle fuer Randomobj anlegen
        output[Game.CURRENT_BANK_IDX] = new Bank(bothBanks[0], this.players, new Random());
        output[Game.NEXT_BANK_IDX] = new Bank(bothBanks[1], this.players, new Random());
        // TODO update possible dominos list
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
            // Last elements \n doesn't have to to be evaluated
            String temp;
            for (int i = 0; i < dominosStr.length; i++) {
                temp = dominosStr[i].substring(0, 4);
                output.add(new Domino(Tiles.fromString(temp)));
            }
        }
        return output;
    }

}
