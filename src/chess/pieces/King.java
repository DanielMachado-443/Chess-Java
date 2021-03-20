package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece { // << third level of inheritance

	private ChessMatch chessMatch; 

	public King(Board board, Color color, ChessMatch chessMatch) { //<< getBoard IS A Piece abs. class method
		super(board, color); //<< getBoard IS A Piece abs. class method
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position); //<< getBoard IS A Piece abs. class method
		return p == null || p.getColor() != getColor(); // << Interesting
	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position); // << Picked this piece up ... //<< getBoard IS A Piece abs. class method
		return p != null && p instanceof Rook && p.getColor() == getColor() // << Same actual player color
				&& p.getMoveCount() == 0; // << Didnt move yet
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; // << Picking up the rows and
																						// columns from the Board object
		Position p = new Position(0, 0);

		// ABOVE
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) { // << Checks if the p position has a opponent piece ... getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// BELLOW
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) { //<< getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// LEFT
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) { //<< getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// RIGHT
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { //<< getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// NW
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) { //<< getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// NE
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { //<< getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SW
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) { //<< getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SE
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) { //<< getBoard IS A Piece abs. class method
			mat[p.getRow()][p.getColumn()] = true;
		}

		// #specialMove CASTLING
		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// #specialMove castling kingside rook
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3); // << Actual THIS king position
																						// + 3
			if (testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) { // << Remember: THIS king class has
																					// a ChessMatch dependency
					mat[position.getRow()][position.getColumn() + 2] = true; // << Small rock is two squares right
				}
			}
		}

		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// #specialMove castling King side rook
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4); // << Actual THIS king position
																						// + 3
			if (testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3); //<< getBoard IS A Piece abs. class method
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) { // << Remember: THIS king class has
																					// a ChessMatch dependency
					mat[position.getRow()][position.getColumn() - 2] = true; // << Small rock is two squares right
				}
			}
		}

		return mat;
	}
}
