package test;

import java.util.Scanner;

import mcts.MonteCarloTreeSearch;
import tictactoe.Board;
import tictactoe.Position;

public class TestAI {
	public static void main(String[] args) {
		simulateAIvsAI();
		//simulateHumanvsAI();
	}
	
	// test for ai against ai
	public static void simulateAIvsAI() {
		MonteCarloTreeSearch ai = new MonteCarloTreeSearch();
		Board board = new Board();
		board.display();
		System.out.println();

		int player = 1;
		
		while(board.checkStatus() == -1) {
			board = ai.findNextMove(board, player);
			board.display();
			System.out.println();

			player = 3 - player;
		}
		
		board.printStatus();
	}
	
	// test for human vs ai
	public static void simulateHumanvsAI() {
		MonteCarloTreeSearch ai = new MonteCarloTreeSearch();
		Board board = new Board();
		board.display();
		System.out.println();

		int player = 1;
		
		while(board.checkStatus() == -1) {
			Scanner reader = new Scanner(System.in);
			System.out.print("Row (0, 1, 2)? ");
			int row = reader.nextInt();
			System.out.print("Column (0, 1, 2)? ");
			int column = reader.nextInt();
			
			board.performMove(player, new Position(row, column));
			player = 3 - player;

			board = ai.findNextMove(board, player);
			board.display();
			System.out.println();

			player = 3 - player;
		}
		
		board.printStatus();
	}
}
