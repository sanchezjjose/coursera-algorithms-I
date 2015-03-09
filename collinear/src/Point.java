/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

  // compare points by slope
  public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

  private final int x;                              // x coordinate
  private final int y;                              // y coordinate

  // create the point (x, y)
  public Point(int x, int y) {
        /* DO NOT MODIFY */
    this.x = x;
    this.y = y;
  }

  private class SlopeOrder implements Comparator<Point> {

    @Override
    public int compare(Point p1, Point p2) {
      /*
       * See: http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
       *
       * The SLOPE_ORDER comparator should compare points by the slopes they make with the invoking point (x0, y0).
       * Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0)
       * is less than the slope (y2 − y0) / (x2 − x0). Treat horizontal, vertical,
       * and degenerate line segments as in the slopeTo() method.
       */
      double slopeP1 = slopeTo(p1);
      double slopeP2 = slopeTo(p2);

      if (slopeP1 < slopeP2) {
        return -1;
      }

      if (slopeP1 > slopeP2) {
        return 1;
      }

      return 0;
    }
  }

  // plot this point to standard drawing
  public void draw() {
        /* DO NOT MODIFY */
    StdDraw.point(x, y);
  }

  // draw line between this point and that point to standard drawing
  public void drawTo(Point that) {
        /* DO NOT MODIFY */
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  // slope between this point and that point
  public double slopeTo(Point that) {
    double yDiff = that.y - Point.this.y;
    double xDiff = that.x - Point.this.x;

    // degenerate line segment (between a point and itself)
    if (yDiff == 0.0 && xDiff == 0.0) {
      return Double.NEGATIVE_INFINITY;
    }

    // vertical line segment
    if (xDiff == 0.0) {
      return Double.POSITIVE_INFINITY;
    }

    // horizontal line segment
    if (yDiff == 0.0) {
      return 0.0;
    }

    return yDiff / xDiff;
  }

  // is this point lexicographically smaller than that one?
  // comparing y-coordinates and breaking ties by x-coordinates
  public int compareTo(Point that) {
    if (this.y > that.y) {
      return 1;
    } else if (this.y < that.y) {
      return -1;
    } else if (this.y == that.y) {
      if (this.x < that.x) {
        return -1;
      } else if (this.x > that.x) {
        return 1;
      }
    }

    return 0;
  }

  // return string representation of this point
  public String toString() {
        /* DO NOT MODIFY */
    return "(" + x + ", " + y + ")";
  }

  // unit test
  public static void main(String[] args) {

    Point p1 = new Point(3, 2);
    Point p2 = new Point(4, 5);
    Point p3 = new Point(7, 9);

    System.out.println(p1.slopeTo(p2)); // 3.0
    System.out.println(p2.slopeTo(p1)); // 3.0
    System.out.println(p2.slopeTo(p3)); // 1.333333....
    System.out.println(p3.slopeTo(p2)); // 1.333333....
    System.out.println(p1.slopeTo(p1)); // 0.0

    System.out.println(p1.compareTo(p2)); // -3
    System.out.println(p2.compareTo(p1)); // 3
    System.out.println(p1.compareTo(p1)); // 0
    System.out.println(p3.compareTo(p1)); // 7
    System.out.println(p1.compareTo(p3)); // -7

    Point[] points = new Point[]{p3, p1, p2};
    Point[] pointsSorted = Arrays.copyOf(points, points.length);

    for (int i = 0; i < points.length; i++) {
      Arrays.sort(pointsSorted, points[i].SLOPE_ORDER);

      for (int j = 0; j < pointsSorted.length; j++)
        System.out.println(pointsSorted[j]);
    }

  }
}