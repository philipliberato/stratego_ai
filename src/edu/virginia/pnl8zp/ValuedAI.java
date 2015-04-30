package edu.virginia.pnl8zp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import edu.virginia.pnl8zp.Piece.PieceType;

/*
 * Code to implement:
 * 	- moveMade()
 * 	- makeValuedMove()
 */

public class ValuedAI extends AI {

	public ArrayList<Piece> pieces;
	public static HashMap<PieceType, Integer> setupOptions;
	public static boolean visiblePieceValues = false;
	public ArrayList<Piece> knownOpponentPieces;
	public boolean isAIRed;
	public Cell lastPieceLocation = null;
	
	public void isAIRed(boolean val) {
		isAIRed = val;
	}
	
	@Override
	public String stringIdentifier() {
		return "ValuedAI";
	}
	
	@Override
	public void togglePieceView() {
		visiblePieceValues = !visiblePieceValues;
	}
	
	@Override
	public boolean getArePieceValuesVisible() {
		return visiblePieceValues;
	}
	
	public ValuedAI() {
		pieces = new ArrayList<Piece>();
		knownOpponentPieces = new ArrayList<Piece>();
		setupOptions = new HashMap<PieceType, Integer>();
		loadSetupOptions();	
	}
	
	public void loadSetupOptions() {
		setupOptions.put(PieceType.FLAG, 1);
		setupOptions.put(PieceType.SPY, 1);
		setupOptions.put(PieceType.GENERAL, 1);
		setupOptions.put(PieceType.MARSHAL, 1);
		setupOptions.put(PieceType.COLONEL, 2);
		setupOptions.put(PieceType.MAJOR, 3);
		setupOptions.put(PieceType.SERGEANT, 4);
		setupOptions.put(PieceType.LIEUTENANT, 4);
		setupOptions.put(PieceType.CAPTAIN, 4);
		setupOptions.put(PieceType.MINER, 5);
		setupOptions.put(PieceType.BOMB, 6);
		setupOptions.put(PieceType.SCOUT, 8);

	}
	
	@Override
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	@Override
	public void setPieces(ArrayList<Piece> thePieces) {
		pieces = thePieces;
	}

	@Override
	public void moveMade() {
		if(pieces.size() > 1) {
			makeValuedMove(pieces, "AI");
			if(Stratego.player.isAI) {
				// System.out.println("Player is AI");
				Stratego.player.makeMoveAsAI();
			} else {
				// System.out.println("Player is Human");
			}
		} else {
		   	 JOptionPane.showMessageDialog(null, "YOU WIN!!!!");
		}
	}

	@Override
	public boolean removePiece(Piece beatenPiece) {
		for(Piece p : pieces) {
			if(p.getCell().equals(beatenPiece.getCell())) {
				if(Stratego.getPlayer().isAI) {
					Stratego.getPlayer().getAI_Instance().removeDiscoveredOpponentPiece(beatenPiece);
				}
				pieces.remove(p);
				// removeDiscoveredOpponentPiece(beatenPiece);
				return true;
			}
		}	
		return false;
	}
	
	@Override
	public void removeDiscoveredOpponentPiece(Piece discoveredPiece) {
		if(knownOpponentPieces.contains(discoveredPiece)) {
			knownOpponentPieces.remove(discoveredPiece);
		}
	}

	public PieceType assignment;
	@Override
	public void assignPieceType(Piece p) {

		int size = setupOptions.keySet().size();
		int item = new Random().nextInt(size);
		int i = 0;
		
		for(Object obj : setupOptions.keySet()) {
		    if (i == item) {
		    	assignment = (PieceType) obj;
		    	setupOptions.put(assignment, setupOptions.get(assignment) - 1);
		    	p.setpType(assignment);
		    	break;
		    }
		    i++;
		}
		if(setupOptions.get(assignment) == 0) {
			setupOptions.remove(assignment);
		}
	}

	@Override
	public void makeRandomMove(ArrayList<Piece> availablePieces, String oType) {
		// only implemented in the RandomAI class
		// perhaps I could just make a call to makeValuedMove here, in the event
		// that something buggy/weird happens...	
	}
	
