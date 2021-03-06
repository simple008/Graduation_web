package com.lukong.services.threads;

import com.alibaba.fastjson.JSONObject;
import com.lukong.services.SpringRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by lukong on 2016/10/30.
 */
public class MetricsThread implements Runnable {

    public static Logger LOG= LoggerFactory.getLogger(MetricsThread.class);
    private volatile boolean flag=true;//使每个线程任务时候看到该变量值都是相同的
    private SpringRestClient springRestClient=null;
    private Hashtable<String,Object> table_rate=null;
    //private List<JSONObject> list=new ArrayList<>();


    /*构造函数*/
    public MetricsThread(SpringRestClient springRestClient,
                         Hashtable table_rate){
        this.springRestClient=springRestClient;
        this.table_rate=table_rate;

    }

    @Override
    public void run() {
        while (flag){
            /*监控每个任务运行情况，主要指标是数据的处理速度*/
            List<Map> jobs=springRestClient.getJobs();
            List<JSONObject> list=new ArrayList<>();

            for (Map job:jobs) {

                String jid= (String) job.get("jid");
                String name= (String) job.get("name");
                //System.out.println("jid: "+jid);
                //LOG.info("jid: "+jid);
                List<Map>metrics=springRestClient.getMetrics(jid);
                Map metrics_node_map=metrics.get(1);

//                LOG.info("read-bytes:"+metrics_node_map.get("read-bytes"));
//                LOG.info("write-bytes:"+metrics_node_map.get("write-bytes"));
                //LOG.info("read-records:"+metrics_node_map.get("read-records"));
                //LOG.info("write-records:"+metrics_node_map.get("write-records"));
            /*
                监督任务运行情况，根据每个任务处理数据的情况（读入记录和写入记录之比）
                进行判断，该数据需不需进行缓存到redis，若写入记录/读入记录<95%，则将
                数据缓存到redis中，任务开始从redis缓存队列中获取数据，进行解析，若写入
                记录/读入记录>=95%,则正常解析数据
            */

                double rate=0;
                int read_records= (int) metrics_node_map.get("read-records");
                int write_records= (int) metrics_node_map.get("write-records");

                if(read_records==0){
                    LOG.info("sensor: "+name+" job: "+jid+ " 未处理数据");
                }else {

                    /*获取不同的jid*/

                    rate=write_records/read_records;
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("rate",rate);
                    jsonObject.put("jid",jid);
                    jsonObject.put("name",name);

                    list.add(jsonObject);

//                    table_rate.put("rate",rate);
//                    table_rate.put("jid",jid);
                    //System.out.println(jid);
                    //System.out.println("json: "+jsonObject.toJSONString());
                }
            }

            table_rate.put("msg",list);
            //System.out.println("msg: "+table_rate.get("msg").toString());

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOG.error((Marker) e,e.getMessage());
            }
        }
    }
    /*停止监控线程*/
    public void stop(){
        flag=false;
    }
}
