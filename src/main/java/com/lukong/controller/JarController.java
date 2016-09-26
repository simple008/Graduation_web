package com.lukong.controller;

import com.lukong.model.RunnableEntity;
import com.lukong.repository.RunRepository;
import com.lukong.repository.SNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



/**
 * Created by lukong on 16/9/18.
 */
@Controller
public class JarController {

    @Autowired
    SNRepository snRepository;
    @Autowired
    RunRepository runRepository;

    @RequestMapping(value = "/jar/add/{sensor}",method = RequestMethod.GET)
    public String getAdd(@PathVariable("sensor") String sensor){
        return "/jar/addJar";
    }

    @RequestMapping(value = "/jar/submit/{sensor}",method = RequestMethod.GET)
    public String submit(@PathVariable("sensor") String sensor){

        /*将运行*/
        RunnableEntity runnableEntity=new RunnableEntity();
        runnableEntity.setSensor(sensor);
        runRepository.saveAndFlush(runnableEntity);

        /*采用本地运行的方式，调用线程运行每个传感器适配器*/

        return "/jar/submit";
    }

    @RequestMapping(value = "/jar/addJar",method = RequestMethod.GET)
    public String getJarList(ModelMap modelMap){

        return "/addJar";
    }



}
