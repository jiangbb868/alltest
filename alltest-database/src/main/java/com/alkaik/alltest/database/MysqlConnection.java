package com.alkaik.alltest.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MysqlConnection {
    private static String driver = "com.mysql.cj.jdbc.Driver";//驱动  com.mysql.jdbc.Driver
    private String url ="jdbc:mysql://212.64.90.199:3306/ws_lightapp?useUnicode=true&characterEncoding=utf-8"; //JDBC连接URL
    private String user = "root"; //用户名
    private String password = "123456"; //密码

    static {
        try {
            //加载驱动
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("驱动加载出错！");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    private static void test() throws Exception {
        MysqlConnection dbConnection = new MysqlConnection();
        try (
            Connection connection = dbConnection.getConnection();
            Statement statement = connection.createStatement();
                //String sql = "select attributes->>'zzjkmzt' from ci_inst_other where type = 'b87ddb2e-5c95-11ea-9f00-5fe8e774dfc3' and attributes ->> 'account' like 'yqyc03%' limit 10";
        ){
            String sql = "SELECT t_virtual_clock_data.xh AS a_t_virtual_clock_data_xh,\n"
                         + "         t_virtual_clock_data.xm AS a_t_virtual_clock_data_xm,\n"
                         + "         t_virtual_clock_data.is_clock AS a_t_virtual_clock_data_is_clock,\n"
                         + "         t_virtual_clock_data.tiwen AS a_t_virtual_clock_data_tiwen,\n"
                         + "         t_virtual_clock_data.shifouzaixiao AS a_t_virtual_clock_data_shifouzaixiao,\n"
                         + "         t_virtual_clock_data.clock_time AS a_t_virtual_clock_data_clock_time,\n"
                         + "         t_virtual_clock_data.miqiejiechuguanxi AS a_t_virtual_clock_data_miqiejiechuguanxi,\n"
                         + "         t_virtual_clock_data.jkzt AS a_t_virtual_clock_data_jkzt,\n"
                         + "         t_virtual_clock_data.gelifangshi AS a_t_virtual_clock_data_gelifangshi,\n"
                         + "         t_virtual_clock_data.jinyigeyueshifoujiechuguoyisibingli AS a_t_virtual_clock_data_jinyigeyueshifoujiechuguoyisibingli,\n"
                         + "         t_virtual_clock_data.shifougeli AS a_t_virtual_clock_data_shifougeli,\n"
                         + "         t_virtual_clock_data.jinyigeyueshifoujiechuguoquezhenbingli AS a_t_virtual_clock_data_jinyigeyueshifoujiechuguoquezhenbingli,\n"
                         + "         t_virtual_clock_data.shifoufare AS a_t_virtual_clock_data_shifoufare,\n"
                         + "         t_virtual_clock_data.jinyigeyueshifouquguohubei AS a_t_virtual_clock_data_jinyigeyueshifouquguohubei\n"
                         + "     FROM \n"
                         + "    (SELECT a_questionnaire_answer_chzu.stu_code AS xh,\n"
                         + "         a_questionnaire_answer_chzu.stu_name AS xm,\n"
                         + "         1 AS is_clock,\n"
                         + "         '' AS tiwen, '' AS shifouzaixiao, a_questionnaire_answer_chzu.createtime AS clock_time, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22422 THEN(CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29961 THEN\n"
                         + "        'qinyoujiqita'\n"
                         + "        ELSE '' END)\n"
                         + "        ELSE '' END)) AS miqiejiechuguanxi, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22432 THEN\n"
                         + "        (\n"
                         + "        CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29975 THEN\n"
                         + "        '确诊'\n"
                         + "        WHEN 29974 THEN\n"
                         + "        '疑似'\n"
                         + "        ELSE '健康'\n"
                         + "        END )\n"
                         + "        ELSE '' END)) AS jkzt, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22416 THEN\n"
                         + "        (\n"
                         + "        CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29952 THEN\n"
                         + "        'jizhong'\n"
                         + "        ELSE ''\n"
                         + "        END )\n"
                         + "        ELSE '' END)) AS gelifangshi, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22422 THEN\n"
                         + "        (\n"
                         + "        CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29961 THEN\n"
                         + "        'shi'\n"
                         + "        ELSE 'fou'\n"
                         + "        END )\n"
                         + "        ELSE '' END)) AS jinyigeyueshifoujiechuguoyisibingli, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22416 THEN\n"
                         + "        (\n"
                         + "        CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29952 THEN\n"
                         + "        'shi'\n"
                         + "        ELSE 'fou'\n"
                         + "        END )\n"
                         + "        ELSE '' END)) AS shifougeli, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22422 THEN\n"
                         + "        (\n"
                         + "        CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29961 THEN\n"
                         + "        'shi'\n"
                         + "        ELSE 'fou'\n"
                         + "        END )\n"
                         + "        ELSE '' END)) AS jinyigeyueshifoujiechuguoquezhenbingli, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22419 THEN\n"
                         + "        (\n"
                         + "        CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29957 THEN\n"
                         + "        'fou'\n"
                         + "        ELSE 'shi'\n"
                         + "        END )\n"
                         + "        ELSE '' END)) AS shifoufare, max((\n"
                         + "        CASE a_questionnaire_answer_chzu.questionid\n"
                         + "        WHEN 22429 THEN\n"
                         + "        (\n"
                         + "        CASE a_questionnaire_answer_chzu.optionid\n"
                         + "        WHEN 29965 THEN\n"
                         + "        'shi'\n"
                         + "        ELSE 'fou'\n"
                         + "        END )\n"
                         + "        ELSE '' END)) AS jinyigeyueshifouquguohubei\n"
                         + "    FROM a_questionnaire_answer_chzu\n"
                         + "    WHERE a_questionnaire_answer_chzu.activityid = 3407\n"
                         + "   AND a_questionnaire_answer_chzu.createtime >'2020-02-27 00:00:00' AND a_questionnaire_answer_chzu.createtime <'2020-02-27 23:00:0' "
                         + "    GROUP BY  a_questionnaire_answer_chzu.stu_code) AS t_virtual_clock_data";

            //statement.execute("SELECT COUNT(1) FROM a_questionnaire_answer_chzu");
            String sql1 = "SELECT * FROM a_questionnaire_answer_chzu limit 100";
            ResultSet resultSet = statement.executeQuery(sql1);
            //boolean hasNext = resultSet.next();
            ResultSetMetaData rsMeta = resultSet.getMetaData();
            List<Map<String, Object>> rows = new ArrayList();
            while(resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap();
                int i = 0;

                for(int size = rsMeta.getColumnCount(); i < size; ++i) {
                    String columName = rsMeta.getColumnLabel(i + 1);
                    Object value = resultSet.getObject(i + 1);
                    row.put(columName, value);
                }

                rows.add(row);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private static void insertClock() throws Exception {
        String presql = "INSERT INTO a_questionnaire_answer_chzu(stu_code,stu_name,sch_code,is_clock,createtime,tiwen,shifouzaixiao,miqiejiechuguanxi,jkzt,gelifangshi,jinyigeyueshifoujiechuguoyisibingli,shifougeli,jinyigeyueshifoujiechuguoquezhenbingli,shifoufare,jinyigeyueshifouquguohubei) VALUES";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(presql)
                .append("('chzu1','张三','chzu',1,'%s',36.1,'fou','','健康','','fou','fou','fou','fou','fou'),")
                .append("('chzu2','李四','chzu',1,'%s',36.1,'fou','','健康','','fou','fou','fou','fou','fou'),")
                .append("('chzu3','李四','chzu',1,'%s',36.1,'fou','','健康','','fou','fou','fou','fou','fou'),")
                .append("('chzu4','王五','chzu',1,'%s',36.1,'fou','','健康','','fou','fou','fou','fou','fou')");
        MysqlConnection dbConnection = new MysqlConnection();
        try (
                Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement();
        ){
            while (true) {
                try {
                    for (int i = 0; i < 1; i++) {
                        String time = new Timestamp(System.currentTimeMillis()).toString();
                        String
                                sql =
                                String.format(stringBuilder.toString(), time, time, time, time);
                        int size = statement.executeUpdate(sql);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Thread.sleep(1000*20);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static void main(String[] argv) throws Exception {
        //test();
        insertClock();
    }
}
