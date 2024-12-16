package main.java.com;

import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args){
        JFrame frame = new JFrame("Border Layout");
        JButton north = new JButton("North");
        JButton south = new JButton("South");
        JButton east = new JButton("East");
        JButton west = new JButton("West");
        JButton center = new JButton("Center");

        JPanel framePanel = (JPanel) frame.getContentPane();
        framePanel.add(BorderLayout.NORTH, north);
        framePanel.add(BorderLayout.SOUTH, south);
        framePanel.add(BorderLayout.WEST, west);
        framePanel.add(BorderLayout.EAST, east);
        framePanel.add(BorderLayout.CENTER, center);


        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JFrame frameFlowLayout = new JFrame("Flow Layout");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frameFlowLayout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameFlowLayout.setSize(200, 200);

        panel.setBackground(Color.RED);
        panel.add(new JButton("To szok!"));
        panel.add(new JButton("Trach!"));
        panel.add(new JButton("Och?!"));
        frameFlowLayout.add(BorderLayout.EAST, panel);
        frameFlowLayout.setVisible(true);

        JFrame otherComponents = new JFrame("Playing with otehr components");
        otherComponents.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        otherComponents.setSize(500, 500);
        JPanel otherPanel = new JPanel();
        //otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
        JLabel password = new JLabel("Provide password:");
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(20, 20));
        otherPanel.add(password);
        otherPanel.add(field);
        otherComponents.add(BorderLayout.CENTER, otherPanel);
        otherComponents.setVisible(true);
        
    }
    
    
}
