package com.alkaid.common.redis.benchmark;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseReport {

    public static List parceRport(String configFile) throws IOException {

        if (configFile != null && configFile.length() > 0) {
            File file = new File(configFile);
            FileReader in = new FileReader(file);
            BufferedReader bufIn = new BufferedReader(in);
            // 内存流, 作为临时流
            CharArrayWriter tempStream = new CharArrayWriter();
            // 替换
            String line = null;
            List<String> reportList = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufIn.readLine()) != null) {
                if (line.length() > 0) {
                    if (line.contains(":")) {
                        String[] content = line.split(":");
                        String[] metric = content[1].split(" ");
                        stringBuilder.append(metric[1]).append(",");
                    } else {
                        if (stringBuilder.length() > 0) {
                            reportList.add(stringBuilder.append("\n").toString());
                            stringBuilder = new StringBuilder();
                        }
                        //reportList.add(line + "\n");
                    }
                }
            }
            // 关闭 输入流
            bufIn.close();
            // 将内存中的流 写入 文件
            return reportList;
        }
        return null;
    }

    public static void writeFile(List<String> list, String fileName) {
        try {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            list.forEach(it -> {
                try {
                    fileWriter.write(it.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception ex) {

        }
    }

    public static void main(String[] args) throws IOException {
        List<String> list = parceRport("D:\\work\\ruijie\\document\\架构设计\\201910-集群架构\\缓存\\redis-report.txt");
        System.out.print(list.toString());
        writeFile(list, "D:\\work\\ruijie\\document\\架构设计\\201910-集群架构\\缓存\\redis-report-out.csv");
    }

}
