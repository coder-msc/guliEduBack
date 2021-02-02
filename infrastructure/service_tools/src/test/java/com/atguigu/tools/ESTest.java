package com.atguigu.tools;

import com.alibaba.fastjson.JSON;
import com.atguigu.tools.entity.UcenterMember;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test //索引的创建
    public void search() throws IOException {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("kuang_index");
        //执行请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }

    /*测试获取索引*/
    @Test
    public void getIndex() throws IOException {
        GetIndexRequest index = new GetIndexRequest("kuang_index");
        boolean exists = restHighLevelClient.indices().exists(index, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /*测试删除suoyin*/
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("kuang_index");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    /*创建文档*/
    @Test
    public void addDocument() throws IOException {
        UcenterMember ucenterMember= new UcenterMember();
        ucenterMember.setAvatar("hello");
        ucenterMember.setAge(32);
        ucenterMember.setNickname("小明小明");
        //创建请求
        IndexRequest request = new IndexRequest("kuang_index");
        //规则 put  /kuang_index/_doc/1
        request.id("1");
        request.timeout("1s");
        // 将数据放入请求  JSON
        request.source(JSON.toJSON(ucenterMember), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    /*CRUD文档*/
    @Test
    public void getDocument() throws IOException {
        /*获取文档 判断文档是否存在*/
        GetRequest getRequest = new GetRequest("kuang_index","1");
//        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println(exists);//判断是否存在
        //获得文档信息
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getRequest);
    }


}
