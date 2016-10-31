package com.lukong.utils;


import com.alibaba.fastjson.JSONObject;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
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
    private static final String TOPIC = "ais-up";
    private static final int THREAD_AMOUNT = 1;

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

    public List<JSONObject> consumer(String... topic){
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

    public JSONObject kafkaConsumer(String topic){

        /*遗留问题：有些线程会一直运行，无法正确关闭连接
        * 状态：待解决
        * */

        ConsumerConfig consumerConfig=new ConsumerConfig(properties);
        ConsumerConnector consumer= Consumer.createJavaConsumerConnector(consumerConfig);
        HashMap<String,Integer> map=new HashMap<>();
        map.put(topic,THREAD_AMOUNT);
        Map<String, List<KafkaStream<byte[], byte[]>>> msgStreams=
                consumer.createMessageStreams(map);

        List<KafkaStream<byte[], byte[]>> msgStreamList=
                msgStreams.get(topic);


        KafkaStream<byte[], byte[]> kafkaStream=msgStreamList.get(0);


        JSONObject jsonObject=new JSONObject();
        List<String> strs=new ArrayList<>();
        int count=10;

        ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();

        if(!iterator.hasNext()){
            LOG.info("kafka队列没有数据");
            consumer.shutdown();
            return new JSONObject();
        }


        while(count>0&&iterator.hasNext()) {
            LOG.info("从kafka中抽取数据");
            String message = new String(iterator.next().message());
            //System.out.println("message: " + message);
            //jsonObject.put("data",message);
            strs.add(message);
            count--;
        }

        consumer.shutdown();
        jsonObject.put("datas",strs);
        LOG.info("message: "+jsonObject.toJSONString());
        return jsonObject;
    }


}
