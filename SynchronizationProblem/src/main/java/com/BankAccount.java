package main.java.com;

/**
 * BankAccount
 */
public class BankAccount {
    private int balance = 100;
    public int getBalanc() {
        return balance;
    }

    public void decreaseBalance(int amount) {
        balance -= amount;
        if (balance < 0) 
            System.out.println("Limit exceeded");
    }
    
}
