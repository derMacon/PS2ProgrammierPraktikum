package other;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Doodle {

    public static void main(String[] args) {
        List test = new LinkedList<String>();
        test.add("Apfel");
        test.add("Birne");
        test.add("Cola");

        Iterator<String> it = test.iterator();
        int count = 0;
        while(count < 6) {
            System.out.println(it.next());
            count++;
        }
    }
}
