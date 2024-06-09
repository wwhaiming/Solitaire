import java.awt.*;
import javax.swing.*;
import javax.swing.OverlayLayout;

public class Game extends JFrame {
   public Game() {
      setTitle("OverlayLayout Test");
      JPanel panel = new JPanel() {
         public boolean isOptimizedDrawingEnabled() {
            return false;
         }
      };
      LayoutManager overlay = new OverlayLayout(panel);
      panel.setLayout(overlay);
      JButton button = new JButton("Small");
      button.setMaximumSize(new Dimension(500/4, 726/4));
      button.setBackground(Color.white);
      panel.add(button);
      button = new JButton("Medium Btn");
      button.setMaximumSize(new Dimension(500/4, 726/4));
      button.setBackground(Color.lightGray);
      panel.add(button);
      button = new JButton("Large Button");
      button.setMaximumSize(new Dimension(500/4, 726/4));
      button.setBackground(Color.orange);
      panel.add(button);
      add(panel, BorderLayout.CENTER);
      setSize(400, 300);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }  
   public static void main(String args[]) {
      new Game();
   }
}