/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private final SET<Point2D> setPo;
    public PointSET()                               // construct an empty set of points
    {
        setPo = new SET<>();
    }
    public boolean isEmpty()                      // is the set empty?
    {
        return setPo.isEmpty();
    }
    public int size()                         // number of points in the set
    {
        return setPo.size();
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new IllegalArgumentException("insert parameter null");
        setPo.add(p);
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null)
            throw new IllegalArgumentException("contains parameter null");
        return setPo.contains(p);
    }
    public void draw()                         // draw all points to standard draw
    {
        for (Point2D pt: setPo) {
            pt.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null)
            throw new IllegalArgumentException("range parameter null");
        Queue<Point2D> queue = new Queue<Point2D>();
        for (Point2D pt:setPo) {
            if (rect.contains(pt))
                queue.enqueue(pt);
        }

        return queue;
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
            throw new IllegalArgumentException("nearset parameter null");
        double min = Double.MAX_VALUE;
        Point2D p1 = null;
        for (Point2D pt: setPo) {
            double dis = p.distanceSquaredTo(pt);
            if (min > dis) {
                min = dis;
                p1 = pt;
            }
        }
        return p1;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        //
    }
}