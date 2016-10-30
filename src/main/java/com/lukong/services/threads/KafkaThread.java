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
        while (flag) {
            LOG.info("进入kafka队列线程");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(),e);
            }
        }
    }

    public void stop(){
        flag=false;
    }
}
