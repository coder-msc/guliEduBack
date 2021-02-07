package com.atguigu.tools;

import com.alibaba.fastjson.JSON;
import com.atguigu.tools.entity.UcenterMember;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test //索引的创建
    public void search() throws IOException {
        //创建索引
        CreateIndexRequest request = new CreateIndexRequest("jd_goods");
        //执行请求
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }

    /*测试获取索引*/
    @Test
    public void getIndex() throws IOException {
        GetIndexRequest index = new GetIndexRequest("jd_goods");
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
        ucenterMember.setNickname("xiaoming");
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


                /*设置这两条记录进去


                PUT testdb
                {
                  "mappings": {
                    "properties": {
                      "name":{
                        "type": "text"
                      },
                      "desc":{
                        "type": "keyword"
                      }
                    }
                  }
                }

            PUT testdb/_doc/1
            {
              "name":"123 java 好",
              "desc":"123331ava desc"
            }

            PUT testdb/_doc/2
            {
              "name":"好",
              "desc":"234242Java desc2"
            }
                * */
    //查询
    @Test
    public void Testsearch() throws IOException {
        SearchRequest searchRequest=  new SearchRequest("testdb");
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询条件
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "好");
//        QueryBuilders.matchAllQuery(); // 匹配所有查询
        SearchSourceBuilder query = searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));//查询超时时间

        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = search.getHits();
        System.out.println(JSON.toJSONString(searchHits.getHits()));
        System.out.println("=============");
        for(SearchHit document: searchHits.getHits()){
            System.out.println(document.getSourceAsMap());
        }

    }
}
