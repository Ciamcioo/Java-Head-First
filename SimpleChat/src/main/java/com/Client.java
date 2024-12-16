package main.java.com;

import javax.swing.*;
import java.net.*;
import java.nio.channels.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.awt.*;
import static  java.nio.charset.StandardCharsets.UTF_8;

public class Client {
    private ClientGui gui;
    private PrintWriter serverWriter;
    private BufferedReader clientReader;

    public Client() {
       configurateComunication(); 
       SwingUtilities.invokeLater(() -> gui = new ClientGui());
    }
    public static void main(String[] args) {
        new Client();
    }

    private void configurateComunication() {
        try {
           InetSocketAddress address = new InetSocketAddress("127.0.0.1", 5000); 
           SocketChannel channel = SocketChannel.open(address);
           serverWriter = new PrintWriter(Channels.newWriter(channel, UTF_8));
           clientReader = new BufferedReader(Channels.newReader(channel, UTF_8));
           System.out.println("Network connection establish. Channel address: " + channel.getLocalAddress());
        } catch(IOException e) {
            e.printStackTrace();
        } 
    } 
    
    private void sendMessage() {
       
        serverWriter.println(gui.clientMesage.getText());
        serverWriter.flush(); // we need to flush it because it is a buffered writer so we need to make sure that everything will be printed out and the buffer will not wait for any extra data
        SwingUtilities.invokeLater(() -> {
            gui.clientMesage.setText("");
            gui.clientMesage.requestFocus();
        });
    }

    public void reciveMessage() {
        //  Code from the book 
            String messageToRecive;
            try {
                while((messageToRecive = clientReader.readLine()) != null) {
                    String finalMessage = messageToRecive + "\n";
                    System.out.println("Recived: " + messageToRecive);
                    SwingUtilities.invokeLater(() -> gui.messages.append(finalMessage));
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }

        /* Solution with a SwingUtilities 
            String messageToRecive;
            try {
                while((messageToRecive = clientReader.readLine()) != null) {
                    String finalMessage = messageToRecive + "\n";
                    System.out.println("Recived: " + messageToRecive);
                    SwingUtilities.invokeLater(() -> {
                        gui.messages.append(finalMessage);
                        gui.messages.setCaretPosition(gui.messages.getDocument().getLength());
                    }); 
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        */

        /* Solution with a SwingWorker
            SwingWorker<Void, String> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        String messageToRecive;
                        while ((messageToRecive = clientReader.readLine()) != null) {
                            System.out.println("Read: " + messageToRecive);
                            publish(messageToRecive);
                        } 
                       
                    } catch(IOException ex) {
                        ex.printStackTrace();
                    }
                    return null;
                }
                @Override
                protected void process(List<String> messages) {
                    for (String message : messages) {
                        gui.messages.append(message);
                        gui.messages.setCaretPosition(gui.messages.getDocument().getLength());
                    }

                }
            };
            worker.execute();
        */
    }

    class ClientGui {
        JFrame frame;
        JTextArea messages;
        JTextField clientMesage;

        public ClientGui() {
            frame = new JFrame("Simple Chat Client");

            JScrollPane chat = createChat();
            clientMesage = new JTextField(20);

            JButton sendButton = new JButton("Send");    
            sendButton.addActionListener(e -> sendMessage());

            JPanel mainPanel = new JPanel();
            mainPanel.add(chat);
            mainPanel.add(clientMesage);
            mainPanel.add(sendButton);
            frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
            frame.setSize(400, 350);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Thread readerThread = new Thread(() -> reciveMessage());
            readerThread.start();
            frame.setVisible(true);
        }
        
        private JScrollPane createChat() {
            messages = createMessageSpace(); 
            JScrollPane messagesPane = new JScrollPane(messages);
            messagesPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            messagesPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            return messagesPane; 
        }

        private JTextArea createMessageSpace() {
            JTextArea area = new JTextArea("",15, 30);
            area.setLineWrap(true);
            area.setWrapStyleWord(true); 
            area.setEditable(false);
            return area;
        }
    }    
}
