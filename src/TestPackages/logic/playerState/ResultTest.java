package TestPackages.logic.playerState;

import TestPackages.other.FakeGUI;
import logic.playerState.DefaultAIPlayer;
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


}
