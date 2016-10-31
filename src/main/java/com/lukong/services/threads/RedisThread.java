package com.lukong.services.threads;


import com.lukong.services.SpringRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by lukong on 2016/10/30.
 */
public class RedisThread implements Runnable {
    private static Logger LOG= LoggerFactory.getLogger(RedisThread.class);
    private boolean flag=true;
    private SpringRestClient springRestClient=new SpringRestClient();
    private String jid=null;

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

        /*根据jid，利用JPA获取数据库获取执行参数*/

        String program_args="";
        springRestClient.run(jarId,entry_class,program_args);

    }

    public void stop(){
        flag=false;
    }


    public static void main(String... args){
        RedisThread redisThread=new RedisThread();
        Thread thread=new Thread(redisThread);
        thread.start();
    }
}
