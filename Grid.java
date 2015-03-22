package connect4;


public class Grid {
	public Grid() {
		cols = 7;
		rows = 6;
		hasWon = false;
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
				this.setHasWin(col, i);
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
	void setHasWin(int col,int row)
	{
		if(col==3 && row>2)
		{
			if(this.horizontalLeft(col, row) || this.horizontalRight(col, row) || this.verticalDown(col, row) || this.diagonalSE(col, row) || this.diagonalSW(col, row))
			{
				this.hasWon = true;
			}
		}
		else if(col== 3 && row<3)
		{
			if(this.horizontalLeft(col, row) || this.horizontalRight(col, row) || this.diagonalNE(col, row) || this.diagonalNW(col, row))
			{
				this.hasWon = true;
			}
		}
		else if(col<3 && row>2)
		{
			if(this.verticalDown(col, row) || this.horizontalRight(col, row) || this.diagonalSE(col, row))
				this.hasWon = true;
		}
		else if(col>3 && row>2)
		{
			if(this.verticalDown(col, row) || this.horizontalLeft(col, row) || this.diagonalSW(col, row))
			{
				this.hasWon = true;
			}
		}
		else if(col<3 && row<3)
		{
			if(this.horizontalRight(col, row) || this.diagonalNE(col, row))
			{
				this.hasWon = true;
			}
		}
		else if(col>3 && row<3)
		{
			if(this.horizontalLeft(col, row) || this.diagonalNW(col, row))
			{
				this.hasWon = true;
			}
		}
		else
		{
			System.exit(1);
		}
		
	}
	

	boolean horizontalRight(int col,int row)
	{
		boolean hasWinFlag = true;
		for(int i = col+1 ; i< col+4; i++)
		{
			if(grid[i][row]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		} 
		return hasWinFlag;
		
	}
	boolean horizontalLeft(int col,int row)
	{
		boolean hasWinFlag = true;
		for(int i = col-1 ; i> col-4; i--)
		{					//2
			if(grid[i][row]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		}
 
		return hasWinFlag;
		
	}
	boolean verticalUp(int col,int row)
	{
		boolean hasWinFlag = true;
		for(int i = row+1 ; i<row+4 ;i++)
		{
			if(grid[col][i]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		}
		return hasWinFlag;
		
	}
	boolean verticalDown(int col,int row)
	{
		boolean hasWinFlag = true;
		for(int i = row-1 ; i>row-4 ;i--)
		{ 
			if(grid[col][i]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		}
		return hasWinFlag;
		
	}
	boolean diagonalNE(int col,int row)
	{
		int localCol =col;
		int localRow = row;
		boolean hasWinFlag = true;
		for(int i = 0 ; i<3 ;i++)
		{
			localCol++;
			localRow++;
			if(grid[localCol][localRow]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		}
		return hasWinFlag;
		
	}
	boolean diagonalNW(int col,int row)
	{
		int localCol =col;
		int localRow = row;
		boolean hasWinFlag = true;
		for(int i = 0 ; i<3 ;i++)
		{
			localCol--;
			localRow++;
			if(grid[localCol][localRow]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		}
		return hasWinFlag;
		
	}

	boolean diagonalSE(int col,int row)
	{
		int localCol =col;
		int localRow = row;
		boolean hasWinFlag = true;
		for(int i = 0 ; i<3 ;i++)
		{
			localCol++;
			localRow--;
			if(grid[localCol][localRow]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		} 
		return hasWinFlag;	
	}
	boolean diagonalSW(int col,int row)
	{
		int localCol =col;
		int localRow = row;
		boolean hasWinFlag = true;
		for(int i = 0 ; i<3 ;i++)
		{
			localCol--;
			localRow--;
			if(grid[localCol][localRow]!=grid[col][row])
			{
				hasWinFlag = false;
				break;
			}
		}
		return hasWinFlag;
		
	}
	
	public void hasWon(char player) { // We can think about this fun. as getter function
	 	if(hasWon) {
	 		System.out.println("Player " + player + " has won.\n");
	 		drawBoard();
	 		System.exit(1);
	 	}
	}

	private boolean hasWon;
	private int rows;
	private int cols;
	private char[][] grid;// = new int[6][5];
	}
