package edu.virginia.pnl8zp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map.Entry;

import javax.swing.tree.TreeNode;

import edu.virginia.pnl8zp.Piece.PieceType;

public class BoardNode implements TreeNode {
	
	public MinimaxCell[][] boardForMovementVector;
	public int depth;
	public int depthLimit;
	public BoardNode parent = null;
	public ArrayList<BoardNode> children = null;
	public boolean isAIRed;
	public MovementVector movementVector = null;
	public MinimaxCell oLocation;
	public MinimaxCell nLocation;
	public MinimaxCell oldCellData;
	public double boardStateUtility = -100;
	public ProbabilityDistribution globalProbDistribution;
	public ArrayList<MinimaxCell> yourPieces;
	public ArrayList<MinimaxCell> opponentPieces;
	public ArrayList<MovementVector> availableMoves;
	
	public BoardNode(MinimaxCell[][] parentBoard, BoardNode parent, MovementVector mv, int depth, int depthLimit, boolean isAIRed) {
		this.movementVector = mv;
		this.depth = depth;
		this.depthLimit = depthLimit;
		this.parent = parent;
		this.isAIRed = isAIRed;
		children = new ArrayList<BoardNode>();
		calculateBoardState(parentBoard);
		getPieces();
		availableMoves = getAvailableMoves();
		if(parent != null) {
			// System.out.println("parent isn't null - calculating board");
			//this.boardForMovementVector = calculateBoardState(parent.getBoard().clone());
			boardStateUtility = utilityFunction();
		}
	}
	
	public void addChild(BoardNode child) {
		children.add(child);
	}
	
	public ArrayList<BoardNode> getChildren() {
		return children;
	}
	
	public MinimaxCell flagCell = null;
	public void getPieces() {
		yourPieces = new ArrayList<MinimaxCell>();
		opponentPieces = new ArrayList<MinimaxCell>();
    	for(MinimaxCell[] mc1 : boardForMovementVector) {
    		for(MinimaxCell mc : mc1) {
    			if(mc.hasPiece && mc.isRed == isAIRed && mc.pType != null) {
    				yourPieces.add(mc);
    				if(mc.pType.equals(PieceType.FLAG)) {
    					flagCell = mc;
    				}
    			}
    			if(mc.hasPiece && mc.isRed != isAIRed && mc.pType != null) {
    				opponentPieces.add(mc);
    			}
    		}
    	}
	}
	
	public ArrayList<MovementVector> getAvailableMoves() {
		//ArrayList<MinimaxCell> yourPieceCells = getYourPieces();
		// System.out.println("number of pieces: " + yourPieceCells.size());
		ArrayList<MovementVector> availableMoves = new ArrayList<MovementVector>();
    	for(MinimaxCell c : yourPieces) {
    		int rowIndex = c.rowIndex;
    		int colIndex = c.colIndex;
    		if(c.pType != PieceType.BOMB && c.pType != PieceType.FLAG) {
        		for(int i = -1; i <= 1; i++) {
        			for(int j = -1; j <= 1; j++) {
        				if(Math.abs(i) != Math.abs(j)) {
        					int row = rowIndex + i;
        					int col = colIndex + j;
        					if((0 <= row && row <= 9) && (0 <= col && col <= 9)) {   	
        						int isMovableResult = boardForMovementVector[row][col].isMovable(isAIRed);
            		    		if(isMovableResult != 0) {
            		    			if(movementVector != null) {
                		    			availableMoves.add(new MovementVector(isMovableResult, new MinimaxCell(c), new MinimaxCell(boardForMovementVector[row][col]), movementVector.selectedPiece));
            		    			} else {
            		    				MinimaxCell dummy = new MinimaxCell();
            		    				dummy.setCellLocation(0, 0, false);
                		    			availableMoves.add(new MovementVector(isMovableResult, new MinimaxCell(c), new MinimaxCell(boardForMovementVector[row][col]), dummy));
            		    			}
            		    		}
        					}
        				}
        			}
        		}
    		}
    	}
    	return availableMoves;
	}
	
	public MinimaxCell[][] getBoard() {
		return boardForMovementVector;
	}
	
	public void setBoard(MinimaxCell[][] board) { // manually construct root node board from Stratego.board.clone()
		boardForMovementVector = board;		
	}
	
