package logic.logicTransfer;

import static org.junit.Assert.*;

import logic.dataPreservation.Loader;
import logic.playerState.Board;
import logic.playerState.Player;
import logic.playerTypes.HumanPlayer;
import org.junit.Assert;
import other.FakeGUI;
import logic.bankSelection.Bank;
import logic.dataPreservation.Logger;
import logic.logicTransfer.Game;
import logic.token.Domino;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GameConstructorFileIOTest {

    @Test
    public void testFileInputConstructor() {
        FakeGUI fakeGui = new FakeGUI();
        String fileInput = Loader.openGivenFile(new File("test1.txt"));
        Game game = new Game(fakeGui, fileInput);

        // Boards
        Board expBoard1 = new Board(
                "EC EC EC EC EC\n" +
                "EC EC EC EC EC\n" +
                "EC EC CC P0 S2\n" +
                "EC EC I2 P0 EC\n" +
                "EC EC EC EC EC\n");
        Board expBoard2 = new Board(
            "EC EC P0 EC EC\n" +
                    "EC EC I3 EC EC\n" +
                    "A0 S1 CC EC EC\n" +
                    "EC EC EC EC EC\n" +
                    "EC EC EC EC EC\n");
        Board expBoard3 = new Board(
                "EC EC O0 EC EC\n" +
                "EC EC I2 EC EC\n" +
                "A0 S2 CC EC EC\n" +
                "EC EC EC EC EC\n" +
                "EC EC EC EC EC\n");
        Board expBoard4 = new Board(
                "P0 O1 O0 EC EC\n" +
                "EC EC I2 EC EC\n" +
                "EC EC CC EC EC\n" +
                "EC EC EC EC EC\n" +
                "EC EC EC EC EC\n");
        assertEquals(expBoard1, game.getPlayers()[0].getBoard());
        assertEquals(expBoard2, game.getPlayers()[1].getBoard());
        assertEquals(expBoard3, game.getPlayers()[2].getBoard());
        assertEquals(expBoard4, game.getPlayers()[3].getBoard());

        // banks
        Player p1 = new HumanPlayer(fakeGui, 0, expBoard1);
        Player p2 = new HumanPlayer(fakeGui, 1, expBoard2);
        Player p3 = new HumanPlayer(fakeGui, 2, expBoard3);
        Player p4 = new HumanPlayer(fakeGui, 3, expBoard4);
        List<Player> playerArr = Arrays.asList(new Player[] {p1, p2, p3, p4});
//        1 A0S1,3 P0O1,0 S0O1,2 I1P0
//        3 A1H0,- A1H0,- A1H0,1 P0S1
        Bank expCurrentBank = new Bank("1 A0S1,3 P0O1,0 S0O1,2 I1P0", playerArr);
        Bank actCurrentBank = game.getCurrentRoundBank();
        assertEquals(expCurrentBank, game.getCurrentRoundBank());

        // Stack
        List<Domino> expStack = Arrays.asList( new Domino[] {
                new Domino(Tiles.fromString("P0P0")),
                new Domino(Tiles.fromString("P0P0"))
        });
        List<Domino> stack = game.getStack();
        Assert.assertEquals(expStack, game.getStack());
    }
}
