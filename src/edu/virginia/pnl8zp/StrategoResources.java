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
	
	// HashMap<PieceType, Integer> aiSetupOptions = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
	// HashMap<PieceType, Integer> playerSetupOptions = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
	
	public static void assignPieceGeneticAlgorithm(Piece p, String oType) {
//		if(oType.equals("AI")) {
//			
//		}
//		if(oType.equals("PLAYER")) {
//			
//		}
		// p.setpType(assignGeneticPieceType(p.getCell(), oType));
		
	}
	
}
