import java.util.*;
public class IntelligentAgent extends Player {
	private int horizon = 15;
	public void startPlaying (Grid board, char player) 
	{ 
		super.intelligenceMode=true; 
		super.startPlaying(board, player);
	}
	public char intelligence(Grid board, char player)
	{	
		super.intelligence(board, player);
		board.refreshBoard(alphaBetaSearch(board), player);
		if(board.hasWon(player))
		{
			System.out.println("Player: "+ player + " has won!"); 
			board.drawBoard();
			System.exit(1);
		}
		return 'X'; 
	}
	
	public int alphaBetaSearch (Grid board)
	{
		Integer [] values;
		values = new Integer[7];
		int v = -Integer.MAX_VALUE;
		int largest = v; 
		List<Integer> valid = new ArrayList<Integer>() ;
		board.validMoves(valid);
		for (int i=0; i<valid.size(); i++){
			board.refreshBoard(valid.get(i), 'Y');
			v = Math.max(v, minValue(board,-Integer.MAX_VALUE,Integer.MAX_VALUE, 0));
			values[i]=v;
			if (v>largest)
				largest = v; 
			board.removeMove(valid.get(i), board.lastRow(valid.get(i)));
			}

		return java.util.Arrays.asList(values).indexOf(largest); 
	}

	public int minValue (Grid state, int alpha, int beta, int depth)
	{
		if(isTerminal(depth))
			return evaluation(state);
		int v= Integer.MAX_VALUE;
		List<Integer> valid = new ArrayList<Integer>() ;
		state.validMoves(valid);
		for (int i=0; i<valid.size(); i++)
		{			
			state.refreshBoard(valid.get(i), 'X');
			v = Math.min(v, maxValue(state, alpha, beta, depth+1)); 
			if (v<=alpha){
				state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
				return v;//beta cut-off
			}
			beta = Math.min(v, beta);
			state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
		} 
		return v; 
	}
	public int maxValue(Grid state, int alpha, int beta, int depth)
	{
		if (isTerminal(depth))
			return evaluation(state);
		int v= -Integer.MAX_VALUE;
		List<Integer> valid = new ArrayList<Integer>() ;
		state.validMoves(valid);
		for (int i=0; i<valid.size();i++)
		{
			state.refreshBoard(valid.get(i), 'Y');
			v= Math.max(v,minValue(state,alpha,beta, depth+1)); 
			if (v>=beta){
				state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
				return v;//alpha cut-off
			}
			alpha = Math.max(v,alpha); 
			state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
		}
		return v; 
	}
	
	private int evaluation(Grid state) { 
		if(state.hasWon('X')) 
			 return (-Integer.MAX_VALUE);
		 if(state.hasWon('Y')) 
			 return Integer.MAX_VALUE;	
		 List<Integer> checkLines= new ArrayList<Integer>(state.checkLines('Y'));
		 return ((checkLines.get(1) * 100) + (checkLines.get(0) * 10)) - ((checkLines.get(2) * 100) + (checkLines.get(3) * 10));
	}

	private boolean isTerminal(int nodeHorizon) {
		return (nodeHorizon>=horizon); 

	}
	}
