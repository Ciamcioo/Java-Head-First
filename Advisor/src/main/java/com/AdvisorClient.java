package main.java.com;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class AdvisorClient{

    private void generateAdivse() {
        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1", 5000); // 127.0.0.1 is loop back address so our pc would be the server and the client at once, the porst which we choose for our application needs to range from 1024 to around 65 000.        
        try (SocketChannel channel = SocketChannel.open(serverAddress)) {
            Reader channelReader = Channels.newReader(channel, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(channelReader);
            String advice = reader.readLine();
            System.out.println("Advice for today: " + advice);
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
           new AdvisorClient().generateAdivse();
    }
}
