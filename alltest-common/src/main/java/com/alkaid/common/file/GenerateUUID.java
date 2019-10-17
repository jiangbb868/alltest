package com.alkaid.common.file;

import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

public class GenerateUUID {

    private static void generateUUID(String fileName, int count) throws Exception {
        File file = new File(fileName);
        FileWriter out = new FileWriter(file);
        for (int i = 0; i < count; i ++) {
            String uuid = UUID.randomUUID().toString();
            out.write(uuid + "\n");
        }
        out.flush();
        out.close();
    }

    public static void main(String[] argv) throws Exception {
        String fileName = argv[0];
        String countString = argv[1];
        int uuidCount = Integer.valueOf(countString);
        generateUUID(fileName, uuidCount);
    }
}
