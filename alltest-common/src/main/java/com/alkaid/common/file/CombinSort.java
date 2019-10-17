package com.alkaid.common.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CombinSort {

    private static List<String> recordList = new LinkedList<>();
    private static String title;

    private static void readFile(String fileName, boolean firstLine, double weight, long lineNumber) throws IOException {
        if (fileName != null && fileName.length() > 0) {
            File file = new File(fileName);
            FileReader in = new FileReader(file);
            BufferedReader bufIn = new BufferedReader(in);
            String line = bufIn.readLine();
            if (firstLine) {
                title = line + "\n";
            }
            if  (weight < 0.0 || weight > 1) {
                weight = 1;
            }
            int interval = 100 / Double.valueOf(weight * 100.00).intValue();
            long i = 0;
            while ((line = bufIn.readLine()) != null) {
                if (lineNumber < 0 || (lineNumber > 0 && i ++ < lineNumber)) {
                    if (line.length() > 0 && line.contains(",") && i%interval == 0) {
                        recordList.add(line + "\n");
                    }
                }
            }
            // 关闭 输入流
            bufIn.close();
            // 将内存中的流 写入 文件
        }
    }

    private static void sort() {
        Collections.sort(recordList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                String[] content1 = o1.split(",");
                String[] content2 = o2.split(",");
                long timestamp1 = Long.valueOf(content1[0]);
                long timestamp2 = Long.valueOf(content2[0]);
                // return timestamp1 > timestamp2 ? 1 : timestamp1 == timestamp2 ? 0 : -1;
                return Long.compare(timestamp1, timestamp2);
            }
        });
    }

    private static void writeFile(String fileName) throws IOException {
        File file = new File(fileName);
        FileWriter out = new FileWriter(file);
        out.write(title);
        recordList.forEach(it -> {
            try {
                out.write(it);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.close();
    }

    public static void main(String[] argv) throws IOException {
        /*
        String file1 = argv[0];
        System.out.println(file1);
        String file2 = argv[1];
        System.out.println(file2);
        String resultFile = argv[2];
        */
        String file1 = "D:\\work\\ruijie\\document\\架构落地\\201908中规模\\性能测试\\lobby_page_2_merge.jtl";
        String file2 = "D:\\work\\ruijie\\document\\架构落地\\201908中规模\\性能测试\\lobby_page_merge.jtl";
        //String file3 = "D:\\work\\ruijie\\document\\架构落地\\201908中规模\\性能测试\\lobby_page_2_189.106_9090.jtl";
        String resultFile = "D:\\work\\ruijie\\document\\架构落地\\201908中规模\\性能测试\\lobby_page_combin_merge.jtl";

        System.out.println("readFile ..." + file1);
        readFile(file1, true, 0.9, 10000);

        System.out.println("readFile ..." + file2);
        readFile(file2, false, 0.1, 10000);

        //System.out.println("readFile ..." + file3);
        //readFile(file3, false, 1, -1);

        System.out.println("sort ...");
        sort();

        System.out.println("writeFile ..." + resultFile);
        writeFile(resultFile);
    }

}
