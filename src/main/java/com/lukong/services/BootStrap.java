package com.lukong.services;


import com.alibaba.fastjson.JSONObject;
import com.lukong.services.dao.SensorDaoImpl;
import com.lukong.services.threads.KafkaThread;
import com.lukong.services.threads.MetricsThread;
import com.lukong.services.threads.RedisThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.*;
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
    private static HashSet set=new HashSet();
    private static HashMap map=new HashMap();
    private static SensorDaoImpl sensorDaoImpl=new SensorDaoImpl();


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

            /*-----解析json数据-------*/

            List msg= (List) table_rate.get("msg");

            if(msg==null){
                continue;
            }

            for(int i=0;i<msg.size();i++){
                JSONObject json= (JSONObject) msg.get(i);
                //System.out.println("main json:"+json);
                if(json.get("rate")==null){
                    rate=0;
                }else {
                    rate= (double) json.get("rate");
                    jid= (String) json.get("jid");
                }

            /*-------随机概率测试-------------*/

            /*生成随机处理速度rate测试*/
                rate=Math.random();

                /*设定固定值分块测试*/
                rate=0.95;
                //LOG.info("random rate: "+rate);
                rate_str=new DecimalFormat("#.00").format(rate*100);


                double normal_lower= (double) properties.get("normal.rate.lower");
                double redis_upper= (double) properties.get("redis.rate.upper");
                double redis_lower= (double) properties.get("redis.rate.lower");
                double kafka_upper= (double) properties.get("kafka.rate.upper");
                double kafka_lower= (double) properties.get("kafka.rate.lower");

                /*--------调度策略-------------*/

                if(rate>normal_lower){
                /*数据处理速度在98%上，正常处理*/
                    LOG.info("job: "+jid+" parse rate: "+rate_str+"%"+" 采用正常策略");

                /*如果任务的处理速度上升，将将任务恢复正常处理策略*/
                    if(set.contains(jid)){
                        LOG.info("将缓存的任务恢复正常策略...");
                        //springRestClient.cancel(jid);
                    }

                } else if(rate<=redis_upper&&rate>=redis_lower){
                /* 数据处理数据在[90%,98%]
                 * 将数据存储在redis中
                 */

                /*增加判断条件:
                * 处理过的Job不需要再处理一遍*/
                    if(set.contains(jid)){
                        if(map.get(jid)=="redis")
                            LOG.info("job: "+jid+" 已采用redis缓存策略");
                        else if(map.get(jid)=="kafka"){
                            LOG.info("job: "+jid+" 已采用kafka消息队列策略");
                        }
                    }


                    if(flag_redis&&!set.contains(jid)){
                        LOG.info("job: "+jid+"parse rate: "+rate_str+"%"+" 采用redis缓存策略");

                        springRestClient.cancel(jid);
                        Thread.sleep(5000);

                        set.add(jid);
                        map.put(jid,"redis");
                        /*将jid传入执行线程*/
                        System.out.println("jid: "+jid);
                        future_redis=threadPool.submit(new RedisThread(jid));
                        Thread.sleep(10000);
                        System.out.println("future_redis result: "+future_redis.isDone());
                        flag_redis=future_redis.isDone();
                    }


                }else if(rate<kafka_upper&&rate>kafka_lower){
                /*数据处理速度小于90%则将数据推送到kafka消息队列中，数据解析在从消息队列中获取数据解析*/

                    if(set.contains(jid)){
                        if(map.get(jid)=="kafka")
                            LOG.info("job: "+jid+" 已采用kafka消息队列策略");
                        else if(map.get(jid)=="redis"){
                            LOG.info("job: "+jid+" 已采用redis缓存策略");
                        }
                    }

                    if(flag_kafka&&!set.contains(jid)){

                        LOG.info("job: "+jid+" parse rate: "+rate_str+"%"+" 采用kafka消息队列策略");

                        springRestClient.cancel(jid);
                        Thread.sleep(5000);

                        set.add(jid);
                        map.put(jid,"kafka");

                        System.out.println("jid: "+jid);
                        future_kafka=threadPool.submit(new KafkaThread(jid));
                        Thread.sleep(10000);
                        System.out.println("future_kafka result: "+future_kafka.isDone());
                        flag_kafka=future_kafka.isDone();
                    }

                }else {
                    LOG.info("parse rate: "+rate_str+"%"+" 没有数据流入");
                }

                Thread.sleep(2000);
            }

        }
    }
}