	@Override
	public void addDicoveredOpponentPiece(Piece discoveredPiece) {
		// implement this
		if(knownOpponentPieces.contains(discoveredPiece) == false) {
			knownOpponentPieces.add(discoveredPiece);			
		}
	}
	
	public ArrayList<Piece> getKnownOpponentPieces() {
		ArrayList<Piece> knownOpponentPieces = new ArrayList<Piece>();
		
		for(Cell[] c : Stratego.board) {
			for(Cell cell : c) {
				if(cell.getPiece() != null) {
					Piece aPiece = cell.getPiece();
					if(aPiece.knownByOpponent && aPiece.isRed != isAIRed) {
						knownOpponentPieces.add(aPiece);
					}
				}
			}
		}
		
		return knownOpponentPieces;
	}

	@Override
	public void makeValuedMove(ArrayList<Piece> availablePieces, String oType) {
		System.out.println("makeValuedMove called by: " + oType);
		
		ArrayList<Piece> knownOpponentPieces = getKnownOpponentPieces();
		
		for(Piece p : knownOpponentPieces) {
			System.out.println("Known - " + p.pieceTypeToString() + ": " + p.getCell().rowIndex + ", " + p.getCell().colIndex);
		}
		
		ArrayList<Piece> yourPieces = new ArrayList<Piece>();
		
		// get your pieces
    	for(Cell[] c1 : Stratego.board) {
    		for(Cell c : c1) {
    			if(c.piece != null) {
    				Piece p = c.piece;
    				if(p.isRed == isAIRed) {
    					yourPieces.add(p);
    				}
    			}
    		}
    	}
    	
    	System.out.println("Number of pieces: " + yourPieces.size());
    	
    	ArrayList<MovementVector> availableMoves = new ArrayList<MovementVector>();
    	
    	// get all possible moves
    	for(Piece p : yourPieces) {
    		int rowIndex = p.getCell().rowIndex;
    		int colIndex = p.getCell().colIndex;
    		if(p.getpType() != PieceType.BOMB && p.getpType() != PieceType.FLAG) {
        		for(int i = -1; i <= 1; i++) {
        			for(int j = -1; j <= 1; j++) {
        				if(Math.abs(i) != Math.abs(j)) {
        					int row = rowIndex + i;
        					int col = colIndex + j;
        					if((0 <= row && row <= 9) && (0 <= col && col <= 9)) {   	
        						int isMovableResult = Stratego.board[row][col].isMovable(isAIRed);
            		    		if(isMovableResult != 0) {
            		    			availableMoves.add(new MovementVector(isMovableResult, p, Stratego.board[row][col], lastPieceLocation));
            		    		}
        					}
        				}
        			}
        		}
    		}
    	}
    	
    	System.out.println("Number of possible moves: " + availableMoves.size());
    	for(MovementVector mv : availableMoves) {
    		mv.printMovementVector();
    	}
    	System.out.println("--------");

//    	PriorityQueue<MovementVector> prioritizeMoves = new PriorityQueue<MovementVector>(availableMoves.size(), new MovementVectorComparator());
//    	for(MovementVector mv : availableMoves) {
//    		prioritizeMoves.add(mv);
//    	}
//    	MovementVector best = prioritizeMoves.poll();
//    	best.printMovementVector();
    	
    	MovementVector best = null;
    	for(MovementVector mv : availableMoves) {
    		if(best == null) {
    			best = mv;
    		} else {
    	   		if(mv.costFunction() > best.costFunction()) {
        			best = mv;
        		}
    		}
    	}
    	best.printMovementVector();
    	// System.out.println(best.costFunction());
		
    	lastPieceLocation = best.yourPiece.getCell();
		Move.movePiece(lastPieceLocation, best.moveLocation);
		
		
		// goal of method is to get oldCell and newCell, movePiece fits into the framework
		// Move.movePiece(oldCell, newCell);
	}
	
	@Override
	public void makeMinimaxMove(ArrayList<Piece> availablePieces, String oType) {
		// only implemented in the MinimaxAI class
		// perhaps I could just make a call to makeValuedMove here, in the event
		// that something buggy/weird happens...
	}

}