	public void calculateBoardState(MinimaxCell[][] parentBoardState) {
		// MinimaxCell[][] currentBoardState = parentBoardState.clone();
		boardForMovementVector = new MinimaxCell[10][10];
		
		for(MinimaxCell[] mcRow : parentBoardState) {
			for(MinimaxCell mc : mcRow) {
				MinimaxCell mCell = new MinimaxCell(mc);
//				mCell.setCellLocation(mc.rowIndex, mc.colIndex, mc.water);
//				
//				if(mc.pType != null) {
//					mCell.setPieceBasics(true, mc.isRed, mc.knownByOpponent);
//					mCell.setPieceFundamentals(mc.pType, mc.probability);
//				} else {
//					mCell.setPieceBasics(false, false, false);
//					mCell.setPieceFundamentals(null, null);
//				}

				boardForMovementVector[mCell.rowIndex][mCell.colIndex] = mCell;
			}
		}
		
		if(movementVector != null) {
			oLocation = movementVector.selectedPiece;
			nLocation = movementVector.selectedPieceDestination;
			oLocation = boardForMovementVector[oLocation.rowIndex][oLocation.colIndex];
			oldCellData = boardForMovementVector[oLocation.rowIndex][oLocation.colIndex];
			nLocation = boardForMovementVector[nLocation.rowIndex][nLocation.colIndex];
//			
//			oLocation.hasPiece = false;
//			nLocation.hasPiece = true;
//			
//			nLocation.hasMoven = true;
//			// nLocation.probability = updatedProbabilityDistribution();
//			nLocation.probability = oLocation.probability;
//			nLocation.isRed = oLocation.isRed;
//			nLocation.pType = oLocation.pType;
//			nLocation.knownByOpponent = true;
			
			nLocation.setPieceBasics(true, isAIRed, true);
			nLocation.setPieceFundamentals(oLocation.pType, oLocation.probability);

			MinimaxCell blank = new MinimaxCell();
			blank.setCellLocation(oLocation.rowIndex, oLocation.colIndex, oLocation.water);
			oLocation.setPieceBasics(false, false, false);
			oLocation.setPieceFundamentals(null, null);
			boardForMovementVector[oLocation.rowIndex][oLocation.colIndex] = blank;
			boardForMovementVector[nLocation.rowIndex][nLocation.colIndex] = nLocation;
		}
		
	}
	
	public ProbabilityDistribution updatedProbabilityDistribution() {
		// I know who wins
		if(movementVector.isMovableResult == -1) { // moved to empty space
			nLocation.isRed = isAIRed;
			nLocation.pType = movementVector.selectedPiece.pType;
			return movementVector.selectedPiece.probability;
		} else { // battle
			MinimaxCell opponent = movementVector.selectedPieceDestination;
			MinimaxCell attacker = movementVector.selectedPiece;
			if(opponent.knownByOpponent) {
				String defenderVal = Piece.pieceTypeToString(opponent.pType);
				String attackerVal = Piece.pieceTypeToString(attacker.pType);
				int wonBattle = StrategoResources.attackerWonBattle(attackerVal, defenderVal);
				if(wonBattle == 1) {
					this.oLocation.probability = null;
					this.nLocation.isRed = isAIRed;
					nLocation.pType = movementVector.selectedPiece.pType;
					return movementVector.selectedPiece.probability;
				} else if(wonBattle == -1) {
					this.oLocation.probability = null;
					return movementVector.selectedPieceDestination.probability;
				} else {
					this.oLocation.probability = null;
					this.nLocation.hasPiece = false;
					return null;
				}
			} else {
				// what should I return here? =========================================================================================================
				this.nLocation.isRed = isAIRed;
				return movementVector.selectedPiece.probability;
			}
		}
	}

