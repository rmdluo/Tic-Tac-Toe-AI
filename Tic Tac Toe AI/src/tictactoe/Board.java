package tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private int[][] boardValues;
	private int totalMoves;
	
	public Board() {
		boardValues = new int[3][3];
	}
	
	public Board(int[][] boardValues) {
        this.boardValues = boardValues;
    }
	
	public Board(int[][] boardValues, int totalMoves) {
        this.boardValues = boardValues;
        this.totalMoves = totalMoves;
    }
	
	public Board(Board board) {
        int boardLength = board.getBoardValues().length;
        this.boardValues = new int[boardLength][boardLength];
        int[][] boardValues = board.getBoardValues();
        int n = boardValues.length;
        for (int i = 0; i < n; i++) {
            int m = boardValues[i].length;
            for (int j = 0; j < m; j++) {
                this.boardValues[i][j] = boardValues[i][j];
            }
        }
    }

	/**
	 * performs a move on the board
	 * @param player - the player making the move
	 * @param p - where the move is
	 */
	public void performMove(int player, Position p) {
		if(boardValues[p.getRow()][p.getColumn()] == 0) {
			boardValues[p.getRow()][p.getColumn()] = player;
			totalMoves++;
		}
	}
	
	/**
	 * returns the status of the board
	 * @return
	 * 		-1 - game in progress
	 * 		0 - draw
	 * 		1 - player 1 wins
	 * 		2 - player 2 wins
	 */
	public int checkStatus() {		
		//checks if a player wins
		if(boardValues[0][0] != 0 && boardValues[0][0] == boardValues[0][1] && boardValues[0][1] == boardValues[0][2])
			return boardValues[0][0];
		
		if(boardValues[1][0] != 0 && boardValues[1][0] == boardValues[1][1] && boardValues[1][1] == boardValues[1][2])
			return boardValues[1][0];
		
		if(boardValues[2][0] != 0 && boardValues[2][0] == boardValues[2][1] && boardValues[2][1] == boardValues[2][2])
			return boardValues[2][0];
		
		if(boardValues[0][0] != 0 && boardValues[0][0] == boardValues[1][0] && boardValues[1][0] == boardValues[2][0])
			return boardValues[0][0];
		
		if(boardValues[0][1] != 0 && boardValues[0][1] == boardValues[1][1] && boardValues[1][1] == boardValues[2][1])
			return boardValues[0][1];
		
		if(boardValues[0][2] != 0 && boardValues[0][2] == boardValues[1][2] && boardValues[1][2] == boardValues[2][2])
			return boardValues[0][2];
		
		if(boardValues[0][0] != 0 && boardValues[0][0] == boardValues[1][1] && boardValues[1][1] == boardValues[2][2])
			return boardValues[0][0];
		
		if(boardValues[0][2] != 0 && boardValues[0][2] == boardValues[1][1] && boardValues[1][1] == boardValues[2][0])
			return boardValues[0][2];
		
		//checks if there is a tie
		boolean filled = true;
		for(int i = 0; i < boardValues.length; i++) {
			for(int j = 0; j < boardValues[i].length; j++) {
				if(boardValues[i][j] == 0)
					filled = false;
			}
		}
		if(filled)
			return 0;
		
		//if none of the above are true, return 0
		return -1;
    }
	
	/**
	 * prints the board
	 */
	public void display() {
		int size = this.boardValues.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(boardValues[i][j] + " ");
            }
            System.out.println();
        }
	}
	
	/**
	 * prints the status of the board
	 */
	public void printStatus() {
		switch (this.checkStatus()) {
        case 1:
            System.out.println("Player 1 wins");
            break;
        case 2:
            System.out.println("Player 2 wins");
            break;
        case 0:
            System.out.println("Game Draw");
            break;
        case -1:
            System.out.println("Game In Progress");
            break;
        }
	}
	
	/**
	 * gets the possible moves on the board
	 * @return a list of possible moves
	 */
	public List<Position> getPossibleMoves() {
		int size = this.boardValues.length;
        List<Position> possibleMoves = new ArrayList<>();
        
        //goes through board to find empty spaces
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (boardValues[i][j] == 0)
                	possibleMoves.add(new Position(i, j));
            }
        }
        return possibleMoves;
	}
	
	public int[][] getBoardValues() {
		return boardValues;
	}

	public void setBoardValues(int[][] boardValues) {
		this.boardValues = boardValues;
	}

	public int getTotalMoves() {
		return totalMoves;
	}

	public void setTotalMoves(int totalMoves) {
		this.totalMoves = totalMoves;
	}
}
