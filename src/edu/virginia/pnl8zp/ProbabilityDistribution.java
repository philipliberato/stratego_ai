package edu.virginia.pnl8zp;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.virginia.pnl8zp.Piece.PieceType;

public class ProbabilityDistribution {
	
	HashMap<PieceType, double[]> possibilities;


	public ProbabilityDistribution() {
		possibilities = new HashMap<PieceType, double[]>();
		
		possibilities.put(PieceType.FLAG, new double[]{1, 40, 0.025});
		possibilities.put(PieceType.BOMB, new double[]{6, 40, 0.15});
		possibilities.put(PieceType.SPY, new double[]{1, 40, 0.025});
		possibilities.put(PieceType.SCOUT, new double[]{8, 40, 0.2});
		possibilities.put(PieceType.MINER, new double[]{5, 40, 0.125});
		possibilities.put(PieceType.SERGEANT, new double[]{4, 40, 0.1});
		possibilities.put(PieceType.LIEUTENANT, new double[]{4, 40, 0.1});
		possibilities.put(PieceType.CAPTAIN, new double[]{4, 40, 0.1});
		possibilities.put(PieceType.MAJOR, new double[]{3, 40, 0.075});
		possibilities.put(PieceType.COLONEL, new double[]{2, 40, 0.05});
		possibilities.put(PieceType.GENERAL, new double[]{1, 40, 0.025});
		possibilities.put(PieceType.MARSHAL, new double[]{1, 40, 0.025});
				
	}
	
	public void generatePossibilities() {
		possibilities = new HashMap<PieceType, double[]>();
		
		possibilities.put(PieceType.FLAG, new double[]{1, 40, 0.025});
		possibilities.put(PieceType.BOMB, new double[]{6, 40, 0.15});
		possibilities.put(PieceType.SPY, new double[]{1, 40, 0.025});
		possibilities.put(PieceType.SCOUT, new double[]{8, 40, 0.2});
		possibilities.put(PieceType.MINER, new double[]{5, 40, 0.125});
		possibilities.put(PieceType.SERGEANT, new double[]{4, 40, 0.1});
		possibilities.put(PieceType.LIEUTENANT, new double[]{4, 40, 0.1});
		possibilities.put(PieceType.CAPTAIN, new double[]{4, 40, 0.1});
		possibilities.put(PieceType.MAJOR, new double[]{3, 40, 0.075});
		possibilities.put(PieceType.COLONEL, new double[]{2, 40, 0.05});
		possibilities.put(PieceType.GENERAL, new double[]{1, 40, 0.025});
		possibilities.put(PieceType.MARSHAL, new double[]{1, 40, 0.025});
	}
	
	public boolean firstTime = true;
	public void pieceMoved() {
		if(possibilities == null) {
			generatePossibilities();
		}
		if(firstTime) {
			double[] flag = possibilities.get(PieceType.FLAG);
			double[] bomb = possibilities.get(PieceType.BOMB);
			double totalRemoved = 0;
			totalRemoved += bomb[0];
			totalRemoved += flag[0];
			for(Entry<PieceType, double[]> entry : possibilities.entrySet()) {
			    PieceType key = entry.getKey();
			    double[] value = entry.getValue();
				if(key.equals(PieceType.BOMB)) {
					possibilities.put(PieceType.BOMB, new double[]{0, 0, 0});
				} else if(key.equals(PieceType.FLAG)) {
					possibilities.put(PieceType.FLAG, new double[]{0, 0, 0});
				} else {
					value[1] -= totalRemoved;
					value[2] = value[0] / value[1];
					possibilities.put(key, value);
				}
			}
			firstTime = false;
		}
	}
	
	public void updateProbabilityForKnownPiece(Piece discoveredPiece) {
		for(Cell[] c1 : Stratego.board) {
			for(Cell c : c1) {
				Piece p = c.piece;
				if(p != null) {
					if(p.isRed == discoveredPiece.isRed) {
						updateProbForRemovedPiece(discoveredPiece.getpType());
					}
				}
			}
		}
		discoveredPiece.probability.possibilities.clear();
		discoveredPiece.probability.possibilities.put(discoveredPiece.getpType(), new double[]{1, 1, 1.0});
	}
	
	public double getProbabilityWinOrTie(PieceType yourPiece) {
		double probSuccess = 0;
		for(Entry<PieceType, double[]> entry : possibilities.entrySet()) {
			if(wonBattle(yourPiece, entry.getKey())) {
			    probSuccess += entry.getValue()[2];
			}
		}
		
		return probSuccess;
	}
	
	public double getProbabilityNotPiece(PieceType pieceType) {
		return 1 - possibilities.get(pieceType)[2];
	}
	
	public double getProbabilityForPiece(PieceType pieceType) {
		return possibilities.get(pieceType)[2];
	}
	
	public double calculateProbability(double nPieces, double tPieces) {
		return nPieces / tPieces;
	}
	
	public void updateProbForRemovedPiece(PieceType pieceType) {
		for(Entry<PieceType, double[]> entry : possibilities.entrySet()) {
		    PieceType key = entry.getKey();
		    double[] value = entry.getValue();
		    
		    if(key.equals(pieceType)) {
		    	value[0] -= 1;
		    }
		    
		    value[1] -= 1;
		    value[2] = calculateProbability(value[0], value[1]);
		    possibilities.put(key, value);
		}
	}
	
	public void printProbDist() {
		for(Entry<PieceType, double[]> entry : possibilities.entrySet()) {
		    PieceType key = entry.getKey();
		    double[] value = entry.getValue();
		    System.out.println(key.toString() + ": " + value[0] + " left out of " + value[1] + ", with probability: " + value[2]);
		}
	}
	
	 public boolean wonBattle(PieceType yourPiece, PieceType otherPiece) {

	    	int aValue = Integer.parseInt(pieceTypeToString(yourPiece));
	    	int dValue;
	    	String other = pieceTypeToString(otherPiece);
	    	if(other.equals("F")) { 
	    		return true;
	    	} else if(other.equals("B")) {
	    		if(aValue == 3) {
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		dValue = Integer.parseInt(other);
	    		if(aValue == 1 && dValue == 10) {
	    			return true;
	    		}
	    		if(aValue == 10 && dValue == 1) {
	    			return false;
	    		}
	    		if(aValue > dValue) {
	    			return true;
	    		} else if(aValue < dValue) {
	    			return false;
	    		} else {
	    			// might need to adjust for tie
	    			return true;
	    		}
	    	}
	    }
	 
		public String pieceTypeToString(PieceType pType) {
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
		
}
