package com.lukong.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ResourceBundle;

/**
 * Created by lukong on 2016/10/24.
 * 用于连接redis，以及相关的操作
 */
public class RedisUtil{
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



    public static boolean set(String key, String value) {
        Jedis jedis=null;
        jedis=jedisPool.getResource();
        if(jedis==null){
            return false;
        }
        jedis.set(key,value);

        jedisPool.returnResource(jedis);
        return true;
    }

    public static boolean set(String key, Object value) {
        Jedis jedis=null;
        jedis=jedisPool.getResource();
        String objectJson=JSONObject.toJSONString(value);
        if(jedis==null){
            return false;
        }
        jedis.set(key,objectJson);

        jedisPool.returnResource(jedis);
        return true;
    }

    public static boolean delete(String key) {
        Jedis jedis=null;
        jedis=jedisPool.getResource();
        if(jedis==null){
            return false;
        }
        jedis.del(key);

        jedisPool.returnResource(jedis);

        return true;
    }

    public static Object get(String key) {
        Jedis jedis=null;
        jedis=jedisPool.getResource();
        if (jedis==null){
            return null;
        }
        Object value=jedis.get(key);

        jedisPool.returnResource(jedis);

        return value;
    }

    public static <T> T get(String key, Class<T> clazz) {
        Jedis jedis=null;
        jedis=jedisPool.getResource();

        if(jedis==null){
            return null;
        }
        String value=jedis.get(key);

        jedisPool.returnResource(jedis);

        return JSONObject.parseObject(value,clazz);
    }
}
