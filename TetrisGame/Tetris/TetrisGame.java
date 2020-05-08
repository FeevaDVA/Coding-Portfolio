package Tetris;

import java.util.*;
import javax.swing.Timer;

import java.awt.event.*;
import java.io.File;
import java.awt.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import Tetris.Tetromino.Block;

public class TetrisGame extends JFrame {

	private int[][] board;
	private Clip clip;
	private static Queue<Tetromino> tetrisList;
	public GameBoard game;
	private Tetromino hold;
	private static Tetromino current;
	private static Tetromino next;
	private boolean skip = false;
	private boolean drop = false;
	private int prevKey;
	private heldGUI heldArea;
	private nextGUI nextArea;
	private Timer timer;
	private JLabel clock, heldType, scoreg, levelg;
	private int minutes, seconds;
	private boolean usedHeld = false;
	private boolean held = false;
	private JPanel panel2, panel3;
	private JInternalFrame heldPiece, nextPiece;
	private JLayeredPane layeredPane;
	private boolean gameEnd = false;

	public static void main(String[] args) {

		TetrisGame tGame = new TetrisGame();
		generateList();
		current = tetrisList.poll();
		next = tetrisList.poll();

		tGame.setVisible(true);
	}

	public TetrisGame() {
		tetrisList = new LinkedList<Tetromino>();
		board = new int[10][24];
		for (int i = 0; i < board.length; i++) {
			for (int y = 0; y < board[0].length; y++) {
				board[i][y] = 0;
			}
		}
		getContentPane().setLayout(null);
		game = new GameBoard(this); //creates new game board
		game.setBounds(114, 0, 311, 596);
		// game.nextPiece(getNext());
		getContentPane().add(game);
		heldPiece = new JInternalFrame(""); // creates the held piece frame
		heldPiece.setLocation(0, 0);
		nextPiece = new JInternalFrame("");// creates the nextPiece frame
		getContentPane().add(heldPiece);
		nextPiece.getContentPane().setLayout(new BorderLayout());
		heldPiece.setVisible(true);
		nextPiece.setVisible(true);
		heldPiece.setSize(112, 596);
		nextPiece.setSize(127, 400);
		nextPiece.setLocation(447, 0);
		getContentPane().add(nextPiece);
		panel2 = new JPanel();

		// heldPiece.add(panel2);
		heldType = new JLabel("");
		heldArea = new heldGUI(game, this);
		nextArea = new nextGUI(game, this);
		heldPiece.getContentPane().setLayout(new BorderLayout(0, 0));
		heldPiece.getContentPane().add(heldArea);
		
		JLabel lblHeld = new JLabel("        Held"); // makes a JLabel and makes sure that its centered
		lblHeld.setFont(new Font("Tahoma", Font.PLAIN, 14));
		heldPiece.getContentPane().add(lblHeld, BorderLayout.NORTH); //adds it to layout
		nextPiece.getContentPane().add(nextArea);
		
		JLabel lblHeld_1 = new JLabel("          Next"); // makes JLabel and makes sure that its centered
		lblHeld_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nextPiece.getContentPane().add(lblHeld_1, BorderLayout.NORTH); //adds it to layout
		setSize(600, 635); // sets the size of the game
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		timer = new Timer(1000, new ActionListener() { //makes a new timer of 1 second
			@Override
			public void actionPerformed(ActionEvent e) { //every second it adds one second
				seconds++;
				if (seconds < 100000) {
					if (seconds == 60) {// every 60 seconds 
						seconds = 0; // it sets seconds to 0
						minutes++; // thens adds a minute
					} else if (seconds < 10) {
						clock.setText(minutes + ":0" + seconds); //sets the time of the clock when its less than 10 seconds
					} else
						clock.setText(minutes + ":" + seconds); //sets the time of the clock when its more than 10 seconds
				} else {
					((Timer) (e.getSource())).stop();
				}
			}
		});
		timer.setInitialDelay(0);
		timer.start(); //starts the timer
		JPanel panel = new JPanel();
		panel.setBounds(435, 411, 139, 185);
		getContentPane().add(panel);
		clock = new JLabel("...");
		clock.setFont(new Font("Tahoma", Font.PLAIN, 17)); // sets the font and size of the clock/timer
		// panel.add(clock);
		panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.add(clock, BorderLayout.SOUTH); // adds the clock to the south of the layout
		scoreg = new JLabel("Score: 0"); 
		scoreg.setFont(new Font("Tahoma", Font.PLAIN, 17)); // sets the font of the scores
		panel3.add(scoreg, BorderLayout.NORTH); // then adds the score to the north of the border layout
		levelg = new JLabel("Level: 1");
		levelg.setFont(new Font("Tahoma", Font.PLAIN, 17)); // sets the font and size of the level
		panel3.add(levelg, BorderLayout.CENTER); // adds it to the center
		panel.add(panel3, BorderLayout.SOUTH); //then adds the whole panel to the main panel in the south
		play();
		addKeyListener(new KeyListener() { //creates the key listener
			public void keyTyped(KeyEvent e) {
			}
			public void keyPressed(KeyEvent e) { //when the keys are pressed do an action
					drop = false;
					switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: // when the up arrow is pressed
						if (prevKey != KeyEvent.VK_SPACE) { // if the prev key is space it will do nothing to prevent moving the key after hard drop
							Tetromino t = game.getCurrent(); //gets the current tetromino from the game board
							game.setCurrent(game.getCurrent().rotateCW()); //sets the current one to rotate
							if (game.collision(game.getOriginX(), game.getOriginY(), 0)) //then checks if there is a collision
								game.setCurrent(t);// if it collides sets it back to the starting rotation
						}
						game.repaint(); // then repaints
						break;
					case KeyEvent.VK_DOWN: //if the down key 
						drop = true; 
						game.dropDown(); //moves the piece down one
						game.repaint(); 
						break;
					case KeyEvent.VK_LEFT: // if the left arrow is pressed
						if (prevKey != KeyEvent.VK_SPACE) // if space is pressed it wont move the piece
							game.move(-1); // moves the piece to the left
						break;
					case KeyEvent.VK_RIGHT: //if the right arrow is pressed
						if (prevKey != KeyEvent.VK_SPACE) // if space is the last key pressed it wont move the piece
							game.move(+1); //moves the piece to the right
						break;
					case KeyEvent.VK_SPACE: //if space is pressed
						game.hardDrop(); //hard drops the piece to the bottom
						prevKey = e.getKeyCode(); //sets it as the prev key
						skip = true; //sets skip to true
						break;
					case KeyEvent.VK_SHIFT: //if shift is pressed
						if (prevKey != KeyEvent.VK_SPACE){ // checks if the hard drop was pressed
							if (!usedHeld) { // if there is not already held piece 
								heldType.setText(game.getCurrent().getType() + ""); 
								holdPiece(); // it holds the piece
								usedHeld = true; //sets held to true
							}
						}
						break;
					}

			}

			public void keyReleased(KeyEvent e) {
			}
		});
		new Thread() { //creates a new thread
			public void run() {
				while (true) { //continues to run
					try {
						updateScoreLevel(); //updates the score guis
						
						if (prevKey == KeyEvent.VK_SPACE) { //if the prev key is space
							prevKey = 0; //sets prev key to 0
						} else if (!drop) {// if the piece is not soft dropping
							double speed = Math.pow((0.8-(game.getLevel())*0.007),game.getLevel())*1000; //calculates the speed of the game
							System.out.println(speed + "");
							System.out.println(game.getLevel() + "");
							Thread.sleep((long) speed); //uses speed to sleep the thread
							game.dropDown(); //drops the piece down one

						}
						drop = false;
						if (game.isDropped()) { // checks if the piece is dropped
							if(game.isGameOver()) { //checks if there is a gave over
								gameOver(game.isGameOver()); // runs the game over method
								timer.stop(); //stops the timer
								stop(); // stops the thread
							}
							usedHeld = false; //sets the used held to false
							game.clearRows(); // checks for completed row and clears them
							if (tetrisList.isEmpty()) //if the queue is empty it will generate a new queue and add it
								generateList();
							game.nextPiece(next); //sets the current piece in game to the next one and sets the origin back at the top
							current = next; //sets current to next
							next = tetrisList.poll(); //polls new piece into next
							nextArea.repaint(); // repaints the next area
							game.repaint(); // then repiants the game
						}
					} catch (InterruptedException e) {
					}
				}
			}
		}.start(); // starts the thread
	}

	public static void generateList() {
		if (tetrisList.isEmpty()) { // create a random set of 7 pieces and add them to the queue
			int count = 0;
			Tetromino[] newSet = new Tetromino[7]; // creates a array of 7
			newSet[0] = new Tetromino();
			newSet[0].setRandomShape(); // adds the first random shape to array for compairison
			while(count < 7) { // while it does not contain 7 shapes it will try to place shapes
				boolean notSame = true;
				Tetromino newPiece = new Tetromino();
				newPiece.setRandomShape(); // creates a random new piece
				for(int i = 0; i<newSet.length; i++) { // checks the array if that piece already exists
					if(newSet[i] != null)
						if(newPiece.compareTo(newSet[i])<0)
							notSame = false;
				}
				if(notSame) { // if it does not exist already it will add it and up count
					newSet[count] = newPiece;
					count++;
				} // if not then it will loop again
			}
			for (Tetromino n : newSet) {
				tetrisList.add(n); // adds all the pieces from the array into the queue
			}
		}
}

	public Tetromino getHold() { // returns the held piece
		return hold;
	}

	public void holdPiece() {
		if (hold == null) {
			hold = new Tetromino();
			hold.setShape(game.getCurrent().getShape());
			heldArea.repaint();
			if (!tetrisList.isEmpty()) {
				game.setCurrent(next);
				next = tetrisList.poll();
				nextArea.repaint();
			} else {
				generateList();
				game.setCurrent(next);
				next = tetrisList.poll();
				nextArea.repaint();
			}
			held = true;

		} else {
			heldArea.repaint();
			Tetromino temp = new Tetromino();
			temp.setShape(game.getCurrent().getShape());
			game.setCurrent(hold);
			hold = temp;
			held = true;
		}
		game.setOrigin(5, 0);
	}

	public Tetromino getCurrent() { // returns current
		if (current == null) // if current is already null
			current = tetrisList.poll(); // it tries to poll from the queue only used on first run
		return current;
	}

	public Color getAColor(Tetromino g) { // returns the color of the given piece based on shape
		if (g != null) {
			if (g.getShape() == Block.ZBlock)
				return Color.red;
			if (g.getShape() == Block.SBlock)
				return Color.green;
			if (g.getShape() == Block.LBlock)
				return Color.orange;
			if (g.getShape() == Block.ReverseLBlock)
				return Color.blue;
			if (g.getShape() == Block.LineBlock)
				return Color.cyan;
			if (g.getShape() == Block.SquareBlock)
				return Color.yellow;
			return Color.magenta;
		}
		return null;
	}

	public Tetromino getNext() { // returns the next piece
		return next;
	}

	public void updateScoreLevel() { // updates the score and levels on the gui
		scoreg.setText("Score: " + game.getScore());
		scoreg.repaint();
		levelg.setText("Level: " + game.getLevel());
		levelg.repaint();
	}
	
	public void gameOver(boolean isOver) { //runs when the game is over
		if(isOver) {
			if(!gameEnd) {
				clip.stop();
				GameOverScreen nm = new GameOverScreen(this, game); // creates the game over frame
				nm.setLocationRelativeTo(null); // sets the location
				nm.setVisible(true);
				gameEnd = true; // stops this method from being called again 
			}
		}

	}
	
	public void start(TetrisGame tGame) { // starts the game
		generateList(); // creates the first list
		current = tetrisList.poll(); //sets the first piece current
		next = tetrisList.poll(); // sets the next piece 

		tGame.setVisible(true);
	}
	
	public void play() {
		try {
			File file = new File("Tetris_theme.wav");
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (float) ((range * 0.4) + gainControl.getMinimum());
			gainControl.setValue(gain);
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
