package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.playerTypes.PlayerType;
import logic.token.Domino;
import logic.token.Tiles;

import java.util.LinkedList;
import java.util.List;

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

    // --- fields that will be filled by this class -> Will be transfered to the game via Getter
    /**
     * Players of the game
     */
    private List<Player> players = new LinkedList<>();

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

    // stack of possible dominos, only for internal purposes -> no gettter, every method has to delete the used dominos
    private List<Domino> possibleDominos = Domino.fill(new LinkedList<Domino>());

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
        if(input == null || input.length() == 0) {
            return UNSUCCESSFUL_READ_MESSAGE;
        } else {
            String[][] descriptionBlocks = genDescriptiveField(input);
            fillFieldsWithDescriptiveBlocks(descriptionBlocks, gui);
            return SUCCESSFUL_READ_MESSAGE;
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
    public String[][] genDescriptiveField(String input) {
        List<String> blocks = new LinkedList<>();
        // overall sections (board/banks/stack) are seperated
        for (String currBlock : input.split("<")) {
            blocks.add(currBlock);
        }
        blocks.remove(""); // First element may be empty because of split()

        // Data seperated from Identifier
        String[][] output = new String[blocks.size()][2];
        for (int i = 0; i < blocks.size(); i++) {
            output[i] = blocks.get(i).split(">\n");
            System.out.println(i);
            // if any args are empty
            if (output[i].length < 2) {
               output[i] = new String[] {output[i][0], ""};   
            }
        }
        //<editor-fold defaultstate="collapsed" desc="Alternative">
//        // First element of the list is useless
//        int usefullElemCnt = blocks.get(0).equals("") ? blocks.size() - 1 : blocks.size();
//        // overall sections modified: title in first field, data in second
//        String[][] output = new String[usefullElemCnt][2];
//        for (int i = 0; i < usefullElemCnt; i++) {
//            output[i] = (blocks.get(i + 1).split(">\n"));
//        }
        //</editor-fold>
        return output;
    }

    /**
     * Generates the data for the fields. Iterates through the given description
     * blocks, checks the title to chose which conversion method will be called
     *
     * @param descriptionBlocks
     * @param gui
     */
    public void fillFieldsWithDescriptiveBlocks(String[][] descriptionBlocks, GUIConnector gui) {
        for (int i = 0; i < descriptionBlocks.length; i++) {
            switch (descriptionBlocks[i][DESCRIPTION_IDX]) {
                case BOARD_IDENTIFIER:
                    this.players.add(i, convertStrToPlayerWithDefaultOccupancy(descriptionBlocks[i][DATA_IDX], i, gui));
                    break;
                case BANK_IDENTIFIER:
                    Bank[] banks = convertStrToBanks(descriptionBlocks[i][DATA_IDX]);
                    this.currentBank = banks[Game.CURRENT_BANK_IDX];
                    this.nextBank = banks[Game.NEXT_BANK_IDX];
                    break;
                case STACK_IDENTIFIER:
                    this.stack = convertStrToStack(descriptionBlocks[i][DATA_IDX]);
                    break;
                default:
                    System.out.println("Not a valid identifier at idx " + i + " -> " + descriptionBlocks[i][0] + descriptionBlocks[i][1]);
            }
        }
    }

    // --- convert players ----
    /**
     * Generates the default playertype from a given playertype and calls the
     * convertStrToPlayer method to convert the given String to the players
     * board.
     *
     * @param input board for the player
     * @param idxPlayerArray index of the player that later on will be
     * instanciated
     * @param gui reference to the gui
     * @return a fully instanciated Player containing a board and the
     * corresponding districts
     */
    private Player convertStrToPlayerWithDefaultOccupancy(String input, int idxPlayerArray, GUIConnector gui) {
        PlayerType defaultPlayerTypeRelativeToIdx = 0 == idxPlayerArray ? PlayerType.HUMAN : PlayerType.DEFAULT;
        return convertStrToPlayer(input, defaultPlayerTypeRelativeToIdx, idxPlayerArray, gui);
    }

    /**
     * Method calls the static factory method to instanciate a the desired
     * player with the given information.
     *
     * @param input board for the player
     * @param type type of the player that will be instanciated
     * @param idxPlayerArray index of the player that later on will be
     * instanciated
     * @param gui reference to the gui
     * @return a fully instanciated Player containing a board and the
     * corresponding districts
     */
    private Player convertStrToPlayer(String input, PlayerType type, int idxPlayerArray, GUIConnector gui) {
        // TODO update possible dominos list
        return PlayerType.loadPlayerInstanceWithGivenTypeAndBoard(type, new Board(input), idxPlayerArray, gui);
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
        output[Game.CURRENT_BANK_IDX] = new Bank(bothBanks[0], this.players);
        output[Game.NEXT_BANK_IDX] = new Bank(bothBanks[1], this.players);
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
        String[] dominosStr = input.split(",");
        List<Domino> output = new LinkedList<>();
//        for (String currDomStr : dominosStr) {
//            output.add(new Domino(Tiles.fromString(currDomStr)));
//        }
        // Last elements \n doesn't have to to be evaluated
        String temp;
        for (int i = 0; i < dominosStr.length; i++) {
            temp = dominosStr[i].substring(0, 4);
            output.add(new Domino(Tiles.fromString(temp)));
        }
        return output;
    }

}
