/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.dataPreservation;

import java.io.File;

/**
 *
 * @author silas
 */
public abstract class Preserver {
    
    /**
     * Reference to the logger
     */
    Logger logger;

    protected File dir;

    public Preserver(Logger logger) {
        this.logger = logger;
    }
        
   private File selectDir() {
       /* opens filechooser for loading and saving. 
       (directory can be both, File OR folder) */
        return null;
    } 
    
   
   
    
   
}
