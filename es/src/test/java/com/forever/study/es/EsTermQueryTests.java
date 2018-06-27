package com.forever.study.es;

import com.forever.study.es.mode.Book;
import com.forever.study.es.mode.Product;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EsTermQueryTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    private TransportClient client;

    /**
     * 模板:
     * 模型对象上 注解了类型和索引
     * 通过属性映射将es的字段映射到对象上
     */
    @Test
    public void testTermNumByTemplate() {
        ConstantScoreQueryBuilder termQueryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders
                .termQuery("price", 30));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(termQueryBuilder);
        List<Product> productList = elasticsearchTemplate.queryForList(nativeSearchQuery, Product.class);
        log.info(""+productList);
    }

    /**
     * 使用原生client进行查询
     */
    @Test
    public void testTermNumByClient() {
        /**
         * QueryBuilders 各种查询的构建
         */
        ConstantScoreQueryBuilder termQueryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders
                .termQuery("price", "30"));
        /**
         * prepareSearch 索引位置
         * setTypes 类型
         * setQuery 查询
         */
        SearchHits hits = client.prepareSearch("my_store").setTypes("products").setQuery(termQueryBuilder).execute().actionGet
                ().getHits();
        for (SearchHit hit : hits) {
            log.info(hit.getSourceAsString());
        }
    }






    @Test
    public void test() {
        ConstantScoreQueryBuilder termQueryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders
                .termQuery("name", "西"));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(termQueryBuilder);
        List<Book> maps = elasticsearchTemplate.queryForList(nativeSearchQuery, Book.class);
        log.info("" + maps);
    }



}
