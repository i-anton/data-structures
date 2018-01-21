package seminar1.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedStack<Item> implements IStack<Item> {
    private Node<Item> head;
    private int size;

    @Override
    public void push(Item item) {
        if (item == null) throw new NullPointerException();
        head = new Node<>(item,head);
        size++;
    }

    @Override
    public Item pop() {
        if (size <= 0) throw new NoSuchElementException();
        Item result = head.item;
        head = head.next;
        size--;
        return result;
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
        return new LinkedStackIterator();
    }

    private class LinkedStackIterator implements Iterator<Item> {
        private Node<Item> current = head;
        @Override
        public boolean hasNext() {
            return (current.next != null);
        }

        @Override
        public Item next() {
            if (hasNext()){
                Item res = current.item;
                current = current.next;
                return res;
            }
            return null;
        }
    }

    private static class Node<Item> {
        Item item;
        Node<Item> next;

        Node(Item item, Node<Item> next) {
            this.item = item;
            this.next = next;
        }
    }
}
