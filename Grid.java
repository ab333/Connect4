import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
	private class NConnectedSequers
	{
		public boolean isNotNull = false;// this var. will be true only if there is n Connected Sequers
		public ArrayList<int[]> sequersPostion = new ArrayList<>();
		
	}
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
	///-----Functions for AI Agent to use-------/// 
	public void validMoves(List<Integer> valid)
	{
		for (int i=0; i<cols;i++)
			for(int j=0; j<rows;j++)
				if(grid[i][j]=='_'){
					valid.add(i);
					break; 
				}
	}
	public int lastRow(int col)
	{
		for (int i=rows-1; i>-1; i--){
			if(grid[col][i]!='_')
				return i;
			}
		return 0;
	}

	public void removeMove(int col, int row) {
		if(col<cols && col>=0 && row >=0 && row<rows)
			grid[col][row]='_';
		if(hasWon)
			hasWon=false;
	}
	////--------END------///// 
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
	
	
	public List<Integer> checkLines (char p) {
		List<Integer> returnList = new ArrayList<Integer>();
		List<Integer> listX = new ArrayList<Integer>();
		List<Integer> listO = new ArrayList<Integer>();
		int countX = 0;
		int countO = 0;
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				if (grid[j][i] == p) {
					//count += checkR (3, j, i).size()+checkC (3, j, i).size()+checkDU (3, j, i).size()+checkDD (3, j, i).size();
					listX.addAll(checkR (3, j, i));
					listX.addAll(checkC (3, j, i));
					listX.addAll(checkDU (3, j, i));
					listX.addAll(checkDD (3, j, i));
					countX += checkR (2, j, i).size()+checkC (2, j, i).size()+checkDU (2, j, i).size()+checkDD (2, j, i).size();
				}
				else if (grid[j][i] == '_')
					;
				else {
					//count += checkR (3, j, i).size()+checkC (3, j, i).size()+checkDU (3, j, i).size()+checkDD (3, j, i).size();
					listO.addAll(checkR (3, j, i));
					listO.addAll(checkC (3, j, i));
					listO.addAll(checkDU (3, j, i));
					listO.addAll(checkDD (3, j, i));
					countO += checkR (2, j, i).size()+checkC (2, j, i).size()+checkDU (2, j, i).size()+checkDD (2, j, i).size();
				}
			}
		}
		Set<Integer> uniqueList = new HashSet<Integer>(listX);
		returnList.add(countX);
		returnList.add(uniqueList.size());
		uniqueList = new HashSet<Integer>(listO);
		returnList.add(countO);
		returnList.add(uniqueList.size());
		return returnList;
	}
	
	private List<Integer> checkR (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<Integer>();
		//List<Integer> nullLocations = new ArrayList<Integer>();
		int nullLocation = 99;
		char p = grid[col][row];
		int countMe = 0;
		int i;

		for(i=col;i<col+n && i<cols;i++) {
			if (grid[i][row] == p)
				countMe++;
			else if (grid[i][row] == '_') 
				nullLocation = (i*10)+(row);
			else 
				return returnList;//return null;
		}
		if ((countMe == n && col-1>-1 && grid[col-1][row] == '_') || (countMe == n && i<cols && grid[i][row] == '_')) {
			// two-sided 3s check (_XXX_)
			if(n == 3 && col-1>-1 && grid[col-1][row] == '_')
				returnList.add((col-1)*10+(row));//return 1;
			if(n == 3 && i<cols && grid[i][row] == '_')
				returnList.add((i*10)+(row));
			if(n == 2) {
				// useless 2s check (OXX_O)
				int usableTest = 0;
				if(col-1>-1 &&( grid[col-1][row] == p))
					return returnList;
				if(col-1>-1 &&( grid[col-1][row] == '_'))// || grid[col-1][row] == p ))
					usableTest += 10;
				if(col-2>-1 &&( grid[col-2][row] == '_'))// || grid[col-2][row] == p))
					usableTest += 2;
				if(i<cols &&( grid[i][row] == p))
					return returnList;
				if(i<cols &&( grid[i][row] == '_'))// || grid[i][row] == p))
					usableTest += 20;
				if(i+1<cols &&( grid[i+1][row] == '_'))// || grid[i+1][row] == p))
					usableTest += 4;
				if(usableTest > 23 || usableTest == 12 || usableTest == 16)
					returnList.add((col*10)+(row));
			}
		}
		// disjoint 3s check (XX_X)
		else if (countMe == 2 && n == 3 && i<cols && grid[i][row] == p)
			returnList.add(nullLocation);//return 1;
		else
			;//return 0;
		return returnList;
	}
	
	private NConnectedSequers checkRWhitPostions (int n, int col, int row) {
		NConnectedSequers connectedS = new NConnectedSequers();
		char p = grid[col][row];
		int countMe = 0;
		for(int i=col;i<col+n && i<cols;i++) {
			if (grid[i][row] == p)
				countMe++;
			int arr[] = new int[2];
			arr[0] = i;
			arr[1] = row;
			connectedS.sequersPostion.add(arr);
			}
		if (countMe == n){
			connectedS.isNotNull = true;
			return connectedS;
			}
		else 
			return connectedS;
	}
	
	private List<Integer> checkC (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<Integer>();
		char p = grid[col][row];
		int countMe = 0;
		int i;
		if (row > 2) // TODO check 2 or 1?
			return returnList;
		for(i=row;i<row+n && i<rows;i++) {
			if (grid[col][i] == p)
				countMe++;
			else if (grid[col][i] == '_')
				;
			else
				return returnList;
		}
		if ((countMe == n && i<rows && grid[col][i] == '_')) {
			if (n == 3)
				returnList.add((col*10)+(i));
			if (n == 2)
				if (row-1>-1 && grid[col][row-1] == p)
					return returnList;
				else
					returnList.add((col*10)+(row));
		}
		return returnList;
	}
	
	
	private NConnectedSequers checkCWhitPostions (int n, int col, int row) {
		NConnectedSequers connectedS = new NConnectedSequers();
		
		char p = grid[col][row];
		int countMe = 0;
		for(int i=row;i<row+n && i<rows;i++) {
			if (grid[col][i] == p)
				countMe++;
			int arr[] = new int[2];
			arr[0] = col;
			arr[1] = i;
			connectedS.sequersPostion.add(arr);}
		if (countMe == n){
			connectedS.isNotNull = true;
			return connectedS;
			}
		else 
			return connectedS;
	}
	
	
	private List<Integer> checkDU (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<Integer>();
		char p = grid[col][row];
		int countMe = 0;
		int nullLocation = 99;
		int i;
		int j;
		for(i=col,j=row;i<col+n && i<cols && j<row+n && j<rows;i++,j++) {
			if (grid[i][j] == p)
				countMe++;
			else if (grid[i][j] == '_') 
				nullLocation = (i*10)+(j);
			else
				return returnList;
		}
		if ((countMe == n && col-1>-1 && row-1>-1 && grid[col-1][row-1] == '_') || (countMe == n && i<cols && j<rows && grid[i][j] == '_')) {
			// two-sided 3s check (_XXX_)
			if(n == 3 && col-1>-1 && row-1>-1 && grid[col-1][row-1] == '_')
				returnList.add((col-1)*10+(row-1));//return 1;
			if(n == 3 && i<cols && j<rows && grid[i][j] == '_')
				returnList.add((i*10)+(j));
			if(n == 2) {
				// useless 2s check (OXX_O)
				int usableTest = 0;
				if(col-1>-1 && row-1>-1 &&( grid[col-1][row-1] == p))
					return returnList;
				if(col-1>-1 && row-1>-1 &&( grid[col-1][row-1] == '_'))// || grid[col-1][row-1] == p))
					usableTest += 10;
				if(col-2>-1 && row-2>-1 &&( grid[col-2][row-2] == '_'))// || grid[col-2][row-2] == p))
					usableTest += 2;
				if(i<cols && j<rows &&( grid[i][j] == p))
					return returnList;
				if(i<cols && j<rows &&( grid[i][j] == '_'))// || grid[i][j] == p))
					usableTest += 20;
				if(i+1<cols && j+1<rows &&( grid[i+1][j+1] == '_'))// || grid[i+1][j+1] == p))
					usableTest += 4;
				if(usableTest > 23 || usableTest == 12 || usableTest == 16)
					returnList.add((col*10)+(row));
			}
		}
		// disjoint 3s check (XX_X)
		else if (countMe == 2 && n == 3 && i<cols && j<rows && grid[i][j] == p)
			returnList.add(nullLocation);//return 1;
		else 
			;
		return returnList;
	}
	
	
	private NConnectedSequers checkDUWhitPostions (int n, int col, int row) {
		NConnectedSequers connectedS = new NConnectedSequers();
		char p = grid[col][row];
		int countMe = 0;
		for(int i=col,j=row;i<col+n && i<cols && j<row+n && j<rows;i++,j++) {
			if (grid[i][j] == p)
				countMe++;
			int arr[] = new int[2];
			arr[0] = i;
			arr[1] = j;
			connectedS.sequersPostion.add(arr);}
		if (countMe== n){
			connectedS.isNotNull = true;
			return connectedS;
			}
		else 
			return connectedS;
	}
	
	private List<Integer> checkDD (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<Integer>();
		char p = grid[col][row];
		int countMe = 0;
		int i;
		int j;
		int nullLocation = 99;
		for(i=col,j=row;i<col+n && i<cols && j>row-n && j>-1;i++,j--) {
			if (grid[i][j] == p)
				countMe++;
			else if (grid[i][j] == '_') 
				nullLocation = (i*10)+(j);
			else
				return returnList;
		}
		if ((countMe == n && col-1>-1 && row+1<rows && grid[col-1][row+1] == '_') || (countMe == n && i<cols && j>-1 && grid[i][j] == '_')) {
			// two-sided 3s check (_XXX_)
			if(n == 3 && col-1>-1 && row+1<rows && grid[col-1][row+1] == '_')
				returnList.add((col-1)*10+(row+1));//return 1;
			if(n == 3 && i<cols && j>-1 && grid[i][j] == '_')
				returnList.add((i*10)+(j));
			if(n == 2) {
				// useless 2s check (OXX_O)
				int usableTest = 0;
				if(col-1>-1 && row+1<rows &&( grid[col-1][row+1] == p))
					return returnList;
				if(col-1>-1 && row+1<rows &&( grid[col-1][row+1] == '_'))// || grid[col-1][row+1] == p))
					usableTest += 10;
				if(col-2>-1 && row+2<rows &&( grid[col-2][row+2] == '_'))// || grid[col-2][row+2] == p))
					usableTest += 2;
				if(i<cols && j>-1 &&( grid[i][j] == p))
					return returnList;
				if(i<cols && j>-1 &&( grid[i][j] == '_'))// || grid[i][j] == p))
					usableTest += 20;
				if(i+1<cols && j-1>-1 &&( grid[i+1][j-1] == '_'))// || grid[i+1][j-1] == p))
					usableTest += 4;
				if(usableTest > 23 || usableTest == 12 || usableTest == 16)
					returnList.add((col*10)+(row));
			}
		}
		// disjoint 3s check (XX_X)
		else if (countMe == 2 && n == 3 && i<cols && j>-1 && grid[i][j] == p)
			returnList.add(nullLocation);//return 1;
		else 
			;
		return returnList;
	}
	
	
	private NConnectedSequers checkDDWhitPostions (int n, int col, int row) {
		NConnectedSequers connectedS = new NConnectedSequers();
		char p = grid[col][row];
		int countMe = 0;
		for(int i=col,j=row;i<col+n && i<cols && j>row-n && j>-1;i++,j--) {
			if (grid[i][j] == p)
				countMe++;
			int arr[] = new int[2];
			arr[0] = i;
			arr[1] = j;
			connectedS.sequersPostion.add(arr);
			}
		if (countMe == n){
			connectedS.isNotNull = true;
			return connectedS;
			}
		else 
			return connectedS;
	}

	public boolean hasWon(char player) { // We can think about this fun. as getter function
			for (int i=0;i<rows;i++) {
				for (int j=0;j<cols;j++) {
					if (grid[j][i] == player) {
						if( this.checkRWhitPostions(4, j, i).isNotNull
						|| this.checkCWhitPostions (4, j, i).isNotNull
						|| this.checkDUWhitPostions (4, j, i).isNotNull
						|| this.checkDDWhitPostions (4, j, i).isNotNull)
						{
//                                                    for(int r=0;r<4;r++)
//                                                    {
//                                                        System.out.print(this.checkCWhitPostions (4, j, i).sequersPostion.get(r)[0]+""+""+this.checkCWhitPostions (4, j, i).sequersPostion.get(r)[1]);
//                                                    }
							this.hasWon = true;
						}
					}
				}
			}
	 	return this.hasWon;
	}

	private boolean hasWon;
	private int rows;
	private int cols;
	private char[][] grid;// = new int[6][5];
	}
