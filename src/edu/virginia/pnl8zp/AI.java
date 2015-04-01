package edu.virginia.pnl8zp;

import java.util.ArrayList;

// use a genetic algorithm for initially placing pieces on the board - can use for all AI instances

public abstract class AI implements MoveListener {
	
	abstract public ArrayList<Piece> getPieces();
	abstract public void moveMade();
	abstract public boolean removePiece(Piece p);
	abstract public void assignPieceType(Piece piece);
	abstract public String stringIdentifier();

}





