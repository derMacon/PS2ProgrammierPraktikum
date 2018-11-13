package logic.randomizer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author silas
 */
public class PseudoRandResidueClassTest {

    // --- nextInt ---
    @Test(expected = AssertionError.class)
    public void testNextInt_ModOperandToSmall() {
        new PseudoRandZeroResidueClass(0);
    }

    @Test
    public void testNextInt_AlwaysZero() {
        Integer[] expectedOutput = new Integer[]{0, 0, 0, 0};
        Random rand = new PseudoRandZeroResidueClass();
        List<Integer> actualOutput = new LinkedList<>();
        actualOutput.add(rand.nextInt(1));
        actualOutput.add(rand.nextInt(1));
        actualOutput.add(rand.nextInt(1));
        actualOutput.add(rand.nextInt(1));
        assertArrayEquals(expectedOutput, actualOutput.toArray(new Integer[0]));
    }

    @Test
    public void testNextInt_Overflow() {
        Integer[] expectedOutput = new Integer[]{0, 1, 2, 0};
        Random rand = new PseudoRandZeroResidueClass();
        List<Integer> actualOutput = new LinkedList<>();
        actualOutput.add(rand.nextInt(3));
        actualOutput.add(rand.nextInt(3));
        actualOutput.add(rand.nextInt(3));
        actualOutput.add(rand.nextInt(3));
        assertArrayEquals(expectedOutput, actualOutput.toArray(new Integer[0]));
    }
    
    @Test
    public void testNextInt_ModOperandGreaterThenOne() {
        Integer[] expectedOutput = new Integer[]{0, 2};
        Random rand = new PseudoRandZeroResidueClass(2);
        List<Integer> actualOutput = new LinkedList<>();
        actualOutput.add(rand.nextInt(3));
        actualOutput.add(rand.nextInt(3));
        assertArrayEquals(expectedOutput, actualOutput.toArray(new Integer[0]));
    }
    
    @Test
    public void testNextInt_ModOperandGreaterThenOne_Overflow() {
        Integer[] expectedOutput = new Integer[]{0, 3, 1, 4, 2, 0, 3};
        Random rand = new PseudoRandZeroResidueClass(3);
        List<Integer> actualOutput = new LinkedList<>();
        actualOutput.add(rand.nextInt(5));
        actualOutput.add(rand.nextInt(5));
        actualOutput.add(rand.nextInt(5));
        actualOutput.add(rand.nextInt(5));
        actualOutput.add(rand.nextInt(5));
        actualOutput.add(rand.nextInt(5));
        actualOutput.add(rand.nextInt(5));
        assertArrayEquals(expectedOutput, actualOutput.toArray(new Integer[0]));
    }
   
    @Test
    public void testNextInt_ChangingBound() {
        Integer[] expectedOutput = new Integer[]{0, 3, 0, 1, 0, 0, 3};
        Random rand = new PseudoRandZeroResidueClass(3);
        List<Integer> actualOutput = new LinkedList<>();
        actualOutput.add(rand.nextInt(5));
        actualOutput.add(rand.nextInt(4));
        actualOutput.add(rand.nextInt(3));
        actualOutput.add(rand.nextInt(2));
        actualOutput.add(rand.nextInt(1));
        actualOutput.add(rand.nextInt(1));
        actualOutput.add(rand.nextInt(5));
        assertArrayEquals(expectedOutput, actualOutput.toArray(new Integer[0]));
    }

}
