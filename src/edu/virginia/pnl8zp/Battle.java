package edu.virginia.pnl8zp;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Battle {

	public static String message;
	
    public static int battle(Piece defender, Piece attacker) {

		String format   = "           ";
		message  = "<html>Attacker: " + (attacker.getOwner() + format).substring(0, 10) + "<br>";
		message += "Defender: " + (defender.getOwner() + format).substring(0, 10) + "<br><br>";
		message += ("Winner: " + format).substring(0, 10);

		// Piece winner = null;
		Piece loser = null;
		
		boolean liveGame = true;
		int result = wonBattle(attacker, defender);
		
		if(result == 1) { // remove defender
			 message += attacker.getOwner() + " </html>";
			 // winner = attacker;
			 loser = defender;
		} else if(result == -1) { // remove attacker
			message += defender.getOwner() + " </html>";
			// winner = defender;
			loser = attacker;
		} else if(result == 0) { // remove both
			message += "DRAW! </html>";
			loser = defender;
			if(attacker.oType.equals("AI")) {
				Stratego.deadRedPieces.add(attacker.getImage().getScaledInstance(30, 30, 0));
				Stratego.getAgent().removePiece(attacker);
			} else {
				Stratego.deadBluePieces.add(attacker.getImage().getScaledInstance(30, 30, 0));
				Player.pieces.remove(attacker);
			}
		} else if(result == -2) {
			liveGame = false;
			message = "GAME OVER!!!";
			loser = defender;
		} else {
			System.out.println("Shouldn't be here.... " + result);
		}
		
		if(loser.oType.equals("AI")) {
			Stratego.deadRedPieces.add(loser.getImage().getScaledInstance(30, 30, 0));
			Stratego.getAgent().removePiece(loser);
		} else {
			Stratego.deadBluePieces.add(loser.getImage().getScaledInstance(30, 30, 0));
			Player.pieces.remove(loser);
		}

		JPanel panel = new JPanel();
		panel.add(new JLabel(new ImageIcon(attacker.getImage())));
		JLabel label = new JLabel(message);
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		panel.add(label);
		panel.add(new JLabel(new ImageIcon(defender.getImage())));
		
		if(liveGame) {
			JOptionPane.showMessageDialog(Stratego.frame, panel, "BATTLE REPORT", JOptionPane.PLAIN_MESSAGE, null);
		} else {
			String options[] = {"New Game", "Record Data", "Exit"};
			Stratego.endGameOption = JOptionPane.showOptionDialog(Stratego.frame, panel, "BATTLE REPORT", JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, null);
			Stratego.gameOver = true;
			if(attacker.oType.equals("AI")) {
				Stratego.WINNER = Stratego.getAgent().stringIdentifier();
			} else {
				Stratego.WINNER = "HUMAN";
			}
			return -2;
		}
		
		return result;
    }
    
    public static int wonBattle(Piece attacker, Piece defender) {

    	int aValue = Integer.parseInt(attacker.pieceTypeToString());
    	int dValue;
    	String checkDefense = defender.pieceTypeToString();
    	if(checkDefense.equals("F")) { 
    		System.out.println("Game Over - Battle.wonBattle()");
    		return -2;
    	} else if(checkDefense.equals("B")) {
    		if(aValue == 3) {
    			return 1;
    		} else {
    			return -1;
    		}
    	} else {
    		dValue = Integer.parseInt(checkDefense);
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
}
