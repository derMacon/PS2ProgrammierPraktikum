/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.dataPreservation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.ZonedDateTime;

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

    public static final String selectionLoggerFormat = "%s chose %s at index %d for %s round";

    public static final String ccDragLoggerFormat = "%s dragged center to %s";

    public static final String dismissalLoggerFormat = "%s did not use %s";

    public static final String GAME_SEPARATOR =  "-----------------------------------\n" +
            ZonedDateTime.now().toString();

    public static final String TERMINATION_DELIMITER = "...................................";

    public static final String CURR_ROUND_IDENTIFIER = "current";
    public static final String NEXT_ROUND_IDENTIFIER = "next";

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

    private void appendFileWithNewMove(String logInput) {
        // TODO insert code
        try {
            Writer outputStream = new BufferedWriter(new FileWriter(this.dir, true));
            outputStream.write(logInput + "\n");
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
