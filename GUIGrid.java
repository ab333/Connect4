import javax.swing.*;
import java.awt.*;
import java.util.List;


public class GUIGrid extends JPanel {

    private final ImageIcon red = new ImageIcon(GUIGrid.class.getResource("/img/red.png"));
    private final ImageIcon blue = new ImageIcon(GUIGrid.class.getResource("/img/blue.png"));
    private final ImageIcon redWin = new ImageIcon(GUIGrid.class.getResource("/img/redWin.png"));
    private final ImageIcon blueWin = new ImageIcon(GUIGrid.class.getResource("/img/blueWin.png"));

    private final JLabel[][] cells;
    private final JButton[] actions;

    GUIGrid() {
        JFrame frame = new JFrame("Connect Four");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(560, 560);
        frame.setLocationRelativeTo(null);
        JPanel cellsContainer = new JPanel(new GridLayout(7, 7, 0, 0));

        actions = new JButton[7];
        for (int i = 0; i < actions.length; i++) {
            this.actions[i] = new JButton();
            ImageIcon actionArrow = new ImageIcon(GUIGrid.class.getResource("/img/arr.png"));
            actions[i].setIcon(actionArrow);
            actions[i].setBorder(BorderFactory.createEmptyBorder());
            actions[i].setContentAreaFilled(true);
            actions[i].setActionCommand(Integer.toString(i));
            cellsContainer.add(actions[i]);
        }

        cells = new JLabel[7][6];
        for (int r = 5; r > -1; r--) {
            for (int c = 0; c < 7; c++) {
                ImageIcon gray = new ImageIcon(GUIGrid.class.getResource("/img/gray.png"));
                cells[c][r] = new JLabel(gray);
                cellsContainer.add(cells[c][r]);
            }
        }

        frame.add(cellsContainer);
        frame.setVisible(true);
        frame.pack();
    }


    void updateGrid(int col, int row, char player) {
        if (player == 'X')
            cells[col][row].setIcon(red);
        else
            cells[col][row].setIcon(blue);
    }

    void hasWon(List<int[]> winningPositions, char player) {
        if (player == 'X') {
            for (int i = 3; i > -1; i--) {
                cells[winningPositions.get(i)[0]][winningPositions.get(i)[1]].setIcon(redWin);
            }
            JOptionPane.showMessageDialog(null, "Red player has won!");
        } else {
            for (int i = 3; i > -1; i--) {
                cells[winningPositions.get(i)[0]][winningPositions.get(i)[1]].setIcon(blueWin);
            }
            JOptionPane.showMessageDialog(null, "Blue player has won!");
        }
    }

    void setButtonAction(int actionNumber, Player p) {
        actions[actionNumber].addActionListener(p);
    }

}