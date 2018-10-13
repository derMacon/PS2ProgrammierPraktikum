package logic.playerTypes;

import logic.bankSelection.Bank;
import logic.bankSelection.Choose;
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
 * Default bot logic rules:
 * TODO fill in the bots logic rules
 */
public class DefaultAIPlayer extends Player implements BotBehavior {

    public DefaultAIPlayer(GUIConnector gui, int idx, int boardSizeX, int boardSizeY) {
        super(gui, idx, boardSizeX, boardSizeY);
    }

    public DefaultAIPlayer(GUIConnector gui, int idx, Board board) {
        super(gui, idx, board);
    }

    public DefaultAIPlayer(GUIConnector gui, int idx, String strBoard) {
        super(gui, idx, strBoard);
    }

    @Override
    public Bank selectFromBank(Bank bank, int ordBank) {
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
        Choose overallBestChoose = Choose.max(bestChoosesForEachPossibleBankSlot);
        // update domino (rotation and position) of the best choose
        bank.updateDomino(overallBestChoose.getIdxOnBank(), overallBestChoose.getDomWithPosAndRot());
        // actually select a domino from the bank
        bank.selectEntry(this, overallBestChoose.getIdxOnBank());
        // update gui
        this.gui.selectDomino(ordBank, overallBestChoose.getIdxOnBank(), this.idxInPlayerArray);
        // return the bank, although bank reference is modified internally (just to make sure is is evident)
        return bank;
    }

    @Override
    public void doStandardTurn(Bank currBank, Bank nextBank) {
        Bank nexBank = selectFromBank(nextBank, Game.NEXT_BANK_IDX);
        Domino playersSelectedDomino = currBank.getPlayerSelectedDomino(this);
        this.gui.deleteDomFromBank(Game.CURRENT_BANK_IDX, currBank.getSelectedDominoIdx(this)); 
        showOnBoard(playersSelectedDomino);
    }

    /**
     * Generates the max. points available on the board for a given domino.
     * Iterates through board -> sets copyWithoutSelection on every pos -> gets the board points -> find max
     *
     * @param domino
     * @return
     */
    private Choose genBestChoose(Domino domino, int bankSlotIndex) {
        Choose currChoose;
        Choose maxChoose = null;
        for (int y = 0; y < this.board.getSizeY(); y++) {
            for (int x = 0; x < this.board.getSizeX(); x++) {
                domino.setPos(new Pos(x, y));
                if (this.board.fits(domino)) {
                    currChoose = genChoose(domino, bankSlotIndex);
                    maxChoose = Choose.max(maxChoose, currChoose);
                }
                // TODO else branch, what happens if domino doesn't fit anywhere
            }
        }
        return maxChoose;
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
        return domino; // Method is needed for other Bot types...
    }

    private Domino trySpecificDomino(Domino domino) {
        return null;
    }


}
