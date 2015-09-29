package chess.controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chess.gui.ChessGUI;
import chess.main.Chess;

public class ChessController {

	/** Is the ChessGUI to add to the Controller */
	private ChessGUI gui;

	/** Is the Chess to add to the controller */
	private Chess game;

	private int r1 = -1, c1 = -1, r2 = -1, c2 = -1;

	private boolean firstClick;

	/*******************************************************************
	 * Constructor for the Controller part of the MVC Takes in both the
	 * GUI and the game as parameters and adds the ChessListener onto
	 * the GUI.
	 * 
	 * The design works like so:
	 * 
	 * Chess.java and anything in chess.objects holds all logic
	 * 
	 * ChessController.java communicates between Chess and GUI
	 * 
	 * ChessGUI simply displays the view, the controller updates it
	 * 
	 * @param gui
	 *            is the ChessGUI to add
	 * @param game
	 *            is the Chess to add
	 ******************************************************************/
	public ChessController(ChessGUI gui, Chess game) {
		this.gui = gui;
		this.game = game;
		this.firstClick = true;

		this.gui.addChessListener(new ChessListener());
		// TODO Auto-generated constructor stub
	}

	private void findCell(ActionEvent e) {
		for (int row = 0; row < 8; ++row) {
			for (int col = 0; col < 8; ++col) {
				if (e.getSource() == gui.getBoard()[row][col]) {
					if (firstClick) {
						r1 = row;
						c1 = col;
						firstClick = false;
					} else {
						r2 = row;
						c2 = col;
						firstClick = true;
					}
					System.out.println("The Row is: " + (row + 1)
							+ " and the Column is: " + (col + 1));
				}
			}
		}
	}

	class ChessListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Very basic method to find the button and peice and checks
			// if it can move there
			if (firstClick) {
				findCell(e);
				// System.out.println(r1 + 1 + " " + (c1 +1));
			} else {
				findCell(e);
				System.out.println(r2 + 1 + " " + (c2 + 1));
				if (game.checkMove(r1, c1, r2, c2,
						game.getPieceAt(r1, c1))) {
					gui.getButtonAt(r1, c1).setText("");
					if (game.getPieceAt(r2, c2).getName() != null) {
						gui.getButtonAt(r2, c2).setText(
								game.getPieceAt(r2, c2).getIcon());
						gui.revalidate();
					}
				}
			}

			// Simply going to search through the 2D array of buttons
			// and
			// print out the location of them
			// for (int row = 0; row < 8; ++row) {
			// for (int col = 0; col < 8; ++col) {
			// if (e.getSource() == gui.getBoard()[row][col]) {
			// System.out.println("The Row is: " + (row + 1)
			// + " and the Column is: " + (col + 1));
			// }
			// }
			// }

		}

	}

}