package edu.virginia.pnl8zp;

import edu.virginia.pnl8zp.Piece.PieceType;

public class MinimaxCell {
	
	public int rowIndex;
	public int colIndex;
	public boolean water = false;
	
	
	public boolean hasPiece = false;
	public boolean isRed = false;
	public boolean knownByOpponent = false;
	public PieceType pType = null;
	public ProbabilityDistribution probability = null;

	public boolean hasMoven;
	
	public MinimaxCell() {
		
	}
	
	public MinimaxCell(MinimaxCell mc) {
		this.rowIndex = mc.rowIndex;
		this.colIndex = mc.colIndex;
		this.water = mc.water;
		this.hasPiece = mc.hasPiece;
		this.isRed = mc.isRed;
		this.knownByOpponent = mc.knownByOpponent;
		this.pType = mc.pType;
		this.probability = mc.probability;
		this.hasMoven = mc.hasMoven;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinimaxCell other = (MinimaxCell) obj;
		if (hasMoven != other.hasMoven)
			return false;
		if (hasPiece != other.hasPiece)
			return false;
		if (isRed != other.isRed)
			return false;
		if (knownByOpponent != other.knownByOpponent)
			return false;
		if (pType != other.pType)
			return false;
		if (probability == null) {
			if (other.probability != null)
				return false;
		} else if (!probability.equals(other.probability))
			return false;
		return true;
	}
	
	public int isMovable(boolean _isAIRed) {
		if(water) {
			return 0;
		}
		if(hasPiece == false) {
			return -1;
		}	
		if(isRed != _isAIRed) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setCellLocation(int rowIndex, int colIndex, boolean isWater) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.water = isWater;
	}

	public void setPieceBasics(boolean hasPiece, boolean isRed, boolean knownByOpponent) {
		this.hasPiece = hasPiece;
		this.isRed = isRed;
		this.knownByOpponent = knownByOpponent;
	}
	
	public void setPieceFundamentals(PieceType pType, ProbabilityDistribution pd) {
		this.pType = pType;
		this.probability = pd;
	}
	
	

}
