package com.forever.study.es;

import com.forever.study.es.mode.Book;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
public class EsApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Test
    public void contextLoads() {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "游");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(matchQueryBuilder);
        List<Book> books = elasticsearchTemplate.queryForList(nativeSearchQuery, Book.class);
    }

}
