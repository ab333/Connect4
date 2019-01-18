
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;

public class Player implements ActionListener {
    private int col;
    boolean intelligenceMode;
    private boolean randomMode;
    private GUIGrid guiGrid;
    private char playerName;
    private Grid board2;

    public Player() {
        intelligenceMode = false;
        randomMode = false;
    }

    void activateRandomPlayer() {
        randomMode = true;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        col = Integer.parseInt(e.getActionCommand());
        if (!board2.refreshBoard(col, playerName)) {
            System.out.println("Please choose another column");
            JOptionPane.showMessageDialog(null, "Please choose another column!");
            return;
        }
        if (playerName == 'X') {
            guiGrid.updateGrid(col, board2.getFirstPlayerLastTryR(), playerName);

        }
        if (playerName == 'Y') {
            guiGrid.updateGrid(col, board2.getSecondPlayerLastTryR(), playerName);
        }

        printWinner(playerName);

        playerName = (playerName == 'Y') ? 'X' : 'Y';

        char tempPlayerName = playerName;
        if (intelligenceMode) {
            playerName = intelligence(board2, playerName);
            guiGrid.updateGrid(board2.getSecondPlayerLastTryC(), board2.getSecondPlayerLastTryR(), tempPlayerName);
        } else if (randomMode) {
            playerName = randomAgent(board2, playerName);
            guiGrid.updateGrid(board2.getSecondPlayerLastTryC(), board2.getSecondPlayerLastTryR(), tempPlayerName);
        }

        printWinner(tempPlayerName);

        board2.drawBoard();

        if (board2.isBoardFull()) {
            JOptionPane.showMessageDialog(null, "Game over!");
            System.exit(1);
        }
    }

    private void printWinner(char tempPlayerName) {
        if (board2.hasWon(tempPlayerName)) {
            System.out.println("Player: " + tempPlayerName + " has won!");
            guiGrid.hasWon(board2.winningPositions, tempPlayerName);
            board2.drawBoard();
            System.exit(1);
        }
    }

    void startPlaying(Grid board, char player) {
        playerName = player;
        board2 = board;
        guiGrid = new GUIGrid();
        for (int i = 0; i < 7; i++) {
            guiGrid.setButtonAction(i, this);
        }
        if (intelligenceMode) {
            playerName = (playerName == 'Y') ? 'X' : 'Y';
            char tempPlayerName = playerName;
            playerName = intelligence(board2, playerName);
            guiGrid.updateGrid(board2.getSecondPlayerLastTryC(), board2.getSecondPlayerLastTryR(), tempPlayerName);
        }
    }

    private char randomAgent(Grid board, char player) {
        do {
            Random rand = new Random();
            col = rand.nextInt((6) + 1);
        } while (!board.refreshBoard(col, player)); // Add Referee isAllowed here
        return 'X';
    }

    protected char intelligence(Grid board, char player) {
        return 'X';
    }
}
