
public class Connect4 {
	public static void main(String[] args) {
		Grid g = new Grid();
		//g.grid[1][1] = 5;
		
	g.drawBoard();
	g.refreshBoard(1,'X');
	g.refreshBoard(1,'O');
	g.refreshBoard(6,'X');
	g.drawBoard();
		//System.out.print(g.grid[1][1]);
	}
}
