
public class Percolation {

  private WeightedQuickUnionUF wqu;
  private boolean[][] sites;
  private int width;
  private int virtualTopIndex;
  private int virtualBottomIndex;

  // create N-by-N grid, with all sites blocked
  // Requirement: should take time proportional to N^2
  public Percolation(int N) {
    if (N <= 0)
      throw new java.lang.IllegalArgumentException();

    int gridSize = N * N;
    virtualTopIndex = 0;
    virtualBottomIndex = gridSize + 1;
    wqu = new WeightedQuickUnionUF(gridSize + 2); // + 2 to include the virtual sites
    sites = new boolean[N + 1][N + 1]; // ignore the 0th position, start at position 1
    width = N;

    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        sites[i][j] = false;
      }
    }
  }

  private void precondition(int n) {
    if (!isValidSite(n)) {
      throw new java.lang.IndexOutOfBoundsException();
    }
  }

  private boolean isValidSite(int n) {
    return n >= 1 && n <= width;
  }

  private int convert2Dto1D(int row, int column) {
    return (row - 1) * width + column;
  }

  // open site (row i, column j) if it is not open already
  // Requirement: should take constant time plus a constant number of calls
  // to the union-find methods union(), find(), connected(), and count()
  // connect to all adjacent OPEN sites, a few calls to union
  public void open(int i, int j) {
    precondition(i);
    precondition(j);

    // open the site
    if (!sites[i][j])
      sites[i][j] = true;

    int siteIndex = convert2Dto1D(i, j);

    // connect virtual top and bottoms
    if (i == 1)
      wqu.union(virtualTopIndex, siteIndex);
    if (i == width)
      wqu.union(virtualBottomIndex, siteIndex);

    // connect to adjacent sides if adjacent node is valid
    if (isValidSite(i - 1) && isOpen(i - 1, j)) // Top
      wqu.union(siteIndex, convert2Dto1D(i - 1, j));
    if (isValidSite(i + 1) && isOpen(i + 1, j)) // Bottom
      wqu.union(siteIndex, convert2Dto1D(i + 1, j));
    if (isValidSite(j - 1) && isOpen(i, j - 1)) // Left
      wqu.union(siteIndex, convert2Dto1D(i, j - 1));
    if (isValidSite(j + 1) && isOpen(i, j + 1)) // Right
      wqu.union(siteIndex, convert2Dto1D(i, j + 1));
  }

  // is site (row i, column j) open?
  // Requirement: should take constant time plus a constant number of calls
  // to the union-find methods union(), find(), connected(), and count()
  public boolean isOpen(int i, int j) {
    precondition(i);
    precondition(j);

    return sites[i][j];
  }

  // is site (row i, column j) full?
  // Requirement: should take constant time plus a constant number of calls
  // to the union-find methods union(), find(), connected(), and count()
  public boolean isFull(int i, int j) {
    precondition(i);
    precondition(j);

    return wqu.connected(virtualTopIndex, convert2Dto1D(i, j));
  }

  // does the system percolate?
  // Requirement: should take constant time plus a constant number of calls
  // to the union-find methods union(), find(), connected(), and count()
  public boolean percolates() {

    // the virtual top site is connected to all the sites on the top row
    // the virtual bottom site is connected to all the sites on the bottom row
    // if the virtual top site is connected to the virtual bottom site,
    // we say the system percolates
    return wqu.connected(virtualTopIndex, virtualBottomIndex);
  }


  public static void printTest(Percolation p, int i, int j) {
    p.open(i, j);

    StdOut.println("i = " + i + ", j = " + j);
    StdOut.println("isOpen = " + p.isOpen(i, j));
    StdOut.println("isFull = " + p.isFull(i, j));
    StdOut.println("percolates = " + p.percolates());
    StdOut.println("===================");
  }

  public static void main(String[] args) {

//    int N = 10;
//    Percolation p = new Percolation(N);
//
//    printTest(p, 1, 6);
//    printTest(p, 2, 6);
//    printTest(p, 3, 6);
//    printTest(p, 4, 6);
//    printTest(p, 5, 6);
//    printTest(p, 5, 5);
//    printTest(p, 4, 4);
//    printTest(p, 3, 4);
//    printTest(p, 2, 4);
//    printTest(p, 2, 3);
//    printTest(p, 2, 2);
//    printTest(p, 2, 1);
//    printTest(p, 3, 1);
//    printTest(p, 4, 1);
//    printTest(p, 5, 1);
//    printTest(p, 5, 2);
//    printTest(p, 6, 2);
//    printTest(p, 5, 4);

    int N = 3;
    Percolation p = new Percolation(N);

    printTest(p, 1, 6);
    printTest(p, 2, 6);
    printTest(p, 3, 6);
    printTest(p, 4, 6);
  }
}
