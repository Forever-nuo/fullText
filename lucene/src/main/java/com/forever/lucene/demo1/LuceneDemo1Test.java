package com.forever.lucene.demo1;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Forever丶诺
 * @createTime: 2018-6-19.21:53
 */
@Slf4j
public class LuceneDemo1Test {


    @Test
    public void testCreateIndex() throws IOException {
        //获取原始文档
        String fileDirPath = URLDecoder.decode(this.getClass().getResource("/").getPath() + "luceneFile", "utf-8");
        File[] files = new File(fileDirPath).listFiles();


        //将原始文档 转换成 document
        List<Document> docList = new ArrayList<>();
        for (File file : files) {
            Document document = new Document();
            /**
             * 创建Field域
             */
            Field nameField = new TextField("nameField", file.getName(), Field.Store.YES);
            String content = FileUtils.readFileToString(file, "utf-8");
            Field contentField = new TextField("contentField", content, Field.Store.YES);
            Field pathField = new StoredField("pathField", file.getPath());
            Field sizeField = new LongPoint("sizeField", FileUtils.sizeOf(file));
            /**
             * Document 和 Field 绑定
             */
            document.add(nameField);
            document.add(contentField);
            document.add(pathField);
            document.add(sizeField);
            docList.add(document);
        }

        //创建索引
        //使用IndexWriter
        Directory dir = FSDirectory.open(Paths.get("index"));

        IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig());

        // 将doc写入到索引库
        indexWriter.addDocuments(docList);
        indexWriter.commit();
        indexWriter.close();
    }

    @Test
    public void testQuery() throws IOException, ParseException {
        String searchField = "contentField";
        String searchValue = "蓝瘦香菇";


        QueryParser queryParser = new QueryParser(searchField, new IKAnalyzer());
        Query query = queryParser.parse(searchValue);

        /**
         * 创建IndexSearch对象
         * 1.需要 IndexReader
         * 2.indexRead的创建需要FSDirectory
         */
        IndexSearcher search = new IndexSearcher(DirectoryReader.open(FSDirectory.open(Paths.get("index"))));

        TopDocs topDocs = search.search(query, 20);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = search.doc(scoreDoc.doc);
            log.info("得分:{}", scoreDoc.score);
            log.info(doc.get("nameField"));
            log.info("--------------文本内容---------------------");
            log.info(doc.get("contentField"));
            log.info("-------------------------------------------------");
        }
    }

}
