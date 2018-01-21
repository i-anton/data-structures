package seminar1.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class ArrayPriorityQueue<Key extends Comparable<Key>> implements IPriorityQueue<Key> {
    private static final int DEFAULT_CAPACITY = 3;
    private Key[] elementData;
    private Comparator<Key> comparator;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayPriorityQueue() {
        elementData = (Key[]) new Comparable[DEFAULT_CAPACITY];
    }

    public ArrayPriorityQueue(Comparator<Key> comparator) {
        this();
        this.comparator = comparator;
    }

    @Override
    public void add(Key key) {
        /* O(log n) */
        if (key == null)
            throw new NullPointerException("Key shouldn't be null");
        ++size;
        if (size >= elementData.length) grow();
        elementData[size-1] = key;
        siftUp();
    }

    @Override
    public Key peek() {
        /*
         * O(1)
         * Посмотреть на минимальный элемент
         */
        if (size <= 0) throw new NoSuchElementException();
        return elementData[0];
    }

    @Override
    public Key extractMin() {
        /*
         * O(log n)
         * Достать минимальный элемент
         *  и перестроить кучу
         */
        if (size <= 0) throw new NoSuchElementException();
        Key min = elementData[0];
        elementData[0] = elementData[size - 1];
        --size;
        siftDown();
        if (size >= 3 && elementData.length/size >= 4) shrink();
        return min;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void siftUp() {
        /*
         * O(log n)
         * Просеивание вверх —
         *  подъём элемента больше родителей
         */
        int i = size-1;
        while (greater((i-1)/2,i)){
            swap(i,(i-1)/2);
            i = (i-1)/2;
        }
    }

    private void siftDown() {
        /*
         * O(log n)
         * Просеивание вниз
         *  спуск элемента меньше детей
         */
        int i = 0;
        while (2*i +1 < size){
            int left = 2*i +1;
            int right = 2*i +2;
            int j = left;
            if (right < size && greater(left,right)){
                j = right;
            }
            if (greater(j,i)){
                break;
            }
            swap(i,j);
            i=j;
        }
    }

    private void grow() {
        /*
         * Если массив заполнился,
         * то увеличить его размер в полтора раз
         */
        @SuppressWarnings("unchecked")
        Key[] newData = (Key[]) new Comparable[elementData.length*3/2+1];
        System.arraycopy(elementData, 0, newData, 0, elementData.length);
        elementData = newData;
    }

    private void shrink() {
        /*
         * Если количество элементов в четыре раза меньше,
         * то уменьшить его размер в два раза
         */
        @SuppressWarnings("unchecked")
        Key[] newData = (Key[]) new Comparable[elementData.length/2+1];
        System.arraycopy(elementData, 0, newData, 0, elementData.length/2);
        elementData = newData;
    }

    private boolean greater(int i, int j) {
        return comparator == null
                ? elementData[i].compareTo(elementData[j]) > 0
                : comparator.compare(elementData[i], elementData[j]) > 0
                ;
    }

    private void swap(int i, int j){
        Key temp = elementData[i];
        elementData[i] = elementData[j];
        elementData[j] = temp;
    }
    @Override
    public Iterator<Key> iterator() {
        return new PriorityQueueIterator();
    }
    class PriorityQueueIterator implements Iterator<Key>{
        int currPos;
        @Override
        public boolean hasNext() {
            return currPos < elementData.length && elementData[currPos] != null;
        }

        @Override
        public Key next() {
            Key toReturn = elementData[currPos];
            currPos++;
            while (currPos < elementData.length){
                if (elementData[currPos] != null) break;
                currPos++;
            }
            return toReturn;
        }
    }
}
