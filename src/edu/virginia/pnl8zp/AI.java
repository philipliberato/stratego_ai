package edu.virginia.pnl8zp;

import java.util.ArrayList;

// use a genetic algorithm for initially placing pieces on the board - can use for all AI instances

public abstract class AI implements MoveListener {
	
	abstract public ArrayList<Piece> getPieces();
	abstract public void moveMade();
	abstract public boolean removePiece(Piece p);
	abstract public void assignPieceType(Piece piece); // for setting up initial board state
	abstract public String stringIdentifier(); // what type of AI instance is the class
	abstract public void togglePieceView();
	abstract public boolean getArePieceValuesVisible();
	abstract public void setPieces(ArrayList<Piece> thePieces);
	abstract public void makeRandomMove(ArrayList<Piece> availablePieces, String oType);
	abstract public void makeValuedMove(ArrayList<Piece> availablePieces, String oType);
	abstract public void makeMinimaxMove(ArrayList<Piece> availablePieces, String oType);
	abstract public void addDicoveredOpponentPiece(Piece discoveredPiece);
	abstract public void removeDiscoveredOpponentPiece(Piece discoveredPiece);
	abstract public void isAIRed(boolean val);
	
	public void makeMove(ArrayList<Piece> availablePieces) {
		
		String aiType = this.stringIdentifier();
		
		if(aiType.equals("RandomAI")) {
			this.makeRandomMove(availablePieces, "AI");
		}
		
		if(aiType.equals("ValuedAI")) {
			this.makeValuedMove(availablePieces, "AI");			
		} 
		
		if(aiType.equals("MinimaxAI")) {
			this.makeMinimaxMove(availablePieces, "AI");			
		}
	}

}





