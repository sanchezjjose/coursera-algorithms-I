import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

  /**
   * TreeSet:
   *
   * A NavigableSet implementation based on a TreeMap. The elements are ordered using their natural ordering,
   * or by a Comparator provided at set creation time, depending on which constructor is used.
   *
   * This implementation provides guaranteed log(n) time cost for the basic operations (add, remove and contains).
   *
   * Allows implementation fo red-black BST.
   */
  private TreeSet<Point2D> points;

  // construct an empty set of points
  public PointSET() {
    points = new TreeSet<Point2D>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return points.isEmpty();
  }

  // number of points in the set
  public int size() {
    return points.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    validate(p);

    points.add(p);
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    validate(p);

    return points.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    for (Point2D p : points){
      p.draw();
    }
  }

  // all points that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    validate(rect);

    List<Point2D> pointsInRect = new LinkedList<Point2D>();

    for (Point2D p : points) {
      if (rect.contains(p)) {
        pointsInRect.add(p);
      }
    }

    return pointsInRect;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    validate(p);

    Point2D nearestPoint = null;
    Double nearestDistance = Double.POSITIVE_INFINITY;

    for (Point2D point : points) {
      Double currentDistance = point.distanceTo(p);

      if (currentDistance < nearestDistance) {
        nearestDistance = currentDistance;
        nearestPoint = point;
      }
    }

    return nearestPoint;
  }

  private void validate(Object o) {
    if (o == null) {
      throw new java.lang.NullPointerException();
    }
  }

  public static void main(String[] args) {

    TreeSet<String> test1 = new TreeSet<String>();

    test1.add("Hello");
    test1.add("World");
    test1.add("My");
    test1.add("Name");
    test1.add("Is");
    test1.add("Jose");
    test1.add("Jose");

    String firstWord = test1.first();
    String lastWord = test1.last();

    System.out.println(firstWord);
    System.out.println(lastWord);
  }
}