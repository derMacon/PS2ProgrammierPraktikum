package logic.playerState;

import logic.differentPlayerTypes.DefaultAIPlayer;
import org.junit.Test;
import other.FakeGUI;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResultTest {

    // TODO test equals method

    @Test
    public void testGetWinner_OnlyOnePlayerHasPoints() {
        Player expWinner = new DefaultAIPlayer(new FakeGUI(), 0,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H0");
        Player[] players = new Player[]{expWinner, new DefaultAIPlayer(new FakeGUI(), 1, 3, 2),
                new DefaultAIPlayer(new FakeGUI(), 2, 3, 2)};
        List<ResultRanking> expectedRankedList = Arrays.asList(
                new ResultRanking(1, new Player[]{players[0]}),
                new ResultRanking(2, new Player[]{players[1], players[2]})
        );
        List<ResultRanking> actualRankedList = new Result(players).getRankedList();
        assertEquals(expectedRankedList.get(0), actualRankedList.get(0));
        assertEquals(expectedRankedList.get(1), actualRankedList.get(1));
    }


    @Test
    public void testGetWinner_OnePlayerWinnsAllBoardsAreFilled_CanDetermineWinner() {
        FakeGUI fakeGui = new FakeGUI();
        Player expWinner = new DefaultAIPlayer(fakeGui, 0,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- H1 H1");
        Player loser1 = new DefaultAIPlayer(fakeGui, 1,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- H0 H0");
        Player loser2 = new DefaultAIPlayer(fakeGui, 2,
                "-- -- --\n" +
                        "-- CC P1\n" +
                        "-- -- H0");
        List<ResultRanking> expectedRankedList = Arrays.asList(
                new ResultRanking(1, new Player[]{expWinner}),
                new ResultRanking(2, new Player[]{loser1}),
                new ResultRanking(3, new Player[]{loser2})
        );
        List<ResultRanking> actualRankedList = new Result(new Player[]{expWinner,
                loser1, loser2}).getRankedList();
        assertEquals(expectedRankedList.get(0), actualRankedList.get(0));
        assertEquals(expectedRankedList.get(1), actualRankedList.get(1));
        assertEquals(expectedRankedList.get(2), actualRankedList.get(2));
        assertEquals(expectedRankedList, actualRankedList);
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
        Player[] playersVersion1 = new Player[]{expWinner, loser1, loser2};
        Player[] playersVersion2 = new Player[]{loser1, expWinner, loser2};
        Player[] playersVersion3 = new Player[]{loser1, loser2, expWinner};
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


}
