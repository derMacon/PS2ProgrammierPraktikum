package logic;


import static logic.SingleTile.*;

public enum Tiles {
    P0_P0   (P0, P0, new int[] {1,2}),
    H0_H0   (H0, H0, new int[] {3,4,5,6}),
    A0_A0   (A0, A0, new int[] {7,8,9}),
    S0_S0   (S0, S0, new int[] {10, 11}),
    O0_O0   (O0, O0, new int[] {12}),
    P0_H0   (P0, H0, new int[] {13}),
    P0_A0   (P0,A0, new int[] {14}),
    P0_S0   (P0,S0, new int[] {15}),
    P0_O0   (P0,O0, new int[] {16}),
    H0_A0   (H0,A0, new int[] {17}),
    H0_S0   (H0,S0, new int[] {18}),
    P1_H0   (P1,H0, new int[] {19}),
    P1_A0   (P1, A0, new int[] {20}),
    P1_S0   (P1, S0, new int[] {21}),
    P1_O0   (P1, O0, new int[] {22}),
    P1_I0   (P1, I0, new int[] {23}),
    H1_P0   (H1, P0, new int[] {24, 25, 26, 27}),
    H1_A0   (H1, A0, new int[] {28}),
    H1_S0   (H1, S0, new int[] {29}),
    A1_P0   (A1, P0, new int[] {30, 31}),
    A1_H0   (A1, H0, new int[] {32, 33, 34, 35}),
    P0_S1   (P0, S1, new int[] {36}),
    A0_S1   (A0, S1, new int[] {37}),
    P0_O1   (P0, O1, new int[] {38}),
    S0_O1   (S0, O1, new int[] {39}),
    I1_P0   (I1, P0, new int[] {40}),
    P0_S2   (P0, S2, new int[] {41}),
    A0_S2   (A0, S2, new int[] {42}),
    P0_O2   (P0, O2, new int[] {43}),
    S0_O2   (S0, O2, new int[] {44}),
    I2_P0   (I2, P0, new int[] {45}),
    O0_I2   (O0, I2, new int[] {46, 47}),
    P0_I3   (P0, I3, new int[] {48});

    public static final int HIGHEST_VALUE = 48;

    private final SingleTile fst;
    private final SingleTile snd;

    private final int[] values;

    Tiles(SingleTile fst, SingleTile snd, int[] values) {
        this.fst = fst;
        this.snd = snd;
        this.values = values;
    }

    public int getFst() {
        return fst.ordinal();
    }

    public int getSnd() {
        return snd.ordinal();
    }

    public int[] getValues() {
        return this.values;
    }

}