/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {

    private int dim;
    private int times;
    private double[] test;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException();
        }
        dim = n;
        times = trials;
        test = new double[trials];
        for (int i = 0; i < trials; i++)
        {
            test[i] = goPercolate();
        }
    }

    private double goPercolate()
    {
        Percolation perl = new Percolation(dim);
        int row, col;
        while (!perl.percolates())
        {
            row = StdRandom.uniform(1, dim + 1);
            col = StdRandom.uniform(1, dim + 1);
            perl.open(row, col);
        }
        return perl.numberOfOpenSites() / (double) (dim * dim);
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(test);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(test);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - 1.96 * stddev()/ Math.sqrt(times);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + 1.96 * stddev()/ Math.sqrt(times);
    }

    // test client (see below)
    public static void main(String[] args)
    {
        //if(args.length != 2)
        //{
        //    StdOut.println("less arguments!");
        //    return;
        //}
        //PercolationStats mainP = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        //StdOut.println("mean\t="+mainP.mean());
        //StdOut.println("stddev\t="+mainP.stddev());
        //StdOut.println("95% confidence interval\t= ["+mainP.confidenceLo()+", "+mainP.confidenceHi()+"]");
    }

}
