package main.java.com;

/**
 * BankAccountSynchronized
 */
public class BankAccountSynchronized {
    private int balance = 100;
    public int getBalanc() {
        return balance;
    }

    public synchronized void decreaseBalance(String name, int amount) {
        if (getBalanc() >= amount)
            balance -= amount;
        else
           System.out.println("Not enough moenye " + name);
    }

    
}
