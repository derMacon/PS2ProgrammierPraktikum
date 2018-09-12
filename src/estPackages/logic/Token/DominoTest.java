package estPackages.logic.Token;

import logic.token.*;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class DominoTest {

    @Test
    public void testFill_WithoutCityHall() {
        List<Domino> stack = new LinkedList<>();
        Domino.fill(stack);
        assertTrue(stack.size() == 48);
    }

}