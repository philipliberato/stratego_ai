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
	public Boolean knownByOpponent = false;
	public ProbabilityDistribution probability = null;
	public boolean hasMoved = false;
	
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
	
	public static String pieceTypeToString(PieceType pType) {
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
	
	public void pieceMoved() {
		this.hasMoved = true;
		this.probability.pieceMoved();
	}

	public Piece(Rectangle rect) {
		this.piece = rect;
		this.probability = new ProbabilityDistribution();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cell == null) ? 0 : cell.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (hasMoved ? 1231 : 1237);
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((isRed == null) ? 0 : isRed.hashCode());
		result = prime * result
				+ ((knownByOpponent == null) ? 0 : knownByOpponent.hashCode());
		result = prime * result + ((oType == null) ? 0 : oType.hashCode());
		result = prime * result + ((pType == null) ? 0 : pType.hashCode());
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
		result = prime * result
				+ ((probability == null) ? 0 : probability.hashCode());
		result = prime * result + ((seen == null) ? 0 : seen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (cell == null) {
			if (other.cell != null)
				return false;
		} else if (!cell.equals(other.cell))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (hasMoved != other.hasMoved)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (isRed == null) {
			if (other.isRed != null)
				return false;
		} else if (!isRed.equals(other.isRed))
			return false;
		if (knownByOpponent == null) {
			if (other.knownByOpponent != null)
				return false;
		} else if (!knownByOpponent.equals(other.knownByOpponent))
			return false;
		if (oType == null) {
			if (other.oType != null)
				return false;
		} else if (!oType.equals(other.oType))
			return false;
		if (pType != other.pType)
			return false;
		if (piece == null) {
			if (other.piece != null)
				return false;
		} else if (!piece.equals(other.piece))
			return false;
		if (probability == null) {
			if (other.probability != null)
				return false;
		} else if (!probability.equals(other.probability))
			return false;
		if (seen == null) {
			if (other.seen != null)
				return false;
		} else if (!seen.equals(other.seen))
			return false;
		return true;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((color == null) ? 0 : color.hashCode());
//		result = prime * result + ((image == null) ? 0 : image.hashCode());
//		result = prime * result + ((isRed == null) ? 0 : isRed.hashCode());
//		result = prime * result + ((oType == null) ? 0 : oType.hashCode());
//		result = prime * result + ((pType == null) ? 0 : pType.hashCode());
//		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
//		return result;
//	}
//

}
