/**
 * TwoPlayerTetris
 *
 * @author Kim Su Gyeom (2017311745)
 * @date 2021. 5. 20.
 * @file Block.java
 */

/**
 * An abstract class that define the shape, and movement of each block. Seven kinds of blocks should
 * implement this class.
 * 
 * @author Kim Su Gyeom
 */
public abstract class Block {
  /**
   * -1: already stuck block <br>
   * 0 : empty <br>
   * 1 : Penalty block <br>
   * 2 : block I <br>
   * 3 : block J <br>
   * 4 : block L <br>
   * 5 : block O <br>
   * 6 : block S <br>
   * 7 : block T <br>
   * 8 : block Z
   */
  protected int blockType;

  /**
   * The shape to be on the board.
   */
  protected int[][][] block;
  protected int posRow;
  protected int posCol;
  protected int[] height;
  protected int[] width;

  /**
   * This shows the number of cases that block can be rotated.
   */
  protected int mode;

  /**
   * This shows the current rotation state of the block.
   */
  protected int curMode = 0;

  protected Board originalBoard;
  protected int[][] cloneBoard;

  /**
   * Try to rotate the block. <br>
   * Board should be updated after getting true return value.
   * 
   * @return true if successes, false if not.
   */
  public abstract boolean rotate();

  /**
   * Try to move one column left the block. <br>
   * Board should be updated after getting true return value.
   * 
   * @return true if successes, false if not.
   */
  public boolean moveLeft() {
    /**
     * Check whether the block is movable.
     */
    if (this.posCol == 0)
      return false;

    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (this.block[this.curMode][i][j] > 1) {
          if (cloneBoard[this.posRow + i][this.posCol + j - 1] == -1
              || cloneBoard[this.posRow + i][this.posCol + j - 1] == 1) {
            return false;
          }
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
    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (this.block[this.curMode][i][j] > 1) {
          cloneBoard[this.posRow + i][this.posCol + j - 1] = this.blockType;
        }
      }
    }

    this.posCol--;
    originalBoard.setBoardValues(this.cloneBoard);
    return true;
  }

  /**
   * Try to move one column left the block. <br>
   * Board should be updated after getting true return value.
   * 
   * @return true if successes, false if not.
   */
  public boolean moveRight() {
    /**
     * Check whether the block is movable.
     */
    if (this.posCol + this.width[this.curMode] == 10)
      return false;

    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (this.block[this.curMode][i][j] > 1) {
          if (cloneBoard[this.posRow + i][this.posCol + j + 1] == -1
              || cloneBoard[this.posRow + i][this.posCol + j + 1] == 1) {
            return false;
          }
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
    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (this.block[this.curMode][i][j] > 1) {
          cloneBoard[this.posRow + i][this.posCol + j + 1] = this.blockType;
        }
      }
    }

    this.posCol++;
    originalBoard.setBoardValues(this.cloneBoard);
    return true;
  }

  /**
   * Try to move one row down the block. <br>
   * Board should be updated after getting true return value.
   * 
   * @return true if successes, false if not.
   */
  public boolean moveDown() {
    /**
     * Check whether the block is movable.
     */
    if (this.posRow + this.height[this.curMode] == 22)
      return false;

    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (this.block[this.curMode][i][j] > 1) {
          if (cloneBoard[this.posRow + i + 1][this.posCol + j] == -1
              || cloneBoard[this.posRow + i + 1][this.posCol + j] == 1) {
            return false;
          }
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
    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        if (this.block[this.curMode][i][j] > 1) {
          cloneBoard[this.posRow + i + 1][this.posCol + j] = this.blockType;
        }
      }
    }

    this.posRow++;
    originalBoard.setBoardValues(this.cloneBoard);
    return true;
  }

  public void add(Board board) {
    this.originalBoard = board;
    this.cloneBoard = board.getBoardValues();

    for (int i = 0; i < this.height[this.curMode]; i++) {
      for (int j = 0; j < this.width[this.curMode]; j++) {
        cloneBoard[i + this.posRow][j + this.posCol] = this.block[this.curMode][i][j];
      }
    }

  }

  /**
   * Drop the block to the bottom, by repeating moveDown().
   */
  public void drop() {
    while (true) {
      if (!this.moveDown())
        break;
    }
  }

}
