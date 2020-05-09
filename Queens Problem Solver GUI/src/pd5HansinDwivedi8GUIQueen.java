import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class pd5HansinDwivedi8GUIQueen extends JPanel {
	private int[][] board;
	private int queens;
	private JButton[][] buttons;
	private final JLabel label;
	private final JTextField textfield;
    private JPanel center;
	
    public pd5HansinDwivedi8GUIQueen() { // creates the base Window
    	setLayout(new BorderLayout());
		JPanel north = new JPanel();
        north.setLayout(new FlowLayout());
		add(north, BorderLayout.NORTH);
       
		label = new JLabel("Time to place some queens! Type in the number of queens you want to place! ");
		north.add(label);
		
		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());
		add(south, BorderLayout.SOUTH);
        
		center = new JPanel();
		center.setLayout(new GridLayout());
        add(center, BorderLayout.CENTER);

		JButton placeQs = new JButton("Place Queens");
		placeQs.addActionListener(new Handler1());
		placeQs.setEnabled(true);
		south.add(placeQs);
		
		textfield = new JTextField(10);
		textfield.setEnabled(true);
		textfield.setText("0");
		south.add(textfield);	
 
    }

	public boolean placeQueens() {
		int col = 0;
		return placeHelper(col);
	}
	
	public boolean placeHelper(int col) {
		if(col >= queens)// Returns true if it reaches the end of the board
			return true;
		for (int i = 0; i < queens; i++) //Goes through every row of the board
			if(isSafe(i, col)) { //checks if its safe to place the queen
				board[i][col] = 1;//places queen the queen at location
				buttons[i][col].setBackground(Color.blue);
				buttons[i][col].setText("Queen");
				if(placeHelper(col+1)) { //recursive statement that moves the column to the right by 1
					return true;// will only be returned when the first if statement is met
				}
				board[i][col]=0;//If its not safe to place then it removes the queen
				buttons[i][col].setBackground(Color.white);
				buttons[i][col].setText("");
			}
			
		return false;//returns false if there are no safe places to place all the queens
	}
	
	public boolean isSafe(int row, int col) {
		for (int i = 0; i < col; i++) //checks to the left of the row
	        if (board[row][i] == 1) 
	            return false; 
	    for (int i=row, j=col; i>=0 && j>=0; i--, j--) //checks upper left diagonally
	        if (board[i][j] == 1) 
	            return false; 
	    for (int i=row, j=col; j>=0 && i<queens; i++, j--) //checks the lower left diagonal  
	        if (board[i][j] == 1) 
	            return false; 
	    return true;// returns true if no queen is found in the directions
	} 
	private class Handler1 implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
        	if(Integer.parseInt(textfield.getText()) <= 0) { //Checks if given int is within the correct parameters to make a grid
        		label.setText("Enter a valid number!");
        	}else {
        		center = new JPanel(new GridLayout(Integer.parseInt(textfield.getText()), Integer.parseInt(textfield.getText())));//sets the size of the grid to the given Int
        		buttons = new JButton[Integer.parseInt(textfield.getText())][Integer.parseInt(textfield.getText())];//sets the size to the given Int
        		board = new int[Integer.parseInt(textfield.getText())][Integer.parseInt(textfield.getText())];//sets the size to the given Int
        		queens = Integer.parseInt(textfield.getText()); // Sets queens to the given int
        		for(int i = 0; i<buttons.length; i++) { 
        			for(int k = 0; k<buttons[0].length; k++) {// goes through the button matrix and sets everyone to a new button then adds it to the JPanel 
        				buttons[i][k] = new JButton();
        				buttons[i][k].setBackground(Color.white);
        				center.add(buttons[i][k]);
        			}
        		}
        		add(center, BorderLayout.CENTER); //Places JPanel in the layout
        		if(placeQueens()) {
            		label.setText("You placed " + Integer.parseInt(textfield.getText()) + " queens"); 
            	}
            	else {
            		label.setText("No Solution!"); // prints only if there is no way of placing the amt of queens given
            	}
            	
        	}
        }
    }

	public static void main(String[]args) {
		JFrame frame = new JFrame("Hansin Dwivedi Queens");
        frame.setSize(550, 550);
        frame.setLocation(200, 100);
        frame.setContentPane(new pd5HansinDwivedi8GUIQueen());
        frame.setVisible(true);
	}
}


