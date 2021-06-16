import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 6. 3.
 * @file KeyHandler.java
 */

/**
 * A class to handle all key press & release. <br>
 * Each event must be handled only once when each key is just pressed. <br>
 * This class must contain fields to reference both boards, to communicate between diffrent threads.
 * <br>
 * When a key has been pressed, make preesX true and call corresponding method in a board.
 */
public class KeyHandler implements KeyListener {

  /**
   * Keys for the Player 1
   */
  private Board leftBoard;
  private boolean pressW = false;
  private boolean pressE = false;
  private boolean pressA = false;
  private boolean pressS = false;
  private boolean pressD = false;

  /**
   * Keys for the Player 2
   */
  private Board rightBoard;
  private boolean pressI = false;
  private boolean pressO = false;
  private boolean pressJ = false;
  private boolean pressK = false;
  private boolean pressL = false;

  /**
   * A class to handle all key press & release. <br>
   * Each event must be handled only once when each key is just pressed.
   * 
   * @param leftBoard
   * @param rightBoard
   */
  public KeyHandler(Board leftBoard, Board rightBoard) {
    this.leftBoard = leftBoard;
    this.rightBoard = rightBoard;
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    /**
     * 1. Checking keyPressed for Player 1
     */
    // if (e.getKeyCode() == KeyEvent.VK_Q && !pressQ) {
    // System.out.print("Q");
    // pressQ = true;
    // }
    if (e.getKeyCode() == KeyEvent.VK_W && !pressW) {
      System.out.print("W");
      leftBoard.rotate();
      pressW = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_E && !pressE) {
      System.out.print("E");
      leftBoard.drop();
      pressE = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_A && !pressA) {
      System.out.print("A");
      leftBoard.moveLeft();
      pressA = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_S && !pressS) {
      System.out.print("S");
      leftBoard.moveDown();
      pressS = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_D && !pressD) {
      System.out.print("D");
      leftBoard.moveRight();
      pressD = true;
    }

    /**
     * 2. Checking keyPressed for Player 2
     */
    // if (e.getKeyCode() == KeyEvent.VK_U && !pressU) {
    // pressU = true;
    // }
    if (e.getKeyCode() == KeyEvent.VK_I && !pressI) {
      System.out.print("I");
      rightBoard.rotate();
      pressI = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_O && !pressO) {
      System.out.print("I");
      rightBoard.drop();
      pressO = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_J && !pressJ) {
      System.out.print("J");
      rightBoard.moveLeft();
      pressJ = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_K && !pressK) {
      System.out.print("K");
      rightBoard.moveDown();
      pressK = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_L && !pressL) {
      System.out.print("L");
      rightBoard.moveRight();
      pressL = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    /**
     * 1. Checking keyReleased for Player 1
     */
    // if (e.getKeyCode() == KeyEvent.VK_Q)
    // pressQ = false;
    if (e.getKeyCode() == KeyEvent.VK_W)
      pressW = false;
    if (e.getKeyCode() == KeyEvent.VK_E)
      pressE = false;
    if (e.getKeyCode() == KeyEvent.VK_A)
      pressA = false;
    if (e.getKeyCode() == KeyEvent.VK_S)
      pressS = false;
    if (e.getKeyCode() == KeyEvent.VK_D)
      pressD = false;

    /**
     * 2. Checking keyReleased for Player 2
     */
    // if (e.getKeyCode() == KeyEvent.VK_U)
    // pressU = false;
    if (e.getKeyCode() == KeyEvent.VK_I)
      pressI = false;
    if (e.getKeyCode() == KeyEvent.VK_O)
      pressO = false;
    if (e.getKeyCode() == KeyEvent.VK_J)
      pressJ = false;
    if (e.getKeyCode() == KeyEvent.VK_K)
      pressK = false;
    if (e.getKeyCode() == KeyEvent.VK_L)
      pressL = false;
  }

}
