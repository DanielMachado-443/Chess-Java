package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece{	

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	private Color color;

	public Color getColor() {
		return color;
	}		
}
