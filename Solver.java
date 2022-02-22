/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
    private Iterable<Board> solution;
    private SNode nd;
    private int moves;
    private boolean issolvable;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Solver constructor failed");
        solvetrial(initial);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return issolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (issolvable)
            return moves;
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!issolvable)
            return null;
        return solution;
    }

    private void solvetrial(Board initial) {
        MinPQ<SNode> pq = new MinPQ<SNode>();
        MinPQ<SNode> pqtwin = new MinPQ<SNode>();
        pq.insert(new SNode(null, initial));
        pqtwin.insert(new SNode(null, initial.twin()));
        while (true) {
            if (solvetrialinternal(pq)) {
                issolvable = true;
                break;
            }
            if (solvetrialinternal(pqtwin)) {
                issolvable = false;
                break;
            }
        }
        moves = nd.moves;
        ArrayList<Board> arr = new ArrayList<Board>();
        arr.add(nd.getBd());
        SNode node = nd;
        while (node.prnode != null) {
            arr.add(node.prnode.getBd());
            node = node.prnode;
        }
        Collections.reverse(arr);
        solution = arr;
    }

    private boolean solvetrialinternal(MinPQ<SNode> pq) {
        SNode node = pq.delMin();
        if (node.getBd().isGoal()) {
            nd = node;
            return true;
        }
        Iterable<Board> it = node.getBd().neighbors();
        for (Board bd : it) {
            if (node.prnode == null || !node.prnode.getBd().equals(bd))
                pq.insert(new SNode(node, bd));
        }
        return false;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SNode implements Comparable<SNode> {
        private Board bd;
        private SNode prnode;
        private int moves;

        public SNode(SNode node, Board bd) {
            this.prnode = node;
            this.bd = new Board(bd);
            if (node != null)
                moves = node.moves + 1;
        }

        public Board getBd() {
            return bd;
        }

        public SNode getPrnode() {
            return prnode;
        }

        public int getMoves() {
            return moves;
        }

        public int getPriority() {
            return this.moves + this.bd.manhattan();
        }

        public int compareTo(SNode sNode) {
            return Integer.compare(this.getPriority(), sNode.getPriority());
        }
    }

}
