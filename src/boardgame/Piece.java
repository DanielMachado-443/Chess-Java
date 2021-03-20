package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
		position = null; // << it was already be initialized with null value
	}

	protected Board getBoard() { //<< Interesting!!!
		return board;
	}

	public abstract boolean[][] possibleMoves(); // the parameter will be implemented in this method concrete implementation													

	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()]; // << Hook method
	}
	
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}
