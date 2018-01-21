package seminar1.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор возвращающий последовательность из двух возрастающих итераторов в порядке возрастания
 * first = 1,3,4,5,7
 * second = 0,2,4,6,8
 * result = 0,1,2,3,4,4,5,6,7,8
 * <p>
 * Time = O(k),
 * k — суммарное количество элементов
 */
public class MergingIncreasingIterator implements Iterator<Integer> {

    private IncreasingIterator first;
    private IncreasingIterator second;
    private Integer lastMax;
    private boolean isFirst;

    public MergingIncreasingIterator(IncreasingIterator first, IncreasingIterator second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean hasNext() {
        return lastMax != null || first.hasNext() || second.hasNext();
    }

    @Override
    public Integer next() {
        if (lastMax != null) {
            if (isFirst) {
                if (second.hasNext()) {
                    Integer temp = second.next();
                    if (temp > lastMax) {
                        // замена временной ячейки
                        Integer toReturn = lastMax;
                        lastMax = temp;
                        isFirst = false;
                        return toReturn;
                    }
                    return temp;
                } else {
                    // вторая последовательность кончилась
                    Integer toReturn = lastMax;
                    if (first.hasNext()) {
                        lastMax = first.next();
                    } else {
                        lastMax = null;
                    }
                    return toReturn;
                }
            } else {
                if (first.hasNext()) {
                    Integer temp = first.next();
                    if (temp > lastMax) {
                        // замена временной ячейки
                        Integer toReturn = lastMax;
                        lastMax = temp;
                        isFirst = true;
                        return toReturn;
                    }
                    return temp;
                } else {
                    // вторая последовательность кончилась
                    Integer toReturn = lastMax;
                    if (second.hasNext()) {
                        lastMax = second.next();
                    } else {
                        lastMax = null;
                    }
                    return toReturn;
                }
            }
        } else {
            // первое и последнее получение элемента
            if (!first.hasNext() && !second.hasNext()) throw new NoSuchElementException("Tries to get from empty");
            if (first.hasNext() && second.hasNext()) {
                Integer tempFirst = first.next();
                Integer tempSecond = second.next();
                if (tempFirst > tempSecond) {
                    lastMax = tempFirst;
                    isFirst = true;
                    return tempSecond;
                } else {
                    lastMax = tempSecond;
                    isFirst = false;
                    return tempFirst;
                }
            } else if (first.hasNext()){
                return first.next();
            } else {
                return second.next();
            }
        }
    }
}
