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

    public static final int DESCRIPTION_IDX = 0;

    public static final int DATA_IDX = 1;

    public static final String BOARD_IDENTIFIER = "Spielfeld";
    public static final String BANK_IDENTIFIER = "Bänke";
    public static final String STACK_IDENTIFIER = "Beutel";

    /**
     * String message for displaying a successful read from the given data
     */
    public static final String SUCCESSFUL_READ_MESSAGE = "Laden erfolgreich";

    private List<Player> players = new LinkedList<>();
    private int currentPlayer;
    private Bank currentBank;
    private Bank nextBank;
    private List<Domino> stack;
    private Domino currDomino;

    // stack of possible dominos, only for internal purposes -> no gettter, every method has to delete the used dominos
    private List<Domino> possibleDominos = Domino.fill(new LinkedList<Domino>());

    /**
     * Reads string input and converts it into the appropriate game instance fields
     * @param input input to convert
     * @return String message containing the error messages, SUCCESSFUL_READ_MESSAGE if reading String was successful
     */
    public String readStr(GUIConnector gui, String input) {
        String[][] descriptionBlocks = gendDescriptiveField(input);
        fillFieldsWithDescriptiveBlocks(descriptionBlocks, gui);
        return SUCCESSFUL_READ_MESSAGE;
    }


    public Player[] getPlayers() {
        return players.toArray(new Player[0]);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Bank getCurrentBank() {
        return currentBank;
    }

    public Bank getNextBank() {
        return nextBank;
    }

    public List<Domino> getStack() {
        return stack;
    }

    public Domino getCurrDomino() {
        return currDomino;
    }



    /**
     * Takes a given String and extracts the information about the game. The data structure can be described as follows:
     *
     * <Spielfeld>
     * <Spielfeld>
     * <Spielfeld>
     * <Spielfeld>
     * <Bänke>
     * <Beutel>
     *
     * @param input String to extract data from
     * @return String array containing the name of the field in the first slot and the actual data in the second
     */
    public String[][] gendDescriptiveField(String input) {
        List<String> blocks = new LinkedList<>();
        // overall sections (board/banks/stack) are seperated
        for (String currBlock : input.split("<")) {
            blocks.add(currBlock);
        }
         // First element of the list is useless
        int usefullElemCnt = blocks.get(0).equals("") ? blocks.size() - 1 : blocks.size();
        // overall sections modified: title in first field, data in second
        String[][] output = new String[usefullElemCnt][2];
        for (int i = 0; i < usefullElemCnt; i++) {
            output[i] = (blocks.get(i + 1).split(">\n"));
        }
        return output;
    }

    /**
     * Generates the data for the fields. Iterates through the given description blocks, checks the title to chose which
     * conversion method will be called
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
                    Bank[] banks = convertStrToBank(descriptionBlocks[i][DATA_IDX]);
                    this.currentBank = banks[Game.CURRENT_BANK_IDX];
                    this.nextBank = banks[Game.NEXT_BANK_IDX];
                    break;
                case STACK_IDENTIFIER:
                    this.stack = convertStrToStack(descriptionBlocks[i][DATA_IDX]);
                    break;
                default:
                    System.out.println("Not a valid identifier at idx " + i);
            }
        }
    }

    // --- convert players ----
    private Player convertStrToPlayerWithDefaultOccupancy(String input, int idxPlayerArray, GUIConnector gui) {
        PlayerType defaultPlayerTypeRelativeToIdx = 0 == idxPlayerArray ? PlayerType.HUMAN : PlayerType.DEFAULT;
        return convertStrToPlayer(input, defaultPlayerTypeRelativeToIdx, idxPlayerArray, gui);
    }

    private Player convertStrToPlayer(String input, PlayerType type, int idxPlayerArray, GUIConnector gui) {
        // TODO update possible dominos list
        return PlayerType.loadPlayerInstanceWithGivenTypeAndBoard(type, new Board(input), idxPlayerArray, gui);
    }
    
    // --- convert bank ---
    private Bank[] convertStrToBank(String input) {
        String[] bothBanks = input.split("\n");
        Bank[] output = new Bank[2];
        output[Game.CURRENT_BANK_IDX] = new Bank(bothBanks[0], this.players);
        output[Game.NEXT_BANK_IDX] = new Bank(bothBanks[1], this.players);
        // TODO update possible dominos list
        return output;
    }

    // --- convert stack ---
    private List<Domino> convertStrToStack(String input) {
        String[] dominosStr = input.split(",");
        List<Domino> output = new LinkedList<>();
        for(String currDomStr : dominosStr) {
            output.add(new Domino(Tiles.fromString(currDomStr)));
        }
        return output;
    }






}