package logic.randomizer;

import java.util.Random;

/**
 *
 * @author silas
 */
public class PseudoRandResidueClass extends Random {
    
    private int modOperand; 
    
    private int outputFactor; 

    public PseudoRandResidueClass() {
        this(1); 
    }
    
    public PseudoRandResidueClass(int modOperand) {
        super(); 
        assert 0 < modOperand; 
        this.modOperand = modOperand; 
        this.outputFactor = 0; 
    }
    
    @Override
    public int nextInt(int bound) {
        int output = this.outputFactor * this.modOperand;
        if(output < bound) {
            this.outputFactor++; 
            return output; 
        } else {
            this.outputFactor = 0; 
            return this.outputFactor * this.modOperand; 
        } 
    }
    
    
    
}
