package TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
	
	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		System.out.println(game);

		do {
			game.playMove();
			System.out.println(game);
			game.switchTurns();
		} while (!game.isGameOver());
		
		switch (game.getWinner()) {
			case AI: System.out.println("Computer wins.. :("); 
			break;
			case HUMAN: System.out.println("Human wins! ~8^)"); 
			break;
			default: System.out.println("No one wins. :-/"); 
			break;
		}
	}
	
	/** Denotes the size of the board (the board is 3X3) */
	private static final int BOARD_SIZE = 3;

	// instance variables
	private Mark[][] board = new Mark[BOARD_SIZE][BOARD_SIZE];
	private PlayerTypes currPlayer = PlayerTypes.NONE;
	private PlayerTypes winner = PlayerTypes.NONE;
	private int totalPiecesOnBoard = 0;
	
	/**
	 * Constructs a new game with an empty board and sets the first player.
	 */
	public TicTacToe() {
		
		// iterate through the entire board
		for (int currRow = 0; currRow < board.length; ++currRow) {
			for (int currCol = 0; currCol < board[currRow].length; ++currCol) {
				
				// reset each cell
				board[currRow][currCol] = Mark.EMPTY;
			}
		}
		
		// let the human play first
		currPlayer = PlayerTypes.HUMAN;
	}

	/**
	 * Gets the game winner.
	 * @return the game winner
	 */
	public PlayerTypes getWinner() {
		return this.winner;
	}
	
	/**
	 * Plays a single game move by the current player.
	 */
	public void playMove() {
		
		// run the move according to the current player
		switch (currPlayer) {
			case AI:			// it's the human's turn
				runComputerTurn();
				break;
			case HUMAN:				// it's the computer's turn
				runHumanTurn();
				break;
		}
	}
	
	/**
	 * Checks whether the game is over or not.
	 * A game is over in one of two cases:
	 * - someone won
	 * - the board is full
	 * @return true if the game is over, otherwise false
	 */
	public boolean isGameOver() {
		// 8 options of winning
		// cases 1-3, horizontal
		for (int row = 0; row < 3; row++) {
			if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
				if (board[row][0] != Mark.EMPTY) {
					this.winner = pieceToPlayer(board[row][0]);
					return true;
				}
			}
		}
		
		// cases 4-6, vertical
		for (int col = 0; col < 3; col++) {
			if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
				if (board[0][col] != Mark.EMPTY) {
					this.winner = pieceToPlayer(board[0][col]);
					return true;
				}
			}
		}

		// cases 7-8, diagonals
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[1][1] != Mark.EMPTY) {
				this.winner = pieceToPlayer(board[1][1]);
				return true;
			}
		}

		// case 8, opposite diagonal
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			if (board[1][1] != Mark.EMPTY) {
				this.winner = pieceToPlayer(board[1][1]);
				return true;
			}
		}

		// check the case of full board and no winners.
		if (totalPiecesOnBoard == BOARD_SIZE * BOARD_SIZE) {
			this.winner = PlayerTypes.NONE;
			return true;
		}

		return false;
	}
	

	/**
	 * Returns a String representation of the current board and the pieces on it.
	 */
	public String toString() {
		String result = separatorLineToString();

		// iterate on the rows
		for (Mark[] row : board) {
			
			// iterate on the cells of each row
			for (Mark cell : row) {
				
				// get a String representation of each cell
				switch (cell) {
					case EMPTY: result += "| "; break;
					case NOUGHT: result += "|O"; break;
					case CROSS: result += "|X"; break;
				}
			}
			result += "|\n";
			result += separatorLineToString();
		}

		return result;
	}

	/**
	 * Switches the turns among the 2 players.
	 */
	public void switchTurns() {
		currPlayer = PlayerTypes.values()[1 - currPlayer.ordinal()];
	}
	
	/*------------------ PRIVATE MEMBERS -------------------------/*

	/**
	 * Runs the computer's turn.
	 * The computer is pretty dumb and selects a random (valid)
	 * cell to place its piece on.
	 */
	private void runComputerTurn() {
		System.out.println("Computer move");

		// select a random cell
		Random random = new Random();
		int targetX = random.nextInt(BOARD_SIZE);
		int targetY = random.nextInt(BOARD_SIZE);
		
		// keep choosing a cell until valid
		while (board[targetX][targetY] != Mark.EMPTY) {
			targetX = random.nextInt(BOARD_SIZE);
			targetY = random.nextInt(BOARD_SIZE);
		}
		
		// set the piece on that cell
		board[targetX][targetY] = Mark.NOUGHT;
		
		// this will be used to check if the game is over
		this.totalPiecesOnBoard++;
	}
	
	/**
	 * Run the human turn.
	 * The game player can choose where to put their piece, using the console.
	 */
	private void runHumanTurn() {
		boolean validInput = false;
		System.out.println("Player move");

		do {
		// let the user set the cell
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter coordinates, human [X Y]: ");
		int targetX = scanner.nextInt();
		int targetY = scanner.nextInt();
		if (targetX >= 0 && targetX < board.length && targetY >= 0 && targetY < board.length && board[targetX][targetY] == Mark.EMPTY) {
		validInput = true;
		board[targetX][targetY] = Mark.CROSS;
		} else {
			System.out.println("This move is not valid. Please try again.");
	}
} while (!validInput);

		// this will be used to check if the game is over
		this.totalPiecesOnBoard++;
	}
	
	/**
	 * Returns a String representation of the board separator line.
	 */
	private String separatorLineToString() {
		String result = "";
		
		// draw the line
		for (int i = 0; i < BOARD_SIZE; ++i) {
			result += "+-";
		}
		result += "+\n";
		
		return result;
	}

	/**
	 * Converts a cell type to a player, according to the cell content: if
	 * the piece is X, it's a human, if O it's a computer.
	 * @param type the cell type
	 * @return the player type that put the piece on that cell, or null if
	 * no piece is on that cell.
	 */
	private static PlayerTypes pieceToPlayer(Mark theSeed) {
		switch (theSeed) {
			case NOUGHT: return PlayerTypes.AI;
			case CROSS: return PlayerTypes.HUMAN;
			default: return null;
		}
	}
}
