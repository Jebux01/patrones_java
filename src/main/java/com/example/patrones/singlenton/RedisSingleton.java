package com.example.patrones.singlenton;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisSingleton {
    private static RedisSingleton instance;
    private JedisPool jedisPool;

    private RedisSingleton() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(-1);
        poolConfig.setBlockWhenExhausted(true);

        jedisPool = new JedisPool(poolConfig, "localhost", 6379);
    }

    public static synchronized RedisSingleton getInstance() {
        if (instance == null) {
            instance = new RedisSingleton();
        }
        return instance;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void close() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }
}
