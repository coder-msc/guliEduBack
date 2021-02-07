package com.atguigu.tools.controller;

import com.atguigu.tools.service.impl.ElSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ESContentController {
    @Autowired
    private ElSearchService elSearchService;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword)throws  Exception{
        return elSearchService.parseContent(keyword);
    }
}
