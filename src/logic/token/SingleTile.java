package logic.token;

import static logic.token.DistrictType.*;

/**
 * Enum representing the whole label of a token / domino.
 */
public enum SingleTile {
    CC(CENTER, 0), EC(EMPTY_CELL, 0),
    A0(AMUSEMENT, 0), A1(AMUSEMENT, 1), A2(AMUSEMENT, 2), A3(AMUSEMENT, 3),
    I0(INDUSTRY, 0), I1(INDUSTRY, 1), I2(INDUSTRY, 2), I3(INDUSTRY, 3),
    O0(OFFICE, 0), O1(OFFICE, 1), O2(OFFICE, 2), O3(OFFICE, 3),
    P0(PARK, 0), P1(PARK, 1), P2(PARK, 2), P3(PARK, 3),
    S0(SHOPPING, 0), S1(SHOPPING, 1), S2(SHOPPING, 2), S3(SHOPPING, 3),
    H0(HOME, 0), H1(HOME, 1), H2(HOME, 2), H3(HOME, 3);

    /**
     * Each value has an overall district type. Used to check if a domino fits in a board.
     */
    private DistrictType districtType;

    /**
     * Number of tokens on this singletile
     */
    private int tokenCnt;


    /**
     * Constructor to set the district type of the values.
     *
     * @param disctrictType
     */
    SingleTile(DistrictType disctrictType, int tokenCnt) {
        this.districtType = disctrictType;
        this.tokenCnt = tokenCnt;
    }

    /**
     * Getter for the overall district type.
     *
     * @return
     */
    public DistrictType getDistrictType() {
        return this.districtType;
    }

    /**
     * Getter for the number of tokens this singletile is valid
     * @return the number of tokens this singletile is valid
     */
    public int getTokenCnt() {
        return this.tokenCnt;
    }
}