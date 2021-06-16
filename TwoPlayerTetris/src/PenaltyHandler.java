/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 6. 5.
 * @file PenaltyHandler.java
 */

/**
 * A class to handle penalties of the game. <br>
 * This class must contain fields to reference both boards, to communicate between diffrent threads.
 */
public class PenaltyHandler {
  Board boardOne;
  Board boardTwo;

  public PenaltyHandler(Board boardOne, Board boardTwo) {
    this.boardOne = boardOne;
    this.boardTwo = boardTwo;
  }

  /**
   * Give another player penalty. <br>
   * Whenever erasing more than 2 lines, (the number of erased lines) - 1 will be the number of
   * penalty lines.
   * 
   * @param senderPlayerNum the number of sender
   * @param lines erased lines (0 or 1 will be ignored)
   */
  public void givePenalty(int senderPlayerNum, int lines) {
    if (lines > 1) {
      System.out.format("Player %d is giving the opponent %d penalties!\n", senderPlayerNum,
          lines - 1);
      if (senderPlayerNum == 1) {
        boardTwo.setPenaltyLines(lines - 1);
      } else {
        boardOne.setPenaltyLines(lines - 1);
      }
    }
  }
}
