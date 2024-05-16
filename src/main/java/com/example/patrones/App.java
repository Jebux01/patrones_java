package com.example.patrones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.example.patrones.decorator.DiscountDecoratorImpl;
import com.example.patrones.observer.PaymentObserver;
import com.example.patrones.payments.PayInterfaces;
import com.example.patrones.payments.PayCard;
import com.example.patrones.payments.PayPSE;
import com.example.patrones.payments.PayCash;
import com.example.patrones.command.Command;
import com.example.patrones.command.PayCardCommand;
import com.example.patrones.command.PayCashCommand;
import com.example.patrones.command.PayPSECommand;

public class App {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static PayInterfaces paymentMethod;
    private static PaymentProcessor paymentProcessor = new PaymentProcessor();
    private String paymentSelected;

    public static void main(String[] args) throws IOException {

        Map<String, PayInterfaces> strategy = new HashMap<>();

        strategy.put("1", new PayCard());
        strategy.put("2", new PayPSE());
        strategy.put("3", new PayCash());

        // Creamos el observer
        PaymentObserver observer = new PaymentObserver();

        while (true) {
            System.out.print("\033\143");
            System.out.println("Select payment method: ");
            System.out.println("1. Card");
            System.out.println("2. PSE");
            System.out.println("3. Cash");
            System.out.println("4. Undo payment");
            System.out.println("5. Exit");
            System.out.println("Enter the number of the payment method: ");

            String paymentSelected = reader.readLine();

            if (paymentSelected.equals("5")) {
                break;
            }

            if (paymentSelected.equals("4")) {
                System.out.print("Enter the id of the payment to undo: ");
                String idLastPayment = reader.readLine();
                paymentProcessor.undoPayment(idLastPayment);
                continue;
            }

            try {
                System.out.print("\033\143");
                System.out.println("Enter the amount to pay: " + "\n");
                double amount = Double.parseDouble(reader.readLine());

                paymentMethod = strategy.get(paymentSelected);

                if (paymentMethod == null) {
                    throw new CustomException("Invalid payment method");
                }

                // Registramos el observer
                paymentMethod.registerObserver(observer);

                // Obtener el nombre de la clase
                String nameOfMethod = paymentMethod.getClass().getName();
                nameOfMethod = nameOfMethod.substring(nameOfMethod.lastIndexOf('.') + 1);

                // Preguntar si se aplica un descuento
                System.out.println("Do you want to apply a discount? (yes/no)");
                String applyDiscount = reader.readLine();

                if (applyDiscount.equalsIgnoreCase("yes")) {
                    System.out.println("Enter the discount percentage: ");
                    double discountRate = Double.parseDouble(reader.readLine());
                    paymentMethod = new DiscountDecoratorImpl(paymentMethod, discountRate);
                }

                // seteamos el comando por el tipo de pago
                Command payCommand = setPaymentCommand(paymentMethod, amount, nameOfMethod);
                paymentProcessor.setCommand(payCommand);
                paymentProcessor.processPayment();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Command setPaymentCommand(PayInterfaces paymentMethod, double amount, String nameOfMethod) {
        if (paymentMethod instanceof PayCard) {
            return new PayCardCommand(paymentMethod, amount, nameOfMethod);
        }

        if (paymentMethod instanceof PayPSE) {
            return new PayPSECommand(paymentMethod, amount, nameOfMethod);
        }

        if (paymentMethod instanceof PayCash) {
            return new PayCashCommand(paymentMethod, amount, nameOfMethod);
        }

        return null;
    }
}
