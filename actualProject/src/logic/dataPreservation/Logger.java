/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.dataPreservation;

import java.io.File;

/**
 * Logger for the game. Uses the singleton pattern, so that there can only be one single instance of this logger.
 * The constructor is private and the instance can only be retuned be the corresponding static getter method.
 * @author silas
 */
public class Logger {

    /**
     * Single instance of the logger. Initialized with null, can be returned with the corresponding getter.
     */
    private static Logger singleInstance = null;

    /**
     * Default file path for the file to which the data will be saved.
     */
    private static final File DEFAULT_FILE = new File("./dataOutput/logFile.txt");

    /**
     * Path to dir where log data should be stored
     */
    private File dir;

    /**
     * Cosntructor only setting the playerCnt, taking a default path as the file path for the data
     */
    private Logger(){
        this.dir = DEFAULT_FILE;
    }

    /**
     * Getter for the logger instance
     */
    public static Logger getInstance() {
        if (null == singleInstance) {
            singleInstance = new Logger();
        }
        return singleInstance;
    }

    /**
     * Setter for the file path (Type: File)
     * @param path File representing the file path
     */
    public void setPath(File path) {
        this.dir = path;
    }

    /**
     * Setter for the file path (Type: String)
     * @param path String representing the file path
     */
    public void setPath(String path) {
        this.dir = new File(path);
    }

    // Actual logger method, prints the given message and then saves it to a given File
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