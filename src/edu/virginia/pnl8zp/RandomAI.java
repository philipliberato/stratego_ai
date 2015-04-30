package edu.virginia.pnl8zp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;

import edu.virginia.pnl8zp.Piece.PieceType;

// use a genetic algorithm for initially placing pieces on the board - can use for all AI instances

public class RandomAI extends AI {
	
	public ArrayList<Piece> pieces;
	public static HashMap<PieceType, Integer> setupOptions;
	public static boolean visiblePieceValues = false;
	public boolean isAIRed;
	
	public String stringIdentifier() {
		return "RandomAI";
	}
	
	public void isAIRed(boolean val) {
		isAIRed = val;
	}
	
	public void togglePieceView() {
		visiblePieceValues = !visiblePieceValues;
	}
	
	public boolean getArePieceValuesVisible() {
		return visiblePieceValues;
	}

	public RandomAI () {
		pieces = new ArrayList<Piece>();
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
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	public void setPieces(ArrayList<Piece> thePieces) {
		pieces = thePieces;
	}
	
	@Override
	public void moveMade() {
		/*
		 * I should really run this in a separate thread... or several
		 */
		if(pieces.size() > 1) {
			makeRandomMove(pieces, "AI");
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
	
	public boolean removePiece(Piece beatenPiece) {
		
		for(Piece p : pieces) {
			if(p.getCell().equals(beatenPiece.getCell())) {
				pieces.remove(p);
				return true;
			}
		}
		
		return false;
	}
	public PieceType assignment;
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
	public void removeDiscoveredOpponentPiece(Piece discoveredPiece) {

	}
	
	public void makeRandomMove(ArrayList<Piece> availablePieces, String oType) {
		
		System.out.println("makeRandomMove called by: " + oType);
		
		int pieceIndex = 0;
		boolean randomMoveFound = false;
		Cell oldCell;
		Cell newCell;
		Random rand = new Random();
		int randRow = 0;
		int randCol = 0;
		int direction = 0;
		do {
			direction = rand.nextInt(4);
			
			String checkStationary = "B";
			do {
				pieceIndex = rand.nextInt(availablePieces.size());
				checkStationary = availablePieces.get(pieceIndex).pieceTypeToString();
			} while(checkStationary.equals("B") || checkStationary.equals("F"));
			
			
			oldCell = availablePieces.get(pieceIndex).getCell();
			randRow = oldCell.rowIndex;
			randCol = oldCell.colIndex;
			
			switch(direction) {
				case 0:
					if(Move.availableSpot(randRow - 1, randCol, oType)) {
						randRow--;
						randomMoveFound = true;
						break;
					}
				case 1:
					if(Move.availableSpot(randRow, randCol + 1, oType)) {
						randCol++;
						randomMoveFound = true;
						break;
					}
				case 2:
					if(Move.availableSpot(randRow, randCol - 1, oType)) {
						randCol--;
						randomMoveFound = true;
						break;
					}
				case 3:
					if(Move.availableSpot(randRow + 1, randCol, oType)) {
						randRow++;
						randomMoveFound = true;
						break;
					}
				default:
			}
			
			newCell = Stratego.board[randRow][randCol];

		} while(randomMoveFound == false);
		Move.movePiece(oldCell, newCell);
	}
	
	@Override
	public void addDicoveredOpponentPiece(Piece discoveredPiece) {
		
	}

	@Override
	public void makeValuedMove(ArrayList<Piece> availablePieces, String oType) {
		// only implemented in the ValuedAI class
		// perhaps I could just make a call to makeRandomMove here, in the event
		// that something buggy/weird happens...
	}
	
	@Override
	public void makeMinimaxMove(ArrayList<Piece> availablePieces, String oType) {
		// only implemented in the MinimaxAI class
		// perhaps I could just make a call to makeRandomMove here, in the event
		// that something buggy/weird happens...
	}
}





