package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color); // << Super class call
	}

	@Override
	public String toString() {
		return "Q";
	}

	@Override
	public boolean[][] possibleMoves() { // << The Queen is a mix of Bishop and Rook movements!
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // << Picking up the rows and //
																						// columns from the Board object
		Position p = new Position(0, 0); // << ITS JUST TO COPY AND TEST THIS PIECE REAL POSITION

		
		// ROOK MOVEMENTS BELLOW!!! // ROOK MOVEMENTS BELLOW!!! // ROOK MOVEMENTS BELLOW!!!
		// ABOVE
		p.setValues(position.getRow() - 1, position.getColumn()); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;

			p.setRow(p.getRow() - 1); // << Decrement the rows of position p
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy AFTER the while
			mat[p.getRow()][p.getColumn()] = true;
		}

		// BELLOW
		p.setValues(position.getRow() + 1, position.getColumn()); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(p.getRow() + 1); // << Decrement the rows of position p
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}

		// LEFT
		p.setValues(position.getRow(), position.getColumn() - 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() - 1); // << Decrement the rows of position p
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}

		// RIGHT
		p.setValues(position.getRow(), position.getColumn() + 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(p.getColumn() + 1); // << Decrement the rows of position p
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}
		// ROOK MOVEMENTS ABOVE!!! // ROOK MOVEMENTS ABOVE!!! // ROOK MOVEMENTS ABOVE!!!

		// BISHOP MOVEMENTS BELLOW!!! // BISHOP MOVEMENTS BELLOW!!! // BISHOP MOVEMENTS BELLOW!!!
		// NW
		p.setValues(position.getRow() - 1, position.getColumn() - 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << While will get the incremented
																				// bellow p values
			mat[p.getRow()][p.getColumn()] = true;

			p.setValues(p.getRow() - 1, p.getColumn() - 1); // << Changing the position values with its 'setValues'
															// method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy AFTER the while
			mat[p.getRow()][p.getColumn()] = true;
		}

		// NE
		p.setValues(position.getRow() - 1, position.getColumn() + 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << While will get the incremented
																				// bellow p values
			mat[p.getRow()][p.getColumn()] = true;

			p.setValues(p.getRow() - 1, p.getColumn() + 1); // << Changing the position values with its 'setValues'
															// method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SW
		p.setValues(position.getRow() + 1, position.getColumn() - 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << This position p now has these
																				// values
			mat[p.getRow()][p.getColumn()] = true;

			p.setValues(p.getRow() + 1, p.getColumn() - 1); // << Changing the position values with its 'setValues'
															// method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SE
		p.setValues(position.getRow() + 1, position.getColumn() + 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << This position p now has these
																				// values
			mat[p.getRow()][p.getColumn()] = true;

			p.setValues(p.getRow() + 1, p.getColumn() + 1); // << Changing the position values with its 'setValues'
															// method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}
		// BISHOP MOVEMENTS ABOVE!!! // BISHOP MOVEMENTS ABOVE!!! // BISHOP MOVEMENTS ABOVE!!!

		return mat;
	}
}