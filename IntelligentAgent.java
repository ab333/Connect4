
import java.util.ArrayList;
import java.util.List;

//import java.util.*;// no need for * !!
 
public class IntelligentAgent extends Player {
	private final int MAX_HORIZON = 8; 
	private final int POSITIVE_INFINITY = Integer.MAX_VALUE; 
	private final int MINUS_INFINITY = -Integer.MAX_VALUE;
	private char firstPlayer; 
	private char opponent; 
	private Grid state; 
	IntelligentAgent() { 
		this.intelligenceMode = true; 
	}
        @Override
	public char intelligence(Grid board, char player) {
        state = board; 
        playMove(player);
		return (player == 'Y')? 'X': 'Y'; 
	}
	
	private void playMove(char player) {
		state.refreshBoard(alphaBetaSearch(player), player);
			
		}
	protected int alphaBetaSearch (char player) {
		firstPlayer  = player; 
		opponent = (firstPlayer=='X')?'Y':'X';
		return getBestMove();
	}

	private int getBestMove() {
		Integer [] childsValue;
		int temp = MINUS_INFINITY;
		int bestMoveValue = temp;
		List<Integer> childrens = new ArrayList<>() ;
		createChildrens(childrens);
		childsValue = new Integer[childrens.size()];
		for (int i = 0; i<childrens.size(); i++){
			goToChild(childrens.get(i), firstPlayer);
			temp = minValue(MINUS_INFINITY,POSITIVE_INFINITY, 0); 
			childsValue[i] = temp;
			bestMoveValue = getMaxChildValue(bestMoveValue, temp);
			goBackToParent(childrens.get(i));
			if(thereIsAWinMove(bestMoveValue))
				break; 
			}
		int bestMoveIndex = java.util.Arrays.asList(childsValue).indexOf(bestMoveValue);
		return childrens.get(bestMoveIndex); 
	}

	private void createChildrens(List<Integer> childrens) {
		state.getValidMoves(childrens);
		
	}
	
	private void goToChild(int action, char player) {
		state.refreshBoard(action, player);
		
	}
	
	private int minValue (int alpha, int beta, int depth) {
		int holder = POSITIVE_INFINITY;
		List<Integer> childrens = new ArrayList<>() ;
		createChildrens(childrens); 
		if(isTerminal(depth))
			return evaluation();
	        for (Integer children : childrens) {
	            goToChild(children, opponent);
	            holder = Math.min(holder, maxValue(alpha, beta, depth+1));
	            if (isBetaCutOff(holder,alpha)) {
	                goBackToParent(children);
	                break;
	            }
			beta = Math.min(holder, beta);
			goBackToParent(children); 
		} 
		return holder; 
	}
	
	private boolean isTerminal(int nodeHorizon) {
		return (nodeHorizon>=MAX_HORIZON || state.isThereAWin() || state.isBoardFull());
	}

	private int evaluation() { 
		if(isCurrentAgentLost())
			return MINUS_INFINITY;
		else if(isCurrentAgentWon()) 
			return POSITIVE_INFINITY;		
		return estimateBoardToPlayer(); 
	}

	private boolean isCurrentAgentLost() { 
		return state.hasWon(opponent);
	}
	private boolean isCurrentAgentWon() {
		return state.hasWon(firstPlayer);
	}
	
	private int estimateBoardToPlayer() {
		List<Integer> linesCombination = new ArrayList<>(state.checkLines(firstPlayer));
		return ((linesCombination.get(1) * 9) + (linesCombination.get(0) * 2)) - ((linesCombination.get(3) * 9) + (linesCombination.get(2) * 2));
	}

	private int maxValue(int alpha, int beta, int depth) {
		int holder = MINUS_INFINITY;
		List<Integer> childrens = new ArrayList<>() ;
		state.getValidMoves(childrens); 
		if(isTerminal(depth))
			return evaluation();
	        for (Integer children : childrens) {
	            this.goToChild(children, firstPlayer);
	            holder = Math.max(holder, minValue(alpha,beta, depth+1));
	            if (isAlphaCuttOff(holder,beta)) {
	                goBackToParent(children);
	                break;
	            }
	            alpha = Math.max(holder,alpha);
	            this.goBackToParent(children);
	        }
		return holder; 
	}
	
	private boolean isAlphaCuttOff(int holder, int beta) {
		return holder >= beta; 
	}
	private boolean isBetaCutOff(int holder, int alpha) {
		return holder <= alpha;
	}

	private void goBackToParent(int action) {
		state.removeMove(action, state.getLastRow(action));
		
	}
	
	private int getMaxChildValue(int bestMoveValue, int temp) {
			return Math.max(bestMoveValue, temp);  
	}
	
	private boolean thereIsAWinMove(int bestMoveValue) {
		return (bestMoveValue==POSITIVE_INFINITY);
	}
	

}
