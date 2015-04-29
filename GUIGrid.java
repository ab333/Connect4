
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GUIGrid extends JPanel
{
    
    private final ImageIcon red         = new ImageIcon(GUIGrid.class.getResource("/img/red.png"));
    private final ImageIcon blue        = new ImageIcon(GUIGrid.class.getResource("/img/blue.png"));
    private final ImageIcon gray        = new ImageIcon(GUIGrid.class.getResource("/img/gray.png"));
    private final ImageIcon actionArrow = new ImageIcon(GUIGrid.class.getResource("/img/arr.png"));
    private final ImageIcon redWin      = new ImageIcon(GUIGrid.class.getResource("/img/redWin.png"));
    private final ImageIcon blueWin     = new ImageIcon(GUIGrid.class.getResource("/img/blueWin.png"));
    
    private final JLabel[][] cells;
    private final JButton[] actions;
    private final JFrame frame;
    private final JPanel cellsContainer;
    
      public GUIGrid()
    {
        frame = new JFrame("Connect Four");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(560, 560);
        frame.setLocationRelativeTo(null);
        cellsContainer = new JPanel(new GridLayout(7, 7, 0, 0));
        
        actions = new JButton[7];
        for(int i=0; i<actions.length ;i++)
        {
            this.actions[i] = new JButton();
            actions[i].setIcon(actionArrow);
            actions[i].setBorder(BorderFactory.createEmptyBorder());
            actions[i].setContentAreaFilled(true);
            actions[i].setActionCommand(Integer.toString(i));
            cellsContainer.add(actions[i]);
        }
        
        cells = new JLabel[7][6];
        for(int r=5; r>-1 ;r--){
            for(int c=0;c<7;c++)
            {
                cells[c][r] = new JLabel(gray);
                cellsContainer.add(cells[c][r]);
            }
        }
        
        frame.add(cellsContainer);
        frame.setVisible(true);
        frame.pack();
    }
    
    
    public void updateGrid(int col, int row,char player)
    {
        //synchronized(this){
        if(player == 'X')
            cells[col][row].setIcon(red);
        else
            cells[col][row].setIcon(blue);
        //TimeUnit.SECONDS.sleep(1);
        //}
    }
    
    public void hasWon(ArrayList<int[]> winningPositions, char player)
    {
        if(player == 'X')
        {
            for(int i = 3; i >-1 ; i--){
                cells[winningPositions.get(i)[0]][winningPositions.get(i)[1]].setIcon(redWin);
            }
            JOptionPane.showMessageDialog(null, "Red player has won!");
        }
        else
        {
            for(int i = 3; i >-1 ; i--){
                cells[winningPositions.get(i)[0]][winningPositions.get(i)[1]].setIcon(blueWin);
            }
            JOptionPane.showMessageDialog(null, "Blue player has won!");
        }
    }
    
    public void setButtonAction(int actionNumber, Player p)
    {
        actions[actionNumber].addActionListener(p);
    }
    
}