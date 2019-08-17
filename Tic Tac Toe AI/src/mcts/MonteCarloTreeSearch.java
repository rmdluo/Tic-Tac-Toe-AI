package mcts;

import java.util.List;

import tictactoe.Board;
import tree.Node;
import tree.Tree;

public class MonteCarloTreeSearch {
	private int level;
	private int opponent;

	public MonteCarloTreeSearch() {
		this.level = 3;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	private int getMillisForCurrentLevel() {
		return 2 * (this.level - 1) + 1;
	}

	/**
	 * finds the next move
	 * @param board - the current board
	 * @param player - who's turn it is (player 1 or player 2)
	 * @return the board after the move
	 */
	public Board findNextMove(Board board, int player) {
		//sets up time constraints
		long start = System.currentTimeMillis();
		long end = start + 60 * getMillisForCurrentLevel();
		
		//sets up the tree / game
		opponent = 3 - player;
		Tree tree = new Tree();
		Node root = tree.getRoot();
		root.getState().setBoard(board);
        root.getState().setPlayer(opponent);
        
        //finds the best move
        while(System.currentTimeMillis() < end) {
        	//finds a promising leaf node
        	Node promisingNode = selectPromisingNode(root);
        	
        	//expands the node
        	if(promisingNode.getState().getBoard().checkStatus() == -1)
        		expandNode(promisingNode);
        	
        	//get a random child of the promising node
        	Node nodeToExplore = promisingNode;
        	if(promisingNode.getChildren().size() > 0)
        		nodeToExplore = promisingNode.getRandomChildNode();

        	//simulate a random game from the child
        	int playoutResult = simulateRandomPlayout(nodeToExplore);

        	//update the values of the nodes
        	backPropogation(nodeToExplore, playoutResult);
        }
        
        //gets the best move
        Node winnerNode = root.getChildWithMaxScore();
        tree.setRoot(root);
        return winnerNode.getState().getBoard();
	}
	
	/**
	 * gets the node that is a likely good choice
	 * @param root - the top most node
	 * @return the node that is likely to be good
	 */
	private Node selectPromisingNode(Node root) {
		Node node = root;
		while(node.getChildren().size() > 0) {
			node = UCT.findBestNodeWithUCT(node);
		}
		return node;
	}
	
	/**
	 * adds children to a node based on possible moves
	 * @param node - the node to add children to
	 */
	private void expandNode(Node node) {
		//gets the possible moves/states
		List<State> possibleStates = node.getState().getAllPossibleStates();
		
		//adds a child for each state
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            newNode.getState().setPlayer(node.getState().getOpponent());
            node.getChildren().add(newNode);
        });
	}
	
	/**
	 * simulates a random game
	 * @param node - the node to start play at
	 * @return which player won the random simulation
	 */
	private int simulateRandomPlayout(Node node) {
		//set up the game
		Node tempNode = new Node(node);
		State tempState = tempNode.getState();
        int boardStatus = tempState.getBoard().checkStatus();
        
        //if the move makes the opponent win, set the win score for the move to the minimum so it doesn't get picked
        if (boardStatus == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }
        
        //does random moves until the random game is over
        while(boardStatus == -1) {
        	tempState.togglePlayer();
        	tempState.randomPlay();
        	boardStatus = tempState.getBoard().checkStatus();
        }
        
        return boardStatus;
	}
	
	/**
	 * Update node values
	 * @param nodeToExplore - The node to start updating from
	 * @param playoutResult - The result of the simulation
	 */
	private void backPropogation(Node nodeToExplore, int playoutResult) {
		Node tempNode = nodeToExplore;
		
		// goes through the nodes
		while(tempNode != null) {
			//increments visit count of the nodes
			tempNode.getState().incrementVisit();
			
			//adds to the win score if the move led to a win
			if(tempNode.getState().getPlayer() == playoutResult)
				tempNode.getState().addScore(10);
			
			//goes to the next node
			tempNode = tempNode.getParent();
		}
	}
}
