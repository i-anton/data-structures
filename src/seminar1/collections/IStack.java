package seminar1.collections;

import java.util.Iterator;

/**
 * LIFO — Last In First Out
 */
public interface IStack<Item> extends Iterable<Item> {

    void push(Item item);

    Item pop();

    Iterator<Item> reversedIterator();

    boolean isEmpty();

    int size();
}
