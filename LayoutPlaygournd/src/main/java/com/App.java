package main.java.com;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        App app = new App();        
        app.run();
    }

    public void run() {
        JFrame frame = new JFrame("LayoutPlaygournd");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        JButton button = new JButton("Press meeeeeeeeeeeeeeeeeee");
        frame.getContentPane().add(BorderLayout.EAST, button);
        frame.setVisible(true);
    }
    
}
