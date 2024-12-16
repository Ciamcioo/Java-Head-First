package main.java.com;

import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.concurrent.*;

import static java.time.format.DateTimeFormatter.ofLocalizedTime;

/**
 * App
 */
public class App {
    public static void main(String[] args) {
        List<Chat> chatHistory = new CopyOnWriteArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            executor.execute(() -> chatHistory.add(new Chat("Hello!")));
            executor.execute(() -> System.out.println(chatHistory));
            executor.execute(() -> System.out.println(chatHistory));
        }
        executor.shutdown();
    }
    
}

final class Chat {
    private final String comunicat;
    private final LocalDateTime timeStamp;

    public Chat(String comunicat) {
        this.comunicat = comunicat;
        timeStamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        String timeStamp =  this.timeStamp.format(ofLocalizedTime(FormatStyle.MEDIUM));
        return timeStamp + " " + comunicat;
    }
}
