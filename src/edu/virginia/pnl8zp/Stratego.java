package edu.virginia.pnl8zp;
/**
 * @author Philip Liberato
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
 
public class Stratego extends JApplet implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L; // why do I need this?
	// private JButton  button;
    // private ArrayList<ArrayList<JButton>> board;
    private static final int POSITION_WIDTH = 60; // i'll modify this when I determine the piece size
    private static final int PIECE_WIDTH = 50;
    private static final int BOARD_WIDTH = 600; //i'll modify this when I determine the board size
    private static final Color grass = new Color(129, 214, 111);
    private static final Color water = new Color(64, 172, 245);
	private static final Color bluePlayer = new Color(20, 67, 114);
	private static final Color redPlayer = new Color(227, 61, 64);
    private Cell[][] board = new Cell[10][10];
    private Piece[][] pieces = new Piece[10][10];
    private Player player = new Player();
    private AI agent = new AI();
    
    // this method acts like the applet constructor
    public void init() {
//        button = new JButton("Click me!");
//        button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                JOptionPane.showMessageDialog(QuickApplet.this,
//                    "Hello! I am an applet!");
//            }
//        });
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	setBackground(grass);
        System.out.println("APPLET IS INITIALIZED");
        
        player.addListener(agent);
    }
 
    public void start() {
        // getContentPane().add(button);
        System.out.println("APPLET IS STARTED");
    }
 
    public void stop() {
        System.out.println("APPLET IS STOPPED");
    }
 
    public void destroy() {
        System.out.println("APPLET IS DESTROYED");
    }
    Boolean firstTime = true;
    public void paint(Graphics g) {
    	if(firstTime) {
    		initializeBoard();
    		drawBoard(g);
    		drawPieces(g);
    		firstTime = false;
    	} else {
    		drawBoard(g);
    		drawPieces(g);
    	}
    }
    
    public void drawPieces(Graphics g) {
				
    	for(int row = 0; row < 10; row++) {
    		for(int col = 0; col < 10; col++) {
    			Cell c = board[row][col];
    			Piece p = c.piece;
    			if(p != null) {
    				if(g.getColor() != p.getColor()) {
    					g.setColor(p.getColor());
    				}
    				Rectangle r = p.getRect();
    	    		// g.fillRect(c.xVal + 5, c.yVal + 5, c.width, c.width);
    	    		g.fillRect(r.x, r.y, r.width, r.height);

    			}
    		}
    	}
    }
    
    public void initializeBoard() {
    	int rowCounter = 0;
    	int columnCounter = 0;
    	for(int row = 0; row < BOARD_WIDTH; row += POSITION_WIDTH) {
    		for(int col = 0; col < BOARD_WIDTH; col += POSITION_WIDTH) {
//    			g.drawRect(col, row, POSITION_WIDTH, POSITION_WIDTH);
    			Cell cell = new Cell(col, row, POSITION_WIDTH); //50);
    			if(rowCounter != 4 && rowCounter != 5) {
        			Rectangle r = new Rectangle();
        			r.x = cell.xVal + 5;
        			r.y = cell.yVal + 5;
        			r.width = PIECE_WIDTH;
        			r.height = PIECE_WIDTH;
        			Piece piece = new Piece(r);
        			if(rowCounter < 4) {
        				piece.setColor(redPlayer); 
        				piece.isRed = true;
        			} else {
        				piece.setColor(bluePlayer);
        				piece.isRed = false;
        			}
        			piece.setCell(cell);
        			cell.piece = piece;
        			pieces[rowCounter][columnCounter] = piece;
    			} else {
    				cell.piece = null;
    				if(columnCounter == 2 || columnCounter == 3 || columnCounter == 6 || columnCounter == 7) {
    					cell.water = true;
    				}
    			}
    			cell.rowIndex = rowCounter;
    			cell.colIndex = columnCounter;
    			board[rowCounter][columnCounter] = cell;
    			columnCounter++;
    		}
    		rowCounter++;
    		columnCounter = 0;
    	}
    }
    
    public void drawBoard(Graphics g) {

    	g.clearRect(0, 0, 601, 601);
    	
    	for(Cell[] cellRow : board) {
    		for(Cell cell : cellRow) {
    			// System.out.println("Row: " + cell.rowIndex + ", Col: " + cell.colIndex);
    			g.drawRect(cell.xVal, cell.yVal, cell.width, cell.width);
    		}
    	}
    	
    	g.setColor(water);
    	
    	// g.fillRect(61, 61, 59, 59); // fits a spot perfectly
    	
    	int waterSquareWidth = 2 * POSITION_WIDTH - 1;
    	int waterYStart = POSITION_WIDTH * 4 + 1;
    	int waterXStart1 = POSITION_WIDTH * 2 + 1;
    	int waterXStart2 = POSITION_WIDTH * 6 + 1;
    	
    	g.fillRect(waterXStart1, waterYStart, waterSquareWidth, waterSquareWidth);
    	g.fillRect(waterXStart2, waterYStart, waterSquareWidth, waterSquareWidth);
    	    	
    }
    
    public void mouseEntered( MouseEvent e ) {
        // called when the pointer enters the applet's rectangular area
     }
     public void mouseExited( MouseEvent e ) {
        // called when the pointer leaves the applet's rectangular area
    	// JOptionPane.showMessageDialog(QuickApplet.this, "Keep Playing!");
     }
     public void mouseClicked( MouseEvent e ) {
        // called after a press and release of a mouse button
        // with no motion in between
        // (If the user presses, drags, and then releases, there will be
        // no click event generated.)
     }
     public void mousePressed( MouseEvent e ) {  // called after a button is pressed down
    	// System.out.println("X: " + e.getX() + ", Y: " + e.getY());
        // "Consume" the event so it won't be processed in the
        // default manner by the source which generated it.
	   	Cell c = getCell(e.getX(), e.getY());
	   	Piece p = c.piece;
	   	if(p != null) {
	   		selectedPiece = p;
	   	}
        e.consume();
     }
     public void mouseReleased( MouseEvent e ) {  // called after a button is released
    	if(selectedPiece != null) {
	        Cell oldCell = selectedPiece.getCell();
	        Cell newCell = getCell(e.getX(), e.getY());
	        
	        movePiece(oldCell, newCell, selectedPiece);
	        
	        selectedPiece = null;
    	}
    	repaint();
    	e.consume();
     }
     public void mouseMoved( MouseEvent e ) {  // called during motion when no buttons are down
        int x = e.getX();
        int y = e.getY();
        showStatus( "Mouse Moving at (" + x + "," + y + ")" );
        e.consume();
     }
     
     public Piece selectedPiece = null;
     
     public void mouseDragged( MouseEvent e ) {  // called during motion with buttons down
        int x = e.getX();
        int y = e.getY();
        showStatus( "Mouse Dragging at (" + x + "," + y + ")" );
        
	   	Cell c = getCell(e.getX(), e.getY());
        
        if(selectedPiece != null) {
	   		Rectangle r = selectedPiece.getRect();
	   		if(r != null) {
	   			r.x = x - 25;
	   			r.y = y - 25;
		   		//r.setLocation(x+1, y+1);
		   		selectedPiece.setRect(r);
		   		// System.out.println("Drag - X: " + p.getRect().x + ", Y: " + p.getRect().y);
	   		} else {
	   			System.out.println("Rect is null!");
	   		}
        }
//        } else {
//	   		Piece p = pieces[c.rowIndex][c.colIndex];
//	   		if(p == null) {
//	   			System.out.println("Piece is null!");
//	   		}
//	   		Rectangle r = p.getRect();
//	   		if(r != null) {
//	   			r.x = x - 25;
//	   			r.y = y - 25;
//		   		//r.setLocation(x+1, y+1);
//		   		p.setRect(r);
//		   		System.out.println("Drag - X: " + p.getRect().x + ", Y: " + p.getRect().y);
//	   		} else {
//	   			System.out.println("Rect is null!");
//	   		}
//        }
        
        repaint(); // will I really have to repaint the entire applet?
        e.consume();
     }
    
     public Cell getCell(int x, int y) {
    	 int xIndex = x / 60;
    	 int yIndex = y / 60;
    	 return board[yIndex][xIndex];
     }
     
     public void updateCell(Cell updatedCell) {
    	 for(Cell[] cellRow : board) {
    		 for(Cell oldCell : cellRow) {
    			 if(oldCell.xVal == updatedCell.xVal && oldCell.yVal == updatedCell.yVal) {
    				 oldCell = updatedCell;
    			 }
    		 }
    	 }
     }
     
     public boolean validMove(Cell oldCell, Cell newCell) {
    	 if(newCell.piece != null || newCell.water == true) {
    		 return false;
    	 }
    	 int rowDiff = Math.abs(newCell.rowIndex - oldCell.rowIndex);
    	 int colDiff = Math.abs(newCell.colIndex - oldCell.colIndex);
    	 if(rowDiff + colDiff > 1) {
    		 return false;
    	 }
    	 return true;
     }
     
     public void movePiece(Cell oldCell, Cell newCell, Piece thePiece) {
    	 
    	 if(validMove(oldCell, newCell)) {
             oldCell.setPiece(null); // = null;
             selectedPiece.setCell(newCell);
    		 Rectangle r = new Rectangle();
    		 r.x = newCell.xVal + 5;
    		 r.y = newCell.yVal + 5;
    		 r.width = PIECE_WIDTH;
    		 r.height = PIECE_WIDTH;
    		 selectedPiece.setRect(r);
             newCell.setPiece(selectedPiece); // = selectedPiece;
             updateCell(oldCell);
             updateCell(newCell);
        	 player.move(board);
    	 } else {
    		 Rectangle r = new Rectangle();
    		 r.x = oldCell.xVal + 5;
    		 r.y = oldCell.yVal + 5;
    		 r.width = PIECE_WIDTH;
    		 r.height = PIECE_WIDTH;
    		 selectedPiece.setRect(r);
    		 oldCell.setPiece(selectedPiece);
    		 updateCell(oldCell);
    	 }
    	 
     }
     
}


