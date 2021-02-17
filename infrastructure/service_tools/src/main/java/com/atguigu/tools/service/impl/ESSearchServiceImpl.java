package com.atguigu.tools.service.impl;

import com.atguigu.tools.service.ESSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ESSearchServiceImpl implements ESSearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<Map<String, Object>> searchBlogsPage(String keyword, int pageNo, int pageSize) throws Exception {
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("description", keyword);
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("description", keyword);
        SearchSourceBuilder query = searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchSourceBuilder.from(pageNo); //分页
        searchSourceBuilder.size(pageSize);//分页

        /*高亮*/
        HighlightBuilder highlighter = new HighlightBuilder();
        highlighter.field("description");
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
            HighlightField highlightField = highlightFields.get("description");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();//原来的结果  需要与高亮字段做置换
            if (highlightField != null) {
                Text[] fragments = highlightField.fragments();
                String title = "";
                for (Text text : fragments) {
                    title += text;
                }
                sourceAsMap.put("description", title);
            }
            resoultList.add(sourceAsMap);
        }
        return resoultList;
    }
}
