package com.forever.lucene.RamDirectory;

import com.forever.lucene.util.DocumentUtil;
import com.forever.lucene.util.LuceneUtil;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.SimpleFSLockFactory;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: Forever丶诺
 * @createTime: 2018-6-20.20:22
 */
public class RamDirectoryTest {

    @Test
    public void test() throws IOException {
        Directory directory = new RAMDirectory();
        IndexWriter indexWriter = new IndexWriter(directory,LuceneUtil.config);
        indexWriter.addDocuments(DocumentUtil.getDocs());
        indexWriter.commit();
        indexWriter.close();
    }

}
