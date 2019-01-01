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
 */
public class Logger {

    //<editor-fold defaultstate="collapsed" desc="Format strings used as templates in the different classes">
    public static final String DEPOSIT_LOGGER_FORMAT = "%s put %s %s to %s";
    public static final String SELECTION_LOGGER_FORMAT = "%s chose %s at index %d for %s round";
    public static final String CC_DRAG_LOGGER_FORMAT = "%s dragged center to %s";
    public static final String DISMISSAL_LOGGER_FORMAT = "%s did not use %s";
    public static final String ERROR_DELIMITER = "...................................";
    public static final String GAME_SEPARATOR = "-----------------------------------\n"
            + ZonedDateTime.now().toString();
    //</editor-fold>

    /**
     * Default file path for the file to which the data will be saved.
     */
    private static final File DEFAULT_FILE = new File("./dataOutput/logFile.txt");
    /**
     * Single instance of the logger. Initialized with null, can be returned with the corresponding getter.
     */
    private static Logger singleInstance = null;
    /**
     * Path to dir where log data should be stored
     */
    private File dir;

    /**
     * Cosntructor only setting the playerCnt, taking a default path as the file path for the data
     */
    private Logger() {
        this.dir = DEFAULT_FILE;
    }

    /**
     * Getter for the logger instance
     * @return logger instance
     */
    public static Logger getInstance() {
        if (null == singleInstance) {
            singleInstance = new Logger();
        }
        return singleInstance;
    }

    /**
     * Setter for the file path (Type: File)
     *
     * @param path File representing the file path
     */
    public void setPath(File path) {
        this.dir = path;
    }

    /**
     * Setter for the file path (Type: String)
     *
     * @param path String representing the file path
     */
    public void setPath(String path) {
        this.dir = new File(path);
    }

    /**
     * prints the given message and then saves it to a given File
     * @param inputLog text that will be displayed and saved
     */
    public void printAndSafe(String inputLog) {
        System.out.println(inputLog);
        appendFileWithNewMove(inputLog);
    }

    /**
     * appends the log file with a given message
     * @param logInput input to attach to the logfile
     */
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
