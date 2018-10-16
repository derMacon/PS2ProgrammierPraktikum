package logic.bankSelection;

import logic.token.Domino;

import java.util.List;

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
     * @param domWithPosAndRot Domino with a position and rotation
     * @param potentialPointsOnBoard sum of potential points for domino when layed at the right pos / rot
     */
    public Choose(Domino domWithPosAndRot, int potentialPointsOnBoard, int idxOnBank) {
        this.domWithPosAndRot = domWithPosAndRot;
        this.potentialPointsOnBoard = potentialPointsOnBoard;
        this.idxOnBank = idxOnBank;
    }

    /**
     * Getter for the domino
     * @return
     */
    public Domino getDomWithPosAndRot() {
        return this.domWithPosAndRot;
    }

    /**
     * Getter for the points
     * @return
     */
    public int getPotentialPointsOnBoard() {
        return this.potentialPointsOnBoard;
    }

    /**
     * Getter for the index of the domino on the bank
     * @return index of the domino on the bank
     */
    public int getIdxOnBank() {
        return this.idxOnBank;
    }

    /**
     * Compares the given chose objects and returns the object with the highest number of points
     * @param chooseDom chose objects that will be examined
     * @return the object with the highest number of points, null if list is empty
     */
    public static Choose max(List<Choose> chooseDom) {
        assert null != chooseDom;
        int maxPoints = INIT_VALUE_MAX_POINTS;
        Domino lestValue;
        Choose output = null;
        for(Choose currChoose : chooseDom) {
            // evaluates possible points
            if(currChoose.potentialPointsOnBoard > maxPoints) {
                   output = currChoose;
                   maxPoints = currChoose.potentialPointsOnBoard;
            }
        }
        // TODO situation for tie -> Very Important. Write tests
        return output;
    }

    // TODO write Javadoc when it's clear what this method actually does :)
    public static Choose max(Choose fstChoose, Choose sndChoose) {
        if(null == fstChoose) {
            return sndChoose;
        }
        if(null == sndChoose) {
            return fstChoose;
        }
        if(fstChoose.potentialPointsOnBoard >= sndChoose.potentialPointsOnBoard) {
            return fstChoose;
        } else {
            return sndChoose;
        }
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
