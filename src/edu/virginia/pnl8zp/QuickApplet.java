package edu.virginia.pnl8zp;
/**
 * @author Philip Liberato
 *
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
 
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
 
public class QuickApplet extends JApplet {

	private static final long serialVersionUID = 1L; // why do I need this?
	private JButton  button;
    private ArrayList<ArrayList<JButton>> board;
 
    public void init() {
        button = new JButton("Click me!");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(QuickApplet.this,
                    "Hello! I am an applet!");
            }
        });
 
        System.out.println("APPLET IS INITIALIZED");
    }
 
    public void start() {
        getContentPane().add(button);
        System.out.println("APPLET IS STARTED");
    }
 
    public void stop() {
        System.out.println("APPLET IS STOPPED");
    }
 
    public void destroy() {
        System.out.println("APPLET IS DESTROYED");
    }
}