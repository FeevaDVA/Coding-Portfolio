package GameOfLife;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JPanel;
// used a matrix to represent the board to make it easier to check and count neigbors also make it easy to modify
public class CellMatrix {
	
	private int height, length;
	private int[][] board;
	
	public CellMatrix(int w, int h) {
		height = h;
		length = w;
		
		board = new int[length][height];
		for(int i = 0; i < board.length; i++)
			for(int n = 0; n< board[0].length; n++)
				board[i][n] = 0;
	}

	public int[][] getBoard(){
		return board;
	}
	
	//gernerates the next generations of cells based on the rules of the game of life
	public void nextGen() {
		int[][] newBoard = new int[length][height];
		for(int x = 0; x<length; x++)
			for(int y = 0; y<height; y++) {
				if(board[x][y] == 1) {
					if(numOfNeighbors(x, y) <= 1)
						newBoard[x][y] = 0;
					else if(numOfNeighbors(x, y) >= 4)
						newBoard[x][y] = 0;
					else
						newBoard[x][y] = 1;
				} else if(board[x][y] == 0) {
					if(numOfNeighbors(x, y) == 3)
						newBoard[x][y] = 1;
				}
			}
		board = newBoard;
	}
	
	//checks the amount of  neighbors around a given cell coordinate
	public int numOfNeighbors(int x, int y) {
		int number = 0;
		
		if(x-1>0 && y-1>0) //check 1
			if(board[x-1][y-1] == 1)
				number++;
		if(x-1 > 0 && y+1 < height) // check 2
			if(board[x-1][y+1] == 1)
				number++;
		if(x-1>0) // check 3
			if(board[x-1][y] == 1)
				number++;
		if(y-1>0) // check 4
			if(board[x][y-1] == 1)
				number++;
		if(x+1<length && y+1 < height) // check 5
			if(board[x+1][y+1] == 1)
				number++;
		if(x+1<length && y-1 > 0) //check 6
			if(board[x+1][y-1] == 1)
				number++;
		if(x+1<length) //check 7
			if(board[x+1][y] == 1)
				number++;
		if(y+1<height) //check 8
			if(board[x][y+1] == 1)
				number++;
		
		return number;
	}
	
	//used to change
	public void swapCell(int x, int y) {
		if(board[x][y] == 1)
			board[x][y] = 0;
		else
			board[x][y] = 1;
	}
	
	//places a cell at the given coordinates in the matrix
	public void placeCell(int x, int y) {
		if(board[x][y] == 1)
			board[x][y] = 0;
		else
			board[x][y] = 1;
	}
	
	
}
