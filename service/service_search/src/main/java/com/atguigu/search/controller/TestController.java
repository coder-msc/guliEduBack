package com.atguigu.search.controller;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Qualifier("esRestClient")
    @Autowired
    private RestHighLevelClient client;

    @RequestMapping("/getClient")
    public String GetTestClient(){
        System.out.print(client);
        return "";
    }
}
