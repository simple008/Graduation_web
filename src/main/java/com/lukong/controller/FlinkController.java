package com.lukong.controller;

import com.lukong.services.SpringRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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


}
