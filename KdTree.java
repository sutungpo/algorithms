/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

public class KdTree {
    private Node root;
    private Point2D pn;
    private int depthtotal;
    private final RectHV rectmax = new RectHV(0, 0, 1, 1);
    public KdTree() {

    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public int size() {
        return size(root);
    }
    private int size(Node node) {
        if (node == null)
            return 0;
        else
            return node.size;
    }
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null p");
        root = put(root, p, rectmax, 0);
    }
    private Node put(Node node, Point2D p, RectHV rect, int depth) {
        if (depthtotal < depth)
            depthtotal = depth;
        if (node == null)
            return new Node(p, rect, depth, 1);
        if (p.equals(node.p))
            return node;
        int cmp = cmpTo(node, p);
        if (cmp > 0)
            node.left = put(node.left, p, node.rectleft, depth + 1);
        else
            node.right = put(node.right, p, node.rectright, depth + 1);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("argument null to contaims()");
        return getKey(root, p) != null;
    }
    private Node getKey(Node node, Point2D p) {
        if (node == null)
            return null;
        if (node.p.equals(p))
            return node;
        int cmp = cmpTo(node, p);
        if (cmp > 0) return getKey(node.left, p);
        else return getKey(node.right, p);
    }
    // arg node can not null;
    private int cmpTo(Node node, Point2D p) {
        int dimx = 0, dimy = 0;
        if (node.cutdim == 0)
            dimx = 1;
        else if (node.cutdim == 1)
            dimy = 1;
        double cmp = dimx * (node.p.x() - p.x()) + dimy * (node.p.y() - p.y());
        return Double.compare(cmp, 0);
    }

    public void draw() {
        drawNode(root);
    }
    private void drawNode(Node n) {
        if (n != null) {
            n.nodeDraw();
            drawNode(n.left);
            drawNode(n.right);
        }
    }
    private void visitrange(Node node, RectHV rect, Queue<Point2D> qu) {
        if (node == null)
            return;
        if (!node.rect.intersects(rect))
            return;
        if (rect.contains(node.p))
            qu.enqueue(node.p);
        visitrange(node.left, rect, qu);
        visitrange(node.right, rect, qu);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("args in range failed");
        Queue<Point2D> qu = new Queue<Point2D>();
        visitrange(root, rect, qu);
        return qu;
    }
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("args in nearest failed");
        double distance = Double.MAX_VALUE;
        visitnearest(root, p, distance);
        return pn;
    }
    private double visitnearest(Node node, Point2D p, double distance) {
        if (node == null)
            return -1;
        if (Double.compare(node.rect.distanceSquaredTo(p), distance) > 0)
            return -1;
        double dis = p.distanceSquaredTo(node.p);
        if (Double.compare(dis, distance) < 0) {
            distance = dis;
            pn = node.p;
        }
        int cmp = cmpTo(node, p);
        Node nodenew = null, nodenext = null;
        if (cmp <= 0) {
            nodenew = node.right;
            nodenext = node.left;
        }
        else {
            nodenew = node.left;
            nodenext = node.right;
        }
        dis = visitnearest(nodenew, p, distance);
        if (Double.compare(dis, -1) != 0 && Double.compare(distance, dis) > 0)
                distance = dis;
        if (nodenext != null && Double.compare(nodenext.rect.distanceSquaredTo(p), distance) > 0)
            return distance;
        dis = visitnearest(nodenext, p, distance);
        if (Double.compare(dis, -1) != 0 && Double.compare(distance, dis) > 0)
            distance = dis;
        return distance;
    }
    private void visit(Node node) {
        if (node == null)
            return;
        System.out.println(node.p);
        visit(node.left);
        visit(node.right);
    }

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        RectHV rec = new RectHV(0.125, 0.875,0.75, 1.0);
        for (Point2D p:kdtree.range(rec))
            System.out.println(p);
    }

    private class Node {
        private final Point2D p;
        private final int cutdim;
        private final RectHV rect;
        private final int dep;
        private RectHV rectleft, rectright;
        private Node left, right;
        private int size;

        public Node (Point2D p, RectHV rect, int depth, int size) {
            this.p = p;
            this.rect = rect;
            dep = depth;
            cutdim = depth % 2;
            this.size = size;
            if (rect != null) {
                if (cutdim == 0) {
                    rectleft = new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
                    rectright = new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
                }
                else if ( cutdim == 1) {
                    rectleft = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
                    rectright = new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
                }
            }
        }

        private void nodeDraw() {
            StdDraw.setPenRadius(0.008);
            p.draw();
            StdDraw.setPenRadius();
            if (cutdim == 0) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
            }
            else if (cutdim == 1) {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
            }
            StdDraw.setPenColor();
        }
    }
}
