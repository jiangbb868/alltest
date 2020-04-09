package com.alkaik.alltest.database.input;

import com.alkaik.alltest.database.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbInput implements Input {

    DbConnection dbConnection;
    Connection connection;
    Statement statement;

    public DbInput(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public DbInput() {}

    public void init() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        connection = dbConnection.getConnection();
        statement = connection.createStatement();
    }

    public void destory() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        if (statement != null) {
            statement.close();
        }
    }

    @Override
    public Object read(Object object) throws Exception {
        if (!(object instanceof String)) {
            throw new Exception("参数类型不正确");
        }
        String sql = (String)object;
        if (statement != null && sql != null) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Map<String, String>> list = new ArrayList<>();
            while (resultSet.next()) {
                return resultSet.getString(1); // for test
            }
        }
        return null;
    }

}
