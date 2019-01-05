package logic;

public class Doodle {

    public static void main(String[] args) {
//        String pattern = "<Spielfeld [^>]*>(?s).*";
        String pattern = "(<Spielfeld[^>]*>(?s)[^<>]*)*<Bänke>(?s)[^<>]*<Beutel>[^<>]*";
        String input = "<Spielfeld 1>\n" +
                "-- -- -- -- --\n" +
                "-- -- H1 P0 --\n" +
                "-- -- CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "<Spielfeld>\n" +
                "-- -- -- -- --\n" +
                "-- -- H1 P0 P1\n" +
                "-- -- CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "<Spielfeld>\n" +
                "-- -- -- P1 --\n" +
                "-- -- H1 P0 --\n" +
                "-- -- CC -- --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "<Spielfeld>\n" +
                "-- -- -- -- --\n" +
                "-- -- H1 P0 --\n" +
                "-- -- CC P1 --\n" +
                "-- -- -- -- --\n" +
                "-- -- -- -- --\n" +
                "<Bänke>\n" +
                "0 H1P0,2 P0O1,3 I1P0\n" +
                "- P0P0,- A0A0,1 H0A0,- P1H0<Beutel>\n" +
                "P0P0,P0P0,A1H0,I2P0";
        System.out.println(input.matches(pattern));
    }

}
