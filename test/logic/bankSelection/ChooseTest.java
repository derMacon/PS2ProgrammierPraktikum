package logic.bankSelection;

import logic.playerState.Board;
import logic.playerTypes.DefaultAIPlayer;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;
import other.FakeGUI;

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


    // --- genMostEfficientChoose ---
    @Test
    public void testGenMostEfficientChoose_EveryChooseInefficient() {
        Domino fstDom = new Domino(Tiles.genTile(SingleTile.P1, SingleTile.H0), Pos.DOWN_ROT);
        Domino sndDom = new Domino(Tiles.genTile(SingleTile.A1, SingleTile.H0), new Pos(0, 1),
                Pos.UP_ROT);
        Choose fstChoose = new Choose(fstDom, 0, 1);
        Choose sndChoose = new Choose(sndDom, 0, 1);
        Board board = new Board(
                "-- P0 P1\n"
                        + "-- CC O0\n"
                        + "-- A0 A1\n"
        );
        Choose output = Choose.genMostEfficientChoose(Arrays.asList(fstChoose, sndChoose), board);
        assertEquals(fstChoose, output);
    }

    @Test
    public void testGenMostEfficientChoose_SndChooseIsEfficient() {
        Domino fstDom = new Domino(Tiles.genTile(SingleTile.H0, SingleTile.S0), Pos.DOWN_ROT);
        Domino sndDom = new Domino(Tiles.genTile(SingleTile.A1, SingleTile.H0), new Pos(0, 1),
                Pos.UP_ROT);
        Choose fstChoose = new Choose(fstDom, 0, 1);
        Choose sndChoose = new Choose(sndDom, 0, 1);
        Board board = new Board(
                "-- H0 P1\n"
                        + "-- CC O0\n"
                        + "-- A0 A1\n"
        );
        Choose output = Choose.genMostEfficientChoose(Arrays.asList(fstChoose, sndChoose), board);
        assertEquals(sndChoose, output);
    }

    @Test
    public void testGenMostEfficientChoose_FstChooseIsEfficient() {
        Domino fstDom = new Domino(Tiles.genTile(SingleTile.H0, SingleTile.A0), Pos.DOWN_ROT);
        Domino sndDom = new Domino(Tiles.genTile(SingleTile.A1, SingleTile.H0), new Pos(0, 1),
                Pos.UP_ROT);
        Choose fstChoose = new Choose(fstDom, 0, 1);
        Choose sndChoose = new Choose(sndDom, 0, 1);
        Board board = new Board(
                "-- H0 P1\n"
                        + "-- CC O0\n"
                        + "-- A0 A1\n"
        );
        Choose output = Choose.genMostEfficientChoose(Arrays.asList(fstChoose, sndChoose), board);
        assertEquals(fstChoose, output);
    }

    @Test(expected = AssertionError.class)
    public void testGenMostEfficientChoose_NullParam1() {
        Choose.genMostEfficientChoose(null, new Board(1, 1));
    }

    @Test(expected = AssertionError.class)
    public void testGenMostEfficientChoose_NullParam2() {
        Domino fstDom = new Domino(Tiles.genTile(SingleTile.H0, SingleTile.A0), Pos.DOWN_ROT);
        Domino sndDom = new Domino(Tiles.genTile(SingleTile.A1, SingleTile.H0), new Pos(0, 1),
                Pos.UP_ROT);
        Choose fstChoose = new Choose(fstDom, 0, 1);
        Choose sndChoose = new Choose(sndDom, 0, 1);
        Choose.genMostEfficientChoose(Arrays.asList(fstChoose, sndChoose), null);
    }

    @Test(expected = AssertionError.class)
    public void testGenMostEfficientChoose_ListTooSmall() {
        Domino fstDom = new Domino(Tiles.genTile(SingleTile.H0, SingleTile.A0), Pos.DOWN_ROT);
        Choose fstChoose = new Choose(fstDom, 0, 1);
        Choose.genMostEfficientChoose(Arrays.asList(fstChoose), null);
    }

    // --- isSorted ---
    @Test
    public void testIsSorted_EmptyList() {
        assertTrue(Choose.isSorted(new LinkedList<>()));
    }


    @Test
    public void testIsSorted_SortedList_OneElement() {
        List<Choose> list = Arrays.asList(
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.P0)), 0, 1)
        );
        assertTrue(Choose.isSorted(list));
    }

    @Test
    public void testIsSorted_SortedList_ThreeElements() {
        List<Choose> list = Arrays.asList(
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.P0)), 0, 1),
                new Choose(new Domino(Tiles.genTile(SingleTile.H1, SingleTile.P0)), 0, 1),
                new Choose(new Domino(Tiles.genTile(SingleTile.O0, SingleTile.I2)), 0, 1)
        );
        assertTrue(Choose.isSorted(list));
    }


    // --- max with List<Domino> ---
    @Test(expected = AssertionError.class)
    public void testListMax_NullParam1() {
        Choose.max(null, new Board(2, 2));
    }

    @Test(expected = AssertionError.class)
    public void testListMax_NullParam2() {
        List<Choose> listChoose = Arrays.asList(new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1));
        Choose.max(listChoose, null);
    }

    @Test
    public void testListMax_EmptyList() {
        assertNull(Choose.max(new LinkedList<>(), new Board(1, 1)));
    }

    // TODO Singletile - correct import
    @Test(expected = AssertionError.class)
    public void testListMax_UnorderedList() {
        List<Choose> incorrectList = Arrays.asList(
                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.P0)), 0, 1),
                new Choose(new Domino(Tiles.genTile(SingleTile.O0, SingleTile.I2)), 0, 1),
                new Choose(new Domino(Tiles.genTile(SingleTile.H1, SingleTile.P0)), 0, 1)
        );
        Choose.max(incorrectList, new Board(1, 1));
    }

    @Test
    public void testListMax_OneElementInList() {
        List<Choose> listChoose = Arrays.asList(new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1));
        assertEquals(listChoose.get(0), Choose.max(listChoose, new Board("-- -- CC")));
    }

//    @Test
//    public void testListMax_TwoElementsInList_FirstElemWithMorePoints() {
//        List<Choose> listChoose = Arrays.asList(new Choose[]{
//                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 1, 1),
//                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1)
//        });
//        assertEquals(listChoose.get(0), Choose.max(listChoose));
//    }
//
//    @Test
//    public void testListMax_TwoElementsInList_SecondElemWithMorePoints() {
//        List<Choose> listChoose = Arrays.asList(new Choose[]{
//                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1),
//                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 1, 1)
//        });
//        assertEquals(listChoose.get(1), Choose.max(listChoose));
//    }
//
//    @Test
//    public void testListMax_TwoElementsInList_EqualsPoints() {
//        List<Choose> listChoose = Arrays.asList(new Choose[]{
//                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1),
//                new Choose(new Domino(Tiles.genTile(SingleTile.P0, SingleTile.H0)), 0, 1)
//        });
//        assertEquals(listChoose.get(0), Choose.max(listChoose));
//    }


    // --- max with two Choose obj. ---
    @Test
    public void max1() {
    }


}