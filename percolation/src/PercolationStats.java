
public class PercolationStats {

  private double[] percolationThreshold;
  private int totalExperiments; // number of experiments

  // perform T independent experiments on an N-by-N grid
  public PercolationStats(int N, int T) {
    if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();

    this.percolationThreshold = new double[T];
    this.totalExperiments = T;

    double totalOpenSites = 0.0;
    int gridSize = N * N;

    for (int i = 0; i < T; i++) {
      Percolation p = new Percolation(N);

      // randomly open sites until it percolates
      while (!p.percolates()) {
        int siteRow = StdRandom.uniform(1, N);
        int siteColumn = StdRandom.uniform(1, N);

        if (!p.isOpen(siteRow, siteColumn)) {
          p.open(siteRow, siteColumn);
          totalOpenSites++;
        }
      }

      percolationThreshold[i] = (totalOpenSites / gridSize) / totalExperiments;
      totalOpenSites = 0; // reset
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(percolationThreshold);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(percolationThreshold);
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - (1.96 * stddev() / Math.sqrt(totalExperiments));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + (1.96 * stddev() / Math.sqrt(totalExperiments));
  }

  // test client (described below)
  public static void main(String[] args) {

    int N = 4;
    int T = 10;

    PercolationStats pStats = new PercolationStats(N, T);

    StdOut.println("mean                        = " + pStats.mean());
    StdOut.println("stddev                      = " + pStats.mean());
    StdOut.println("95% confidence interval     = " + pStats.confidenceLo() + ", " + pStats.confidenceHi());
  }
}
