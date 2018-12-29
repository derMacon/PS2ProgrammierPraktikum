/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.randomizer;

import java.util.Random;

/**
 * @author silas
 */
public class PseudoRandAlwaysHighestVal extends Random {

    @Override
    public int nextInt(int bound) {
        assert 0 < bound;
        return bound - 1;
    }
}
