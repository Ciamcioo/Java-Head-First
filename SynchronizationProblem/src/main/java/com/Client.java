package main.java.com;

public class Client implements Runnable {
    private final String name;
    private final BankAccount account;
    private final int moneyToSpend;

    public Client(String name, BankAccount account, int moneyToSpend) {
       this.name = name;
       this.account = account;
       this.moneyToSpend = moneyToSpend; 
    }

    public void run() {
        shopping(moneyToSpend);
    }

    private void shopping(int amount) {
        synchronized(account) {
            if (amount <= account.getBalanc()) {
                System.out.println(name + " spends money from the bank account.");
                account.decreaseBalance(amount);
                System.out.println(name + " finished doing shopping.");
            }
            else {
                System.out.println("There is not enough banalnc to finish the operation."); 
            }
        }
    }
    
}
