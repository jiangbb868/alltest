package com.alkaid.common.perfmon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Performance {

    private static void diskw(String[] argv) throws IOException {
        // diskw bytes loops
        long bytes = Long.valueOf(argv[1]);
        long loops = Long.valueOf(argv[2]);
        long begin = System.currentTimeMillis();
        for (long i = 0; i < loops; i ++) {
            File file = new File("filewrite" + i);
            FileWriter fileWriter = new FileWriter(file);
            for (long j = 0; j < bytes; j ++) {
                fileWriter.write("A");
            }
            fileWriter.flush();
            fileWriter.close();
        }

        // delete all temp files
        for (int i = 0; i < loops; i ++) {
            File file = new File("filewrite" + i);
            file.delete();
        }
        long end = System.currentTimeMillis();
        System.out.println("writing " + bytes + " bytes for " + loops + " loops spends: " + (end - begin));
    }

    private static void diskr(String[] argv) {
        // diskr bytes loops
    }

    private static void math(String[] argv) {
        // math loops
        long loops = Long.valueOf(argv[1]);
        long begin = System.currentTimeMillis();

        for (long i = 0; i < loops; i ++) {
            double d = (21342324.45234354 + 234342.98766*334324.433544346)/2334534.3343;
        }

        long end = System.currentTimeMillis();
        System.out.println("mathing " + loops + " loops spends: " + (end - begin));
    }

    private static void database(String[] argv) throws Exception {
        // database ip port loops

        String ip = argv[1];
        int port = Integer.valueOf(argv[2]);
        long loops = Long.valueOf(argv[3]);

        String driver = "org.postgresql.Driver";    //驱动
        String user = "postgres";
        String password = "";
        String url ="jdbc:postgresql://"+ip+":"+port+"/itone"; //JDBC连接URL

        long begin = System.currentTimeMillis();
        try {
            //加载驱动
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("驱动加载出错！");
            e.printStackTrace();
        }

        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement()
                ){
            Map useridMap = new HashMap<>();
            String createUser = "";
            String department = "";
            initUser(loops, createUser, department, statement, useridMap);
            removeUser(statement, useridMap);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("connect database " + loops + " loops spends: " + (end - begin));
    }

    private static void initUser(long count, String createUser, String department, Statement statement, Map useridMap) throws Exception {
        if (statement != null) {
            int batchCount = 100;

            for (long i = 0; i < count; i ++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INSERT INTO sec_user (id, code, modify_user, sex, modify_time, account_status, display_name, type, password, domain, department, account, email, attributes)")
                        .append(" VALUES \n");
                for (int j = 0; j < batchCount && i < count; j ++) {
                    String uuid = UUID.randomUUID().toString();
                    useridMap.put(String.valueOf(i), uuid);
                    stringBuilder.append(" (")
                            .append("'").append(uuid).append("', ")                        // id
                            .append("'',")                                                 // code
                            .append("'").append(createUser).append("', ")                  // modify_user：超级管理员  f300343e-7ea4-4e58-bcb0-0b2c64f18a83
                            .append("'male', ")                                            // 男性
                            .append("'2019-08-20 18:48:52.894', ")                         // modify_time
                            .append("'1', ")                                               // account_status：有效
                            .append("'test").append(i).append("', ")
                            .append("'fa09796e-762d-4240-be51-a7e1ba3d23b7',")             // type：用户
                            .append("'c740e88b3872826126933bfd44f21a91', ")                // password：1qqqqq
                            .append("'d0ef761d-1391-4b9c-9b96-a1634deda104', ")            // domain：
                            .append("'").append(department).append("', ")                  // department：支持部 7502f38e-2e97-11e9-ae39-372f008d05da
                            .append("'account").append("-test").append(i).append("', ")    // account：account-test-xxxx
                            .append("'', ")
                            .append("'{\"icon\":\"\",\"level\":\"vip\",\"title\":\"\",\"leader\":null,\"mobile\":\"\",\"wechat\":\"\",\"location\":null,\"platform\":\"relax\",\"telephone\":\"\",\"user_type\":\"0\",\"expriation\":1660953600000,\"callcenterstate\":\"close\"}'")
                            .append(") ");
                    if (j < batchCount-1 && i < count-1) {
                        stringBuilder.append(", \n");
                    }
                    i ++;
                }
                stringBuilder.append("; \n");
                // System.out.println("insert 100 users " + stringBuilder.toString());
                statement.execute(stringBuilder.toString());
                i --;
            }
        }
    }

    private static void removeUser(Statement statement, Map useridMap) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM sec_user WHERE id IN (");
        Object[] values = useridMap.values().toArray();
        for (Object id : values) {
            stringBuilder.append("'").append(id.toString()).append("',");
        }
        stringBuilder.append("'tail-id'"); // 不存在的id，为处理最后一个逗号
        stringBuilder.append(")");

        if (statement != null) {
            statement.executeUpdate(stringBuilder.toString());
        }
    }

    private static void printUsage() {
        System.out.println(
                "Usage:\n" +
                "diskw    : test disk write, sample as: \n" +
                "           diskw bytes loops\n" +
                "diskr    : test disk read, sample as: \n" +
                "           diskr bytes loops \n" +
                "math     : test capability of math, sample as:\n" +
                "           math loops \n" +
                "database : test database read and write, sample as\n" +
                "           database ip port loops"
        );
    }

    public static void main(String[] argv) throws Exception {
        // command, count of arguments
        Object[][] params = {
                {"diskw",    3},    // disk write: cmd, bytes, loops
                {"diskr",    3},    // disk read: cmd, bytes, loops
                {"math",     2},    // math: cmd, loops
                {"database", 4}     // database: cmd, ip, port, loops
        };

        if (argv.length == 0) {
            printUsage();
            return;
        }
        String cmd = argv[0];
        System.out.println(cmd);

        if (cmd.equals("diskw")) {
            diskw(argv);
        } else if (cmd.equals("math")) {
            math(argv);
        } else if (cmd.equals("database")) {
            database(argv);
        } else {
            printUsage();
        }
    }

}
