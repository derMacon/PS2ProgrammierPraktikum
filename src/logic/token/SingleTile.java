package logic.token;

import static logic.token.DistrictType.*;

/**
 * Enum representing the whole label of a token / domino.
 */
public enum SingleTile {
    CITY_HALL(CENTER),
    A0(AMUSEMENT), A1(AMUSEMENT), A2(AMUSEMENT), A3(AMUSEMENT),
    I0(INDUSTRY), I1(INDUSTRY), I2(INDUSTRY), I3(INDUSTRY),
    O0(OFFICE), O1(OFFICE), O2(OFFICE), O3(OFFICE),
    P0(PARK), P1(PARK), P2(PARK), P3(PARK),
    S0(SHOPPING), S1(SHOPPING), S2(SHOPPING), S3(SHOPPING),
    H0(HOME), H1(HOME), H2(HOME), H3(HOME);

    /**
     * Each value has an overall district type. Used to check if a domino fits in a board.
     */
    private DistrictType districtType;

    /**
     * Constructor to set the district type of the values.
     * @param disctrictType
     */
    SingleTile(DistrictType disctrictType) {
        this.districtType = disctrictType;
    }

    /**
     * Getter for the overall district type.
     * @return
     */
    public DistrictType getDistrictType() {
        return this.districtType;
    }
}
