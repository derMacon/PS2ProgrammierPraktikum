package logic.token;


import static logic.token.SingleTile.*;

public enum Tiles {
    P0_P0_Val1   (P0, P0, 1),
    P0_P0_Val2   (P0, P0, 2),
    H0_H0_Val3   (H0, H0, 3),
    H0_H0_Val4   (H0, H0, 4),
    H0_H0_Val5   (H0, H0, 5),
    H0_H0_Val6   (H0, H0, 6),
    A0_A0_Val7   (A0, A0, 7),
    A0_A0_Val8   (A0, A0, 8),
    A0_A0_Val9   (A0, A0, 9),
    S0_S0_Val10   (S0, S0, 10),
    S0_S0_Val11   (S0, S0, 11),
    O0_O0_Val12   (O0, O0, 12),
    P0_H0_Val13   (P0, H0, 13),
    P0_A0_Val14   (P0,A0, 14),
    P0_S0_Val15   (P0,S0, 15),
    P0_O0_Val16   (P0,O0, 16),
    H0_A0_Val17   (H0,A0, 17),
    H0_S0_Val18   (H0,S0, 18),
    P1_H0_Val19   (P1,H0, 19),
    P1_A0_Val20   (P1, A0, 20),
    P1_S0_Val21   (P1, S0, 21),
    P1_O0_Val22   (P1, O0, 22),
    P1_I0_Val23   (P1, I0, 23),
    H1_P0_Val24   (H1, P0, 24),
    H1_P0_Val25   (H1, P0, 25),
    H1_P0_Val26   (H1, P0, 26),
    H1_P0_Val27   (H1, P0, 27),
    H1_A0_Val28   (H1, A0, 28),
    H1_S0_Val29   (H1, S0, 29),
    A1_P0_Val30   (A1, P0, 30),
    A1_P0_Val31   (A1, P0, 31),
    A1_H0_Val32   (A1, H0, 32),
    A1_H0_Val33   (A1, H0, 33),
    A1_H0_Val34   (A1, H0, 34),
    A1_H0_Val35   (A1, H0, 35),
    P0_S1_Val36   (P0, S1, 36),
    A0_S1_Val37   (A0, S1, 37),
    P0_O1_Val38   (P0, O1, 38),
    S0_O1_Val39   (S0, O1, 39),
    I1_P0_Val40   (I1, P0, 40),
    P0_S2_Val41   (P0, S2, 41),
    A0_S2_Val42   (A0, S2, 42),
    P0_O2_Val43   (P0, O2, 43),
    S0_O2_Val44   (S0, O2, 44),
    I2_P0_Val45   (I2, P0, 45),
    O0_I2_Val46   (O0, I2, 46),
    O0_I2_Val47   (O0, I2, 47),
    P0_I3_Val48   (P0, I3, 48);

    public static final int TILES_CNT = 48;

    private final SingleTile fst;
    private final SingleTile snd;

    private final int value;

    Tiles(SingleTile fst, SingleTile snd, int value) {
        this.fst = fst;
        this.snd = snd;
        this.value = value;
    }

    public SingleTile getFst() {
        return this.fst;
    }

    public SingleTile getSnd() {
        return this.snd;
    }

    public int getValue() {
        return this.value;
    }

}