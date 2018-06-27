package com.forever.study.es.mode;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author: Forever丶诺
 * @date: 2018/6/27 10:56
 */
@Data
@Accessors(chain = true)
@Document(indexName = "my_store",type = "products")
public class Product {
    private  Integer price;
    private  String productID;
}
