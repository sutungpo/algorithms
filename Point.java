/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;
    public Point(int x, int y)                     // constructs the point (x, y)
    {
        this.x = x;
        this.y = y;
    }
    public void draw()                               // draws this point
    {
        StdDraw.point(x, y);
    }
    public void drawTo(Point that)                   // draws the line segment from this point to that point
    {
        StdDraw.line(x, y, that.x, that.y);
    }
    public String toString()                           // string representation
    {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
    {
        if (y < that.y || (y == that.y && x < that.x))
            return -1;
        else if (x == that.x && y == that.y)
            return 0;
        else
            return +1;
    }
    public double slopeTo(Point that)       // the slope between this point and that point
    {
        if (x != that.x) {
            if (y == that.y)
                return +0.0;
            else
                return (double) (that.y - y) / (that.x - x);
        }
        else {
            if (y == that.y)
                return Double.NEGATIVE_INFINITY;
            else
                return Double.POSITIVE_INFINITY;
        }
    }
    public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
    {
        return new Comparator<Point>() {
            public int compare(Point point, Point t1) {
                double slop1 = Point.this.slopeTo(point);
                double slop2 = Point.this.slopeTo(t1);
                return Double.compare(slop1, slop2);
            }
        };
    }
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        System.out.println("p1.compareTo(p2)=" + p1.compareTo(p2));
        System.out.println("p1.slopeTo(p2)=" + p1.slopeTo(p2));
        Point p3 = new Point(0, 4);
        System.out.println("p1.slopeTo(p3)=" + p1.slopeTo(p3));
        Point p4 = new Point(4, 4);
        System.out.println("p3.compareTo(p4)=" + p3.compareTo(p4));
        System.out.println("p3.slopeTo(p4)=" + p3.slopeTo(p4));
        Point p5 = new Point(0, 0);
        System.out.println("p1.slopeTo(p5)=" + p1.slopeTo(p5));
    }

}
