package Tetris;

import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Color;
import javax.swing.*;
import Tetris.GameBoard;
import Tetris.TetrisGame;
public class heldGUI extends JPanel {
	private GameBoard game;
	private TetrisGame game2;
	public heldGUI(GameBoard temp,TetrisGame temp2) {
		game=temp;
		game2=temp2;
	}	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(game2.getAColor(game2.getHold()));
		if(game2.getHold() != null) { // does not paint if there is nothing in held
			int [][] i = game2.getHold().getCoords();
			for(int r=0;r<i.length;r++) {
				g.fillRect((i[r][0])*26, (i[r][1])*26, 25, 25); //paints the current piece that is held in the tetrisgame
			}
		}
	}

}