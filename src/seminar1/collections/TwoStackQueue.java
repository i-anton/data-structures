package seminar1.collections;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwoStackQueue<Item> implements IQueue<Item> {

    private IStack<Item> stack1;
    private IStack<Item> stack2;
    private int size;

    public TwoStackQueue() {
        stack1 = new ArrayStack<>();
        stack2 = new ArrayStack<>();
    }

    @Override
    public void enqueue(Item item) {
        stack1.push(item);
        ++size;
    }

    @Override
    public Item dequeue() {
        if (stack2.isEmpty()){
            while (!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) throw new NoSuchElementException();
        --size;
        return stack2.pop();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Item> iterator() {
        return new TwoStackQueueIterator();
    }

    //TODO: need reversed iterator
    private class TwoStackQueueIterator implements Iterator<Item> {
        Iterator<Item> stack2Iter = stack2.iterator();

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            // top - down stack2, потом down - up stack1
            return null;
        }
    }
}
