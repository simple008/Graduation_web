package com.lukong.services.threads;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lukong.services.SpringRestClient;
import com.lukong.services.dao.SensorDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;


/**
 * Created by lukong on 2016/10/30.
 */
public class RedisThread implements Runnable {
    private static Logger LOG= LoggerFactory.getLogger(RedisThread.class);
    private volatile boolean flag=true;
    private SpringRestClient springRestClient=new SpringRestClient();
    private String jid=null;
    private SensorDaoImpl sensorDaoImpl=new SensorDaoImpl();
    private Hashtable jobid_table=null;
    private JSONObject jsonObject=new JSONObject();


    public RedisThread(){}
    public RedisThread(String jid){
        this.jid=jid;
    }
    public RedisThread(String jid,Hashtable jobid_table){
        this.jid=jid;
        this.jobid_table=jobid_table;
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

        UUID key=UUID.randomUUID();//随机生成key，由于缓存与处理逻辑之间的桥梁

        /*用任务的ID作为redis缓存的KEY*/
        String program_args_cache=
                "--sensor "+sensor +" --key "+key +" --mode "+"redis";

        String program_args_process=
                "--sensor "+sensor +" --key "+key +" --topic "+topic;


        Map<String,Object> map=springRestClient.run(jarId,entry_class_cache,program_args_cache);

        Map<String,Object> map1=springRestClient.run(jarId,entry_class_process,program_args_process);

        String k= (String) map1.get("jobid");
        String v= (String) map.get("jobid");
        System.out.println("k: "+k+" v: "+v);

        /*将缓存策略的任务id与之前的任务id绑定*/
        jsonObject.put("pre",jid);
        jsonObject.put("cache",v);


        jobid_table.put(k,jsonObject);

        LOG.info("redis缓存完成");
    }

    public void stop(){
        flag=false;
    }


    public static void main(String ... args){
        RedisThread redisThread=new RedisThread("f76d0ae7ccca4e390e27ce6979585a0c");
        Thread thread=new Thread(redisThread);
        thread.start();
    }

}
