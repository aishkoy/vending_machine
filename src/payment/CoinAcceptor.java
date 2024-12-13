package payment;

public class CoinAcceptor implements PaymentProcessor{
    private int balance;

    public CoinAcceptor(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public boolean processPayment(int amount) {
        if(balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addFunds(int amount) {
        balance += amount;
    }

    @Override
    public String getPaymentMethod() {
        return "Монеты";
    }
}
