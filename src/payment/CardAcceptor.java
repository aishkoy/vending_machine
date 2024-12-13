package payment;

public class CardAcceptor implements PaymentProcessor{
    private int balance;

    public CardAcceptor(int initialBalance) {
        balance = initialBalance;
    }

    @Override
    public boolean processPayment(int amount) {

        if(balance >= amount) {
            balance -= amount;
            System.out.println("Оплата прошла успешно!");
            return true;
        }

        System.out.println("Недостаточно средств!");
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
        return "Банковская карта";
    }
}

