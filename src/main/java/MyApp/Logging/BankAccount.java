package MyApp.Logging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BankAccount {
    private static final Logger logger = LogManager.getLogger(BankAccount.class);

    public static Logger getLogger() {
        return logger;
    }

    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        logger.info("Deposit of $" + amount + " into account " + accountNumber + ". New balance: $" + balance);
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            logger.info("Withdrawal of $" + amount + " from account " + accountNumber + ". New balance: $" + balance);
        } else {
            logger.error("Insufficient funds in account " + accountNumber + " to withdraw $" + amount);
        }
    }

    public static void main(String[] args) {

        System.out.println(getLogger().getLevel().intLevel());

        BankAccount account = new BankAccount("123456789", 1000.0);
        account.deposit(500.0);
        account.withdraw(200.0);
        account.withdraw(1500.0);
    }
}
