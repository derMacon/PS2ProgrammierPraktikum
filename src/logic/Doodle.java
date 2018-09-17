package logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Doodle {

    public static void main(String[] args) {
        String test = " a a  a \n a a a ";
        String[] temp = test.replaceAll(" ", "").split("\n");
        for(String str : temp) {
            System.out.println(str);
        }
    }
}
