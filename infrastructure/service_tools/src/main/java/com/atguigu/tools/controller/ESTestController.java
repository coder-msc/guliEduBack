package com.atguigu.tools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/tools")
@Controller
public class ESTestController {

    @GetMapping("/tools/index")
    public String index(){
        return "index";
    }

}
