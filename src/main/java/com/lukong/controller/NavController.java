package com.lukong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lukong on 2016/10/22.
 */
@Controller
public class NavController {
    @RequestMapping(value = "/navigation/navigation",method = RequestMethod.GET)
    public String navigation(){

        return "/navigation/navigation";
    }

}
