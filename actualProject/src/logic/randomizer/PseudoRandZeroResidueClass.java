package logic.randomizer;

import java.util.Random;

/**
 *
 * @author silas
 */
public class PseudoRandZeroResidueClass extends Random {

    private int modOperand;

    private int outputFactor;

    public PseudoRandZeroResidueClass() {
        this(1);
    }

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
