package edu.virginia.pnl8zp;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

import edu.virginia.pnl8zp.Piece.PieceType;

public class StrategoResources {
	// 		FLAG, BOMB, SPY, MARSHALL, GENERAL, COLONEL, MAJOR, CAPTAIN, LIEUTENANT, SERGEANT, MINER, SCOUT

	public Image blue_flag = null;
	public Image blue_bomb = null;
	public Image blue_spy = null;
	public Image blue_marshal = null;
	public Image blue_general = null;
	public Image blue_colonel = null;
	public Image blue_major = null;
	public Image blue_captain = null;
	public Image blue_lieutenant = null;
	public Image blue_sergeant = null;
	public Image blue_miner = null;
	public Image blue_scout = null;
	public Image red_flag = null;
	public Image red_bomb = null;
	public Image red_spy = null;
	public Image red_marshal = null;
	public Image red_general = null;
	public Image red_colonel = null;
	public Image red_major = null;
	public Image red_captain = null;
	public Image red_lieutenant = null;
	public Image red_sergeant = null;
	public Image red_miner = null;
	public Image red_scout = null;
	public static Image red_piece = null;

	public StrategoResources() {
		loadImages();
	}
	
	public void loadImages() {
		this.blue_flag = loadImage("blue/", "blue_flag");
		this.blue_bomb = loadImage("blue/", "blue_bomb");
		this.blue_spy = loadImage("blue/", "blue_spy");
		this.blue_marshal = loadImage("blue/", "blue_marshal");
		this.blue_general = loadImage("blue/", "blue_general");
		this.blue_colonel = loadImage("blue/", "blue_colonel");
		this.blue_major = loadImage("blue/", "blue_major");
		this.blue_captain = loadImage("blue/", "blue_captain");
		this.blue_lieutenant = loadImage("blue/", "blue_lieutenant");
		this.blue_sergeant = loadImage("blue/", "blue_sergeant");
		this.blue_miner = loadImage("blue/", "blue_miner");
		this.blue_scout = loadImage("blue/", "blue_scout");
		this.red_flag = loadImage("red/", "red_flag");
		this.red_bomb = loadImage("red/", "red_bomb");
		this.red_spy = loadImage("red/", "red_spy");
		this.red_marshal = loadImage("red/", "red_marshal");
		this.red_general = loadImage("red/", "red_general");
		this.red_colonel = loadImage("red/", "red_colonel");
		this.red_major = loadImage("red/", "red_major");
		this.red_captain = loadImage("red/", "red_captain");
		this.red_lieutenant = loadImage("red/", "red_lieutenant");
		this.red_sergeant = loadImage("red/", "red_sergeant");
		this.red_miner = loadImage("red/", "red_miner");
		this.red_scout = loadImage("red/", "red_scout");
		StrategoResources.red_piece = loadImage("red/", "red_piece");
	}
	
	public Image loadImage(String subfolder, String filename) {
		java.net.URL url = ClassLoader.getSystemResource("images/" + subfolder + filename + ".png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		return kit.createImage(url);
	}
	
	public static int attackerWonBattle(String attacker, String defender) {
		int aValue = Integer.parseInt(attacker);
    	int dValue;
    	if(defender.equals("F")) { 
    		//System.out.println("Game Over - Battle.wonBattle()");
    		return 1;
    	} else if(defender.equals("B")) {
    		if(aValue == 3) {
    			return 1;
    		} else {
    			return -1;
    		}
    	} else {
    		if(!defender.equals("P")) {
    			dValue = Integer.parseInt(defender);
    		} else {
    			return 1;
    		}
    		if(aValue == 1 && dValue == 10) {
    			return 1;
    		}
    		if(aValue == 10 && dValue == 1) {
    			return -1;
    		}
    		if(aValue > dValue) {
    			return 1;
    		} else if(aValue < dValue) {
    			return -1;
    		} else {
    			return 0;
    		}
    	}
	}
	
	public static PieceType dummyGetNextPlayerPieceType(HashMap<PieceType, Integer> o) {
		if(o.containsKey(PieceType.FLAG)) {
			return PieceType.FLAG;
		} else if(o.containsKey(PieceType.BOMB)) {
			return PieceType.BOMB;
		} else if(o.containsKey(PieceType.MARSHAL)) {
			return PieceType.MARSHAL;
		} else if(o.containsKey(PieceType.GENERAL)) {
			return PieceType.GENERAL;
		} else if(o.containsKey(PieceType.SPY)) {
			return PieceType.SPY;
		} else if(o.containsKey(PieceType.COLONEL)) {
			return PieceType.COLONEL;
		} else if(o.containsKey(PieceType.MAJOR)) {
			return PieceType.MAJOR;
		} else if(o.containsKey(PieceType.CAPTAIN)) {
			return PieceType.CAPTAIN;
		} else if(o.containsKey(PieceType.LIEUTENANT)) {
			return PieceType.LIEUTENANT;
		} else if(o.containsKey(PieceType.SERGEANT)) {
			return PieceType.SERGEANT;
		} else if(o.containsKey(PieceType.MINER)) {
			return PieceType.MINER;
		} else if(o.containsKey(PieceType.SCOUT)) {
			return PieceType.SCOUT;
		}
		return null;
	}
	
	public static PieceType dummyGetNextAIPieceType(HashMap<PieceType, Integer> o) {	
		if(o.containsKey(PieceType.SCOUT)) {
			return PieceType.SCOUT;
		} else if(o.containsKey(PieceType.MINER)) {
			return PieceType.MINER;
		} else if(o.containsKey(PieceType.SERGEANT)) {
			return PieceType.SERGEANT;
		} else if(o.containsKey(PieceType.LIEUTENANT)) {
			return PieceType.LIEUTENANT;
		} else if(o.containsKey(PieceType.CAPTAIN)) {
			return PieceType.CAPTAIN;
		} else if(o.containsKey(PieceType.MAJOR)) {
			return PieceType.MAJOR;
		} else if(o.containsKey(PieceType.COLONEL)) {
			return PieceType.COLONEL;
		} else if(o.containsKey(PieceType.SPY)) {
			return PieceType.SPY;
		} else if(o.containsKey(PieceType.GENERAL)) {
			return PieceType.GENERAL;
		} else if(o.containsKey(PieceType.MARSHAL)) {
			return PieceType.MARSHAL;
		} else if(o.containsKey(PieceType.BOMB)) {
			return PieceType.BOMB;
		} else if(o.containsKey(PieceType.FLAG)) {
			return PieceType.FLAG;
		}
		return null;
	}
	
	public static HashMap<PieceType, Integer> assignPieceGeneticAlgorithm(HashMap<PieceType, Integer> setupOptions, Piece piece, String oType) {
		PieceType assignment = null;
		
		if(oType.equals("AI")) {
			assignment = dummyGetNextPlayerPieceType(setupOptions);
		}
		if(oType.equals("PLAYER")) {
			assignment = dummyGetNextAIPieceType(setupOptions);
		}
		piece.setpType(assignment);
		
		if(setupOptions.get(assignment) == 1) {
			setupOptions.remove(assignment);
		} else {
			setupOptions.put(assignment, setupOptions.get(assignment) - 1);
		}
		
		return setupOptions;
		
	}
	
}
