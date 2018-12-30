package logic.bankSelection;

import logic.token.Domino;

/**
 * Class that implements the selection of a domino a player has chosen.
 */
public class Choose {

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
