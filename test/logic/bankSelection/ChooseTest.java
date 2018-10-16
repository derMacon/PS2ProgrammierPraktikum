package logic.bankSelection;

import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ChooseTest {

    // --- equals ---
    @Test
    public void testEquals_NullParam() {
        assertFalse(new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 0).equals(null));
    }

    @Test
    public void testEquals_SameReference() {
        Choose ref = new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 0);
        assertTrue(ref.equals(ref));
    }

    @Test
    public void testEquals_SameObjNewReference() {
        Choose ref1 = new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 0);
        Choose ref2 = new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 0);
        assertTrue(ref1.equals(ref2));
    }

    @Test
    public void testEquals_NotSameObjNewReference() {
        Choose ref1 = new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1);
        Choose ref2 = new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 0);
        assertFalse(ref1.equals(ref2));
    }


    // --- max with List<Domino> ---
    @Test(expected = AssertionError.class)
    public void testListMax_NullParam() {
        Choose.max(null);
    }

    @Test
    public void testListMax_EmptyList() {
        assertNull(Choose.max(new LinkedList<>()));
    }

    @Test
    public void testListMax_OneElementInList() {
        List<Choose> listChoose = Arrays.asList(new Choose[] {
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1)
        });
        assertEquals(listChoose.get(0), Choose.max(listChoose));
    }

    @Test
    public void testListMax_TwoElementsInList_FirstElemWithMorePoints() {
        List<Choose> listChoose = Arrays.asList(new Choose[] {
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 1, 1),
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1)
        });
        assertEquals(listChoose.get(0), Choose.max(listChoose));
    }

    @Test
    public void testListMax_TwoElementsInList_SecondElemWithMorePoints() {
        List<Choose> listChoose = Arrays.asList(new Choose[] {
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1),
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 1, 1)
        });
        assertEquals(listChoose.get(1), Choose.max(listChoose));
    }

    @Test
    public void testListMax_TwoElementsInList_EqualsPoints() {
        List<Choose> listChoose = Arrays.asList(new Choose[] {
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1),
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1)
        });
        assertEquals(listChoose.get(0), Choose.max(listChoose));
    }


    // --- max with two Choose obj. ---
    @Test
    public void max1() {
    }



}