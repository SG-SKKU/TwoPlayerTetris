import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * TwoPlayerTetris
 *
 * @author SG-SKKU
 * @date 2021. 5. 20.
 * @file Main.java
 * 
 * @todo 1. Display each player's next block between the two boards. <br>
 *       2. Display penalties awaiting instead of instructions below. <br>
 *       3. Support T-Spins or etc. <br>
 *       4. Support the block holding feature.
 */

/**
 * A class to construct the frame. KeyListener is added here. <br>
 * This contains 7 objects: <br>
 * A JPanel with BorderLayout named contentPane. This contains all the other objects. <br>
 * Two Board objects. This shows each player's board GUI. <br>
 * Another JPanel with BorderLayout named statusPanel. This displays several information at the
 * bottom. <br>
 * - A JProgressBar to show game speed. <br>
 * - Two JLabel objects to show how to control.
 */
public class Main extends JFrame {

  private JPanel contentPane;
  public Board leftBoard;
  public Board rightBoard;
  private JPanel statusPanel;
  private JLabel textForRight;
  private JLabel textForLeft;
  public JProgressBar progressBar;
  private JPanel nextPanel;
  private PenaltyHandler penaltyHandler;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {

    /**
     * Make this false to see logs in stdout.
     */
    final boolean LOG_TO_FILE = false;

    if (LOG_TO_FILE == true) {
      File log = new File("log.txt");
      try {
        PrintStream printStream = new PrintStream(new FileOutputStream(log));
        System.setOut(printStream);
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      }
    }

    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Main frame = new Main();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame. <br>
   * This method includes a KeyHandler object to respond with multiple keyboard inputs.
   */
  public Main() {
    setTitle("Tetris");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 800);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(new BorderLayout(0, 0));

    statusPanel = new JPanel();
    contentPane.add(statusPanel, BorderLayout.SOUTH);

    nextPanel = new JPanel();
    contentPane.add(nextPanel, BorderLayout.CENTER);

    textForLeft = new JLabel("ASD to move, W to rotate, E to drop.");
    textForLeft.setHorizontalAlignment(SwingConstants.LEFT);
    statusPanel.add(textForLeft);

    progressBar = new JProgressBar();
    progressBar.setStringPainted(true);
    progressBar.setString("READY");
    statusPanel.add(progressBar);

    leftBoard = new Board(1, progressBar);
    contentPane.add(leftBoard, BorderLayout.WEST);

    rightBoard = new Board(2, progressBar);
    contentPane.add(rightBoard, BorderLayout.EAST);

    textForRight = new JLabel("JKL to move, I to rotate, O to drop.");
    textForRight.setHorizontalAlignment(SwingConstants.RIGHT);
    statusPanel.add(textForRight);

    addKeyListener(new KeyHandler(leftBoard, rightBoard));
    penaltyHandler = new PenaltyHandler(leftBoard, rightBoard);
    leftBoard.assignPenaltyHandler(penaltyHandler);
    rightBoard.assignPenaltyHandler(penaltyHandler);
  }

}
