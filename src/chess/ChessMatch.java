package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable; // << It wont be initiated in the constructor, because it already is null by default
	private ChessPiece promoted;
											

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

	public boolean getCheckMate() {
		return checkMate;
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

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
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

		ChessPiece movedPiece = (ChessPiece)board.piece(target); // << 'target' is the player moved piece, so we're
																	// getting the moved piece
		
		// #specialmove promotion
		promoted = null;
		if(movedPiece instanceof Pawn) {
			if(movedPiece.getColor() == Color.WHITE && target.getRow() == 0
					|| movedPiece.getColor() == Color.BLACK && target.getRow() == 7) {
				
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // << Nice strategy ... Conditional ternary
																		// expression
		if (testCheckMate(opponent(currentPlayer))) { // << It tests if the opponent king is in CheckMate
			checkMate = true;
		} else {
			nextTurn();
		}

		// #specialmove en passant
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2)
				|| (target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) capturedPiece; // << Downcasting from Piece to its subclass object ChessPiece
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException();
		}
		if(!type.equals("B") && !type.equals("H") && !type.equals("R") && !type.equals("Q")) { // We use .equals because String isnt a primitive type
			return promoted; //<< Keeps the Queen in the position without changing it
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {			
		if(type.equals("B")) return new Bishop(board, color);
		if(type.equals("H")) return new Knight(board, color);
		if(type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source); // Picked up the source piece ... //<< getBoard IS A
																// Piece abs. class method
		p.increaseMoveCount();

		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// #specialMove castling KING SIDE ROOK
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // << It means that the king has moved
																					// two squares to the right
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);

			ChessPiece thatRook = (ChessPiece) board.removePiece(sourceRook); // << The 'thatRook' variablhe will now
																				// keep THIS PICKEUP tower (rook) value
			board.placePiece(thatRook, targetRook);
			thatRook.increaseMoveCount();
		}

		// #specialMove castling QUEEN SIDE ROOK
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { // << It means that the king has moved
																					// two squares to the right
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);

			ChessPiece thatRook = (ChessPiece) board.removePiece(sourceRook); // << The 'thatRook' variablhe will now
																				// keep THIS PICKEUP tower (rook) value
			board.placePiece(thatRook, targetRook);
			thatRook.increaseMoveCount();
		}

		// #specialMove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) { // << It first tests if the Pawn
																						// has moved on diagonal
				Position capturedPawnPosition; // << It is declared here due to the if/else limited scope
				if (p.getColor() == Color.WHITE) {
					capturedPawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					capturedPawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(capturedPawnPosition); // << removePiece returns a Piece
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece; // << It returns this capturedPiece to the performChessMove method
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) { // << The trick is in the Piece
																					// capturedPiece argument
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) { // << NICE STRATEGY
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// #specialMove castling KING SIDE ROOK
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) { // << It means that the king has moved
																					// two squares to the right
			Position sourceRook = new Position(source.getRow(), source.getColumn() + 3);
			Position targetRook = new Position(source.getRow(), source.getColumn() + 1);

			ChessPiece thatRook = (ChessPiece) board.removePiece(targetRook); // << The 'thatRook' variablhe will now
																				// keep THIS PICKEUP tower (rook) value
			board.placePiece(thatRook, sourceRook);
			thatRook.decreaseMoveCount(); // << REVERSE PROCESS
		}

		// #specialMove castling QUEEN SIDE ROOK
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) { // << It means that the king has moved
																					// two squares to the right
			Position sourceRook = new Position(source.getRow(), source.getColumn() - 4);
			Position targetRook = new Position(source.getRow(), source.getColumn() - 1);

			ChessPiece thatRook = (ChessPiece) board.removePiece(targetRook); // << The 'thatRook' variablhe will now
																				// keep THIS PICKEUP tower (rook) value
			board.placePiece(thatRook, sourceRook);
			thatRook.decreaseMoveCount(); // << REVERSE PROCESS
		}

		// #specialMove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) { // << It first tests
																									// if the Pawn has
																									// moved on diagonal
				ChessPiece capturedPawn = (ChessPiece) board.removePiece(target); // << CONSIDERING that this
																					// removedPiece was put ON target position																					
																					// by THIS undoMove method
																					// we use this strategy to pick the
																					// recently removed piece from
																					// target
				Position capturedPawnForUndoPosition; // << It is declared here due to the if/else limited scope

				if (p.getColor() == Color.WHITE) {
					capturedPawnForUndoPosition = new Position(3, target.getColumn());
				} else {
					capturedPawnForUndoPosition = new Position(4, target.getColumn());
				}
				board.placePiece(capturedPawn, capturedPawnForUndoPosition);
			}
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

	// CHECK LOGIC BELLOW!!! // CHECK LOGIC BELLOW!!! // CHECK LOGIC BELLOW!!!
	private boolean testCheck(Color color) { // << WONDERFUL METHOD!!!
		Position kingPosition = pickTheChessPieceKing(color).getChessPosition().toPosition(); // << getChessPosition is
																								// a ChessPiece method
		List<Piece> opponentPieces = piecesOnTheBoard.stream() // << toPosition transforms ChessPosition to Position
																// again
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)) // << There is a magic that happens here
				.collect(Collectors.toList());

		for (Piece p : opponentPieces) { // << Like C# foreach? (Yes, I've learned C# first)
			boolean[][] mat = p.possibleMoves(); // << Aux boolean matrix
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) { // << Checks IF the kingPosition is a possible
																		// move of at least ONE ENEMY piece
				return true; // IT CUTS THE METHOD AT THE FIRST TRUE, TEST IN ALL ENEMY PIECES possibleMoves
			}
		}
		return false;
	}
	// CHECK LOGIC ABOVE!!! // CHECK LOGIC ABOVE!!! // CHECK LOGIC ABOVE!!!

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) { // << If this color's King isn't in Check, the method should be cut here, from
									// the beggining
			return false;
		}

		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (mat[i][j]) { // << Its a possible move, once I get it, I will test if this position takes the
										// King off from check
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);

						boolean testCheck = testCheck(color); // << It tests if the King of this color is STILL in
																// check;
						undoMove(source, target, capturedPiece);

						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {

		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
