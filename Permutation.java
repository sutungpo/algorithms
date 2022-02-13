/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1)
            return;
        int num = Integer.parseInt(args[0]);
        RandomizedQueue<String> raqu = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            raqu.enqueue(str);
        }
        Iterator<String> it = raqu.iterator();
        while (it.hasNext() && num > 0) {
            System.out.println(it.next());
            num--;
        }
    }
}
