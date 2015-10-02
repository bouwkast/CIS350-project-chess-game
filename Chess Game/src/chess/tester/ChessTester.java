package chess.tester;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chess.main.Chess;
import chess.objects.Bishop;
import chess.objects.Board;
import chess.objects.King;
import chess.objects.Knight;
import chess.objects.PColor;
import chess.objects.Pawn;
import chess.objects.Queen;
import chess.objects.Rook;

public class ChessTester {

	Chess game = new Chess();
	Board boardTest = new Board();

	Pawn whitePawn = (Pawn) game.getPieceAt(6, 1);
	Pawn blackPawn = (Pawn) game.getPieceAt(1, 1);

	@Test
	public void checkPawnPColorWhite() {
		Pawn pW = new Pawn(PColor.White);
		assertTrue(pW.getColor() == PColor.White);
	}

	@Test
	public void checkPawnPColorBlack() {
		Pawn pB = new Pawn(PColor.Black);
		assertTrue(pB.getColor() == PColor.Black);
	}

	@Test
	public void checkPawnName() {
		Pawn p = new Pawn(PColor.Black);
		assertTrue(p.getName().equals("Pawn"));
	}

	@Test
	public void checkPawnIconWhite() {
		Pawn pW = new Pawn(PColor.White);
		assertTrue(pW.getIcon().equals("\u2659"));
	}

	@Test
	public void checkPawnIconBlack() {
		Pawn pB = new Pawn(PColor.Black);
		assertTrue(pB.getIcon().equals("\u265f"));
	}

	@Test
	public void checkPawnIsAlive() {
		Pawn p = new Pawn(PColor.Black);
		assertTrue(p.isAlive());
	}

	@Test
	public void checkPawnHasMovedInitialStatus() {
		Pawn p = new Pawn(PColor.Black);
		assertFalse(p.isHasMoved());
	}

	@Test
	public void checkWhitePawnsPosition() {
		// White Pawns are in row 6, col 0-7
		game = new Chess();
		boolean isCorrect = false;
		for (int i = 0; i < 8; i++) {
			boolean isPawn = game.getPieceAt(6, i) instanceof Pawn;
			boolean isWhite = game.getPieceAt(6, i).getColor() == PColor.White;
			isCorrect = (isPawn && isWhite);
			if (!isCorrect)
				return;
		}

		assertTrue(isCorrect);
	}

	@Test
	public void checkWhiteKnightsPosition() {
		// White Knights are at row 7, col 1 and 6
		game = new Chess();
		Knight leftKnight = (Knight) game.getPieceAt(7, 1);
		Knight rightKnight = (Knight) game.getPieceAt(7, 6);
		boolean isLeftWhite = leftKnight.getColor() == PColor.White;
		boolean isRightWhite = rightKnight.getColor() == PColor.White;
		assertTrue(isLeftWhite && isRightWhite);
	}

	@Test
	public void checkWhiteBishopsPosition() {
		// White Bishops are at row 7, col 2 and 5
		game = new Chess();
		boolean isLeftBishop = game.getPieceAt(7, 2) instanceof Bishop;
		boolean isRightBishop = game.getPieceAt(7, 5) instanceof Bishop;
		boolean isLeftWhite = game.getPieceAt(7, 2).getColor() == PColor.White;
		boolean isRightWhite = game.getPieceAt(7, 5).getColor() == PColor.White;
		assertTrue(isLeftBishop && isRightBishop && isLeftWhite && isRightWhite);
	}

	@Test
	public void checkWhiteRooksPosition() {
		// White Rooks are at row 7, col 0 and 7
		game = new Chess();
		boolean isLeftRook = game.getPieceAt(7, 0) instanceof Rook;
		boolean isRightRook = game.getPieceAt(7, 7) instanceof Rook;
		boolean isLeftWhite = game.getPieceAt(7, 0).getColor() == PColor.White;
		boolean isRightWhite = game.getPieceAt(7, 7).getColor() == PColor.White;
		assertTrue(isLeftRook && isRightRook && isLeftWhite && isRightWhite);
	}

	@Test
	public void checkWhiteQueenPosition() {
		// White Queen is located at row 7, col 3
		game = new Chess();
		boolean isQueen = game.getPieceAt(7, 3) instanceof Queen;
		boolean isQueenWhite = game.getPieceAt(7, 3).getColor() == PColor.White;
		assertTrue(isQueen && isQueenWhite);
	}

