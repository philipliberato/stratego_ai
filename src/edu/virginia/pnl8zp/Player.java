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
	
	public Player() {
		pieces = new ArrayList<Piece>();
		setupOptions = new HashMap<PieceType, Integer>();
	}
	
	public void addListener(MoveListener listener) {
		listeners.add(listener);
	}
	
	public void move() {
		// System.out.println("Player made move!");
		
		for(MoveListener listener : listeners) {
			listener.moveMade();
		}
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
}
