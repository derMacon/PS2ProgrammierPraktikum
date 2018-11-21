package logic.logicTransfer;

import static org.junit.Assert.*;

import logic.dataPreservation.Loader;
import other.FakeGUI;
import logic.bankSelection.Bank;
import logic.dataPreservation.Logger;
import logic.logicTransfer.Game;
import logic.token.Domino;
import logic.token.SingleTile;
import logic.token.Tiles;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class GameConstructorFileIOTest {

    @Test
    public void testFileInputConstructor() {
        Game game = new Game(new FakeGUI(), Loader.getInstance().openGivenFile(new File("fileTests/validFiles/testBoardAfterFirstMove.txt")));
        List<Domino> stack = game.getStack();
        Board
    }
}
