package seminar1.collections;

import java.util.Iterator;

public class LinkedQueue<Item> implements IQueue<Item> {

    // -> [tail -> .. -> .. -> head] ->
    private Node<Item> first;
    private Node<Item> last;
    private int size;

    @Override
    public void enqueue(Item item) {
        Node<Item> newLast = new Node<>(item);
        if (last != null) {
            last.next = newLast;
        }
        last = newLast;
        if (first == null) first = last;
        size++;
    }

    @Override
    public Item dequeue() {
        if (size <= 0) return null;
        Node<Item> oldFirst = first;
        first = first.next;
        if (first == null) last = null;
        size--;
        return oldFirst.item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedQueueIterator();
    }

    private class LinkedQueueIterator implements Iterator<Item> {
        Node<Item> curr = first;
        @Override
        public boolean hasNext() {
            return curr == null;
        }

        @Override
        public Item next() {
            Item old = curr.item;
            curr = curr.next;
            return old;
        }
    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;

        Node(Item item) {
            this.item = item;
        }
    }
}
