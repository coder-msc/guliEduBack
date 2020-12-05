package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    //上传头像的方法
    @PostMapping
    public R uploadossFile(@RequestParam(value = "image") MultipartFile image){
        System.out.println(image+"--------------------");
       String url= ossService.uploadAvatar(image);
        return R.ok().data("url",url);
    }
}
