import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private class Site {
        private int index;
        private boolean flag;

        public Site(int number) {
            index = number;
            flag = false;
        }

        public int getIndex() {
            return index;
        }

        public void setFlag(boolean status) {
            flag = status;
        }

        public boolean getFlag() {
            return flag;
        }
    }

    private int openSites = 0, firstSite;
    private Site[][] grid;
    private WeightedQuickUnionUF check;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        grid = new Site[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                Site tempSite = new Site(i * n + j + 1);
                grid[i][j] = tempSite;
            }
        }
        check = new WeightedQuickUnionUF(n * n + 2);
        firstSite = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid[0].length)
            throw new IllegalArgumentException();
        if (grid[row - 1][col - 1].getFlag())
            return;
        grid[row - 1][col - 1].setFlag(true);
        if (row == 1)
            check.union(grid[row - 1][col - 1].getIndex(), 0);
        if (row == grid.length)
            check.union(grid[row - 1][col - 1].getIndex(), firstSite);
        if (row != 1) {
            if (grid[row - 2][col - 1].getFlag())
                check.union(grid[row - 2][col - 1].getIndex(), grid[row - 1][col - 1].getIndex());
        }
        if (col != 1) {
            if (grid[row - 1][col - 2].getFlag())
                check.union(grid[row - 1][col - 2].getIndex(), grid[row - 1][col - 1].getIndex());
        }
        if (row != grid.length) {
            if (grid[row][col - 1].getFlag())
                check.union(grid[row][col - 1].getIndex(), grid[row - 1][col - 1].getIndex());
        }
        if (col != grid[row - 1].length) {
            if (grid[row - 1][col].getFlag())
                check.union(grid[row - 1][col].getIndex(), grid[row - 1][col - 1].getIndex());
        }
        ++openSites;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid[0].length)
            throw new IllegalArgumentException();
        return grid[row - 1][col - 1].getFlag();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid[0].length)
            throw new IllegalArgumentException();
        return isOpen(row, col) && check.find(grid[row - 1][col - 1].getIndex()) == check.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return check.find(0) == check.find(firstSite);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}

