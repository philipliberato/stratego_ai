package edu.virginia.pnl8zp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
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
		
		// hTheta: 0.9672881802569002
		
		double w1 = 0.97970;
		double w2 = 0.88921; //0.5;
		double w3 = 0.86356; //0.5;
		double w4 = 0.82001; //0.3;
		double w5 = 0.5996; //0.5;
		double w6 = 0.47643; //0.3;
		double w7 = 0.83684;
		
		// Training Data For Logistic Regression
		
//		TrainingDataInputs trainingData = Stratego.trainingData;
//		
//		int element = 0;
//		
//		w1 = trainingData.trainingDataInputs[element][0];
//		w2 = trainingData.trainingDataInputs[element][1];
//		w3 = trainingData.trainingDataInputs[element][2];
//		w4 = trainingData.trainingDataInputs[element][3];
//		w5 = trainingData.trainingDataInputs[element][4];
//		w6 = trainingData.trainingDataInputs[element][5];
		
		// -------------------------------------		
		
		double attackedPiece = 0;
		double personalStrength = 0;
		double opponentStrength = 0;
		double flagProtectionStrength = 0;
		double minerBombSearchFactor = 0;
		double verticalMovement = 0;
		double distanceToUnknowns = 0;
		
//		if(movementVector.lastMinimaxMoveLocation != null && movementVector.lastMinimaxMoveLocation == movementVector.selectedPieceDestination) {
//			utility += 100;
//		}
		
		if(oldCellData.hasPiece && oldCellData.isRed != isAIRed) {
			attackedPiece = 1;
			double victoryProb = oldCellData.probability.getProbabilityWinOrTie(movementVector.selectedPiece.pType);
			if(victoryProb == 0) {
				attackedPiece -= 100;
			} else if(victoryProb < 0.5) {
				attackedPiece -= 3;
			}else { // victoryProb > 0.5
				attackedPiece += 3;
			}
			if(victoryProb == 1) {
				attackedPiece += 10;
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
		
		MinimaxCell you = movementVector.selectedPieceDestination;
		// double xVal = 100;
		// double yVal = 100;
		double distance = 100;
		for(MinimaxCell o : opponentPieces) {
			if(o.hasMoven == false) {
				double tempDist = Math.hypot(you.colIndex - o.colIndex, you.rowIndex - o.rowIndex);
				if(tempDist < distance) {
					distance = tempDist;
				}
			}
		}
		if(distance != 100) {
			minerBombSearchFactor += 10 - distance;
		}
		
		// int numUnseen = 0;
		// MinimaxCell you = movementVector.selectedPiece;
		// xVal = 100;
		// yVal = 100;
		distance = 100;
		for(MinimaxCell o : opponentPieces) {
			if(o.knownByOpponent == false) {
				double tempDist = Math.hypot(you.colIndex - o.colIndex, you.rowIndex - o.rowIndex);
				if(tempDist < distance) {
					distance = tempDist;
				}
			}
		}
		if(distance != 100) {
			distanceToUnknowns += 10 - distance;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((availableMoves == null) ? 0 : availableMoves.hashCode());
		result = prime * result + Arrays.hashCode(boardForMovementVector);
		long temp;
		temp = Double.doubleToLongBits(boardStateUtility);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result + depth;
		result = prime * result + depthLimit;
		result = prime * result
				+ ((flagCell == null) ? 0 : flagCell.hashCode());
		result = prime
				* result
				+ ((globalProbDistribution == null) ? 0
						: globalProbDistribution.hashCode());
		result = prime * result + (isAIRed ? 1231 : 1237);
		result = prime * result
				+ ((movementVector == null) ? 0 : movementVector.hashCode());
		result = prime * result
				+ ((nLocation == null) ? 0 : nLocation.hashCode());
		result = prime * result
				+ ((oLocation == null) ? 0 : oLocation.hashCode());
		result = prime * result
				+ ((oldCellData == null) ? 0 : oldCellData.hashCode());
		result = prime * result
				+ ((opponentPieces == null) ? 0 : opponentPieces.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((yourPieces == null) ? 0 : yourPieces.hashCode());
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
		BoardNode other = (BoardNode) obj;
		if (availableMoves == null) {
			if (other.availableMoves != null)
				return false;
		} else if (!availableMoves.equals(other.availableMoves))
			return false;
		if (!Arrays.deepEquals(boardForMovementVector,
				other.boardForMovementVector))
			return false;
		if (Double.doubleToLongBits(boardStateUtility) != Double
				.doubleToLongBits(other.boardStateUtility))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (depth != other.depth)
			return false;
		if (depthLimit != other.depthLimit)
			return false;
		if (flagCell == null) {
			if (other.flagCell != null)
				return false;
		} else if (!flagCell.equals(other.flagCell))
			return false;
		if (globalProbDistribution == null) {
			if (other.globalProbDistribution != null)
				return false;
		} else if (!globalProbDistribution.equals(other.globalProbDistribution))
			return false;
		if (isAIRed != other.isAIRed)
			return false;
		if (movementVector == null) {
			if (other.movementVector != null)
				return false;
		} else if (!movementVector.equals(other.movementVector))
			return false;
		if (nLocation == null) {
			if (other.nLocation != null)
				return false;
		} else if (!nLocation.equals(other.nLocation))
			return false;
		if (oLocation == null) {
			if (other.oLocation != null)
				return false;
		} else if (!oLocation.equals(other.oLocation))
			return false;
		if (oldCellData == null) {
			if (other.oldCellData != null)
				return false;
		} else if (!oldCellData.equals(other.oldCellData))
			return false;
		if (opponentPieces == null) {
			if (other.opponentPieces != null)
				return false;
		} else if (!opponentPieces.equals(other.opponentPieces))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (yourPieces == null) {
			if (other.yourPieces != null)
				return false;
		} else if (!yourPieces.equals(other.yourPieces))
			return false;
		return true;
	}
	
	

}
