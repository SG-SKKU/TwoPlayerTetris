import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 5. 20.
 * @file Board.java
 */

/**
 * A class to show GUI of each player's board. <br>
 * Boards have 22 columns, and the top 2 columns are not shown in GUI. If these 2 lines are invaded,
 * this game ends.
 */
public class Board extends JPanel {

  /**
   * This final variable controls the initial game speed.
   */
  public static final int INITIAL_TIME = 400;

  /**
   * This final variable controls the increment of speed in the next phase.
   */
  public static final int TIME_DECREMENT = 40;

  /**
   * This final variable controls the number of phases in the game. Staring from phase 1, the speed
   * increment stops when the game reaches N phase.
   */
  public static final int N_PHASES = 10;

  /**
   * The length of a phase in ms.
   */
  public static final int PHASE_LENGTH = 15000;

  /**
   * This field show which player is controlling the board.
   */
  private int playerNum;

  /**
   * The number of penalty lines to generate given from the opponent.
   */
  private int penaltyLines = 0;

  public void setPenaltyLines(int penaltyLines) {
    this.penaltyLines = penaltyLines;
  }

  public int getPlayerNum() {
    return playerNum;
  }

  /**
   * Representing each space in a board. <br>
   * 
   * -1 : already fixed block <br>
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
  private int[][] boardValues = new int[22][10];

  public Board getBoard() {
    return this;
  }

  public int[][] getBoardValues() {
    return this.boardValues;
  }

  public void setBoardValues(int[][] boardValues) {
    this.boardValues = boardValues;
  }

  /**
   * Representing the actual JButton to be displayed.
   */
  private ArrayList<JButton> boardGUI = new ArrayList<JButton>(200);

  /**
   * File input to preset the board. File name must be "phase.txt", and must contain either 0 or
   * 1.<br>
   * Only 1 values will be a penalty block on the board.
   */
  private FileInputStream preset;

  /**
   * phase will increment as time elapses, and game will speed up.
   */
  private int phase = 0;

  /**
   * ms will count elapsed time in milliseconds.
   */
  private int ms = 0;

  /**
   * This indicates whether this player needs the next block. <br>
   * If it is false, there is a block falling down.
   */
  private boolean waitForNext = true;

  /**
   * This indicates that any block has invaded the upper 2 lines of the board, and no more blocks
   * are dropped.
   */
  private boolean gameEnd = false;

  private PenaltyHandler penaltyHandler;
  private JProgressBar bar;
  private BlockGenerator blockGenerator = new BlockGenerator();

  public void rotate() {
    if (waitForNext == false)
      this.blockGenerator.getBlock().rotate();
  }

  public void moveLeft() {
    if (waitForNext == false)
      this.blockGenerator.getBlock().moveLeft();
  }

  public void moveRight() {
    if (waitForNext == false)
      this.blockGenerator.getBlock().moveRight();
  }

  public void moveDown() {
    if (waitForNext == false)
      this.blockGenerator.getBlock().moveDown();
  }

  public void drop() {
    if (waitForNext == false)
      this.blockGenerator.getBlock().drop();
  }

