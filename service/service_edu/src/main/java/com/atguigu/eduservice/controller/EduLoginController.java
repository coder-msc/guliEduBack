package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin
public class EduLoginController {


    @PostMapping("login")
    public R Login(){

        return  R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R GetInfo(){
        return  R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
