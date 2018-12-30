package logic.bankSelection;

import logic.playerState.Board;
import logic.playerState.District;
import logic.token.Domino;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that implements the selection of a domino a player has chosen.
 */
public class Choose {

    /**
     * Value with which the maxvalue local variable in the max method will be
     * initialized with
     */
    public static final int INIT_VALUE_MAX_POINTS = -1;

    /**
     * Domino with a position and rotation
     */
    private Domino domWithPosAndRot;

    /**
     * sum of potential points for domino when layed at the right pos / rot
     */
    private int potentialPointsOnBoard;

    /**
     * Index of the domino on the bank
     */
    private int idxOnBank;

    /**
     * Constructor setting both fields
     *
     * @param domWithPosAndRot       Domino with a position and rotation
     * @param potentialPointsOnBoard sum of potential points for domino when layed at the right pos
     *                               / rot
     * @param idxOnBank              index of the domino on the bank
     */
    public Choose(Domino domWithPosAndRot, int potentialPointsOnBoard, int idxOnBank) {
        this.domWithPosAndRot = domWithPosAndRot;
        this.potentialPointsOnBoard = potentialPointsOnBoard;
        this.idxOnBank = idxOnBank;
    }

    /**
     * Compares the given chose objects and returns the object with the highest number of points
     *
     * @param chooseDom chose objects that will be examined, objects may be null
     * @param board     board from which the most efficient chose will be generated from
     * @return the object with the highest number of points, null if list is empty
     */
//    public static Choose max(List<Choose> chooseDom, Board board) {
//        assert null != chooseDom && null != board;
//        if (chooseDom.isEmpty()) {
//            return null;
//        }
//        int maxPoints = INIT_VALUE_MAX_POINTS;
//        List<Choose> possibleOutput = new LinkedList<>();
//        for (Choose currChoose : chooseDom) {
//            if (null != currChoose.getDomWithPosAndRot().getFstPos()
//                    && currChoose.potentialPointsOnBoard >= maxPoints) {
//                // if new highscore -> output list reseted
//                if (currChoose.potentialPointsOnBoard > maxPoints) {
//                    possibleOutput = new LinkedList<>();
//                }
//                possibleOutput.add(currChoose);
//                maxPoints = currChoose.potentialPointsOnBoard;
//            }
//        }
//        // TODO situation for tie -> Very Important. Write tests
//        return possibleOutput.size() == 1 ? possibleOutput.get(0)
//                : mostEfficientChoose(possibleOutput, board);
//    }

//    public static Choose max(List<Choose> chooseDom, Player player) {
//        assert null != chooseDom && null != player;
//        if (chooseDom.isEmpty()) {
//            return null;
//        }
//        int maxPoints = INIT_VALUE_MAX_POINTS;
//        List<Choose> possibleOutput = new LinkedList<>();
//        for (Choose currChoose : chooseDom) {
//            if (null != currChoose.getDomWithPosAndRot().getFstPos()
//                    && currChoose.potentialPointsOnBoard >= maxPoints) {
//                // if new highscore -> output list reseted
//                if (currChoose.potentialPointsOnBoard > maxPoints) {
//                    possibleOutput = new LinkedList<>();
//                }
//                possibleOutput.add(currChoose);
//                maxPoints = currChoose.potentialPointsOnBoard;
//            }
//        }
//        // TODO situation for tie -> Very Important. Write tests
//        return possibleOutput.size() == 1 ? possibleOutput.get(0)
//                : genMostEfficientChoose(possibleOutput, board);
//    }


//    protected static Choose mostEfficientChoose(List<Choose> chooses, Board board) {
//        boolean isEfficient;
//        int i = 0;
//        Choose currChoose = null;
//        do {
//            currChoose = chooses.get(i);
//
//            // TODO delete before final commit
//            boolean debugfstPos = !board.isNextToDistrict(currChoose.domWithPosAndRot.getFstPos());
//            boolean debugSndPos = !board.isNextToDistrict(currChoose.domWithPosAndRot.getSndPos());
//
//
//            isEfficient = !board.isNextToDistrict(currChoose.domWithPosAndRot.getFstPos())
//                    && !board.isNextToDistrict(currChoose.domWithPosAndRot.getSndPos());
//            i++;
//        } while (!isEfficient && i < chooses.size());
//        return isEfficient ? currChoose : chooses.get(0);
//    }