  SwingWorker<Boolean, Integer> worker = new SwingWorker<Boolean, Integer>() {

    protected Boolean doInBackground() throws Exception {
      while (true) {
        if (ms % (INITIAL_TIME - phase * TIME_DECREMENT) == 0) {
          if (waitForNext) {
            /**
             * Generate penalty lines if exist.
             */
            if (penaltyLines > 0) {
              blockGenerator.addPenaltyBlockToBoard(getBoard(), penaltyLines);
              penaltyLines = 0;
            }

            /**
             * Check whether the top two lines are empty.
             */
            for (int i = 0; i < 2; i++) {
              for (int j = 0; j < 10; j++) {
                if (boardValues[i][j] != 0)
                  gameEnd = true;
              }
            }

            if (gameEnd == true) {
              System.out.format("\nPlayer %d over.\n", getPlayerNum());
              break;
            }

            /**
             * Create a new block.
             */
            System.out.format("\nPlayer %d is waiting for a new block...", getPlayerNum());
            blockGenerator.addBlockToBoard(getBoard());
            waitForNext = false;
          }

          /**
           * Move block a row down. <br>
           * If not movable, stuck the moving block and wait for the next block.
           */
          if (blockGenerator.getBlock().moveDown() == false) {
            for (int i = 0; i < 22; i++) {
              for (int j = 0; j < 10; j++) {
                if (boardValues[i][j] > 1)
                  boardValues[i][j] = -1;
              }
            }
            waitForNext = true;
          }
        } else if (ms == PHASE_LENGTH - 1) {
          /**
           * Initialize ms, and proceed to the next phase.
           */
          ms = -1;
          if (phase < N_PHASES) {
            phase++;
            System.out.format("\nPHASE %d: SPEED UP!", phase + 1);
          }
        }

        /**
         * Check whether any line is made.
         */
        if (waitForNext == true) {
          int count = 0;
          for (int i = 2; i < 22; i++) {
            for (int j = 0; j < 10; j++) {
              if (boardValues[i][j] != 1 && boardValues[i][j] != -1)
                break;

              if (j == 9) {
                count++;
                for (int k = i; k >= 2; k--) {
                  for (int l = 0; l < 10; l++) {
                    boardValues[k][l] = boardValues[k - 1][l];
                  }
                }
              }
            }
          }

          if (count > 0) {
            System.out.format("\nPlayer %d is erasing %d line(s).", playerNum, count);
          }

          penaltyHandler.givePenalty(playerNum, count);
        }

        if (phase < N_PHASES) {
          publish(ms / (PHASE_LENGTH / 100), phase + 1);
        } else {
          publish(100, N_PHASES);
        }
        Thread.sleep(1);
        ms++;
      }
      return null;
    }

    protected void process(List<Integer> chunks) {
      /**
       * Update the progress bar.
       */
      String phaseCheck = "Phase " + Integer.toString(chunks.get(chunks.size() - 1));
      bar.setValue(chunks.get(chunks.size() - 2));
      bar.setString(phaseCheck);

      /**
       * Update the board GUI.
       */
      ListIterator<JButton> buttons = boardGUI.listIterator();
      for (int i = 2; i < 22; i++) {
        for (int j = 0; j < 10; j++) {
          switch (boardValues[i][j]) {
            case -1:
              buttons.next().setBackground(Color.GRAY);
              break;
            case 0:
              buttons.next().setBackground(Color.WHITE);
              break;
            case 1:
              buttons.next().setBackground(Color.BLACK);
              break;
            case 2:
              buttons.next().setBackground(Color.CYAN);
              break;
            case 3:
              buttons.next().setBackground(Color.ORANGE);
              break;
            case 4:
              buttons.next().setBackground(Color.GREEN);
              break;
            case 5:
              buttons.next().setBackground(Color.YELLOW);
              break;
            case 6:
              buttons.next().setBackground(Color.MAGENTA);
              break;
            case 7:
              buttons.next().setBackground(Color.BLUE);
              break;
            case 8:
              buttons.next().setBackground(Color.PINK);
              break;
          }
        }
      }
    }
  };

  /**
   * Initialize the board.
   * 
   * @param playernum
   * @param progressBar
   */
  public Board(int playernum, JProgressBar progressBar) {
    this.bar = progressBar;
    this.playerNum = playernum;

    /**
     * 1. Set the GUI components.
     */
    this.setBackground(Color.WHITE);
    this.setBorder(new EmptyBorder(2, 2, 2, 2));
    this.setLayout(new GridLayout(20, 10, 1, 1));

    /**
     * 2. Create buttons which constructs the board.
     */
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 10; j++) {
        boardValues[i][j] = 0;

        JButton button = new JButton();
        button.setBackground(Color.WHITE);
        button.setForeground(Color.WHITE);
        button.setEnabled(false);
        boardGUI.add(button);
      }
    }

    /**
     * 3. Read the preset file if exists.
     */
    try {
      preset = new FileInputStream("preset.txt");
      Scanner scanner = new Scanner(preset);
      System.out.format("Player %d: ", this.getPlayerNum());
      System.out.print("The preset File is applied to a board.");

      ListIterator<JButton> guiIter = boardGUI.listIterator();
      for (int i = 2; i < 22; i++) {
        for (int j = 0; j < 10; j++) {
          if (!scanner.hasNextInt())
            break;

          if (scanner.nextInt() == 1) {
            guiIter.next().setBackground(Color.BLACK);
            boardValues[i][j] = 1;
          } else {
            guiIter.next().setBackground(Color.WHITE);
          }
        }
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      // Do Nothing
      System.out.println("No Preset File.");
    }

    /**
     * 4. Add all buttons to GUI.
     */
    for (int i = 0; i < 200; i++) {
      this.add(boardGUI.get(i));
    }

    /**
     * 5. Execute a background process which will periodically update GUI.
     */
    worker.execute();
  }

  /**
   * @param penaltyHandler
   */
  public void assignPenaltyHandler(PenaltyHandler penaltyHandler) {
    this.penaltyHandler = penaltyHandler;
  }

}
