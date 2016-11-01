package com.lukong.services.threads;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lukong on 2016/10/30.
 */
public class KafkaThread implements Runnable {

    private static Logger LOG= LoggerFactory.getLogger(KafkaThread.class);
    private boolean flag=true;

    @Override
    public void run() {
        LOG.info("进入kafka队列线程");

        LOG.info("采用kafka策略处理中...");

        LOG.info("kafka队列线程结束");
    }

    public void stop(){
        flag=false;
    }
}
