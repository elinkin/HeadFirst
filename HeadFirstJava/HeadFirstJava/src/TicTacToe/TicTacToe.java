package TicTacToe;

public class TicTacToe {

	public static void main(String[] args) {
		Game game = new Game();
		System.out.println(game);

		do {
			game.playMove();
			System.out.println(game);
			game.paint();
			game.switchTurns();
		} while (!game.isGameOver());
		
		switch (game.getWinner()) {
			case AI: System.out.println("Computer has beaten you!"); 
			break;
			case HUMAN: System.out.println("You win!)"); 
			break;
			default: System.out.println("No more moves. It is a draw."); 
			break;
		}
	}

}
