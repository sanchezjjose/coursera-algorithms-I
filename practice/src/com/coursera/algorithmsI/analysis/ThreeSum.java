package com.coursera.algorithmsI.analysis;

/*************************************************************************
 *  Compilation:  javac ThreeSum.java
 *  Execution:    java ThreeSum input.txt
 *  Dependencies: In.java StdOut.java Stopwatch.java
 *  Data files:   http://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program with cubic running time. Read in N integers
 *  and counts the number of triples that sum to exactly 0
 *  (ignoring integer overflow).
 *
 *  % java ThreeSum 1Kints.txt
 *  70
 *
 *  % java ThreeSum 2Kints.txt
 *  528
 *
 *  % java ThreeSum 4Kints.txt
 *  4039
 *
 *************************************************************************/

import com.coursera.algorithmsI.stdlib.StdOut;

/**
 *  The <tt>ThreeSum</tt> class provides static methods for counting
 *  and printing the number of triples in an array of integers that sum to 0
 *  (ignoring integer overflow).
 *  <p>
 *  This implementation uses a triply nested loop and takes proportional to N^3,
 *  where N is the number of integers.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/14analysis">Section 1.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class ThreeSum {

  /**
   * Prints to standard output the (i, j, k) with i < j < k such that a[i] + a[j] + a[k] == 0.
   * @param a the array of integers
   */
  public static void printAll(int[] a) {
    int N = a.length;
    for (int i = 0; i < N; i++) {
      for (int j = i+1; j < N; j++) {
        for (int k = j+1; k < N; k++) {
          if (a[i] + a[j] + a[k] == 0) {
            StdOut.println(a[i] + " " + a[j] + " " + a[k]);
          }
        }
      }
    }
  }

  /**
   * Returns the number of triples (i, j, k) with i < j < k such that a[i] + a[j] + a[k] == 0.
   * @param a the array of integers
   * @return the number of triples (i, j, k) with i < j < k such that a[i] + a[j] + a[k] == 0
   */
  public static int count(int[] a) {
    int N = a.length;
    int cnt = 0;
    for (int i = 0; i < N; i++) {
      for (int j = i + 1; j < N; j++) {
        for (int k = j + 1; k < N; k++) {
          if (a[i] + a[j] + a[k] == 0) {
            cnt++;
          }
        }
      }
    }
    return cnt;
  }

  /**
   * Reads in a sequence of integers from a file, specified as a command-line argument;
   * counts the number of triples sum to exactly zero; prints out the time to perform
   * the computation.
   */
  public static void main(String[] args)  {


//    File f = new File(args[0]);
//    StdOut.println(f.exists());
//
//    In in = new In(args[0]);
//    int[] a = in.readAllInts();
//
//    Stopwatch timer = new Stopwatch();
//    int cnt = count(a);
//    StdOut.println("elapsed time = " + timer.elapsedTime());
//    StdOut.println(cnt);



//    int cnt0 = 0;
//    int cnt1 = 0;
//    int cnt2 = 0;
//    int cnt3 = 0;
//    int N = 10;
//
//    // N = 10
//    for (int i = 0; i < N; i++) {
//      cnt0++;
//
//      // N - 1 = 9
//      for (int j = i + 1; j < N; j++) {
//        cnt1++;
//
//        // N - 2 = 8
//        for (int k = j + 1; k < N; k++) {
//          cnt2++;
//
//          // N - 3 = 7
//          for (int m = k + 1; m < N; m++) {
//            cnt3++;
//          }
//        }
//      }
//    }

//    System.out.println(cnt0);
//    System.out.println(cnt1);
//    System.out.println(cnt2);
//    System.out.println(cnt3);

//    BigInteger N = new BigInteger("100000000");
//    int sum = 0;
//
//    System.out.println(N.intValue());
//
//    Stopwatch timer = new Stopwatch();
//    for (int i = 1; i <= N.intValue(); i = i + 2) // 1, 3, 5, 7, 9 (N/2)
//
//      // [1], [1, 2, 3], [1, 2, 3, 4, 5], [1, 2, 3...7], [1, 2, 3...9]
//      // Executes 5 times
//      for (int j = 1; j <= i; j++)
//        sum++;
//
//    StdOut.println("elapsed time = " + timer.elapsedTime());
//    System.out.println(sum);


    int N = 10;
    int sum = 0;

    for (int i = 0; i < N; i++) {
      for (int j = i; j < N; j++) {
        for (int k = i; k < j; j++) {
          sum++;
        }
      }
    }

    System.out.println(sum);
  }
}
