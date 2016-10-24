package com.lukong.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by lukong on 2016/10/22.
 * 根据数据处理的速度，将数据进行缓存
 */
public class BootStarp {

    public static Logger LOG= LoggerFactory.getLogger(BootStarp.class);

    public static void main(String... args){
        SpringRestClient src=new SpringRestClient();
        List<Map> jobs=src.getJobs();
        for (Map job:jobs) {
            String jid= (String) job.get("jid");
            //System.out.println("jid: "+jid);
            LOG.info("jid: "+jid);
            List<Map>metrics=src.getMetrics(jid);
            Map metrics_node_map=metrics.get(1);

            LOG.info("read-bytes:"+metrics_node_map.get("read-bytes"));
            LOG.info("write-bytes:"+metrics_node_map.get("write-bytes"));
            LOG.info("read-records:"+metrics_node_map.get("read-records"));
            LOG.info("write-records:"+metrics_node_map.get("write-records"));
            /*
                监督任务运行情况，根据每个任务处理数据的情况（读入记录和写入记录之比）
                进行判断，该数据需不需进行缓存到redis，若写入记录/读入记录<95%，则将
                数据缓存到redis中，任务开始从redis缓存队列中获取数据，进行解析，若写入
                记录/读入记录>=95%,则正常解析数据
            */


        }
    }
}
