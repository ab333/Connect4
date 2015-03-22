public class IntelligentAgent extends Player {
	private final int horizon = 15;
	protected Grid tempBoard; 
	public void startPlaying (Grid board, char player) 
	{ 
		super.intelligenceMode=true; 
		super.startPlaying(board, player);
	}
	public char intelligence(Grid board, char player)
	{	
		super.intelligence(board, player);
		board.refreshBoard(alphaBetaSearch(board), player);
		return 'X'; 
	}
	
	public int alphaBetaSearch (Grid board) {
		return horizon;
	//	TreeNode state = new TreeNode(-1,board,0); 
	//	int bestMove= searchNode(maxValue(newBoard, -Integer.MAX_VALUE, Integer.MAX_VALUE)).getAction(); 
		//return bestMove; 
	}
	}
