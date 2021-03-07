package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;

	private List<Piece> piecesOnTheBoard = new ArrayList<>(); // << This could be inside of the constructor aswell
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		check = false; // << Not necessary, because a boolean property is already started with false
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); // << making a downcasting
			}
		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) { // why is it necessary to import the Position as
																		// ChessPosition
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target); // << Interesting
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {// << currentPlayer is a color
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself in a check position");
		}
		
		check = (testCheck(opponent(currentPlayer)))? true : false; // << Nice strategy ... Conditional ternary expression

		nextTurn();
		return (ChessPiece) capturedPiece; // << Downcasting from Piece to its subclass object ChessPiece
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source); // Picked up the source piece
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece; // << It returns this capturedPiece to the performChessMove method
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) { // << The trick is in the Piece
																					// capturedPiece argument
		Piece p = board.removePiece(target);
		board.placePiece(p, source);

		if (capturedPiece != null) { // << NICE STRATEGY
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}

	private void validateSourcePosition(Position position) { // << Nice defensive programming
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) { // << This reverses the color received
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE; // << Like if else
	}

	private ChessPiece pickTheChessPieceKing(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		// The strategy above fills the >>list<< up with ONLY ONE SPECIFIC COLOR of ALL
		// ON the board PIECES

		for (Piece p : list) { // << Like C# foreach? (Yes, I've learned C# first)
			if (p instanceof King) { // Picking up the King in the collection of all of THIS color pieces ON the
										// BOARD
				return (ChessPiece) p; // << Basic downcasting
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board"); // << Critical chess system
																						// problem!!!
	}

	// CHECK LOGIC BELLOW!!! // CHECK LOGIC BELLOW!!! // CHECK LOGIC BELLOW!!! //
	// CHECK LOGIC BELLOW!!! // CHECK LOGIC BELLOW!!!
	private boolean testCheck(Color color) { // << WONDERFUL METHOD!!!
		Position kingPosition = pickTheChessPieceKing(color).getChessPosition().toPosition(); // << Nice strategy
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)) // << There is a magic that happens here
				.collect(Collectors.toList());

		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves(); // << Aux boolean matrix
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true; // IT CUTS THE METHOD AT THE FIRST TRUE TEST IN ALL ENEMY PIECES possibleMoves
			}
		}
		return false;
	}
	// CHECK LOGIC ABOVE!!! // CHECK LOGIC ABOVE!!! // CHECK LOGIC ABOVE!!! // CHECK
	// LOGIC ABOVE!!! // CHECK LOGIC ABOVE!!!

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {

		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
