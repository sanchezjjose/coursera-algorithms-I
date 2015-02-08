
public class Percolation {

  private WeightedQuickUnionUF wqu;
  private boolean[][] sites;
  private int virtualTopIndex;
  private int virtualBottomIndex;

  // create N-by-N grid, with all sites blocked
  // Requirement: should take time proportional to N^2
  public Percolation(int N) {
    if (N <= 0)
      throw new java.lang.IllegalArgumentException();

    int gridSize = N * N;
    virtualTopIndex = 1;
    virtualBottomIndex = N;
    wqu = new WeightedQuickUnionUF(gridSize + 2); // + 2 to include the virtual sites
    sites = new boolean[N + 1][N + 1]; // ignore the 0th position, start at position 1

    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        sites[i][j] = false;
      }
    }
  }

  private void precondition(int n) {
    if (n < 1 || n > sites.length) {
      throw new java.lang.IndexOutOfBoundsException();
    }
  }

  private boolean isValidSite(int n) {
    return n > 0 && n <= sites.length;
  }

  private int convertToIndex(int row, int column) {
    int width = sites.length;
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

    int siteIndex = convertToIndex(i, j);

    // connect virtual top and bottoms
    if (i == 1)
      wqu.union(virtualTopIndex, siteIndex);
    else if (i == sites.length)
      wqu.union(virtualBottomIndex, siteIndex);

    // connect to adjacent sides if adjacent side is valid
    if (isValidSite(i - 1) && isOpen(i - 1, j))
      wqu.union(siteIndex, convertToIndex(i - 1, j));
    if (isValidSite(i + 1) && isOpen(i + 1, j))
      wqu.union(siteIndex, convertToIndex(i + 1, j));
    if (isValidSite(j - 1) && isOpen(j - 1, j))
      wqu.union(siteIndex, convertToIndex(j - 1, j));
    if (isValidSite(j + 1) && isOpen(j + 1, j))
      wqu.union(siteIndex, convertToIndex(j + 1, j));
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

    return wqu.connected(virtualTopIndex, convertToIndex(i, j));
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

  public static void main(String[] args) {


  }
}
