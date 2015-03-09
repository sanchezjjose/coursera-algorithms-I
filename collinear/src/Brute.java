import java.util.Arrays;

/**
 * Brute force. Write a program Brute.java that examines 4 points at a time and checks whether they all lie
 * on the same line segment, printing out any such line segments to standard output and drawing them using
 * standard drawing. To check whether the 4 points p, q, r, and s are collinear, check whether the slopes
 * between p and q, between p and r, and between p and s are all equal.
 * The order of growth of the running time of your program should be N4 in the worst case and it should use space proportional to N.
 */
public class Brute {

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

  public static void main(String args[]) {

    // rescale the coordinate system
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.setPenRadius(0.01);

    Point[] points = coordinatesToPoints(args);
    Arrays.sort(points); // sort using it's natural order

    for (int i = 0; i < points.length; i++) {
      Point p = points[i];

      for (int j = i + 1; j < points.length; j++) {
        Point q = points[j];
        double pqSlope = p.slopeTo(q);

        for (int k = j + 1; k < points.length; k++) {
          Point r = points[k];
          double prSlope = p.slopeTo(r);

          for (int l = k + 1; l < points.length; l++) {
            Point s = points[l];
            double psSlope = p.slopeTo(s);

            if (pqSlope == prSlope && pqSlope == psSlope) {
              p.drawTo(s); // plot from the min point to the max point

              StdOut.println(
                String.format("%s -> %s -> %s -> %s", p, q, r, s)
              );
            }
          }
        }
      }
    }
  }
}
