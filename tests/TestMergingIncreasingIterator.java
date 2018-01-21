import org.junit.Assert;
import org.junit.Test;
import seminar1.iterators.IncreasingIterator;
import seminar1.iterators.MergingIncreasingIterator;

import java.util.NoSuchElementException;

/**
 * Класс тестирующий {@link seminar1.iterators.MergingIncreasingIterator}
 */

public class TestMergingIncreasingIterator {
    private MergingIncreasingIterator iterator;

    @Test(timeout = 1000)
    public void isIncreasingEqualIterators() {
        IncreasingIterator it1 = new IncreasingIterator(0,100,2,2);
        IncreasingIterator it2 = new IncreasingIterator(0,100,2,2);
        iterator = new MergingIncreasingIterator(it1,it2);
        Assert.assertTrue(iterator.hasNext());
        Integer tmp = iterator.next();
        Assert.assertTrue(tmp != null);
        while (iterator.hasNext()) {
            Integer tmp2 = iterator.next();
            Assert.assertTrue(tmp2 != null);
            Assert.assertTrue(tmp + " is not less than " + tmp2, tmp <= tmp2);
            tmp = tmp2;
        }
        // проверка последнего получения
        boolean throwed = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e){
            throwed = true;
        }
        Assert.assertTrue(throwed);
    }

    @Test(timeout = 1000)
    public void isIncreasingDifferentLenIteratorsFirstLarger() {
        IncreasingIterator it1 = new IncreasingIterator(1, 100, 10, 2);
        IncreasingIterator it2 = new IncreasingIterator(0, 100, 5, 2);
        iterator = new MergingIncreasingIterator(it1, it2);
        Assert.assertTrue(iterator.hasNext());
        Integer tmp = iterator.next();
        Assert.assertTrue(tmp != null);
        while (iterator.hasNext()) {
            Integer tmp2 = iterator.next();
            Assert.assertTrue(tmp2 != null);
            Assert.assertTrue(tmp + " is not less than " + tmp2, tmp <= tmp2);
            tmp = tmp2;
        }
        boolean throwed = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e){
            throwed = true;
        }
        Assert.assertTrue(throwed);
    }

    @Test(timeout = 1000)
    public void isIncreasingDifferentLenIteratorsSecondLarger() {
        IncreasingIterator it1 = new IncreasingIterator(0, 100, 5, 2);
        IncreasingIterator it2 = new IncreasingIterator(1, 100, 10, 2);
        iterator = new MergingIncreasingIterator(it1, it2);
        Assert.assertTrue(iterator.hasNext());
        Integer tmp = iterator.next();
        Assert.assertTrue(tmp != null);
        while (iterator.hasNext()) {
            Integer tmp2 = iterator.next();
            Assert.assertTrue(tmp2 != null);
            Assert.assertTrue(tmp + " is not less than " + tmp2, tmp <= tmp2);
            tmp = tmp2;
        }
        boolean throwed = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e){
            throwed = true;
        }
        Assert.assertTrue(throwed);
    }

    @Test(timeout = 1000)
    public void isIncreasingOnlyFirst() {
        IncreasingIterator it1 = new IncreasingIterator(0, 100, 5, 10);
        IncreasingIterator it2 = new IncreasingIterator(0, 100, 0, 10);
        iterator = new MergingIncreasingIterator(it1, it2);
        Assert.assertTrue(iterator.hasNext());
        Integer tmp = iterator.next();
        Assert.assertTrue(tmp != null);
        while (iterator.hasNext()) {
            Integer tmp2 = iterator.next();
            Assert.assertTrue(tmp2 != null);
            Assert.assertTrue(tmp + " is not less than " + tmp2, tmp <= tmp2);
            tmp = tmp2;
        }
        boolean throwed = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e){
            throwed = true;
        }
        Assert.assertTrue(throwed);
    }

    @Test(timeout = 1000)
    public void isIncreasingOnlySecond() {
        IncreasingIterator it1 = new IncreasingIterator(0, 100, 0, 10);
        IncreasingIterator it2 = new IncreasingIterator(0, 100, 5, 10);
        iterator = new MergingIncreasingIterator(it1, it2);
        Assert.assertTrue(iterator.hasNext());
        Integer tmp = iterator.next();
        Assert.assertTrue(tmp != null);
        while (iterator.hasNext()) {
            Integer tmp2 = iterator.next();
            Assert.assertTrue(tmp2 != null);
            Assert.assertTrue(tmp + " is not less than " + tmp2, tmp <= tmp2);
            tmp = tmp2;
        }
        boolean throwed = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e){
            throwed = true;
        }
        Assert.assertTrue(throwed);
    }

    @Test
    public void testEmpty() {
        IncreasingIterator it1 = new IncreasingIterator(0, 100, 0, 10);
        IncreasingIterator it2 = new IncreasingIterator(0, 100, 0, 10);
        iterator = new MergingIncreasingIterator(it1, it2);
        Assert.assertFalse(iterator.hasNext());
        boolean throwed = false;
        try {
            iterator.next();
        } catch (NoSuchElementException e){
            throwed = true;
        }
        Assert.assertTrue(throwed);
    }

}
