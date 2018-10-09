package TestPackages.logic.playerState;

import TestPackages.other.FakeGUI;
import logic.dataPreservation.Logger;
import logic.playerTypes.DefaultAIPlayer;
import logic.playerState.Player;
import logic.playerState.Result;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void testGetWinner_OnlyOnePlayerHasPoints() {
        Player expWinner = new DefaultAIPlayer(new FakeGUI(), 0,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H0");
        Player[] players = new Player[] {expWinner, new DefaultAIPlayer(new FakeGUI(), 1, 3, 2),
                new DefaultAIPlayer(new FakeGUI(), 1, 3, 2)};
        Result res = new Result(players);
        assertNotNull(res.getWinner());
        assertEquals(1, res.getWinner().size());
        assertEquals(expWinner, res.getWinner().get(0));
    }


    @Test
    public void testGetWinner_OnePlayerWinnsAllBoardsAreFilled_CanDetermineWinner() {
        FakeGUI fakeGui = new FakeGUI();
        Player expWinner = new DefaultAIPlayer(fakeGui, 0,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H1");
        Player loser1 = new DefaultAIPlayer(fakeGui, 1,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H0");
        Player loser2 = new DefaultAIPlayer(fakeGui, 2,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H0");
        Result result = new Result(new Player[] {expWinner, loser1, loser2});
        assertEquals(expWinner, result.getWinner());
        assertSame(expWinner, result.getWinner());
    }

    @Test
    public void testGetWinner_OnePlayerWinnsAllBoardsAreFilled_OrderOfPlayersIrrelevant() {
        FakeGUI fakeGui = new FakeGUI();
        Player expWinner = new DefaultAIPlayer(fakeGui, 0,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H1");
        Player loser1 = new DefaultAIPlayer(fakeGui, 1,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H0");
        Player loser2 = new DefaultAIPlayer(fakeGui, 2,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H0");
        Player[] playersVersion1 = new Player[] {expWinner, loser1, loser2};
        Player[] playersVersion2 = new Player[] {loser1, expWinner, loser2};
        Player[] playersVersion3 = new Player[] {loser1, loser2, expWinner};
        Result res1 = new Result(playersVersion1);
        Result res2 = new Result(playersVersion2);
        Result res3 = new Result(playersVersion3);
        assertNotNull(res1);
        assertNotNull(res2);
        assertNotNull(res3);
        assertEquals(res1, res2);
        assertEquals(res1, res3);
        assertEquals(res2, res3);
    }

    @Test
    public void testGetWinner_TwoPlayersHaveSameNumOfPoints_OneActualWinner() {

    }

    @Test
    public void testGetWinner_TwoPlayersHaveSameNumOfPoints_BothWinners() {

    }

    @Test
    public void testGetWinner_ThreePlayersHaveEmptyBoards_AllWinners() {

    }

    @Test (expected = AssertionError.class)
    public void testGetWinner_NullParam() {
        new Result(null);
    }

}
