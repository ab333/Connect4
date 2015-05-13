package Connect4new;


import java.util.ArrayList;
import java.util.List;

//import java.util.*;// no need for * !!

// Support agent play against himself // 
public class IntelligentAgent extends Player {
	private final int MAX_HORIZON = 8; 
	private final int POSITIVE_INFINITY = Integer.MAX_VALUE; 
	private final int MINUS_INFINITY = -Integer.MAX_VALUE;
        @Override
	public void startPlaying (Grid board, char player) { 
		super.intelligenceMode=true;
		super.startPlaying(board, player);
	}
        @Override
	public char intelligence(Grid board, char player) {	
		board.refreshBoard(alphaBetaSearch(board,player), player);
		if(player =='Y')
			return 'X'; 
		return 'Y'; 
	}
	
	protected int alphaBetaSearch (Grid board, char player)
	{
		return getBestMove(board, player);
	}

	private int getBestMove(Grid board, char player) {
		Integer [] childsValue;
		childsValue = new Integer[7];
		int dummyTemp = MINUS_INFINITY;
		int bestMoveValue = dummyTemp;
		List<Integer> validMoves = new ArrayList<Integer>() ;
		board.getValidMoves(validMoves);
		for (int i=0; i<validMoves.size(); i++){
			board.refreshBoard(validMoves.get(i), player);
			dummyTemp = minValue(board,MINUS_INFINITY,POSITIVE_INFINITY, 0, player);
			childsValue[i]=dummyTemp;
			if (dummyTemp>bestMoveValue)
				bestMoveValue = dummyTemp; 
			board.removeMove(validMoves.get(i), board.lastRow(validMoves.get(i)));
			if(bestMoveValue==POSITIVE_INFINITY)
				break; 
			}  
		return validMoves.get(java.util.Arrays.asList(childsValue).indexOf(bestMoveValue)); 
	}
	private int minValue (Grid state, int alpha, int beta, int depth, char player) {
		int holder= POSITIVE_INFINITY;
		List<Integer> validMoves = new ArrayList<Integer>() ;
		state.getValidMoves(validMoves); 
		if(isTerminal(state, depth, validMoves))
			return evaluation(state, player);
		for (int i=0; i<validMoves.size(); i++)
		{	
			if(player=='Y')
				state.refreshBoard(validMoves.get(i), 'X');
			else 
				state.refreshBoard(validMoves.get(i), 'Y');
			holder = Math.min(holder, maxValue(state, alpha, beta, depth+1, player)); 
			if (holder<=alpha){ 
				state.removeMove(validMoves.get(i), state.lastRow(validMoves.get(i)));
				return holder;//beta cut-off
			}
			beta = Math.min(holder, beta);
			state.removeMove(validMoves.get(i), state.lastRow(validMoves.get(i)));

		} 
		return holder; 
	}
	private int maxValue(Grid state, int alpha, int beta, int depth, char player) {
		int holder= MINUS_INFINITY;
		List<Integer> validMoves = new ArrayList<Integer>() ;
		state.getValidMoves(validMoves); 
		if(isTerminal(state, depth, validMoves))
			return evaluation(state, player);
		for (int i=0; i<validMoves.size();i++)
		{
			state.refreshBoard(validMoves.get(i), player);
			holder= Math.max(holder,minValue(state,alpha,beta, depth+1, player)); 
			if (holder>=beta){
				state.removeMove(validMoves.get(i), state.lastRow(validMoves.get(i)));
				return holder;//alpha cut-off
			}
			alpha = Math.max(holder,alpha); 
			state.removeMove(validMoves.get(i), state.lastRow(validMoves.get(i)));
		}
		return holder; 
	}
	
	
	private int evaluation(Grid state, char player) { 
		if(isCurrentAgentLost(state, player))
			return MINUS_INFINITY;
		else if(isCurrentAgentWon(state,player)) 
			return POSITIVE_INFINITY;		
		return estimateBoard(state, player); 
	}

	private int estimateBoard(Grid state, char player) {
		List<Integer> checkLines= new ArrayList<Integer>(state.checkLines(player));
		return ((checkLines.get(1) * 9) + (checkLines.get(0) * 2)) - ((checkLines.get(3) * 9) + (checkLines.get(2) * 2));
	}
	private boolean isTerminal(Grid state, int nodeHorizon, List<Integer> validMoves) {
		if (nodeHorizon>=MAX_HORIZON || state.getHasWon() || validMoves.size() == 0)
			return true; 
		return false; 

	}
	private boolean isCurrentAgentLost(Grid state, char player) { 
		if(player == 'Y' && state.hasWon('X'))
			return true; 
			else if(player== 'X' && state.hasWon('Y'))
				return true; 
		return false; 
	}
	private boolean isCurrentAgentWon(Grid state, char player) {
		return(state.hasWon(player));
	}
}
