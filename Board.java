/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board {
    private int[][] tiles;
    private final int dim;
    private int zeroro, zeroco, hamming, manhattan;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dim = tiles.length;
        this.tiles = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int tmp = tiles[i][j];
                this.tiles[i][j] = tmp;
                if (tmp == 0) {
                    zeroro = i;
                    zeroco = j;
                }
                if (tmp == 0)
                    continue;
                if (tmp != (dim * i + j + 1)) hamming++;
                manhattan += Math.abs((tmp - 1) / dim - i) + Math.abs((tmp - 1) % dim - j);
            }
        }
    }

    public Board(Board bd) {
        dim = bd.dim;
        zeroro = bd.zeroro;
        zeroco = bd.zeroco;
        hamming = bd.hamming;
        manhattan = bd.manhattan;
        tiles = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                tiles[i][j] = bd.tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(dim + "\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                str.append(" ");
                str.append(tiles[i][j]);
            }
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return dim;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board b = (Board) y;
        if (this.dim != b.dim) return false;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (this.tiles[i][j] != b.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            public Iterator<Board> iterator() {
                return new BoradIt();
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] arr = new int[dim][];
        for (int i = 0; i < dim; i++) {
            arr[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
        for (int i = 0; i < dim - 1; i++) {
            for (int j = 0; j < dim - 1; j++) {
                if (tiles[i][j] != 0 && tiles[i + 1][j + 1] != 0) {
                    arr[i][j] = tiles[i + 1][j + 1];
                    arr[i + 1][j + 1] = tiles[i][j];
                    return new Board(arr);
                }
            }
        }
        return null;
    }
    private class BoradIt implements Iterator<Board> {
        private short status;
        private short num;

        public BoradIt() {
            if (valid(zeroro - 1)) num++;
            if (valid(zeroro + 1)) num++;
            if (valid(zeroco + 1)) num++;
            if (valid(zeroco - 1)) num++;
        }

        public boolean hasNext() {
            if (num == 0) return false;
            return true;
        }

        public Board next() {
            int[][] arr = new int[dim][];
            for (int i = 0; i < dim; i++) {
                arr[i] = Arrays.copyOf(tiles[i], tiles[i].length);
            }
            switch (status) {
                case 0:
                    if (valid(zeroro - 1)) {
                        arr[zeroro - 1][zeroco] = 0;
                        arr[zeroro][zeroco] = tiles[zeroro - 1][zeroco];
                        status = 1;
                        num--;
                        break;
                    }
                case 1:
                    if (valid(zeroro + 1)) {
                        arr[zeroro + 1][zeroco] = 0;
                        arr[zeroro][zeroco] = tiles[zeroro + 1][zeroco];
                        status = 2;
                        num--;
                        break;
                    }
                case 2:
                    if (valid(zeroco - 1)) {
                        arr[zeroro][zeroco - 1] = 0;
                        arr[zeroro][zeroco] = tiles[zeroro][zeroco - 1];
                        status = 3;
                        num--;
                        break;
                    }
                case 3:
                    if (valid(zeroco + 1)) {
                        arr[zeroro][zeroco + 1] = 0;
                        arr[zeroro][zeroco] = tiles[zeroro][zeroco + 1];
                        status = 4;
                        num--;
                        break;
                    }
                default:
                    throw new NoSuchElementException("no more element in next()");
            }
            return new Board(arr);
        }

        private boolean valid(int i) {
            return i >= 0 && i < dim;
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = {{1, 2, 3}, {6, 5, 4}, {8, 7, 0}};
        Board bd = new Board(a);
        System.out.println(bd);
        System.out.println(bd.hamming());
        System.out.println(bd.manhattan());
        Iterable<Board> it = bd.neighbors();
        for (Board b: it)
            System.out.println(b);
    }
}
