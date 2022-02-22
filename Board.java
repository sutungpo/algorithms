/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] tiles;
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
                    continue;
                }
                if (tmp != (dim * i + j + 1)) hamming++;
                manhattan += Math.abs((tmp - 1) / dim - i) + Math.abs((tmp - 1) % dim - j);
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
        return Arrays.deepEquals(tiles, b.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        for (int[] direction : directions) {
            int xx = zeroro + direction[0];
            int yy = zeroco + direction[1];
            if (valid(xx, yy)) {
                int[][] tmp = new int[dim][dim];
                for (int i = 0; i < dim; i++)
                    for (int j = 0; j < dim; j++) {
                        tmp[i][j] = tiles[i][j];
                    }
                tmp[xx][yy] = 0;
                tmp[zeroro][zeroco] = tiles[xx][yy];
                neighbors.add(new Board(tmp));
            }
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] arr = new int[dim][];
        for (int i = 0; i < dim; i++) {
            arr[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
        if (zeroro == 0 && zeroco == 0) {
            arr[0][dim - 1] = tiles[1][dim - 1];
            arr[1][dim - 1] = tiles[0][dim - 1];
        }
        else if (zeroro == 0 && zeroco == dim - 1) {
            arr[0][0] = tiles[1][0];
            arr[1][0] = tiles[0][0];
        }
        else {
            arr[0][0] = tiles[0][dim - 1];
            arr[0][dim - 1] = tiles[0][0];
        }
        return new Board(arr);
    }

    private boolean valid(int x, int y) {
        return x >= 0 && x < dim && y >= 0 && y < dim;
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
