package com.alkaik.alltest.database.output;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class OutputFactory {

    public final static String OUTPUT_TYPE_FILE = "file";
    public final static String OUTPUT_TYPE_DB = "database";

    public final static String OUTPUT_FILE = "outputFile";
    public final static String DB_HOST = "dbHost";
    public final static String DB_PORT = "dbPort";
    public final static String DB_USER = "dbUser";
    public final static String PASSWORD = "password";
    public final static String DB_NAME = "dbName";

    public static Output createOutput(String type, Properties properties) throws IOException, SQLException {
        Output output = null;
        switch (type) {
            case OUTPUT_TYPE_FILE:
                String file = (String)properties.get(OUTPUT_FILE);
                SqlOutput sqlOutput = new SqlOutput();
                sqlOutput.init(file);
                output = sqlOutput;
                break;
            case OUTPUT_TYPE_DB:
                String dbHost = (String)properties.get(DB_HOST);
                Integer dbPort = (Integer)properties.get(DB_PORT);
                String dbName = (String)properties.get(DB_NAME);
                String dbUser = (String)properties.get(DB_USER);
                String password = (String)properties.get(PASSWORD);

                DbOutput dbOutput = new DbOutput();
                dbOutput.init(dbHost, dbPort, dbName, dbUser, password);
                output = dbOutput;
                break;
        }
        return output;
    }
}
