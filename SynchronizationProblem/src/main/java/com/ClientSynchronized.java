package main.java.com;

/**
 * ClientSynchronized
 */
public class ClientSynchronized implements Runnable{
    private final String name;
    private final BankAccountSynchronized account;
    private final int moneyToSpend;

    public ClientSynchronized(String name, BankAccountSynchronized account, int moneyToSpend ) {
        this.name = name;
        this.account = account;
        this.moneyToSpend = moneyToSpend;
    }

    
    public void run() {
        shopping(moneyToSpend);
    }

    private void shopping(int amount) {
        System.out.println(name + " spends money from the bank account.");
        account.decreaseBalance(name, amount);
        System.out.println(name + " finished doing shopping.");
    }
    
}
