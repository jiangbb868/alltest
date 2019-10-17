package com.alkaik.alltest.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnection {

    private static String driver = "org.postgresql.Driver";//驱动
    private static String url ="jdbc:postgresql://127.0.0.1:5432/itone"; //JDBC连接URL
    private static String user = "postgres"; //用户名
    private static String password = ""; //密码

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

    public static void main (String[] argc) {
        DbConnection dbConnection = new DbConnection();
        try (
                Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement()
                ){
            //statement.execute("SELECT COUNT(1) FROM sec_user");
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM sec_user");
            boolean hasNext = resultSet.next();
        } catch (Exception ex) {

        }
    }
}
