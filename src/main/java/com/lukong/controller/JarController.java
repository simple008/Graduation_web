package com.lukong.controller;

import com.lukong.repository.RunRepository;
import com.lukong.repository.SNRepository;
import com.lukong.services.SpringRestClient;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

        File file=new File("/Users/lukong/Desktop/svn/Graduation_web/methods/"+jar.getOriginalFilename());
        try {
            FileUtils.writeByteArrayToFile(file,jar.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/sns";
    }


    /*将自动组装好的协议适配器发送给Flink集群服务器*/

    @RequestMapping(value = "jar/submit/{sensor}",method = RequestMethod.GET)
    public String submit(@PathVariable("sensor") String sensor){

        String jarId= (String) springRestClient.getJars().get("id");
        System.out.println("jarId:"+ jarId);
        String entry_class="com.bupt.flink.apps.demo.FlinkSensorAdapter";
        String program_args="--sensor "+sensor +" --jarFileName "+jarFileName;
        Map<String,Object> jobInfo=springRestClient.run(jarId,entry_class,program_args);

        System.out.println("jobId: "+jobInfo.get("jobid"));
        System.out.println("jarFileName: "+jarFileName);

        return "redirect:/admin/sns";
    }

    @RequestMapping(value = "/jar/addJar",method = RequestMethod.GET)
    public String getJarList(ModelMap modelMap){

        return "/addJar";
    }



}
