package edu.virginia.pnl8zp;

import java.awt.Rectangle;

public class Move {

    public static boolean validMove(Cell oldCell, Cell newCell) {
      	 if(newCell.piece != null || newCell.water == true || oldCell.piece == null || oldCell.water == true) {
      		 return false;
      	 }
//      	 if(oldCell.piece.isRed == false) {
//      		 return false;
//      	 }
      	 int rowDiff = Math.abs(newCell.rowIndex - oldCell.rowIndex);
      	 int colDiff = Math.abs(newCell.colIndex - oldCell.colIndex);
      	 if(rowDiff + colDiff > 1) {
      		 return false;
      	 }
      	 //player.move(board);
      	 return true;
    }
    
    public static Cell[][] movePiece(Cell[][] board, Cell oldCell, Cell newCell) {
    	 Piece thePiece = oldCell.piece;
         oldCell.setPiece(null); // = null;
         thePiece.setCell(newCell);
   		 Rectangle r = new Rectangle();
   		 r.x = newCell.xVal + 5;
   		 r.y = newCell.yVal + 5;
   		 r.width = 50;
   		 r.height = 50;
   		 thePiece.setRect(r);
   		 newCell.setPiece(thePiece);
   		 
   	   	 for(Cell[] cellRow : board) {
    		 for(Cell c : cellRow) {
    			 if(c.xVal == oldCell.xVal && c.yVal == oldCell.yVal) {
    				 c = oldCell;
    			 }
    			 if(c.xVal == newCell.xVal && c.yVal == newCell.yVal) {
    				 c = newCell;
    			 }
    		 }
    	 }
   	   	 
   	   	 return board;
   		 
       	 // player.move(board);
   	 }
	
}
