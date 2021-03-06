package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color); // << Super class call
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // << Picking up the rows and // columns from the Board object									 
		
		Position p = new Position(0, 0); // << ITS JUST TO COPY AND TEST THIS PIECE REAL POSITION
		
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

		return mat;
	}
}
