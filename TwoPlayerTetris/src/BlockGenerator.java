import java.util.ArrayList;
import java.util.Collections;

/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 6. 4.
 * @file BlockGenerator.java
 */

/**
 * A class to randomly generate normal blocks or penalty blocks. <br>
 * This memorizes the currently moving block on each board. The moving block is accessible by
 * getBlock().<br>
 * Use addBlockToBoard() to pop a block from a set, and attatch it into a board. <br>
 * Use addPenaltyBlockToBoard(), penalty lines can be made.
 */
public class BlockGenerator {

  private Block nowMovingBlock;

  private ArrayList<Block> set = new ArrayList<Block>();

  /**
   * Make 7 different blocks once, and shuffle the order of blocks.
   */
  public void initSet() {
    set.add(new BlockI());
    set.add(new BlockJ());
    set.add(new BlockL());
    set.add(new BlockO());
    set.add(new BlockS());
    set.add(new BlockT());
    set.add(new BlockZ());
    Collections.shuffle(set);
  }

  /**
   * Pop a block from a set, and attach it to given board. <br>
   * The block will be at the top of the board, which will not be shown immediately.
   * 
   * @param board
   */
  public void addBlockToBoard(Board board) {
    if (set.isEmpty()) {
      this.initSet();
    }

    nowMovingBlock = set.get(0);
    set.remove(0);

    nowMovingBlock.add(board);
  }

  public Block getBlock() {
    return nowMovingBlock;
  }

  /**
   * Make a penanlty line which is given from the opponent player. The line will have only one
   * blank, and the other columns will be blackened.
   * 
   * @param board
   * @param penaltyLines
   */
  public void addPenaltyBlockToBoard(Board board, int penaltyLines) {
    int[][] newBoard = board.getBoardValues();
    for (int i = 0; i < 22 - penaltyLines; i++) {
      /**
       * Move up the original lines.
       */
      for (int j = 0; j < 10; j++) {
        newBoard[i][j] = newBoard[i + penaltyLines][j];
      }
    }

    /**
     * Make penalty lines at the bottom of the board.
     */
    for (int i = 22 - penaltyLines; i < 22; i++) {
      ArrayList<Integer> line = new ArrayList<Integer>();
      for (int j = 0; j < 9; j++)
        line.add(1);
      line.add(0);
      Collections.shuffle(line);

      for (int j = 0; j < 10; j++)
        newBoard[i][j] = line.get(j);
    }

  }
}
