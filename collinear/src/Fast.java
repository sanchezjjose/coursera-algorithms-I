import java.util.Arrays;

/**
 * Remarkably, it is possible to solve the problem much faster than the
 * brute-force solution described above. Given a point p, the following method
 * determines whether p participates in a set of 4 or more collinear points.
 *
 * - Think of p as the origin.
 * - For each other point q, determine the slope it makes with p.
 * - Sort the points according to the slopes they makes with p.
 * - Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
 *   If so, these points, together with p, are collinear.
 *
 * Applying this method for each of the N points in turn yields an efficient algorithm to the problem.
 * The algorithm solves the problem because points that have equal slopes with respect to p are collinear,
 * and sorting brings such points together. The algorithm is fast because the bottleneck operation is sorting.
 */
public class Fast {

  private static Point[] coordinatesToPoints(String[] coordinates) {
    In in = new In(coordinates[0]);
    int size = in.readInt();

    Point[] points = new Point[size];

    for (int i = 0; i < size; i++) {
      int x = in.readInt();
      int y = in.readInt();

      Point p = new Point(x, y);
      points[i] = p;
    }

    return points;
  }

  private static void printCollinearPoints(Point[] points, int max) {
    String output = "";

    for (int i = 0; i < max; i++) {
      if (i == max - 1) {
        output += String.format("%s", points[i]);
      } else {
        output += String.format("%s -> ", points[i]);
      }
    }

    StdOut.println(output);
  }

  public static void main(String[] args) {

    // rescale the coordinate system
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01);

    final int MAX_COMPARES = 5;

    Point[] points = coordinatesToPoints(args);

    if (points.length >= MAX_COMPARES) {
      double prevSlope = Double.NEGATIVE_INFINITY;

      for (int i = 0; i < points.length; i++) {
        Point p = points[i];

        /*
         * Sort using the points comparator. Arrays.sort uses both
         * Quick and Merge sort. It optimizes to use quick sort when
         * the collection is small, and when it is almost sorted. In this
         * case, quick sort is faster and more efficient than merge sort.
         * Merge sort is then used for the rest of the cases.
         */
        Point[] pointsBySlopeOrder = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsBySlopeOrder, p.SLOPE_ORDER);

        int j = 1;
        while (j < MAX_COMPARES) {
          Point q = pointsBySlopeOrder[j++];
          Point r = pointsBySlopeOrder[j++];
          Point s = pointsBySlopeOrder[j++];
          Point t = pointsBySlopeOrder[j++];

          p.draw();
          q.draw();
          r.draw();
          s.draw();
          t.draw();

          double pqSlope = p.slopeTo(q);
          double prSlope = p.slopeTo(r);
          double psSlope = p.slopeTo(s);
          double ptSlope = p.slopeTo(t);

          if (prevSlope != pqSlope && pqSlope == prSlope && pqSlope == psSlope) {

            if (pqSlope == ptSlope) {
              Arrays.sort(pointsBySlopeOrder, 0, 5); // sort and reassign using the natural order
              pointsBySlopeOrder[0].drawTo(pointsBySlopeOrder[4]);
              printCollinearPoints(pointsBySlopeOrder, 5);

            } else {
              Arrays.sort(pointsBySlopeOrder, 0, 4); // sort and reassign using the natural order
              pointsBySlopeOrder[0].drawTo(pointsBySlopeOrder[3]);
              printCollinearPoints(pointsBySlopeOrder, 4);
            }

            prevSlope = pqSlope;
          }
        }
      }
    }

    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();
  }
}
