package logic.logicTransfer;

import logic.dataPreservation.Loader;
import org.junit.Test;
import other.FakeGUI;

import static org.junit.Assert.assertEquals;

public class InvalidFileReadTests {

    @Test
    public void test_blankAfterFstTag() {
        String expOutput = String.format(WrongTagException.DEFAULT_MESSAGE, "<Spielfeld> ");
        String fileOutput = TestToolkit.readAsString("inv_blankAfterFstTag");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

}
