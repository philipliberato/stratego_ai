package edu.virginia.pnl8zp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import edu.virginia.pnl8zp.Piece.PieceType;

/*
 * Code to implement:
 * 	- moveMade()
 * 	- makeMinimaxMove()
 */

public class MinimaxAI extends AI {

	public ArrayList<Piece> pieces;
	public static HashMap<PieceType, Integer> setupOptions;
	public static boolean visiblePieceValues = true;
	public ArrayList<Piece> knownOpponentPieces;
	public boolean isAIRed;
	public Cell lastPieceLocation = null;
	public HashMap<BoardNode, ArrayList<BoardNode>> decisionTree;
	public long minimaxAlphaBetaStartTime;
	
	@Override
	public String stringIdentifier() {
		return "MinimaxAI";
	}
	
	public void isAIRed(boolean val) {
		isAIRed = val;
	}
	
	@Override
	public void togglePieceView() {
		visiblePieceValues = !visiblePieceValues;
	}
	
	@Override
	public boolean getArePieceValuesVisible() {
		return visiblePieceValues;
	}
	
	public MinimaxAI() {
		pieces = new ArrayList<Piece>();
		knownOpponentPieces = new ArrayList<Piece>();
		setupOptions = new HashMap<PieceType, Integer>();
		loadSetupOptions();	
	}
	
	public void loadSetupOptions() {
		setupOptions.put(PieceType.FLAG, 1);
		setupOptions.put(PieceType.SPY, 1);
		setupOptions.put(PieceType.GENERAL, 1);
		setupOptions.put(PieceType.MARSHAL, 1);
		setupOptions.put(PieceType.COLONEL, 2);
		setupOptions.put(PieceType.MAJOR, 3);
		setupOptions.put(PieceType.SERGEANT, 4);
		setupOptions.put(PieceType.LIEUTENANT, 4);
		setupOptions.put(PieceType.CAPTAIN, 4);
		setupOptions.put(PieceType.MINER, 5);
		setupOptions.put(PieceType.BOMB, 6);
		setupOptions.put(PieceType.SCOUT, 8);

	}
	
	@Override
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	@Override
	public void setPieces(ArrayList<Piece> thePieces) {
		pieces = thePieces;
	}

	@Override
	public void moveMade() {
		// implement
		makeMinimaxMove(pieces, "AI");
	}

	@Override
	public boolean removePiece(Piece beatenPiece) {
		for(Piece p : pieces) {
			if(p.getCell().equals(beatenPiece.getCell())) {
				pieces.remove(p);
				return true;
			}
		}	
		return false;
	}

	public PieceType assignment;
	@Override
	public void assignPieceType(Piece p) {

		int size = setupOptions.keySet().size();
		int item = new Random().nextInt(size);
		int i = 0;
		
		for(Object obj : setupOptions.keySet()) {
		    if (i == item) {
		    	assignment = (PieceType) obj;
		    	setupOptions.put(assignment, setupOptions.get(assignment) - 1);
		    	p.setpType(assignment);
		    	break;
		    }
		    i++;
		}
		if(setupOptions.get(assignment) == 0) {
			setupOptions.remove(assignment);
		}
	}

	@Override
	public void makeRandomMove(ArrayList<Piece> availablePieces, String oType) {
		// only implemented in the RandomAI class
		// perhaps I could just make a call to makeValuedMove here, in the event
		// that something buggy/weird happens...	
	}
	
	@Override
	public void addDicoveredOpponentPiece(Piece discoveredPiece) {
		// implement this
	}

	@Override
	public void removeDiscoveredOpponentPiece(Piece discoveredPiece) {
		if(knownOpponentPieces.contains(discoveredPiece)) {
			knownOpponentPieces.remove(discoveredPiece);
		}
	}
	
	@Override
	public void makeValuedMove(ArrayList<Piece> availablePieces, String oType) {
		// only implemented in the ValuedAI class
		// perhaps I could just make a call to makeRandomMove here, in the event
		// that something buggy/weird happens...
	}
	
	public ArrayList<Piece> getYourPieces() {
		ArrayList<Piece> yourPieces = new ArrayList<Piece>();
		// get your pieces
    	for(Cell[] c1 : Stratego.board) {
    		for(Cell c : c1) {
    			if(c.piece != null) {
    				Piece p = c.piece;
    				if(p.isRed == isAIRed) {
    					yourPieces.add(p);
    				}
    			}
    		}
    	}
    	return yourPieces;
	}
	
