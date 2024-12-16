package main.java;

import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import java.io.*;

/**
 * MuzServer
 */
public class MuzServer {
    private  List<ObjectOutputStream> outputStreams = new ArrayList<>();
    
    public static void main(String[] args) {
        new MuzServer().start();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(4242);
            ExecutorService executor = Executors.newCachedThreadPool();

            while(!serverSocket.isClosed()) {
                Socket clientSocket = new Socket("127.0.0.1", 4242);
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStreams.add(oos);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.execute(clientHandler);
                System.out.println("Conection established");
            }
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public void brodcastMessage(Object nameAndMessage, Object tactSequenc) {
        for (ObjectOutputStream oos : outputStreams) {
            try {
                oos.writeObject(nameAndMessage);
                oos.writeObject(tactSequenc);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }


    class ClientHandler implements Runnable {
        private ObjectInputStream ois;
        public ClientHandler(Socket socket) {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch(IOException exception) {
                exception.printStackTrace();
            }
        }

        public void run() {
            Object userNameAndMessage;
            Object tackSequence;
            try {
                while ((userNameAndMessage = ois.readObject()) != null) {
                    tackSequence = ois.readObject();
                    System.out.println("Two objects read");
                    brodcastMessage(userNameAndMessage, tackSequence);
                }
            } catch(IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }
}
