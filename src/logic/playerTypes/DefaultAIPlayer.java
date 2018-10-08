package logic.playerTypes;

import logic.bankSelection.Bank;
import logic.logicTransfer.GUIConnector;
import logic.playerState.Board;
import logic.playerState.BotBehavior;
import logic.playerState.District;
import logic.playerState.Player;
import logic.token.Domino;
import logic.token.Pos;

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
    public int selectFromBank(Bank bank) {
        // TODO insert code
        int maxSlotIdx = 0;
        int maxBankVal = 0;
        for (int i = 0; i < bank.getBankSize(); i++) {
            if(bank.isNotSelected(i) && maxBankVal < potentialPointsOnCurrentBoard(bank.getDomino(i))) {
                maxSlotIdx = i;
            }
        }
        bank.selectEntry(this, maxSlotIdx);
        // TODO updating gui here, and not in game class
        return maxSlotIdx;
    }

    /**
     * Generates the max. points available on the board for a given domino.
     * Iterates through board -> sets copy on every pos -> gets the board points -> find max
     * @param domino
     * @return
     */
    private int potentialPointsOnCurrentBoard(final Domino domino) {
        int maxPoints = 0;
        int currPosPoints;
        for (int y = 0; y < this.board.getSizeY(); y++) {
            for (int x = 0; x < this.board.getSizeX(); x++) {
                currPosPoints = potentialPointsOnSpecificPos(domino, new Pos(x, y));
                if(currPosPoints > maxPoints) {
                    maxPoints = currPosPoints;
                }
            }
        }
        return maxPoints;
    }

    /**
     *
     * @param domino
     * @param pos
     * @return
     */
    private int potentialPointsOnSpecificPos(Domino domino, Pos pos) {
        domino.setPos(pos);
        List<District> updatedDeepCopy = updatedDistricts(this.districts, domino);
        return genDistrictPoints(updatedDeepCopy);
    }



    @Override
    public Domino updateDominoPos(Domino domino) {
        // use in selectFromBank
        // TODO insert code
        return null;
    }

    private Domino trySpecificDomino(Domino domino) {
        return null;
    }


}
