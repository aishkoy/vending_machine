package payment;

import java.util.Scanner;

public class CardAcceptor implements PaymentProcessor{
    private int balance;
    private static final Scanner sc = new Scanner(System.in);

    public CardAcceptor(int initialBalance) {
        balance = initialBalance;
    }

    @Override
    public boolean processPayment(int amount) {
        System.out.print("Введите номер карты (6 цифр): ");
        String cardNumber = sc.nextLine().strip();

        if(!isCardNumberValid(cardNumber)) {
            System.out.println("Неверный номер карты!");
            return processPayment(amount);
        }

        String confirmCode;

        do {
            System.out.print("Введите код подтверждения (3 цифры): ");
            confirmCode = sc.nextLine().strip();
            if(!isConfirmCodeValid(confirmCode)) {
                System.out.println("Неверный код подтверждения");
            }
        } while (!isConfirmCodeValid(confirmCode));

        if(balance >= amount) {
            balance -= amount;
            System.out.println("Оплата прошла успешно!");
            return true;
        }

        System.out.println("Недостаточно средств!");
        return false;
    }

    private boolean isCardNumberValid(String cardNumber) {
        return cardNumber.matches("[0-9]{6}") && !cardNumber.isBlank();
    }

    private boolean isConfirmCodeValid(String confirmationCode) {
        return confirmationCode.matches("[0-9]{3}") && !confirmationCode.isBlank();
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

