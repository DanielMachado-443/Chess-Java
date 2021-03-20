package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece { //<< getBoard IS A Piece abs. class method

	private ChessMatch chessMatch; // << This Pawn kind of piece must have a ChessMatch dependency in order to know
									// whether it is el passant vulnerable

	public Pawn(Board board, Color color, ChessMatch chessMatch) { // << We need to use a getter to access this board
																	// attribute
		super(board, color); //<< getBoard IS A Piece abs. class method
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; //<< getBoard IS A Piece abs. class method
		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {

			p.setValues(position.getRow() - 1, position.getColumn()); // << This 'position.getRow' refers to the
																		// position ATTRIBUTE of this class
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //<< getBoard IS A Piece abs. class method
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 2, position.getColumn()); // << This 'position.getRow' refers to the
																		// position ATTRIBUTE of this class
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) //<< getBoard IS A Piece abs. class method
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) { //<< getBoard IS A Piece abs. class method
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 1, position.getColumn() - 1); // << This 'position.getRow' refers to the
																			// position ATTRIBUTE of this class
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //<< getBoard IS A Piece abs. class method
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 1, position.getColumn() + 1); // << This 'position.getRow' refers to the
																			// position ATTRIBUTE of this class
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //<< getBoard IS A Piece abs. class method
				mat[p.getRow()][p.getColumn()] = true;
			}

			// #specialMove en passant white LEFT
			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) //<< getBoard IS A Piece abs. class method
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) { // << We need to use a
																							// getter to access this
																							// board attribute
					mat[left.getRow() - 1][left.getColumn()] = true; // << The column - 1 is ALREADY subtracted above on the if FIRST line
				}
				// #specialMove en passant white RIGHT
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) //<< getBoard IS A Piece abs. class method
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) { // << We need to use a
																								// getter to access this
																								// board attribute
					mat[right.getRow() - 1][right.getColumn()] = true; // << // << The column - 1 is ALREADY subtracted above on the if FIRST line
				}
			}
		} else {
			p.setValues(position.getRow() + 1, position.getColumn()); // << This 'position.getRow' refers to the
																		// position ATTRIBUTE of this class
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {//<< getBoard IS A Piece abs. class method
				mat[p.getRow()][p.getColumn()] = true; 
			}

			p.setValues(position.getRow() + 2, position.getColumn()); // << This 'position.getRow' refers to the
																		// position ATTRIBUTE of this class
			Position p2 = new Position(position.getRow() + 1, position.getColumn()); //<< getBoard IS A Piece abs. class method
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) //<< getBoard IS A Piece abs. class method
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() - 1); // << This 'position.getRow' refers to the
																			// position ATTRIBUTE of this class
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //<< getBoard IS A Piece abs. class method
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() + 1); // << This 'position.getRow' refers to the
																			// position ATTRIBUTE of this class
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //<< getBoard IS A Piece abs. class method
				mat[p.getRow()][p.getColumn()] = true;
			}

			// #specialMove en passant black LEFT
			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left) //<< getBoard IS A Piece abs. class method
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) { // << We need to use a
																							// getter to access this
																							// board attribute
					mat[left.getRow() + 1][left.getColumn()] = true; // << CHECK IT AGAIN
				}
				// #specialMove en passant white RIGHT
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right) //<< getBoard IS A Piece abs. class method
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) { // << We need to use a
																								// getter to access this
																								// board attribute
					mat[right.getRow() + 1][right.getColumn()] = true; // << CHECK IT AGAIN
				}
			}
		}

		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
}
