package chess.main;

import chess.objects.Bishop;
import chess.objects.Board;
import chess.objects.King;
import chess.objects.Knight;
import chess.objects.PColor;
import chess.objects.Pawn;
import chess.objects.Piece;
import chess.objects.Queen;
import chess.objects.Rook;

public class Chess {

	/** This is the board that will be used within the chess game */
	private Board board;

	/*******************************************************************
	 * Default constructor - in the future we may add some parameters
	 * like opening from a new board, number of players, level of AI,
	 * ect.
	 ******************************************************************/
	public Chess() {
		board = new Board();
	}
	
	public void reset() {
		board = new Board();
	}

	/*******************************************************************
	 * Returns the board object
	 * 
	 * @return the board that is within the game
	 ******************************************************************/
	public Board getBoard() {
		return board;
	}

	/*******************************************************************
	 * Sets the board to the specified parameter
	 * 
	 * @param board is the board to set
	 ******************************************************************/
	public void setBoard(Board board) {
		this.board = board;
	}

	/*******************************************************************
	 * Makes it easier to get the piece
	 * 
	 * @param row the row of the piece
	 * @param col the col of the piece
	 * @return the piece at the specified cell
	 ******************************************************************/
	public Piece getPieceAt(int row, int col) {
		return this.getBoard().getCellAt(row, col).getChessPiece();
	}

	/*******************************************************************
	 * Simply moves one piece from one cell to the one specified. No
	 * checking should be done in this function, it is a basic helper
	 * method, so all checking if the piece can go in that cell should
	 * be done outside.
	 * 
	 * This will remove any piece in the second cell and replace it with
	 * the piece that was in the first cell.
	 * 
	 * @param r1 is the row of the first cell
	 * @param c1 is the col of the first cell
	 * @param r2 is the row of the second cell
	 * @param c2 is the col of the second cell
	 * @param piece is the piece to move
	 ******************************************************************/
	private void movePieceTo(int r1, int c1, int r2, int c2,
			Piece piece) {
		// Set the second cell to the pawn
		board.getCellAt(r2, c2).setChessPiece(piece);
		// Set the previous cell to null
		board.getCellAt(r1, c1).setChessPiece(null);
		// If it was the Piece's first move, set that it has moved
		if (!piece.isHasMoved()) {
			piece.setHasMoved(true);
		}
	}

