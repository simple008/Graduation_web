package com.lukong.services.threads;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lukong.services.SpringRestClient;
import com.lukong.services.dao.SensorDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * Created by lukong on 2016/10/30.
 */
public class RedisThread implements Runnable {
    private static Logger LOG= LoggerFactory.getLogger(RedisThread.class);
    private boolean flag=true;
    private SpringRestClient springRestClient=new SpringRestClient();
    private String jid=null;
    private SensorDaoImpl sensorDaoImpl=new SensorDaoImpl();

    public RedisThread(){}
    public RedisThread(String jid){
        this.jid=jid;
    }

    @Override
    public void run() {
        LOG.info("进入redis缓存线程");
        /*通过配置信息，获取不同的连接方式获取数据，并将获取的数据缓存在redis中
        * 通过RESTful API开启接收原始数据包的任务*/

        String jarId= (String) springRestClient.getJars().get("id");
        String entry_class_cache="com.bupt.flink.apps.demo.dataRev.DataReceive";
        String entry_class_process="com.bupt.flink.apps.demo.FlinkSensorAdapterUpCache";


        /*------通过jid获取相关信息-------*/

        JSONObject jsonObject=
                JSON.parseObject(sensorDaoImpl.getSensor(jid));

        String sensor= (String) jsonObject.get("sensor");
        String topic= (String) jsonObject.get("topic_up");

        System.out.println("sensor: "+sensor);
        System.out.println("topic: "+topic);

        /*用任务的ID作为redis缓存的KEY*/
        String program_args="--sensor "+sensor +"--key "+jid +"--way "+"redis "+"--topic "+topic;
        Map map_cache=
                springRestClient.run(jarId,entry_class_cache,program_args);

        Map map_process=
                springRestClient.run(jarId,entry_class_process,program_args);


        if(map_cache.get("jid")!=null)
            LOG.info("开始将数据缓存在Redis中");
        else {
            LOG.info("数据未能进入缓存中...");
        }

        if(map_process.get("jid")!=null){
            LOG.info("开始解析数据");
        }else {
            LOG.info("未开始解析数据");
        }
    }

    public void stop(){
        flag=false;
    }


    public static void main(String ... args){
        RedisThread redisThread=new RedisThread("0908568485a3eec1e7c18ec8921522a1");
        Thread thread=new Thread(redisThread);
        thread.start();
    }

}
