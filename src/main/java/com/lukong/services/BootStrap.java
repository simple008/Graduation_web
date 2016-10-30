package com.lukong.services;


import com.lukong.services.threads.KafkaThread;
import com.lukong.services.threads.MetricsThread;
import com.lukong.services.threads.RedisThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lukong on 2016/10/22.
 * 根据数据处理的速度，将数据进行缓存
 */
public class BootStrap {

    private static Logger LOG= LoggerFactory.getLogger(BootStrap.class);
    private static ExecutorService threadPool=null;


    public static void main(String... args) throws InterruptedException, ExecutionException {
        SpringRestClient springRestClient=new SpringRestClient();
        Hashtable table_rate=new Hashtable();
        threadPool= Executors.newCachedThreadPool();

//        MetricsThread metricsThread=new MetricsThread(springRestClient,table_rate);
//        Thread thread=new Thread(metricsThread);
//        thread.start();

        /*利用线程池进行管理监控任务线程*/
        threadPool.execute(new MetricsThread(springRestClient,table_rate));


        while (true){

            double rate=0;
            if(table_rate.get("rate")==null){
                rate=0;
            }else {
                rate= (double) table_rate.get("rate");
            }

            /*生成随机处理速度rate测试*/
            rate=Math.random();
            LOG.info("random rate: "+rate);

            if(rate>0.98){
                /*数据处理速度在98%上，正常处理*/
                LOG.info("parse rate: "+rate +" 采用正常策略");

            } else if(rate<=0.98&&rate>=0.9){
                /* 数据处理数据在[90%,98%]
                 * 将数据存储在redis中
                 */
                LOG.info("parse rate: "+rate+" 采用redis缓存策略");
                Future future=threadPool.submit(new RedisThread());
                System.out.println("future_redis result: "+future.isDone());

            }else if(rate<0.9&&rate>0){
                /*数据处理速度小于90%则将数据推送到kafka消息队列中，数据解析在从消息队列中获取数据解析*/
                LOG.info("parse rate: "+rate+" 采用kafka消息队列策略");

                Future future_kafka=threadPool.submit(new KafkaThread());
                System.out.println("future_kafka result: "+future_kafka.isDone());

            }else {
                LOG.info("parse rate: "+rate +" 没有数据流入");
            }

            Thread.sleep(500);
        }
    }
}
