package edu.virginia.pnl8zp;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

public class Piece {
	
	public static enum PieceType {
		FLAG, BOMB, SPY, MARSHAL, GENERAL, COLONEL, MAJOR, CAPTAIN, LIEUTENANT, SERGEANT, MINER, SCOUT
	}
	
	public enum Owner {
		PLAYER, AI
	}
	
	private PieceType pType = null;
	private Image image = null;
	private Boolean seen;
	// private int pieceSize = 40;
	private Cell cell;
	private Rectangle piece;
	private Color color;
	public Boolean isRed;
	// public Owner owner;
	public String oType = "";
	
	public String pieceTypeToString() {
		if(pType == null) {
			return "P";
		}	
		switch(pType) {
			case FLAG:
				return "F";
			case BOMB:
				return "B";
			case SPY:
				return "1";
			case MARSHAL:
				return "10";
			case GENERAL:
				return "9";
			case COLONEL:
				return "8";
			case MAJOR:
				return "7";
			case CAPTAIN:
				return "6";
			case LIEUTENANT:
				return "5";
			case SERGEANT:
				return "4";
			case MINER:
				return "3";
			case SCOUT:
				return "2";
			default:
				return "error";
		}
	}
	
	public void setOwner(String oType) {
		this.oType = oType;
	}
	
	public String getOwner() {
		return oType;
	}
	
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
		setImage(pType);
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(PieceType pType) {
		if(this.isRed == false) {
			switch(pType) {
				case FLAG:
					this.image = Stratego.resources.blue_flag;
					break;
				case BOMB:
					this.image = Stratego.resources.blue_bomb;
					break;
				case SPY:
					this.image = Stratego.resources.blue_spy;
					break;
				case MARSHAL:
					this.image = Stratego.resources.blue_marshal;
					break;
				case GENERAL:
					this.image = Stratego.resources.blue_general;
					break;
				case COLONEL:
					this.image = Stratego.resources.blue_colonel;
					break;
				case MAJOR:
					this.image = Stratego.resources.blue_major;
					break;
				case CAPTAIN:
					this.image = Stratego.resources.blue_captain;
					break;
				case LIEUTENANT:
					this.image = Stratego.resources.blue_lieutenant;
					break;
				case SERGEANT:
					this.image = Stratego.resources.blue_sergeant;
					break;
				case MINER:
					this.image = Stratego.resources.blue_miner;
					break;
				case SCOUT:
					this.image = Stratego.resources.blue_scout;
					break;
				default:
					System.out.println("Piece.setImage() Error: Blue");
					break;
			}
		} else {
			switch(pType) {
			case FLAG:
				this.image = Stratego.resources.red_flag;
				break;
			case BOMB:
				this.image = Stratego.resources.red_bomb;
				break;
			case SPY:
				this.image = Stratego.resources.red_spy;
				break;
			case MARSHAL:
				this.image = Stratego.resources.red_marshal;
				break;
			case GENERAL:
				this.image = Stratego.resources.red_general;
				break;
			case COLONEL:
				this.image = Stratego.resources.red_colonel;
				break;
			case MAJOR:
				this.image = Stratego.resources.red_major;
				break;
			case CAPTAIN:
				this.image = Stratego.resources.red_captain;
				break;
			case LIEUTENANT:
				this.image = Stratego.resources.red_lieutenant;
				break;
			case SERGEANT:
				this.image = Stratego.resources.red_sergeant;
				break;
			case MINER:
				this.image = Stratego.resources.red_miner;
				break;
			case SCOUT:
				this.image = Stratego.resources.red_scout;
				break;
			default:
				System.out.println("Piece.setImage() Error: Red");
				break;
		}
		}
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
