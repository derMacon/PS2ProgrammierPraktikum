package logic.playerTypes;

import logic.logicTransfer.GUIConnector;
import logic.playerState.Player;

/**
 * Enum serving as a static factory of the player class
 */
public enum PlayerType {
    HUMAN("Menschlicher Spieler"),
    DEFAULT("Standard Gegner"),
    CAPABLE_OF_MOVING_BOARD("Not supported yet"),
    INVOLVE_NEXT_BANK("Not supported yet"),
    INVOLVE_OTHER_PLAYERS("Not supported yet"),
    CONSIDERING_PLAYED_CARDS("Not supported yet");

    private final String guiRepresentation;

    PlayerType(String guiRepresentation) {
        this.guiRepresentation = guiRepresentation;
    }

    /**
     * Getter for the String representation of a single element
     * @return
     */
    public String getStringRepresentation() {
        return guiRepresentation;
    }

//    public static Player getPlayerInstanceWithoutGivenType(int idx, GUIConnector gui, int boardSizeX, int boardSizeY) {
//        PlayerType defaultPlayerTypeRelativeToIdx = 0 == idx ? HUMAN : DEFAULT;
//        return getPlayerInstanceWithGivenType(defaultPlayerTypeRelativeToIdx, idx, gui, boardSizeX, boardSizeY);
//    }

    /**
     * Static factory for the players of a game
     * @param type Type of the player
     * @param gui Reference to the gui
     * @param boardSizeX X - dimension of the board
     * @param boardSizeY Y - dimension of the board
     * @return a player of the given type
     */
    public static Player getPlayerInstanceWithGivenType(PlayerType type, int idx, GUIConnector gui, int boardSizeX, int boardSizeY) {
        Player output = null;
        switch (type) {
            case HUMAN:
                output = new HumanPlayer(gui, idx, boardSizeX, boardSizeY);
                break;
            case DEFAULT:
               output = new DefaultAIPlayer(gui, idx, boardSizeX, boardSizeY);
                break;
            default:
                System.out.println("Not supported yet -> cannot instanciate desired playertype");
        }
        return output;
    }

}
