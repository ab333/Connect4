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
	
	void drawBoard () {
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
	boolean refreshBoard (int col, char disc) {
		if (col > cols-1 || col < 0)
			return false;
		for (int i=0;i<6;i++)
			if(grid[col][i] == '_') {
				grid[col][i] = disc;
				return true;
			}
		return false;
	}
	boolean boardFull () {
		for (int i=0;i<cols;i++) {
			if (grid[i][rows-1] == '_')
				return false;
		}
		return true;
	}
	public int rows;
	public int cols;
	public char[][] grid;// = new int[6][5];
	}
