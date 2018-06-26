package com.forever.study.es;

import com.forever.study.es.mode.Book;
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
    private TransportClient elasticsearchClient;

    @Test
    public void test() {
        ConstantScoreQueryBuilder termQueryBuilder = QueryBuilders.constantScoreQuery(QueryBuilders
                .termQuery("name", "西"));
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(termQueryBuilder);
        List<Book> maps = elasticsearchTemplate.queryForList(nativeSearchQuery, Book.class);
        log.info("" + maps);
    }

    @Test
    public void test2() throws IOException {





    }

}
