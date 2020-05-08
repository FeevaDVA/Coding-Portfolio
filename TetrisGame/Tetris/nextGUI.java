package Tetris;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Color;
import javax.swing.*;
import Tetris.GameBoard;
import Tetris.TetrisGame;

public class nextGUI extends JPanel {
		private GameBoard game;
		private TetrisGame game2;
		public nextGUI(GameBoard temp,TetrisGame temp2) {
			game=temp;
			game2=temp2;
		}	
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(game2.getAColor(game2.getNext()));
			if(game2.getNext() != null) { //does not paint if there is nothing in the next
				int [][] i = game2.getNext().getCoords();
				for(int r=0;r<i.length;r++) {
					g.fillRect((i[r][0])*26, (i[r][1])*26, 25, 25); //paints the current piece in next in the Tetris game
				}
			}
		}

	}