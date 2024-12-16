package main.java.com;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Server {
    private List<PrintWriter> clientChannels = new ArrayList<>();

    public static void main(String[] args) {
        new Server().go();
    }

    private void  go() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(5000));

            while(serverChannel.isOpen()) {
               SocketChannel clientChannel = serverChannel.accept(); 
               PrintWriter writer = new PrintWriter(Channels.newWriter(clientChannel, UTF_8), true);
               clientChannels.add(writer);
               threadPool.submit(new ClientHandling(clientChannel));
               System.out.println("Conection");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void brodcastMessage(String message) {
        for (PrintWriter clientChannel : clientChannels) {
            clientChannel.println(message);
            clientChannel.flush();
        }
    }

    public class ClientHandling implements Runnable {
        private BufferedReader reader;
        private SocketChannel channel;

        public ClientHandling(SocketChannel channel) {
            this.channel = channel; 
            this.reader = new BufferedReader(Channels.newReader(channel,UTF_8));
        }
        public void run() {
            String message;
            try {
                while((message = reader.readLine()) != null) {
                    System.out.println("Read: " + message);
                    brodcastMessage(message);
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
