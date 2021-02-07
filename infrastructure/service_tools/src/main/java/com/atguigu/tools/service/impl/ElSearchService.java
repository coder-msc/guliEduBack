package com.atguigu.tools.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.tools.entity.Content;
import com.atguigu.tools.utils.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ElSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //解析数据放入ES 中
    public Boolean parseContent(String keyword) throws Exception {
        List<Content> contents = new HtmlParseUtil().parseJD(keyword);
        //解析查询结果放入ES中
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        for (int i = 0; i < contents.size(); i++) {
            bulkRequest.add(new IndexRequest("jd_goods").source(JSON.toJSONString(contents.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    //基本的搜索功能
    public List<Map<String, Object>> searchPage(String keyword, int pageNo, int pageSize) throws Exception {
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name", keyword);
        SearchSourceBuilder query = searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.from(pageNo); //分页
        searchSourceBuilder.size(pageSize);//分页

        /*高亮*/
        HighlightBuilder highlighter = new HighlightBuilder();
        highlighter.field("name");
        highlighter.requireFieldMatch(false); // 不需要多个高亮
        highlighter.preTags("<span style='color:red'>");
        highlighter.postTags("</span>");
        SearchSourceBuilder highlighterBuilder = searchSourceBuilder.highlighter(highlighter);

        searchRequest.source(query);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

//        解析结果
        List<Map<String, Object>> resoultList = new ArrayList<>();
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            //解析高亮的字段
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField highlightField = highlightFields.get("name");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();//原来的结果  需要与高亮字段做置换
            if (highlightField != null) {
                Text[] fragments = highlightField.fragments();
                String title="";
                for(Text text:fragments){
                    title+=text;
                }
                sourceAsMap.put("name",title);
            }
            resoultList.add(sourceAsMap);
        }
        return resoultList;
    }

}
