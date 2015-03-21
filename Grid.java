public class Grid {
	public Grid() {
		cols = 7;
		rows = 6;
		grid = new char[7][6];
		for( int i = 0 ; i < grid.length ; i++ ) { 
	           for ( int j = 0 ; j < grid[i].length ; j++ ) { 
	              grid[i][j] = '_';
	           }
		}
	}
		//grid[1][1] = 5;
	
	public void drawBoard () {
		for(int n=0;n<7;n++)
			System.out.print(" " + n + " ");
		System.out.println();
		for(int i=5;i>-1;i--) {
			for(int j=0;j<grid.length;j++) {
				System.out.print(" " + grid[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public boolean refreshBoard (int col, char disc) {
		if (col > cols-1 || col < 0)
			return false;
		else if(boardFull()) // when called outside Startplaying. must check before play. 
			return false; 
		for (int i=0;i<6;i++)
			if(grid[col][i] == '_') {
				grid[col][i] = disc;
				return true;
			}
		return false;
	}
	public boolean boardFull () {
		for (int i=0;i<cols;i++) {
			if (grid[i][rows-1] == '_')
				return false;
		}
		return true;
	}
	public int check3s (char player) {
		int count = 0;
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				if (grid[j][i] == player) {
					count += checkR (3, j, i)+checkC (3, j, i)+checkDU (3, j, i)+checkDD (3, j, i);
				}
			}
		}
		return count;
	}
	public int check2s (char player) {
		int count = 0;
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				if (grid[j][i] == player) {
					count += checkR (2, j, i)+checkC (2, j, i)+checkDU (2, j, i)+checkDD (2, j, i);
				}
			}
		}
		return count;
	}
	int checkR (int n, int col, int row) {
		char p = grid[col][row];
		int countMe = 0;
		for(int i=col;i<col+n && i<cols;i++) {
			if (grid[i][row] == p)
				countMe++;
		}
		if (countMe == n)
			return 1;
		else 
			return 0;
	}
	int checkC (int n, int col, int row) {
		char p = grid[col][row];
		int countMe = 0;
		for(int i=row;i<row+n && i<rows;i++) {
			if (grid[col][i] == p)
				countMe++;
		}
		if (countMe == n)
			return 1;
		else 
			return 0;
	}
	int checkDU (int n, int col, int row) {
		char p = grid[col][row];
		int countMe = 0;
		for(int i=col,j=row;i<col+n && i<cols && j<row+n && j<rows;i++,j++) {
			if (grid[i][j] == p)
				countMe++;
		}
		if (countMe == n)
			return 1;
		else 
			return 0;
	}
	
	int checkDD (int n, int col, int row) {
		char p = grid[col][row];
		int countMe = 0;
		for(int i=col,j=row;i<col+n && i<cols && j>row-n && j>-1;i++,j--) {
			if (grid[i][j] == p)
				countMe++;
		}
		if (countMe == n)
			return 1;
		else 
			return 0;
	}
	private int rows;
	private int cols;
	private char[][] grid;// = new int[6][5];
	}
