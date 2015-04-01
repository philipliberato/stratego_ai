package edu.virginia.pnl8zp;

import java.awt.Rectangle;

public class Move {
	
	 public static Battle b = new Battle();
	
	public static boolean availableSpot(int row, int col) {
		if(row > 9 || row < 0 || col > 9 || col < 0) {
			return false;
		}
		Cell c = Stratego.board[row][col];
		if(c.water) {
			return false;
		}
		if(c.getPiece() == null) {
			return true;
		}
		if(c.getPiece() != null && c.getPiece().oType.equals("AI")) {
			return false;
		}

		return true;
	}

    public static boolean validMove(Cell oldCell, Cell newCell) {
      	 if(newCell.water == true || oldCell.piece == null || oldCell.water == true) {
      		 return false;
      	 }
//      	 if(oldCell.piece.isRed == false) {
//      		 return false;
//      	 }
      	 
      	 if(newCell.piece != null && oldCell.piece.oType.equals(newCell.piece.oType)) {
      		 return false;
      	 }
      	 
      	 String checkStationary = oldCell.piece.pieceTypeToString();
      	 if(checkStationary.equals("B") || checkStationary.equals("F")) {
      		 return false;
      	 }
      	 
      	 int rowDiff = Math.abs(newCell.rowIndex - oldCell.rowIndex);
      	 int colDiff = Math.abs(newCell.colIndex - oldCell.colIndex);
      	 int totalDiff = rowDiff + colDiff;
      	 if(totalDiff > 1 || totalDiff == 0) {
      		 return false;
      	 }
      	 //player.move(board);
      	 return true;
    }
    
    public static void movePiece(Cell oldCell, Cell newCell) {
    	 Piece thePiece = oldCell.piece;
   		 Rectangle r = new Rectangle();
   		 r.x = newCell.xVal; // + 5;
   		 r.y = newCell.yVal; // + 5;
   		 r.width = 50;
   		 r.height = 50;
   		 
//   		 Piece slider = null;
//   		 for(Piece p : Stratego.getAgent().getPieces()) {
//   			 if(p.getCell().equals(oldCell)) {
//   				 slider = p;
//   			 }
//   		 }
//   		 for(Piece p : Stratego.player.pieces) {
//   			 if(p.getCell().equals(oldCell)) {
//   				 slider = p;
//   			 }
//   		 }
//   		 
//   		 if(thePiece.oType.equals("AI")) {
//	   		//  for(int i = 0; i < 60; i += 6) {
//	   			 slider.setRect(new Rectangle(300, 300, 50, 50));
//	   			 slider.setCell(new Cell(300, 300, 50));
//	   			 // oldCell.setPiece(thePiece);
////	   			 Stratego.frame.paintComponents(Stratego.gPieces);
////	   			 Stratego.frame.paint(Stratego.gPieces);
//	   			 //System.out.println("AI movement: " + i);
////	   	   		 try {
////	   				 Thread.sleep(500);
////	   			 } catch (InterruptedException e) {
////	   				 e.printStackTrace();
////	   			 }
//	   		 
//   		 }
         thePiece.setCell(newCell);
         oldCell.setPiece(null); // = null;
   		 thePiece.setRect(r);

   		 newCell.setPiece(thePiece);
   		 
   	   	 for(Cell[] cellRow : Stratego.board) {
    		 for(Cell c : cellRow) {
    			 if(c.xVal == oldCell.xVal && c.yVal == oldCell.yVal) {
    				 c = oldCell;
    			 }
    			 if(c.xVal == newCell.xVal && c.yVal == newCell.yVal) {
    				 c = newCell;
    			 }
    		 }
    	 }
   	   	    		 
       	 // player.move(board);
   	 }
	
}
