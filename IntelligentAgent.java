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
		board.hasWon(player); 
		return 'X'; 
	}
	
public int alphaBetaSearch (Grid board) {
		TreeNode currentState = new TreeNode(0,board,0); 
		int bestMove = currentState.searchNode(maxValue(currentState, -Integer.MAX_VALUE, Integer.MAX_VALUE)).getAction();
		return bestMove; 
	}
	
	/*public int maxValue(TreeNode state, int alpha, int beta)
	{
		if (isTerminal(state))
			return evaluation(state.getBoard()); 
		int v= -Integer.MAX_VALUE;
		for (int i=0; i<state.successorLenght();i++)
		{
			if(tempBoard.refreshBoard(i, 'X')) 
					state= new TreeNode (i,tempBoard,state.getDepth()); 
			v= Math.max(v,minValue(state,alpha,beta)); 
			if (v>=beta)
				return v; 
			alpha = Math.max(v,alpha); 
			
		}
		return v; 
	}*/
	

	/*public int minValue (TreeNode state, int alpha, int beta)
	{
		if(isTerminal(state))
			return evaluation(state.getBoard());
		int v= -Integer.MAX_VALUE; 
		tempBoard = state.getBoard();
		for (int i=0; i<7; i++)
		{
			if(tempBoard.refreshBoard(i, 'X'))
			{
				state= new TreeNode (i,tempBoard,state.getDepth());
			}
		//	state.refreshBoard(i, 'Y');
			v = Math.min(v, maxValue(state, alpha, beta)); 
			if (v<=alpha)
				return v; 
			beta = Math.min(v, beta); 
		}
		return v; 
	}*/
	
	private int evaluation(Grid state) {
                
                int fourSequers = state.check4s('X'); // X is the max !! i do not know why !!
                int threeSequers = state.check3s('X');
                int twoSequers = state.check2s('X');
                int oppfourSequers = state.check4s('Y');
                int oppthreeSequers = state.check3s('Y');
                int opptwoSequers = state.check2s('Y');
                int bestValue = ((threeSequers * 100) + (twoSequers * 10)) - ((oppthreeSequers * 100) + (twoSequers * 10));
                
                if(fourSequers>0)
                {
                    return Integer.MAX_VALUE;
                }
                else if(oppfourSequers>0)
                {
                    return -Integer.MAX_VALUE;
                }else
		return bestValue;
	}

	public boolean isTerminal(TreeNode state) {
		// TODO 
		if (state.getDepth()==horizon)
			return true;
		return false; 

	}
	}
