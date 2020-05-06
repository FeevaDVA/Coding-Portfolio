package GameOfLife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GridsCanvas extends JPanel {
  int width, height;
  CellMatrix matrix;
  GridsCanvas(int w, int h) {
    setSize(width = w, height = h);
    matrix = new CellMatrix(w/5, h/5);
  }

  //paints the matrix and the board of the game 
  //repaints it everytime the matrix is modified
  public void paint(Graphics g) {
	  super.paint(g);
	  width = getSize().width;
	  height = getSize().height;
    
	  Graphics2D g2 = (Graphics2D) g;
	  
	  int[][] board = matrix.getBoard();
	  for(int x = 0; x<board.length; x++)
		  for(int y = 0; y<board[0].length; y++) {
			  if(board[x][y] == 1) {
				  g2.setPaint(Color.YELLOW);
				  g2.fillRect(x*5, y*5, 5, 5);
			  } else {
				  g2.setPaint(Color.DARK_GRAY);
				  g2.fillRect(x*5, y*5, 5, 5);
			  }
		  }
	}
  
  public void start() {
	  matrix.nextGen();
	  repaint();
  }
  
  public void refresh() {
	  repaint();
  }
  
  public void setCell(int x, int y) {
	  matrix.placeCell(x, y);
	  repaint();
  }
  
}

public class CellFrame extends JFrame implements MouseListener, ActionListener {
  
	private GridsCanvas grid;
	private JButton start;
	private Thread thrd;
	private boolean gameStart = false;
	
	//creates a frame that shows the game
	public CellFrame() {
		grid = new GridsCanvas(1000, 1000);
		grid.addMouseListener(this);
		add(BorderLayout.CENTER, grid);
		start = new JButton("Start");
		start.addActionListener(this);
		add(BorderLayout.SOUTH, start);
		setSize(1000, 1000);
	}
	
	public boolean getGameState() {
		return gameStart;
	}
	
	//used to start
	public void start() {
		grid.start();
	}
	
	public void refresh() {
		grid.refresh();
	}
	
	public GridsCanvas getCanvas() {
		return grid;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	//used clicking on the matrix it will place down a cell 
	// the coordinates of the cursor is used to find the spot on the matrix
	public void mousePressed(MouseEvent e) {
		int x = e.getX() / 5;
		int y = e.getY() / 5;
		grid.setCell(x, y);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	//used to start the game of life simulation and used to stop it
	public void actionPerformed(ActionEvent e) {
		thrd = new Thread(new GameThread(this));
		if(!gameStart) {
			start.setText("Stop");
			gameStart = true;
			thrd.start();
		} else {
			start.setText("Start");
			gameStart = false;
		}
			
		
	}
}


