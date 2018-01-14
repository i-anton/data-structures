package seminar1.collections;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayStack<Item> implements IStack<Item> {

    private static final int DEFAULT_CAPACITY = 10;

    private Item[] elementData;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.elementData = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    public Item top(){
        if (size == 0) return null;
        return elementData[size-1];
    }
    @Override
    public void push(Item item) {
        if (elementData.length < size) grow();
        elementData[size] = item;
        size++;
    }

    @Override
    public Item pop() {
        if (size == 0) return null;
        if (size / elementData.length >= 4) shrink();
        return elementData[--size];
    }

    @Override
    public Iterator<Item> reversedIterator() {
        return new ReversedArrayStackIterator();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void grow() {
        /*
         * Если массив заполнился,
         * то увеличить его размер в полтора раз
         */
        changeCapacity(elementData.length+(elementData.length/2));
    }

    private void shrink() {
        /*
          Если количество элементов в четыре раза меньше,
          то уменьшить его размер в два раза
         */
        changeCapacity(elementData.length/2);
    }

    private void changeCapacity(int newCapacity) {
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    @Override
    public Iterator<Item> iterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<Item> {

        private int currentPosition = size;

        @Override
        public boolean hasNext() {
            return currentPosition != 0;
        }

        @Override
        public Item next() {
            return elementData[currentPosition--];
        }

    }
    private class ReversedArrayStackIterator implements Iterator<Item> {

        private int currentPosition = 0;

        @Override
        public boolean hasNext() {
            return currentPosition < size;
        }

        @Override
        public Item next() {
            return elementData[currentPosition++];
        }

    }
}
