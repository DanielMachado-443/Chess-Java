package boardgame;

public class Piece {
protected Position position;
private Board board;

public Piece(Board board) {	
	this.board = board;
	position = null; // << it was already be initialized with null value
}

protected Board getBoard() {
	return board;
}

}
