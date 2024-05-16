package com.example.patrones;

import com.example.patrones.command.Command;

public class PaymentProcessor {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void processPayment() {
        if (command != null) {
            command.execute();
        }
    }

    public void undoPayment(String idLastPayment) {
        if (command != null) {
            System.out.println("Undoing payment with id: " + idLastPayment);
            command.undo(idLastPayment);
        }
    }
}
