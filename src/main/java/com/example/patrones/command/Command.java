package com.example.patrones.command;

public interface Command {
    void execute();

    void undo(String idLastPayment);

    String generateUUID();
}
