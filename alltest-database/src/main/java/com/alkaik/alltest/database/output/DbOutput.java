package com.alkaik.alltest.database.output;

import com.alkaik.alltest.database.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbOutput implements Output {

    DbConnection dbConnection;
    Connection connection;
    Statement statement;

    public void init() throws SQLException {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.getConnection();
        Statement statement = connection.createStatement();
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
    public void write(String string) throws Exception {
        if (statement != null && string != null) {
            statement.execute(string);
        }
    }
}
