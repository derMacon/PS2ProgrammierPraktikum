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

    /**
     * Path to dir where log data should be stored
     */
    private final File dir;

    /**
     * Constructor setting the default logger of the game containing the human player on idx == 0 and all bots on the
     * slots comming after that
     * @param path path to the log file
     */
    public Logger(File path) {
        this.dir = path;
    }

    /**
     * Constructor with string representation of the path.
     * @param strPath String representation of the directory where the data will be stored
     */
    public Logger(String strPath) {
        this(new File(strPath));
    }

    /**
     * Cosntructor only setting the playerCnt, taking a default path as the file path for the data
     */
    public Logger(){
        this(DEFAULT_FILE_PATH);
    }

    public void printAndSafe(String inputLog) {
        System.out.println(inputLog);
        appendFileWithNewMove(inputLog);
    }


    // --- Methods for organization ---
    private void initializeNewFile(File file) {
        // TODO insert code
    }

    private void appendFileWithNewMove(String moveDescription) {
        // TODO insert code
    }









}
