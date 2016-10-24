package com.lukong.utils;

/**
 * Created by lukong on 2016/10/24.
 */
public interface IRedis {
    boolean set(String key,String value);
    boolean set(String key,Object value);
    boolean delete(String key);
    Object get(String key);
    <T> T get(String key,Class<T> clazz);
}
