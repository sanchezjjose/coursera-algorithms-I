
import java.util.Arrays;

public class Board {

  private int[] blocks1D;
  private int N;
  private int size;
  private int width; // the width of an array (i.e., a 3x5 array has width 3)

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks2D) {

    this.N = blocks2D.length;
    this.size = N * N;
    this.width = blocks2D[0].length;
    this.blocks1D = new int[size];

    int k = 0;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        this.blocks1D[k++] = blocks2D[i][j];
      }
    }
  }

  // board dimension N
  public int dimension() {
    return N;
  }

  // number of blocks out of place
  public int hamming() {
    int distance = 0;

    for (int i = 0; i < size; i++) {
      int blockIndex = i + 1;
      int blockValue = blocks1D[i];

      if (blockValue != 0 && blockValue != blockIndex) {
        distance++;
      }
    }

    return distance;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() {
    int distance = 0;

    for (int i = 0; i < size; i++) {
      int blockIndex = i + 1;
      int blockValue = blocks1D[i];

      if (blockValue != 0 && blockValue != blockIndex) {
        Coordinates wrongPos = convert1Dto2D(blockIndex);
        Coordinates rightPos = convert1Dto2D(blockValue);

        int horizontalDistance = wrongPos.column - rightPos.column;
        int verticalDistance = wrongPos.row - rightPos.row;

        // sum of the vertical and horizontal distance
        distance += Math.abs(verticalDistance) + Math.abs(horizontalDistance);
      }
    }

    return distance;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return hamming() == 0;
  }

  // a board that is obtained by exchanging two adjacent blocks in the same row
  public Board twin() {
    return null;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    Board that = (Board) y;
    return Arrays.equals(this.blocks1D, that.blocks1D);
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    return null;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 1; i <= size; i++) {
      s.append(String.format("%2d ", blocks1D[i - 1]));

      if (i % N == 0) {
        s.append("\n");
      }
    }
    return s.toString();
  }

  private int convert2Dto1D(int row, int column) {
    return (row - 1) * width + column;
  }

  private Coordinates convert1Dto2D(int index) {
    Coordinates c = null;

    if (index == 0) {
      c = new Coordinates(N-1, N-1);

    } else {
//      int row = Math.abs(index / width);
//      int col = Math.abs(index % width);

      int k = 1;

      // TODO: use equation (above) rather then traversal method
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < width; j++) {
          if (index == k) {
            c = new Coordinates(i, j);
          }
          k++;
        }
      }
    }

    return c;
  }

  private class Coordinates {
    private int row;
    private int column;

    public Coordinates(int row, int column) {
      this.row = row;
      this.column = column;
    }
  }

  // unit tests (not graded)
  public static void main(String[] args) {

    int[][] initialBlocks = new int[][] {
            {8,1,3},
            {4,0,2},
            {7,6,5}
    };

    int[][] copyOfInitialBlocks = new int[][] {
            {8,1,3},
            {4,0,2},
            {7,6,5}
    };

    int[][] goalBlocks = new int[][] {
            {1,2,3},
            {4,5,6},
            {7,8,0}
    };

    Board board = new Board(initialBlocks);
    Board boardCopy = new Board(copyOfInitialBlocks);
    Board goalBoard = new Board(goalBlocks);

    System.out.println(board.toString());

    System.out.println(board.hamming());
    System.out.println(board.manhattan());

    System.out.println(goalBoard.hamming());
    System.out.println(goalBoard.manhattan());

    System.out.println(board.equals(boardCopy));

    System.out.println(board.isGoal());
    System.out.println(goalBoard.isGoal());
  }
}