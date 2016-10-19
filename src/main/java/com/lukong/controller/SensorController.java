package com.lukong.controller;

import com.lukong.model.SensorEntity;
import com.lukong.repository.SNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by lukong on 16/9/17.
 */
@Controller
public class SensorController {
    @Autowired
    SNRepository snRepository;

    /*---index----*/

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    /*---sns-----*/

    @RequestMapping(value = "/admin/sns",method = RequestMethod.GET)
    public String getSns(ModelMap modelMap){
        List<SensorEntity> snList=snRepository.findAll();
        modelMap.addAttribute("snList",snList);

        return "/admin/sns";
    }

    /*----addSn-----*/

    @RequestMapping(value = "/admin/sns/add",method = RequestMethod.GET)
    public String addSn(){
        return "admin/addSn";
    }
    @RequestMapping(value = "/admin/sns/addP",method = RequestMethod.POST)
    public String addSnPost(@ModelAttribute("sn") SensorEntity snEntity){
        // 注意此处，post请求传递过来的是一个SnEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'sn'，并创建这个对象

        // 数据库中添加一个用户，该步暂时不会刷新缓存
        //userRepository.save(SnEntity);

        // 数据库中添加一个用户，并立即刷新缓存
        snRepository.saveAndFlush(snEntity);
        return "redirect:/admin/sns";
    }


    /*----show----*/

    @RequestMapping(value = "/admin/sns/show/{sensor}",method = RequestMethod.GET)
    public String showSn(@PathVariable("sensor")String sensor,ModelMap modelMap){

        //找到sensor所表示的用户
        SensorEntity snEntity_ex=new SensorEntity();
        snEntity_ex.setSensor(sensor);
        SensorEntity snEntity=snRepository.findOne(Example.of(snEntity_ex));
        //传递给请求页面
        modelMap.addAttribute("sn",snEntity);
        return "admin/snDetail";
    }


    /*---update-----*/
    @RequestMapping(value = "/admin/sns/update/{sensor}",method = RequestMethod.GET)
    public String updateSn(@PathVariable("sensor") String sensor,ModelMap modelMap){
        //找到sensor所表示的用户
        SensorEntity snEntity_ex=new SensorEntity();
        snEntity_ex.setSensor(sensor);
        SensorEntity snEntity=snRepository.findOne(Example.of(snEntity_ex));
        //传递给请求页面
        modelMap.addAttribute("sn",snEntity);
        return "admin/updateSn";
    }
    @RequestMapping(value = "/admin/sns/updateP",method = RequestMethod.POST)
    public String updateSnPost(@ModelAttribute("snP") SensorEntity snEntity){
        //更新用户信息
        snRepository.updateSn(snEntity.getSensor(),snEntity.getProtocol(),
                snEntity.getCommunication(),snEntity.getIp(),snEntity.getPort(),snEntity.getTopic());
        snRepository.flush();//刷新缓冲区
        return "redirect:/admin/sns";
    }


    /*----delete----*/

    @RequestMapping(value = "/admin/sns/delete/{sensor}",method = RequestMethod.GET)
    public String deleteSn(@PathVariable("sensor")String sensor){
        //删除sensor为sensor的传感器
        SensorEntity snEntity=new SensorEntity();
        snEntity.setSensor(sensor);
        snRepository.delete(snEntity);
        return "redirect:/admin/sns";
    }

}
