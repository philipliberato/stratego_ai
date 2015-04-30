package edu.virginia.pnl8zp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


import edu.virginia.pnl8zp.Piece.PieceType;

public class Player {
	List<MoveListener> listeners = new ArrayList<MoveListener>();
	public static ArrayList<Piece> pieces;
	public static HashMap<PieceType, Integer> setupOptions;
	public boolean isAI = false;
	public static AI AI_Instance = null;
	
	public AI getAI_Instance() {
		return AI_Instance;
	}
	
	public String stringIdentifier() {
		if(AI_Instance == null) {
			return "HUMAN";
		} else {
			return AI_Instance.stringIdentifier();
		}
	}

	public void setAI_Instance(AI aI_Instance) {
		AI_Instance = aI_Instance;
		isAI = true;
		AI_Instance.setPieces(pieces);
		AI_Instance.isAIRed(false);
	}
	
//	public static void setAI_Pieces(ArrayList<Piece> thePieces) {
//		AI_Instance.
//	}

	public Player() {
		pieces = new ArrayList<Piece>();
//		geneticPieces = new ArrayList<Piece>();
//		geneticBoard = new Cell[10][10];
		setupOptions = new HashMap<PieceType, Integer>();
		AI_Instance = new RandomAI();
	}
	
	public void addListener(MoveListener listener) {
		listeners.add(listener);
	}
	
	public boolean removePiece(Piece beatenPiece) {
		
		//Stratego.getAgent().removeDiscoveredOpponentPiece(beatenPiece);
		System.out.println("removePiece - player");
		
		for(Piece p : pieces) {
			if(p.getCell().equals(beatenPiece.getCell())) {
				Stratego.getAgent().removeDiscoveredOpponentPiece(beatenPiece);
				pieces.remove(p);
				return true;
			}
		}
				
		return false;
	}
	
	public void move() {		
		
//   	 Stratego.panel.revalidate();
//   	 Stratego.panel.repaint();  
//   	    	 
//   	 	ThreadA t = new ThreadA();
//   	 	System.out.println("ThreadMain");
//   	 	t.start();
//     
//    	synchronized(t) { 
//	    	try { 
//	    	   	 Stratego.panel.revalidate();
//	    	   	 Stratego.panel.repaint(); 
//	    		t.wait();
//	    		t.start();
//	    	} catch (Exception ex) { }
//	    	System.out.println("Back in ThreadMain");
//   	   	 Stratego.panel.revalidate();
//   	   	 Stratego.panel.repaint(); 
//			for(MoveListener listener : listeners) {
//				listener.moveMade();
//			}
//    	}
		
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				
//			}
//			
//		});
   	 
		
		for(MoveListener listener : listeners) {
			listener.moveMade();
		}
	}
	
	public void makeMoveAsAI() {
		// System.out.println("Player called makeMoveAsAI");
		if(AI_Instance.stringIdentifier().equals("RandomAI")) {
			AI_Instance.makeRandomMove(pieces, "PLAYER");
		}
		if(AI_Instance.stringIdentifier().equals("ValuedAI")) {
			AI_Instance.makeValuedMove(pieces, "PLAYER");
		}
		if(AI_Instance.stringIdentifier().equals("MinimaxAI")) {
			AI_Instance.makeMinimaxMove(pieces, "PLAYER");
		}
		//move();
	}
	
	public PieceType assignment;
	public void randomBoard(Piece p) {

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

//	public ArrayList<Piece> geneticPieces;
//	public Cell[][] geneticBoard;
//	public void geneticBoard(Piece piece) {
//		if(geneticPieces.size() < 40) {
//			geneticPieces.add(piece);
//			int row = piece.getCell().rowIndex;
//			int col = piece.getCell().colIndex;
//			geneticBoard[row][col] = piece.getCell();
//		}
//	}
}

class ThreadA extends Thread { 
    public void run() {
        synchronized(this) { 
			try {
				Thread.sleep(1000);
				System.out.println("Sleep in ThreadA");
	    	   	 Stratego.panel.removeAll();
	    	   	 Stratego.panel.updateUI(); 
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

//        	SwingUtilities.invokeLater(new Runnable() {
//
//				@Override
//				public void run() {
//					try {
//						Thread.sleep(1000);
//					   	 Stratego.panel.revalidate();
//					   	 Stratego.panel.repaint(); 
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println("Sleep in ThreadA");
//				}
//				
//			});
        	notify();
        }
    }
} 
