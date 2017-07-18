package a10;

import java.awt.Color;

import javax.swing.*;

/**
 * Created by chloe on 11/20/2016.
 */
public class LightButton extends JButton {

	//instance variables
	private int row;
	private int column;
	/**
	 * constructs the LightButton object
	 * 
	 * @param row
	 * @param column
	 */
	public LightButton(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Changes the color of the button when that button is clicked
	 */
	public void changeColor() {
		if (this.isEnabled()) {
			if (this.getBackground().equals(Color.BLACK)) {
				setBackground(Color.WHITE);
			} else {
				setBackground(Color.BLACK);
			}
		} else {
			setBackground(Color.WHITE);
		}
	}

	/**
	 * Sets the button to black to black out the board
	 */
	public void blackOut() {
		this.setBackground(Color.BLACK);
	}

	/**
	 * gets the row of the button
	 * 
	 * @return int row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * gets the column of the button
	 * 
	 * @return int column
	 */
	public int getColumn() {
		return column;
	}


}