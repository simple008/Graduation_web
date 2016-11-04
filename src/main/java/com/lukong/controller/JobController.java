package com.lukong.controller;

import com.alibaba.fastjson.JSONObject;
import com.lukong.services.SpringRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by lukong on 2016/10/10.
 */
/*显示执行解析协议任务，例如起始事件，持续时间以及状态等...*/

@Controller
public class JobController {
    @Autowired
    SpringRestClient springRestClient;

    @RequestMapping(value = "/job/jobinfo",method = RequestMethod.GET)
    public String getSns(ModelMap modelMap){

        List<Map> jobs=springRestClient.getJobs();

        List<Map> jobsComp=springRestClient.getJobsComp();

        modelMap.addAttribute("jobs",jobs);
        modelMap.addAttribute("jobsComp",jobsComp);

        return "/job/running";
    }
    @RequestMapping(value = "/job/opr/{jid}",method = RequestMethod.GET)
    public String cancel(@PathVariable("jid")String jid){
        springRestClient.cancel(jid);

        return "redirect:/job/jobinfo";
    }
    @ResponseBody
    @RequestMapping(value = "/job/running",method = RequestMethod.GET)
    public String redirect(ModelMap modelMap){
        List<Map> jobs=springRestClient.getJobs();
        List<Map> jobsComp=springRestClient.getJobsComp();

//        modelMap.addAttribute("jobs",jobs);
//        modelMap.addAttribute("jobsComp",jobsComp);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("jobs",jobs);
        jsonObject.put("jobsComp",jobsComp);

        return jsonObject.toJSONString();
    }

}
