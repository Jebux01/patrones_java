package com.example.patrones.payments;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.patrones.observer.Observer;

public class PayPSE implements PayInterfaces {
    private static final Logger logger = Logger.getLogger(PayPSE.class.getName());
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void pay(double amount, String method) {
        notifyObservers(amount, method);
    }

    @Override
    public void undo(double amount) {
        // Undo payment
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(double amount, String method) {
        for (Observer observer : observers) {
            observer.update(amount, method);
        }
    }
}
