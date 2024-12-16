package main.java.com;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
/**
 * Animation
 */
public class Animation {
    private JFrame frame = new JFrame();
    private int x = 1, y = 1;
    public static void main(String[] args) {
        Animation animation = new Animation();
        animation.runAnimation();
    }

    public void runAnimation() {
       frame.setSize(500, 500);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       AnimationPanel panel = new AnimationPanel();
       frame.getContentPane().add(BorderLayout.CENTER, panel);
       frame.setVisible(true);

       for (int i = 0; i < 480; i++) { 
          x++;
          y++;
          panel.repaint(); 

            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    } 

    class AnimationPanel extends JPanel {
        public void paintComponent(Graphics graphics) {
            Graphics2D graphics2d = (Graphics2D) graphics;
            graphics2d.setColor(Color.WHITE);
            graphics2d.fillRect(0, 0, getWidth(), getHeight());
            graphics2d.setColor(Color.RED);
            graphics2d.fillOval(x, y, 20, 20);
        }        
    }

    
}
