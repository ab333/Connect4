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
    private final char[][] grid;
    List<int[]> winningPositions;

    Grid() {
        cols = 7;
        rows = 6;
        winningPositions = new ArrayList<>();
        hasWon = false;
        grid = new char[7][6];
        for (char[] gridTemp : grid) {
            for (int j = 0; j < gridTemp.length; j++) {
                gridTemp[j] = '_';
            }
        }
    }

    ///-----Functions for AI Agent to use-------///
    boolean isThereAWin() {
        return hasWon;
    }

    void getValidMoves(List<Integer> valid) {
        for (int i = 0; i < cols; i++)
            for (int j = 0; j < rows; j++)
                if (grid[i][j] == '_') {
                    valid.add(i);
                    break;
                }
    }

    int getLastRow(int col) {
        for (int i = rows - 1; i > -1; i--) {
            if (grid[col][i] != '_')
                return i;
        }
        return 0;
    }

    void removeMove(int col, int row) {
        if (col < cols && col >= 0 && row >= 0 && row < rows)
            grid[col][row] = '_';
        if (hasWon)
            hasWon = false;
    }

    ////--------END------/////
    void drawBoard() {
        for (int n = 0; n < 7; n++)
            System.out.print(" " + n + " ");
        System.out.println();
        for (int i = 5; i > -1; i--) {
            for (int j = 0; j < grid.length; j++) {
                System.out.print(" " + grid[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    boolean refreshBoard(int col, char disc) {
        if (col > cols - 1 || col < 0 || isBoardFull())
            return false;

        for (int i = 0; i < 6; i++)
            if (grid[col][i] == '_') {
                grid[col][i] = disc;
                if (disc == 'X') {
                    this.x_LastTry_C = col;
                    this.x_lastTry_R = i;
                } else {
                    this.y_LastTry_C = col;
                    this.y_lastTry_R = i;
                }
                this.hasWon(disc);
                return true;
            }
        return false;
    }

    protected boolean isBoardFull() {
        for (int i = 0; i < cols; i++) {
            if (grid[i][rows - 1] == '_')
                return false;
        }
        return true;
    }


    List<Integer> checkLines(char player) {
        List<Integer> returnList = new ArrayList<>();
        List<Integer> listX = new ArrayList<>();
        List<Integer> listO = new ArrayList<>();
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[j][i] == player) {
                    countX = getCountLines(listX, countX, i, j);
                } else if (grid[j][i] != '_') {
                    countO = getCountLines(listO, countO, i, j);
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

    private int getCountLines(List<Integer> listX, int countX, int i, int j) {
        listX.addAll(checkR(3, j, i));
        listX.addAll(checkC(3, j, i));
        listX.addAll(checkDU(3, j, i));
        listX.addAll(checkDD(3, j, i));
        countX += checkR(2, j, i).size() + checkC(2, j, i).size() + checkDU(2, j, i).size() + checkDD(2, j, i).size();
        return countX;
    }

    private List<Integer> checkR(int n, int col, int row) {
        List<Integer> returnList = new ArrayList<>();
        int nullLocation = 99;
        char p = grid[col][row];
        int countMe = 0;
        int i;

        for (i = col; i < col + n && i < cols; i++) {
            if (grid[i][row] == p)
                countMe++;
            else if (grid[i][row] == '_')
                nullLocation = (i * 10) + (row);
            else
                return returnList;
        }
        if ((countMe == n && col - 1 > -1 && grid[col - 1][row] == '_') || (countMe == n && i < cols && grid[i][row] == '_')) {
            // two-sided 3s check (_XXX_)
            if (n == 3 && col - 1 > -1 && grid[col - 1][row] == '_')
                returnList.add((col - 1) * 10 + (row));
            if (n == 3 && i < cols && grid[i][row] == '_')
                returnList.add((i * 10) + (row));
            if (n == 2) {
                // useless 2s check (OXX_O)
                int usableTest = 0;
                if (col - 1 > -1 && (grid[col - 1][row] == p))
                    return returnList;
                if (col - 1 > -1 && (grid[col - 1][row] == '_'))// || grid[col-1][row] == p ))
                    usableTest += 10;
                if (col - 2 > -1 && (grid[col - 2][row] == '_'))// || grid[col-2][row] == p))
                    usableTest += 2;
                if (i < cols && (grid[i][row] == p))
                    return returnList;
                if (i < cols && (grid[i][row] == '_'))// || grid[i][row] == p))
                    usableTest += 20;
                if (i + 1 < cols && (grid[i + 1][row] == '_'))// || grid[i+1][row] == p))
                    usableTest += 4;
                if (usableTest > 23 || usableTest == 12 || usableTest == 16)
                    returnList.add((col * 10) + (row));
            }
        }
        // disjoint 3s check (XX_X)
        else if (countMe == 2 && n == 3 && i < cols && grid[i][row] == p)
            returnList.add(nullLocation);


        return returnList;
    }


    private List<Integer> checkC(int n, int col, int row) {
        List<Integer> returnList = new ArrayList<>();
        char p = grid[col][row];
        int countMe = 0;
        int i;
        if (row > 2) // TODO check 2 or 1?
            return returnList;

        for (i = row; i < row + n && i < rows; i++) {
            if (grid[col][i] == p)
                countMe++;
            else if (grid[col][i] != '_')
                return returnList;
        }

        if ((countMe == n && i < rows && grid[col][i] == '_')) {
            if (n == 3)
                returnList.add((col * 10) + (i));
            if (n == 2)
                if (row - 1 > -1 && grid[col][row - 1] == p)
                    return returnList;
                else
                    returnList.add((col * 10) + (row));
        }
        return returnList;
    }


    private List<Integer> checkDU(int n, int col, int row) {
        List<Integer> returnList = new ArrayList<>();
        char p = grid[col][row];
        int countMe = 0;
        int nullLocation = 99;
        int i;
        int j;
        for (i = col, j = row; i < col + n && i < cols && j < row + n && j < rows; i++, j++) {
            if (grid[i][j] == p)
                countMe++;
            else if (grid[i][j] == '_')
                nullLocation = (i * 10) + (j);
            else
                return returnList;
        }
        if ((countMe == n && col - 1 > -1 && row - 1 > -1 && grid[col - 1][row - 1] == '_') || (countMe == n && i < cols && j < rows && grid[i][j] == '_')) {
            // two-sided 3s check (_XXX_)
            if (n == 3 && col - 1 > -1 && row - 1 > -1 && grid[col - 1][row - 1] == '_')
                returnList.add((col - 1) * 10 + (row - 1));
            if (n == 3 && i < cols && j < rows && grid[i][j] == '_')
                returnList.add((i * 10) + (j));
            if (n == 2) {
                // useless 2s check (OXX_O)
                int usableTest = 0;
                if (col - 1 > -1 && row - 1 > -1 && (grid[col - 1][row - 1] == p))
                    return returnList;
                if (col - 1 > -1 && row - 1 > -1 && (grid[col - 1][row - 1] == '_'))// || grid[col-1][row-1] == p))
                    usableTest += 10;
                if (col - 2 > -1 && row - 2 > -1 && (grid[col - 2][row - 2] == '_'))// || grid[col-2][row-2] == p))
                    usableTest += 2;
                if (i < cols && j < rows && (grid[i][j] == p))
                    return returnList;
                if (i < cols && j < rows && (grid[i][j] == '_'))// || grid[i][j] == p))
                    usableTest += 20;
                if (i + 1 < cols && j + 1 < rows && (grid[i + 1][j + 1] == '_'))// || grid[i+1][j+1] == p))
                    usableTest += 4;
                if (usableTest > 23 || usableTest == 12 || usableTest == 16)
                    returnList.add((col * 10) + (row));
            }
        }
        // disjoint 3s check (XX_X)
        else if (countMe == 2 && n == 3 && i < cols && j < rows && grid[i][j] == p)
            returnList.add(nullLocation);

        return returnList;
    }


    private List<Integer> checkDD(int n, int col, int row) {
        List<Integer> returnList = new ArrayList<>();
        char p = grid[col][row];
        int countMe = 0;
        int i;
        int j;
        int nullLocation = 99;
        for (i = col, j = row; i < col + n && i < cols && j > row - n && j > -1; i++, j--) {
            if (grid[i][j] == p)
                countMe++;
            else if (grid[i][j] == '_')
                nullLocation = (i * 10) + (j);
            else
                return returnList;
        }
        if ((countMe == n && col - 1 > -1 && row + 1 < rows && grid[col - 1][row + 1] == '_') || (countMe == n && i < cols && j > -1 && grid[i][j] == '_')) {
            // two-sided 3s check (_XXX_)
            if (n == 3 && col - 1 > -1 && row + 1 < rows && grid[col - 1][row + 1] == '_')
                returnList.add((col - 1) * 10 + (row + 1));
            if (n == 3 && i < cols && j > -1 && grid[i][j] == '_')
                returnList.add((i * 10) + (j));
            if (n == 2) {
                // useless 2s check (OXX_O)
                int usableTest = 0;
                if (col - 1 > -1 && row + 1 < rows && (grid[col - 1][row + 1] == p))
                    return returnList;
                if (col - 1 > -1 && row + 1 < rows && (grid[col - 1][row + 1] == '_'))// || grid[col-1][row+1] == p))
                    usableTest += 10;
                if (col - 2 > -1 && row + 2 < rows && (grid[col - 2][row + 2] == '_'))// || grid[col-2][row+2] == p))
                    usableTest += 2;
                if (i < cols && j > -1 && (grid[i][j] == p))
                    return returnList;
                if (i < cols && j > -1 && (grid[i][j] == '_'))// || grid[i][j] == p))
                    usableTest += 20;
                if (i + 1 < cols && j - 1 > -1 && (grid[i + 1][j - 1] == '_'))// || grid[i+1][j-1] == p))
                    usableTest += 4;
                if (usableTest > 23 || usableTest == 12 || usableTest == 16)
                    returnList.add((col * 10) + (row));
            }
        }
        // disjoint 3s check (XX_X)
        else if (countMe == 2 && n == 3 && i < cols && j > -1 && grid[i][j] == p)
            returnList.add(nullLocation);

        return returnList;
    }


    private boolean diagonalNE(char player, int col, int row) {
        for (int i = col, j = row; i < this.cols && j < this.rows; i++, j++) {
            int sequersCount = 0;
            while (grid[i][j] == player) {
                int arr[] = new int[2];
                arr[0] = i;
                arr[1] = j;
                winningPositions.add(arr);
                sequersCount++;
                i++;
                j++;
                if (sequersCount == 4) {
                    return true;
                }

                if (i >= this.cols || j >= this.rows) {
                    break;
                }
            }

            winningPositions.clear();
        }

        return false;

    }

    private boolean diagonalSE(char player, int col, int row) {
        for (int i = col, j = row; i < this.cols && j > -1; i++, j--) {
            int sequersCount = 0;
            while (grid[i][j] == player) {
                int arr[] = new int[2];
                arr[0] = i;
                arr[1] = j;
                winningPositions.add(arr);
                sequersCount++;
                i++;
                j--;
                if (sequersCount == 4) {
                    return true;
                }
                if (i >= this.cols || j < 0) {
                    break;
                }
            }

            winningPositions.clear();

        }
        return false;

    }

    private boolean vertical(char player, int col, int row) {
        boolean hasWinFlag = true;
        for (int i = row; i > row - 4; i--) {
            int arr[] = new int[2];
            arr[0] = col;
            arr[1] = i;
            winningPositions.add(arr);
            if (grid[col][i] != player) {
                winningPositions.clear();
                hasWinFlag = false;
                break;
            }
        }
        return hasWinFlag;

    }

    private boolean horizontal(char player, int col, int row) {
        for (int i = col; i < this.cols; i++) {
            int sequersCount = 0;
            while (grid[i][row] == player) {
                int arr[] = new int[2];
                arr[0] = i;
                arr[1] = row;
                winningPositions.add(arr);
                sequersCount++;
                i++;
                if (sequersCount == 4) {
                    return true;
                }
                if (i >= this.cols) {
                    break;
                }
            }
            winningPositions.clear();
        }

        return false;

    }

    protected boolean hasWon(char player) {
        int startPointDSE;
        int startPointDNE;

        if (player == 'X') {
            startPointDSE = this.x_LastTry_C + this.x_lastTry_R;
            startPointDNE = this.x_LastTry_C - this.x_lastTry_R;

            if ((this.x_lastTry_R >= 3 && this.vertical(player, this.x_LastTry_C, this.x_lastTry_R)) || this.horizontal(player, 0, this.x_lastTry_R)) {
                this.hasWon = true;
                return true;
            }

        } else {
            startPointDSE = this.y_LastTry_C + this.y_lastTry_R;
            startPointDNE = this.y_LastTry_C - this.y_lastTry_R;
            if ((this.y_lastTry_R >= 3 && this.vertical(player, this.y_LastTry_C, this.y_lastTry_R)) ||
                    this.horizontal(player, 0, this.y_lastTry_R)) {
                this.hasWon = true;
                return true;

            }
        }

        /////////////////////////////

        if (startPointDNE == 0 && this.diagonalNE(player, startPointDNE, startPointDNE)) {
            this.hasWon = true;
            return true;
        }

        if (startPointDNE < 0 && startPointDNE > -3 && this.diagonalNE(player, 0, Math.abs(startPointDNE))) {
            this.hasWon = true;
            return true;
        }

        if (startPointDNE > 0 && startPointDNE < 4 && this.diagonalNE(player, startPointDNE, 0)) {
            this.hasWon = true;
            return true;
        }
        ////////////////////////////////////

        if (startPointDSE >= 3 && startPointDSE <= 5 && this.diagonalSE(player, 0, startPointDSE)) {
            this.hasWon = true;
            return true;
        }

        if (startPointDSE >= 6 && startPointDSE <= 8 && this.diagonalSE(player, startPointDSE - 5, 5)) {
            this.hasWon = true;
            return true;

        }
        ////////////////////////
        return false;
    }


    int getFirstPlayerLastTryR() {
        return x_lastTry_R;
    }

    int getSecondPlayerLastTryR() {
        return y_lastTry_R;
    }

    int getFirstPlayerLastTryC() {
        return x_LastTry_C;
    }

    int getSecondPlayerLastTryC() {
        return y_LastTry_C;
    }

}
