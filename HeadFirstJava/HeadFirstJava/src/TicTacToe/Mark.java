package TicTacToe;

public enum Mark {
	EMPTY, CROSS, NOUGHT;

Mark content;
	
	public void paint() {
	    switch (content) {
	       case CROSS:  System.out.print(" X "); break;
	       case NOUGHT: System.out.print(" O "); break;
	       case EMPTY:  System.out.print("   "); break;
	    }
	}
}