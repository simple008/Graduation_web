package com.lukong.services.data2db;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.*;

public class Date2db {
    private static KafkaConsumer<String,String> consumer=null;
    private static String str_topics=null;
    static {
        ResourceBundle resourceBundle=ResourceBundle.getBundle("topics");
        str_topics=resourceBundle.getString("topics");

    }
    public static void main(String... args){
        //设定消费者配置


        Properties props = new Properties();
        props.put("zk.connect", "zk_node:2181");
        props.put("zk.connectiontimeout.ms", "1000000");
        props.put("groupid", "test_group");
        consumer=new KafkaConsumer<String, String>(props);
        List<String> topics =new ArrayList<>();
        String[] arr_topics=str_topics.split(";");
        for (String topic:arr_topics) {
            topics.add(topic);
        }
        consumer.subscribe(topics);
        while (true){
            ConsumerRecords<String,String> records=consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records){
                String data=record.value();
                HandleMessage.handle(data);
            }
        }
    }
}
