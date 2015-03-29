
import java.util.Arrays;

public class Board {

  private int[] board; // array is the board, the indices are the blocks
  private int N; // the width of an array (i.e., a 3x5 array has width 3)
  private int size; // size of the board

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks2D) {
    this.N = blocks2D.length;
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
      if (twinBoard.board[i] == 0) continue;

      int leftBlockIndex = i - 1;
      int rightBlockIndex = i + 1;

      if (leftBlockIndex >= 0 && leftBlockIndex % N != N - 1 && twinBoard.board[leftBlockIndex] != 0) { // LEFT
        swap(twinBoard, i, leftBlockIndex);
        break;
      } else if (rightBlockIndex < size && rightBlockIndex % N != 0 && twinBoard.board[rightBlockIndex] != 0) { // RIGHT
        swap(twinBoard, i, rightBlockIndex);
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
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null)
      return false;

    if (o.getClass() != this.getClass())
      return false;

    Board that = (Board) o;

    if (this.dimension() != that.dimension())
      return false;

    if (Arrays.equals(this.board, that.board))
      return true;

    return false;
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

    int topIndex = zeroIndex - N;
    int bottomIndex = zeroIndex + N;
    int leftBlockIndex = zeroIndex - 1;
    int rightBlockIndex = zeroIndex + 1;

    if (topIndex >= 0 && board[topIndex] != 0) { // TOP
      Board topBoard = new Board(this.board,  this.N);
      swap(topBoard, zeroIndex, topIndex);
      boardStack.push(topBoard);
    }

    if (bottomIndex < size && board[bottomIndex] != 0) { // BOTTOM
      Board bottomBoard = new Board(this.board, this.N);
      swap(bottomBoard, zeroIndex, bottomIndex);
      boardStack.push(bottomBoard);
    }

    if (leftBlockIndex >= 0 && leftBlockIndex % N != N - 1 && board[leftBlockIndex] != 0) { // LEFT
      Board leftBoard = new Board(this.board, this.N);
      swap(leftBoard, zeroIndex, leftBlockIndex);
      boardStack.push(leftBoard);
    }

    if (rightBlockIndex < size && rightBlockIndex % N != 0 && board[rightBlockIndex] != 0) { // RIGHT
      Board rightBoard = new Board(this.board, this.N);
      swap(rightBoard, zeroIndex, rightBlockIndex);
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

    /***********
       {8,0,3},
       {4,1,2},
       {7,6,5}
     ***********/
    int[][] initialBlocks = new int[][] {{8, 0, 3}, {4, 1, 2}, {7, 6, 5}};

    /***********
       {8,0,3},
       {4,1,2},
       {7,6,5}
     ***********/
    int[][] copyOfInitialBlocks = new int[][] {{8, 0, 3}, {4, 1, 2}, {7, 6, 5}};

    /***********
       {1,2,3},
       {4,5,6},
       {7,8,0}
     ***********/
    int[][] goalBlocks = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

    /***********
       {1,0},
       {2,3}
     ***********/
    int[][] unsolvableBlocks = new int[][] {{1, 0}, {2, 3}};

    Board board = new Board(initialBlocks);
    Board boardCopy = new Board(copyOfInitialBlocks);
    Board goalBoard = new Board(goalBlocks);
    Board unsolvableBoard = new Board(unsolvableBlocks);

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

    System.out.println(unsolvableBoard.twin().toString());
    System.out.println(unsolvableBoard.neighbors());
  }
}