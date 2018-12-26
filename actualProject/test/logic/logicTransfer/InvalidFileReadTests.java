package logic.logicTransfer;

import org.junit.Test;
import other.FakeGUI;

import static org.junit.Assert.assertEquals;

public class InvalidFileReadTests {

    @Test
    public void test_blankAfterFstTag() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE + "Spielfeld 1> ";
        String fileOutput = TestToolkit.readAsString("inv_blankAfterFstTag");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

}
