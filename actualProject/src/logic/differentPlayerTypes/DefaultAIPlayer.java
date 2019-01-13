package logic.differentPlayerTypes;

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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Default bot logic player, implements the most basic AI.
 */
public class DefaultAIPlayer extends Player implements BotBehavior {

    /**
     * Prefix that will be displayed before each call of the printing Method of the Logger instance
     */
    public static final String NAME_PREFIX = "BOT";

    /**
     * Constructor used in the normal / standard game
     *
     * @param gui        gui reference to display changes made by the player
     * @param idx        index of the player in the player array in the game class
     * @param boardSizeX width of the board
     * @param boardSizeY height of the board
     */
    public DefaultAIPlayer(GUIConnector gui, int idx, int boardSizeX, int boardSizeY) {
        super(gui, idx, boardSizeX, boardSizeY);
    }

    /**
     * Constructor used for special test cases
     *
     * @param gui   gui reference to display changes made by the player
     * @param idx   index of the player in the player array in the game class
     * @param board board reference of the player
     */
    public DefaultAIPlayer(GUIConnector gui, int idx, Board board) {
        super(gui, idx, board);
    }

    /**
     * Constructor used for the default test cases since the call of the super class constructor with a String
     * representation of the board automatically generates the districts for the board without any further method
     * calls required.
     *
     * @param gui      gui reference to display changes made by the player
     * @param idx      index of the player in the player array in the game class
     * @param strBoard String representation of the players board
     */
    public DefaultAIPlayer(GUIConnector gui, int idx, String strBoard) {
        super(gui, idx, strBoard);
    }

    /**
     * Getter for the name (e.g. HUMAN, BOT1, etc.)
     *
     * @return The String representation of the player's name.
     */
    public String getName() {
        return NAME_PREFIX + (this.idxInPlayerArray + 1);
    }

    /**
     * Approach: - Find domino with highest number of possible points - If
     * multtiple dominos share the highest score the one with the most efficient
     * domino will be selected
     * todo javadoc woanders hin (interface)
     * @param bank    the bank that the player will select from
     * @param ordBank ordinal value of the bank
     * @return the edited bank
     */
    @Override
    public Bank selectFromBank(Bank bank, int ordBank, boolean displayOnGui) {
        if (null == bank || bank.isEmpty()) {
            return bank;
        }
        Bank bankCopy = bank.copy();
        List<Choose> bestChoosesForEachPossibleBankSlot = bestChooseForEachDom(bankCopy);
        Choose overallBestChoose = bestOverallChoose(bankCopy, bestChoosesForEachPossibleBankSlot);
        doSelect(bank, overallBestChoose);
        updateBoard(ordBank, displayOnGui, overallBestChoose);

        // log selection
        String roundIdentifier = ordBank == 0 ? "current" : "next";
        Logger.getInstance().printAndSafe("\n" + String.format(Logger.SELECTION_LOGGER_FORMAT,
                getName(), overallBestChoose.getDomWithPosAndRot().toString(),
                overallBestChoose.getIdxOnBank(), roundIdentifier));

        // return the bank, although bank reference is modified internally
        // (just to make sure it is evident, pos and rot modified)
        return bank;
    }

    /**
     * Updates the board with the selected domino
     * @param ordBank ordinal value of the bank
     * @param displayOnGui flag determining if the selection should be displayed on the gui or not
     * @param overallBestChoose choose object containing the domino that should be put on the board
     */
    private void updateBoard(int ordBank, boolean displayOnGui, Choose overallBestChoose) {
        // put domino on board without showing it on the gui
        this.board.lay(overallBestChoose.getDomWithPosAndRot());
        // update gui
        if (displayOnGui) {
            this.gui.selectDomino(ordBank, overallBestChoose.getIdxOnBank(), this.idxInPlayerArray);
        }
    }

    /**
     * updating / selecting the domino on the actual bank
     * @param bank bank to select domino from
     * @param overallBestChoose choose containing the best fitting domino
     */
    private void doSelect(Bank bank, Choose overallBestChoose) {
        // update domino (rotation and position) of the best choose
        bank.updateDomino(overallBestChoose.getIdxOnBank(), overallBestChoose.getDomWithPosAndRot());
        // actually select a domino from the bank
        bank.selectEntry(this, overallBestChoose.getIdxOnBank());
    }

