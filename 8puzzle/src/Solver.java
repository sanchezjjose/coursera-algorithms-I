import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Solver {

  Board initial;
  Stack<Board> solutionBoards = new Stack<Board>();

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    this.initial = initial;
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
      return board.manhattan();
    }

    @Override
    public int compareTo(SearchNode that) {
      return (int) Math.signum(this.getPriority() - that.getPriority());
    }
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return false;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (isSolvable()) {
      return solutionBoards.size();
    }

    return -1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (isSolvable()) {
      MinPQ<SearchNode> searchNodePQ = new MinPQ<SearchNode>();
      Iterator<SearchNode> searchNodeItr = searchNodePQ.iterator();
      Set<Integer> boardHashCodes = new HashSet<Integer>();

      searchNodePQ.insert(new SearchNode(initial, null, 0));

      while (searchNodeItr.hasNext()) {
        SearchNode currentSearchNode = searchNodeItr.next();
        SearchNode minSearchNode = searchNodePQ.delMin();

        solutionBoards.push(minSearchNode.getBoard());
        boardHashCodes.add(currentSearchNode.hashCode());

        if (minSearchNode.getBoard().isGoal()) {
          break;
        }

        for (Board b : currentSearchNode.getBoard().neighbors()) {
          if (!boardHashCodes.contains(b.hashCode())) {
            searchNodePQ.insert(new SearchNode(b, currentSearchNode, currentSearchNode.getMoves() + 1));
          }
        }
      }

      return solutionBoards;

    } else {
      return null;
    }
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