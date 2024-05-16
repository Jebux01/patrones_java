package com.example.patrones.decorator;

import com.example.patrones.observer.Observer;
import com.example.patrones.payments.PayInterfaces;

public abstract class DiscountDecorator implements PayInterfaces {
    protected PayInterfaces decoratedPaymentMethod;

    public DiscountDecorator(PayInterfaces decoratedPaymentMethod) {
        this.decoratedPaymentMethod = decoratedPaymentMethod;
    }

    @Override
    public void pay(double amount, String method) {
        decoratedPaymentMethod.pay(amount, method);
    }

    @Override
    public void undo(double amount) {
        decoratedPaymentMethod.undo(amount);
    }

    @Override
    public void registerObserver(Observer observer) {
        decoratedPaymentMethod.registerObserver(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        decoratedPaymentMethod.removeObserver(observer);
    }

    @Override
    public void notifyObservers(double amount, String method) {
        decoratedPaymentMethod.notifyObservers(amount, method);
    }
}
