package com.lukong.controller;

import com.lukong.model.SnEntity;
import com.lukong.repository.SNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

}
