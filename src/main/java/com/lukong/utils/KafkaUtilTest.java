package com.lukong.utils;

import org.junit.Test;

/**
 * Created by lukong on 2016/10/26.
 */
public class KafkaUtilTest {
    @Test
    public void testConsumer(){
        KafkaUtil kafkaUtil=new KafkaUtil();
        kafkaUtil.kafkaConsumer("ais-up");
    }
}
