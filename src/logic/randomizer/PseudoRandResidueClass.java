package logic.randomizer;

import java.util.Random;

/**
 *
 * @author silas
 */
public class PseudoRandResidueClass extends Random {
    
    private int modOperand; 

    public PseudoRandResidueClass() {
        this(1); 
    }
    
    public PseudoRandResidueClass(int modOperand) {
        super(); 
        assert 0 < modOperand; 
        this.modOperand = modOperand; 
    }
    
    @Override
    public int nextInt(int bound) {
        return 0; 
    }
    
    
    
}
