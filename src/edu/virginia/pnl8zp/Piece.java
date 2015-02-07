package edu.virginia.pnl8zp;

import java.awt.Rectangle;

public class Piece {
	
	public enum PieceType {
		FLAG, BOMB, SPY, MARSHALL
	}
	
	private PieceType pType;
	private Boolean seen;
	private int pieceSize = 40;
	private Cell cell;
	public Rectangle piece = new Rectangle();

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

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
}
