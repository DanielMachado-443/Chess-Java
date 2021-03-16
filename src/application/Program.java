package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {		
		
		Scanner sc = new Scanner(System.in); // << In the place of Console.ReadLine();		
		ChessMatch chessMatch = new ChessMatch();
		List<ChessPiece> captured = new ArrayList<>(); // << Mean to instance a list
		
		while(!chessMatch.getCheckMate()) { // << Tests if there is a checkMate
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured);				
				
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc); // << In the place of Console.ReadLine();
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves); // The other printBoard overload has been called by the printMatch already
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc); // << In the place of Console.ReadLine();
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
				if(capturedPiece != null) { // << Whenever I've made a move and the piece is not null, it will be added in the captured pieces list
					captured.add(capturedPiece);
				}
			}
			catch(ChessException e){
				System.out.println(e.getMessage());
				sc.nextLine();
			}	
			catch(InputMismatchException e){
				System.out.println(e.getMessage());
				sc.nextLine();
			}	
		}
		
		UI.clearScreen();
		UI.printMatch(chessMatch, captured); // Prints the match again after the game is ended
	}
}
