package com.forever.study.es;

import com.forever.study.es.mode.Product;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsNullTests {

    @Autowired
    private ElasticsearchTemplate es;

    @Autowired
    private TransportClient client;

    /**
     * 存在查询
     */
    @Test
    public void testExists() {
        ExistsQueryBuilder existsQuery = QueryBuilders.existsQuery("tags");
        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(existsQuery);
        System.out.println(constantScoreQueryBuilder);

        SearchResponse searchResponse = client.prepareSearch("my_index").setTypes("posts").setQuery
                (constantScoreQueryBuilder).execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 缺失查询
     */
    @Test
    public void testNotExist() {

        BoolQueryBuilder notExist = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("tags"));
        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(notExist);
        System.out.println(constantScoreQueryBuilder);

        SearchResponse searchResponse = client.prepareSearch("my_index").setTypes("posts").setQuery
                (constantScoreQueryBuilder).execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }




}
