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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
        File[] files = getFiles();

        //将原始文档 转换成 document
        List<Document> docList = getDocs(files);

        //创建索引
        //使用IndexWriter 将doc写入到索引库
        Directory dir = FSDirectory.open(Paths.get("index"));
        IndexWriterConfig config = new IndexWriterConfig();
        IndexWriter indexWriter = new IndexWriter(dir,config);

        indexWriter.addDocuments(docList);
        indexWriter.commit();
        indexWriter.close();
    }


    private List<Document> getDocs(File[] files ) throws IOException {
        //将原始文档 转换成 document
        List<Document> docList = new ArrayList<>();
        for (File file : files) {
            Document doc = new Document();
            /**
             * 创建Field域
             */
            Field nameField = new TextField("nameField",file.getName(),Field.Store.YES);
            String content = FileUtils.readFileToString(file, "utf-8");
            Field contentField = new TextField("contentField",content,Field.Store.YES);
            Field pathField = new StoredField("pathField",file.getPath());
            Field sizeField = new LongPoint("sizeField",FileUtils.sizeOf(file));
            /**
             * Document 和 Field 绑定
             */
            doc.add(nameField);
            doc.add(contentField);
            doc.add(pathField);
            doc.add(sizeField);

            docList.add(doc);
        }
        return docList;
    }


    /**
     * 获取原始文档
     * @return
     * @throws UnsupportedEncodingException
     */
    private File[] getFiles() throws UnsupportedEncodingException {
        String fileDirPath = URLDecoder.decode( this.getClass().getResource("/").getPath()+"luceneFile", "utf-8");
        File fileDir = new File(fileDirPath);
        File[] files = fileDir.listFiles();
        return  files;
    }





}
