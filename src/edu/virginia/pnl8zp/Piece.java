package edu.virginia.pnl8zp;

public class Piece {
	
	public enum PieceType {
		FLAG, BOMB, SPY, MARSHALL
	}
	
	private PieceType pType;
	private Boolean seen;

	public Boolean getSeen() {
		return seen;
	}

	public void setSeen(Boolean seen) {
		this.seen = seen;
	}

	public PieceType getpType() {
		return pType;
	}

	public void setpType(PieceType pType) {
		this.pType = pType;
	}

}
