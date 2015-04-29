
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class MainWindow extends JFrame {
    private final JFrame frame;
    private final JButton anotherPlayer;
    private final JButton randomAgent;
    private final JButton aiAgent;
    MainWindow()
    {
            setLayout(new BorderLayout());
            frame = new JFrame("Connect Four");
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel panel = new JPanel(new GridBagLayout());
            frame.add(panel);
            frame.getContentPane().add(panel, BorderLayout.CENTER);
            
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(2, 10, 2, 10);
            c.ipady = 100;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.weightx = 0.5;
            c.weighty = 0.5;
            
            
            anotherPlayer = new JButton("Another Player");
            anotherPlayer.setFont(anotherPlayer.getFont().deriveFont((float) 30));
            c.gridy = 0;
            anotherPlayer.setActionCommand("Another");
            panel.add(anotherPlayer,c);
            anotherPlayer.addActionListener(new Connect4());

            randomAgent = new JButton("Random Agent");
            randomAgent.setFont(randomAgent.getFont().deriveFont((float) 30));
            c.gridy = 1;
            randomAgent.setActionCommand("Random");
            panel.add(randomAgent, c);
            randomAgent.addActionListener(new Connect4());
            
            aiAgent = new JButton("AI Agent");
            aiAgent.setFont(aiAgent.getFont().deriveFont((float) 30));
            c.gridy = 2;
            aiAgent.setActionCommand("AI");
            panel.add(aiAgent, c);
            aiAgent.addActionListener(new Connect4());
            
            frame.setVisible(true);
            //pack();
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    
}
