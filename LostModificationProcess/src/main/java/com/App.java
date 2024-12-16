package main.java.com;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * App
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(10000);
    BankAccount account = new BankAccount();
        for (int i = 0; i < 1000; i++) { 
            executor.execute(() ->  account.increment());
            executor.execute(() -> account.synchronizedIncrementation());
            executor.execute(() -> account.atomicIncrementation());
        }
        
    executor.shutdown();
    if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
        System.out.println("Balance: " + account.getBalance());
        System.out.println("Synchronized balance: " + account.getSynchronizedBalanc());
        System.out.println("Atomic increamentation: " + account.getAtomicBalance());
    }
    }
}

class BankAccount{
    private int balance = 0;
    private int synchronizedBalanc = 0;
    private AtomicInteger atmoicBalance = new AtomicInteger(0);
    
    public void increment() {
        balance++;
    }

    public synchronized void synchronizedIncrementation() {
        synchronizedBalanc++;
    }

    public void atomicIncrementation() {
        atmoicBalance.incrementAndGet();
    }

    public int getBalance() {
        return balance;
    }
    
    public int getSynchronizedBalanc() {
        return synchronizedBalanc;
    }

    public int getAtomicBalance() {
        return  atmoicBalance.get();
    }

   
        
}
