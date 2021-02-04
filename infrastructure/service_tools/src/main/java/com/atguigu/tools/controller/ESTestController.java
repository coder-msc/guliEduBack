package com.atguigu.tools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ESTestController {

    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

}
