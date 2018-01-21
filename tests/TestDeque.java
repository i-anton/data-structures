import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import seminar1.collections.CyclicArrayDeque;
import seminar1.collections.IDeque;
import seminar1.collections.LinkedDeque;

/**
 * Класс тестирующий интерфейс {@link IDeque<Integer>} в двух реализациях:
 * 1) на массиве {@link CyclicArrayDeque<Integer>}
 * 2) на списке {@link LinkedDeque<Integer>}
 */
enum Cases {
    BackFront, FrontFront, FrontBack, BackBack
}

@RunWith(value = Parameterized.class)
public class TestDeque {

    @Parameterized.Parameter()
    public Class<?> testClass;

    private IDeque<Integer> deque;
    private ArrayDeque<Integer> goodDeque;
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Class<?>> data() {
        return Arrays.asList(
                CyclicArrayDeque.class,
                LinkedDeque.class
        );
    }

    @Before
    @SuppressWarnings("unchecked")
    public void init() {
        try {
            deque = (IDeque<Integer>) testClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        goodDeque = new ArrayDeque<>();
    }

    // check empty without add
    @Test
    public void isEmptyNoChange() {
        Assert.assertTrue(deque.isEmpty());
        Assert.assertEquals(deque.size(), 0);
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsPopFrontEmptyNoChange() {
        deque.popFront();
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsPopBackEmptyNoChange() {
        deque.popBack();
    }

    @Test(expected = NullPointerException.class)
    public void throwsPushFrontNullNoChange() {
        deque.pushFront(null);
    }

    @Test(expected = NullPointerException.class)
    public void throwsPushBackNullNoChange() {
        deque.pushBack(null);
    }

    //checks size changes only
    @Test
    public void sizeChanges(){
        Assert.assertEquals(0,deque.size());
        Assert.assertEquals(true, deque.isEmpty());
        int toPut = 40;
        int expectedSize = 0;
        for (int i = 0; i < toPut; i++) {
            deque.pushBack(1);
            expectedSize++;
            Assert.assertEquals(expectedSize,deque.size());
            deque.pushFront(1);
            expectedSize++;
            Assert.assertEquals(expectedSize,deque.size());
        }
        Assert.assertEquals(false, deque.isEmpty());
        for (int i = 0; i < toPut; i++) {
            deque.popBack();
            expectedSize--;
            Assert.assertEquals(expectedSize,deque.size());
            deque.popFront();
            expectedSize--;
            Assert.assertEquals(expectedSize,deque.size());
        }
        Assert.assertEquals(0,deque.size());
        Assert.assertEquals(true, deque.isEmpty());
    }

    @Test
    public void checkOneElementPushNPop(){
        Integer item = 100;
        // 4 common cases
        deque.pushBack(item);
        Assert.assertEquals(item,deque.popFront());
        Assert.assertEquals(0,deque.size());

        deque.pushBack(item);
        Assert.assertEquals(item,deque.popBack());
        Assert.assertEquals(0,deque.size());

        deque.pushFront(item);
        Assert.assertEquals(item,deque.popFront());
        Assert.assertEquals(0,deque.size());

        deque.pushFront(item);
        Assert.assertEquals(item,deque.popBack());
        Assert.assertEquals(0,deque.size());
    }

    private void pushDeques(boolean front, Integer elem){
        if (front){
            deque.pushFront(elem);
            goodDeque.addFirst(elem);
        } else {
            deque.pushBack(elem);
            goodDeque.addLast(elem);
        }
    }
    private void popDeques(boolean front){
        Integer a, b;
        if (front){
            a = deque.popFront();
            b = goodDeque.removeFirst();
        } else {
            a = deque.popBack();
            b = goodDeque.removeLast();
        }
        Assert.assertEquals(a,b);
    }

    // can be merged with check2Pop, but need some method references
    private void make2Push(Cases scenario,Integer a, Integer b){
        switch (scenario){
            case BackBack:
                pushDeques(false,a);
                pushDeques(false,b);
                break;
            case FrontBack:
                pushDeques(true,a);
                pushDeques(false,b);
                break;
            case FrontFront:
                pushDeques(true,a);
                pushDeques(true,b);
                break;
            case BackFront:
                pushDeques(false,a);
                pushDeques(true,b);
                break;
        }
    }

    private void check2Pops(Cases scenario){
        switch (scenario){
            case BackBack:
                popDeques(false);
                popDeques(false);
                break;
            case FrontBack:
                popDeques(true);
                popDeques(false);
                break;
            case FrontFront:
                popDeques(true);
                popDeques(true);
                break;
            case BackFront:
                popDeques(false);
                popDeques(true);
                break;
        }
    }

    @Test
    public void checkTwoElementPushNPop(){
        // TODO: Parametrize to return READABLE error
        // maybe use @Nested in junit5?
        Integer a = 1, b = 2;
        // perform cross join
        Cases[][] actions = new Cases[16][2];
        for (int i = 0; i < 4; i++) {
            // fill [i][0] like 1,2,3,4,1,2,3,4 but values from enum
            // fill [i][1] like 1111,2222,3333,... but values from enum
            for (int j = 0; j < 4; j++) {
                actions[i*4+j][0] = Cases.values()[j];
                actions[i*4+j][1] = Cases.values()[i];
            }
        }
        //16 checks, should be fine
        for (int i = 0; i < 16; i++) {
            make2Push(actions[i][0],a,b);
            check2Pops(actions[i][1]);
        }
    }

    @Test
    public void canGrow() {
        for (int i = 0; i < 100; i++) {
            pushDeques(true, i);
        }
        Assert.assertEquals(100,deque.size());
        for (int i = 0; i < 100; i++) {
            popDeques(false);
        }
    }

    @Test
    public void canGrowShrinkNGrow() {
        for (int i = 0; i < 100; i++) {
            pushDeques(true, i);
        }
        Assert.assertEquals(goodDeque.size(),deque.size());
        for (int i = 0; i < 90; i++) {
            popDeques(false);
        }
        Assert.assertEquals(goodDeque.size(),deque.size());
        for (int i = 0; i < 100; i++) {
            pushDeques(true, i);
        }
        Assert.assertEquals(goodDeque.size(),deque.size());
        for (int i = 0; i < 110; i++) {
            popDeques(false);
        }
        Assert.assertEquals(goodDeque.size(),deque.size());
    }

    @Test
    public void tryToFailResize() {
        //shift by half (can be failed by early resize (but A LOT memory will suffer))
        for (int i = 0; i < 8; i++) {
            pushDeques(true, i);
        }
        for (int i = 0; i < 4; i++) {
            popDeques(false);
        }
        //overflow
        for (int i = 0; i < 8; i++) {
            pushDeques(true, 8 + i);
        }
        //check values
        for (int i = 0; i < 12; i++) {
            popDeques(false);
        }
        Assert.assertEquals(0,deque.size());
    }

    // check iterator
    // on failed resize!
    // simple
    @Test
    public void simpleIteratorTest() {
        for (int i = 0; i < 100; i++) {
            pushDeques(true, i);
        }
        Assert.assertEquals(100,deque.size());
        Iterator<Integer> iterTested = deque.iterator();
        Iterator<Integer> iterGood = goodDeque.iterator();
        while (iterGood.hasNext()){
            Assert.assertTrue(iterTested.hasNext());
            Assert.assertEquals(iterGood.next(),iterTested.next());
        }
    }

    @Test
    public void addNRemoveIteratorTest() {
        for (int i = 0; i < 100; i++) {
            pushDeques(true, i);
        }
        for (int i = 0; i < 40; i++) {
            popDeques(true);
        }
        for (int i = 0; i < 20; i++) {
            pushDeques(true, 8 + i);
        }
        Iterator<Integer> iterTested = deque.iterator();
        Iterator<Integer> iterGood = goodDeque.iterator();
        while (iterGood.hasNext()){
            Assert.assertTrue(iterTested.hasNext());
            Assert.assertEquals(iterGood.next(),iterTested.next());
        }
    }

    @Test
    public void tryToFailIteratorOnResize() {
        //shift by half (can be failed by early resize (but A LOT memory will suffer))
        for (int i = 0; i < 8; i++) {
            pushDeques(true, i);
        }
        for (int i = 0; i < 4; i++) {
            popDeques(false);
        }
        //overflow
        for (int i = 0; i < 8; i++) {
            pushDeques(true, 8 + i);
        }
        Iterator<Integer> iterTested = deque.iterator();
        Iterator<Integer> iterGood = goodDeque.iterator();
        while (iterGood.hasNext()){
            Assert.assertTrue(iterTested.hasNext());
            Assert.assertEquals(iterGood.next(),iterTested.next());
        }
    }
}
