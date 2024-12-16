package main.java.com;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
public class QuizEditor {
    private List<QuizCard> cards = new ArrayList<>();
    private EditorGui gui;

    public QuizEditor() {
        gui = new EditorGui();
    }
    public static void main(String[] args) {
        QuizEditor editor = new QuizEditor();
    }
    
    class EditorGui {
        private JFrame frame;
        private JPanel mainPanel;
        private JTextArea questionArea, answearArea;

        public EditorGui() {
            frame = new JFrame("Quiz Editor");
            mainPanel = new JPanel();
            questionArea = createTextArea();
            answearArea = createTextArea();
            
            mainPanel.add(new JLabel("Question:"));
            mainPanel.add(createScrollPane(questionArea));
            mainPanel.add(new JLabel("Answear:"));
            mainPanel.add(createScrollPane(answearArea));

            JButton nextButton = new JButton("Next card");
            nextButton.addActionListener(event -> nextQuizCard());
            mainPanel.add(nextButton);

            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("Plik"); // Menu odpowiada za jedną listę rozwijaną w pasku górnym
                                               //
            JMenuItem newItem = new JMenuItem("New");  
            newItem.addActionListener(event -> clearAll());
            menu.add(newItem);

            JMenuItem saveItem = new JMenuItem("Save");
            saveItem.addActionListener(event -> saveCard());
            menu.add(saveItem);
            
            menuBar.add(menu);
            frame.setJMenuBar(menuBar);

            frame.getContentPane().add(BorderLayout.CENTER, mainPanel); 
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

        private JTextArea createTextArea() {
            JTextArea textArea = new JTextArea(6, 20);
            textArea.setFont(new Font("sanserif", Font.BOLD, 24));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            return textArea;
        }

        private JScrollPane createScrollPane(JTextArea area) {
            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            return scrollPane;
        }

        private void clearAll() {
            cards.clear(); 
            clearCard();
        }
        
        private void clearCard() {
           questionArea.setText(""); 
           answearArea.setText("");
           questionArea.requestFocusInWindow(); // lepssza metod niż requestFocuse
           
        }

        private void saveCard() {
            QuizCard card = new QuizCard(questionArea.getText(), answearArea.getText());
            cards.add(card);
            clearCard();

            JFileChooser saveFileDialog = new JFileChooser();
            saveFileDialog.showSaveDialog(frame);
            saveFile(saveFileDialog.getSelectedFile());
        }

        private void saveFile(File file) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (QuizCard card : cards) 
                    writer.write(new StringBuilder().append(card.getQuestion()).append('/').append(card.getAnswear()).append('\n').toString());
                writer.close(); 
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        private void nextQuizCard() {
            QuizCard card = new QuizCard(questionArea.getText(), answearArea.getText());
            cards.add(card);    
            clearCard();
        }
    }
}
