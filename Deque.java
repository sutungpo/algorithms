/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int qty;
    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return qty == 0;
    }

    // return the number of items on the deque
    public int size() {
        return qty;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node(item);
        first.next = oldfirst;
        if (last == null) {
            last = first;
        }
        else {
            oldfirst.prev = first;
        }
        qty++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldlast = last;
        last = new Node(item);
        last.prev = oldlast;
        if (first == null) {
            first = last;
        }
        else {
            oldlast.next = last;
        }
        qty++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node oldfirst = first;
        first = first.next;
        if (first != null)
            first.prev = null;
        else
            last = null;
        qty--;
        return oldfirst.it;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node oldlast = last;
        last = last.prev;
        if (last != null)
            last.next = null;
        else
            first = null;
        qty--;
        return oldlast.it;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
        Deque<String> deque = new Deque<String>();
        System.out.println(deque.size());
        deque.addFirst("haha");
        deque.addFirst("hehe");
        deque.addFirst("heihei");
        deque.addFirst("hiahia");
        deque.addFirst("houhou");
        System.out.println(deque.size());
        deque.removeFirst();
        System.out.println(deque.size());
        deque.removeLast();
        System.out.println(deque.size());
        Iterator<String> i = deque.iterator();
        while (i.hasNext()) System.out.println(i.next());
    }

    private class DequeIterator implements Iterator<Item> {

        private Node index = first;

        public boolean hasNext() {
            return index != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node tmp = index;
            index = index.next;
            return tmp.it;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private class Node {
        private final Item it;
        private Node next;
        private Node prev;
        public Node(Item item) {
            it = item;
        }
    }
}
