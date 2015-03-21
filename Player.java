package connect4;

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
			if(player == 'X')
			{
				player = 'Y';
				if(intelligenceMode)
					player = intelligence(board, player);
				else if (randomMode)
					player = randomAgent(board,player); 
			}
			else
				player = 'X';
		} while (!board.boardFull());
		in.close();
		
	} 
	
	public char randomAgent(Grid board, char player)
	{
		do {
			Random rand = new Random();
			col = rand.nextInt((6 - 0) + 1) + 0;
		} while (!board.refreshBoard(col,player)); // Add Referee isAllowed here
		board.drawBoard();
		return 'X'; 	
	}	
	public char intelligence(Grid board, char player) {
		return 'X'; 
	}
}
