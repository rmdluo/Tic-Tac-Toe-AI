package mcts;

import java.util.ArrayList;
import java.util.List;

import tictactoe.Board;
import tictactoe.Position;

public class State {
	private Board board;
	private int player;
	private int visitCount;
	private double winScore;
	
	public State() {
		board = new Board();
	}
	
	public State(State state) {
		this.board = new Board(state.getBoard());
        this.player = state.getPlayer();
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
	}
	
	public State(Board board) {
        this.board = new Board(board);
    }

	/**
	 * gets the possible states from possible moves
	 * @return the possible states
	 */
	public List<State> getAllPossibleStates() {
		List<State> possibleStates = new ArrayList<>();
		
		//gets the possible moves
        List<Position> availablePositions = this.board.getPossibleMoves();
        
        //adds a state for each possible move
        availablePositions.forEach(p -> {
            State newState = new State(this.board);
            newState.setPlayer(3 - this.player);
            newState.getBoard().performMove(newState.getPlayer(), p);
            possibleStates.add(newState);
        });
        return possibleStates;
	}
	
	public void incrementVisit() {
		visitCount++;
	}
	
	public void addScore(double score) {
		winScore += score;
	}
	
	/**
	 * does random move
	 */
	public void randomPlay() {
		//gets the possible moves
		List<Position> availablePositions = this.board.getPossibleMoves();
		
		//selects a random move
        int totalPossibilities = availablePositions.size();
        int selectRandom = (int) (Math.random() * totalPossibilities);
        
        //performs the move
        this.board.performMove(this.player, availablePositions.get(selectRandom));
	}
	
	public void togglePlayer() {
		player = 3 - player;
	}
	
	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public double getWinScore() {
		return winScore;
	}

	public void setWinScore(double winScore) {
		this.winScore = winScore;
	}
	
	public int getOpponent() {
		return 3 - player;
	}
}
