package com.atguigu.tools.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.56.1",9200,"http")));
        return client;
    }

    /*
    *   RestClientBuilder builder=null;
        builder= RestClient.builder(new HttpHost("192.168.56.1", 9200, "http"));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    * */
}
