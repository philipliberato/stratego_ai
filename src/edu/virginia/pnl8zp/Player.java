package edu.virginia.pnl8zp;

import java.util.ArrayList;
import java.util.List;

public class Player {
	List<MoveListener> listeners = new ArrayList<MoveListener>();
	
	public void addListener(MoveListener listener) {
		listeners.add(listener);
	}
	
	public void move(Cell[][] board) {
		System.out.println("Player made move!");
		
		for(MoveListener listener : listeners) {
			listener.moveMade(board);
		}
	}
}
