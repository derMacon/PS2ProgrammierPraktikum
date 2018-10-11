package logic.token;

import logic.token.DistrictType;
import logic.token.Domino;
import logic.token.Pos;
import logic.token.Tiles;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static logic.token.SingleTile.*;
import static org.junit.Assert.*;

public class DominoTest {

    //<editor-fold defaultstate="collapsed" desc="Modified tests from ueb09">
    /*
    Domino P0_H0_Val13
            fstPos  sndPos  fst  snd
    rot 0   1,1     2,1     P0   H0
    rot 1   1,1     1,2     P0   H0
    rot 2   0,1     1,1     H0   H0
    rot 3   1,0     1,1     H0   H0
    */

    @Test
    public void testPositions_rot0() {
        Domino dom = new Domino(Tiles.P0H0_Val13, new Pos(1, 1), 0);
        assertEquals(0, dom.getRot());
        assertEquals(P0, dom.getFstVal());
        assertEquals(H0, dom.getSndVal());
        assertEquals(new Pos(2, 1), dom.getSndPos());
    }

    @Test
    public void testPositions_rot1() {
        Domino dom = new Domino(Tiles.P0H0_Val13, new Pos(1, 1), 1);
        assertEquals(1, dom.getRot());
        assertEquals(P0, dom.getFstVal());
        assertEquals(H0, dom.getSndVal());
        assertEquals(new Pos(1, 2), dom.getSndPos());
    }

    @Test
    public void testPositions_rot2() {
        Domino dom = new Domino(Tiles.P0H0_Val13, new Pos(0, 1), 2);
        assertEquals(2, dom.getRot());
        assertEquals(H0, dom.getFstVal());
        assertEquals(P0, dom.getSndVal());
        assertEquals(new Pos(1, 1), dom.getSndPos());
    }

    @Test
    public void testPositions_rot3() {
        Domino dom = new Domino(Tiles.P0H0_Val13, new Pos(1, 0), 3);
        assertEquals(3, dom.getRot());
        assertEquals(H0, dom.getFstVal());
        assertEquals(P0, dom.getSndVal());
        assertEquals(new Pos(1, 1), dom.getSndPos());
    }
    //</editor-fold>

    // --- constructor ---
    @Test
    public void testDifferentConstructors_TestEquality() {
        Domino dom1 = new Domino(Tiles.P0H0_Val13, new Pos(0, 0), 0);
        Domino dom2 = new Domino(Tiles.P0H0_Val13, new Pos(0, 0));
        Domino dom3 = new Domino(Tiles.P0H0_Val13, 0);
        Domino dom4 = new Domino(Tiles.P0H0_Val13);
        assertTrue(dom1.equals(dom2));
        assertTrue(dom1.equals(dom3));
        assertTrue(dom1.equals(dom4));
        assertTrue(dom2.equals(dom3));
        assertTrue(dom2.equals(dom4));
        assertTrue(dom3.equals(dom4));
    }

    @Test(expected = AssertionError.class)
    public void testDifferentConstructors_NullParam1a() {
        new Domino(null, new Pos(0, 0), 0);
    }

    @Test
    public void testDifferentConstructors_NullParam1b() {
        Domino dom = new Domino(Tiles.P0H0_Val13, null, 0);
        assertNull(dom.getFstPos());
        assertNull(dom.getSndPos());
    }

    @Test(expected = AssertionError.class)
    public void testDifferentConstructors_NullParam2a() {
        new Domino(null, new Pos(0, 0));
    }

    @Test
    public void testDifferentConstructors_NullParam2b() {
        Domino dom = new Domino(Tiles.P0H0_Val13, null);
        assertNull(dom.getFstPos());
        assertNull(dom.getSndPos());
    }

    @Test(expected = AssertionError.class)
    public void testDifferentConstructors_NullParam3a() {
        new Domino(null, 0);
    }

    @Test(expected = AssertionError.class)
    public void testDifferentConstructors_NullParam3b() {
        new Domino(Tiles.P0H0_Val13, -1);
    }

