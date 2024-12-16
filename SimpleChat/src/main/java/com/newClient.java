package main.java.com;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.concurrent.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class newClient {
    private JTextArea recivedMessages;
    private JTextField message;
    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new newClient().make();
        }); 
    }

    public void make() {
        JFrame frame = new JFrame("Simple chat"); 
        JPanel mainPanel = new JPanel();

        JScrollPane scrollPane = configurateScrollPane();

        message = new JTextField(20);
        
        JButton button = new JButton("Send");
        button.addActionListener(e -> sendMessage());

        mainPanel.add(scrollPane);
        mainPanel.add(message);
        mainPanel.add(button);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        configuratedComunication();
        Thread thread = new Thread(new Reciver());
        thread.start();
        //ExecutorService executor = Executors.newSingleThreadExecutor();
        //executor.execute(new Reciver());

        frame.setSize(400, 350);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configuratedComunication() {
        try {
            Socket address = new Socket("127.0.0.1", 5000);
            InputStreamReader ipr = new InputStreamReader(address.getInputStream());
            reader = new BufferedReader(ipr);
            writer = new PrintWriter(address.getOutputStream());
            System.out.println("Connection established.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private JScrollPane configurateScrollPane() {
        recivedMessages = new JTextArea(15, 30);
        recivedMessages.setLineWrap(true);
        recivedMessages.setWrapStyleWord(true);
        recivedMessages.setEditable(false);
        JScrollPane pane = new JScrollPane(recivedMessages);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return pane;
    }        

    private void sendMessage() {
        writer.println(message.getText());
        writer.flush();
        message.setText("");
        message.requestFocus();
    }

    class Reciver implements Runnable {
        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("Recived: " + message);
                    recivedMessages.append(message + "\n");
                }
            } catch(IOException ex) {
                    ex.printStackTrace();
            }
        } 
    }
}

