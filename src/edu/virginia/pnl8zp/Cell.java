package edu.virginia.pnl8zp;

public class Cell {
	
	public int xVal;
	public int yVal;
	public int width;
	public int rowIndex;
	public int colIndex;
	public boolean water = false;
	public Piece piece = null;
	
	public Cell(int xVal, int yVal, int width) {
		super();
		this.xVal = xVal;
		this.yVal = yVal;
		this.width = width;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

}
