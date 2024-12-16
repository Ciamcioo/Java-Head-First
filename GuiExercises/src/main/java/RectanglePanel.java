package main.java;

import javax.swing.*;
import java.awt.*;

public class RectanglePanel extends JPanel {
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(10, 10, 100, 100);
    }
    
}
