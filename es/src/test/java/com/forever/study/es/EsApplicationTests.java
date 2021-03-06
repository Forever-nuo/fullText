package com.forever.study.es;

import com.forever.study.es.mode.Book;
import com.forever.study.es.mode.Product;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
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
    private ElasticsearchTemplate es;

    @Test
    public void contextLoads() {
        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(null);
        System.out.println(constantScoreQueryBuilder);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(null);
        List<Product> products = es.queryForList(nativeSearchQuery, Product.class);
    }

}
