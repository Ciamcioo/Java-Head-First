package main.java.com;

import java.util.concurrent.*;

/**
 * AppSynchronized
 */
public class AtomicApp{
    public static void main(String[] args) {
        AtomicBankAccount account = new AtomicBankAccount();
        AtomicClient monika = new AtomicClient("Monika", account, 100);
        AtomicClient chandler = new AtomicClient("Chandler", account, 50);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        fixedThreadPool.execute(monika);
        fixedThreadPool.execute(chandler);
        fixedThreadPool.shutdown();
    }
    
}
