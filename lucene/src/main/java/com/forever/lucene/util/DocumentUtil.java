package com.forever.lucene.util;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.document.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Forever丶诺
 * @createTime: 2018-6-20.20:08
 */
public class DocumentUtil {

    public static List<Document>  getDocs() {
        try {
            return getDocs(getFiles());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static List<Document> getDocs(File[] files) throws IOException {
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
    private static File[] getFiles() throws UnsupportedEncodingException {
        String fileDirPath = URLDecoder.decode(DocumentUtil.class.getResource("/").getPath() + "luceneFile", "utf-8");
        return new File(fileDirPath).listFiles();
    }


}
