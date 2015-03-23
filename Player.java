

import java.util.Random;
import java.util.Scanner;

public class Player {
	
	private int col;
	protected boolean intelligenceMode; 
	private boolean randomMode; 
	
	public Player () {
		intelligenceMode = false; 
		randomMode = false; 
	}
	public void activateRandomPlayer()
	{
		randomMode = true; 
	}

	public void startPlaying(Grid board,char player) {
		Scanner in = new Scanner(System.in);
		do {
			do {
				
				col = in.nextInt(); 
			} while (!board.refreshBoard(col,player)); // Add Referee isAllowed here
			///// Switch players
			if(board.hasWon(player))
			{
				board.drawBoard();
				System.out.println("Player " + player + " has won.\n");
		 		System.exit(1);
			}
			if(player == 'X')
			{
				player = 'Y';
				if(intelligenceMode) { 
					player = intelligence(board, player);
				} 
				else if (randomMode)
					player = randomAgent(board,player); 
			}
			else 
				player = 'X';
				board.drawBoard();
			
		} while (!board.boardFull());
		in.close();
		
	} 
	
	public char randomAgent(Grid board, char player)
	{
		do {
			Random rand = new Random();
			col = rand.nextInt((6 - 0) + 1) + 0;
		} while (!board.refreshBoard(col,player)); // Add Referee isAllowed here
		board.hasWon(player); 
		board.drawBoard();
		return 'X'; 	
	}	
	public char intelligence(Grid board, char player) {
		return 'X'; 
	}
}
