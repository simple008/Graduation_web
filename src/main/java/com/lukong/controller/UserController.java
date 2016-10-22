package com.lukong.controller;

import com.lukong.model.UserEntity;
import com.lukong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lukong on 16/9/18.
 */
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    /*----confirmation-------*/

    @RequestMapping(value = "/admin/users" ,method = RequestMethod.POST)
    public String confirmation(@ModelAttribute("us")UserEntity userEntity){
        String name=userRepository.select(userEntity.getName(),userEntity.getPassword());
        if(name==null){
            System.out.println("未注册...");
            return "/index";
        }else if(name.equals(userEntity.getName()))
        {
            //return "redirect:/admin/sns";
            return "redirect:/navigation/navigation";
        }else {
            System.out.println("user or password error!");
            return "/index";
        }
    }

    /*----register------*/
    @RequestMapping(value = "/admin/reg",method = RequestMethod.GET)
    public String getReg(){
        return "/admin/register";
    }

    @RequestMapping(value = "/admin/users/add",method = RequestMethod.POST)
    public String addUser(@ModelAttribute("usadd")UserEntity userEntity){
        System.out.println("insert!");
        userRepository.saveAndFlush(userEntity);
        return "/index";
    }
}
