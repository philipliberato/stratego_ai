package edu.virginia.pnl8zp;

import java.util.Random;

public class AI implements MoveListener {
	
	@Override
	public void moveMade(Cell[][] board) {
		makeMove(board);
	}
	
	public void makeMove(Cell[][] board) {
		// System.out.println("AI move!");
		
		boolean randomMoveFound = false;
		Cell oldCell;
		Cell newCell;
		Random rand = new Random();
		int randRow = 0;
		int randCol = 0;
		int direction = 0;
		do {
			randRow = rand.nextInt(10);
			randCol = rand.nextInt(10);
			direction = rand.nextInt(4);
			
			oldCell = board[randRow][randCol];
			
			switch(direction) {
				case 0:
					if(randRow < 9) {
						randRow++;
					}
				case 1:
					if(randRow > 0) {
						randRow--;
					}
				case 2:
					if(randCol < 9) {
						randCol++;
					}
				case 3:
					if(randCol > 0) {
						randCol--;
					}
				default:
			}
			
			newCell = board[randRow][randCol];
			
			randomMoveFound = Move.validMove(oldCell, newCell);
		} while(randomMoveFound == false);
		System.out.println("AI move: (" + oldCell.rowIndex + ", " + oldCell.colIndex + ") --> (" + newCell.rowIndex + ", " + newCell.colIndex + ")");
		board = Move.movePiece(board, oldCell, newCell);
	}
	
	

}





