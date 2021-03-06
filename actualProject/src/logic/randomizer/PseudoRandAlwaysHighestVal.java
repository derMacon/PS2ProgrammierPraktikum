/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.randomizer;

import java.util.Random;

/**
 * Random Object that always returns the highest value. Used for testing.
 *
 * @pre 0 < bound
 */
public class PseudoRandAlwaysHighestVal extends Random {
    @Override
    public int nextInt(int bound) {
        assert 0 < bound;
        return bound - 1;
    }
}
