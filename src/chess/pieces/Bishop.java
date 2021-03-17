package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color); // << Super class call
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // << Picking up the rows and // columns from the Board object									 
		
		Position p = new Position(0, 0); // << ITS JUST TO COPY AND TEST THIS PIECE REAL POSITION
		
		// NW
		p.setValues(position.getRow() - 1, position.getColumn() - 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << While will get the incremented bellow p values
			mat[p.getRow()][p.getColumn()] = true;
			
			p.setValues(p.getRow() - 1, p.getColumn() - 1); // << Changing the position values with its 'setValues' method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy AFTER the while
			mat[p.getRow()][p.getColumn()] = true;
		}

		// NE
		p.setValues(position.getRow() - 1, position.getColumn() + 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << While will get the incremented bellow p values
			mat[p.getRow()][p.getColumn()] = true;
			
			p.setValues(p.getRow() - 1, p.getColumn() + 1); // << Changing the position values with its 'setValues' method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SW
		p.setValues(position.getRow() + 1, position.getColumn() - 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << This position p now has these values
			mat[p.getRow()][p.getColumn()] = true;
			
			p.setValues(p.getRow() + 1, p.getColumn() - 1); // << Changing the position values with its 'setValues' method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SE
		p.setValues(position.getRow() + 1, position.getColumn() + 1); // << This position p now has these values
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // << This position p now has these values
			mat[p.getRow()][p.getColumn()] = true;
			
			p.setValues(p.getRow() + 1, p.getColumn() + 1); // << Changing the position values with its 'setValues' method and PUTING IT AGAIN IN THE WHILE
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { // << Nice strategy
			mat[p.getRow()][p.getColumn()] = true;
		}

		return mat;
	}
}
