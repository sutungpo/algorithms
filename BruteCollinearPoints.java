/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class BruteCollinearPoints {
    private final List<Point> arrpo;
    private final List<LineSegment> lseg;
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
            throw new IllegalArgumentException();
        arrpo = new ArrayList<Point>();
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException();
            if (!arrpo.contains(p))
                arrpo.add(p);
        }
        if (arrpo.size() != points.length)
            throw new IllegalArgumentException();
        Collections.sort(arrpo);
        lseg = new ArrayList<LineSegment>();
        checkcollinear();
    }
    public int numberOfSegments()        // the number of line segments
    {
        return lseg.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        return lseg.toArray(new LineSegment[0]);
    }
    private void checkcollinear()
    {
        int sz = arrpo.size();
        for (int i = 0; i < sz - 3; i++) {
            Comparator<Point> comparator = arrpo.get(i).slopeOrder();
            for (int j = i + 1; j < sz - 2; j++) {
                for (int k = j + 1; k < sz - 1; k++) {
                    if (comparator.compare(arrpo.get(j), arrpo.get(k)) != 0)
                        continue;
                    for (int m = k + 1; m < sz; m++) {
                        if (comparator.compare(arrpo.get(k), arrpo.get(m)) == 0) {
                            lseg.add(new LineSegment(arrpo.get(i), arrpo.get(m)));
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        In in = new In("input8.txt"); //本地测试使用
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
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
