package main.java.com;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.io.*;

public class QuizGame {
    private List<QuizCard> cards = new ArrayList<>();
    private QuizCard currentCard;
    private int currentCardIndex;
    private GameGui gui;

    public QuizGame() {
        gui = new GameGui();
    }
    public static void main(String[] args) {
        new QuizGame();
    }

    private void loadCardSet(File file) {
        currentCardIndex = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) 
                cards.add(createCard(line));
            reader.close(); 
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private QuizCard createCard(String mergedCard) {
        String[] result = mergedCard.split("/");
        return new QuizCard(result[0], result[1]);
         
    }

    class GameGui {
        private JFrame frame;
        private JPanel mainPanel;
        private JTextArea textArea;
        private JButton nextButton;

        private boolean isQuestionVisible = false;

        public GameGui(){
            frame = new JFrame("Quiz Game");            
            mainPanel = new JPanel();

            textArea = new JTextArea(6, 20);
            textArea.setFont(new Font("sanserif", Font.BOLD, 24));
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            JScrollPane textPane = new JScrollPane(textArea);
            textPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            textPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            mainPanel.add(textPane);
            
            nextButton = new JButton("Pokaż pytanie");
            nextButton.addActionListener(event -> nextCard());
            mainPanel.add(nextButton);

            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("Plik");
            JMenuItem open = new JMenuItem("Otwórz zestaw card");
            open.addActionListener(event -> openCardSet());
            fileMenu.add(open);
            menuBar.add(fileMenu);
            frame.setJMenuBar(menuBar);

            frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
            frame.setSize(500, 430);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

        private void nextCard(){
            if (isQuestionVisible) {
                textArea.setText(currentCard.getAnswear());    
                nextButton.setText("Next question");
                isQuestionVisible = false;
            }
            else {
                if (currentCardIndex < cards.size())
                   showNextCard(); 
                else {
                    textArea.setText("That was last card!");
                    nextButton.setEnabled(false);
                } 
            }
        }
    
        private void showNextCard() {
            currentCard = cards.get(currentCardIndex++);
            textArea.setText(currentCard.getQuestion());
            nextButton.setText("Show answear");
            isQuestionVisible = true;
        }


        private void openCardSet() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(frame);
            loadCardSet(fileChooser.getSelectedFile()); 
            showNextCard();
        }

    } 
}
