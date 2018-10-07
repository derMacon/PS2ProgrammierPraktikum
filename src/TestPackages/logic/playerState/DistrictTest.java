package TestPackages.logic.playerState;

import logic.playerState.District;
import logic.token.Pos;
import logic.token.SingleTile;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class DistrictTest {

    // --- constructor ---

    @Test (expected = AssertionError.class)
    public void testConstructor_NullParam1() {
        new District(null, new Pos(0,0));
    }

    @Test (expected = AssertionError.class)
    public void testConstructor_NullParam2() {
        new District(SingleTile.S0, null);
    }

    @Test (expected = AssertionError.class)
    public void testConstructor_NullParam3() {
        List<SingleTile> list = new LinkedList<>();
        list.add(SingleTile.S0);
        new District(list, null);
    }

    @Test (expected = AssertionError.class)
    public void testConstructor_NullParam4() {
        List<Pos> list = new LinkedList<>();
        list.add(new Pos(0,0));
        new District(null, list);
    }

    @Test
    public void testConstructor_ValidConstr1() {
        SingleTile inputTile = SingleTile.A0;
        Pos inputPos = new Pos(0,0);
        District district = new District(inputTile, inputPos);

        List<SingleTile> expOutputTile = new LinkedList<>();
        expOutputTile.add(inputTile);
        assertEquals(expOutputTile, district.getSingleTiles());

        List<Pos> expOutputPos = new LinkedList<>();
        expOutputPos.add(inputPos);
        assertEquals(expOutputPos, district.getTilePositions());
    }

    @Test
    public void testConstructor_ValidConstr2() {
        List<SingleTile> expOutputTile = new LinkedList<>();
        expOutputTile.add(SingleTile.H0);
        expOutputTile.add(SingleTile.A0);
        List<Pos> expOutputPos = new LinkedList<>();
        Pos inputPos = new Pos(0,0);
        District district = new District(expOutputTile, expOutputPos);
        assertEquals(expOutputTile, district.getSingleTiles());
        assertEquals(expOutputPos, district.getTilePositions());
    }


    // --- add ---

    @Test (expected = AssertionError.class)
    public void testAdd_NullParam1() {
        District district = new District(SingleTile.A0, new Pos(0,0));
        district.add(null, new Pos(0,1));
    }

    @Test (expected = AssertionError.class)
    public void testAdd_NullParam2() {
        District district = new District(SingleTile.A0, new Pos(0,0));
        district.add(SingleTile.S0, null);
    }

    @Test (expected = AssertionError.class)
    public void testAdd_Valid() {
        // expected output
        List<SingleTile> districtTiles = new LinkedList<>();
        districtTiles.add(SingleTile.A0);
        districtTiles.add(SingleTile.S0);
        List<Pos> districtPos = new LinkedList<>();
        districtPos.add(new Pos(0,0));
        districtPos.add(new Pos(0,1));
        District expectedOutput = new District(districtTiles, districtPos);
        // actual output
        District actualOutput = new District(SingleTile.A0, new Pos(0,0));
        actualOutput.add(SingleTile.S0, new Pos(1,0));
        assertEquals(expectedOutput, actualOutput);
    }

    @Test (expected = AssertionError.class)
    public void testAdd_Invalid_PosHaveToBeDifferent() {
        List<SingleTile> tilesList = new LinkedList<>();
        tilesList.add(SingleTile.A0);
        tilesList.add(SingleTile.H0);
        List<Pos> posList = new LinkedList<>();
        posList.add(new Pos(0,0));
        posList.add(new Pos(0,1));
        District district = new District(tilesList, posList);
        district.add(SingleTile.H1, new Pos(0, 1));
    }

    // --- merge ---
    @Test (expected = AssertionError.class)
    public void testMerge_NullParam() {
        District district1 = new District(SingleTile.A0, new Pos(0,0));
        district1.merge(null);
    }

    @Test
    public void testMerge_ValidOnlyConstr() {
        // expected output
        List<SingleTile> districtTiles = new LinkedList<>();
        districtTiles.add(SingleTile.A0);
        districtTiles.add(SingleTile.S0);
        List<Pos> districtPos = new LinkedList<>();
        districtPos.add(new Pos(0,0));
        districtPos.add(new Pos(0,1));
        District expectedOutput = new District(districtTiles, districtPos);

        // actual output
        District district1 = new District(SingleTile.A0, new Pos(0,0));
        District district2 = new District(SingleTile.S0, new Pos(0,1));

        // testing
        District actualOutput = district1.merge(district2);
        assertEquals(districtTiles, actualOutput.getSingleTiles());
        assertEquals(districtPos, actualOutput.getTilePositions());
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMerge_Valid_FirstDistrictAfterConstr_SecondAfterAdding() {
        // expected output
        List<SingleTile> districtTiles = new LinkedList<>();
        districtTiles.add(SingleTile.A0);
        districtTiles.add(SingleTile.S0);
        districtTiles.add(SingleTile.H0);
        List<Pos> districtPos = new LinkedList<>();
        districtPos.add(new Pos(0,0));
        districtPos.add(new Pos(0,1));
        districtPos.add(new Pos(0,2));
        District expectedOutput = new District(districtTiles, districtPos);

        // actual output
        District district1 = new District(SingleTile.A0, new Pos(0,0));
        district1.add(SingleTile.S0, new Pos(0,1));
        District district2 = new District(SingleTile.H0, new Pos(0,2));
        District actualOutput = district1.merge(district2);

        // testing
        assertEquals(districtTiles, actualOutput.getSingleTiles());
        assertEquals(districtPos, actualOutput.getTilePositions());
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMerge_Valid_SecondAfterAdding_FirstDistrictAfterConstr() {
        // expected output
        List<SingleTile> districtTiles = new LinkedList<>();
        districtTiles.add(SingleTile.H0);
        districtTiles.add(SingleTile.A0);
        districtTiles.add(SingleTile.S0);
        List<Pos> districtPos = new LinkedList<>();
        districtPos.add(new Pos(0,2));
        districtPos.add(new Pos(0,0));
        districtPos.add(new Pos(0,1));
        District expectedOutput = new District(districtTiles, districtPos);

        // actual output
        District district1 = new District(SingleTile.H0, new Pos(0,2));
        District district2 = new District(SingleTile.A0, new Pos(0,0));
        district2.add(SingleTile.S0, new Pos(0,1));
        District actualOutput = district1.merge(district2);

        // testing
        assertEquals(districtTiles, actualOutput.getSingleTiles());
        assertEquals(districtPos, actualOutput.getTilePositions());
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMerge_Valid_BothAfterAdding() {
        // expected output
        List<SingleTile> districtTiles = new LinkedList<>();
        districtTiles.add(SingleTile.A0);
        districtTiles.add(SingleTile.S0);
        districtTiles.add(SingleTile.A1);
        districtTiles.add(SingleTile.S1);
        List<Pos> districtPos = new LinkedList<>();
        districtPos.add(new Pos(0,0));
        districtPos.add(new Pos(0,1));
        districtPos.add(new Pos(0,2));
        districtPos.add(new Pos(0,3));
        District expectedOutput = new District(districtTiles, districtPos);

        // actual output
        District district1 = new District(SingleTile.A0, new Pos(0,0));
        district1.add(SingleTile.S0, new Pos(0,1));
        District district2 = new District(SingleTile.A1, new Pos(0,2));
        district2.add(SingleTile.S1, new Pos(0, 3));
        District actualOutput = district1.merge(district2);

        // testing
        assertEquals(districtTiles, actualOutput.getSingleTiles());
        assertEquals(districtPos, actualOutput.getTilePositions());
        assertEquals(expectedOutput, actualOutput);
    }

    @Test (expected = AssertionError.class)
    public void testMerge_Invalid_DistrictsMustHoldDifferentElements() {
        // actual output
        District district1 = new District(SingleTile.A0, new Pos(0,0));
        district1.add(SingleTile.S0, new Pos(0,1));
        District district2 = new District(SingleTile.A1, new Pos(0,2));
        district2.add(SingleTile.S1, new Pos(0, 1));
        District actualOutput = district1.merge(district2);
    }


    // --- equals ---
    @Test (expected = AssertionError.class)
    public void testEquals_NullParam() {
        District district = new District(SingleTile.A2, new Pos(1,1));
        district.equals(null);
    }

    @Test
    public void testEquals_Invalid() {
        District district1 = new District(SingleTile.A0, new Pos(0,0));
        District district2 = new District(SingleTile.S0, new Pos(0,1));
        assertFalse(district1.equals(district2));
        assertFalse(district2.equals(district1));
    }

    @Test
    public void testEquals_Valid() {
        District district1 = new District(SingleTile.A0, new Pos(0,0));
        District district2 = new District(SingleTile.A0, new Pos(0,0));
        assertTrue(district1.equals(district2));
        assertTrue(district2.equals(district1));
    }


    // --- genPoints ---
    @Test
    public void testGenPoints_NoPoints() {
        int expectedOutput = 0;
        District district = new District(SingleTile.A0, new Pos(0,0));
        int actualOutput = district.genPoints();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGenPoints_WithAddingNewField_OnlyConstrWithPoints() {
        int expectedOutput = 4;
        District district = new District(SingleTile.A2, new Pos(0,0));
        district.add(SingleTile.A0, new Pos(0, 1));
        int actualOutput = district.genPoints();
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGenPoints_WithAddingNewField_ConstrWithPointsAddingExtraPoints() {
        int expectedOutput = 8;
        District district = new District(SingleTile.A2, new Pos(0,0));
        district.add(SingleTile.A2, new Pos(0, 1));
        int actualOutput = district.genPoints();
        assertEquals(expectedOutput, actualOutput);
    }


    // --- isNextTo ---
    @Test (expected = AssertionError.class)
    public void testIsNextTo_NullParam1() {
        District district = new District(SingleTile.A2, new Pos(0,0));
        district.typeAndPosMatchCurrDistrict(null, new Pos(0,1));
    }

    @Test (expected = AssertionError.class)
    public void testIsNextTo_NullParam2() {
        District district = new District(SingleTile.A2, new Pos(0,0));
        district.typeAndPosMatchCurrDistrict(SingleTile.A3, null);
    }

    @Test (expected = AssertionError.class)
    public void testIsNextTo_NullParam3() {
        District district = new District(SingleTile.A2, new Pos(0,0));
        district.typeAndPosMatchCurrDistrict(null, null);
    }

    @Test
    public void testIsNextTo_Valid_AllDirections_MatchingTypes() {
        District district = new District(SingleTile.A2, new Pos(1,1));
        assertTrue(district.typeAndPosMatchCurrDistrict(SingleTile.A0, new Pos(0,1))); // Left
        assertTrue(district.typeAndPosMatchCurrDistrict(SingleTile.A0, new Pos(2,1))); // Right
        assertTrue(district.typeAndPosMatchCurrDistrict(SingleTile.A0, new Pos(1,0))); // Up
        assertTrue(district.typeAndPosMatchCurrDistrict(SingleTile.A0, new Pos(1,2))); // Down
    }

    @Test
    public void testIsNextTo_Valid_AllDirections_NotMatchingTypes() {
        District district = new District(SingleTile.A2, new Pos(1,1));
        assertFalse(district.typeAndPosMatchCurrDistrict(SingleTile.P0, new Pos(0,1))); // Left
        assertFalse(district.typeAndPosMatchCurrDistrict(SingleTile.P0, new Pos(2,1))); // Right
        assertFalse(district.typeAndPosMatchCurrDistrict(SingleTile.P0, new Pos(1,0))); // Up
        assertFalse(district.typeAndPosMatchCurrDistrict(SingleTile.P0, new Pos(1,2))); // Down
    }

}
