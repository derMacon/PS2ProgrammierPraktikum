package logic.randomizer;

import java.util.Random;

/**
 * Pseudo random generator used for testing
 */
public class PseudoRandZeroResidueClass extends Random {

    private int modOperand;

    private int outputFactor;

    /**
     * Constructor
     */
    public PseudoRandZeroResidueClass() {
        this(1);
    }

    /**
     * Constructor setting the residue class
     *
     * @param modOperand asdfsadf
     * @pre 0 < modOperand
     */
    public PseudoRandZeroResidueClass(int modOperand) {
        super();
        assert 0 < modOperand;
        this.modOperand = modOperand;
        this.outputFactor = 0;
    }

    @Override
    public int nextInt(int bound) {
        int output = (this.outputFactor * this.modOperand) % bound;
        this.outputFactor++;
        return output;
    }

}