	@Test
	public void checkWhiteKingPosition() {
		// White King is located at row 7, col 4
		game = new Chess();
		boolean isKing = game.getPieceAt(7, 4) instanceof King;
		boolean isKingWhite = game.getPieceAt(7, 4).getColor() == PColor.White;
		assertTrue(isKing && isKingWhite);
	}

	@Test
	public void checkBlackPawnsPositions() {
		// Black Pawns are in row 6, col 0-7
		game = new Chess();
		boolean isCorrect = false;
		for (int i = 0; i < 8; i++) {
			boolean isPawn = game.getPieceAt(1, i) instanceof Pawn;
			boolean isWhite = game.getPieceAt(1, i).getColor() == PColor.Black;
			isCorrect = (isPawn && isWhite);
			if (!isCorrect)
				return;
		}

		assertTrue(isCorrect);
	}

	@Test
	public void checkBlackKnightsPositions() {
		// White Knights are at row 7, col 1 and 6
		game = new Chess();
		boolean isLeftKnight = game.getPieceAt(0, 1) instanceof Knight;
		boolean isRightKnight = game.getPieceAt(0, 6) instanceof Knight;
		boolean isLeftBlack = game.getPieceAt(0, 1).getColor() == PColor.Black;
		boolean isRightBlack = game.getPieceAt(0, 6).getColor() == PColor.Black;
	
		assertTrue(isLeftKnight && isRightKnight && isLeftBlack && isRightBlack);
	}

	@Test
	public void checkBlackBishopsPositions() {

	}

	@Test
	public void checkBlackRooksPositions() {

	}

	@Test
	public void checkBlackQueenPosition() {

	}

	@Test
	public void checkBlackKingPosition() {

	}

	@Test
	public void whitePawnDoesntMoveIfRowGreaterThanTwo() {
		game = new Chess();
		assertFalse(game.checkMove(6, 1, 3, 2, (whitePawn)));
	}

	@Test
	public void blackPawnDoesntMoveIfRowGreaterThanTwo() {
		game = new Chess();
		assertFalse(game.checkMove(6, 1, 3, 2, (blackPawn)));
	}

	@Test
	public void whitePawnShouldMoveOneRow() {
		game = new Chess();
		assertTrue(game.checkMove(6, 1, 5, 1, (whitePawn)));
	}

	@Test
	public void blackPawnShouldMoveOneRow() {
		game = new Chess();
		assertTrue(game.checkMove(1, 1, 2, 1, (blackPawn)));
	}

	@Test
	public void whitePawnHasMovedShouldBeTrue() {
		game = new Chess();
		game.checkMove(6, 1, 5, 1, (whitePawn));
		assertTrue(whitePawn.isHasMoved());
	}

	@Test
	public void blackPawnHasMovedShouldBeTrue() {
		game = new Chess();
		game.checkMove(1, 1, 2, 1, (blackPawn));
		assertTrue(blackPawn.isHasMoved());
	}

	@Test
	public void whitePawnHasNotMoved() {
		game = new Chess();
		assertFalse(whitePawn.isHasMoved());
	}

	@Test
	public void blackPawnHasNotMoved() {
		game = new Chess();
		assertFalse(blackPawn.isHasMoved());
	}

	@Test
	public void whitePawnHasNotMoved2() {
		game = new Chess();
		game.checkMove(6, 1, 1, 1, whitePawn);
		assertFalse(whitePawn.isHasMoved());
	}

	@Test
	public void blackPawnHasNotMoved2() {
		game = new Chess();
		game.checkMove(1, 1, 6, 1, blackPawn);
		assertFalse(blackPawn.isHasMoved());
	}

	@Test
	public void testWPawnHasMoved3() {
		game = new Chess();
		game.checkMove(6, 1, 3, 1, (whitePawn));
		assertFalse(whitePawn.isHasMoved());
	}

	@Test
	public void testBPawnHasMoved3() {
		game = new Chess();
		game.checkMove(1, 1, 4, 1, (blackPawn));
		assertFalse(blackPawn.isHasMoved());
	}

	@Test
	public void testWPawnMove2Rows1() {
		game = new Chess();
		assertTrue(game.checkMove(6, 1, 4, 1, whitePawn) && whitePawn.isHasMoved());
	}

	@Test
	public void testWPawnMove2Rows2() {

	}

	@Test
	public void testBPawnMove2Rows1() {

	}

	@Test
	public void testBPawnMove2Rows2() {

	}

}
