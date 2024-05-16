package com.example.patrones.payments;

import com.example.patrones.observer.Observer;

public interface PayInterfaces {
    void pay(double amount, String method);

    void undo(double amount);

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(double amount, String method);
}