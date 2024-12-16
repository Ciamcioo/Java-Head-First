package main.java;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel  {
    public void paintComponent(Graphics graphics) {
       Image picture = new ImageIcon("/home/ciamcio/workspace/javaPrograming/HeadFristProjects/GuiExercises/src/main/java/kotzila.jpeg").getImage();  
       graphics.drawImage(picture, 1, 1, this);
    }
    
}
