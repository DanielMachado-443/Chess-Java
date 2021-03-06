package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; // the printer method will automatically print "-" whenever the piece is null
	
	public Board(int rows, int columns) {	
		if(rows < 1 || columns < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}	
	
	public Piece piece(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	
	public Piece piece(Position position) { // overload of the method above
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // Changing the piece position from null to the new one that has been passed as this method argument
	}
	
	public Piece removePiece(Position position) { // << NICE STRATEGY!!!
		if(!positionExists(position)) {
			throw new BoardException("Position not on the board");
		}
		if(piece(position) == null) {
			return null;
		}
		Piece aux = piece(position); // Why not just change the piece's position straightly? 
		aux.position = null; // reseting the piece position to null (once piece is an object it will be affected by the aux object)
		pieces[position.getRow()][position.getColumn()] = null; // REMOVING the PIECE from the BOARD
		return aux;
	}
	
	private boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) { // << Before testing if there is already a piece in the position, it tests if the position exists
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null;
	}
}
