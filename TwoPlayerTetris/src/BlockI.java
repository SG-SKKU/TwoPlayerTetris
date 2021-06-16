/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 5. 21.
 * @file BlockI.java
 */

/**
 * An I-shaped block. blockType is 2, and color is CYAN on the board.
 */
public class BlockI extends Block {

  public BlockI() {
    this.blockType = 2;
    System.out.println("BlockI generated.");

    this.block = new int[][][] {{{2, 2, 2, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{2, 0, 0, 0}, {2, 0, 0, 0}, {2, 0, 0, 0}, {2, 0, 0, 0}}};
    this.posRow = 0;
    this.posCol = 3;
    this.height = new int[] {1, 4};
    this.width = new int[] {4, 1};
    this.mode = 2;
  }

  @Override
  public boolean rotate() {
    /**
     * Check whether the block is movable.
     */
    if (this.curMode == 0 && this.posRow > 18)
      return false;

    /**
     * Move block if the position is not suitable.
     */
    if (this.curMode == 1 && this.posCol > 6) {
      while (this.posCol > 6)
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
