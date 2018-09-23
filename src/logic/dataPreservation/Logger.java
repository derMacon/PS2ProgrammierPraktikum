/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.dataPreservation;

import logic.token.Domino;

import java.io.File;

/**
 *
 * @author silas
 */
public class Logger {

    private static final File DEFAULT_FILE_PATH = new File("xxx");

    private static final String DOMINO_OPENING_BRACKET = "[";
    private static final String DOMINO_CLOSING_BRACKET = "]";
    private static final String HUMAN_PLAYER_NAME = "HUMAN";
    private static final String BOT_PLAYER_NAME= "BOT";

    private final String[] playerNames;

    /**
     * Path to dir where log data should be stored
     */
    private final File dir;

    /**
     * Constructor setting the default logger of the game containing the human player on idx == 0 and all bots on the
     * slots comming after that
     * @param playerCnt number of players participating in the game
     */
    public Logger(int playerCnt, File path) {
        assert 0 < playerCnt && null != path;
        this.dir = path;
        this.playerNames = new String[playerCnt];
        this.playerNames[0] = HUMAN_PLAYER_NAME;
        for (int i = 1; i < playerCnt; i++) {
            this.playerNames[i] = BOT_PLAYER_NAME + i;
        }
    }

    /**
     * Constructor with string representation of the path.
     * @param playerCnt number of players participating in the game
     * @param strPath String representation of the directory where the data will be stored
     */
    public Logger(int playerCnt, String strPath) {
        this(playerCnt, new File(strPath));
    }

    /**
     * Cosntructor only setting the playerCnt, taking a default path as the file path for the data
     * @param playerCnt
     */
    public Logger(int playerCnt){
        this(playerCnt, DEFAULT_FILE_PATH);
    }


    // --- Methods for organization ---
    private void initializeNewFile(File file) {
        // TODO insert code
    }

    private void appendFileWithNewMove(String moveDescription) {
        // TODO insert code
    }

    private void updateOutput(String moveDescr) {
        appendFileWithNewMove(moveDescr);
        System.out.println(moveDescr);
    }


    // --- String representation of things ---

    private String getDomRepresentation(Domino domino) {
        return DOMINO_OPENING_BRACKET + domino.getFstVal() + ", " + domino.getSndVal() + DOMINO_CLOSING_BRACKET;
    }

    // --- actual logger methods ---
    public void setToBank(int ordBank, Domino[] dominos) {
        for (int i = 0; i < dominos.length; i++) {
            setToBank(ordBank, i, dominos[i]);
        }
    }

    public void setToBank(int ordBank, int index, Domino domino) {
        String strBank = 0 == ordBank ? "this round's" : "next round's";
        updateOutput(getDomRepresentation(domino) + " drawn to " + strBank + " bank at index " + index);
    }

    public void selectDomForNextRound(int currPlayer, Domino domino, int idx) {
        updateOutput(this.playerNames[currPlayer] + " chose " + getDomRepresentation(domino) + " at index " +
                idx + " for next round");
    }

    public void selectDomForThisRound(int currPlayer, Domino domino, int idx) {
        updateOutput(this.playerNames[currPlayer] + " chose " + getDomRepresentation(domino) + " at index " +
                idx + " for this round");
    }






}
