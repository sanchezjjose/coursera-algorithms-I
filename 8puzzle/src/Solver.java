
public class Solver {

  private SearchNode goalNode;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {

    MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
    minPQ.insert(new SearchNode(initial, null, 0));
    SearchNode currentNode = minPQ.delMin();

    MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
    minPQTwin.insert(new SearchNode(initial.twin(), null, 0));
    SearchNode currentNodeTwin = minPQTwin.delMin();

    while (!(currentNode.getBoard().isGoal()
            || currentNodeTwin.getBoard().isGoal())) {

      // Initial
      for (Board neighbor : currentNode.getBoard().neighbors()) {
        if (currentNode.getPreviousNode() != null && neighbor.equals(currentNode.getPreviousNode().getBoard())) {
          continue;
        }

        minPQ.insert(new SearchNode(neighbor, currentNode, currentNode.getMoves() + 1));
      }

      if (minPQ.size() > 0) {
        currentNode = minPQ.delMin();
      }

      // Twin
      for (Board neighbor : currentNodeTwin.getBoard().neighbors()) {
        if (currentNodeTwin.getPreviousNode() != null && neighbor.equals(currentNodeTwin.getPreviousNode().getBoard())) {
          continue;
        }

        minPQTwin.insert(new SearchNode(neighbor, currentNodeTwin, currentNodeTwin.getMoves() + 1));
      }

      if (minPQ.size() > 0) {
        currentNodeTwin = minPQTwin.delMin();
      }
    }

    if (currentNode.getBoard().isGoal()) {
      goalNode = currentNode;
    } else {
      goalNode = null;
    }
  }

  private class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private SearchNode previousNode;
    private int moves;

    public SearchNode(Board board, SearchNode previousNode, int moves) {
      this.board = board;
      this.previousNode = previousNode;
      this.moves = moves;
    }

    public Board getBoard() {
      return board;
    }

    public SearchNode getPreviousNode() {
      return previousNode;
    }

    public int getMoves() {
      return moves;
    }

    public int getPriority() {
      return board.manhattan() + moves;
    }

    @Override
    public int compareTo(SearchNode that) {
      return (int) Math.signum(this.getPriority() - that.getPriority());
    }
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return goalNode != null;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (isSolvable()) {
      return goalNode.getMoves();
    }

    return -1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (isSolvable()) {
      Stack<Board> boards = new Stack<Board>();

      for (SearchNode node = goalNode; node != null; node = node.getPreviousNode()) {
        boards.push(node.getBoard());
      }

      return boards;
    }

    return null;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();

    Board initial = new Board(blocks);

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

}