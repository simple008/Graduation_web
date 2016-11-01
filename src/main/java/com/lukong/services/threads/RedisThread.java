package com.lukong.services.threads;

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
        String entry_class="com.bupt.flink.apps.demo.dataRev.DataReceive";

        String sensor=sensorDaoImpl.getSensor(jid);
        System.out.println("sensor: "+sensor);

        /*用任务的ID作为redis缓存的KEY*/
        String program_args="--sensor "+sensor +"--key "+jid +"--way "+"redis";
        Map map=
                springRestClient.run(jarId,entry_class,program_args);

        if(map.get("jid")!=null)
            LOG.info("开始将数据缓存在Redis中");
        else {
            LOG.info("数据未能进入缓存中...");
        }
    }

    public void stop(){
        flag=false;
    }

}
