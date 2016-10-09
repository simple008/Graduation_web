package com.lukong.controller;

import com.lukong.model.UserEntity;
import com.lukong.services.SpringRestClient;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lukong on 16/10/7.
 * 与Flink监控web应用进行交互
 */
@Controller
@RequestMapping("/flink")
public class FlinkController {

    @Autowired
    SpringRestClient springRestClient;

    /*-----最简单请求--------*/

    /*显示所有提交的JAR包*/
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String getJars(ModelMap modelMap){

        Map map=springRestClient.getJars();

        //modelMap.addAttribute("jar",map);
        /*将属性以map的形式传给页面*/
        modelMap.addAllAttributes(map);

        return "/flink/jars";
    }
    @RequestMapping(value = "/uploadJar",method = RequestMethod.POST)
    public void upload(){

    }

    /*----参数URL请求-------*/
    /*处理的请求是http://hostname:port/flink/test?name="***"*/
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(@RequestParam("name") String name,ModelMap modelMap){

    }

    /*----处理表单--------*/

    /*将对象与表单绑定*/
    @RequestMapping(value = "/demo",method = RequestMethod.GET)
    public String demo(ModelMap modelMap){
        modelMap.addAttribute("user",new UserEntity());
        return "/flink/demo";
    }

    /*处理表单信息*/
    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String form(@ModelAttribute("user")UserEntity userEntity,@RequestParam(value = "jar") MultipartFile jar) throws IOException {

        System.out.println(userEntity.getName());
        System.out.println("jar:"+jar.getName()+" size: "+jar.getSize());

        File file=new File("/Users/lukong/Desktop/svn/Graduation_web/methods/"+jar.getName()+".jar");
        FileUtils.writeByteArrayToFile(file,jar.getBytes());


        return "/flink/demo";
    }

    /*-----处理上传文件------*/


}
