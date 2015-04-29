
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
//import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Player implements ActionListener {
        private int col;
	protected boolean intelligenceMode; 
	private boolean randomMode; 
	private boolean agentPlayAgent;
	private boolean userPlay;
        private GUIGrid guiGrid;
        private char PlayerName;
        private Grid board2;
       
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
        
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
            col = Integer.parseInt(e.getActionCommand());
            if(!board2.refreshBoard(col,PlayerName))
            {
                
                System.out.println("Please choose another column");
                JOptionPane.showMessageDialog(null, "Please choose another column!");
                return;
            }
            else
            {
                if(PlayerName == 'X'){ 
                    guiGrid.updateGrid(col,board2.getX_lastTry_R(),PlayerName);
                    
                }
                if(PlayerName == 'Y'){
                    guiGrid.updateGrid(col,board2.getY_lastTry_R(),PlayerName);
                }
                if(board2.hasWon(PlayerName))
                {
                    System.out.println("Player: "+ PlayerName + " has won!");
                    guiGrid.hasWon(board2.winningPositions,PlayerName);
//                    for(int i = 3; i >-1 ; i--){
//                    System.out.println(board2.winningPositions.get(i)[0]+":"+board2.winningPositions.get(i)[1]);
//                    }
                    board2.drawBoard();
                    System.exit(1);
                }
                if(PlayerName == 'X')
                {
                    
                    PlayerName = 'Y';
                    char tempPlayerName = PlayerName;
                    if(intelligenceMode){
                        
                        PlayerName = intelligence(board2, PlayerName);
                        guiGrid.updateGrid(board2.getY_LastTry_C(),board2.getY_lastTry_R(),'Y');
                        
                        if(board2.hasWon(tempPlayerName))
                        {
                            System.out.println("Player: "+ tempPlayerName + " has won!");
                            guiGrid.hasWon(board2.winningPositions,tempPlayerName);
                            board2.drawBoard();
                            System.exit(1);
		}
                        
                    }
                    else if (randomMode){
                        PlayerName = randomAgent(board2,PlayerName);
                        guiGrid.updateGrid(board2.getY_LastTry_C(),board2.getY_lastTry_R(),'Y');
                        if(board2.hasWon(tempPlayerName))
                        {
                            System.out.println("Player: "+ tempPlayerName + " has won!");
                            guiGrid.hasWon(board2.winningPositions,tempPlayerName);
                            board2.drawBoard();
                            System.exit(1);
		}
                    }
                }
                else
                    PlayerName = 'X';
                board2.drawBoard();
                
            }
            if(board2.boardFull())
            {
                JOptionPane.showMessageDialog(null, "Game over!");
                //System.out.println("Game over");
                System.exit(1);
            }
    }
    
	public void startPlaying(Grid board,char player) {
            PlayerName = player;
            board2 = board;
            guiGrid = new GUIGrid();
            for(int i=0 ; i<7 ;i++)
            {
                if(!intelligenceMode){
                    guiGrid.setButtonAction(i,Connect4.user);
                }
                else
                {
                    guiGrid.setButtonAction(i,Connect4.Ai);
                }
            }
//		Scanner in = new Scanner(System.in);
//		do {
//
//			if(agentPlayAgent)
//				intelligence(board, player);
//			if(userPlay)
//				do {
//				col = in.nextInt(); 
//				} while (!board.refreshBoard(col,player)); // Add Referee isAllowed here
//			///// Switch players
//			if(!agentPlayAgent && board.hasWon(player))// Ai has his own (hasWon) func. no need to check twice.
//				{
//					System.out.println("Player: "+ player + " has won!"); 
//					board.drawBoard();
//					System.exit(1);
//				}
//			if(player == 'X')
//			{
//				player = 'Y';
//				if(intelligenceMode)  
//					player = intelligence(board, player);
//				else if (randomMode)
//					player = randomAgent(board,player); 
//			}
//			else 
//				player = 'X';
//			board.drawBoard();
//			
//		} while (!board.boardFull());
//		in.close();
		
	} 
	
	public char randomAgent(Grid board, char player)
	{
		do {
			Random rand = new Random();
			col = rand.nextInt((6 - 0) + 1) + 0;
		} while (!board.refreshBoard(col,player)); // Add Referee isAllowed here
//		board.hasWon(player);
		return 'X'; 	
	}	
	public char intelligence(Grid board, char player) {
		return 'X'; 
	}
}
