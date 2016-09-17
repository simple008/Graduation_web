package com.lukong.controller;

import com.lukong.model.SnEntity;
import com.lukong.repository.SNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by lukong on 16/9/17.
 */
@Controller
public class MainController {
    @Autowired
    SNRepository snRepository;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/admin/sns",method = RequestMethod.GET)
    public String getSns(ModelMap modelMap){
        List<SnEntity> snList=snRepository.findAll();
        modelMap.addAttribute("snList",snList);
        return "/admin/sns";
    }
    @RequestMapping(value = "/admin/sns/add",method = RequestMethod.GET)
    public String addSn(){
        return "admin/addSn";
    }
    @RequestMapping(value = "/admin/sns/addP",method = RequestMethod.POST)
    public String addSnPost(@ModelAttribute("sn") SnEntity snEntity){
        // 注意此处，post请求传递过来的是一个SnEntity对象，里面包含了该用户的信息
        // 通过@ModelAttribute()注解可以获取传递过来的'sn'，并创建这个对象

        // 数据库中添加一个用户，该步暂时不会刷新缓存
        //userRepository.save(userEntity);

        // 数据库中添加一个用户，并立即刷新缓存
        snRepository.saveAndFlush(snEntity);
        return "redirect:/admin/sns";
    }
}
