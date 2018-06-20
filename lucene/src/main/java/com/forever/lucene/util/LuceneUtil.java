package com.forever.lucene.util;

import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author: Forever丶诺
 * @createTime: 2018-6-20.20:01
 */
public class LuceneUtil {

    public static Directory directory;


    public static IndexWriterConfig config;


    static {
        try {
            directory = FSDirectory.open(Paths.get("index"));
            config = new IndexWriterConfig(new IKAnalyzer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
