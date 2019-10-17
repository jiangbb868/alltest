package com.alkaid.common.file;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReplaceTestCase {

    private static Map<String, String> customMap = new HashMap<>();

    /**
     * 替换文本文件中的 非法字符串
     * @param path
     * @throws IOException
     */
    public static void replacTextContent(String path, String port) throws IOException {

        Long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        //原有的内容
        String srcStr = "name:";
        //要替换的内容
        String replaceStr = "userName:";
        // 读
        File file = new File(path);
        FileReader in = new FileReader(file);
        BufferedReader bufIn = new BufferedReader(in);
        // 内存流, 作为临时流
        CharArrayWriter tempStream = new CharArrayWriter();
        // 替换
        String line = null;
        while ((line = bufIn.readLine()) != null) {
            // 替换每行中, 符合条件的字符串
            line = line.replaceAll("tm=[0-9]+", "tm="+timestamp.toString());
            line = line.replaceAll("name=\\\"HTTPSampler.port\\\">9090<", "name=\\\"HTTPSampler.port\\\">"+port+"<");
            line = line.replaceAll("office\\.relax\\.com:9090", "office\\.relax\\.com:"+port);
            line = replaceCustom(line);
            // 将该行写入内存
            tempStream.write(line);
            // 添加换行符
            tempStream.append(System.getProperty("line.separator"));
        }
        // 关闭 输入流
        bufIn.close();
        // 将内存中的流 写入 文件
        FileWriter out = new FileWriter(file);
        tempStream.writeTo(out);
        out.close();
        System.out.println("====path:" + path);
    }

    public static void testReplace() {
        Long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String line = "<stringProp name=\"HTTPSampler.path\">/relax/rpc?tm=1565854850632</stringProp>";
        line = line = line.replaceAll("tm=[0-9]+", "tm="+timestamp.toString());
        System.out.println(line);

        line = "<stringProp name=\"HTTPSampler.port\">9090</stringProp>";
        line = line.replaceAll("name=\\\"HTTPSampler.port\\\">9090<", "name=\\\"HTTPSampler.port\\\">"+9443+"<");
        System.out.println(line);

        line = "<stringProp name=\"Header.value\">http://office.relax.com:9090/relax/index.html;JSESSIONID=cc2c1af9-b699-4120-a570-fa617266e8cd</stringProp>";
        line = line.replaceAll("office\\.relax\\.com:9090", "office\\.relax\\.com:"+9443);
        System.out.println(line);

        line = "<stringProp name=\"49623857\">31307</stringProp>";
        line = line.replaceAll("name=\"49623857\">31307<", "name=\"49623857\">44441<");
        System.out.println(line);
    }

    public static String replaceString(String line, String src, String dest) {
        line = line.replaceAll(src, dest);
        return line;
    }

    public static String replaceCustom(String line) {
        if (customMap.size() > 0) {
            String newLine = line;
            Set<String> keySet = customMap.keySet();
            for (String key : keySet) {
                newLine = replaceString(newLine, key, customMap.get(key));
            }
            return newLine;
        }
        return line;
    }

    public static void readConfig(String configFile) throws IOException {
        if (configFile != null && configFile.length() > 0) {
            File file = new File(configFile);
            FileReader in = new FileReader(file);
            BufferedReader bufIn = new BufferedReader(in);
            // 内存流, 作为临时流
            CharArrayWriter tempStream = new CharArrayWriter();
            // 替换
            String line = null;
            while ((line = bufIn.readLine()) != null) {
                if (line.length() > 0 && line.contains(",")) {
                    String[] content = line.split(",");
                    customMap.put(content[0], content[1]);
                }
            }
            // 关闭 输入流
            bufIn.close();
            // 将内存中的流 写入 文件
        }
    }

    public static void main(String[] argv) throws IOException {
        String file = argv[0];
        System.out.println(file);
        String port = argv[1];
        System.out.println(port);

        if (argv.length == 3) {
            String configFile = argv[2];
            readConfig(configFile);
        }

        replacTextContent(file, port);
    }

    public static void test() throws IOException {
        String file = "D:\\work\\ruijie\\relax_performance_test\\testcase\\industries\\office\\\\office_login.jmx";
        String port = "9090";
        String configFile = "D:\\work\\ruijie\\relax_performance_test\\tools\\custom_map.txt";
        readConfig(configFile);
        testReplace();
    }
}
