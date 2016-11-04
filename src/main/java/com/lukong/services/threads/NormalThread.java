package com.lukong.services.threads;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lukong.services.SpringRestClient;
import com.lukong.services.dao.SensorDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by lukong on 2016/11/4.
 */
public class NormalThread implements Runnable {

    private String jid=null;
    private SensorDaoImpl sensorDaoImpl=new SensorDaoImpl();
    private SpringRestClient springRestClient=new SpringRestClient();
    private Logger LOG= LoggerFactory.getLogger(NormalThread.class);

    public NormalThread(){}
    public NormalThread(String jid){
        this.jid=jid;
    }

    @Override
    public void run() {
        LOG.info("进入正常策略");
        String jarId= (String) springRestClient.getJars().get("id");
        String entry_class="com.bupt.flink.apps.demo.FlinkSensorAdapterUp";

        /*------通过jid获取相关信息-------*/

        JSONObject jsonObject=
                JSON.parseObject(sensorDaoImpl.getSensor(jid));

        String sensor= (String) jsonObject.get("sensor");
        String topic= (String) jsonObject.get("topic_up");

        System.out.println("sensor: "+sensor);
        System.out.println("topic: "+topic);

        String program_args_up="--sensor "+sensor +" --topic "+topic;
        Map<String,Object> jobInfo_up=springRestClient.run(jarId,entry_class,program_args_up);
        String jobUp= (String) jobInfo_up.get("jobid");

        sensorDaoImpl.update(jobUp,sensor);
    }
}
