import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        results = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                perc.open(x, y);
            }
            results[i] = perc.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(results) - (1.96 * StdStats.stddev(results)) / Math.sqrt(
                results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(results) + (1.96 * StdStats.stddev(results)) / Math.sqrt(
                results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + ']');
    }
}
