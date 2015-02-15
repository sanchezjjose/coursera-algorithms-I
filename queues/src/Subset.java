
public class Subset {

  public static void main(String args[]) {
    int k = Integer.valueOf(args[0]);

    RandomizedQueue<String> rq = new RandomizedQueue<String>();

    while (!StdIn.isEmpty()) {
      rq.enqueue(StdIn.readString());
    }

    for (int i = 0; i < k; i++) {
      StdOut.println(rq.dequeue());
    }
  }
}
