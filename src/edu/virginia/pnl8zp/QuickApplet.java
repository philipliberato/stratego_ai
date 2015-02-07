package edu.virginia.pnl8zp;
/**
 * @author Philip Liberato
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
 
public class QuickApplet extends JApplet implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L; // why do I need this?
	// private JButton  button;
    // private ArrayList<ArrayList<JButton>> board;
    private static final int POSITION_WIDTH = 60; // i'll modify this when I determine the piece size
    private static final int BOARD_WIDTH = 600; //i'll modify this when I determine the board size
    private static final Color grass = new Color(129, 214, 111);
    private static final Color water = new Color(64, 172, 245);
    
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
    
    public void paint(Graphics g) {
    	drawBoard(g);
    }
    
    public void drawBoard(Graphics g) {
    	for(int row = 0; row < BOARD_WIDTH; row += POSITION_WIDTH) {
    		for(int col = 0; col < BOARD_WIDTH; col += POSITION_WIDTH) {
    			g.drawRect(col, row, POSITION_WIDTH, POSITION_WIDTH);
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
    	System.out.println("X: " + e.getX() + ", Y: " + e.getY());
        // "Consume" the event so it won't be processed in the
        // default manner by the source which generated it.
        e.consume();
     }
     public void mouseReleased( MouseEvent e ) {  // called after a button is released
        e.consume();
     }
     public void mouseMoved( MouseEvent e ) {  // called during motion when no buttons are down
        int x = e.getX();
        int y = e.getY();
        showStatus( "Mouse Moving at (" + x + "," + y + ")" );
        e.consume();
     }
     public void mouseDragged( MouseEvent e ) {  // called during motion with buttons down
        int x = e.getX();
        int y = e.getY();
        showStatus( "Mouse Dragging at (" + x + "," + y + ")" );
        repaint(); // will I really have to repaint the entire applet?
        e.consume();
     }
    
}


