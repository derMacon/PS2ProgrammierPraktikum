package TestPackages.logic.playerState;

import TestPackages.other.FakeGUI;
import logic.playerTypes.DefaultAIPlayer;
import logic.playerState.Player;
import logic.playerState.Result;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void testGetWinner_OnlyOnePlayerHasPoints() {
        FakeGUI fakeGui = new FakeGUI();
        Player expWinner = new DefaultAIPlayer(fakeGui,
                "e e e\n" +
                            "e 0 1\n" +
                            "e e 1\n");
        Player[] players = new Player[] {expWinner, new DefaultAIPlayer(fakeGui, 3, 2),
                new DefaultAIPlayer(fakeGui, 3, 2)};
        Result res = new Result(players);

        assertEquals(1, res.getWinner().size());
        assertEquals(expWinner, res.getWinner().get(0));
    }


    @Test
    public void testGetWinner_OnePlayerWinnsAllBoardsAreFilled_CanDetermineWinner() {
        FakeGUI fakeGui = new FakeGUI();
        Player expWinner = new DefaultAIPlayer(fakeGui,
                "e e e\n" +
                        "e 0 1\n" +
                        "e e 1\n");
        Player loser1 = new DefaultAIPlayer(fakeGui,
                "e e e\n" +
                        "e 0 1\n" +
                        "e e e\n");
        Player loser2 = new DefaultAIPlayer(fakeGui,
                "e e e\n" +
                        "e 0 1\n" +
                        "e e e\n");
        Result result = new Result(new Player[] {expWinner, loser1, loser2});
        assertEquals(expWinner, result.getWinner());
        assertSame(expWinner, result.getWinner());
    }

    @Test
    public void testGetWinner_OnePlayerWinnsAllBoardsAreFilled_OrderOfPlayersIrrelevant() {
        FakeGUI fakeGui = new FakeGUI();
        Player expWinner = new DefaultAIPlayer(fakeGui,
                "e e e\n" +
                        "e 0 1\n" +
                        "e e 1\n");
        Player loser1 = new DefaultAIPlayer(fakeGui,
                "e e e\n" +
                        "e 0 1\n" +
                        "e e e\n");
        Player loser2 = new DefaultAIPlayer(fakeGui,
                "e e e\n" +
                        "e 0 1\n" +
                        "e e e\n");
        Player[] playersVersion1 = new Player[] {expWinner, loser1, loser2};
        Player[] playersVersion2 = new Player[] {loser1, expWinner, loser2};
        Player[] playersVersion3 = new Player[] {loser1, loser2, expWinner};
        assertEquals(new Result(playersVersion1), new Result(playersVersion2));
        assertEquals(new Result(playersVersion1), new Result(playersVersion3));
        assertEquals(new Result(playersVersion2), new Result(playersVersion3));
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
