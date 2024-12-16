package main.java.com;

import java.util.concurrent.atomic.*;

/**
 * AtomicBankAccount
 */
public class AtomicBankAccount{
    private AtomicInteger balance = new AtomicInteger(100);
    public int getBalanc() {
        return balance.get();
    }

    public void decreaseBalance(String name, int amount) {
        int initialBalance = getBalanc();
        if (initialBalance >= amount) {
            boolean isOkey = balance.compareAndSet(initialBalance, initialBalance - amount);
            if (isOkey) {
            }
            else 
               System.out.println("I'm sorry. Not enough money " + name);
        }
        else
           System.out.println("Not enough money " + name);
    }

    
}
