package com.alkaik.alltest.database.argument;

import static com.alkaik.alltest.database.output.OutputFactory.DB_HOST;
import static com.alkaik.alltest.database.output.OutputFactory.DB_NAME;
import static com.alkaik.alltest.database.output.OutputFactory.DB_PORT;
import static com.alkaik.alltest.database.output.OutputFactory.DB_USER;
import static com.alkaik.alltest.database.output.OutputFactory.OUTPUT_FILE;
import static com.alkaik.alltest.database.output.OutputFactory.PASSWORD;

import java.util.Properties;

public class Arguments {

    private String[] argv;
    private String host;
    private int port;
    private String database;
    private String user;
    private String password;
    private String outputFile;

    private boolean createUser;
    private boolean createDepartment;
    private boolean createWorkorder;
    private boolean createUuid;
    private int count;

    public Arguments() {
        port = -1;
        count = -1;
        createUuid = false;
        createUser = false;
        createWorkorder = false;
        createDepartment = false;
    }

    /**
     * @param argv
     * -user create user
     * -department create department
     * -workorder  create workorder
     * -uuid  create uuid, only output file
     * -c count
     * -h  host
     * -p  port
     * -d  database
     * -u  user
     * -s  password
     * -f  output file
     * @throws Exception
     */
    public void parse(String[] argv) throws Exception {
        if (argv == null || argv.length == 0) {
            throw new Exception("invalid arguments");
        }
        this.argv = argv;
        for (int i = 0; i < argv.length; i ++) {
            switch(argv[i]) {
                case "-user":
                    createUser = true;
                    break;
                case "-department":
                    createDepartment = true;
                    break;
                case "-workorder":
                    createWorkorder = true;
                    break;
                case "-uuid":
                    createUuid = true;
                    break;
                case "-c": // count
                    checkArgIndex(argv.length, i+1);
                    count = Integer.valueOf(argv[i+1]);
                    break;
                case "-h": // host
                    checkArgIndex(argv.length, i+1);
                    host = argv[i+1];
                    break;
                case "-p": // port
                    checkArgIndex(argv.length, i+1);
                    port = Integer.valueOf(argv[i+1]);
                    break;
                case "-d": // database
                    checkArgIndex(argv.length, i+1);
                    database = argv[i+1];
                    break;
                case "-u": // user
                    checkArgIndex(argv.length, i+1);
                    user = argv[i+1];
                    break;
                case "-s": // password
                    checkArgIndex(argv.length, i+1);
                    password = argv[i+1];
                    break;
                case "-f": // output file
                    checkArgIndex(argv.length, i+1);
                    outputFile = argv[i+1];
                    break;
            }
        }
        checkArgument();
    }

    private void checkArgIndex(int argLength, int index) throws Exception {
        if (index >= argLength) {
            throw new Exception("invalid arguments number");
        }
    }

    private void checkArgument() throws Exception {
        // -h -p -d -u -s 参数必须同时存在
        if (!(host != null &&
            port != -1 &&
            database != null &&
            user != null &&
            password != null) &&
            !(host == null &&
           port == -1 &&
           database == null &&
           user == null &&
           password == null)) {
            throw new Exception("host,port,database,user,password 必须同时存在");
        }
        // -c 必须存在
        if (count == -1) {
            throw new Exception("count必须存在");
        }
        // -f 存在时，-h -p -d -u -s 不可同时存在
        if (outputFile != null && (
            host != null || port != -1 || database != null || user != null || password != null
                )) {
            throw new Exception("output file不能同如下参数同时存在: host,port,database,user,password");
        }
        if (outputFile == null &&  (host == null || port == -1 || database == null || user == null || password == null)) {
            throw new Exception("必须制定output file或数据库其中之一");
        }
        // -user -deparment -workorder 与 -uuid 不能同时存在
        if (createUuid && (createUser || createWorkorder || createDepartment)) {
            throw new Exception("创建uuid时不能同时创建user、workorder、department");
        }

        if (createUuid && outputFile == null) {
            throw new Exception("创建uuid必须指定输出文件");
        }

    }

    public Properties toProperties() {
        Properties properties = new Properties();
        if (this.outputFile != null) {
            properties.put(OUTPUT_FILE, outputFile);
        }
        if (this.database != null) {
            properties.put(DB_NAME, database);
        }
        if (this.host != null) {
            properties.put(DB_HOST, host);
        }
        if (this.port != -1) {
            properties.put(DB_PORT, Integer.valueOf(port));
        }
        if (this.user != null) {
            properties.put(DB_USER, user);
        }
        if (this.password != null) {
            properties.put(PASSWORD, password);
        }
        return properties;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public boolean isCreateUser() {
        return createUser;
    }

    public void setCreateUser(boolean createUser) {
        this.createUser = createUser;
    }

    public boolean isCreateDepartment() {
        return createDepartment;
    }

    public void setCreateDepartment(boolean createDepartment) {
        this.createDepartment = createDepartment;
    }

    public boolean isCreateWorkorder() {
        return createWorkorder;
    }

    public void setCreateWorkorder(boolean createWorkorder) {
        this.createWorkorder = createWorkorder;
    }

    public boolean isCreateUuid() {
        return createUuid;
    }

    public void setCreateUuid(boolean createUuid) {
        this.createUuid = createUuid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