	public double utilityFunction() {
		double utility = 0;
//		for(MovementVector mv : availableMoves) {
//			if(mv.selectedPieceDestination.hasPiece) {
//				utility += (1 * mv.selectedPieceDestination.probability.getProbabilityWinOrTie(mv.selectedPiece.pType));
//			} else {
//				utility += 0.01;
//			}
//		}
//		double yourR = 0;
//		double yourC = 0;
//		for(MinimaxCell m : yourPieces) {
//			yourR += m.rowIndex;
//			yourC += m.colIndex;
//		}
//		double opponentR = 0;
//		double opponentC = 0;
//		for(MinimaxCell m : opponentPieces) {
//			opponentR += m.rowIndex;
//			opponentC += m.colIndex;
//		}
//		int totalHumanPieces = 0;
//		for(Entry<PieceType, Integer> entry : possibilities.entrySet()) {
//		    PieceType key = entry.getKey();
//		    double[] value = entry.getValue();
//		    System.out.println(key.toString() + ": " + value[0] + " left out of " + value[1] + ", with probability: " + value[2]);
//		}
//		
//		if(isAIRed == true) {
//			
//		}
		
		double w1 = 0.5;
		double w2 = 1;
		double w3 = 1;
		double w4 = 0.3;
		double w5 = 0;
		double w6 = 0.1;
		double w7 = 0;
		
		double attackedPiece = 0;
		double personalStrength = 0;
		double opponentStrength = 0;
		double flagProtectionStrength = 0;
		double minerBombSearchFactor = 0;
		double verticalMovement = 0;
		double distanceToUnknowns = 0;
		
		if(oldCellData.hasPiece && oldCellData.isRed != isAIRed) {
			attackedPiece = 1;
			if(oldCellData.probability.getProbabilityWinOrTie(movementVector.selectedPiece.pType) > 0.5) {
				attackedPiece += 3;
			}
		}
		
		if(isAIRed == true && movementVector.selectedPieceDestination.rowIndex > movementVector.selectedPiece.rowIndex) {
			verticalMovement = 1;
		}
		if(isAIRed == false && movementVector.selectedPieceDestination.rowIndex < movementVector.selectedPiece.rowIndex) {
			verticalMovement = 1;
		}
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				int row = flagCell.rowIndex + i;
				int col = flagCell.colIndex + j;
				if((0 <= row && row <= 9) && (0 <= col && col <= 9)) {
					MinimaxCell flagNeighbor = boardForMovementVector[row][col];
					if(i != 0 && j != 0) {
						if(flagNeighbor.hasPiece && flagNeighbor.isRed == isAIRed) {
							if(flagNeighbor.pType.equals(PieceType.BOMB)) {
								flagProtectionStrength += 5;
							} else {
								flagProtectionStrength += Integer.parseInt(flagNeighbor.probability.pieceTypeToString(flagNeighbor.pType));
							}
						}
					}
				}
			}
		}
		
		for(MinimaxCell mc : yourPieces) {
			if(mc.pType.equals(PieceType.BOMB)) {
				personalStrength += 5;
			} else if(mc.pType.equals(PieceType.SPY)) {
				personalStrength += 5;
			} else {
				if(!mc.pType.equals(PieceType.FLAG)) {
					personalStrength += Integer.parseInt(mc.probability.pieceTypeToString(mc.pType));
				}
			}
		}
		
		for(MinimaxCell mc : opponentPieces) {
			if(mc.pType.equals(PieceType.BOMB)) {
				opponentStrength += 5;
			} else if(mc.pType.equals(PieceType.SPY)) {
				opponentStrength += 5;
			} else {
				if(!mc.pType.equals(PieceType.FLAG)) {
					opponentStrength += Integer.parseInt(mc.probability.pieceTypeToString(mc.pType));
				}
			}
		}
		
		utility += w1 * attackedPiece;
		utility += w2 * personalStrength;
		utility -= w3 * opponentStrength;
		utility	+= w4 * flagProtectionStrength;
		utility += w5 * minerBombSearchFactor;
		utility += w6 * verticalMovement;
		utility += w7 * distanceToUnknowns;
		
		// repeat penalty
//		if(movementVector.lastMinimaxMoveLocation == movementVector.selectedPieceDestination) {
//			utility -= 100;
//		}
		
		// utility += 10 - Math.hypot((yourR - opponentR), (yourC - opponentC));

		return utility;
	}

	@Override
	public Enumeration<BoardNode> children() {
		return null;
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int index) {
		if(children.size() > index) {
			return children.get(index);
		} else {
			return null;
		}
	}

	@Override
	public int getChildCount() {
		return children.size();
	}

	@Override
	public int getIndex(TreeNode t) {
		return 0;
	}

	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		if(depth == depthLimit) {
			return true;
		} else {
			return false;
		}
	}

}
