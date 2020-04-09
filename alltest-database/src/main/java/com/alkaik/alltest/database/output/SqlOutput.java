package com.alkaik.alltest.database.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SqlOutput implements Output{

    File file;
    FileWriter fileWriter;

    public void init(String fileName) throws IOException {
        file = new File(fileName);
        fileWriter = new FileWriter(file);
    }

    public void destory() throws IOException {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }

    @Override
    public void write(String string) throws Exception {
        fileWriter.write(string);
        fileWriter.flush();
    }
}
