package com.forever.study.es.mode;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Accessors(chain = true)
@Document(indexName = "es",type = "book")
public class Book {
    private Integer id;
    private String name;
    private String author;
}