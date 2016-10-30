package com.lukong.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by lukong on 2016/10/24.
 * 用于连接kafka，以及操作kafka
 * kafka服务器端会根据消息的主题是否创建，自动创建主题
 */
public class KafkaUtil {
    public static Logger LOG= LoggerFactory.getLogger(KafkaUtil.class);
    public static Properties properties=null;
    public static KafkaConsumer<String,String> consumer=null;
    static {
        ResourceBundle resourceBundle=ResourceBundle.getBundle("kafka");
        String zk=resourceBundle.getString("zookeeper.connect");
        String brokers=resourceBundle.getString("bootstrap.servers");

        String key_des=resourceBundle.getString("key.deserializer");
        String val_des=resourceBundle.getString("value.deserializer");

        String gid=resourceBundle.getString("group.id");
        //UUID suffix=UUID.randomUUID();

        properties=new Properties();
        properties.put("zookeeper.connect",zk);
        properties.put("bootstrap.servers",brokers);
        properties.put("key.deserializer",key_des);
        properties.put("value.deserializer",val_des);
        properties.put("group.id",gid);


    }

    /*每一秒从kafka中抽取数据，打包成JSON格式*/

    public  List<JSONObject> consumer(String... topic){
        consumer=new KafkaConsumer(properties);
        consumer.subscribe(Arrays.asList(topic));
        JSONObject jsonObject=null;
        List<JSONObject> list_json=new ArrayList<>();

        ConsumerRecords<String,String> records=consumer.poll(1000);
        for (ConsumerRecord<String, String> record : records){
            System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            String data=record.value();
            jsonObject=JSONObject.parseObject(data);
            list_json.add(jsonObject);
        }

        return list_json;
    }

}
