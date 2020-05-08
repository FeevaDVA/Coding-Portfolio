package Tetris;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;

public class MainMenu extends JFrame {
	public static void main(String [] args) {
		MainMenu menu = new MainMenu();
		menu.setVisible(true);
	}
	public MainMenu() {
	//	TetrisGame tGame=new TetrisGame();
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(400,400);
		JButton btnStart = new JButton("Start"); //creates the button start
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TetrisGame game = new TetrisGame(); // makes a new game and starts it
				game.start(game); //starts the game
				dispose(); //removes this frame
			}
		});
		btnStart.setBounds(170, 48, 89, 23);
		panel.add(btnStart);
		
		JButton btnHighscores = new JButton("Highscores"); //creates the button called highscore
		btnHighscores.setBounds(170, 118, 89, 23);
		panel.add(btnHighscores);
		btnHighscores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					highScoreFrame nm = new highScoreFrame(); //creates the highscore frame
					nm.setLocationRelativeTo(null);
					dispose(); // then disposes of this frame
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		
		JButton btnNewButton = new JButton("Quit"); //creates a close button
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0); //closes everything
			}
		});
		btnNewButton.setBounds(170, 181, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblTetris = new JLabel("TETRIS"); //creates label called tetris
		lblTetris.setBounds(189, 11, 46, 14);
		panel.add(lblTetris);
		JFrame f=new JFrame("Menu"); //
		
	}

}