	public ArrayList<MovementVector> getPossibleMoves(ArrayList<Piece> yourPieces) {
		ArrayList<MovementVector> availableMoves = new ArrayList<MovementVector>();
    	// get all possible moves
    	for(Piece p : yourPieces) {
    		int rowIndex = p.getCell().rowIndex;
    		int colIndex = p.getCell().colIndex;
    		if(p.getpType() != PieceType.BOMB && p.getpType() != PieceType.FLAG) {
        		for(int i = -1; i <= 1; i++) {
        			for(int j = -1; j <= 1; j++) {
        				if(Math.abs(i) != Math.abs(j)) {
        					int row = rowIndex + i;
        					int col = colIndex + j;
        					if((0 <= row && row <= 9) && (0 <= col && col <= 9)) {   	
        						int isMovableResult = Stratego.board[row][col].isMovable(isAIRed);
            		    		if(isMovableResult != 0) {
            		    			availableMoves.add(new MovementVector(isMovableResult, p, Stratego.board[row][col], lastPieceLocation));
            		    		}
        					}
        				}
        			}
        		}
    		}
    	}
    	return availableMoves;
	}

	@Override
	public void makeMinimaxMove(ArrayList<Piece> availablePieces, String oType) {
    	
//    	System.out.println("Number of possible moves: " + availableMoves.size());
//    	for(MovementVector mv : availableMoves) {
//    		mv.printMovementVector();
//    	}
//    	System.out.println("--------");

//    	PriorityQueue<MovementVector> prioritizeMoves = new PriorityQueue<MovementVector>(availableMoves.size(), new MovementVectorComparator());
//    	for(MovementVector mv : availableMoves) {
//    		prioritizeMoves.add(mv);
//    	}
//    	MovementVector best = prioritizeMoves.poll();
//    	best.printMovementVector();
//    	
//    	MovementVector a = null;
//    	for(MovementVector mv : availableMoves) {
//    		if(a == null) {
//    			a = mv;
//    		} else {
//    	   		if(mv.costFunction() > a.costFunction()) {
//        			a = mv;
//        		}
//    		}
//    	}
    	// System.out.println("Number of pieces: " + yourPieces.size());
    	ArrayList<Piece> yourPieces = getYourPieces();
    	ArrayList<MovementVector> availableMoves = getPossibleMoves(yourPieces);
    	BoardNode decisionTreeRoot = buildDecisionTree(availableMoves, 4);
    	
//    	BoardNode currentNode = decisionTreeRoot;
    	System.out.println("Total Nodes: " + countNodes(decisionTreeRoot)); 	
    	
    	bestBoard = decisionTreeRoot.children.get(0);
    	best = null;
    	
    	int depth = 2;
    	int searchDepth = 4;
    	long timeConstraint = 3000;
    	double bestValue = 0;
    	minimaxAlphaBetaStartTime = System.currentTimeMillis();
    	while(depth <= searchDepth) { // && System.currentTimeMillis() - minimaxAlphaBetaStartTime < timeConstraint) {
            double result = alpha_beta(decisionTreeRoot, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            if (result >= bestValue) {
                bestValue = result;
                bestBoard = best;
            }
            depth++;
        }
    	
    	
    	// alpha_beta(decisionTreeRoot, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

    	while(bestBoard.parent.movementVector != null) {
    		bestBoard = bestBoard.parent;
    	}
    	MovementVector bestMove = bestBoard.movementVector;
    	MinimaxCell oldSpot = bestMove.selectedPiece;
    	MinimaxCell newSpot = bestMove.selectedPieceDestination;
    	System.out.println("Chosen Node: r" + oldSpot.rowIndex + " c" + oldSpot.colIndex
    			+ " --> r" + newSpot.rowIndex + " c" + newSpot.colIndex);

    	    	
		Move.movePiece(Stratego.board[oldSpot.rowIndex][oldSpot.colIndex], 
				Stratego.board[newSpot.rowIndex][newSpot.colIndex]);
    	
	}
	
	public int countNodes(BoardNode node) {
		int total = 0;
		if(node.isLeaf()) {
			return 1;
		} else {
			for(BoardNode child : node.children) {
				total += 1 + countNodes(child);
			}
		}
		return total;
	}
	
	public BoardNode buildDecisionTree(ArrayList<MovementVector> availableMoves, int depthLimit) {
		decisionTree = new HashMap<BoardNode, ArrayList<BoardNode>>();
		MinimaxCell[][] rootBoardState = generateRootBoardState();
		// new MovementVector(0, rootBoardState[0][0], rootBoardState[0][0], null)
    	BoardNode root = new BoardNode(rootBoardState, null, null, 0, depthLimit, isAIRed);
    	//root.setBoard(rootBoardState);
    	decisionTree.put(root, null);
		addChildren(root, 1, depthLimit);
		return root;
	}
	public BoardNode bestBoard;
	public BoardNode best;
//	public double minimax(BoardNode node, int depth, boolean maximizingPlayer) {
//		if(depth == 0 || node.isLeaf()) {
//			// best = node;
//			return node.movementVector.costFunction(node);
//		}
//		if(maximizingPlayer) {
//			double bestValue = Integer.MIN_VALUE;
//			for(BoardNode child : node.children) {
//				double utility = minimax(child, depth - 1, false);
//				if(utility > bestValue) {
//					bestValue = utility;
//					best = child;
//				}
//			}
//			//best = node;
//			return bestValue;
//		} else {
//			double bestValue = Integer.MAX_VALUE;
//			for(BoardNode child : node.children) {
//				double utility = minimax(child, depth - 1, true);
//				bestValue = Math.min(bestValue, utility);
//			}
//			// best = node;
//			return bestValue;
//		}
//	}
	
	public double alpha_beta(BoardNode node, int depth, double alpha, double beta, boolean maximizingPlayer) {
		if(depth == 0 || node.isLeaf()) { // || System.currentTimeMillis() - minimaxAlphaBetaStartTime > 3000) {
			return node.boardStateUtility;
		}
		if(maximizingPlayer) {
			double val = Integer.MIN_VALUE;
			for(BoardNode child : node.children) {
				val = Math.max(val, alpha_beta(child, depth - 1, alpha, beta, false));
				alpha = Math.max(alpha, val);
				if(beta <= alpha) {
					best = node;
					break;
				}
			}
			return val;
		} else {
			double val = Integer.MAX_VALUE;
			for(BoardNode child : node.children) {
				val = Math.min(val, alpha_beta(child, depth - 1, alpha, beta, true));
				beta = Math.min(beta, val);
				if(beta <= alpha) {
					best = node;
					break;
				}
			}
			return val;
		}
	}
	
	public void addChildren(BoardNode parent, int depth, int depthLimit) {
		if(depth <= depthLimit) {
			ArrayList<BoardNode> children = new ArrayList<BoardNode>();
			// System.out.println(depth);
			ArrayList<MovementVector> moves = parent.getAvailableMoves();
			// System.out.println("size: " + moves.size());
			for(MovementVector mv : moves) {
				BoardNode availableBoardState = new BoardNode(parent.getBoard(), parent, mv, depth, depthLimit, isAIRed);
				if(children.size() > 10) {
					for(int i = 0; i < 10; i++) {
						if(mv.costFunction(availableBoardState) > children.get(i).movementVector.costFunction(children.get(i))) {
							children.set(i, availableBoardState);
							break;
						}
					}
				} else {
					children.add(availableBoardState);
				}
			}
			for(BoardNode child : children) {
				parent.addChild(child);
				addChildren(child, depth + 1, depthLimit);
			}
		}
	}
	
	public MinimaxCell[][] generateRootBoardState() {
		MinimaxCell[][] rootBoardState = new MinimaxCell[10][10];
		for(Cell[] cRow : Stratego.board) {
			for(Cell c : cRow) {
				MinimaxCell mCell = new MinimaxCell();
				mCell.setCellLocation(c.rowIndex, c.colIndex, c.water);
				
				Piece p = c.getPiece();
				if(p != null) {
					mCell.setPieceBasics(true, p.isRed, p.knownByOpponent);
					mCell.setPieceFundamentals(p.getpType(), p.probability);
					mCell.hasMoven = p.hasMoved;
				} else {
					mCell.setPieceBasics(false, false, false);
					mCell.setPieceFundamentals(null, null);
				}

				rootBoardState[mCell.rowIndex][mCell.colIndex] = mCell;
			}
		}
		return rootBoardState;
	}
	
//	public double maxValue(double alpha, double beta, int depth) { // returns a utility value
//		return 2.0;
//	}
//	
////	public int currDepthLimit = 4;
//
//	public double minValue(double alpha, double beta, int depth) { // returns a utility
//		return 2.0;
//	}

}
