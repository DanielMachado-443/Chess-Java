package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{	// << second level of inheritance && TWO SUBSEQUENT ABASTRACT CLASS
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board); // << I shall understand it better
		this.color = color;
	}	

	public Color getColor() {
		return color;
	}		
	
	public ChessPosition getChessPosition(){
		return ChessPosition.fromPosition(position);
	}
	
	protected boolean isThereOpponentPiece(Position position) { // << Why is this method here instead of ChessMatch?
		ChessPiece p = (ChessPiece)getBoard().piece(position); // << It makes a downcasting
		return p != null && p.getColor() != color; // << Checks if this is an enemy piece
	}
}
