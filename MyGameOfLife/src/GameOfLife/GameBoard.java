package GameOfLife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;


public class GameBoard extends JFrame {
	
	private CellFrame mainFrame;
	
	public GameBoard() {
		mainFrame = new CellFrame();
		mainFrame.setVisible(true);
	}
	
	public CellFrame getgameFrame() {
		return mainFrame;
	}
	
	public static void main(String[] args) {
		//starts the game
		GameBoard game = new GameBoard();
	}
}
