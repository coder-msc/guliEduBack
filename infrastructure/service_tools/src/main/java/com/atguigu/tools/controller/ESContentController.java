package com.atguigu.tools.controller;

import com.atguigu.tools.service.impl.ElSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ESContentController {
    @Autowired
    private ElSearchService elSearchService;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword)throws  Exception{
        return elSearchService.parseContent(keyword);
    }

    //查询基本的操作  带分页
    @GetMapping("/search/{pageNo}/{pageSize}/{keyword}")
    public List<Map<String,Object>> searchPage(@PathVariable("keyword") String keyword,
                                               @PathVariable("pageNo")  int pageNo,
                                               @PathVariable("pageSize")  int pageSize) throws Exception {
        List<Map<String, Object>> mapList = elSearchService.searchPage(keyword, pageNo, pageSize);
        return mapList;
    }
}
