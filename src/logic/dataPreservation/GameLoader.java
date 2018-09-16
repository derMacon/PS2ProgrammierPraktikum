package logic.dataPreservation;

import java.io.File;
import logic.logicTransfer.Game;

/**
 *
 * @author silas
 */
public class GameLoader extends Preserver {
    
    /**
     * Reference to the file
     */
    File txtFile; 
    
    public GameLoader(Logger logger) {
        super(logger); 
    }
    
    public GameLoader(File txtFile, Logger logger) {
        super(logger); 
        this.txtFile = txtFile; 
    }
    
    public GameLoader(String directory, Logger logger) {
        this(new File(directory), logger); 
    }

    /**
     * Loads game from 
     */
    public Game loadPastGame() {
        /* TODO insert code - mind: logger must also be reloaded
            - selectDir (Preserver)
            - loadStrFromFile
            - convertStrToGame
            */
        return null;
    }
    
    private String loadStrFromFile() {
        // TODO insert code
        return null;
    }
    
    private Game convertStrToGame() {
        // TODO insert code
        return null;
    }
    
}
