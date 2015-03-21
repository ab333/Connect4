import java.util.Random;
import java.util.Scanner;


public class Connect4 {
	public static void main(String[] args) {
		Grid g = new Grid();
		int col;
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
				in = new Scanner(System.in);
				col = in.nextInt();
				g.refreshBoard(col,player); // Add Referee isAllowed while loop here
				g.drawBoard();
				///// Switch players
				if(player == 'X')
					player = 'O';
				else
					player = 'X';
			} while (true);
		}
		else if(option == 2) {
			do {
				if(player == 'X') {
					in = new Scanner(System.in);
					col = in.nextInt(); // Add Referee isAllowed while loop here
					g.refreshBoard(col,player);
					player = 'O';
				}
				else {
				    Random rand = new Random();
				    col = rand.nextInt((6 - 0) + 1) + 0; // Add Referee isAllowed while loop here
				    g.refreshBoard(col,player);
					player = 'X';
				}
					
				g.drawBoard();
				
				
			} while (true);
		}
		else if(option == 3) {
			// TODO
		}	
	}
}
