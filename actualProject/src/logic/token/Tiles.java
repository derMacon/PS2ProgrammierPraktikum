package logic.token;


import static logic.token.SingleTile.P0;
import static logic.token.SingleTile.H0;
import static logic.token.SingleTile.A0;
import static logic.token.SingleTile.S0;
import static logic.token.SingleTile.O0;
import static logic.token.SingleTile.P1;
import static logic.token.SingleTile.H1;
import static logic.token.SingleTile.I0;
import static logic.token.SingleTile.A1;
import static logic.token.SingleTile.S1;
import static logic.token.SingleTile.O1;
import static logic.token.SingleTile.I1;
import static logic.token.SingleTile.S2;
import static logic.token.SingleTile.O2;
import static logic.token.SingleTile.I2;
import static logic.token.SingleTile.I3;

/**
 * Enum for the different Tile combinations
 */
public enum Tiles {
    P0P0_Val1(P0, P0, 1),
    P0P0_Val2(P0, P0, 2),
    H0H0_Val3(H0, H0, 3),
    H0H0_Val4(H0, H0, 4),
    H0H0_Val5(H0, H0, 5),
    H0H0_Val6(H0, H0, 6),
    A0A0_Val7(A0, A0, 7),
    A0A0_Val8(A0, A0, 8),
    A0A0_Val9(A0, A0, 9),
    S0S0_Val10(S0, S0, 10),
    S0S0_Val11(S0, S0, 11),
    O0O0_Val12(O0, O0, 12),
    P0H0_Val13(P0, H0, 13),
    P0A0_Val14(P0, A0, 14),
    P0S0_Val15(P0, S0, 15),
    P0O0_Val16(P0, O0, 16),
    H0A0_Val17(H0, A0, 17),
    H0S0_Val18(H0, S0, 18),
    P1H0_Val19(P1, H0, 19),
    P1A0_Val20(P1, A0, 20),
    P1S0_Val21(P1, S0, 21),
    P1O0_Val22(P1, O0, 22),
    P1I0_Val23(P1, I0, 23),
    H1P0_Val24(H1, P0, 24),
    H1P0_Val25(H1, P0, 25),
    H1P0_Val26(H1, P0, 26),
    H1P0_Val27(H1, P0, 27),
    H1A0_Val28(H1, A0, 28),
    H1S0_Val29(H1, S0, 29),
    A1P0_Val30(A1, P0, 30),
    A1P0_Val31(A1, P0, 31),
    A1H0_Val32(A1, H0, 32),
    A1H0_Val33(A1, H0, 33),
    A1H0_Val34(A1, H0, 34),
    A1H0_Val35(A1, H0, 35),
    P0S1_Val36(P0, S1, 36),
    A0S1_Val37(A0, S1, 37),
    P0O1_Val38(P0, O1, 38),
    S0O1_Val39(S0, O1, 39),
    I1P0_Val40(I1, P0, 40),
    P0S2_Val41(P0, S2, 41),
    A0S2_Val42(A0, S2, 42),
    P0O2_Val43(P0, O2, 43),
    S0O2_Val44(S0, O2, 44),
    I2P0_Val45(I2, P0, 45),
    O0I2_Val46(O0, I2, 46),
    O0I2_Val47(O0, I2, 47),
    P0I3_Val48(P0, I3, 48);

    public static final int TILES_CNT = Tiles.values().length;

    private final SingleTile fst;
    private final SingleTile snd;

    private final int value;

    /**
     * Constructor setting the values of the tile combinations
     *
     * @param fst   first Tile of the combination
     * @param snd   second Tile of the combination
     * @param value value of the combination (used for sorting the stack)
     */
    Tiles(SingleTile fst, SingleTile snd, int value) {
        this.fst = fst;
        this.snd = snd;
        this.value = value;
    }

    /**
     * Finds the first Enum member with the same ordinal values as the input
     *
     * @param fstTileOrd ordinal value of the first tile
     * @param sndTileOrd ordinal value of the second tile
     * @return the first Enum member with the same ordinal values as the input, null if there is no
     * such tile.
     */
    public static Tiles genTile(int fstTileOrd, int sndTileOrd) {
        Tiles[] possibleTiles = Tiles.values();
        int counter = 0;
        Tiles output = null;
        Tiles temp;
        do {
            temp = possibleTiles[counter];
            if (temp.fst.ordinal() == fstTileOrd && temp.snd.ordinal() == sndTileOrd) {
                output = temp;
            }
            counter++;
        } while (null == output && counter < possibleTiles.length);
        return output;
    }

    /**
     * Finds the first Enum member with the same values as the input
     *
     * @param fstTile value of the first tile
     * @param sndTile value of the second tile
     * @return the first Enum member with the same values as the input
     */
    public static Tiles genTile(SingleTile fstTile, SingleTile sndTile) {
        assert null != fstTile && null != sndTile;
        return genTile(fstTile.ordinal(), sndTile.ordinal());
    }

    /**
     * Converts a given String to a tile
     *
     * @param input String to convert to tile
     * @return the enum member corresponding with this String, null if no tile matches
     */
    public static Tiles fromString(String input) {
        // TODO insert code
        Tiles output = null;
        Tiles[] possibleTiles = values();
        int i = 0;
        while (null == output && i < possibleTiles.length) {
            output = possibleTiles[i].toString().equals(input) ? possibleTiles[i] : null;
            i++;
        }
        return output;
    }

    /**
     * Checks if the given string matches any string representation of this enum
     * @param input input to check
     * @return true if the given string matches any string representation of this enum
     */
    public static boolean contains(String input) {
        return null != fromString(input);
    }

    /**
     * Getter for the first tile
     *
     * @return first Tile of the combination
     */
    public SingleTile getFst() {
        return this.fst;
    }

    /**
     * Getter for the second tile
     *
     * @return second Tile of the combination
     */
    public SingleTile getSnd() {
        return this.snd;
    }

    /**
     * Getter for the value of the tile
     *
     * @return value of the combination
     */
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        // takes first 4 letters of the enum name
        return this.name().substring(0, 4);
    }

}
