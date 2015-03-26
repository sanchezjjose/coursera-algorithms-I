
import java.util.Arrays;

public class Board {

  private int[] board; // array is the board, the indices are the blocks
  private int N; // the width of an array (i.e., a 3x5 array has width 3)
  private int size; // size of the board

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks2D) {
    this.N = blocks2D.length; // blocks2D[0].length;
    this.size = N * N;
    this.board = new int[size];

    int k = 0;

    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        this.board[k++] = blocks2D[i][j];
      }
    }
  }

  private Board(int[] blocks1D, int width) {
    this.N = width;
    this.size = blocks1D.length;
    this.board = new int[size];

    int k = 0;

    for (int i = 0; i < size; i++) {
      this.board[k++] = blocks1D[i];
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
      int blockValue = board[i];

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
      int blockValue = board[i];

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
    Board twinBoard = new Board(this.board, this.N);

    for (int i = 0; i < size; i++) {
      if (twinBoard.board[i] == 0 || i % N == 0 || i % N == N - 1) continue;

      Integer leftBlock = (i - 1 >= 0 && twinBoard.board[i - 1] != 0) ? twinBoard.board[i - 1] : null;
      Integer rightBlock = (i + 1 <= size && twinBoard.board[i + 1] != 0) ? twinBoard.board[i + 1] : null;

      if (leftBlock != null) {
        swap(twinBoard, i, i - 1);
        break;
      } else if (rightBlock != null) {
        swap(twinBoard, i, i + 1);
        break;
      }
    }

    return twinBoard;
  }

  private void swap(Board board, int i, int j) {
    int temp = board.board[i];
    board.board[i] = board.board[j];
    board.board[j] = temp;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    Board that = (Board) y;
    return Arrays.equals(this.board, that.board);
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    Stack<Board> boardStack = new Stack<Board>();
    int zeroIndex = -1;

    for (int i = 0; i < size; i++) {
      if (board[i] == 0) {
        zeroIndex = i;
        break;
      }
    }

    Integer topBlock = (zeroIndex - N >= 0 && board[zeroIndex - N] != 0) ? board[zeroIndex - N] : null;
    Integer bottomBlock = (zeroIndex + N < size && board[zeroIndex + N] != 0) ? board[zeroIndex + N] : null;
    Integer leftBlock = (zeroIndex - 1 >= 0 && board[zeroIndex - 1] != 0) ? board[zeroIndex - 1] : null;
    Integer rightBlock = (zeroIndex + 1 < size && board[zeroIndex + 1] != 0) ? board[zeroIndex + 1] : null;

    if (topBlock != null) {
      Board topBoard = new Board(this.board, this.N);
      swap(topBoard, zeroIndex, zeroIndex - N);
      boardStack.push(topBoard);
    }

    if (bottomBlock != null) {
      Board bottomBoard = new Board(this.board, this.N);
      swap(bottomBoard, zeroIndex, zeroIndex + N);
      boardStack.push(bottomBoard);
    }

    if (leftBlock != null) {
      Board leftBoard = new Board(this.board, this.N);
      swap(leftBoard, zeroIndex, zeroIndex - 1);
      boardStack.push(leftBoard);
    }

    if (rightBlock != null) {
      Board rightBoard = new Board(this.board, this.N);
      swap(rightBoard, zeroIndex, zeroIndex + 1);
      boardStack.push(rightBoard);
    }

    return boardStack;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 1; i <= size; i++) {
      s.append(String.format("%2d ", board[i - 1]));

      if (i % N == 0) {
        s.append("\n");
      }
    }
    return s.toString();
  }

  private Coordinates convert1Dto2D(int index) {
    Coordinates c = null;

    if (index == 0) {
      c = new Coordinates(N-1, N-1);

    } else {
      int k = 1;

      out:
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          if (index == k) {
            c = new Coordinates(i, j);
            break out;
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
            {8,0,3},
            {4,1,2},
            {7,6,5}
    };

    int[][] copyOfInitialBlocks = new int[][] {
            {8,0,3},
            {4,1,2},
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

    Board twinBoard = board.twin();
    System.out.println(twinBoard.toString());

    for (Board neighbor : board.neighbors()) {
      System.out.println(neighbor);
    }
  }
}