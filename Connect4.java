
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect4 implements ActionListener{
    
    private static final Grid g = new Grid();
    public static final Player user = new Player();
    public static final IntelligentAgent Ai = new IntelligentAgent();
    private static MainWindow mainWindow;
    
        @Override
        public void actionPerformed(ActionEvent e)
        {
            char player = 'X';
            String cmd = e.getActionCommand();
            switch (cmd) {
                case "Another":
                    mainWindow.getFrame().dispose();
                    user.startPlaying(g, player);
                    break;
                case "Random":
                    mainWindow.getFrame().dispose();
                    user.activateRandomPlayer();
                    user.startPlaying(g, player);
                    break;
                case "AI":
                    mainWindow.getFrame().dispose();
                    Ai.startPlaying(g, player);
                    break;
            }
        }
        
	public static void main(String[] args) {
            try{
                mainWindow = new MainWindow();
            
            } catch (Exception e) {
                System.err.print("You can't use GUI :( , but you can play with CMD" + e);
		char player = 'X';
		System.out.println("Play Against:\n" +
								"1- Another Player\n" +
								"2- Random Agent\n" +
								"3- AI Agent\n" +
								"4- Agent vs Agent\n");
                
		g.drawBoard();
                try (Scanner in = new Scanner(System.in)) {
                    int option = in.nextInt();
                    if(option == 1) {
                        user.startPlaying(g, player);
                    }
                    else if(option == 2) {
                        user.activateRandomPlayer();
                        user.startPlaying(g, player);
                    }
                    else if(option == 3) {
                        //IntelligentAgent Ai = new IntelligentAgent ();
                        Ai.startPlaying(g, player);
                    }
                    else if(option == 4)
                    {
                        IntelligentAgent smart = new IntelligentAgent ();
                        smart.activateAgentVsAgent();
                        smart.startPlaying(g, player);
                    }
                } catch (Exception ee)
                        {
                            System.err.print("ERORR" + ee);
                        }
            }
	}
}
