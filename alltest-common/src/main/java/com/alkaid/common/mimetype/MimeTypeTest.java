package com.alkaid.common.mimetype;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;

public class MimeTypeTest {

    public static void main(String[] argv) throws IOException {
        testFileMime();
    }

    private static void testFileMime() throws IOException {
        String path = "alltest-common\\target\\classes\\files\\";
        String[] files = {
                "test.bmp",
                "test.png",
                "test.jpg",
                "test.gif",
                "test.doc",
                "test.docx",
                "test.xls",
                "test.xlsx",
                "test.ppt",
                "test.pptx",
                "test.zip",
                "test.tar",
                "test.rar",
                "test.gz",
                "test.pdf",
                "test.ico",
                "test.txt",
                "test.wav",
                "test.wma",
                "test.mp3",
                "test.mp4",
                "test.avi",
                "test.log",
                "test.mid",
                "test.lic",
                "test.jar",
                "test.exe",
                "[MD5校验工具]Hash_1.0.4.exe",
                "BCompare-zh-3.3.12.18414.exe"
        };
        System.out.println(System.getProperty("user.dir"));
        for (String fileName : files) {
            File file = new File(path + fileName);
            Tika tika = new Tika();
            String mimeType = tika.detect(file);
            System.out.println("fileName = " + file + ", mimeType = " + mimeType);
        }
    }
}
