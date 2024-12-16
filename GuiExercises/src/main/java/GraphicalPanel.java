package main.java;

import javax.swing.*;
import java.awt.*;

public class GraphicalPanel extends JPanel {
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(20, 50, 100, 100);
    }
    
}
