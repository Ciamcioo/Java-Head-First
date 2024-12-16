package main.java.com.component;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private static final int COLOR_RANGE = 256;
    private int ovalWidth = 70, ovalHeight = 70;

    public void paintComponent(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.fillRect(0, 0, getWidth(), getHeight());
        Color color = generateColor(); 
        graphics2d.setColor(color);
        graphics2d.fillOval(new Random().nextInt(getWidth() - ovalWidth), new Random().nextInt(getHeight() - ovalHeight), ovalWidth, ovalHeight);
        
    } 

    /**
     * Method generates random color by generating three ints from rgb range. 
     * @return generated color  
     */ 
    private Color generateColor() {
        Random random = new Random(); 
        int red = random.nextInt(COLOR_RANGE);
        int green = random.nextInt(COLOR_RANGE);
        int blue = random.nextInt(COLOR_RANGE);
        return new Color(red, green, blue);
    }

}
