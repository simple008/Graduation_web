package com.lukong.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * Created by lukong on 2016/10/24.
 * 用于连接redis，以及相关的操作
 */
public class RedisUtil implements IRedis{
    public static JedisPool jedisPool;
    public static Logger LOG= LoggerFactory.getLogger(RedisUtil.class);

    static {
        ResourceBundle resourceBundle=ResourceBundle.getBundle("redis");
        int maxActive=Integer.parseInt(resourceBundle.getString("redis.pool.maxActive"));
        int maxIdle=Integer.parseInt(resourceBundle.getString("redis.pool.maxIdle"));
        int maxWait=Integer.parseInt(resourceBundle.getString("redis.pool.maxWait"));

        String ip=resourceBundle.getString("redis.ip");
        int port=Integer.parseInt(resourceBundle.getString("redis.port"));

        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);

        jedisPool=new JedisPool(config,ip,port);
    }

    public static void main(String... args){
        System.out.println();
    }

    @Override
    public boolean set(String key, String value) {
        return false;
    }

    @Override
    public boolean set(String key, Object value) {
        return false;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }
}
