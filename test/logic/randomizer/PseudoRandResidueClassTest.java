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
        new PseudoRandResidueClass(0);
    }

    @Test
    public void testNextInt_AlwaysZero() {
        Integer[] expectedOutput = new Integer[]{0, 0, 0, 0};
        Random rand = new PseudoRandResidueClass();
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
        Random rand = new PseudoRandResidueClass();
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
        Random rand = new PseudoRandResidueClass(2);
        List<Integer> actualOutput = new LinkedList<>();
        actualOutput.add(rand.nextInt(3));
        actualOutput.add(rand.nextInt(3));
        assertArrayEquals(expectedOutput, actualOutput.toArray(new Integer[0]));
    }
    
    @Test
    public void testNextInt_ModOperandGreaterThenOne_Overflow() {
        Integer[] expectedOutput = new Integer[]{0, 2, 1, 3, 2, 0};
        Random rand = new PseudoRandResidueClass(3);
        List<Integer> actualOutput = new LinkedList<>();
        actualOutput.add(rand.nextInt(4));
        actualOutput.add(rand.nextInt(4));
        actualOutput.add(rand.nextInt(4));
        actualOutput.add(rand.nextInt(4));
        actualOutput.add(rand.nextInt(4));
        actualOutput.add(rand.nextInt(4));
        assertArrayEquals(expectedOutput, actualOutput.toArray(new Integer[0]));
    }

}