    /**
     * Takes in an ordered List of possible Chose obj., checks for the first domino that fits on
     * a board and doesn't generate any spare single cells that cannot be used later on in the
     * game. If there is not such case, the firts list element will be returned. (Least fallout
     * -> can choose earlier on in the next round)
     *
     * @param input ordered List of possible Chose obj
     * @param board board to check the positions for
     * @return most efficient choose obj.
     */
    // protected for testing
//    protected static Choose genMostEfficientChoose(List<Choose> input, Board board) {
//        // TODO assert input is sorted, assert input dominos have the same value
//        assert null != input && null != board && input.size() > 1 && isSorted(input);
//        int i = 0;
//        boolean isEfficient;
//        Choose currChoose;
//        do {
//            currChoose = input.get(i);
//            isEfficient = board.isEfficient(currChoose);
//            i++;
//        } while (!isEfficient && i < input.size());
//        return isEfficient ? currChoose : input.get(0);
//    }


//    protected countEmptySingleCells(Choose input, Board board) {
//        int res = 0;
//        List<Pos> neighbors = input.domWithPosAndRot.getFstPos().getNeighbours();
//        neighbors.addAll(input.domWithPosAndRot.getSndPos().getNeighbours());
//
//        board.getD
//
//    }


    /**
     * Evaluates if a list of choose objects is sorted or not
     *
     * @param input list of choose object to be evaluated
     * @return true if list is sorted or if it's empty
     */
//    public static boolean isSorted(List<Choose> input) {
//        if(input == null) {
//            return false;
//        }
//        if (input.isEmpty()) {
//            return true;
//        }
//        int i = 0;
//        while (i < input.size() - 1
//                && input.get(i).getDomWithPosAndRot().compareTo(input.get(i + 1).getDomWithPosAndRot()) < 0) {
//            i++;
//        }
//        return i == input.size() - 1;
//    }

//    // TODO write Javadoc when it's clear what this method actually does :)
//    public static Choose max(Choose fstChoose, Choose sndChoose, List<District> districts) {
////        assert fstChoose.domWithPosAndRot.compareTo(sndChoose.domWithPosAndRot) <= 0;
//        if (null == fstChoose) {
//            return sndChoose;
//        }
//        if (null == sndChoose) {
//            return fstChoose;
//        }
//        if (fstChoose.potentialPointsOnBoard > sndChoose.potentialPointsOnBoard) {
//            return fstChoose;
//        } else if (fstChoose.potentialPointsOnBoard < sndChoose.potentialPointsOnBoard) {
//            return sndChoose;
//        } else {
//            // tie
//            if (isEfficient(fstChoose, board) || !isEfficient(sndChoose, board)) {
//                return fstChoose;
//            }
//            return sndChoose;
//        }
//    }

//    private static boolean isEfficient(Choose choose, Board board) {
//        //TODO delete
//        boolean debugfstPos = !board.isNextToDistrict(choose.domWithPosAndRot.getFstPos());
//        boolean debugSndPos = !board.isNextToDistrict(choose.domWithPosAndRot.getSndPos());
//
//        return !board.isNextToDistrict(choose.domWithPosAndRot.getFstPos())
//                && !board.isNextToDistrict(choose.domWithPosAndRot.getSndPos());
//    }

//
//    /**
//     * Generates a choose object with the lowest possible domino that is available on the given bank
//     *
//     * @param bank bank from which the lowest domino will be used to generate a new choose object
//     * @return choose object with the lowest possible domino that is available on the given bank
//     */
//    public static Choose genLowOrderChoose(Bank bank) {
//        int idxOnBank = 0;
//        Choose output = null;
//        Domino currDomino;
//        while (idxOnBank < bank.getBankSize() && null == output) {
//            currDomino = bank.getDomino(idxOnBank);
//            if (null != currDomino) {
//                output = new Choose(currDomino, 0, idxOnBank);
//            }
//            idxOnBank++;
//        }
//        return output;
//    }

    /**
     * Getter for the domino
     *
     * @return domino of the chose with its position and rotation
     */
    public Domino getDomWithPosAndRot() {
        return this.domWithPosAndRot;
    }

    /**
     * Getter for the points
     *
     * @return potential points on board
     */
    public int getPotentialPointsOnBoard() {
        return this.potentialPointsOnBoard;
    }

    /**
     * Getter for the index of the domino on the bank
     *
     * @return index of the domino on the bank
     */
    public int getIdxOnBank() {
        return this.idxOnBank;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Choose other = (Choose) obj;
        return this.domWithPosAndRot.equals(other.domWithPosAndRot)
                && this.idxOnBank == other.idxOnBank
                && this.potentialPointsOnBoard == other.potentialPointsOnBoard;
    }


}
