package com.atguigu.tools.controller;

import com.atguigu.tools.service.ESSearchService;
import com.atguigu.tools.service.impl.ElSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/*
* 博客文章查询  从ES中进行查询
* */
@RestController
@RequestMapping("/tools")
public class ESBlogContoller {
//    @Autowired
//    private ElSearchService elSearchService;

    @Autowired
    private ESSearchService esSearchService;


    //查询基本的操作  带分页
    @GetMapping("/blogs/search/{pageNo}/{pageSize}/{keyword}")
    public List<Map<String,Object>> searchPage(@PathVariable("keyword") String keyword,
                                               @PathVariable("pageNo")  int pageNo,
                                               @PathVariable("pageSize")  int pageSize) throws Exception {
        List<Map<String, Object>> mapList = esSearchService.searchBlogsPage(keyword, pageNo, pageSize);
        return mapList;
    }
}
