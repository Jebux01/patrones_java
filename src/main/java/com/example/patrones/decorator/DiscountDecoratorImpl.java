package com.example.patrones.decorator;

import java.util.logging.Logger;

import com.example.patrones.payments.PayInterfaces;

public class DiscountDecoratorImpl extends DiscountDecorator {
    private static final Logger logger = Logger.getLogger(DiscountDecoratorImpl.class.getName());
    private double discountRate;

    public DiscountDecoratorImpl(PayInterfaces decoratedPaymentMethod, double discountRate) {
        super(decoratedPaymentMethod);
        this.discountRate = discountRate / 100;
    }

    @Override
    public void pay(double amount, String method) {
        double discountedAmount = amount - (amount * discountRate);
        logger.info("Applying discount of " + (discountRate * 100) + "%");
        logger.info("Original amount: " + amount + ", Discounted amount: " + discountedAmount);
        super.pay(discountedAmount, method);
    }
}
