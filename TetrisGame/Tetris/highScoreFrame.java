package Tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class highScoreFrame extends JFrame {
	private class Player implements Comparable<Player>{
		private String name;
		private Integer score;
		public Player(String n, int s ) { //creates a private class that makes it easy to get the scores and the names as one object
			name = n;
			score = s;
		}
		public Integer getScore() {
			return score; //returns the score
		}
		public String getName() {
			return name; //returns the name
		}
		public int compareTo(Player other) {
			return this.getScore().compareTo(other.getScore()); //sets up the compare to with comparing the scores
		}
	}
	
	private ArrayList<Player> scores;
	private JFrame scoreGUI;
	private JPanel list;

	public highScoreFrame() throws FileNotFoundException {
		scoreGUI = new JFrame("");
		scoreGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoreGUI.setVisible(true);
		scores = new ArrayList<Player>();
		scoreGUI.setSize(400, 400);
		scoreGUI.setLocationRelativeTo(null);
		String name;
		list = new JPanel(); //creates the panel to hold all the JLabels with the scores
		try {
		Scanner txtdoc = new Scanner(new File("Highscores.txt")); //sets the highscore txt as a scanner
		while(txtdoc.hasNext()||txtdoc.hasNextInt()) {
			int number = txtdoc.nextInt(); //reads all the scores and adds them to the arraylist
			name = txtdoc.next();
			scores.add(new Player(name, number));
		}
		} catch (Exception e) {
			list.add(new JLabel("No Highscores Found"));
		}
		
		if(!scores.isEmpty()) { //sorts the array from descending order if it is not empty
			Collections.sort(scores, Collections.reverseOrder());
			for(Player i : scores)
				list.add(new JLabel(i.getName() + "--->" + i.getScore())); //it then adds all the scores as buttons in descending order
		}
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS)); //sets the layout as a box layout
		
		list.add(new JLabel("Press \"Backspace\" to go back"));
		
		scoreGUI.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) { 
			}
			public void keyPressed(KeyEvent e) { //listens for a backspace to return to main menu
				switch (e.getKeyCode()) {
					case KeyEvent.VK_BACK_SPACE:
						MainMenu nm = new MainMenu(); //creates a new mainmenu
						nm.setVisible(true);
						scoreGUI.dispose();//removes this frame 
						break;
				}
					
		}
			public void keyReleased(KeyEvent arg0) {
				
			};
		});
		
		scoreGUI.getContentPane().add(list, BorderLayout.NORTH);
	}
}
