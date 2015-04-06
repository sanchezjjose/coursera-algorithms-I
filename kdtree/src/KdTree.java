import java.util.HashSet;
import java.util.Set;

public class KdTree {

  private Node root;
  private Point2D nearest;
  private int size = 0;

  private static final boolean HORIZONTAL = true;

  private static class Node {
    private Point2D point; // the point
    private RectHV rect; // the axis-aligned rectangle corresponding to this node
    private Node lb; // the left/bottom subtree
    private Node rt; // the right/top subtree

    public Node(Point2D p, RectHV rect) {
      this.point = p;
      this.rect = rect;
    }
  }

  // is the set empty?
  public boolean isEmpty() {
    return root == null;
  }

  // number of points in the set
  public int size() {
    return size;
  }

  // add the point p to the set (if it is not already in the set)
  public void insert(Point2D p) {
    root = insert(root, null, p, HORIZONTAL);
  }

  private Node insert(Node node, Node parentNode, Point2D point, boolean orientHorizontally) {

    if (node == null) {

      double xmin = 0.0;
      double ymin = 0.0;
      double xmax = 1.0;
      double ymax = 1.0;

      if (parentNode != null) {

        if (orientHorizontally) {
          xmin = parentNode.rect.xmin();
          xmax = parentNode.rect.xmax();

          if (point.y() < parentNode.point.y()) {
            ymin = parentNode.rect.ymin();
            ymax = parentNode.point.y();

          } else if (point.y() > parentNode.point.y()) {
            ymin = parentNode.point.y();
            ymax = parentNode.rect.ymax();
          }

        } else {
          ymax = parentNode.rect.ymax();
          ymin = parentNode.rect.ymin();

          if (point.x() < parentNode.point.x()) {
            xmin = parentNode.rect.xmin();
            xmax = parentNode.point.x();

          } else if (point.x() > parentNode.point.x()) {
            xmin = parentNode.point.x();
            xmax = parentNode.rect.xmax();
          }
        }
      }

      size++;

      return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
    }

    double newKey = orientHorizontally ? point.x() : point.y();
    double nodeKey = orientHorizontally ? node.point.x() : node.point.y();

    if (newKey < nodeKey) {
      node.lb = insert(node.lb, node, point, !orientHorizontally);

    } else if (newKey > nodeKey) {
      node.rt = insert(node.rt, node, point, !orientHorizontally);

    } else {
      double newValue = orientHorizontally ? point.y() : point.x();
      double nodeValue = orientHorizontally ? node.point.y() : node.point.x();

      if (newValue == nodeValue) {
        node.point = point;

      } else {
        node.rt = insert(node.rt, node, point, !orientHorizontally);
      }
    }

    return node;
  }

  // does the set contain the point p?
  public boolean contains(Point2D p) {
    return get(root, p, HORIZONTAL) != null;
  }

  private Point2D get(Node node, Point2D point, boolean orientHorizontally) {

    if (node == null) {
      return null;
    }

    double newKey = orientHorizontally ? point.x() : point.y();
    double nodeKey = orientHorizontally ? node.point.x() : node.point.y();

    if (newKey < nodeKey) {
      return get(node.lb, point, !orientHorizontally);

    } else if (newKey > nodeKey) {
      return get(node.rt, point, !orientHorizontally);

    } else {
      double newValue = orientHorizontally ? point.y() : point.x();
      double nodeValue = orientHorizontally ? node.point.y() : node.point.x();

      if (newValue == nodeValue) {
        return node.point;

      } else {
        return get(node.rt, point, !orientHorizontally);
      }
    }
  }

  // draw all of the points to standard draw
  public void draw() {
    draw(root, null, HORIZONTAL);
  }

  private void draw(Node node, Node parentNode, boolean orientHorizontally) {

    if (node == null) {
      return;
    }

    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);

    node.point.draw();

    StdDraw.setPenRadius();
    StdDraw.setPenColor(orientHorizontally ? StdDraw.RED : StdDraw.BLUE);

    if (orientHorizontally) {

      if (parentNode != null) {

        if (node.point.y() < parentNode.point.y()) {
          Point2D p1 = new Point2D(node.point.x(), 0.0);
          Point2D p2 = new Point2D(node.point.x(), parentNode.point.y());

          p1.drawTo(p2);

        } else if (node.point.y() > parentNode.point.y()) {
          Point2D p1 = new Point2D(node.point.x(), parentNode.point.y());
          Point2D p2 = new Point2D(node.point.x(), 1.0);

          p1.drawTo(p2);
        }

      } else {
        Point2D p1 = new Point2D(node.point.x(), 0.0);
        Point2D p2 = new Point2D(node.point.x(), 1.0);

        p1.drawTo(p2);
      }

    } else {

      if (parentNode != null) {

        if (node.point.x() < parentNode.point.x()) {
          Point2D p1 = new Point2D(0.0, node.point.y());
          Point2D p2 = new Point2D(parentNode.point.x(), node.point.y());

          p1.drawTo(p2);

        } else if (node.point.x() > parentNode.point.x()) {
          Point2D p1 = new Point2D(parentNode.point.x(), node.point.y());
          Point2D p2 = new Point2D(1.0, node.point.y());

          p1.drawTo(p2);
        }

      } else {
        Point2D p1 = new Point2D(0.0, node.point.y());
        Point2D p2 = new Point2D(1.0, node.point.y());

        p1.drawTo(p2);
      }
    }

    draw(node.lb, node, !orientHorizontally);
    draw(node.rt, node, !orientHorizontally);
  }

  // all points in the set that are inside the rectangle
  public Iterable<Point2D> range(RectHV rect) {
    Set<Point2D> result = new HashSet<Point2D>();

    range(root, rect, result);

    return result;
  }

  private void range(Node node, RectHV rect, Set<Point2D> result) {

    if (node == null || !rect.intersects(node.rect)) {
      return;
    }

    if (rect.contains(node.point)) {
      result.add(node.point);
    }

    range(node.lb, rect, result);
    range(node.rt, rect, result);
  }

  // a nearest neighbor in the set to p; null if set is empty
  public Point2D nearest(Point2D p) {
    nearest = root.point;

    nearest(root, p, HORIZONTAL);

    return nearest;
  }

  private void nearest(Node node, Point2D queryPoint, boolean orientHorizontally) {

    if (node == null) {
      return;
    }

    double currentNearestDistanceToQueryPoint = nearest.distanceSquaredTo(queryPoint);
    double rectDistanceToQueryPoint = node.rect.distanceSquaredTo(queryPoint);

    if (currentNearestDistanceToQueryPoint >= rectDistanceToQueryPoint) {

      if (currentNearestDistanceToQueryPoint > node.point.distanceSquaredTo(queryPoint)) {
        nearest = node.point;
      }

      Node firstNode;
      Node secondNode;

      if (orientHorizontally) {

        if (queryPoint.x() < node.point.x()) {
          firstNode = node.lb;
          secondNode = node.rt;

        } else {
          firstNode = node.rt;
          secondNode = node.lb;
        }

      } else {

        if (queryPoint.y() < node.point.y()) {
          firstNode = node.lb;
          secondNode = node.rt;

        } else {
          firstNode = node.rt;
          secondNode = node.lb;
        }
      }

      nearest(firstNode, queryPoint, !orientHorizontally);
      nearest(secondNode, queryPoint, !orientHorizontally);
    }
  }
}