package com.forever.lucene.demo1;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
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
 * @createTime: 2018-6-18.21:54
 */
@Slf4j
public class LuceneTest {

    @Test
    public void testCreateIndex() throws IOException {
        createIndex(new StandardAnalyzer());
        //使用ik中文分词器
        //createIndex(new IKAnalyzer());
    }

    /**
     * 测试全文检索
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testQuery() throws IOException, ParseException {

        String searchField = "contentField";
        String searchValue = "蓝瘦香菇";



        //全文检索
        QueryParser queryParser = new QueryParser(searchField, new IKAnalyzer());
        Query query = queryParser.parse(searchValue);
        query(query);
    }

    /**
     * 创建索引 的演示
     *
     * @param analyzer
     * @throws IOException
     */
    public void createIndex(Analyzer analyzer) throws IOException {
        //获取原始文档
        File[] files = getFiles();

        //将原始文档 转换成 document
        List<Document> docList = getDocs(files);

        //创建索引
        //使用IndexWriter
        Directory dir = FSDirectory.open(Paths.get("index"));

        IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(analyzer));

        // 将doc写入到索引库
        indexWriter.addDocuments(docList);
        indexWriter.commit();
        indexWriter.close();
    }

    private void query(Query query) throws ParseException, IOException {
        /**
         * 创建IndexSearch对象
         * 1.需要 IndexReader
         * 2.indexRead的创建需要{@link FSDirectory}(文件索引库)
         */
        IndexSearcher search = new IndexSearcher(DirectoryReader.open(FSDirectory.open(Paths.get("index"))));


        // term 的查询
        // TopDocs topDocs = search.search(new TermQuery(new Term(searchField, searchValue)), 10);

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

    private List<Document> getDocs(File[] files) throws IOException {
        //将原始文档 转换成 document
        List<Document> docList = new ArrayList<>();
        for (File file : files) {
            Document doc = new Document();
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
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private File[] getFiles() throws UnsupportedEncodingException {
        String fileDirPath = URLDecoder.decode(this.getClass().getResource("/").getPath() + "luceneFile", "utf-8");
        return new File(fileDirPath).listFiles();
    }

}
