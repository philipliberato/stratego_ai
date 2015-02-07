package edu.virginia.pnl8zp;

public class Cell {
	
	public int xVal;
	public int yVal;
	public int width;
	public Piece piece = null;
	
	public Cell(int xVal, int yVal, int width) {
		super();
		this.xVal = xVal;
		this.yVal = yVal;
		this.width = width;
	}

}
