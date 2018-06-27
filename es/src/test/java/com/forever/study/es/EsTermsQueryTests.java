package com.forever.study.es;

import com.forever.study.es.mode.Product;
import org.elasticsearch.index.query.ConstantScoreQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
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
public class EsTermsQueryTests {

    @Autowired
    private ElasticsearchTemplate es;

    /**
     * terms的查询
     */
    @Test
    public void testTerms() {
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("price", "20", "30");
        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(termsQueryBuilder);
        System.out.println(constantScoreQueryBuilder.toString());
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(termsQueryBuilder);
        List<Product> products = es.queryForList(nativeSearchQuery, Product.class);
    }

}
