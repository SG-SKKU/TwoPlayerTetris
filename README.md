![sample_plays](https://user-images.githubusercontent.com/85989429/122204350-fba43580-ced9-11eb-8a72-1ff12d0ac69b.gif)

# Overview
This guide is for any other beginner java students. <br> <br>

`TwoPlayerTestris` is a simple Java Swing game that consists of manipulating Tetriminos (or `Block`) to fill in a line to eliminate them.
Two human players are each given a background thread, allowing them to simultaneously manipulate blocks without stopping the GUI. <br>
A player has a 20*10 board, and blocks will fall down within the board. <br>
Making a line full of blocks, one can erase that line. 

# How to start
Execute the JAR file named TwoPlayerTetris.jar. <br>
Key inputs to operate each player is displayed at the bottom of the screen. <br>
You can move colored blocks, and stuck blocks are greyed.

# Features
### Make some preset blocks
By modifying the file `preset.txt`, preset penalty blocks can be made.
There are basically 200 zeros representing empty spaces on each board.
You can change each 0 to 1 to make a penalty block at that corresponding location on the board. <br>
For instance, to make a O block at the bottom:
```swift
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 0 0 0 0 0 0 
0 0 0 0 1 1 0 0 0 0 
0 0 0 0 1 1 0 0 0 0
```
### Penalty lines
If you erases N multiple lines simultaneously, you can give the other player N-1 penalty lines. <br>
Each penalty lines consist of 9 black blocks, and 1 empty space in a random location.
### Increasing game speed
By default, there are 10 game phases, and the game will get faster over time. <br>
Each phase is indicated below the board, and a progress bar shows the elapsed time of the current phase.
Modyfing the source file(`Board.java`), you can manipulate game speed: 
```swift
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
```
### Single player
If one side invades the top of the board and the game ends, the other player can continue playing.

# Role of each class
### Main
This constructs JFrame. This includes two Board objects, a JProgressBar, a PenaltyHandler. KeyListener is added here using a KeyHandler object.

### KeyHandler
KeyHanler class implements KeyListener, which detects user's multiple keyboard inputs. An anonymous KeyHandler object is made in Main, and two Board objects are given as arguments. The KeyHandler object can use methods to move blocks in the boards when any corresponding key is pressed. Once a key is pressed, it will not be handled again until the key is released.

### Board
A Board object makes an Integer array boardValues[22][10] to memorize board status, and make an ArrayList of 200 JButtons that will make GUI. The boardValues[2] ~ [21] constructs GUI, and boardValues[0] ~ [1] is not shown to the user. If 'preset.txt' exists, penalty blocks are made before the game starts. Every millisecond, SwingWorker checks the present state as described above (2. Flow Diagram). SwingWorker can call a BlockGenerator object to make a new block or penalty lines given from the other player, or a PenaltyHandler object to give the other player penalty lines. Game phase is checked here, and JProgressBar is updated.

### BlockGenerator
A BlockGenerator object has an ArrayList of Blocks. A BlockI, J, L, O, S, T, or Z object is created each, all are put into the ArrayList named set, and their orders are shuffled. By doing this player does not receive the same block continuously. Penalty lines can also be made here, which is consisted of 1 blank, and 9 black blocks. This will be at the bottom of a board, and it will move up existing blocks.

### Block
A Block object is consisted of methods that would move itself within a board. It checks whether there are any other blocks in the path it is going to proceed, and updates boardValues if nothing is there. Method moveLeft(), moveRight(), moveDown(), drop(), and add() are common to all children objects, but since rotate() depends on the shape of block, it is abstract. rotate() is implemented in each child class BlockX.

### PenaltyHandler
A PenaltyHandler object helps communication between two different threads. It has each Boards as a field, and checks whether multiple lines are cleared. Checking a player clears mutiple lines, this gives the other player penalty lines that are one less than the number of cleared lines.

# Known Issues
1. Since two threads check one JProgressBar simultaneously, it may appears to vibarate. But by doing this, even if one player is over, the progress bar can still keep updated.
2. If the input file is filled with characters other than 0 or 1, the game does not crash but appearance may be different than you think.

# Features to be added (or considered)
 1. Displaying each player's next block between the two boards.
 2. Displaying penalties awaiting instead of instructions below.
 3. Supporting T-Spins or etc.
 4. Supporting the block holding feature.
