

import java.util.Random;
import java.util.Scanner;

public class Player {
	
	private int col;
	protected boolean intelligenceMode; 
	private boolean randomMode; 
	private boolean agentPlayAgent;
	private boolean userPlay;
	
	public Player () {
		intelligenceMode = false; 
		randomMode = false; 
		userPlay = true;
	}
	public void activateRandomPlayer()
	{
		randomMode = true; 
	}
	public void activateAgentVsAgent()
	{
		agentPlayAgent=true;
		userPlay=false; 
	}
	public void startPlaying(Grid board,char player) {
		Scanner in = new Scanner(System.in);
		do {

			if(agentPlayAgent)
				intelligence(board, player);
			if(userPlay)
				do {
				col = in.nextInt(); 
				} while (!board.refreshBoard(col,player)); // Add Referee isAllowed here

			///// Switch players
			if(!agentPlayAgent && board.hasWon(player))// Ai has his own (hasWon) func. no need to check twice.
				{
					System.out.println("Player: "+ player + " has won!"); 
					board.drawBoard();
					System.exit(1);
				}
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
