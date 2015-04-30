package edu.virginia.pnl8zp;

public class MovementVector {
	
	public int isMovableResult;
	public Piece yourPiece;
	public Cell moveLocation;
	public Piece otherPiece = null;
	public ProbabilityDistribution probDist = null;
	public double probWinOrTie = 0;
	public Cell lastMoveLocation = null;
	
	public MinimaxCell selectedPiece;
	public MinimaxCell selectedPieceDestination;
	public MinimaxCell lastMinimaxMoveLocation = null;
	public ProbabilityDistribution minimaxProbDistribution;
	
	public MovementVector(int isMovableResult, MinimaxCell selectedPiece, MinimaxCell moveLocation, MinimaxCell lastMoveLocation) {
		this.isMovableResult = isMovableResult;
		this.selectedPiece = selectedPiece;
		this.selectedPieceDestination = moveLocation;
		this.lastMinimaxMoveLocation = lastMoveLocation;
		if(isMovableResult == 1) {
			probDist = moveLocation.probability;
			if(probDist != null) {
				probWinOrTie = probDist.getProbabilityWinOrTie(selectedPiece.pType);
			}
		}
	}

	public MovementVector(int isMovableResult, Piece yourPiece, Cell moveLocation, Cell lastMoveLocation) {
		this.isMovableResult = isMovableResult;
		this.yourPiece = yourPiece;
		this.moveLocation = moveLocation;
		this.lastMoveLocation = lastMoveLocation;
		this.lastMinimaxMoveLocation = new MinimaxCell();
		if(lastMoveLocation != null) {
			this.lastMinimaxMoveLocation.setCellLocation(lastMoveLocation.rowIndex, lastMoveLocation.colIndex, lastMoveLocation.water);
		} else {
			this.lastMinimaxMoveLocation.setCellLocation(0, 0, false);
		}
		if(isMovableResult == 1) {
			otherPiece = moveLocation.piece;
			probDist = otherPiece.probability;
			probWinOrTie = probDist.getProbabilityWinOrTie(yourPiece.getpType());
		}
	}
	
	public double costFunction() {
		/*
		 * Values:
		 * 	- probWinOrTie
		 * 	- distance from nearest opponent piece
		 * 	- value of the given piece
		 * 	- how many of your piece's type are left
		 * 	- piece that is known and can be beaten
		 * 	- consideration for pieces that have never moved (may be bombs/flag)
		 * 	- is the piece currently known
		 * 	- piece location relative to the power of neighbor pieces
		 */
		
		
		double utility = 0;
		// distance
		double distance = 1;
		if(otherPiece == null) {
			distance = getNearestPieceDist();
		}
		int pVal = Integer.parseInt(yourPiece.pieceTypeToString());
		// utility += pVal;
		utility += 10 - distance;
		// System.out.println(distance);
		utility += (10 * probWinOrTie);
		
		
		if(lastMoveLocation != null && lastMoveLocation.equals(moveLocation)) {
			// System.out.println("bad move");
			utility = -100000;
		} 
		// System.out.println(utility);
		return utility;
	}
	
	public double costFunction(BoardNode node) {
		double utility = 0;
		// distance
		double distance = 1;
		if(!selectedPieceDestination.hasPiece) {
			distance = getNearestPieceDist(node);
		}
		int pVal = Integer.parseInt(selectedPiece.probability.pieceTypeToString(selectedPiece.pType));
		// utility += pVal;
		utility += 10 - distance;
		// System.out.println(distance);
		if(selectedPieceDestination.hasPiece) {
			utility += (20 * selectedPieceDestination.probability.getProbabilityWinOrTie(selectedPiece.pType));
		}
			utility += pVal;
			
//			for(int i = -2; i <= 2; i++) {
//				for(int j = -2; j <= 2; j++) {
//					if()
//					if(selectedPieceDestination.hasMoven) {
//						utility += 1;
//					}
//				}
//
//			}

		
		
		if(lastMinimaxMoveLocation != null) {
			if(lastMinimaxMoveLocation.rowIndex == selectedPieceDestination.rowIndex &&
					lastMinimaxMoveLocation.colIndex == selectedPieceDestination.colIndex) {
				utility = -100000;
			} 
		}
		// System.out.println(utility);
		return utility;
	}
	
	public double getNearestPieceDist() {
		double nearestPieceDist = 10;
		for(Cell[] c1 : Stratego.board) {
			for(Cell c : c1) {
				if(c.piece != null) {
					if(c.piece.isRed != yourPiece.isRed) {
						double d = Math.hypot((moveLocation.rowIndex - c.piece.getCell().rowIndex), 
								(moveLocation.colIndex - c.piece.getCell().colIndex));
						if(d < nearestPieceDist) {
							nearestPieceDist = d;
						}
					}
				}
			}
		}
		return nearestPieceDist;
	}
	
	public double getNearestPieceDist(BoardNode node) {
		double nearestPieceDist = 10;
		for(MinimaxCell[] c1 : node.getBoard()) {
			for(MinimaxCell c : c1) {
				if(c.hasPiece) {
					if(c.isRed != node.isAIRed) {
						double d = Math.hypot((selectedPieceDestination.rowIndex - c.rowIndex), 
								(selectedPieceDestination.colIndex - c.colIndex));
						if(d < nearestPieceDist) {
							nearestPieceDist = d;
						}
					}
				}
			}
		}
		return nearestPieceDist;
	}
	
	public void printMovementVector() {
		System.out.println("Piece: " + yourPiece.getpType().toString()
				+ " - moving from: r" + yourPiece.getCell().rowIndex + " c" + yourPiece.getCell().colIndex
				+ " to: r" + moveLocation.rowIndex + " c" + moveLocation.colIndex);
		if(isMovableResult == 1) {
			System.out.println("\tProbOfWinOrTie: " + probDist.getProbabilityWinOrTie(yourPiece.getpType()));
		}
	}
	
}