    /**
     * Generates the best overall choose from the list of choose objects
     * @param bank bank to choose lowest domino from if imput list is empty
     * @param bestChoosesForEachPossibleBankSlot list of best chooses for each bank slot
     * @return the overall best choose from the given list
     */
    private Choose bestOverallChoose(Bank bank, List<Choose> bestChoosesForEachPossibleBankSlot) {
        Choose overallBestChoose;
        if (bestChoosesForEachPossibleBankSlot.isEmpty()) {
            // no possible dom on bank fits on board
            overallBestChoose = genLowOrderChoose(bank);
        } else {
            // at least one domino on the bank fits on the board
            overallBestChoose = Collections.max(bestChoosesForEachPossibleBankSlot);
        }
        return overallBestChoose;
    }

    /**
     * Generate potentially best Choose for each possible domino on the given bank
     * @param bank bank to choose from
     * @return list of best chooses for the dominos which have a fitting position on the board
     */
    private List<Choose> bestChooseForEachDom(Bank bank) {
        List<Choose> bestChoosesForEachPossibleBankSlot = new LinkedList<>();
        for (int i = 0; i < bank.getBankSize(); i++) {
            if (bank.isNotSelected(i)) {
                bestChoosesForEachPossibleBankSlot.add(
                        genBestChoose(bank.getDomino(i).copy(), i));
            }
        }
        return bestChoosesForEachPossibleBankSlot;
    }

    @Override
    public void updateSelectedDom(Bank currBank) {
        if (null != currBank) {
            // Construct a bank containing only the already selected Entry of the player -> now
            // method selectFromBank(...) can be used to find pos on board, etc.
            Domino dom = currBank.getPlayerSelectedDomino(this);
            Bank temp;
            if (null != dom) {
                temp = new Bank(new Entry[]{new Entry(dom)},
                        currBank.getRand());
                Domino tempDom = selectFromBank(temp, Game.CURRENT_BANK_IDX, false).
                        getPlayerSelectedDomino(this);

                dom.setPos(tempDom.getFstPos());
                dom.setRotation(tempDom.getRot());
            }
        }
    }

    @Override
    public Bank doInitialSelect(Bank currBank, int bankOrd) {
        Bank output = selectFromBank(currBank, bankOrd, true);
        Domino playerSelectedDomino = currBank.getPlayerSelectedDomino(this);
        // update board -> has to be done to prevent the bot from laying the
        // second draft directly on the first domino
        this.board.lay(playerSelectedDomino);
        return output;
    }

    @Override
    public void doStandardTurn(Bank currBank, Bank nextBank) {
        // nextBank reference will be modified
        selectFromBank(nextBank, Game.NEXT_BANK_IDX, true);
        Domino playersSelectedDomino = currBank.getPlayerSelectedDomino(this);
        this.gui.deleteDomFromBank(Game.CURRENT_BANK_IDX,
                currBank.getSelectedDominoIdx(this));
        showOnBoard(playersSelectedDomino);
    }

    @Override
    public void doLastTurn(Bank currBank) {
        Domino playersSelectedDomino = currBank.getPlayerSelectedDomino(this);
        this.gui.deleteDomFromBank(Game.CURRENT_BANK_IDX, currBank.getSelectedDominoIdx(this));
        showOnBoard(playersSelectedDomino);
    }


    /**
     * Generates the mostEfficient. points available on the board for a given domino.
     * Iterates through board -> sets copyWithoutSelection on every pos -> gets
     * the board points -> find mostEfficient
     *
     * @param domino        domino to check
     * @param bankSlotIndex index of the bank slot from which the domino was taken, needed to generate a valid Choose
     *                      object
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
                for (int i = 0; i < Board.Direction.values().length; i++) {
                    if (this.board.fits(domino)) {
                        currChoose = genChoose(domino, bankSlotIndex);
                        maxChoose = mostEfficient(maxChoose, currChoose);
                    }
                    domino.incRot();
                }
            }
        }
//        return null == maxChoose ? genChoose(domino.setPos(new Pos(0, 0)), bankSlotIndex) : maxChoose;
        return null == maxChoose ? genChoose(domino.setPos(Domino.DEFAULT_POS), bankSlotIndex) : maxChoose;
    }

    /**
     * Returns the Choose Object with the most possible points on the board (ignoring the single cell districts it
     * might create, this must only be dealt with when finding the position of a domino)
     *
     * @param input input list to evaluate
     * @return the Choose Object with the most possible points on the board (ignoring the single cell districts it
     * might create, this must only be dealt with when finding the position of a domino)
     * @pre !input.isEmpty()
     */
    private Choose max(List<Choose> input) {
        assert !input.isEmpty();
        Choose out = input.get(0);
        Choose curr = null;
        for (int i = 1; i < input.size(); i++) {
            curr = input.get(i);
            if (out.getPotentialPointsOnBoard() < curr.getPotentialPointsOnBoard()) {
                out = curr;
            }
        }
        return out;
    }

