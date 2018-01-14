package seminar1.collections;

import java.util.Iterator;

public class LinkedStack<Item> implements IStack<Item> {
    private Node<Item> head;
    private int size;

    @Override
    public void push(Item item) {
        head = new Node<>(item,head);
        size++;
    }

    @Override
    public Item pop() {
        if (size <= 0) return null;
        Item result = head.item;
        head = head.next;
        size--;
        return result;
    }

    @Override
    public Iterator<Item> reversedIterator() {
        return null;
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

    private class ReversedLinkedStackIterator implements Iterator<Item> {
        private Node<Item> current = head;
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
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
