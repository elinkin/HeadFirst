package TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class Game {

	private static final int BOARD_SIZE = 3;
	private Mark[][] board = new Mark[BOARD_SIZE][BOARD_SIZE];
	private PlayerTypes currentPlayer = PlayerTypes.NONE;
	private PlayerTypes winner = PlayerTypes.NONE;
	private int piecesOnBoard = 0;

	public Game() {

		for (int currRow = 0; currRow < board.length; ++currRow) {
			for (int currCol = 0; currCol < board[currRow].length; ++currCol) {

				board[currRow][currCol] = Mark.EMPTY;
			}
		}

		currentPlayer = PlayerTypes.HUMAN;
	}

	private static PlayerTypes pieceToPlayer(Mark type) {
		switch (type) {
		case NOUGHT:
			return PlayerTypes.AI;
		case CROSS:
			return PlayerTypes.HUMAN;
		default:
			return null;
		}
	}

	public void playMove() {

		switch (currentPlayer) {
		case AI:
			runAITurn();
			break;
		case HUMAN:
			runHumanTurn();
			break;
		}
	}

	public boolean isGameOver() {
		for (int row = 0; row < 3; row++) {
			if (board[row][0] == board[row][1]
					&& board[row][1] == board[row][2]) {
				if (board[row][0] != Mark.EMPTY) {
					this.winner = pieceToPlayer(board[row][0]);
					return true;
				}
			}
		}

		for (int col = 0; col < 3; col++) {
			if (board[0][col] == board[1][col]
					&& board[1][col] == board[2][col]) {
				if (board[0][col] != Mark.EMPTY) {
					this.winner = pieceToPlayer(board[0][col]);
					return true;
				}
			}
		}

		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[1][1] != Mark.EMPTY) {
				this.winner = pieceToPlayer(board[1][1]);
				return true;
			}
		}

		if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			if (board[1][1] != Mark.EMPTY) {
				this.winner = pieceToPlayer(board[1][1]);
				return true;
			}
		}

		if (piecesOnBoard == BOARD_SIZE * BOARD_SIZE) {
			this.winner = PlayerTypes.NONE;
			return true;
		}

		return false;
	}

	public PlayerTypes getWinner() {
		return this.winner;
	}

	private void runAITurn() {
		System.out.println("It is computer's turn.");

		Random random = new Random();
		int move_row = random.nextInt(BOARD_SIZE);
		int move_col = random.nextInt(BOARD_SIZE);

		while (board[move_row][move_col] != Mark.EMPTY) {
			move_row = random.nextInt(BOARD_SIZE);
			move_col = random.nextInt(BOARD_SIZE);
		}

		board[move_row][move_col] = Mark.NOUGHT;

		this.piecesOnBoard++;
	}

	private void runHumanTurn() {
		boolean validInput = false;
		System.out.println("Human player, make your move!");

		do {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter coordinates, (row[1-3] column[1-3]): ");
			int move_row = scanner.nextInt() - 1;
			int move_col = scanner.nextInt() - 1;
			if (move_row >= 0 && move_row < board.length && move_col >= 0 && move_col < board.length && board[move_row][move_col] == Mark.EMPTY) {
				validInput = true;
				board[move_row][move_col] = Mark.CROSS;
			} else {
				System.out.println("This move is not valid. Please try again.");
			}
		} while (!validInput);

		this.piecesOnBoard++;
	}

	public void switchTurns() {
		currentPlayer = currentPlayer == PlayerTypes.HUMAN ? PlayerTypes.AI : PlayerTypes.HUMAN;
	}

	public void displayBoard() {
		for (int row = 0; row < BOARD_SIZE; ++row) {
			for (int col = 0; col < BOARD_SIZE; ++col) {
				switch (board[row][col]) {
				case CROSS:
					System.out.print(" X ");
					break;
				case NOUGHT:
					System.out.print(" O ");
					break;
				case EMPTY:
					System.out.print("   ");
					break;
				}
				if (col < BOARD_SIZE - 1)
					System.out.print("|");
			}
			System.out.println();
			if (row < BOARD_SIZE - 1) {
				System.out.println("-----------");
			}
		}
		System.out.println();
	}
}