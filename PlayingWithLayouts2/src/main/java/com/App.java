package main.java.com;

import javax.swing.*;
import java.awt.*;

public class App {
        String currentName = "Someone";
        String firstPart = "Just press me";
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        JFrame frame = new JFrame("Scroling app");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JTextArea textArea = new JTextArea(20, 20);
        textArea.setLineWrap(true);
        JScrollPane scrollPanel = new JScrollPane(textArea);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        JButton button = new JButton("Just press me!"); 
        button.addActionListener(event -> textArea.append(String.format("%s, %s!\n",firstPart, currentName)));

        JCheckBox checkBox = new JCheckBox("Just not press me!", false);
        checkBox.addItemListener(event -> {
            if (checkBox.isSelected())
                firstPart =  "Just not press me";
            else
                firstPart =  "Just press me";
            });


        JList<String> nameList = new JList<>(new String[]{"John", "Jacob", "Emily", "Petter", "Someone"});
        nameList.setVisibleRowCount(3);
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nameList.addListSelectionListener(event -> {
                currentName = (String) nameList.getSelectedValue(); 
        });
        JScrollPane namePane = new JScrollPane(nameList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        
        
        frame.getContentPane().add(BorderLayout.CENTER, scrollPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.getContentPane().add(BorderLayout.EAST, checkBox);
        frame.getContentPane().add(BorderLayout.WEST, namePane);
        frame.setVisible(true);
    }
    
}
