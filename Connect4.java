package connect4;
import java.util.Scanner;
public class Connect4 {
	static Grid g = new Grid();
	static Player user = new Player(); 
	public static void main(String[] args) {
		char player = 'X'; 
		System.out.println("Play Against:\n" +
								"1- Another Player\n" +
								"2- Random Agent\n" +
								"3- AI Agent\n" +
								"4- Agent vs Agent\n");
		
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		g.drawBoard();
		if(option == 1) {
			user.startPlaying(g, player);  
		}
		else if(option == 2) {
			user.activateRandomPlayer();
			user.startPlaying(g, player);
		}
		else if(option == 3) {
			IntelligentAgent Ai = new IntelligentAgent (); 
			Ai.startPlaying(g, player);
		}
		else if(option == 4)
		{
			IntelligentAgent smart = new IntelligentAgent ();
			smart.activateAgentVsAgent();
			smart.startPlaying(g, player);
		}
		in.close();
	}
}
