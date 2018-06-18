package com.forever.lucene.demo1;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Forever丶诺
 * @createTime: 2018-6-18.21:54
 */
public class LuceneTest {

    @Test
    public void testCreateIndex() throws IOException {
        //获取原始文档
        File fileDir = new File("D:\\luceneFile");
        File[] files = fileDir.listFiles();

        //将原始文档 封装成 document
        List<Document> docList = new ArrayList<>();
        for (File file : files) {
            Document doc = new Document();
            /**
             * 创建Field域
             */
            //fieldName域
            Field nameField = new TextField("nameField",file.getName(),Field.Store.YES);
            String content = FileUtils.readFileToString(file, "utf-8");
            Field contentField = new TextField("contentField",content,Field.Store.YES);
            Field pathField = new StoredField("pathField",file.getPath());
            Field sizeField = new LongPoint("sizeField",FileUtils.sizeOf(file));

            doc.add(nameField);
            doc.add(contentField);
            doc.add(pathField);
            doc.add(sizeField);

            docList.add(doc);
        }

        /**
         * 创建indexWriter对象 将数据写入到索引
         */
        Directory dir = FSDirectory.open(Paths.get("D:\\index"));
        IndexWriterConfig config = new IndexWriterConfig();
        IndexWriter indexWriter = new IndexWriter(dir,config);
        indexWriter.addDocuments(docList);
        indexWriter.commit();
        indexWriter.close();
    }








}
