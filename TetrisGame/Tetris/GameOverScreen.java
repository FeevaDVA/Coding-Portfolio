package Tetris;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;


public class GameOverScreen extends JFrame {
	private GameBoard game1;
	private TetrisGame game2;
	private JPanel contentPane;
	private JTextField txtEnterYourName;
	private JButton btnContinue;
	private JLabel lblEnterThreeCharacters;
	/**
	 * Create the frame.
	 */
	public GameOverScreen(TetrisGame g, GameBoard g2) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtEnterYourName = new JTextField(10); //creates a text field to enter the three letter names
		
		txtEnterYourName.setBounds(112, 21, 186, 32);
		contentPane.add(txtEnterYourName);
		
		JButton btnNewGame = new JButton("New Game"); //creates a button to launch another game
		btnNewGame.setBounds(0, 93, 141, 35);
		contentPane.add(btnNewGame);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtEnterYourName.getText();
				if(name.matches("^[a-zA-Z]+$") && name.length()==3) { //checks the name to see if it is only characters and if the length is 3
					String score = g2.getScore() + " " + txtEnterYourName.getText();
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("Highscores.txt", true)); //creates a writer to append a new line to the text file
						bw.newLine(); //creates a new line
						bw.write(score); //writes the score into the file on the new line
						bw.close(); //closes the writer
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					g.dispose(); // removes the last game
					TetrisGame tGame = new TetrisGame(); // opens a new game
					tGame.start(tGame); //starts the game
					dispose();// removes the game over screen
				}
			}
		});
		
		btnContinue = new JButton("Continue"); //adds a continue button that goes back to the main menu
		btnContinue.setBounds(283, 93, 141, 35);
		contentPane.add(btnContinue);
		
		lblEnterThreeCharacters = new JLabel("Enter ONLY Three Characters with no caps");
		lblEnterThreeCharacters.setBounds(105, 50, 287, 32);
		contentPane.add(lblEnterThreeCharacters);
		
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtEnterYourName.getText();
				if(name.matches("^[a-zA-Z]+$") && name.length()==3) { //checks the name to see if it is only characters and if the length is 3
					String score = g2.getScore() + " " + txtEnterYourName.getText();
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter("Highscores.txt", true));//creates a writer to append a new line to the text file
						bw.newLine();//writes the score into the file on the new line
						bw.write(score);//creates a new line
						bw.close();//closes the writer
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					g.dispose();// removes the last game
					MainMenu mm = new MainMenu(); //creates a new main menu
					mm.setVisible(true); //sets it visible
					mm.setLocationRelativeTo(null);
					dispose(); //removes this frame
				}
			}
		});
	}
}
