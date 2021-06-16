/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 6. 4.
 * @file BlockO.java
 */

/**
 * An I-shaped block. blockType is 5, and color is YELLOW on the board.
 */
public class BlockO extends Block {

  public BlockO() {
    // this.preBlock = new int[][] {{0, 0, 0, 0, 5, 5, 0, 0, 0, 0}, {0, 0, 0, 0, 5, 5, 0, 0, 0, 0}};
    this.blockType = 5;
    System.out.println("BlockO generated.");
    this.block = new int[][][] {{{5, 5}, {5, 5}}};
    this.posRow = 0;
    this.posCol = 4;
    this.height = new int[] {2};
    this.width = new int[] {2};
    this.mode = 1;
  }

  @Override
  public boolean rotate() {
    return false;
  }

}
