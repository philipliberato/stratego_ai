package edu.virginia.pnl8zp;

import java.awt.Color;
import java.awt.Rectangle;

public class Piece {
	
	public enum PieceType {
		FLAG, BOMB, SPY, MARSHALL
	}
	
	private PieceType pType;
	private Boolean seen;
	private int pieceSize = 40;
	private Cell cell;
	private Rectangle piece;
	private Color color;
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Piece(Rectangle rect) {
		this.piece = rect;
	}

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
	
	public Rectangle getRect() {
		return piece;
	}

	public void setRect(Rectangle rect) {
		this.piece = rect;
	}
	
}
