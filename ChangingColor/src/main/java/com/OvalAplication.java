package main.java.com;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.java.com.component.DrawingPanel;

public class OvalAplication {
    private JFrame applicationFrame = new JFrame();
    private DrawingPanel drawingPanel = new DrawingPanel();
    private JButton colorButton = new JButton(), labelButton = new JButton();
    private JLabel label = new JLabel("New label");

    public static void main(String[] args) {
        OvalAplication app = new OvalAplication();
        app.frameInitaliztion();
        app.applicationFrame.getContentPane().add(BorderLayout.CENTER, app.drawingPanel);
        app.applicationFrame.getContentPane().add(BorderLayout.WEST, app.label); 
        app.buttonCreation();
        app.applicationFrame.setVisible(true);
    }

    private void frameInitaliztion() {
        applicationFrame.setSize(1000, 1000);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void buttonCreation() {
        colorButton.setText("Change oval color");
//        colorButton.addActionListener(new ColorChangeListener());
        colorButton.addActionListener(event -> drawingPanel.repaint());
        

        labelButton.setText("Change label");
//        labelButton.addActionListener(new LabelChangeListener());
        labelButton.addActionListener(event -> { label.setText("Changed label"); });

        applicationFrame.getContentPane().add(BorderLayout.EAST, labelButton);
        applicationFrame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        
    }
    
//    class ColorChangeListener implements ActionListener {
//        public void actionPerformed(ActionEvent event) {
//           drawingPanel.repaint(); 
//        }
//    }
//
//    class LabelChangeListener implements ActionListener {
//        public void actionPerformed(ActionEvent event) {
//           label.setText("Changed label"); 
//        }
//    }
//
    
}
