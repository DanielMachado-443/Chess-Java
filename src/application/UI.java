package application;

import chess.ChessPiece;

public class UI {		
	public static void printBoard(ChessPiece[][] pieces) {
		
		System.out.println("\n");
		
		for(int i = 0; i < pieces.length; i++) {
			System.out.print("   " + (8 - i) + " "); // << Appends of first line || Always BEFORE the little for entry
			for(int j = 0; j < pieces.length; j++) { // << pieces.lenght aswell because this is a quadratic matrix
				printPiece(pieces[i][j]);								
			}
			System.out.println();
		}
		System.out.println("     A B C D E F G H");
	}
	
	private static void printPiece(ChessPiece piece) {
		if(piece == null) {
			System.out.print("-");
		}
		else {
			System.out.print(piece);
		}
		System.out.print(" "); // << after each piece printed there will be a blanc space to make the pieces separation
	}
}
