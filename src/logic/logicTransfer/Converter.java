package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.playerState.Player;
import logic.playerTypes.PlayerType;
import logic.token.Domino;

import java.util.LinkedList;
import java.util.List;

public class Converter {

    public static final int DESCRIPTION_IDX = 0;

    public static final int DATA_IDX = 1;

    public static final String BOARD_IDENTIFIER = "Spielfeld";
    public static final String BANK_IDENTIFIER = "Bänke";
    public static final String STACK_IDENTIFIER = "Beutel";

    private List<Player> players = new LinkedList<>();
    private int currentPlayer;
    private Bank currentBank;
    private Bank nextBank;
    private List<Domino> stack;
    private Domino currDomino;

    public static int getDescriptionIdx() {
        return DESCRIPTION_IDX;
    }

    public static int getDataIdx() {
        return DATA_IDX;
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

    public Converter(String input, GUIConnector gui) {
        String[][] descriptionBlocks = gendDescriptiveField(input);
        fillFieldsWithDescriptiveBlocks(descriptionBlocks, gui);
    }

    /**
     * Takes a given String and extracts the information about the game. The Datastructure can be described as follows:
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

        for (String currBlock : input.split("<")) {
            blocks.add(currBlock);
        }

        /**
         * First element of the list is useless
         */
        int usefullElemCnt = blocks.get(0).equals("") ? blocks.size() - 1 : blocks.size();

        String[][] output = new String[usefullElemCnt][2];
        for (int i = 0; i < usefullElemCnt; i++) {
            output[i] = (blocks.get(i + 1).split(">"));
        }

        return output;
    }

    public void fillFieldsWithDescriptiveBlocks(String[][] descriptionBlocks, GUIConnector gui) {
        for (int i = 0; i < descriptionBlocks.length; i++) {
            switch (descriptionBlocks[i][DESCRIPTION_IDX]) {
                case BOARD_IDENTIFIER: break;
                case BANK_IDENTIFIER: break;
                case STACK_IDENTIFIER: break;
                default:
                    System.out.println("Not a valid identifier at idx " + i);
            }
        }
    }






}
