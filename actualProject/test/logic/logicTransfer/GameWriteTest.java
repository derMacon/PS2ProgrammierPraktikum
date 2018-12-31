package logic.logicTransfer;

import logic.bankSelection.Bank;
import logic.bankSelection.Entry;
import logic.playerState.Player;
import logic.differentPlayerTypes.DefaultAIPlayer;
import logic.differentPlayerTypes.HumanPlayer;
import logic.token.Domino;
import logic.token.Tiles;
import org.junit.Test;
import other.FakeGUI;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class GameWriteTest {

    private boolean filesAreEqual(String nameFile1, String nameFile2) {
        String file1Content = new File(nameFile1).toString();
        String file2Content = new File(nameFile2).toString();
        return file1Content.equals(file2Content);
    }

    @Test
    public void testWriteStartOfGame() throws IOException {
        GUIConnector fakeGui = new FakeGUI();
        Player[] players = new Player[]{
                new HumanPlayer(fakeGui, 0, 5, 5),
                new DefaultAIPlayer(fakeGui, 1, 5, 5),
                new DefaultAIPlayer(fakeGui, 2, 5, 5),
                new DefaultAIPlayer(fakeGui, 3, 5, 5)
        };
        Bank currBank = new Bank(new Entry[]{
                new Entry(new Domino(Tiles.A0S1_Val37)),
                new Entry(new Domino(Tiles.P0O1_Val38)),
                new Entry(new Domino(Tiles.S0O1_Val39)),
                new Entry(new Domino(Tiles.I1P0_Val40)),
        }, new Random());
        Bank nextBank = new Bank(new Entry[]{
                new Entry(new Domino(Tiles.A1H0_Val32)),
                new Entry(new Domino(Tiles.A1H0_Val32)),
                new Entry(new Domino(Tiles.A1H0_Val32)),
                new Entry(new Domino(Tiles.P0S1_Val36)),
        }, new Random());

        Game game = new Game(new FakeGUI(), players, 0, currBank, nextBank, new LinkedList<Domino>(),
                new Domino(Tiles.A0S1_Val37));
        TestToolkit.writeAndAssert(game, "test1MinimalGame");
    }
}
