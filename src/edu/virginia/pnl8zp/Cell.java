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
		if(this.piece != null && piece != null) {
			if(!piece.getOwner().equals(this.piece.getOwner())) {
				int retVal = Battle.battle(this.piece, piece); // defender, attacker
				this.piece = handleBattleResult(retVal, piece);
				return;
			}
		}
		this.piece = piece;
	}
	
	public Piece handleBattleResult(int retVal, Piece piece) {
		switch(retVal) {
			case -1:
				return this.piece;
			case 0:
				return null;
			case 1:
				return piece;
			default:
				// System.out.println("Cell.handleBattleResult - default case");
				return null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (xVal != other.xVal)
			return false;
		if (yVal != other.yVal)
			return false;
		return true;
	}

}
