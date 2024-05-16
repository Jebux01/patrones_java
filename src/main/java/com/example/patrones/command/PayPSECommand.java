package com.example.patrones.command;

import java.util.UUID;
import java.util.logging.Logger;

import com.example.patrones.payments.PayInterfaces;
import com.example.patrones.singlenton.RedisSingleton;

import redis.clients.jedis.Jedis;

public class PayPSECommand implements Command {
    private static final Logger logger = Logger.getLogger(PayPSECommand.class.getName());
    private PayInterfaces payMethod;
    private double amount;
    private String method;

    public PayPSECommand(PayInterfaces payMethod, double amount, String method) {
        this.payMethod = payMethod;
        this.amount = amount;
        this.method = method;
    }

    @Override
    public String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Override
    public void execute() {
        payMethod.pay(amount, method);
        try (Jedis jedis = RedisSingleton.getInstance().getJedis()) {
            String id = generateUUID();
            jedis.set(id, "Paid " + amount + " with " + method + " at " + System.currentTimeMillis());
            logger.info("Payment registered with id: " + id);
        }
    }

    @Override
    public void undo(String idLastPayment) {
        payMethod.undo(amount);
        try (Jedis jedis = RedisSingleton.getInstance().getJedis()) {
            jedis.del(idLastPayment);
            logger.info("Payment with id: " + idLastPayment + " has been deleted");
        }
    }
}
