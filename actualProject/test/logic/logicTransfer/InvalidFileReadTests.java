package logic.logicTransfer;

import org.junit.Test;
import other.FakeGUI;

import static org.junit.Assert.assertEquals;

public class InvalidFileReadTests {


    // --- invalid tags ---

    @Test
    public void test_noTagOpenerAtBeginningOfDoc() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE + "Document does not start with <";
        String fileOutput = TestToolkit.readAsString("inv_noTagOpenerAtBeginningOfDoc");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_noTagCloseAtBeginningOfDoc() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_noTagCloseAtBeginningOfDoc");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    @Test
    public void test_multipleTagOpeners1() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagOpeners1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagOpeners2() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagOpeners2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagOpeners3() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagOpeners3");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagOpeners4() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagOpeners4");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagOpeners5() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagOpeners5");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagOpeners6() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagOpeners6");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    @Test
    public void test_multipleTagClosers1() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagClosers1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagClosers2() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagClosers2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagClosers3() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagClosers3");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagClosers4() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagClosers4");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagClosers5() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagClosers5");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_multipleTagClosers6() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_multipleTagClosers6");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_blankBeforeFstTag() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE + "Document does not start with <";
        String fileOutput = TestToolkit.readAsString("inv_blankBeforeFstTag");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    @Test
    public void test_blankAfterTag1() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_blankAfterTag1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_blankAfterTag2() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_blankAfterTag2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_blankAfterTag3() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_blankAfterTag3");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_blankAfterTag4() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_blankAfterTag4");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_blankAfterTag5() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_blankAfterTag5");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_blankAfterTag6() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_blankAfterTag6");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    @Test
    public void test_misspelledTag1() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledTag1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledTag2() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledTag2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledTag3() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledTag3");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledTag4() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledTag4");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledTag5() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledTag5");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledTag6() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledTag6");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    // --- Board content ---
    @Test
    public void test_misspelledBoardElem1() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledTag6");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


}
