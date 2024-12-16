package main.java.com;

import java.util.Random;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

/**
 * AdvisorServer
 */
public class AdvisorServer {
   private final String[] advices = {
       "Use smaller bites",
       "Dress slim fit trousers. You look cool :)",
       "Just one word: inapropriate",
       "Be honest to yourself",
       "Consider your hair cut" 
   };
   private final Random random  = new Random();

   public static void main(String[] args) {
        new AdvisorServer().runSever();
   } 
   
   public void runSever() {
       try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            InetSocketAddress address = new InetSocketAddress(5000);
            serverChannel.bind(address);
            
            while (serverChannel.isOpen()) {
                SocketChannel clientChannel = serverChannel.accept();
                PrintWriter writer = new PrintWriter(Channels.newOutputStream(clientChannel)); 
                // Comented out option would also work it is the same thing
                // PrintWriter writer = new PrintWriter(Channels.newWriter(clientChannel, StandardCharsets.UTF_8));
                String generateAdvice = getRandomAdivce();
                writer.println(generateAdvice);
                writer.close();
                System.out.println(generateAdvice);
            }
       } catch (IOException ex) {
           ex.printStackTrace();
       }
      
   }

    private String getRandomAdivce() {
       int adviceNum = random.nextInt(advices.length); 
       return advices[adviceNum];
    }
   
}
