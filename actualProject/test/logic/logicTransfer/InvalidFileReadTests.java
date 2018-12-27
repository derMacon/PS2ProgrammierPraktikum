package logic.logicTransfer;

import org.junit.Test;
import other.FakeGUI;

import static org.junit.Assert.assertEquals;

public class InvalidFileReadTests {


    // --- invalid tags ---

    @Test
    public void test_noTagOpenerAtBeginningOfDoc() {
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
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
        String expOutput = WrongTagException.DEFAULT_MESSAGE;
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
        String fileOutput = TestToolkit.readAsString("inv_misspelledBoardElem1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledBoardElem2() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledBoardElem2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledBoardElem3() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledBoardElem3");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledBoardElem4() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledBoardElem4");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_misspelledBoardElem5() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_misspelledBoardElem5");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    @Test
    public void test_wrongBoardDimensions1() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBoardDimensions1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBoardDimensions2() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBoardDimensions2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBoardDimensions3() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBoardDimensions3");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBoardDimensions4() {
        String expOutput = WrongBoardSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBoardDimensions4");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    // --- bank ---

    @Test
    public void test_wrongBank_blankAtBeginning() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_blankAtBeginning");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_blankAtEnd() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_blankAtEnd");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_commaAtBeginning() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_commaAtBeginning");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_commaAtEnd() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_blankAtEnd");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_tooManyBlanks() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_tooManyBlanks");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_tooFewBlanks1() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_tooFewBlanks1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_tooFewBlanks2() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_tooFewBlanks2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_onlyOneBank() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_onlyOneBank");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_negativePlayerNum() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_negativePlayerNum");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_tooLargePlayerNum() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_tooLargePlayerNum");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_onePlayerHasNotSelectedAnything() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_onePlayerHasNotSelectedAnything");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_nextBankSmallerThenCurrBank() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_nextBankSmallerThenCurrBank");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_bothBankTooSmall() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_bothBankTooSmall");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_oneBankTooLarge() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_oneBankTooLarge");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_letterAsPlayerNum() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_letterAsPlayerNum");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongBank_thirdBankInArray() {
        String expOutput = WrongBankSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongBank_thirdBankInArray");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }


    // --- Stack ---

    @Test
    public void test_wrongStack_blankAtBeginning() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_blankAtBeginning");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_blankAtEnd() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_blankAtEnd");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_commaAtBeginning() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_commaAtBeginning");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_commaAtEnd() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_commaAtEnd");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_blankAfterElem1() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_blankAfterElem1");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_blankAfterElem2() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_blankAfterElem2");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_blankBeforeElem() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_blankBeforeElem");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_EmptyElem() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_EmptyElem");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_OneSingletile() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_OneSingletile");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

    @Test
    public void test_wrongStack_cntNotDividableByPlayerCnt() {
        String expOutput = WrongStackSyntaxException.DEFAULT_MESSAGE;
        String fileOutput = TestToolkit.readAsString("inv_wrongStack_cntNotDividableByPlayerCnt");
        String actOutput = new Converter().readStr(new FakeGUI(), fileOutput);
        assertEquals(expOutput, actOutput);
    }

}
