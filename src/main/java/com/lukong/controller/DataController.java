package com.lukong.controller;

import com.alibaba.fastjson.JSONObject;
import com.lukong.model.TopicEntity;
import com.lukong.repository.TopicRepository;
import com.lukong.utils.KafkaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lukong on 2016/10/26.
 */
@Controller
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    KafkaUtil kafkaUtil;

    @Autowired
    TopicRepository topicRepository;

    @RequestMapping(value = "/parse",method = RequestMethod.GET)
    @ResponseBody
    public String parse(){

        String topic=topicRepository.select();
        System.out.println("topic: "+topic);
        //List<JSONObject> list_json=kafkaUtil.consumer(topic);

        JSONObject jsonObject=
                kafkaUtil.kafkaConsumer(topic);

        return jsonObject.toJSONString();

    }
    @RequestMapping(value = "/topic",method = RequestMethod.POST)
    public String topic(@ModelAttribute TopicEntity topicEntity){

        topicRepository.deleteAll();

        topicRepository.saveAndFlush(topicEntity);

        return "/data/parse";

    }

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    public String data(){

        return "/data/parse";
    }
}
