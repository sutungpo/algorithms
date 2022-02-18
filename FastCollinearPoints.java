/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
    private final ArrayList<Point> arr;
    private final ArrayList<LineSegment> linearr;
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null)
            throw new IllegalArgumentException("FastCollinearPoints construct");
        arr = new ArrayList<Point>();
        for (Point p:points) {
            if (p == null)
                throw new IllegalArgumentException("array element null");
            if (!arr.contains(p))
                arr.add(p);
            else
                throw new IllegalArgumentException("repeated point");
        }
        Collections.sort(arr);
        linearr = new ArrayList<LineSegment>();
        checkcollinear();
    }
    public int numberOfSegments()        // the number of line segments
    {
        return linearr.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        return linearr.toArray(new LineSegment[0]);
    }

    private void checkcollinear()
    {
        int sz = arr.size();
        PointAs[] darr = new PointAs[sz - 1];
        for (int i = 0; i < sz - 3; i++) {
            Point pi = arr.get(i);
            int len = 0;
            for (int k = 0; k < sz; k++) {
                if (k == i)
                    continue;
                Point pk = arr.get(k);
                double slop = pi.slopeTo(pk);
                darr[len++] = new PointAs(k, slop);
            }
            sort(darr);
            int repeatcount = -1, m = -1;
            for (int j = 0; j < darr.length - 1; j++) {
                if (darr[j].compareTo(darr[j + 1]) == 0) {
                    if (m == -1) {
                        repeatcount = 2;
                        m = darr[j].getSeq();
                    }
                    else
                        repeatcount++;
                    if (j == darr.length -2 && repeatcount > 2 && arr.get(i).compareTo(arr.get(m)) < 0)
                        linearr.add(new LineSegment(pi, arr.get(darr[j + 1].getSeq())));
                }
                else {
                    if (m != -1 && repeatcount > 2 && arr.get(i).compareTo(arr.get(m)) < 0)
                        linearr.add(new LineSegment(pi, arr.get(darr[j].getSeq())));
                    m = -1;
                    repeatcount = -1;
                }
            }
        }
    }

    private static <T extends Comparable<? super T>> void sort(T[] a)
    {
        T[] aux = Arrays.copyOf(a, a.length);
        sort(a, aux, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, T[] aux, int lo, int hi)
    {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux,lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);

    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] aux, int lo, int mid, int hi)
    {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

class PointAs implements Comparable<PointAs> {
    private final int seq;
    private final double  p;

    PointAs(int n, double p) {
        this.seq = n;
        this.p = p;
    }

    public int compareTo(PointAs pa) {
        return Double.compare(p, pa.p);
    }

    public int getSeq()
    {
        return this.seq;
    }

    public double getP()
    {
        return this.p;
    }

    public String toString()
    {
        return "(" + seq + ", " + p + ")";
    }
}
