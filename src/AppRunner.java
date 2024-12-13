import enums.ActionLetter;
import model.*;
import payment.CardAcceptor;
import payment.CoinAcceptor;
import payment.PaymentProcessor;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private PaymentProcessor paymentMethod;

    private final PaymentProcessor coinAcceptor = new CoinAcceptor(0);
    private final PaymentProcessor cardAcceptor = new CardAcceptor(0);

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        paymentMethod = coinAcceptor;
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        print("Баланс: " + paymentMethod.getBalance() + " (" + paymentMethod.getPaymentMethod() + ")");

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (paymentMethod.getBalance() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        print(" m - Сменить способ оплаты");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().strip();

        switch (action.toLowerCase()) {
            case "a":
                paymentMethod.addFunds(10);
                print("Вы пополнили баланс на 10\n");
                return;
            case "m":
                changePayment();
                return;
            case "h":
                isExit = true;
                return;
        }

        try {
            boolean productFound = false;
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    paymentMethod.processPayment(products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName() + "\n");
                    productFound = true;
                    break;
                }
            }
            if (!productFound) {
                print("Выбрана недопустимая команда!\n");
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква. Попробуйте еще раз.\n");
            startSimulation();
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }

    private void changePayment() {
        print("""
                
                Выберите тип платежной системы:
                1. - Монетоприемник
                2. - Банковские карты""");


        while (true) {
            String choice = fromConsole().strip();

            switch (choice) {
                case "1":
                    paymentMethod = coinAcceptor;
                    print("Выбран монетоприемник\n");
                    return;
                case "2":
                    paymentMethod = cardAcceptor;
                    print("Выбрана система оплаты картой\n");
                    return;
                default:
                    System.out.print("Неверный выбор. Попробуйте снова (1 или 2):");
            }
        }
    }
}
