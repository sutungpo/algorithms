/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int num;

    //@SuppressWarnings("unchecked")
    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    //@SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < num; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return num == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return num;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (num == arr.length)
            resize(2 * num);
        arr[num] = item;
        num++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (num < arr.length / 4) {
            resize(arr.length / 2);
        }
        int n = StdRandom.uniform(num);
        Item temp = arr[n];
        arr[n] = arr[num - 1];
        arr[num - 1] = null;
        num--;
        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int n = StdRandom.uniform(num);
        return arr[n];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("a");
        rq.enqueue("b");
        rq.enqueue("c");
        rq.enqueue("d");
        rq.enqueue("e");
        rq.enqueue("f");
        rq.enqueue("g");
        rq.dequeue();
        Iterator<String> iter1 = rq.iterator();
        Iterator<String> iter2 = rq.iterator();
        while (iter1.hasNext()) {
                    System.out.print(iter1.next() + ",");
                }
        System.out.println();
        while (iter2.hasNext()) {
                    System.out.print(iter2.next() + ",");
                }
        System.out.println();
    }

    private class QueueIterator implements Iterator<Item> {

        private final int[] arrshf;
        private int n;

        public QueueIterator() {
            arrshf = new int[num];
            for (int i = 0; i < num; i++) {
                arrshf[i] = i;
            }
            StdRandom.shuffle(arrshf);
        }

        public boolean hasNext() {
            return n < num;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item temp = arr[arrshf[n]];
            n++;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
