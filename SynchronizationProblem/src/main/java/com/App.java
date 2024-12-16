package main.java.com;

import java.util.concurrent.*;

/**
 * App
 */
public class App {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        Client monika = new Client("Monika", account, 100);
        Client chandler = new Client("Chandler", account, 50);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        fixedThreadPool.execute(monika);
        fixedThreadPool.execute(chandler);
        fixedThreadPool.shutdown();

    }
    
}
