package com.example.patrones.observer;

import java.util.logging.Logger;

public class PaymentObserver implements Observer {
    private static final Logger logger = Logger.getLogger(PaymentObserver.class.getName());

    @Override
    public void update(double amount, String method) {
        logger.info("Notification: Payment of " + amount + " has been processed with " + method + " method.");
    }
}
