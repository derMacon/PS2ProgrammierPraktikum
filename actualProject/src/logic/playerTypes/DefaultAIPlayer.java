package logic.playerTypes;

import logic.bankSelection.Bank;
import logic.bankSelection.Choose;
import logic.bankSelection.Entry;
import logic.dataPreservation.Logger;
import logic.logicTransfer.GUIConnector;
import logic.logicTransfer.Game;
import logic.playerState.Board;
import logic.playerState.BotBehavior;
import logic.playerState.District;
import logic.playerState.Player;
import logic.token.Domino;
import logic.token.Pos;

import java.util.LinkedList;
import java.util.List;

/**
 * Default bot logic rules: TODO fill in the bots logic rules
 */
public class DefaultAIPlayer extends Player implements BotBehavior {

    /**
     * Prefix that will be displayed before each call of the printing Method of the Logger instance
     */
    public static final String NAME_PREFIX = "BOT";

       public DefaultAIPlayer(GUIConnector gui, int idx, int boardSizeX, int boardSizeY) {
        super(gui, idx, boardSizeX, boardSizeY);
    }

    public DefaultAIPlayer(GUIConnector gui, int idx, Board board) {
        super(gui, idx, board);
    }

    public DefaultAIPlayer(GUIConnector gui, int idx, String strBoard) {
        super(gui, idx, strBoard);
    }

    public String getName() {
        return NAME_PREFIX + (this.idxInPlayerArray + 1);
    }

    /**
     * Approach: - Find domino with highest number of possible points - If
     * multtiple dominos share the highest score the one with the most efficient
     * domino will be selected
     *
     * @param bank the bank that the player will select from
     * @param ordBank ordinal value of the bank
     * @return the edited bank
     */
    @Override
    public Bank selectFromBank(Bank bank, int ordBank, boolean displayOnGui) {
        if (null == bank || bank.isEmpty()) {
            return bank;
        }
        // bank copyWithoutSelection, serves as temporary bank
        Bank bankCopy = bank.copy();
        // Generate potentially best Choose for each possible domino on the given bank
        List<Choose> bestChoosesForEachPossibleBankSlot = new LinkedList<>();
        for (int i = 0; i < bank.getBankSize(); i++) {
            if (bank.isNotSelected(i)) {
                bestChoosesForEachPossibleBankSlot.add(genBestChoose(bankCopy.getDomino(i).copy(), i));
            }
        }
        // evaluate which choose is best
        Choose overallBestChoose;
        if (bestChoosesForEachPossibleBankSlot.isEmpty()) {
            // no possible dom on bank fits on board
            overallBestChoose = Choose.genLowOrderChoose(bank);
        } else {
            // at least one domino on the bank fits on the board
            overallBestChoose = Choose.max(bestChoosesForEachPossibleBankSlot, this.board);
        }
        // update domino (rotation and position) of the best choose
        bank.updateDomino(overallBestChoose.getIdxOnBank(), overallBestChoose.getDomWithPosAndRot());
        // actually select a domino from the bank
        bank.selectEntry(this, overallBestChoose.getIdxOnBank());
        Logger.getInstance().printAndSafe(String.format(Logger.selectionLoggerFormat,
                getName(), overallBestChoose.getDomWithPosAndRot().toString(),
                overallBestChoose.getIdxOnBank(), "next"));
        // put domino on board without showing it on the gui
        this.board.lay(overallBestChoose.getDomWithPosAndRot());
        // update gui
        if(displayOnGui) {
            this.gui.selectDomino(ordBank, overallBestChoose.getIdxOnBank(), this.idxInPlayerArray);
        }
        // return the bank, although bank reference is modified internally (just to make sure it is evident, pos and rot
        // modified)
        return bank;
    }

    @Override
    public void updateSelectedDom(Bank currBank) {
        // Construct a bank containing only the already selected Entry of the player -> now
        // method selectFromBank(...) can be used to find pos on board, etc.
        Domino dom = currBank.getPlayerSelectedDomino(this);
        Bank temp;
        if(null != dom) {
            temp = new Bank(new Entry[]{new Entry(dom)},
                    currBank.getRand());
            Domino tempDom =
                    selectFromBank(temp, Game.CURRENT_BANK_IDX, false).getPlayerSelectedDomino(this);

            dom.setPos(tempDom.getFstPos());
            dom.setRotation(tempDom.getRot());
        }

    }

    @Override
    public Bank doInitialSelect(Bank currBank, int bankOrd) {
        Bank output = selectFromBank(currBank, bankOrd, true);
        Domino playerSelectedDomino = currBank.getPlayerSelectedDomino(this);
        // update board -> has to be done to prevent the bot from laying the 
        // second draft directly on the first domino 
        this.board.lay(playerSelectedDomino);
        Logger.getInstance().printAndSafe(String.format(Logger.selectionLoggerFormat,
                getName(),
                playerSelectedDomino,
                currBank.getSelectedDominoIdx(this),
                "current") + System.lineSeparator()
        );

        // update districts
//        this.districts = updatedDistricts(this.districts, playerSelectedDomino);
        return output; 
    }

    @Override
    public void doStandardTurn(Bank currBank, Bank nextBank) {
        // nextBank reference will be modified
        selectFromBank(nextBank, Game.NEXT_BANK_IDX, true);
        Domino playersSelectedDomino = currBank.getPlayerSelectedDomino(this);
        this.gui.deleteDomFromBank(Game.CURRENT_BANK_IDX, currBank.getSelectedDominoIdx(this));

        showOnBoard(playersSelectedDomino);
    }

    // TODO call in standardturn
    @Override
    public void doLastTurn(Bank currBank) {
        Domino playersSelectedDomino = currBank.getPlayerSelectedDomino(this);
        this.gui.deleteDomFromBank(Game.CURRENT_BANK_IDX, currBank.getSelectedDominoIdx(this));
        showOnBoard(playersSelectedDomino);
    }


    /**
     * Generates the max. points available on the board for a given domino.
     * Iterates through board -> sets copyWithoutSelection on every pos -> gets
     * the board points -> find max
     *
     * @param domino domino to check
     * @return domino with a modified pos to match the most valuable spot on the
     * board. If the domino does not fit anywhere the pos will be set to null
     * and the points will be set to 0
     */
    private Choose genBestChoose(Domino domino, int bankSlotIndex) {
        Choose currChoose;
        Choose maxChoose = null;
        for (int y = 0; y < this.board.getSizeY(); y++) {
            for (int x = 0; x < this.board.getSizeX(); x++) {
                domino.setPos(new Pos(x, y));
                for (int i = 0; i < 4; i++) {
                    if (this.board.fits(domino)) {
                        currChoose = genChoose(domino, bankSlotIndex);
                        maxChoose = Choose.max(maxChoose, currChoose);
                    }
                    domino.incRot();
                }
            }
        }
        // TODO else branch, what happens if domino doesn't fit anywhere -> maybe disposed ???
        return null == maxChoose ? genChoose(domino.setPos(new Pos(0, 0)), bankSlotIndex) : maxChoose;
    }

    /**
     * @param domino
     * @return
     */
    private Choose genChoose(Domino domino, int bankSlotIndex) {
        List<District> updatedDeepCopy = updatedDistricts(this.districts, domino);
        return new Choose(domino.copy(), genDistrictPoints(updatedDeepCopy), bankSlotIndex);
    }

    @Override
    public Domino updateDominoPos(Domino domino) {
        // Pos and rot already determined when considering the option to choose
        return domino; // Method is needed for other Bot types...
    }

    private Domino trySpecificDomino(Domino domino) {
        return null;
    }

}
