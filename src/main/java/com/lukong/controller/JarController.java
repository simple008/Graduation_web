package com.lukong.controller;

import com.lukong.model.SensorEntity;
import com.lukong.repository.RunRepository;
import com.lukong.repository.SNRepository;
import com.lukong.services.SpringRestClient;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;


/**
 * Created by lukong on 16/9/18.
 */
@Controller
public class JarController {


    @Autowired
    SNRepository snRepository;
    @Autowired
    RunRepository runRepository;
    @Autowired
    SpringRestClient springRestClient;

    String jarFileName;

    /*将按钮触发到提交JAR包页面*/
    @RequestMapping(value = "/jar/add/{sensor}",method = RequestMethod.GET)
    public String addJar(@PathVariable("sensor") String sensor){

        return "/jar/addJar";
    }

    /*处理上传上来的协议解析逻辑（JAR）*/
    @RequestMapping(value = "/jar/upload",method = RequestMethod.POST)
    public String upload(@RequestParam("jar")MultipartFile jar){

        System.out.println("获取到文件："+jar.getOriginalFilename());
        jarFileName=jar.getOriginalFilename();

        File file=new File("/Users/lukong/Desktop/svn/Graduation_web/src/main/webapp/methods/"+jar.getOriginalFilename());
        try {
            FileUtils.writeByteArrayToFile(file,jar.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/sns";
    }


    /*将自动组装好的协议适配器发送给Flink集群服务器*/
    @ResponseBody
    @RequestMapping(value = "jar/submit/{sensor}",method = RequestMethod.GET)
    public String submit(@PathVariable("sensor") String sensor){

        String jarId= (String) springRestClient.getJars().get("id");
        System.out.println("jarId:"+ jarId);
        String entry_class_up="com.bupt.flink.apps.demo.FlinkSensorAdapterUp";
        String entry_class_down="com.bupt.flink.apps.demo.FlinkSensorAdapterDown";

        /*从传感器适配器模型中获取发布主题*/
        SensorEntity sensorEntity=new SensorEntity();
        sensorEntity.setSensor(sensor);
        SensorEntity sensor_target=snRepository.findOne(Example.of(sensorEntity));
        String topic=sensor_target.getTopicUp();
        String topic_down=sensor_target.getTopicDown();

        /*设置解析的并行度*/


        System.out.println("topic: "+topic);

        String program_args_up="--sensor "+sensor +" --jarFileName "+jarFileName +" --topic "+topic;
        String program_args_down="--sensor "+sensor +" --jarFileName "+jarFileName +" --topic "+topic_down;

        Map<String,Object> jobInfo_up=springRestClient.run(jarId,entry_class_up,program_args_up);
        Map<String,Object> jobInfo_down=springRestClient.run(jarId,entry_class_down,program_args_down);

        String jobUp= (String) jobInfo_up.get("jobid");
        String jobDown= (String) jobInfo_down.get("jobid");

        System.out.println("up jobId: "+jobUp);
        System.out.println("down jobId: "+jobDown);

        /*将传感器跟任务ID绑定*/
        snRepository.updateId(sensor,jobUp,jobDown);


        System.out.println("jarFileName: "+jarFileName);

        return "submit success";
    }

    @RequestMapping(value = "/jar/addJar",method = RequestMethod.GET)
    public String getJarList(ModelMap modelMap){

        return "/addJar";
    }



}
