
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] items;
  private int N = 0; // represents number of items in the array above

  // construct an empty randomized queue
  public RandomizedQueue() {
    items = (Item[]) new Object[1];
  }

  private void resize(int capacity) {
    assert capacity >= N;

    Item[] copy = (Item[]) new Object[capacity];

    for (int i = 0; i < N; i++)
      copy[i] = items[i];

    items = copy;
  }

  // is the queue empty?
  public boolean isEmpty() {
    return N == 0;
  }

  // return the number of items on the queue
  public int size() {
    return N;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) throw new NullPointerException();

    if (N == items.length) resize(2 * items.length);
    items[N++] = item; // adds to end of the queue, and increments the N array
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException();

    int randN = StdRandom.uniform(0, N);
    Item item = items[randN];
    items[randN] = items[N-1]; // move last item into random position
    items[N-1] = null; // remove last item to avoid loitering
    N--;

    // shrink array if necessary to preserve memory
    if (N > 0 && N == items.length/4) resize(items.length/2);

    return item;
  }

  // return (but do not remove) a random item
  public Item sample() {
    if (isEmpty()) throw new NoSuchElementException();

    int randN = StdRandom.uniform(0, N);

    return items[randN];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizeQueueIterator();
  }

  private class RandomizeQueueIterator implements Iterator<Item> {

    private int[] randomizedOrder = new int[N];
    private int currentPos = 0;

    public RandomizeQueueIterator() {
      for (int i = 0; i < N; i++)
        randomizedOrder[i] = i;

      StdRandom.shuffle(randomizedOrder);
    }

    public boolean hasNext() {
      return currentPos < N;
    }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      return items[randomizedOrder[currentPos++]];
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing
  public static void main(String[] args) {
    RandomizedQueue<String> rq1 = new RandomizedQueue<String>();

    rq1.enqueue("0 - A");
    rq1.enqueue("1 - B");
    rq1.enqueue("2 - C");
    rq1.enqueue("3 - D");
    rq1.enqueue("4 - E");
    rq1.enqueue("5 - F");
    rq1.enqueue("6 - G");
    rq1.enqueue("7 - H");

    StdOut.print("======");
    for (String s : rq1) {
      StdOut.println(s);
    }

    rq1.dequeue();
    rq1.dequeue();
    rq1.dequeue();
    rq1.dequeue();
    rq1.dequeue();
    rq1.dequeue();

    StdOut.print("======");
    for (String s : rq1) {
      StdOut.println(s);
    }
  }
}
