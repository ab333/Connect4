package connect4;
import java.util.*;
public class IntelligentAgent extends Player {
	private int horizon = 8;
	public void startPlaying (Grid board, char player) 
	{ 
		super.intelligenceMode=true; 
		super.startPlaying(board, player);
	}
	public char intelligence(Grid board, char player)
	{	
		super.intelligence(board, player); 
		board.refreshBoard(alphaBetaSearch(board,player), player)
		if(board.hasWon(player))
		{
			System.out.println("Player: "+ player + " has won!"); 
			board.drawBoard();
			System.exit(1);
		}
		if(player =='Y')
			return 'X'; 
		return 'Y'; 
	}
	
	protected int alphaBetaSearch (Grid board, char player)
	{
		Integer [] values;
		values = new Integer[7];
		int v = -Integer.MAX_VALUE;
		int largest = v; 
		List<Integer> valid = new ArrayList<Integer>() ;
		board.validMoves(valid);
		for (int i=0; i<valid.size(); i++){
			board.refreshBoard(valid.get(i), player);
			v = Math.max(v, minValue(board,-Integer.MAX_VALUE,Integer.MAX_VALUE, 0, player));
			values[i]=v;
			if (v>largest)
				largest = v; 
			board.removeMove(valid.get(i), board.lastRow(valid.get(i)));
			if(largest==Integer.MAX_VALUE)
				break; 
			}  
		for (int i=0; i<valid.size();i++)
			System.out.println("Child ["+ i +"] value: " + values[i]);
		System.out.println("Largest is: " + largest+ " action is: "+ valid.get(java.util.Arrays.asList(values).indexOf(largest)));
		return valid.get(java.util.Arrays.asList(values).indexOf(largest)); 
	}

	private int minValue (Grid state, int alpha, int beta, int depth, char player)
	{
		if(isTerminal(depth) || state.getHasWon())
			return evaluation(state, player);
		int v= Integer.MAX_VALUE;
		List<Integer> valid = new ArrayList<Integer>() ;
		state.validMoves(valid); 
		for (int i=0; i<valid.size(); i++)
		{	
			if(player=='Y')
				state.refreshBoard(valid.get(i), 'X');
			else 
				state.refreshBoard(valid.get(i), 'Y');
			v = Math.min(v, maxValue(state, alpha, beta, depth+1, player)); 
			if (v<=alpha){ 
				state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
				return v;//beta cut-off
			}
			beta = Math.min(v, beta);
			state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
			//if(beta == -Integer.MAX_VALUE)
			//	break;
		} 
		return v; 
	}
	private int maxValue(Grid state, int alpha, int beta, int depth, char player)
	{
		if (isTerminal(depth)|| state.getHasWon())
			return evaluation(state, player);
		int v= -Integer.MAX_VALUE;
		List<Integer> valid = new ArrayList<Integer>() ;
		state.validMoves(valid); 
		for (int i=0; i<valid.size();i++)
		{
			state.refreshBoard(valid.get(i), player);
			v= Math.max(v,minValue(state,alpha,beta, depth+1, player)); 
			if (v>=beta){
				state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
				return v;//alpha cut-off
			}
			alpha = Math.max(v,alpha); 
			state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
		//	if (alpha==Integer.MAX_VALUE)
			//	break; 
		}
		return v; 
	}
	
	
	private int evaluation(Grid state, char player) { 
		if(player=='Y' && state.hasWon('X'))
				 return (-Integer.MAX_VALUE);
		if(player=='X' && state.hasWon('Y'))
			 return (-Integer.MAX_VALUE);
		if(state.hasWon(player)) 
			 return Integer.MAX_VALUE;		
		 List<Integer> checkLines= new ArrayList<Integer>(state.checkLines(player));
		 return ((checkLines.get(1) * 9) + (checkLines.get(0) * 2)) - ((checkLines.get(3) * 9) + (checkLines.get(2) * 2));
	}

	private boolean isTerminal(int nodeHorizon) {
		return (nodeHorizon>=horizon); 

	}
	
}
