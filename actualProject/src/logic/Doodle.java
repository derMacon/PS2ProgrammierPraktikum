package logic;

public class Doodle {

    public static void main(String[] args) {
//        String pattern = "<Spielfeld [^>]*>(?s).*";
        int playerCnt = 4;
        String pattern = "([0-" + (playerCnt - 1) + "] (?s)[^0-4]*).*\n";
//        String pattern = "[0-4].*";
        String input =
                "2 P0P0,1 P0P0,0 A1H0,3 I2P0\n";
        System.out.println(input.matches(pattern));
    }

}
