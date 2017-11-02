package com.hello.util;


import com.hello.function.PropClient;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisPoolFactory {

    private JedisPoolFactory() {

    }

    private static class SingletonHolder {
        private static final JedisPool REDIS_LOCK_JEDIS_POOL = createRedisLockJedisPool();
    }

    public static JedisPool getRedisLockJedisPool() {
        return SingletonHolder.REDIS_LOCK_JEDIS_POOL;
    }
    
    private static JedisPool createRedisLockJedisPool() {
        String host = PropClient.get("lockRedis.host");
        int port = PropClient.getInt("lockRedis.port");
        String password = PropClient.get("lockRedis.password");

        return createPool(host, port, password, "RedisShareLock");
    }

    private static JedisPool createPool(String host, int port,  String password, String clientName) {
        int database = PropClient.getInt("redis.db");
        int connectionTimeout = PropClient.getInt("redis.timeout");
        int soTimeout = PropClient.getInt("redis.so_timeout");

        int minIdle = PropClient.getInt("jedis.pool.minIdle");
        int maxIdle = PropClient.getInt("jedis.pool.maxIdle");
        int maxTotal = PropClient.getInt("jedis.pool.maxTotal");
        boolean testOnBorrow = Boolean.valueOf(PropClient.get("jedis.pool.testOnBorrow"));
        boolean testOnReturn = Boolean.valueOf(PropClient.get("jedis.pool.testOnReturn"));

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        return new JedisPool(poolConfig, host, port, connectionTimeout, soTimeout, password,
                database, clientName, false, null, null, null);
    }

}
