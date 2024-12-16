package main.java;

import javax.swing.JPanel;
import java.awt.*;
import java.util.Random;

public class GradientPanel extends JPanel{
    private static final int COLOR_RANGE = 256;
    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics; 

        Random random = new Random();
        int red = random.nextInt(COLOR_RANGE);
        int blue = random.nextInt(COLOR_RANGE);
        int green = random.nextInt(COLOR_RANGE);
        Color startColor = new Color(red, blue, green);
        
        red = random.nextInt(COLOR_RANGE);
        blue = random.nextInt(COLOR_RANGE);
        green = random.nextInt(COLOR_RANGE);
        Color endColor = new Color(red, green, blue);

        GradientPaint gradient = new GradientPaint(70, 70, startColor, 150, 150, endColor);
        graphics2d.setPaint(gradient);
        graphics2d.fillOval(70, 70, 100, 100);
    }
}
