import org.junit.Assert;
import org.junit.Test;
import seminar1.iterators.IPeekingIterator;
import seminar1.iterators.MergingPeekingIncreasingIterator;
import seminar1.iterators.PeekingIncreasingIterator;

import java.util.NoSuchElementException;

/**
 * Класс тестирующий {@link seminar1.iterators.MergingPeekingIncreasingIterator}
 * на основе {@link seminar1.iterators.IPeekingIterator<Integer>}
 */
public class TestMergingPeekingIncreasingIterator {

    @Test(expected = NullPointerException.class)
    public void testConstructorThrows() {
        new MergingPeekingIncreasingIterator((IPeekingIterator<Integer>[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorBadIterator() {
        new MergingPeekingIncreasingIterator(new PeekingIncreasingIterator(-10,1000,50,10));
    }

    @Test
    public void testZeroLenIterator() {
        MergingPeekingIncreasingIterator iterator = new MergingPeekingIncreasingIterator(
                new PeekingIncreasingIterator(10,1000,0,10));
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void testOrderOnePeeking() {
        MergingPeekingIncreasingIterator iterator = new MergingPeekingIncreasingIterator(
                new PeekingIncreasingIterator(10,1000,50,10));
        orderChecking(iterator);
    }

    @Test
    public void testOrderTwoPeeking() {
        int size[] = new int[]{30,10};
        PeekingIncreasingIterator iterators[] = new PeekingIncreasingIterator[]{
                new PeekingIncreasingIterator(10,1000,size[0],10),
                new PeekingIncreasingIterator(1000,1200,size[1],1),
        };
        MergingPeekingIncreasingIterator iterator = new MergingPeekingIncreasingIterator(iterators);
        orderChecking(iterator);
    }


    @Test
    public void testOrderThreePeeking() {
        int size[] = new int[]{30,10,20};
        PeekingIncreasingIterator iterators[] = new PeekingIncreasingIterator[]{
                new PeekingIncreasingIterator(10,1000,size[0],10),
                new PeekingIncreasingIterator(1000,1200,size[1],1),
                new PeekingIncreasingIterator(500,600,size[2],1),
        };
        MergingPeekingIncreasingIterator iterator =
                new MergingPeekingIncreasingIterator(iterators);
        orderChecking(iterator);
    }

    private void orderChecking(MergingPeekingIncreasingIterator iterator){
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
    public void testCountOne() {
        int size = 30;
        MergingPeekingIncreasingIterator pi = new MergingPeekingIncreasingIterator(
                new PeekingIncreasingIterator(10,1000,size,10));
        Assert.assertTrue(checkCount(size,pi));
    }

    @Test
    public void testCountTwo() {
        int size[] = new int[]{30,10};
        PeekingIncreasingIterator iterators[] = new PeekingIncreasingIterator[]{
                new PeekingIncreasingIterator(10,1000,size[0],10),
                new PeekingIncreasingIterator(1000,1200,size[1],1),
        };
        MergingPeekingIncreasingIterator pi = new MergingPeekingIncreasingIterator(iterators);
        Assert.assertTrue(checkCount(40,pi));
    }

    @Test
    public void testCountThree() {
        int size[] = new int[]{30,10,20};
        PeekingIncreasingIterator iterators[] = new PeekingIncreasingIterator[]{
                new PeekingIncreasingIterator(10,1000,size[0],10),
                new PeekingIncreasingIterator(1000,1200,size[1],1),
                new PeekingIncreasingIterator(500,600,size[2],1),
        };
        MergingPeekingIncreasingIterator pi = new MergingPeekingIncreasingIterator(iterators);
        Assert.assertTrue(checkCount(60,pi));
    }

    private boolean checkCount(int expectedSize, MergingPeekingIncreasingIterator iterator){
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
            if (count > expectedSize) Assert.fail("Returns more than should");
        }
        return count == expectedSize;
    }
}
