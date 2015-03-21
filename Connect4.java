
import java.util.Scanner;


public class Connect4 {
	public static void main(String[] args) {
		Grid g = new Grid();
		int col;
		char player = 'X';
	g.drawBoard();
	do {
		Scanner in = new Scanner(System.in);
		col = in.nextInt();
		g.refreshBoard(col,player);
		g.drawBoard();
		///// Switch player
		if(player == 'X')
			player = 'O';
		else
			player = 'X';
	} while (true);
	}
}
