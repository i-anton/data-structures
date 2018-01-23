package seminar1.iterators;

import seminar1.collections.ArrayPriorityQueue;

import java.util.Iterator;

/**
 * Итератор возвращающий последовательность из N возрастающих итераторов в порядке возрастания
 * <p>
 * first = 1,3,4,5,7
 * second = 0,2,4,6,8
 * result = 0,1,2,3,4,4,5,6,7,8
 * <p>
 * Time = O(n + k * log n),
 * n — количество итераторов
 * k — суммарное количество элементов
 */
public class MergingPeekingIncreasingIterator implements Iterator<Integer> {

    private ArrayPriorityQueue<IPeekingIterator<Integer>> iterators;
    /**
     * Конструктор итератора возвращающего возрастающую последовательность
     *
     * @param peekingIterator — массив из возрастающих итераторов с возможностью узнать следующий элемент
     * @throws NullPointerException если массив равен null
     */
    @SafeVarargs
    public MergingPeekingIncreasingIterator(IPeekingIterator<Integer>... peekingIterator) {
        if (peekingIterator == null) throw new NullPointerException();
        iterators = new ArrayPriorityQueue<>();
        for (int i = 0; i < peekingIterator.length; i++) {
            if (peekingIterator[i].hasNext()){
                iterators.add(peekingIterator[i]);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return !iterators.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer next() {
        IPeekingIterator<Integer> iter = iterators.extractMin();
        Integer toReturn = iter.next();
        if (iter.hasNext()){
            iterators.add(iter);
        }
        return toReturn;
    }
}
