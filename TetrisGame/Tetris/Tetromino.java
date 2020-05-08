package Tetris;

public class Tetromino {
	protected enum Block { EmptyBlock, SBlock, LBlock, LineBlock, TBlock, SquareBlock, ZBlock, ReverseLBlock };
	private Block piece;
	private int [][] coords;
	private int [][][] coordTable;
	private int size=3;
	public Tetromino() {
		coords = new int[4][2]; //creates the shape in a 4 * 2 space
		setShape(Block.EmptyBlock); 
	}
	public void setShape(Block shape) {
		coordTable = new int[][][]
				{ { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, //Empty
				{ { 1, 0 }, { 2, 0 }, { 0, 1 }, { 1, 1 } }, //S
				{ { 0, 1 }, { 1, 1 }, { 2, 1 }, { 2, 0 } }, // L
				{ { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 } }, //Line
				{ { 1, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 } }, //T
				{ { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 1 } }, //Square
				{ { 0, 0 }, { 1, 0 }, { 1, 1 }, { 2, 1 } }, //Z
				{ { 0, 1 }, { 1, 1 }, { 2, 1 }, { 2, 2 } } }; //Reverse L
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 2; ++j) //places the coordinates from the coordinate table into the coords array
				coords[i][j] = coordTable[shape.ordinal()][i][j]; //places the coordinates of the shape into the coord array
		piece = shape; //sets the piece to the shape
	}
	
	public Block getShape() {
		return piece; // returns the block shape
	}
	
	public int [][] getCoords(){
		return coords; //returns the coord array
	}
	
	public void setRandomShape() {
		int r = (int)(Math.random()*7)+1; //sets the shape to a random shape from the block array
		Block[] b = Block.values();
		setShape(b[r]); //sets the shape
	}
	
	public int getX(int i) {
		return coords[i][0];  //gets the x of the coords in coord table
	}
	
	public int getY(int i) {
		return coords[i][1];  //gets the y of the coords in coord table
	}

	public void setX(int i, int x) {
		coords[i][0] = x;//sets the x value of the coordinate in the coordinate table
	}
	
	public void setY(int i, int y) {
		coords[i][1] = y; //sets the y value of the coordinate in the coordinate table
	}

	public Block getType() {
		return piece; // returns the current piece type
	}

	public Tetromino rotateCW() {
		if(piece == Block.SquareBlock) //square block rotations are all the same 
			return this;
		Tetromino t = new Tetromino();
		t.piece = piece;
		if(piece == Block.LineBlock) //the line block is the only piece with a size of 4
			size=4;
		for (int i = 0; i < 4; ++i) { //matrix rotation according to the size of the piece
			t.setX(i, 1-(getY(i)-(size-2))); 
			t.setY(i, getX(i)); 
		}
		return t;
	}
	public int compareTo(Tetromino other) {
		if(this.getShape().name().equals(other.getShape().name())) { //compares the names to each other
			return -1; //if the names equal each other returns -1 
		} else {
			return 1;// if they are different returns 1
		}
	}
}
