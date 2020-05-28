package com.example.rockerproductdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author AGbetrayal
 * @date 2019/7/18 13:54
 */
@Controller
public class loginController {

//    @RequestMapping(path = "loginPage")
//    public String loginPage(){
//        return "login";
//    }

    @RequestMapping(path = "page")
    public String indexPage(){
        return "index";
    }

    @RequestMapping(path = "login.ftl")
    public String loginPage(){
        return "login";
    }
}
