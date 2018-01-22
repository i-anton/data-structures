import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

import seminar1.iterators.PeekingIncreasingIterator;

/**
 * Класс тестирующий {@link seminar1.iterators.PeekingIncreasingIterator}
 */
public class TestPeekingIncreasingIterator {

    @Test(expected = NoSuchElementException.class)
    public void testEmptyIterator() {
        new PeekingIncreasingIterator(10, 10, 0, 1).next();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeStartValue() {
        new PeekingIncreasingIterator(-1, 10, 10, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroMaxValue() {
        new PeekingIncreasingIterator(10, 0, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMaxValue() {
        new PeekingIncreasingIterator(10, -1, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxValueLessStart() {
        new PeekingIncreasingIterator(10, 9, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeStepLimit() {
        new PeekingIncreasingIterator(10, 10, -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testZeroMaxStepGrowth() {
        // zero is not positive! mb fix IncreasingIterator javadoc
        new PeekingIncreasingIterator(10, 11, 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeMaxStepGrowth() {
        new PeekingIncreasingIterator(10, 11, 1, -1);
    }

    @Test
    public void testStartValue() {
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(20, 200, 30, 3);
        Assert.assertTrue(pi.hasNext());
        Assert.assertTrue(pi.next() == 20);
    }
    @Test
    public void testStepLimitWithoutPeek() {
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(10, 200, 30, 3);
        int count = 0;
        while (pi.hasNext()){
            pi.next();
            count++;
        }
        Assert.assertTrue(count == 30);
    }

    @Test
    public void testStepLimitWithPeek() {
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(10, 200, 30, 3);
        int count = 0;
        while (pi.hasNext()){
            pi.peek();
            pi.next();
            count++;
            if (count > 30) Assert.fail("Returns more than should");
        }
        Assert.assertTrue(count == 30);
    }

    @Test(expected = NoSuchElementException.class)
    public void testPeekEmpty() {
        new PeekingIncreasingIterator(10, 10, 0, 1).peek();
    }

    @Test(expected = NoSuchElementException.class)
    public void testPeekWhenHasNoNext() {
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(10, 200, 30, 3);
        while (pi.hasNext()){
            pi.next();
        }
        pi.peek();
    }

    @Test
    public void testPeekNotChangesNext() {
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(10, 200, 30, 3);
        while (pi.hasNext()){
            //triple peek
            Assert.assertEquals(pi.peek(),pi.peek());
            Assert.assertEquals(pi.peek(), pi.next());
        }
    }

    @Test
    public void testIsIncreasing() {
        // Iterator should be strictly INCREASING, next > previous, not next >= previous ?
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(10, 200, 30, 3);
        Integer temp;
        while (pi.hasNext()){
            temp = pi.next();
            if (pi.hasNext()) {
                Assert.assertTrue("Not increasing Previous: "+temp+", Next: "+pi.peek(),
                        temp <= pi.peek());
            }
        }
    }

    @Test
    public void testReturnsInRange() {
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(0, 100000, 100, 10);
        Integer temp = pi.next();
        while (pi.hasNext()){
            Integer nextTemp = pi.next();
            Assert.assertTrue("Diff is :"+(nextTemp-temp)+", Previous: "+temp+", Next: "+nextTemp,
                    nextTemp-temp >= 0 && nextTemp-temp < 100);
            temp = nextTemp;
        }
    }

    @Test
    public void testCompareBothEmpty() {
        //initially empty
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(10, 200, 0, 3);
        PeekingIncreasingIterator pi1 = new PeekingIncreasingIterator(10, 200, 0, 3);
        Assert.assertTrue(pi.compareTo(pi1) == 0);
        Assert.assertTrue(pi1.compareTo(pi) == 0);

        //NOT empty, but ended
        pi = new PeekingIncreasingIterator(10, 300, 10, 4);
        pi1 = new PeekingIncreasingIterator(10, 200, 10, 3);
        while (pi.hasNext()){
            pi.next();
            pi1.next();
        }
        Assert.assertTrue(pi.compareTo(pi1) == 0);
        Assert.assertTrue(pi1.compareTo(pi) == 0);
    }
    @Test
    public void testCompareOneNotEmpty() {
        //initially empty one
        PeekingIncreasingIterator pi = new PeekingIncreasingIterator(10, 200, 0, 3);
        PeekingIncreasingIterator pi1 = new PeekingIncreasingIterator(10, 200, 1, 3);
        Assert.assertTrue(pi.compareTo(pi1) < 0);
        Assert.assertTrue(pi1.compareTo(pi) > 0);

        //different length
        pi = new PeekingIncreasingIterator(10, 300, 10, 4);
        pi1 = new PeekingIncreasingIterator(10, 500, 20, 3);
        while (pi.hasNext()&& pi1.hasNext()){
            pi.next();
            pi1.next();
        }
        Assert.assertTrue(pi.compareTo(pi1) < 0);
        Assert.assertTrue(pi1.compareTo(pi) > 0);
    }
    @Test
    public void testCompareBothNotEmptyStartValues() {
        //start value pi > pi1
        PeekingIncreasingIterator pi =
                new PeekingIncreasingIterator(11, 200, 1, 3);
        PeekingIncreasingIterator pi1 =
                new PeekingIncreasingIterator(10, 200, 1, 3);
        Assert.assertTrue(pi.compareTo(pi1) > 0);
        Assert.assertTrue(pi1.compareTo(pi) < 0);

        //start value pi < pi1
        pi = new PeekingIncreasingIterator(10, 200, 1, 3);
        pi1 = new PeekingIncreasingIterator(11, 200, 1, 3);
        Assert.assertTrue(pi.compareTo(pi1) < 0);
        Assert.assertTrue(pi1.compareTo(pi) > 0);

        //start value pi == pi1
        pi = new PeekingIncreasingIterator(10, 200, 1, 3);
        pi1 = new PeekingIncreasingIterator(10, 200, 1, 3);
        Assert.assertTrue(pi.compareTo(pi1) == 0);
        Assert.assertTrue(pi1.compareTo(pi) == 0);
    }

}
