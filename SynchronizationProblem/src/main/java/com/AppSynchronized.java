package main.java.com;

import java.util.concurrent.*;

/**
 * AppSynchronized
 */
public class AppSynchronized {
    public static void main(String[] args) {
        BankAccountSynchronized account = new BankAccountSynchronized();
        ClientSynchronized monika = new ClientSynchronized("Monika", account, 100);
        ClientSynchronized chandler = new ClientSynchronized("Chandler", account, 50);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        fixedThreadPool.execute(monika);
        fixedThreadPool.execute(chandler);
        fixedThreadPool.shutdown();
    }
    
}
