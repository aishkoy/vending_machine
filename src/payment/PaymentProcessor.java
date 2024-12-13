package payment;

public interface PaymentProcessor {
    boolean processPayment(int amount);
    int getBalance();
    void addFunds(int amount);
    String getPaymentMethod();
}