	/*******************************************************************
	 * Helper method for the white pawns that checks for valid movement
	 * vertically. Just a note the white pawns are located in row 6 in
	 * the beginning. For this method the cols are the same, so we
	 * aren't capturing anything.
	 * 
	 * @param r1 is the row of the first cell
	 * @param c1 is the col of the first cell
	 * @param r2 is the row of the second cell
	 * @param c2 is the col of the second cell
	 * @param pawn is the pawn we are trying to move
	 * @return a boolean value whether the pawn was moved successfully
	 ******************************************************************/
	private boolean pawnWhiteVert(int r1, int c1, int r2, int c2,
			Pawn pawn) {
		if (r1 - r2 > 2) { // Pawns cannot move more than two rows.
			return false;
		}
		// Only gets here if it is 2 or less for the rows
		// If it has moved it can only go 1 row
		if (pawn.isHasMoved()) {
			if (r1 - r2 == 1 && getPieceAt(r2, c2) == null) {
				movePieceTo(r1, c1, r2, c2, pawn);
				return true;
			} else {
				return false;
			}
		} else { // If it hasn't moved it can go two rows

			if (r1 - r2 == 2) { // Pawn is moving two rows, check both
				if (getPieceAt(r2 + 1, c2) == null
						&& getPieceAt(r2, c2) == null) {
					movePieceTo(r1, c1, r2, c2, pawn);
					/** Need to check for passant here */
					// TODO add passant
					return true;
				} else {
					return false;
				}
			} else { // Pawn is only moving one row, it must be empty
				if (getPieceAt(r2, c2) == null) {
					movePieceTo(r1, c1, r2, c2, pawn);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/*******************************************************************
	 * This checks if the pawn is making a valid move diagonally.
	 * Currently there has to be a piece there, but later en passant,
	 * must be implemented
	 * 
	 * EN PASSANT NOT IMPLEMENTED YET
	 * 
	 * @param r1 is the row of the first cell
	 * @param c1 is the col of the first cell
	 * @param r2 is the row of the second cell
	 * @param c2 is the col of the second cell
	 * @param pawn is the pawn that we are checking
	 * @return a boolean value whether the pawn was moved
	 ******************************************************************/
	private boolean pawnWhiteDiag(int r1, int c1, int r2, int c2,
			Pawn pawn) {
		if (r1 - r2 != 1) {
			return false;
		} else {
			if (getPieceAt(r2, c2) != null
					&& getPieceAt(r2, c2).getColor() == PColor.Black) {
				movePieceTo(r1, c1, r2, c2, pawn);
				return true;
			} else {
				return false;
			}
		}
	}

	/*******************************************************************
	 * This checks if the black pawn is making a valid move vertically.
	 * 
	 * @param r1 is the row of the first cell
	 * @param c1 is the col of the first cell
	 * @param r2 is the row of the second cell
	 * @param c2 is the col of the second cell
	 * @param pawn is the pawn we are checking
	 * @return a boolean value whether the pawn was moved
	 ******************************************************************/
	private boolean pawnBlackVert(int r1, int c1, int r2, int c2,
			Pawn pawn) {
		// Pawns at most can only move 2 rows
		if (r2 - r1 > 2) {
			return false;
		}
		if (pawn.isHasMoved()) {
			if (r2 - r1 == 1 && getPieceAt(r2, c2) == null) {
				movePieceTo(r1, c1, r2, c2, pawn);
				return true;
			} else {
				return false;
			}
		} else { // If it hasn't moved it can go two rows
			if (r2 - r1 == 2) { // Pawn moving two rows check both
				if (getPieceAt(r2 - 1, c2) == null
						&& getPieceAt(r2, c2) == null) {
					movePieceTo(r1, c1, r2, c2, pawn);
					/** Need to check for passant here */
					// TODO add passant
					return true;
				} else {
					return false;
				}
			} else { // Pawn moving one row check if empty
				if (getPieceAt(r2, c2) == null) {
					movePieceTo(r1, c1, r2, c2, pawn);
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/*******************************************************************
	 * This checks if the black pawn is making a valid move diagonally.
	 * 
	 * EN PASSANT NOT IMPLEMENTED YET
	 * 
	 * @param r1 is the row of the first cell
	 * @param c1 is the col of the first cell
	 * @param r2 is the row of the second cell
	 * @param c2 is the col of the second cell
	 * @param pawn is the pawn that we are checking
	 * @return a boolean value whether the pawn was moved
	 ******************************************************************/
	private boolean pawnBlackDiag(int r1, int c1, int r2, int c2,
			Pawn pawn) {
		if (r2 - r1 != 1) {
			return false;
		} else {
			if (getPieceAt(r2, c2) != null
					&& getPieceAt(r2, c2).getColor() == PColor.White) {
				movePieceTo(r1, c1, r2, c2, pawn);
				return true;
			} else {
				return false;
			}
		}
	}

	/*******************************************************************
	 * Checks whether the pawn is making a valid move or not.
	 * 
	 * @param r1 is the row of the first Cell w/ Pawn
	 * @param c1 is the col of the first Cell w/ Pawn
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param pawn is the pawn we are checking
	 * @return a boolean value whether the pawn was moved
	 ******************************************************************/
	private boolean checkPawn(int r1, int c1, int r2, int c2,
			Pawn pawn) {
		if (pawn.getColor() == PColor.White) {
			if (c1 == c2) { // Not moving diagonally
				return pawnWhiteVert(r1, c1, r2, c2, pawn);
			} else { // It is moving diagonally
				if (Math.abs(c1 - c2) == 1) { // Can only move 1 col
					return pawnWhiteDiag(r1, c1, r2, c2, pawn);
				} else {
					return false;
				}
			}
		} else { // The pawn is black
			if (c1 == c2) { // Not moving diagonally
				return pawnBlackVert(r1, c1, r2, c2, pawn);
			} else { // It is moving diagonally
				if (Math.abs(c1 - c2) == 1) {
					return pawnBlackDiag(r1, c1, r2, c2, pawn);
				} else {
					return false;
				}
			}
		}
	}

	/*******************************************************************
	 * Checks to see if a move containing the Knight is a valid move. To
	 * be valid the move must either be 2 rows and 1 col over OR be 2
	 * cols and 1 row over. Also, it must be a different color the the
	 * knight passed as a parameter.
	 * 
	 * @param r1 is the row of the first Cell w/ Knight
	 * @param c1 is the col of the first Cell w/ Knight
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param knight is the Knight we are checking
	 * @return a boolean value of whether the Knight was moved
	 ******************************************************************/
	private boolean checkKnight(int r1, int c1, int r2, int c2,
			Knight knight) {

		// First need to calc the difference for the rows and cols
		if ((Math.abs(r1 - r2) == 2 && Math.abs(c1 - c2) == 1)
				|| (Math.abs(r1 - r2) == 1
						&& Math.abs(c1 - c2) == 2)) {
			// Need to check the cell it is moving to
			if (getPieceAt(r2, c2) == null || knight
					.getColor() != getPieceAt(r2, c2).getColor()) {
				// Valid move, we can move the Knight
				movePieceTo(r1, c1, r2, c2, knight);
				return true;

			} else {
				// Invalid move, it contains the same color as Knight
				return false;
			}

		} else {
			// Invalid move pattern
			return false;
		}
	}

	/*******************************************************************
	 * Checks the movement of the Bishop Piece
	 * 
	 * @param r1 is the row of the first Cell w/ Bishop
	 * @param c1 is the col of the first Cell w/ Bishop
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param bishop is the Bishop that we are checking
	 * @return a boolean value whether the Bishop was moved
	 ******************************************************************/
	private boolean checkBishop(int r1, int c1, int r2, int c2,
			Bishop bishop) {
		// Bishops row and col must change by same amount
		if (Math.abs(r1 - r2) == Math.abs(c1 - c2)) {
			return checkDiagonal(r1, c1, r2, c2, bishop);

		} else { // Not a diagonal move
			return false;
		}
	}

	/*******************************************************************
	 * Checks the movement of the Rook in it's four possible directions.
	 * Up, Right, Down, and Left. For each move, either the row or the
	 * col can't change while the other one does
	 * 
	 * Uses the checkLateral method.
	 * 
	 * @param r1 is the row of the first Cell w/ Rook
	 * @param c1 is the col of the first Cell w/ Rook
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param rook is the Rook that we are checking
	 * @return a boolean value whether the Rook can be moved
	 ******************************************************************/
	private boolean checkRook(int r1, int c1, int r2, int c2,
			Rook rook) {
		
		
		boolean RowChanged = false;
		boolean ColChanged = false;
		
		
		if (Math.abs(r2-r1) > 0){
			System.out.println("The Row Has Changed");
			RowChanged = true;
		}
		if (Math.abs(c2-c1) > 0){
			System.out.println("The Column Has Changed");
			ColChanged = true;
		}
		
		if ((Math.abs(r2-r1) > 0) && (Math.abs(c2-c1) > 0)){
			System.out.println("INVALID MOVE");
			return false;
		}
		
		if (RowChanged){
			
			int loops = 0;
			int max = 0;
			if (r1 > r2){
				loops = r2;
				max = r1;
			}
			else{
			loops = r1;
			max = r2;
			}
			
			//max -= 1;
			System.out.println("START ROW: " + loops + " AND END ROW: " + max);
			for (int x = loops; x<=max; x++)
			{
				System.out.println("CHECKING ROW: " + x + " AND COLUMN: " + c1);
				if (x != r1 && x != r2){
					if (board.getCellAt(x, c1).getChessPiece() != null){
						System.out.println("Not an empty Space for Row");
						return false;
					}
				}
			}
			return true;
		}
		else if (ColChanged){
			
			int loops2 = 0;
			int max2 = 0;
			
			if (c1 > c2){
				loops2 = c2;
				max2 = c1;
			}
			else{
				loops2 = c1;
				max2 = c2;
			}
			for (int y = loops2+1; y<max2; y++){
				
				if (board.getCellAt(r1, y).getChessPiece() != null){
					System.out.println("Empty Space for Col");
					return false;
				}
				
			}
 
			return true;
		}
		
		System.out.println("INVALID MOVE");
		return false;
	}

	/*******************************************************************
	 * Checks the movement of the Queen, it uses the check diagonal and
	 * check Lateral
	 * 
	 * @param r1 is the row of the first Cell w/ Queen
	 * @param c1 is the col of the first Cell w/ Queen
	 * @param r2 is the row of the second Cell
	 * @param c2 is the row of the second Cell
	 * @param queen is the Queen that we are checking
	 * @return a boolean value whether the Queen was moved
	 ******************************************************************/
	private boolean checkQueen(int r1, int c1, int r2, int c2,
			Queen queen) {

		return false;
	}

	/*******************************************************************
	 * Checks the movement of the King, similar to the Queen just the
	 * King can only move one spot. Aren't checking for Check or
	 * Checkmate, but if it is captured the game should end.
	 * 
	 * @param r1 is the row of the first Cell w/ King
	 * @param c1 is the col of the first Cell w/ King
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param king is the King that we are checking
	 * @return a boolean value whether the King was moved
	 ******************************************************************/
	private boolean checkKing(int r1, int c1, int r2, int c2,
			King king) {

		return false;
	}

	/*******************************************************************
	 * Checks the Lateral(PROPER WORD? Couldn't think of what to call
	 * it) movement of the piece for its movement UP, RIGHT, DOWN, and
	 * LEFT
	 * 
	 * @param r1 is the row for the first Cell
	 * @param c1 is the col for the first Cell
	 * @param r2 is the row for the second Cell
	 * @param c2 is the col for the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean checkLateral(int r1, int c1, int r2, int c2,
			Piece piece) {
		// I believe that the inequalities are correct, need to test
		if(r1 > r2 && c1 == c2) { // Check UP
			return checkUp(r1, c1, r2, c2, piece);
		} else if(r1 == r2 && c1 < c2) { // Check RIGHT
			return checkRight(r1, c1, r2, c2, piece);
		} else if(r1 < r2 && c1 == c2) { // Check DOWN
			return checkDown(r1, c1, r2, c2, piece);
		} else if(r1 == r2 && c1 > c2) { // Check LEFT
			return checkLeft(r1, c1, r2, c2, piece);
		}
		return false;
	}

	/*******************************************************************
	 * Checks the Piece's movement UP
	 * 
	 * @param r1 is the row for the first Cell
	 * @param c1 is the col for the first Cell
	 * @param r2 is the row for the second Cell
	 * @param c2 is the col for the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean checkUp(int r1, int c1, int r2, int c2,
			Piece piece) {

		return false;
	}

	/*******************************************************************
	 * Checks the Piece's movement to the RIGHT
	 * 
	 * @param r1 is the row for the first Cell
	 * @param c1 is the col for the first Cell
	 * @param r2 is the row for the second Cell
	 * @param c2 is the col for the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean checkRight(int r1, int c1, int r2, int c2,
			Piece piece) {

		return false;
	}

	/*******************************************************************
	 * Checks the Piece's movement DOWN
	 * 
	 * @param r1 is the row for the first Cell
	 * @param c1 is the col for the first Cell
	 * @param r2 is the row for the second Cell
	 * @param c2 is the col for the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean checkDown(int r1, int c1, int r2, int c2,
			Piece piece) {

		return false;
	}

	/*******************************************************************
	 * Checks the Piece's movement LEFT
	 * 
	 * @param r1 is the row for the first Cell
	 * @param c1 is the col for the first Cell
	 * @param r2 is the row for the second Cell
	 * @param c2 is the col for the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean checkLeft(int r1, int c1, int r2, int c2,
			Piece piece) {

		return false;
	}

	/*******************************************************************
	 * Helper method that checks the piece's diagonal movement
	 * 
	 * @param r1 is the row of the first Cell
	 * @param c1 is the col of the first Cell
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param piece is the Piece we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean checkDiagonal(int r1, int c1, int r2, int c2,
			Piece piece) {
		if (r1 > r2 && c1 < c2) { // Up and right
			return cUpRight(r1, c1, r2, c2, piece);
		} else if (r1 < r2 && c1 < c2) { // Down and right
			return cDownRight(r1, c1, r2, c2, piece);
		} else if (r1 < r2 && c1 > c2) { // Down and left
			return cDownLeft(r1, c1, r2, c2, piece);
		} else if (r1 > r2 && c1 > c2) { // Up and left
			return cUpLeft(r1, c1, r2, c2, piece);
		}
		return false;
	}

	/*******************************************************************
	 * Check the Piece's movement up and to the right
	 * 
	 * @param r1 is the row of the first Cell
	 * @param c1 is the col of the first Cell
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean cUpRight(int r1, int c1, int r2, int c2,
			Piece piece) {
		for (int x = r1 - 1, y = c1 + 1; x >= r2
				&& y <= c2; x--, y++) {
			if (x != r2) { // Check all cells inbetween
				if (getPieceAt(x, y) != null) {
					return false;
				}
			} else { // At the final cell check its piece
				if (getPieceAt(x, y) == null || getPieceAt(x, y)
						.getColor() != piece.getColor()) {
					movePieceTo(r1, c1, r2, c2, piece);
					return true;
				}
			}
		}
		return false;
	}

	/*******************************************************************
	 * Check the Piece's movement down and to the right
	 * 
	 * @param r1 is the row of the first Cell
	 * @param c1 is the col of the first Cell
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean cDownRight(int r1, int c1, int r2, int c2,
			Piece piece) {
		for (int x = r1 + 1, y = c1 + 1; x <= r2
				&& y <= c2; x++, y++) {
			if (x != r2) { // Check all cells inbetween
				if (getPieceAt(x, y) != null) {
					return false;
				}
			} else { // At the final cell check its piece
				if (getPieceAt(x, y) == null || getPieceAt(x, y)
						.getColor() != piece.getColor()) {
					movePieceTo(r1, c1, r2, c2, piece);
					return true;
				}
			}
		}
		return false;
	}

	/*******************************************************************
	 * Check the Piece's movement down and to the left
	 * 
	 * @param r1 is the row of the first Cell
	 * @param c1 is the col of the first Cell
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean cDownLeft(int r1, int c1, int r2, int c2,
			Piece piece) {
		for (int x = r1 + 1, y = c1 - 1; x <= r2
				&& y >= c2; x++, y--) {
			if (x != r2) { // Check all cells inbetween
				if (getPieceAt(x, y) != null) {
					return false;
				}
			} else { // At the final cell check its piece
				if (getPieceAt(x, y) == null || getPieceAt(x, y)
						.getColor() != piece.getColor()) {
					movePieceTo(r1, c1, r2, c2, piece);
					return true;
				}
			}
		}
		return false;
	}

	/*******************************************************************
	 * Check the Piece's movement up and to the left
	 * 
	 * @param r1 is the row of the first Cell
	 * @param c1 is the col of the first Cell
	 * @param r2 is the row of the second Cell
	 * @param c2 is the col of the second Cell
	 * @param piece is the Piece that we are checking
	 * @return a boolean value whether the Piece was moved
	 ******************************************************************/
	private boolean cUpLeft(int r1, int c1, int r2, int c2,
			Piece piece) {
		for (int x = r1 - 1, y = c1 - 1; x >= r2
				&& y >= c2; x--, y--) {
			if (x != r2) { // Check all cells inbetween
				if (getPieceAt(x, y) != null) {
					return false;
				}
			} else { // At the final cell check its piece
				if (getPieceAt(x, y) == null || getPieceAt(x, y)
						.getColor() != piece.getColor()) {
					movePieceTo(r1, c1, r2, c2, piece);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method is not done yet, but is just to test how to check the
	 * move for the pieces, right now it only works for Pawns and only
	 * cares about vertical movement, no collision or anything, but it
	 * works
	 */
	public boolean checkMove(int r1, int c1, int r2, int c2,
			Piece piece) {
		// Relatively decent checking style for pawn
		if (piece instanceof Pawn) {
			if (checkPawn(r1, c1, r2, c2, (Pawn) piece)) {
				System.out.println("VALID MOVE");
				return true;
			} else {
				System.out.println("INVALID MOVE");
				return false;
			}
		} else if (piece instanceof Knight) {
			System.out.println("KNIGHT");
			if (checkKnight(r1, c1, r2, c2, (Knight) piece)) {
				System.out.println("VALID MOVE KNIGHT");
				return true;
			} else {
				System.out.println("INVALID MOVE KNIGHT");
				return false;
			}
		} else if (piece instanceof Bishop) {
			System.out.println("BISHOP");
			if (checkBishop(r1, c1, r2, c2, (Bishop) piece)) {
				System.out.println("VALID MOVE BISHOP");
				return true;
			} else {
				System.out.println("INVALID MOVE BISHOP");
				return false;
			}

		} else if (piece instanceof Rook) {
			System.out.println("ROOK");
			if (checkRook(r1,c1,r2,c2, (Rook) piece)){
				System.out.println("VALID MOVE ROOK");
				return true;
			}
			else{
				System.out.println("INVALID MOVE ROOK");
				return false;
			}
		} else if (piece instanceof Queen) {
			System.out.println("QUEEN");
		} else if (piece instanceof King) {
			System.out.println("KING");
		}
		System.out.println("Something went wrong......");
		return false;
	}

}
