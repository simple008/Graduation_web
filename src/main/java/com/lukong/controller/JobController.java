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

import java.text.DecimalFormat;
import java.util.Iterator;
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

        /*将每个任务的处理速度加入JSON字符串中*/

        Iterator iterator=jobs.iterator();
        while (iterator.hasNext()){
            Map item= (Map) iterator.next();
            String jid= (String) item.get("jid");
            List<Map> metrics=springRestClient.getMetrics(jid);
            Map metrics_node_map=metrics.get(1);
            double rate=0;
            int read_records= (int) metrics_node_map.get("read-records");
            int write_records= (int) metrics_node_map.get("write-records");

            item.put("read-records",read_records);
            item.put("write-records",write_records);
            if(read_records==0){
                rate=0.0;
            }else {
                rate=write_records/read_records;
            }
            String rate_str=new DecimalFormat("#0.00").format(rate*100);
            item.put("rate",rate_str+"%");
        }


        JSONObject jsonObject=new JSONObject();
        jsonObject.put("jobs",jobs);
        jsonObject.put("jobsComp",jobsComp);

        return jsonObject.toJSONString();
    }

}
