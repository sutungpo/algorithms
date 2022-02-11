/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] sz;
    private WeightedQuickUnionUF uf;
    private int dim;
    private int countOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException();
        }
        dim = n;
        sz = new boolean[n * n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n * n; i++)
        {
            sz[i] = false;
        }
    }

    private boolean outBorad(int row, int col)
    {
        if (row < 1 || row > dim || col < 1 || col > dim)
        {
            return true;
        }
        return false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (outBorad(row, col))
        {
            throw new IllegalArgumentException();
        }

        if (sz[(row - 1) * dim + col - 1])
        {
            return;
        }
        sz[(row - 1) * dim + col - 1] = true;
        countOpen++;

        if (row == 1)
        {
            uf.union(0, col);
        }
        if (row == dim)
        {
            uf.union(dim * dim + 1, (row - 1) * dim + col);
        }
        if (!outBorad(row - 1, col) && isOpen(row - 1, col))
        {
            uf.union((row - 1) * dim + col, (row - 2) * dim + col);
        }
        if (!outBorad(row + 1, col) && isOpen(row + 1, col))
        {
            uf.union((row - 1) * dim + col, row * dim + col);
        }
        if (!outBorad(row, col - 1) && isOpen(row, col - 1))
        {
            uf.union((row - 1) * dim + col, (row - 1) * dim + col - 1);
        }
        if (!outBorad(row, col + 1) && isOpen(row, col + 1))
        {
            uf.union((row - 1) * dim + col, (row - 1) * dim + col + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (outBorad(row, col))
        {
            throw new IllegalArgumentException();
        }
        return sz[(row - 1) * dim + col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (outBorad(row, col))
        {
            throw new IllegalArgumentException();
        }
        if (uf.find((row - 1) * dim + col) == uf.find(0))
        {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates()
    {
        if (uf.find(0) == uf.find(dim * dim + 1))
        {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args)
    {
        //int num = 4;
        //Percolation peco = new Percolation(num);
        //peco.open(1, 2);
        //peco.open(2, 2);
        //peco.open(2, 3);
        //peco.open(3, 3);
        //peco.open(3, 4);
        //peco.open(4, 4);
        //System.out.println(peco.percolates());
    }
}
