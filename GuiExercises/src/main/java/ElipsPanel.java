package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ElipsPanel extends JPanel {
    private static final int COLOR_RANGE = 256;
    public void paintComponent(Graphics graphics) {
        graphics.fillRect(0, 0, getWidth(), getHeight());
        Random random = new Random();
        int red = random.nextInt(COLOR_RANGE);
        int green = random.nextInt(COLOR_RANGE);
        int blue = random.nextInt(COLOR_RANGE);
        graphics.setColor(new Color(red, green, blue));
        graphics.fillOval(10,10, 100, 100);
    } 

}
