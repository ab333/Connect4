import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
    private int x_LastTry_C; // these vars. will be used by HasWon.
    private int x_lastTry_R; //^
    private int y_LastTry_C; //^
    private int y_lastTry_R; //^
	private boolean hasWon;
	private final int rows;
	private final int cols;
	private final char[][] GRID;
        public ArrayList<int[]> winningPositions;
	Grid() {
		cols = 7;
		rows = 6;
        winningPositions = new ArrayList<>();
		hasWon = false;
		GRID = new char[7][6];
            for (char[] GRID1 : GRID) {
                for (int j = 0; j < GRID1.length; j++) {
                    GRID1[j] = '_';
                }
            }
	}
	///-----Functions for AI Agent to use-------///
 	public boolean isThereAWin(){
 		return hasWon; 
 	}
	public void getValidMoves(List<Integer> valid)
	{
		for (int i=0; i<cols;i++)
			for(int j=0; j<rows;j++)
				if(GRID[i][j]=='_'){
					valid.add(i);
					break; 
				}
	}
	public int getLastRow(int col)
	{
		for (int i=rows-1; i>-1; i--){
			if(GRID[col][i]!='_')
				return i;
			}
		return 0;
	}

	public void removeMove(int col, int row) {
		if(col<cols && col>=0 && row >=0 && row<rows)
			GRID[col][row]='_';
		if(hasWon)
			hasWon=false;
	}
	////--------END------///// 
	public void drawBoard () {
		for(int n=0;n<7;n++)
			System.out.print(" " + n + " ");
		System.out.println();
		for(int i=5;i>-1;i--) {
			for(int j=0;j<GRID.length;j++) {
				System.out.print(" " + GRID[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println(); 
	}
	public boolean refreshBoard (int col, char disc) {
		if (col > cols-1 || col < 0)
			return false;
		else if(isBoardFull()) 
			return false; 
		for (int i=0;i<6;i++)
			if(GRID[col][i] == '_') {
				GRID[col][i] = disc;
                                if(disc == 'X')
                                {
                                    this.x_LastTry_C = col;
                                    this.x_lastTry_R = i;
                                }
                                else
                                {
                                    this.y_LastTry_C = col;
                                    this.y_lastTry_R = i;
                                }
				this.hasWon(disc);
				return true;
			}
		return false;
	}
	public boolean isBoardFull () {
		for (int i=0;i<cols;i++) {
			if (GRID[i][rows-1] == '_')
				return false;
		}
		return true;
	}
	
	
	public List<Integer> checkLines (char p) {
		List<Integer> returnList = new ArrayList<>();
		List<Integer> listX = new ArrayList<>();
		List<Integer> listO = new ArrayList<>();
		int countX = 0;
		int countO = 0;
		for (int i=0;i<rows;i++) {
			for (int j=0;j<cols;j++) {
				if (GRID[j][i] == p) {
					//count += checkR (3, j, i).size()+checkC (3, j, i).size()+checkDU (3, j, i).size()+checkDD (3, j, i).size();
					listX.addAll(checkR (3, j, i));
					listX.addAll(checkC (3, j, i));
					listX.addAll(checkDU (3, j, i));
					listX.addAll(checkDD (3, j, i));
					countX += checkR (2, j, i).size()+checkC (2, j, i).size()+checkDU (2, j, i).size()+checkDD (2, j, i).size();
				}
				else if (GRID[j][i] == '_')
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
		Set<Integer> uniqueList = new HashSet<>(listX);
		returnList.add(countX);
		returnList.add(uniqueList.size());
		uniqueList = new HashSet<>(listO);
		returnList.add(countO);
		returnList.add(uniqueList.size());
		return returnList;
	}
	
	private List<Integer> checkR (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<>();
		//List<Integer> nullLocations = new ArrayList<Integer>();
		int nullLocation = 99;
		char p = GRID[col][row];
		int countMe = 0;
		int i;

		for(i=col;i<col+n && i<cols;i++) {
			if (GRID[i][row] == p)
				countMe++;
			else if (GRID[i][row] == '_') 
				nullLocation = (i*10)+(row);
			else 
				return returnList;//return null;
		}
		if ((countMe == n && col-1>-1 && GRID[col-1][row] == '_') || (countMe == n && i<cols && GRID[i][row] == '_')) {
			// two-sided 3s check (_XXX_)
			if(n == 3 && col-1>-1 && GRID[col-1][row] == '_')
				returnList.add((col-1)*10+(row));//return 1;
			if(n == 3 && i<cols && GRID[i][row] == '_')
				returnList.add((i*10)+(row));
			if(n == 2) {
				// useless 2s check (OXX_O)
				int usableTest = 0;
				if(col-1>-1 &&( GRID[col-1][row] == p))
					return returnList;
				if(col-1>-1 &&( GRID[col-1][row] == '_'))// || grid[col-1][row] == p ))
					usableTest += 10;
				if(col-2>-1 &&( GRID[col-2][row] == '_'))// || grid[col-2][row] == p))
					usableTest += 2;
				if(i<cols &&( GRID[i][row] == p))
					return returnList;
				if(i<cols &&( GRID[i][row] == '_'))// || grid[i][row] == p))
					usableTest += 20;
				if(i+1<cols &&( GRID[i+1][row] == '_'))// || grid[i+1][row] == p))
					usableTest += 4;
				if(usableTest > 23 || usableTest == 12 || usableTest == 16)
					returnList.add((col*10)+(row));
			}
		}
		// disjoint 3s check (XX_X)
		else if (countMe == 2 && n == 3 && i<cols && GRID[i][row] == p)
			returnList.add(nullLocation);//return 1;
		else
			;//return 0;
		return returnList;
	}
	
	
	private List<Integer> checkC (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<>();
		char p = GRID[col][row];
		int countMe = 0;
		int i;
		if (row > 2) // TODO check 2 or 1?
			return returnList;
		for(i=row;i<row+n && i<rows;i++) {
			if (GRID[col][i] == p)
				countMe++;
			else if (GRID[col][i] == '_')
				;
			else
				return returnList;
		}
		if ((countMe == n && i<rows && GRID[col][i] == '_')) {
			if (n == 3)
				returnList.add((col*10)+(i));
			if (n == 2)
				if (row-1>-1 && GRID[col][row-1] == p)
					return returnList;
				else
					returnList.add((col*10)+(row));
		}
		return returnList;
	}
	
	
	
	private List<Integer> checkDU (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<>();
		char p = GRID[col][row];
		int countMe = 0;
		int nullLocation = 99;
		int i;
		int j;
		for(i=col,j=row;i<col+n && i<cols && j<row+n && j<rows;i++,j++) {
			if (GRID[i][j] == p)
				countMe++;
			else if (GRID[i][j] == '_') 
				nullLocation = (i*10)+(j);
			else
				return returnList;
		}
		if ((countMe == n && col-1>-1 && row-1>-1 && GRID[col-1][row-1] == '_') || (countMe == n && i<cols && j<rows && GRID[i][j] == '_')) {
			// two-sided 3s check (_XXX_)
			if(n == 3 && col-1>-1 && row-1>-1 && GRID[col-1][row-1] == '_')
				returnList.add((col-1)*10+(row-1));//return 1;
			if(n == 3 && i<cols && j<rows && GRID[i][j] == '_')
				returnList.add((i*10)+(j));
			if(n == 2) {
				// useless 2s check (OXX_O)
				int usableTest = 0;
				if(col-1>-1 && row-1>-1 &&( GRID[col-1][row-1] == p))
					return returnList;
				if(col-1>-1 && row-1>-1 &&( GRID[col-1][row-1] == '_'))// || grid[col-1][row-1] == p))
					usableTest += 10;
				if(col-2>-1 && row-2>-1 &&( GRID[col-2][row-2] == '_'))// || grid[col-2][row-2] == p))
					usableTest += 2;
				if(i<cols && j<rows &&( GRID[i][j] == p))
					return returnList;
				if(i<cols && j<rows &&( GRID[i][j] == '_'))// || grid[i][j] == p))
					usableTest += 20;
				if(i+1<cols && j+1<rows &&( GRID[i+1][j+1] == '_'))// || grid[i+1][j+1] == p))
					usableTest += 4;
				if(usableTest > 23 || usableTest == 12 || usableTest == 16)
					returnList.add((col*10)+(row));
			}
		}
		// disjoint 3s check (XX_X)
		else if (countMe == 2 && n == 3 && i<cols && j<rows && GRID[i][j] == p)
			returnList.add(nullLocation);//return 1;
		else 
			;
		return returnList;
	}
	
	
	
	private List<Integer> checkDD (int n, int col, int row) {
		List<Integer> returnList = new ArrayList<>();
		char p = GRID[col][row];
		int countMe = 0;
		int i;
		int j;
		int nullLocation = 99;
		for(i=col,j=row;i<col+n && i<cols && j>row-n && j>-1;i++,j--) {
			if (GRID[i][j] == p)
				countMe++;
			else if (GRID[i][j] == '_') 
				nullLocation = (i*10)+(j);
			else
				return returnList;
		}
		if ((countMe == n && col-1>-1 && row+1<rows && GRID[col-1][row+1] == '_') || (countMe == n && i<cols && j>-1 && GRID[i][j] == '_')) {
			// two-sided 3s check (_XXX_)
			if(n == 3 && col-1>-1 && row+1<rows && GRID[col-1][row+1] == '_')
				returnList.add((col-1)*10+(row+1));//return 1;
			if(n == 3 && i<cols && j>-1 && GRID[i][j] == '_')
				returnList.add((i*10)+(j));
			if(n == 2) {
				// useless 2s check (OXX_O)
				int usableTest = 0;
				if(col-1>-1 && row+1<rows &&( GRID[col-1][row+1] == p))
					return returnList;
				if(col-1>-1 && row+1<rows &&( GRID[col-1][row+1] == '_'))// || grid[col-1][row+1] == p))
					usableTest += 10;
				if(col-2>-1 && row+2<rows &&( GRID[col-2][row+2] == '_'))// || grid[col-2][row+2] == p))
					usableTest += 2;
				if(i<cols && j>-1 &&( GRID[i][j] == p))
					return returnList;
				if(i<cols && j>-1 &&( GRID[i][j] == '_'))// || grid[i][j] == p))
					usableTest += 20;
				if(i+1<cols && j-1>-1 &&( GRID[i+1][j-1] == '_'))// || grid[i+1][j-1] == p))
					usableTest += 4;
				if(usableTest > 23 || usableTest == 12 || usableTest == 16)
					returnList.add((col*10)+(row));
			}
		}
		// disjoint 3s check (XX_X)
		else if (countMe == 2 && n == 3 && i<cols && j>-1 && GRID[i][j] == p)
			returnList.add(nullLocation);//return 1;
		else 
			;
		return returnList;
	}
	

        private boolean diagonalNE(char player, int col,int row)
	{
		boolean hasWonFlag = false;
		for(int i=col,j=row; i<this.cols && j<this.rows ;i++,j++)
		{
                    int sequersCount = 0;
                    while(GRID[i][j] == player)
                        {
                            int arr[] = new int[2];
                            arr[0] = i;
                            arr[1] = j;
                            winningPositions.add(arr);
                            sequersCount++;
                            i++;j++;
                            if(sequersCount == 4)
                            {
                               hasWonFlag = true;
                                break;
                            }
                            
                            if(i>=this.cols || j>=this.rows)
                            {break;}
                        }
                    if(sequersCount == 4)
                        break;
                    winningPositions.clear();
		}
		return hasWonFlag;
		
	}
        
        private boolean diagonalSE(char player, int col,int row)
	{
		boolean hasWonFlag = false;
		for(int i=col,j=row; i<this.cols && j>-1 ;i++,j--)
		{
                    int sequersCount = 0;
                    while(GRID[i][j] == player)
                        {
                            int arr[] = new int[2];
                            arr[0] = i;
                            arr[1] = j;
                            winningPositions.add(arr);
                            sequersCount++;
                            i++;j--;
                            if(sequersCount == 4)
                            {
                               hasWonFlag = true;
                                break;
                            }
                            if(i>=this.cols || j<0)
                            {break;}
                        }
                    if(sequersCount == 4)
                        break;
                    winningPositions.clear();
                    
		}
		return hasWonFlag;
		
	}
        
        private boolean vertical(char player, int col,int row)
	{
            //winningPositions.clear();
		boolean hasWinFlag = true;
		for(int i = row ; i>row-4 ;i--)
		{
                        int arr[] = new int[2];
                        arr[0] = col;
                        arr[1] = i;
                        winningPositions.add(arr);
                        if(GRID[col][i]!=player)
                        {
                                winningPositions.clear();
				hasWinFlag = false;
				break;
			}
		}
		return hasWinFlag;
		
	}
        
        private boolean horizontal(char player, int col,int row)
	{
		boolean hasWonFlag = false;
		for(int i = col ; i< this.cols; i++)
		{
                    int sequersCount = 0;
                    while(GRID[i][row] == player)
                        {
                            int arr[] = new int[2];
                            arr[0] = i;
                            arr[1] = row;
                            winningPositions.add(arr);
                            sequersCount++;
                            i++;
                            if(sequersCount == 4)
                            {
                               hasWonFlag = true;
                                break;
                            }
                            if(i>=this.cols)
                            {break;}
                        }
                    if(sequersCount == 4)
                        break;
                    winningPositions.clear();
		}
 
		return hasWonFlag;
		
	}
        
        public boolean hasWon(char player)
        {
            int startPointDSE = 0;
            int startPointDNE = 0;
                
            if(player == 'X')
            {
                startPointDSE = this.x_LastTry_C + this.x_lastTry_R;
                startPointDNE = this.x_LastTry_C - this.x_lastTry_R;
                if(this.x_lastTry_R >=3)
                {
                    if(this.vertical(player, this.x_LastTry_C, this.x_lastTry_R))
                    {
                        this.hasWon = true;
			return hasWon;
                    }
                }
                if(this.horizontal(player, 0, this.x_lastTry_R))
                {
                        this.hasWon = true;
                        return hasWon; 
                } 
            }
            else
            {
                startPointDSE = this.y_LastTry_C + this.y_lastTry_R;
                startPointDNE = this.y_LastTry_C - this.y_lastTry_R;
                if(this.y_lastTry_R >=3)
                {
                    if(this.vertical(player, this.y_LastTry_C, this.y_lastTry_R))
                    {
                        this.hasWon = true;
			return hasWon;
                    }
                }
                if(this.horizontal(player, 0, this.y_lastTry_R))
                {
                        this.hasWon = true;
                        return hasWon; 
                }
            }
            
            /////////////////////////////
            
            if(startPointDNE == 0)
                {
                    if(this.diagonalNE(player, startPointDNE, startPointDNE))
                    {
                        this.hasWon = true;
                        return hasWon; 
                    }
                }
            else if(startPointDNE<0 && startPointDNE > -3)
                {
                    if(this.diagonalNE(player, 0, Math.abs(startPointDNE)))
                    {
                        this.hasWon = true;
                        return hasWon; 
                    }
                }
            else if(startPointDNE>0 && startPointDNE<4)
                {
                    if(this.diagonalNE(player, startPointDNE, 0))
                    {
                        this.hasWon = true;
                        return hasWon; 
                    }
                }
            ////////////////////////////////////
            
            if(startPointDSE >= 3 && startPointDSE<=5)
            {
                if(this.diagonalSE(player, 0, startPointDSE))
                {
                        this.hasWon = true;
                        return hasWon;   
                }
            }
            else if(startPointDSE >=6 && startPointDSE<=8)
            {
                if(this.diagonalSE(player, startPointDSE-5, 5))
                {
                        this.hasWon = true;
                        return hasWon;   
                }
            }
            ////////////////////////
            return false;
        }
        
        
        public int getX_lastTry_R() {
            return x_lastTry_R;
        }
        public int getY_lastTry_R() {
            return y_lastTry_R;
        }
        public int getX_LastTry_C() {
            return x_LastTry_C;
        }

        public int getY_LastTry_C() {
            return y_LastTry_C;
        }
       
	}
