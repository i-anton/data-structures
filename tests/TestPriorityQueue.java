import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import seminar1.collections.ArrayPriorityQueue;
import seminar1.collections.IPriorityQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * Класс тестирующий интерфейс {@link IPriorityQueue<Integer>} на основе {@link ArrayPriorityQueue<>}
 */
@RunWith(Enclosed.class)
public class TestPriorityQueue {
    public static class BasicTests {
        private IPriorityQueue<Integer> queue;

        @Before
        public void init() {
            queue = new ArrayPriorityQueue<>();
        }

        @Test
        public void isEmpty() {
            Assert.assertTrue(queue.isEmpty());
            Assert.assertEquals(queue.size(), 0);
        }

        @Test
        public void isNotEmpty() {
            queue.add(0);
            Assert.assertFalse(queue.isEmpty());
            Assert.assertEquals(queue.size(), 1);
        }

        @Test(expected = NullPointerException.class)
        public void addThrowsNullPointer() {
            queue.add(null);
        }

        @Test(expected = NoSuchElementException.class)
        public void extractFromEmpty() {
            queue.extractMin();
        }

        @Test(expected = NoSuchElementException.class)
        public void peekFromEmpty() {
            queue.peek();
        }
    }

    public static class OrderCheckNoInsertAfterRemoval {
        private IPriorityQueue<Integer> queue;

        @Before
        public void init() {
            queue = new ArrayPriorityQueue<>();
        }

        @Test
        public void checkAscending() {
            int n = 10;
            int step = 1;
            for (int i = 0; i <= n; i++) {
                queue.add(i * step);
                Assert.assertEquals(queue.size(), i + 1);
            }
            for (int i = 0; i <= n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Assert.assertEquals((int) temp, i * step);
            }
            Assert.assertTrue(queue.isEmpty());
            Assert.assertEquals(queue.size(), 0);
        }

        @Test
        public void checkDescending() {
            int n = 10;
            int step = 1;
            for (int i = n; i >= 0; i--) {
                queue.add(i * step);
            }
            for (int i = 0; i <= n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Assert.assertEquals((int) temp, i * step);
            }
            Assert.assertTrue(queue.isEmpty());
            Assert.assertEquals(queue.size(), 0);
        }

        @Test
        public void checkHighLow() {
            int n = 10; //x2-2
            int step = 1;
            for (int i = 1; i <= n; i++) {
                queue.add(i * step);
                queue.add(-i * step);
            }
            for (int i = n; i >= 1; i--) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Assert.assertEquals((int) temp, -i * step);
            }
            for (int i = 1; i <= n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Assert.assertEquals((int) temp, i * step);
            }
            Assert.assertTrue(queue.isEmpty());
            Assert.assertEquals(queue.size(), 0);
        }

        @Test
        public void checkAllSame() {
            int n = 10;
            int controlValue = 1;
            for (int i = 0; i <= n; i++) {
                queue.add(controlValue);
                Assert.assertEquals(queue.size(), i + 1);
            }
            for (int i = 0; i <= n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Assert.assertEquals((int) temp, controlValue);
            }
            Assert.assertTrue(queue.isEmpty());
            Assert.assertEquals(queue.size(), 0);
        }
    }

    public static class OrderCheckWithInsertAfterRemoval {
        private IPriorityQueue<Integer> queue;
        private TreeSet<Integer> goodSet;

        @Before
        public void init() {
            queue = new ArrayPriorityQueue<>();
            goodSet = new TreeSet<>();
        }

        @Test
        public void checkAscending() {
            int n = 10;
            int step = 2;
            for (int i = 0; i <= n; i++) {
                queue.add(i * step + 1);
                goodSet.add(i * step + 1);
                Assert.assertEquals(queue.size(), i + 1);
            }
            for (int i = 0; i <= n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Integer shouldBe = goodSet.first();
                goodSet.remove(shouldBe);
                Assert.assertEquals(temp, shouldBe);
            }

            for (int i = n + 1; i <= n * 2; i++) {
                queue.add(i * step + 1);
                goodSet.add(i * step + 1);
            }
            for (int i = 0; i < n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Integer shouldBe = goodSet.first();
                goodSet.remove(shouldBe);
                Assert.assertEquals(temp, shouldBe);
            }
            Assert.assertTrue(queue.isEmpty());
            Assert.assertEquals(queue.size(), 0);
        }

        @Test
        public void checkDescending() {
            int n = 10;
            int step = 2;
            for (int i = n; i >= 0; i--) {
                queue.add(i * step + 1);
                goodSet.add(i * step + 1);
            }
            for (int i = 0; i <= n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Integer shouldBe = goodSet.first();
                goodSet.remove(shouldBe);
                Assert.assertEquals(temp, shouldBe);
            }
            for (int i = n + 1; i <= n * 2; i++) {
                queue.add(-i * step + 1);
                goodSet.add(-i * step + 1);
            }
            Assert.assertEquals(queue.size(), n);
            for (int i = 0; i < n; i++) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Integer shouldBe = goodSet.first();
                goodSet.remove(shouldBe);
                Assert.assertEquals(temp, shouldBe);
            }
            Assert.assertEquals(queue.size(), 0);
        }

        @Test
        public void checkAllSame() {
            int n = 10;
            int controlValue = 1;
            for (int i = 0; i <= n; i++) {
                queue.add(controlValue);
                Assert.assertEquals(queue.size(), i + 1);
            }
            for (int i = n; i >= n / 2; i--) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Assert.assertEquals((int) temp, controlValue);
            }

            for (int i = 0; i <= n / 2; i++) {
                queue.add(controlValue);
                Assert.assertEquals(queue.size(), n / 2 + i + 1);
            }
            Assert.assertEquals(queue.size(), n + 1);
            for (int i = n; i >= 0; i--) {
                Integer peeked = queue.peek();
                Integer temp = queue.extractMin();
                Assert.assertEquals(peeked, temp);
                Assert.assertEquals((int) temp, controlValue);
            }
            Assert.assertTrue(queue.isEmpty());
            Assert.assertEquals(queue.size(), 0);
        }
    }

    public static class IteratorTests {
        private IPriorityQueue<Integer> queue;

        @Before
        public void init() {
            queue = new ArrayPriorityQueue<>();
        }

        @Test
        public void empty() {
            Iterator<Integer> it = queue.iterator();
            Assert.assertFalse(it.hasNext());
        }

        @Test
        public void oneElement() {
            queue.add(0);
            Iterator<Integer> it = queue.iterator();
            Assert.assertTrue(it.hasNext());
            Integer temp = it.next();
            Assert.assertEquals((int) temp, 0);
            Assert.assertFalse(it.hasNext());
        }

        @Test
        public void someElements() {
            int n = 10;
            int step = 2;
            for (int i = 0; i < n; i++) {
                queue.add(i * step + 1);
                Assert.assertEquals(queue.size(), i + 1);
            }
            Iterator<Integer> it = queue.iterator();
            int counter = 0;
            Assert.assertTrue(it.hasNext());
            while (it.hasNext()){
                it.next();
                counter++;
            }
            Assert.assertEquals(counter, queue.size());
            Assert.assertFalse(it.hasNext());
        }
    }
}
