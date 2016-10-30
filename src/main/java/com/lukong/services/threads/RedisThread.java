package com.lukong.services.threads;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukong on 2016/10/30.
 */
public class RedisThread implements Runnable {
    private static Logger LOG= LoggerFactory.getLogger(RedisThread.class);
    private boolean flag=true;


    @Override
    public void run() {
        LOG.info("进入redis缓存线程");
    }

    public void stop(){
        flag=false;
    }
}
