package a10;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by chloe on 11/20/2016.
 */
public class LightsOut extends JFrame implements ActionListener {

	// Instance Variables 
	private LightButton[][] buttonArray;
	private int count;
	private JLabel label = new JLabel("Move count:0");

	/**
	 * Constructs the GUI grid, restart button, and solve button. also creates a
	 * random solvable grid
	 */
	public LightsOut() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//creates GUI grid
		JPanel completePanel = new JPanel();
		completePanel.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 5));

		buttonArray = new LightButton[5][5];
		//adds actionListners to all the buttons
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				LightButton lightButton = new LightButton(i, j);
				panel.add(lightButton);
				buttonArray[i][j] = lightButton;
				lightButton.addActionListener(this);
			}
		}

		// creates restart button
		JPanel panel1 = new JPanel();
		JButton button = new JButton("Restart");
		panel1.add(button);
		button.addActionListener(this);

		// creates solve button
		JButton button1 = new JButton("Solve");
		panel1.add(button1);
		button1.addActionListener(this);

		// adds label to the panel
		panel1.add(label);

		//formats panel
		completePanel.add(panel, BorderLayout.CENTER);
		completePanel.add(panel1, BorderLayout.SOUTH);

		// blacks out the board
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				buttonArray[i][j].blackOut();
			}
		}
		// does 10 random moves
		for (int i = 0; i < 10; i++) {
			Random random = new Random();
			int x = random.nextInt(5);
			int y = random.nextInt(5);
			buttonArray[x][y].changeColor();
			LightButton lightButton = new LightButton(x, y);
			neighbors(lightButton);
			lightButton.changeColor();
		}

		setTitle("Lights Out");
		setContentPane(completePanel);
		setPreferredSize(new Dimension(300, 300));
		pack();
	}

	/**
	 * implements the actionListeners and responds to buttons being clicked.
	 * First checks to see if restart has been clicked
	 * then checks if the solver has been clicked  
	 * otherwise deals with the grid buttons
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		//Restarts a new board
		if (button.getText().equals("Restart")) {
			restart();
			count = 0;
			label.setText("Move count: " + count);
		} 
		// Solves the board
		else if (button.getText().equals("Solve")) {
			solver();
			while (blackButtons() != 25) {
				solveBottom();
				solver();
			}
			win();
		} 
		//Otherwise causes the button to beep and toggle the neighbors
		//and increase the move count
		else {
			LightButton lightButton = (LightButton) e.getSource();
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			toolkit.beep();
			neighbors(lightButton);
			lightButton.changeColor();
			count++;
			label.setText("Move count: " + count);

			if (blackButtons() == 25) {
				win();
			}

		}

	}

	/**
	 * When the restart button is pressed it blacks out the board and the does
	 * 10 random moves to ensure the board in solvable.
	 * 
	 */
	public void restart() {
		if (this.isEnabled()) {
			// blacks out each button
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					buttonArray[i][j].blackOut();
				}
			}
			// does 10 random moves
			for (int i = 0; i < 10; i++) {
				Random random = new Random();
				int x = random.nextInt(5);
				int y = random.nextInt(5);
				buttonArray[x][y].changeColor();
				LightButton lightButton = new LightButton(x, y);
				neighbors(lightButton);
				lightButton.changeColor();
			}
		}
	}

	/**
	 * Finds the white buttons and then toggles the button right below 
	 * said white square.
	 */
	public void solver() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				if (buttonArray[i][j].getBackground().equals(Color.WHITE)) {
					buttonArray[i + 1][j].changeColor();
					neighbors(buttonArray[i + 1][j]);
				}
			}
		}
	}

	/**
	 * Solves the bottom row of the grid with the 7 different possible solutions
	 */
	public void solveBottom() {
		// Bottom row: 0---0 Top row: 00---
		if (buttonArray[4][0].getBackground().equals(Color.WHITE)
				&& buttonArray[4][4].getBackground().equals(Color.WHITE)) {
			buttonArray[0][0].changeColor();
			neighbors(buttonArray[0][0]);
			buttonArray[0][1].changeColor();
			neighbors(buttonArray[0][1]);
		}
		// Bottom row:-0-0- Top row:0--0-
		else if (buttonArray[4][1].getBackground().equals(Color.WHITE)
				&& buttonArray[4][3].getBackground().equals(Color.WHITE)) {
			buttonArray[0][0].changeColor();
			neighbors(buttonArray[0][0]);
			buttonArray[0][3].changeColor();
			neighbors(buttonArray[0][3]);
		}
		// Bottom row: 000-- Top row:-0---
		else if (buttonArray[4][3].getBackground().equals(Color.BLACK)
				&& buttonArray[4][4].getBackground().equals(Color.BLACK)) {
			buttonArray[0][1].changeColor();
			neighbors(buttonArray[0][1]);
		}
		// Bottom row: --000 Top row:---0-
		else if (buttonArray[4][0].getBackground().equals(Color.BLACK)
				&& buttonArray[4][1].getBackground().equals(Color.BLACK)) {
			buttonArray[0][3].changeColor();
			neighbors(buttonArray[0][3]);
		}
		// Bottom row:0-00- Top row:----0
		else if (buttonArray[4][1].getBackground().equals(Color.BLACK)
				&& buttonArray[4][4].getBackground().equals(Color.BLACK)) {
			buttonArray[0][4].changeColor();
			neighbors(buttonArray[0][4]);
		}
		// Bottom row:-00-0 Top row:0----
		else if (buttonArray[4][0].getBackground().equals(Color.BLACK)
				&& buttonArray[4][3].getBackground().equals(Color.BLACK)) {
			buttonArray[0][0].changeColor();
			neighbors(buttonArray[0][0]);
		}
		// Bottom row: 00-00 Top row:--0--
		else if (buttonArray[4][2].getBackground().equals(Color.BLACK)) {
			buttonArray[0][2].changeColor();
			neighbors(buttonArray[0][2]);
		}
	}

	/**
	 * Displays a dialog box that tells the user they've won.
	 */
	public void win() {
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, "You Win!");
	}

	/**
	 * Counts the number of black buttons on the board
	 */
	public int blackButtons() {
		int blackButton = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (buttonArray[i][j].getBackground().equals(Color.BLACK)) {
					blackButton++;
				}
			}
		}
		return blackButton;
	}

	/**
	 * Neighbors finds the north, south, east and west buttons of the button
	 * that was pressed. It also handles all edges and corner cases.
	 * 
	 * @param button
	 */
	public void neighbors(LightButton button) {
		// get row and column
		int row = button.getRow();
		int column = button.getColumn();

		// checks for the top left corner
		if (row == 0 && column == 0) {
			buttonArray[row][column + 1].changeColor();
			buttonArray[row + 1][column].changeColor();
		}
		// checks for the top right corner
		else if (row == 0 && column == 4) {
			buttonArray[row][column - 1].changeColor();
			buttonArray[row + 1][column].changeColor();
		}
		// checks for the bottom left corner
		else if (row == 4 && column == 0) {
			buttonArray[row][column + 1].changeColor();
			buttonArray[row - 1][column].changeColor();
		}
		// checks for the bottom right corner
		else if (row == 4 && column == 4) {
			buttonArray[row][column - 1].changeColor();
			buttonArray[row - 1][column].changeColor();
		}
		// checks for the top row
		else if (row == 0) {
			buttonArray[row][column + 1].changeColor();
			buttonArray[row][column - 1].changeColor();
			buttonArray[row + 1][column].changeColor();
		}
		// checks for the bottom row
		else if (row == 4) {
			buttonArray[row][column + 1].changeColor();
			buttonArray[row][column - 1].changeColor();
			buttonArray[row - 1][column].changeColor();
		}
		// checks for the right edge
		else if (column == 0) {
			buttonArray[row][column + 1].changeColor();
			buttonArray[row + 1][column].changeColor();
			buttonArray[row - 1][column].changeColor();
		}
		// checks for the left edge
		else if (column == 4) {
			buttonArray[row][column - 1].changeColor();
			buttonArray[row + 1][column].changeColor();
			buttonArray[row - 1][column].changeColor();
		}
		// for everything else
		else {
			buttonArray[row][column + 1].changeColor();
			buttonArray[row][column - 1].changeColor();
			buttonArray[row + 1][column].changeColor();
			buttonArray[row - 1][column].changeColor();
		}
	}

	/**
	 * Runs the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Create and launch the GUI
		LightsOut lightsOut = new LightsOut();
		lightsOut.setVisible(true);
	}
}
