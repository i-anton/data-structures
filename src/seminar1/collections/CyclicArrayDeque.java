package seminar1.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CyclicArrayDeque<Item> implements IDeque<Item> {
    private static final int DEFAULT_CAPACITY = 10;
    private Item[] elementData;
    private int size;
    private int rear;
    private int front;

    @SuppressWarnings("unchecked")
    public CyclicArrayDeque() {
        this.elementData = (Item[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void pushFront(Item item) {
        if (item == null) throw new NullPointerException();
        if (elementData.length <= size) grow();
        front = (front - 1)%elementData.length;
        if (front == -1) front = elementData.length-1;
        elementData[front] = item;
        size++;
    }

    @Override
    public void pushBack(Item item) {
        if (item == null) throw new NullPointerException();
        if (elementData.length <= size) grow();
        elementData[rear] = item;
        rear = (rear + 1)%elementData.length;
        size++;
    }

    @Override
    public Item popFront() {
        if (size == 0) throw new NoSuchElementException();
        if (elementData.length/size >= 4) shrink();
        Item toReturn = elementData[front];
        elementData[front]= null;
        front = (front+1)% elementData.length;
        size--;
        return toReturn;
    }

    @Override
    public Item popBack() {
        if (size == 0) throw new NoSuchElementException();
        if (elementData.length/size >= 4) shrink();
        rear = (rear-1)% elementData.length;
        if (rear == -1) rear = elementData.length-1;
        Item toReturn = elementData[rear];
        elementData[rear]= null;
        size--;
        return toReturn;
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
        int newCapacity = elementData.length+(elementData.length/2);
        @SuppressWarnings("unchecked")
        Item[] newArray = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i]=elementData[(front+i)%elementData.length];
        }
        front = 0;
        rear = size;
        elementData = newArray;
    }

    private void shrink() {
        /*
         * Если количество элементов в четыре раза меньше,
         * то уменьшить его размер в два раза
         */
        int newCapacity = elementData.length/2;
        @SuppressWarnings("unchecked")
        Item[] newArray = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i]=elementData[(front+i)%elementData.length];
        }
        front = 0;
        rear = size;
        elementData = newArray;
    }

    @Override
    public Iterator<Item> iterator() {
        return new CyclicArrayDequeIterator();
    }
    class CyclicArrayDequeIterator implements Iterator<Item>{
        int count;
        int currPos = front;
        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public Item next() {
            Item toReturn = elementData[currPos];
            currPos = (currPos+1)%elementData.length;
            count++;
            return toReturn;
        }
    }
}
