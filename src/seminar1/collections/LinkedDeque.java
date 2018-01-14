package seminar1.collections;

import java.util.Iterator;

public class LinkedDeque<Item> implements IDeque<Item> {

    private int size;
    private Node<Item> first;
    private Node<Item> last;
    @Override
    public void pushFront(Item item) {
        Node<Item> newFirst = new Node<>(item);
        if (first != null) {
            newFirst.next = first;
            first.previous = newFirst;
        }
        first = newFirst;
        if (last == null) last = first;
        size++;
    }

    @Override
    public void pushBack(Item item) {
        Node<Item> newLast = new Node<>(item);
        if (last != null) {
            newLast.previous = last;
            last.next = newLast;
        }
        last = newLast;
        if (first == null) first = last;
        size++;
    }

    @Override
    public Item popFront() {
        if (size <= 0) return null;
        Node<Item> oldFirst = first;
        first = first.next;
        if (first == null)
            last = null;
        else
            first.previous = null;
        size--;
        return oldFirst.item;
    }

    @Override
    public Item popBack() {
        if (size <= 0) return null;
        Node<Item> oldLast = last;
        last = oldLast.previous;
        if (last == null)
            first = null;
        else
            last.next = null;
        size--;
        return oldLast.item;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedDequeIterator();
    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> previous;
        Node(Item item) {
            this.item = item;
        }
    }

    private class LinkedDequeIterator implements Iterator<Item> {

        private Node<Item> currentPosition = first;

        @Override
        public boolean hasNext() {
            return first != null;
        }

        @Override
        public Item next() {
            Item old = first.item;
            currentPosition = currentPosition.next;
            return old;
        }

    }
}
