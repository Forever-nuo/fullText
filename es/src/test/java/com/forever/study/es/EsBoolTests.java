package com.forever.study.es;

import com.forever.study.es.mode.Book;
import com.forever.study.es.mode.Product;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBoolTests {

    @Autowired
    private ElasticsearchTemplate es;

    /**
     * 简单的bool查询
     */
    @Test
    public void testBool() {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.should(QueryBuilders.termQuery("price", 30));
        boolQuery.should(QueryBuilders.termQuery("productID", "KDKE-B-9947-#kL5"));
        boolQuery.mustNot(QueryBuilders.termQuery("productID", "JODL-X-1937-#pV7"));
        String s = boolQuery.toString();

        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(boolQuery);
        System.out.println(s);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(constantScoreQueryBuilder);
        List<Product> products = es.queryForList(nativeSearchQuery, Product.class);
    }

    /**
     * 嵌套的bool查询
     */
    @Test
    public void testBoolAndBool() {
        //外层Bool的语句
        BoolQueryBuilder boolBuilderOut = QueryBuilders.boolQuery();
        boolBuilderOut.should(QueryBuilders.termQuery("productID", "KDKE-B-9947-#kL5"));

        //和should并列的bool
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(QueryBuilders.termQuery("price",30));
        boolBuilder.must(QueryBuilders.termQuery("productID","QQPX-R-3956-#aD8"));

        //放到外层的bool中
        boolBuilderOut.should(boolBuilder);

        ConstantScoreQueryBuilder constantScoreQueryBuilder = QueryBuilders.constantScoreQuery(boolBuilderOut);
        System.out.println(constantScoreQueryBuilder.toString());
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(constantScoreQueryBuilder);
        List<Product> products = es.queryForList(nativeSearchQuery, Product.class);
    }

}
