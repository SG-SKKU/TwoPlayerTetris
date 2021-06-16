/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 6. 4.
 * @file BlockZ.java
 */

/**
 * An I-shaped block. blockType is 8, and color is PINK on the board.
 */
public class BlockZ extends Block {

  public BlockZ() {
    // this.preBlock = new int[][] {{0, 0, 0, 8, 8, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 8, 8, 0, 0, 0, 0}};
    this.blockType = 8;
    System.out.println("BlockZ generated.");
    this.block =
        new int[][][] {{{8, 8, 0}, {0, 8, 8}, {0, 0, 0},}, {{0, 8, 0}, {8, 8, 0}, {8, 0, 0}}};
    this.posRow = 0;
    this.posCol = 3;
    this.height = new int[] {2, 3, 2, 3};
    this.width = new int[] {3, 2, 3, 2};
    this.mode = 2;
  }


  public boolean rotate() {
    /**
     * Check whether the block is movable.
     */
    if ((this.curMode % 2) == 0 && this.posRow > 19)
      return false;

    /**
     * Move block if the position is not suitable.
     */
    if ((this.curMode % 2) == 1 && this.posCol > 7) {
      while (this.posCol > 7)
        if (!this.moveLeft())
          return false;
    }

    for (int i = 0; i < this.height[(this.curMode + 1) % this.mode]; i++) {
      for (int j = 0; j < this.width[(this.curMode + 1) % this.mode]; j++) {
        if (this.block[(this.curMode + 1) % this.mode][i][j] > 1) {
          if (cloneBoard[this.posRow + i][this.posCol + j] == -1
              || cloneBoard[this.posRow + i][this.posCol + j] == 1)
            return false;
        }
      }
    }

    /**
     * Clean the previous location.
     */
    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (cloneBoard[this.posRow + i][this.posCol + j] > 1) {
          cloneBoard[this.posRow + i][this.posCol + j] = 0;
        }
      }
    }

    /**
     * Move the block.
     */
    this.curMode = (this.curMode + 1) % this.mode;
    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (this.block[this.curMode][i][j] > 1) {
          cloneBoard[this.posRow + i][this.posCol + j] = this.blockType;
        }
      }
    }

    originalBoard.setBoardValues(this.cloneBoard);
    return true;
  }

}
