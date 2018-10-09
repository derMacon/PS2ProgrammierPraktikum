package logic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Doodle {

    public static void main(String[] args) {
        List<String> list1 = new LinkedList<>();
        list1.add("Hello");
        list1.add(null);
        list1.removeAll(new LinkedList<String>());
        List<String> list2 = new LinkedList<>();
        list2.add("World");
        list2.add("Hello");

        System.out.println(Collections.disjoint(list1, list2));
    }
}
