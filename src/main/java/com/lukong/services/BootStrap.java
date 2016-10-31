package com.lukong.services;


import com.lukong.services.threads.KafkaThread;
import com.lukong.services.threads.MetricsThread;
import com.lukong.services.threads.RedisThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;
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
    private static Future future_redis=null;
    private static Future future_kafka=null;
    private static boolean flag_redis=true;
    private static boolean flag_kafka=true;
    private static Properties properties=new Properties();


    static {
        ResourceBundle resourceBundle=ResourceBundle.getBundle("cachePolicy");
        double normal_lower= Double.parseDouble(resourceBundle.getString("normal.rate.lower")) ;
        double redis_upper= Double.parseDouble(resourceBundle.getString("redis.rate.upper")) ;
        double redis_lower= Double.parseDouble(resourceBundle.getString("redis.rate.lower")) ;
        double kafka_upper= Double.parseDouble(resourceBundle.getString("kafka.rate.upper")) ;
        double kafka_lower= Double.parseDouble(resourceBundle.getString("kafka.rate.lower")) ;

        properties.put("normal.rate.lower",normal_lower);
        properties.put("redis.rate.upper",redis_upper);
        properties.put("redis.rate.lower",redis_lower);
        properties.put("kafka.rate.upper",kafka_upper);
        properties.put("kafka.rate.lower",kafka_lower);
    }


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
            String rate_str=null;
            String jid=null;
            if(table_rate.get("rate")==null){
                rate=0;
            }else {
                rate= (double) table_rate.get("rate");
                rate_str=new DecimalFormat("#.00").format(rate*100);
                jid= (String) table_rate.get("jid");
            }



            /*生成随机处理速度rate测试*/
            rate=Math.random();
            LOG.info("random rate: "+rate);
            rate_str=new DecimalFormat("#.00").format(rate*100);


            double normal_lower= (double) properties.get("normal.rate.lower");
            double redis_upper= (double) properties.get("redis.rate.upper");
            double redis_lower= (double) properties.get("redis.rate.lower");
            double kafka_upper= (double) properties.get("kafka.rate.upper");
            double kafka_lower= (double) properties.get("kafka.rate.lower");


            if(rate>normal_lower){
                /*数据处理速度在98%上，正常处理*/
                LOG.info("parse rate: "+rate_str+"%"+" 采用正常策略");

            } else if(rate<=redis_upper&&rate>=redis_lower){
                /* 数据处理数据在[90%,98%]
                 * 将数据存储在redis中
                 */
                LOG.info("parse rate: "+rate_str+"%"+" 采用redis缓存策略");


                if(flag_redis){

                    /*将jid传入执行线程*/
                    future_redis=threadPool.submit(new RedisThread(jid));
                    System.out.println("future_redis result: "+future_redis.isDone());
                    flag_redis=future_redis.isDone();
                }


            }else if(rate<kafka_upper&&rate>kafka_lower){
                /*数据处理速度小于90%则将数据推送到kafka消息队列中，数据解析在从消息队列中获取数据解析*/
                LOG.info("parse rate: "+rate_str+"%"+" 采用kafka消息队列策略");

                if(flag_kafka){
                    future_kafka=threadPool.submit(new KafkaThread());
                    System.out.println("future_kafka result: "+future_kafka.isDone());
                    flag_kafka=future_kafka.isDone();
                }

            }else {
                LOG.info("parse rate: "+rate_str+"%"+" 没有数据流入");
            }

            Thread.sleep(500);
        }
    }
}
