package main.java;

import javax.swing.*;
import java.awt.event.*;

public class SimpleGui implements ActionListener {
    private JFrame guiFrame;
    private JButton button;
    public SimpleGui() {
        guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(1200, 1500);
    }
    public static void main(String[] args) {
       SimpleGui gui = new SimpleGui();
       //gui.buttonCreation();
       //gui.panelCreation(new ImagePanel()); 
       //gui.panelCreation(new RectanglePanel()); 
       //gui.panelCreation(new ElipsPanel()); 
       gui.panelCreation(new GradientPanel()); 
       gui.getJFrame().setVisible(true);
    
    }    
    public void buttonCreation() {
        button = new JButton("Press me");
        button.addActionListener(this);
        guiFrame.getContentPane().add(button);

    }

    public void panelCreation(JPanel panel) {
        guiFrame.add(panel); 
        panel.repaint();
    }
    public void actionPerformed(ActionEvent event) {
       button.setText("I was pressed"); 
    }

    public JFrame getJFrame() {
        return guiFrame;
    }
}