    @Test(expected = AssertionError.class)
    public void testDifferentConstructors_NullParam3c() {
        new Domino(Tiles.P0H0_Val13, 4);
    }

    @Test(expected = AssertionError.class)
    public void testDifferentConstructors_NullParam4() {
        new Domino(null);
    }


    //  --- incRot ---
    /*
    Domino P0_H0_Val13
            fstPos  sndPos  fst  snd
    rot 0   0,0     1,0     P0   H0
    rot 1   0,0     0,1     P0   H0
    rot 2   0,0     1,0     H0   H0
    rot 3   0,0     0,1     H0   H0
    */

    @Test
    public void testIncRot0() {
        Domino dom = new Domino(Tiles.P0H0_Val13);
        assertEquals(0, dom.getRot());
        assertEquals(P0, dom.getFstVal());
        assertEquals(H0, dom.getSndVal());
        assertEquals(new Pos(1, 0), dom.getSndPos());
    }

    @Test
    public void testIncRot1() {
        Domino dom = new Domino(Tiles.P0H0_Val13);
        dom.incRot();
        assertEquals(1, dom.getRot());
        assertEquals(P0, dom.getFstVal());
        assertEquals(H0, dom.getSndVal());
        assertEquals(new Pos(0, 1), dom.getSndPos());
    }

    @Test
    public void testIncRot2() {
        Domino dom = new Domino(Tiles.P0H0_Val13);
        dom.incRot();
        dom.incRot();
        assertEquals(2, dom.getRot());
        assertEquals(H0, dom.getFstVal());
        assertEquals(P0, dom.getSndVal());
        assertEquals(new Pos(1, 0), dom.getSndPos());
    }

    @Test
    public void testIncRot3() {
        Domino dom = new Domino(Tiles.P0H0_Val13);
        dom.incRot();
        dom.incRot();
        dom.incRot();
        assertEquals(3, dom.getRot());
        assertEquals(H0, dom.getFstVal());
        assertEquals(P0, dom.getSndVal());
        assertEquals(new Pos(0, 1), dom.getSndPos());
    }

    @Test
    public void testIncRot4() {
        Domino dom = new Domino(Tiles.P0H0_Val13);
        dom.incRot();
        dom.incRot();
        dom.incRot();
        dom.incRot();
        assertEquals(0, dom.getRot());
        assertEquals(P0, dom.getFstVal());
        assertEquals(H0, dom.getSndVal());
        assertEquals(new Pos(1, 0), dom.getSndPos());
    }

    @Test
    public void testIncRot_NullPos() {
        Domino dom = new Domino(Tiles.P0H0_Val13, null);
        assertEquals(0, dom.getRot());
        dom.incRot();
        assertEquals(1, dom.getRot());
        assertEquals(P0, dom.getFstVal());
        assertEquals(H0, dom.getSndVal());
        assertNull(dom.getFstPos());
        assertNull(dom.getSndPos());
    }


    // --- sort list of dominos ---
    @Test
    public void testSortDomList_Valid() {
        List<Domino> overallStack = Domino.fill(new LinkedList<>());
        List<Domino> domInput = Arrays.asList(new Domino[]{
                overallStack.get(5),
                overallStack.get(1),
                overallStack.get(10),
                overallStack.get(6)}
        );
        Collections.sort(domInput);
        Domino[] expectedOutput = new Domino[] {
                overallStack.get(1),
                overallStack.get(5),
                overallStack.get(6),
                overallStack.get(10)
        };
        Domino[] actualOutput = domInput.toArray(new Domino[0]);
        assertArrayEquals(expectedOutput, actualOutput);
    }


    // --- getTileDistrictType ---
    @Test
    public void testGetTileDistrictType() {
        Domino dom = new Domino(Tiles.P0H0_Val13);
        assertEquals(DistrictType.PARK, dom.genTileDistrictType(0));
        assertEquals(DistrictType.HOME, dom.genTileDistrictType(1));
        assertNull(dom.genTileDistrictType(-1));
        assertNull(dom.genTileDistrictType(2));
    }


}
