package Tetris;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Queue;

import javax.swing.*;
import javax.swing.Timer;
import Tetris.Tetromino.Block;

public class GameBoard extends JPanel {
	private Point origin = new Point(5,0);
	private Tetromino next;
	private int rotation;
	private Tetromino current;
	private int score=0;
	private int level=0;
	private int linesCleared=0;

	
	private Color[][] field;
	public GameBoard(TetrisGame parent) { // constructor takes in the tetris game
		initBoard(parent);
	}
	private void initBoard(TetrisGame parent) {
		field=new Color[12][24]; //makes a field that has a gray border on the bottom and sides with black in the middle
		for(int i=0;i<12;i++) {
			for(int j=0;j<23;j++) {
				if(i==0 || i==11 || j==22) // for the bottom and sides set them to gray
					field[i][j]=Color.GRAY;
				else 
					field[i][j]=Color.BLACK;//else it sets every thing to black
			}
		}
		parent.generateList(); //generates the first set of 7 and adds it to the queue 
		
		current = parent.getCurrent(); //sets the first piece as current in the gameboard from the tetrisgame		
		System.out.println(current.getShape()+"");	
	}
	public void setCurrent(Tetromino t) {
		current=t; //sets current with given tetromino
	}
	public Tetromino getCurrent() {
		return current; //returns the current piece
	}
	public int getRotation() {
		return rotation; //returns the current rotation
	}
	public boolean collision(int x, int y, int rotation) { //checks if the piece is colliding with anything
		int [][]i=current.getCoords(); // gets the coordinates of the piece
		if(y < 0) { //if a piece is out of bounds of the field when rotated then it is colliding
			return true;
		}
		for(int r=0;r<4;r++) // for all the parts of the piece
			if(field[i[r][0]+x][i[r][1]+y]!=Color.BLACK) // it checks that part around the origin given for that piece
				return true; //if there is not a black block there then if is colliding
		return false; 
	}
	public int getOriginX() {
		return origin.x; //returns the x of the origin
	}
	public int getOriginY() {
		return origin.y;// returns the y of the origin
	}
	public void dropDown() {
		if(!collision(origin.x,origin.y+1,rotation)) //if it does not collide then it will move it down one
				origin.y+=1;
		else
			fixToWell();
		repaint();
	}
	public void move(int m) {
		if(!collision(origin.x+m,origin.y,rotation)) //if it does not collide it will move it to the right or left
			origin.x+=m;
		repaint();
	}
	public void fixToWell() {
		int [][]i=current.getCoords();
		for(int r=0;r<i.length;r++) {
			field[origin.x+i[r][0]][origin.y+i[r][1]]=getColor(); //sets the color of the block in the field
		}
		
	}
	public Color getColor() { //returns the color of the block based on block shape
		if(current.getShape()==Block.ZBlock)
			return Color.red;
		if(current.getShape()==Block.SBlock)
			return Color.green;
		if(current.getShape()==Block.LBlock)
			return Color.orange;
		if(current.getShape()==Block.ReverseLBlock)
			return Color.blue;
		if(current.getShape()==Block.LineBlock)
			return Color.cyan;
		if(current.getShape()==Block.SquareBlock)
			return Color.yellow;
		return Color.magenta;
	}
	public void drawBlock(Graphics g) {	
		if(!isGameOver()) // if the game is over it will set the block color to gray
			g.setColor(getColor());
		else
			g.setColor(Color.DARK_GRAY);
		int [][] i=current.getCoords();
		for(int r=0;r<i.length;r++) {
				g.fillRect((i[r][0]+origin.x)*26, (i[r][1]+origin.y)*26, 25, 25); //paints the block the color based on the coordinates in the field
			
		}
	}
	public void paintComponent(Graphics g) { //paint the field 
		g.fillRect(0, 0, 26*12, 26*23);
		for(int i=0;i<12;i++) 
			for(int j=0;j<23;j++) {
				g.setColor(field[i][j]); //sets the color to paint the same as in the field
				g.fillRect(26*i, 26*j, 25, 25); //fills the rectangle at the locations given
			}
		drawBlock(g);
		
	}
	public void hardDrop() { // drops it all the way down to the bottom stops if there is a collision made
		for(int i = 0; i<24; i++) { //for the distance to the bottom of the board
			if(!collision(origin.x, origin.y + 1, rotation))  //if there is no collision detected below 
				origin.y+=1; // moves down one
		}
		repaint();
	}
	public void clearRows() { //clears the row if it is full
		boolean gap;
		int numClears = 0; //counter for the number of clears 

		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (field[i][j] == Color.BLACK) { //if the line is not full
					gap = true; 
					break;
				}
			}
			if (!gap) { // if the line is full
				lineClear(j);
				j += 1;
				numClears += 1;
			}
		}
		linesCleared+=numClears; //counter for total lines cleared
		calcScore(numClears); //calculates the score for that clear based on how many lines were cleared
	}
	public void lineClear(int y) {
			for(int j=y-1;j>0;j--) {
				for(int i=1;i<11;i++)
					field[i][j+1]=field[i][j]; //moves each block down a row
			}
	}
	
	
	public boolean isDropped() {
		if(collision(origin.x, origin.y, rotation)) { //checks if there is a collision 
			
			return true; //if there is its dropped
		} else {
			return false; // if not it is not
		}
	}
	public void nextPiece(Tetromino n) {
		current = n; // sets the current tetromino in the current 
		origin.x = 5; // sets the origin x to 5
		origin.y = 0; // sets the origin y to 0
	}
	public void setOrigin(int x, int y) {
		origin.x = x; //sets the origin x to given x 
		origin.y = y; //sets the origin y to given y
	}
	public Tetromino getNext() {
		return next; //returns the next piece in game board
	}
	public int getScore() {
		return score; // returns the current score
	}
	public void calcScore(int n) {
		if(n == 1)
			score += 40*(1+getLevel());
		else if(n == 2)
			score += 100*(1+getLevel());
		else if(n == 3)
			score += 300*(1+getLevel());
		else if(n == 4)
			score += 1200*(1+getLevel());	
	} //calculates the score based on level and the amount of lines cleared at given moment
	
	public int getLevel() {
		level=linesCleared/5; // level goes up every 5 lines cleared
		return level; //returns the current level 
	}
	
	public boolean isGameOver() {
		if(collision(5, 0, rotation)) { //checks if there is a collision at the place point of the pieces
			for (int j = 22; j > 0; j--) {
				for (int i = 1; i < 11; i++) {
					if (field[i][j] != Color.BLACK) { //for every block that is not black 
						field[i][j] = Color.DARK_GRAY; //sets them to dark grey
					}
				}
			}
			return true;
		}
		else 
			return false;
	}
	
	public void raise() {
		origin.y += -1;
	}
}