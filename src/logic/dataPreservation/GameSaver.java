package logic.dataPreservation;

import logic.logicTransfer.Game;

/**
 *
 * @author silas
 */
public class GameSaver extends Preserver {
    
    /**
     * Reference to the game
     */
    Game game; 
    
    public GameSaver(Game game, Logger logger) {
        super(logger); 
        this.game = game;
    }
    
    public void safeCurrentGame() {
        /* TODO insert code
            - selectDir (Preserver)
            - convertGameToStr
            - saveStrToFile
        */
    }
    
    private String convertGameToStr() {
       // TODO insert code - mind: Logger status must also be saved
       return null;
   }
    
    private void saveStrToFile() {
        // TODO insert code
        
    }
    
}