    /**
     * Return the Choose object with the most possible points on the board. At a tie the one with the least single
     * tile districts will be returned.
     *
     * @param fstChoose first choose object to evaluate
     * @param sndChoose second choose object to evaluate
     * @return the Choose object with the most possible points on the board. At a tie the one with the least single
     * tile districts will be returned.
     */
    private Choose mostEfficient(Choose fstChoose, Choose sndChoose) {
        Choose res;
        if (null == fstChoose) {
            res = sndChoose;
        } else if (null == sndChoose) {
            res = fstChoose;
        } else if (fstChoose.getPotentialPointsOnBoard() > sndChoose.getPotentialPointsOnBoard()) {
            res = fstChoose;
        } else if (fstChoose.getPotentialPointsOnBoard() < sndChoose.getPotentialPointsOnBoard()) {
            res = sndChoose;
        } else {
            // tie
//            int fstSingleCellCount = countSingleCellDistricts(fstChoose);
//            int sndSingleCellCount = countSingleCellDistricts(sndChoose);

            int fstSingleCellCount = countSingleCellsOnBoard(fstChoose);
            int sndSingleCellCount = countSingleCellsOnBoard(sndChoose);

            if (fstSingleCellCount < sndSingleCellCount) {
                res = fstChoose;
            } else if (fstSingleCellCount > sndSingleCellCount) {
                res = sndChoose;
            } else {
                res = fstChoose.getDomWithPosAndRot().compareTo(sndChoose.getDomWithPosAndRot()) <= 0 ? fstChoose
                        : sndChoose;
            }
        }
        return res;
    }


    /**
     * Zaehlt die Einzelzellen eines Boards
     * @param choose gegebenes Choose Objekt wird in die Berechnung mit einbezogen, da der gespeicherte Domino
     *               testweise auf dem Spielfeld platziert wird.
     * @return Anzahl der Einzelzellen.
     * @pre null != choose
     */
    private int countSingleCellsOnBoard(Choose choose) {
        assert null != choose;
        int singleCellCnt = 0;
        for (int y = 0; y < this.board.getSizeY(); y++) {
            for (int x = 0; x < this.board.getSizeX(); x++) {
                if(board.isEmptySingleCell(new Pos(x,y))) {
                    singleCellCnt++;
                }
            }
        }
        return singleCellCnt;
    }




    /**
     * Counts how many single tile districts there are on the board after the given choose object was put in the
     * corresponding district. Only temporary, the districts of the player will not be effected since the Method
     * updateDistricts(...) internally manages a deepcopy of the given reference.
     *
     * @param choose choose object containing the domino with which the counting process will be computed
     * @return number of single tile districts after the choose object was added to the district representation
     * @pre null != choose
     */
    private int countSingleCellDistricts(Choose choose) {
        assert null != choose;
        List<District> temp = updateDistricts(this.districts, choose.getDomWithPosAndRot());
        int out = 0;
        for (District currDistrict : temp) {
            if (currDistrict.getTilePositions().size() == 1) {
                out++;
            }
        }
        return out;
    }


    /**
     * Generates a choose object with the lowest possible domino that is available on the given bank
     *
     * @param bank bank from which the lowest domino will be used to generate a new choose object
     * @return choose object with the lowest possible domino that is available on the given bank
     * @post at least one domino on the bank has to be available
     */
    private Choose genLowOrderChoose(Bank bank) {
        int idxOnBank = 0;
        Choose output = null;
        Domino currDomino;
        while (idxOnBank < bank.getBankSize() && null == output) {
            currDomino = bank.getDomino(idxOnBank);
//            currDomino.setPos(null);
            if (null != currDomino) {
                output = new Choose(currDomino, 0, idxOnBank);
            }
            idxOnBank++;
        }
        assert null != output;
        return output;
    }

    /**
     * Generates a choose object from the given domino with its bank slot index
     *
     * @param domino domino to generate the choose object with
     * @param bankSlotIndex index of the domino on the bank from which it was taken
     * @return new choose object containing the given domino with its bank slot index and the potential number of
     * points on the field
     */
    private Choose genChoose(Domino domino, int bankSlotIndex) {
        List<District> updatedDeepCopy = updateDistricts(this.districts, domino);
        return new Choose(domino.copy(), genDistrictPoints(updatedDeepCopy), bankSlotIndex);
    }

}
