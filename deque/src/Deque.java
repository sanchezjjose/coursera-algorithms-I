
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a stack
 * and a queue that supports adding and removing items from either the front or the
 * back of the data structure.
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

  private Node first;
  private Node last;
  private int N = 0;

  private class Node {
    Item item;
    Node next;
    Node prev;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return N == 0;
  }

  // return the number of items on the deque
  public int size() {
    return N;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) throw new NullPointerException();

    Node oldFirst = first; // Initially -- will be null
    first = new Node(); // Initially -- creates actual first node
    first.item = item;
    first.next = oldFirst;

    if (isEmpty()) last = first;

    N++; // Initially -- now becomes 1
  }

  // add the item to the end
  public void addLast(Item item) {
    if (item == null) throw new NullPointerException();

    Node oldLast = last;
    last = new Node();
    last.item = item;
    last.next = null;

    if (isEmpty()) first = last;
    else           oldLast.next = last;

    N++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) throw new NoSuchElementException();

    Item firstItem = first.item;
    first = first.next; // if there were 2 items before removing, this covers first = last
    N--;

    if (isEmpty()) last = null;

    return firstItem;
  }

  // remove and return the item from the end
  public Item removeLast() {
    if (isEmpty()) throw new NoSuchElementException();

    Item lastItem = last.item;
    last = last.prev; // if there were 2 items before removing, this covers first = last
    N--;

    if (isEmpty()) first = null;

    return lastItem;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {

    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();

      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing
  public static void main(String[] args) {

    Deque<String> deque1 = new Deque<String>();

    StdOut.println(deque1.isEmpty());

    deque1.addFirst("hi");

    StdOut.println(deque1.size());
    StdOut.println(deque1.isEmpty());

    deque1.removeLast();

    StdOut.println(deque1.size());
    StdOut.println(deque1.isEmpty());

    deque1.addLast("hi");

    StdOut.println(deque1.size());
    StdOut.println(deque1.isEmpty());

    deque1.removeFirst();

    StdOut.println(deque1.size());
    StdOut.println(deque1.isEmpty());

    deque1.addFirst("hi");
    deque1.addLast("you");

    StdOut.println(deque1.size());
    StdOut.println(deque1.isEmpty());

    for (Iterator<String> it = deque1.iterator(); it.hasNext();) {
      StdOut.println(it.next());
    }

    deque1.removeFirst();
    deque1.removeLast();

    StdOut.println(deque1.size());
    StdOut.println(deque1.isEmpty());

    for (Iterator<String> it = deque1.iterator(); it.hasNext();) {
      StdOut.println(it.next());
    }
  }
}