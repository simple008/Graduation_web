package com.lukong.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lukong on 2016/10/24.
 * 单元测试RedisUtil
 */
public class RedisUtilTest {
    @Test
    public void cache(){
        boolean cache=RedisUtil.set("redis","demo");
        if(cache){
            System.out.println("cache success");
        }else {
            System.out.println("cache fail");
        }
    }

    @Test
    public void get(){
        String value= (String) RedisUtil.get("redis");
        System.out.println(value);
    }

    @Test
    public void cacheObject(){
        Map map=new HashMap();
        map.put("name","lukong1");
        map.put("age",24);

        boolean cache=RedisUtil.set("dj-ads-20140814",map);
        if (cache){
            System.out.println("cache success");
        }else {
            System.out.println("cache fail");
        }
    }
}
