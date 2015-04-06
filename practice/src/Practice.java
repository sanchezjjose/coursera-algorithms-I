import java.util.PriorityQueue;

public class Practice {

  /**
   * Given an array of ints, return a string identifying the range of numbers   
   *
   * Example: Input arr - [0 1 2 7 21 22 1098 1099]  Output - "0-2,7,21-22,1090,1099"
   */
  private static void printRange() {

    int[] a = new int[]{0, 1, 2, 7, 21, 22, 1098, 1099};

    int rangeStart = a[0];

    for (int i = 0; i < a.length; i++) {
      int curr = a[i];

      // previous
      if (i - 1 >= 0) {
        int prev = a[i-1];
        String rangeStartStr = Integer.toString(rangeStart);
        String prevStr = Integer.toString(prev);
        String currStr = Integer.toString(curr);

        String output = "";

        if (curr - prev != 1) {
          output = (rangeStartStr.equals(prevStr)) ? rangeStartStr + ',' : rangeStartStr + '-' + prevStr + ',';

          // reset
          rangeStart = curr;
        }

        // final state
        if (i + 1 == a.length) {
          output = (curr - prev == 1) ? prevStr + '-' + currStr : rangeStartStr + ',' + currStr;
        }

        System.out.print(output);
      }
    }
  }

  /**
   * Given an array such that every next element differs from the previous by +/- 1. (i.e. a[i+1] = a[i] +/-1 )
   * Find the local max OR min in O(1) time. The interviewer mentioned one more condition that the min or max
   * should be non-edge elements of the array.
   *
   * Example:
   *
   * 1 2 3 4 5 4 3 2 1 -> Local max is 5
   * 5 4 3 2 1 2 3 4 5 -> Local min is 1
   * 1 2 3 4 5 -> No local max or min exists
   * 5 4 3 2 1 -> No local max or min exists
   *
   */
  private static void findLocalMaxOrMin() {
    // Find the integer with no duplicates
    // Priority Queues / Double ended priority queues are efficient O(1) with remove the min / max

     Integer[] a = new Integer[]{0,1,2,3,4,5,6,7,8,7,6};
//     int[] a2 = new int[]{5,4,3,2,1,2,3,4};

    MinPQ<Integer> minPQ = new MinPQ<Integer>(a);

    System.out.print(minPQ.min());
  }

  public static void main(String args[]) {
//    printRange();
    findLocalMaxOrMin();
  }
}
