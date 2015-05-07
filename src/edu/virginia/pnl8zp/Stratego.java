package edu.virginia.pnl8zp;
/**
 * @author Philip Liberato
 *
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import edu.virginia.pnl8zp.Piece.PieceType;

class MenuActionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		
		String selection = e.getActionCommand();
		
		if(selection.equals("New Game")) {
			// start();
		}
		if(selection.equals("Log Data")) {
			Stratego.recordData();
		}
		if(selection.equals("Toggle Opponent View")) {
			//Stratego.recordData();
			Stratego.getAgent().togglePieceView();
		}
	}
}
 
public class Stratego extends JApplet implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L; // why do I need this?
	// private JButton  button;
    // private ArrayList<ArrayList<JButton>> board;
    private static int POSITION_WIDTH = 60; // i'll modify this when I determine the piece size
    private static final int PIECE_WIDTH = 50;
    private static int BOARD_WIDTH = 600; //i'll modify this when I determine the board size
    private static final int TRAY_START = 630;
    private static final int TRAY_END = 780;
    // private static final int MENU_OFFSET = 20;
    private static final Color grass = new Color(129, 214, 111);
    private static final Color water = new Color(64, 172, 245);
    private static final Color lightBrown = new Color(214, 186, 107);
	private static final Color bluePlayer = new Color(20, 67, 114);
	private static final Color redPlayer = new Color(227, 61, 64);
    public static Cell[][] board = new Cell[10][10];
    public static Cell[][] tray = new Cell[10][10];
    private static Piece[][] pieces = new Piece[10][10];
    public static Player player = null; //new Player();
    private static AI agent = null; // = new RandomAI();
	public static Battle b = new Battle();
	public static Frame frame;
	public static StrategoResources resources;
	public static JPanel panel;
	public static boolean gameOver = false;
	public static int endGameOption = 0;
	public static Graphics gPieces;
	public static String AI_TYPE = "RandomAI";
	public static String WINNER = "NULL";
	public static int TOTAL_MOVES = 0;
	public static int TOTAL_BATTLES = 0;
	public static HashMap<PieceType, Integer> HUMAN_REMAINING_PIECES = new HashMap<PieceType, Integer>();
	public static HashMap<PieceType, Integer> AI_REMAINING_PIECES = new HashMap<PieceType, Integer>();
	public static Graphics GRAPHICS = null;
	public static JPanel startOfGameDialog = null;
	public static String setupSelection = "Random";
	public static String aiSelection = "RandomAI";
	public static String playerSelection = "Manual";
	public static boolean playerTurn = false;
	public static boolean aiTurn = true;
	public static boolean playerHasMadeMove = false;
	public static TrainingDataInputs trainingData;
	public static int trainingEntry = 0;
	
    
    // this method acts like the applet constructor
    public void init() {
    	
    	trainingData = new TrainingDataInputs();

        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            frame.setMenuBar(null);
            frame.pack();
        }
        frame = (Frame)this.getParent().getParent();
        frame.setTitle("Stratego");
                
        // recordData();
        
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	setBackground(grass);
    	resources = new StrategoResources();
        System.out.println("APPLET IS INITIALIZED");
                
        JMenuBar menuBar = new JMenuBar();
        JMenu settings = new JMenu("Settings");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new MenuActionListener());
        settings.add(newGame);
        JMenuItem logData = new JMenuItem("Log Data");
        logData.addActionListener(new MenuActionListener());
        settings.add(logData);
        JMenuItem opponentView = new JMenuItem("Toggle Opponent View");
        opponentView.addActionListener(new MenuActionListener());
        settings.add(opponentView);
        menuBar.add(settings);
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
        frame.setVisible(true);
                
        panel = new JPanel() {
            /**
			 * No clue why I need the serial thing.
			 */
			private static final long serialVersionUID = 1L;
			@Override
            public Dimension getPreferredSize() {
            	return new Dimension(600, 600);
            }

            boolean firstTime = true;
            @Override
            protected void paintComponent(Graphics g) {
            	super.paintComponent(g);
            	if(firstTime) {
            		initializeBoard();
            		initializeTray();
            		firstTime = false;
            	}
        		drawBoard(g);
        		drawTray(g);
        		drawPieces(g);
            }
        };
        panel.setLocation(0, 20);
        panel.setVisible(true);
        add(panel);
    }

    class StartupDialogSetupListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		    	JRadioButton theButton = ((JRadioButton) e.getSource());
		    	String actionCommand = theButton.getActionCommand();
		    	Stratego.setupSelection = actionCommand;
		    }
		}
    }
    
    class StartupDialogAIListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		    	JRadioButton theButton = ((JRadioButton) e.getSource());
		    	String actionCommand = theButton.getActionCommand();
		    	Stratego.aiSelection = actionCommand;
		    }
		}
    }
    
    class StartupDialogPlayerListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {
		    if (e.getStateChange() == ItemEvent.SELECTED) {
		    	JRadioButton theButton = ((JRadioButton) e.getSource());
		    	String actionCommand = theButton.getActionCommand();
		    	Stratego.playerSelection = actionCommand;
		    }
		}
    }
    
    public JPanel startOfGameDialog() {
        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.PAGE_AXIS));
        
        JLabel setupText = new JLabel("Choose your starting board setup:");
        input.add(setupText);
        
        ItemListener setupChoiceListener = new StartupDialogSetupListener();
        ItemListener aiChoiceListener = new StartupDialogAIListener();
        ItemListener playerChoiceListener = new StartupDialogPlayerListener();

        
        ButtonGroup setupChoices = new ButtonGroup();
        
        JRadioButton random = new JRadioButton("Random");
        random.setSelected(true);
        random.setActionCommand("Random");
        random.addItemListener(setupChoiceListener);
        setupChoices.add(random);
        input.add(random);
        JRadioButton manual = new JRadioButton("Manual");
        manual.setEnabled(false);
        setupChoices.add(manual);
        manual.setActionCommand("Manual");
        manual.addItemListener(setupChoiceListener);
        input.add(manual);
        JRadioButton genetic = new JRadioButton("Genetic Algorithm");
        genetic.setActionCommand("Genetic");
        genetic.addItemListener(setupChoiceListener);
        setupChoices.add(genetic);
        input.add(genetic);
        
        
        JLabel pickAIText = new JLabel("Choose an AI to play against:");
        input.add(pickAIText);
        
        ButtonGroup aiChoices = new ButtonGroup();
        
        JRadioButton randomAI = new JRadioButton("RandomAI");
        randomAI.setSelected(true);
        randomAI.setActionCommand("RandomAI");
        randomAI.addItemListener(aiChoiceListener);
        aiChoices.add(randomAI);
        input.add(randomAI);
        JRadioButton valuedAI = new JRadioButton("ValuedAI");
        valuedAI.setActionCommand("ValuedAI");
        valuedAI.addItemListener(aiChoiceListener);
        aiChoices.add(valuedAI);
        input.add(valuedAI);        
        JRadioButton minimaxAI = new JRadioButton("MinimaxAI");
        minimaxAI.setActionCommand("MinimaxAI");
        minimaxAI.addItemListener(aiChoiceListener);
        aiChoices.add(minimaxAI);
        input.add(minimaxAI);
        
        JLabel pickPlayerText = new JLabel("How would you like to play:");
        input.add(pickPlayerText);
        
        ButtonGroup playerChoices = new ButtonGroup();
        
        JRadioButton manualPlayer = new JRadioButton("Manual");
        manualPlayer.setSelected(true);
        manualPlayer.setActionCommand("Manual");
        manualPlayer.addItemListener(playerChoiceListener);
        playerChoices.add(manualPlayer);
        input.add(manualPlayer);
        JRadioButton randomPlayer = new JRadioButton("Random (AI)");
        randomPlayer.setActionCommand("Random");
        randomPlayer.addItemListener(playerChoiceListener);
        playerChoices.add(randomPlayer);
        input.add(randomPlayer);
        JRadioButton valuedPlayer = new JRadioButton("Valued (AI)");
        valuedPlayer.setActionCommand("Valued");
        valuedPlayer.addItemListener(playerChoiceListener);
        playerChoices.add(valuedPlayer);
        input.add(valuedPlayer);
        JRadioButton minimaxPlayer = new JRadioButton("Minimax (AI)");
        minimaxPlayer.setActionCommand("Minimax");
        minimaxPlayer.addItemListener(playerChoiceListener);
        playerChoices.add(minimaxPlayer);
        input.add(minimaxPlayer);
                
        return input;
    }
    
    public AI aiSelectionInstance() {
    	if(aiSelection.equals("RandomAI")) {
    		return new RandomAI();
    	} else if(aiSelection.equals("ValuedAI")) {
    		return new ValuedAI();
    	} else if(aiSelection.equals("MinimaxAI")) {
    		return new MinimaxAI();
    	} else {
    		System.out.println("Problem in aiSelectionInstance() ... I shouldn't be here");
    		return new RandomAI();
    	}
    }
    
    public Player playerSelectionInstance() {
    	
    	Player playerInstance = new Player();
    	
    	if(playerSelection.equals("Random")) {
    		playerInstance.setAI_Instance(new RandomAI());
    	} else if(playerSelection.equals("Valued")) {
    		playerInstance.setAI_Instance(new ValuedAI());
    	} else if(playerSelection.equals("Minimax")) {
    		playerInstance.setAI_Instance(new MinimaxAI());
    	}
    	// playerInstance.getAI_Instance().isAIRed(false);
    	return playerInstance;
    }
    
    boolean notFirst = false;
    @SuppressWarnings("unchecked")
	public void start() {
        System.out.println("APPLET IS STARTED");
                
        startOfGameDialog = startOfGameDialog();
        JOptionPane.showMessageDialog(frame, startOfGameDialog, "New Game", JOptionPane.PLAIN_MESSAGE);
        
        System.out.println("Setup Selection: " + setupSelection);
        System.out.println("AI Selection: " + aiSelection);
        System.out.println("Player Selection: " + playerSelection);
        
        agent = aiSelectionInstance();
        agent.isAIRed(true);
        player = playerSelectionInstance();
        
        player.addListener(getAgent());
        
        if(player.isAI) {
        	player.getAI_Instance().isAIRed(false);
        }
        
        if(notFirst) {
            gameOver = false;
            // agent = new RandomAI();
            // player = new Player();
            // player.addListener(getAgent());
            deadRedPieces = new ArrayList<Image>();
            deadBluePieces = new ArrayList<Image>();
            AI_REMAINING_PIECES = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
            HUMAN_REMAINING_PIECES = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
    		initializeBoard();
    		initializeTray();
    		repaint();
        } else {
        	// agent = new RandomAI();
            // player.addListener(getAgent());
        	AI_REMAINING_PIECES = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
        	HUMAN_REMAINING_PIECES = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
        }
        notFirst = true;
        
        javax.swing.Timer paintTimer = new javax.swing.Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Graphics gr = getGraphics();      
                Stratego.panel.repaint();
                if(gameOver != true) {
                    if(Stratego.playerTurn) {
                    	if(Stratego.getPlayer().isAI) {
                        	Stratego.getPlayer().makeMoveAsAI();
                        	Stratego.playerTurn = false;
                        	Stratego.aiTurn = true;
                    	} else if(Stratego.playerHasMadeMove) {
                        	Stratego.playerTurn = false;
                        	Stratego.playerHasMadeMove = false;
                        	Stratego.aiTurn = true;
                    	} else {
                    		// keep on waiting
                    	}

                    } else if(Stratego.aiTurn) {
                        //Stratego.getAgent().makeRandomMove(Stratego.getAgent().getPieces(), "AI");
                        Stratego.getAgent().makeMove(Stratego.getAgent().getPieces());
                        Stratego.aiTurn = false;
                        Stratego.playerTurn = true;
                    } else {
                    	
                    }
                } else {
                	// call game over?
                }
            }
        });
        paintTimer.setRepeats(true);
        paintTimer.setCoalesce(true);
        paintTimer.start();
    }
 
    public void stop() {
        System.out.println("APPLET IS STOPPED");
    }
 
    public void destroy() {
        System.out.println("APPLET IS DESTROYED");
        System.exit(0);
    }
    // Boolean firstTime = true;
    public void paint(Graphics g) {
    	GRAPHICS = g;
    	super.paint(g);
    	panel.paintComponents(g);
    }
    
    public static void drawPieces(Graphics g) {
    					
//    	for(int row = 0; row < 10; row++) {
//    		for(int col = 0; col < 10; col++) {
//    			// Cell c = board[row][col];
//    			Piece p = board[row][col].piece;
//    			if(p != null) {
//    				if(g.getColor() != p.getColor()) {
//    					g.setColor(p.getColor());
//    				}
//    				Rectangle r = p.getRect();
//    	    		// g.fillRect(c.xVal + 5, c.yVal + 5, c.width, c.width);
//    	    		// g.fillRect(r.x, r.y, r.width, r.height);
//    	    		g.setColor(Color.white);
//    	    		int stringWidth = g.getFontMetrics().stringWidth("P");
//    	    		int wOffset = (r.width - stringWidth) / 2;
//    	    		int hOffset = r.height / 2;
//    	    		// g.drawString(p.pieceTypeToString(), r.x + wOffset, r.y + hOffset + 3);
//    	    		if(p.getImage() != null) {
//    	    			if(mousePressed) {
//    	    				g.drawImage(p.getImage(), p.getRect().x + 1, p.getRect().y + 1, null);
//    	    			} else {
//    	    				g.drawImage(p.getImage(), p.getCell().xVal + 1, p.getCell().yVal + 1, null);
//    	    			}
//    	    		}
//    			}
//    		}
//    	}
    	
    	for(Piece p : agent.getPieces()) {
    		Image image = null;
    		if(agent.getArePieceValuesVisible()) {
    			image = p.getImage();
    		} else {
    			image = StrategoResources.red_piece;
    		}
			if(mousePressed) {
				g.drawImage(image, p.getRect().x + 1, p.getRect().y + 1, null);
			} else {
				g.drawImage(image, p.getCell().xVal + 1, p.getCell().yVal + 1, null);
			}
    	}
    	
    	for(Piece p : Player.pieces) {
			if(mousePressed) {
				g.drawImage(p.getImage(), p.getRect().x + 1, p.getRect().y + 1, null);
			} else {
				g.drawImage(p.getImage(), p.getCell().xVal + 1, p.getCell().yVal + 1, null);
			}
    	}
    	
    }
    
    public void initializeTray() {
    	// g.paint();
    }
    public static ArrayList<Image> deadRedPieces = new ArrayList<Image>();
    public static ArrayList<Image> deadBluePieces = new ArrayList<Image>();
    public void drawTray(Graphics g) {
    	
    	g.setColor(new Color(223, 223, 223));
    	
    	g.fillRect(BOARD_WIDTH, 0, 30 + 150, 601);
    	
    	g.setColor(lightBrown);
    	g.fillRect(TRAY_START - 15, 0, 150, 240);
    	g.fillRect(TRAY_START - 15, 361, 150, 240);
    	
    	g.setColor(Color.black);
    	
    	int trayCounterRed = 0;
    	int trayCounterBlue = 0;
    	int numDeadRed = deadRedPieces.size();
    	int numDeadBlue = deadBluePieces.size();
    	
    	for(int row = 0; row < BOARD_WIDTH; row += 30) {
    		for(int col = TRAY_START - 15; col < TRAY_END - 15; col += 30) {
    			if(row < 8 * 30 || row > 11 * 30) {
        			g.drawRect(col, row, 30, 30);
    			}
    			if(trayCounterRed < numDeadRed && row < 8 * 30) {
                	g.drawImage(deadRedPieces.get(trayCounterRed), col, row, null);
                	trayCounterRed++;
    			}
       			if(trayCounterBlue < numDeadBlue && row > 11 * 30) {
                	g.drawImage(deadBluePieces.get(trayCounterBlue), col, row, null);
                	trayCounterBlue++;
    			}
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public void initializeBoard() {
    	Player.setupOptions = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
    	HashMap<PieceType, Integer> playerSetupOptions = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
    	HashMap<PieceType, Integer> aiSetupOptions = (HashMap<PieceType, Integer>) RandomAI.setupOptions.clone();
    	int rowCounter = 0;
    	int columnCounter = 0;
    	for(int row = 0; row < BOARD_WIDTH; row += POSITION_WIDTH) {
    		for(int col = 0; col < BOARD_WIDTH; col += POSITION_WIDTH) {
    			Cell cell = new Cell(col, row, POSITION_WIDTH);
    			Piece piece = null;
    			if(rowCounter != 4 && rowCounter != 5) {
        			Rectangle r = new Rectangle();
        			r.x = cell.xVal; // + 5;
        			r.y = cell.yVal; // + 5;
        			r.width = PIECE_WIDTH;
        			r.height = PIECE_WIDTH;
        			piece = new Piece(r);
        			if(rowCounter < 4) {
        				piece.isRed = true;
        				piece.setColor(redPlayer); 
        				piece.setOwner("AI");
        				if(Stratego.setupSelection.equals("Random")) {
            				getAgent().assignPieceType(piece);
        				}
        				if(Stratego.setupSelection.equals("Genetic")) {
        					aiSetupOptions = StrategoResources.assignPieceGeneticAlgorithm(aiSetupOptions, piece, "AI");
        				}
        				getAgent().getPieces().add(piece);
        			} else {
        				piece.setColor(bluePlayer);
        				piece.setOwner("PLAYER");
        				piece.isRed = false;
        				// temp addition follows:
        				if(Stratego.setupSelection.equals("Random")) {
            				player.randomBoard(piece);
        				}
        				if(Stratego.setupSelection.equals("Genetic")) {
        					playerSetupOptions = StrategoResources.assignPieceGeneticAlgorithm(playerSetupOptions, piece, "PLAYER");
        				}
        				Player.pieces.add(piece);
        				// piece.setpType(PieceType.FLAG);
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
    	// player.setAI_Instance(new RandomAI());
    }
    
    public void drawBoard(Graphics g) {

    	// g.clearRect(0, 0, 601, 601);
    	g.clearRect(0, 0, BOARD_WIDTH, BOARD_WIDTH);
    	
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
    	 if(e.getX() <= BOARD_WIDTH && e.getY() <= BOARD_WIDTH && Stratego.getPlayer().isAI == false) {
    		 Cell c = getCell(e.getX(), e.getY());
    		 Piece p = c.piece;
		   	 if(p != null && p.getOwner().equals("PLAYER")) {
		   		 String checkStationary = p.pieceTypeToString();
		   		 if(!checkStationary.equals("F") && !checkStationary.equals("B")) {
			   		 selectedPiece = p;
			   		 mousePressed = true;
		   		 }
		   	}
    	  }
         e.consume();
     }
     public void mouseReleased( MouseEvent e ) {  // called after a button is released
    	if(selectedPiece != null) {
    		if(e.getX() <= BOARD_WIDTH && e.getY() <= BOARD_WIDTH) {
    	        Cell oldCell = selectedPiece.getCell();
    	        Cell newCell = getCell(e.getX(), e.getY());	        
    	        movePiece(oldCell, newCell, selectedPiece);
    		}
	        
	        selectedPiece = null;
	        mousePressed = false;
    	}
    	repaint();
    	e.consume();
     }
     public void mouseMoved( MouseEvent e ) {  // called during motion when no buttons are down
    	if(gameOver) {
    		gameOver();
    	}
        int x = e.getX();
        int y = e.getY();
        showStatus( "Mouse Moving at (" + x + "," + y + ")" );
        e.consume();
        repaint();
     }
     
     public Piece selectedPiece = null;
     public static boolean mousePressed = false;
     
     public void mouseDragged( MouseEvent e ) {  // called during motion with buttons down
        int x = e.getX();
        int y = e.getY();
        showStatus( "Mouse Dragging at (" + x + "," + y + ")" );
        
	   	// Cell c = getCell(e.getX(), e.getY());
        
        if(selectedPiece != null) {
	   		Rectangle r = selectedPiece.getRect();
	   		if(r != null) {
	   			r.x = x - 25;
	   			r.y = y - 45;
		   		//r.setLocation(x+1, y+1);
		   		selectedPiece.setRect(r);
		   		// System.out.println("Drag - X: " + p.getRect().x + ", Y: " + p.getRect().y);
	   		} else {
	   			System.out.println("Rect is null!");
	   		}
        }
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
    	 if(newCell.water == true) {
    		 return false;
    	 }
    	 int rowDiff = Math.abs(newCell.rowIndex - oldCell.rowIndex);
    	 int colDiff = Math.abs(newCell.colIndex - oldCell.colIndex);
    	 if(rowDiff + colDiff > 1) {
    		 return false;
    	 }
    	 if(newCell.piece != null && newCell.piece.oType.equals("PLAYER")) {
    		 return false;
    	 }
    	 return true;
     }
 
     public boolean isBattle(Cell newCell) {
    	 if(newCell.piece != null && newCell.piece.oType.equals("AI")) {
    		 return true;
    	 }
    	 return false;
     }
     
     public void movePiece(Cell oldCell, Cell newCell, Piece thePiece) {
    	 
    	 if(Move.validMove(oldCell, newCell)) {
    		 Stratego.TOTAL_MOVES++;
             oldCell.setPiece(null); // = null;
             selectedPiece.setCell(newCell);
    		 Rectangle r = new Rectangle();
    		 r.x = newCell.xVal; // + 5;
    		 r.y = newCell.yVal; // + 5;
    		 r.width = PIECE_WIDTH;
    		 r.height = PIECE_WIDTH;
    		 selectedPiece.setRect(r);
             newCell.setPiece(selectedPiece); // = selectedPiece;
             updateCell(oldCell);
             if(isBattle(newCell)) {
            	 // repaint();
//            	 Battle.battle(selectedPiece, newCell.piece);
//               	 JOptionPane.showMessageDialog(null, "ATTACK!!!!!!");
            	 // Thread.join(b.getId());
             }
             updateCell(newCell);
        	 //player.move();
        	 if(player.isAI == false) {
        		 Stratego.playerHasMadeMove = true;
        	 }
    	 } else {
    		 Rectangle r = new Rectangle();
    		 r.x = oldCell.xVal; // + 5;
    		 r.y = oldCell.yVal; // + 5;
    		 r.width = PIECE_WIDTH;
    		 r.height = PIECE_WIDTH;
    		 selectedPiece.setRect(r);
    		 oldCell.setPiece(selectedPiece);
    		 updateCell(oldCell);
    	 }
    	 
     }
     
     public static String getHumanRemainingPieces() {
    	 return compressRemainingPiecesStruct(HUMAN_REMAINING_PIECES);
     }
     
     public static String getAIRemainingPieces() {
    	 return compressRemainingPiecesStruct(AI_REMAINING_PIECES);
     }
     
     public static String compressRemainingPiecesStruct(HashMap<PieceType, Integer> data) {
    	 String logFormat = "[";
    	 logFormat += data.get(PieceType.FLAG) + ":" + data.get(PieceType.BOMB) + ":" + data.get(PieceType.SPY) + ":";
    	 logFormat += data.get(PieceType.SCOUT) + ":" + data.get(PieceType.MINER) + ":" + data.get(PieceType.SERGEANT) + ":";
    	 logFormat += data.get(PieceType.LIEUTENANT) + ":" + data.get(PieceType.CAPTAIN) + ":" + data.get(PieceType.MAJOR) + ":";
    	 logFormat += data.get(PieceType.COLONEL) + ":" + data.get(PieceType.GENERAL) + ":" + data.get(PieceType.MARSHAL);
    	 logFormat += "]";
    	 return logFormat;
     }

	public static AI getAgent() {
		return agent;
	}
	
	public static Player getPlayer() {
		return player;
	}

	public static void setAgent(AI agent) {
		Stratego.agent = agent;
	}
	
	public void gameOver() {
		// New Game, Record Data, Exit
		switch(endGameOption) {
			case 0:
				start();
				break;
			case 1:
				recordData();
				break;
			case 2:
				destroy();
				break;
			default:
				break;
		}
	}
	
	public static String cwdPath = "/home/philip/Development/UVA/stratego_ai";
	public static String filename = "minimaxVrandom-old.txt"; // "logger.txt";
	public static void recordData() {
		System.out.println("Recording Data");
		
		// AI_TYPE::PLAYER_TYPE::WINNER::TOTAL_MOVES::TOTAL_BATTLES::HUMAN_REMAINING_PIECES[F:B:S:2:3:4:5:6:7:8:9:10]::AI_REMAINING_PIECES[F:B:S:2:3:4:5:6:7:8:9:10]::TIME_STAMP
		
		String results = "";
		results += agent.stringIdentifier() + "::" + player.stringIdentifier() + "::" + WINNER + "::" + TOTAL_MOVES + "::" + TOTAL_BATTLES + "::HUMAN_REMAINING_PIECES" 
		        + getHumanRemainingPieces() + "::AI_REMAINING_PIECES" + getAIRemainingPieces() + "::" 
				+ (new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		
		int element = Stratego.trainingEntry;
		int classification = 0;
		if(Stratego.WINNER.equals("AI")) {
			classification = 1;
		}
//		results += trainingData.trainingDataInputs[element][0] + " " + trainingData.trainingDataInputs[element][1] + " "
//				+ trainingData.trainingDataInputs[element][2] + " " + trainingData.trainingDataInputs[element][3] + " "
//				+ trainingData.trainingDataInputs[element][4] + " " + trainingData.trainingDataInputs[element][5] + " "
//				+ classification;
		
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(cwdPath+"/"+filename, true)))) {
		    out.println(results);
		}catch (IOException e) {
			e.printStackTrace();
		}

		System.exit(0);
		// start();
		
	}
     
}


