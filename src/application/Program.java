package application;

import java.util.Scanner;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {		
		
		Scanner sc = new Scanner(System.in); // << In the place of Console.ReadLine();
		
		ChessMatch chessMatch = new ChessMatch();
		
		while(true) {
			UI.printBoard(chessMatch.getPieces());
			
			System.out.println();
			System.out.print("Source: ");
			ChessPosition source = UI.readChessPosition(sc); // << In the place of Console.ReadLine();
			
			System.out.println();
			System.out.print("Target: ");
			ChessPosition target = UI.readChessPosition(sc); // << In the place of Console.ReadLine();
			
			ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
		}		
	}
}
