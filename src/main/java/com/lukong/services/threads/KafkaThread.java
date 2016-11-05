package com.lukong.services.threads;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lukong.services.SpringRestClient;
import com.lukong.services.dao.SensorDaoImpl;
import com.lukong.utils.KafkaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lukong on 2016/10/30.
 */
public class KafkaThread implements Runnable {

    private static Logger LOG= LoggerFactory.getLogger(KafkaThread.class);
    private volatile boolean flag=true;
    private SensorDaoImpl sensorDaoImpl=new SensorDaoImpl();
    private SpringRestClient springRestClient=new SpringRestClient();
    private String jid=null;
    private Hashtable jobid_table=null;
    private JSONObject jsonObject=new JSONObject();

    public KafkaThread(){}
    public KafkaThread(String jid){
        this.jid=jid;
    }
    public KafkaThread(String jid,Hashtable jobid_table){
        this.jid=jid;
        this.jobid_table=jobid_table;
    }
    @Override
    public void run() {
        LOG.info("进入kafka队列线程");

        String jarId= (String) springRestClient.getJars().get("id");
        String entry_class_cache="com.bupt.flink.apps.demo.dataRev.DataReceive";
        String entry_class_process="com.bupt.flink.apps.demo.FlinkSensorAdapterUpQueue";


        /*------通过jid获取相关信息-------*/

        JSONObject jsonObject=
                JSON.parseObject(sensorDaoImpl.getSensor(jid));

        String sensor= (String) jsonObject.get("sensor");
        String topic= (String) jsonObject.get("topic_up");

        System.out.println("sensor: "+sensor);
        System.out.println("topic: "+topic);

        UUID key=UUID.randomUUID();

        /*用任务的ID作为redis缓存的KEY*/
        String program_args_cache=
                "--sensor "+sensor +" --key "+key +" --mode kafka";
        String program_args_process=
                "--sensor "+sensor +" --key "+key +" --topic "+topic;

        Map<String,Object> map=
            springRestClient.run(jarId,entry_class_cache,program_args_cache);
        Map<String,Object> map1=
            springRestClient.run(jarId,entry_class_process,program_args_process);

        String k= (String) map1.get("jobid");
        String v= (String) map.get("jobid");
        System.out.println("k: "+k+" v: "+v);

        jsonObject.put("pre",jid);
        jsonObject.put("cache",v);

        jobid_table.put(k,jsonObject);

        LOG.info("kafka队列线程结束");
    }

    public void stop(){
        flag=false;
    }

    public static void main(String ... args){
        KafkaThread kafkaThread=new KafkaThread("f80a0d3a1bd37e211bb761a4d82254e9");
        Thread thread=new Thread(kafkaThread);
        thread.start();

    }
}
