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
		board.hasWon(player); 
		return 'X'; 
	}
	
	public int alphaBetaSearch (Grid board)
	{
		Integer [] values;
		values = new Integer[7];
		int v = -Integer.MAX_VALUE;
		int largest = v; 
		List<Integer> valid = new ArrayList<Integer>();
		board.validMoves(valid);
		for (int i=0; i<valid.size(); i++){
			board.refreshBoard(valid.get(i), 'Y');
			v = Math.max(v, minValue(board,-Integer.MAX_VALUE,Integer.MAX_VALUE));
			values[i]=v;
			if (v>largest)
				largest = v; 
			board.removeMove(valid.get(i), board.lastRow(valid.get(i)));
			}
		System.out.println("Largest is: " + largest+ " action is: "+ java.util.Arrays.asList(values).indexOf(largest)); 
		return java.util.Arrays.asList(values).indexOf(largest); 
	}
	
	public int minValue (Grid state, int alpha, int beta)
	{
		if(isTerminal())
			return evaluation(state);
		int v= Integer.MAX_VALUE;
		List<Integer> valid = new ArrayList<Integer>() ;
		state.validMoves(valid);
		for (int i=0; i<valid.size(); i++)
		{			
			state.refreshBoard(valid.get(i), 'X');
			v = Math.min(v, maxValue(state, alpha, beta)); 
			if (v<=alpha)	{
				state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
				return v;//beta cut-off
			}
			beta = Math.min(v, beta);
			state.removeMove(valid.get(i), state.lastRow(valid.get(i)));
		} 
		return v; 
	}
	public int maxValue(Grid state, int alpha, int beta)
	{
		if (isTerminal())
			return evaluation(state); 
		int v= -Integer.MAX_VALUE;
		List<Integer> valid = new ArrayList<Integer>() ;
		state.validMoves(valid);
		for (int i=0; i<valid.size();i++)
		{
			state.refreshBoard(valid.get(i), 'Y');
			v= Math.max(v,minValue(state,alpha,beta)); 
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
                
                int fourSequers = state.check4s('Y'); // X is the max !! i do not know why !!
                int threeSequers = state.check3s('Y');
                int twoSequers = state.check2s('Y');
                int oppfourSequers = state.check4s('X');
                int oppthreeSequers = state.check3s('X');
                int opptwoSequers = state.check2s('X');
                int bestValue = ((threeSequers * 100) + (twoSequers * 10)) - ((oppthreeSequers * 100) + (opptwoSequers * 10));
                
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


	public boolean isTerminal() {
		return ((horizon--)<=0); 

	}
	}
