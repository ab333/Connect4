import java.util.Random;
import java.util.Scanner;


public class Connect4 {
	public static void main(String[] args) {
		Grid g = new Grid();
		int col;
		boolean allowedMove;
		char player = 'X';
		System.out.println("Play Against:\n" +
				"1-Second Player\n" +
				"2- Random Agent\n" +
				"3- AI Agent");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		g.drawBoard();
		if(option == 1) {
			do {
				do {
					in = new Scanner(System.in);
					col = in.nextInt();
					allowedMove = g.refreshBoard(col,player);
				} while (!allowedMove); // Add Referee isAllowed here 
				g.drawBoard();
				///// Switch players
				if(player == 'X')
					player = 'O';
				else
					player = 'X';
			} while (!g.boardFull());
			
		}
		else if(option == 2) {
			do {
				if(player == 'X') {
					do {
						in = new Scanner(System.in);
						col = in.nextInt();
						allowedMove = g.refreshBoard(col,player);
					} while (!allowedMove); // Add Referee isAllowed here
					player = 'O';
				}
				else {
					do {
						Random rand = new Random();
						col = rand.nextInt((6 - 0) + 1) + 0;
						allowedMove = g.refreshBoard(col,player);
					} while (!allowedMove); // Add Referee isAllowed here
					player = 'X';
				}
					
				g.drawBoard();
				
				
			} while (!g.boardFull());
		}
		else if(option == 3) {
			// TODO
		}
		in.close();
	}
}
