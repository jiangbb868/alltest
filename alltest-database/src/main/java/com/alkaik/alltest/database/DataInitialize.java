package com.alkaik.alltest.database;

import static com.alkaik.alltest.database.output.OutputFactory.OUTPUT_TYPE_DB;
import static com.alkaik.alltest.database.output.OutputFactory.OUTPUT_TYPE_FILE;

import com.alkaik.alltest.database.argument.Arguments;
import com.alkaik.alltest.database.output.Output;
import com.alkaik.alltest.database.output.OutputFactory;
import com.alkaik.alltest.database.output.SqlOutput;

import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

public class DataInitialize {
    private static String[] firstNames = {"章", "王", "李", "赵", "陈", "张", "刘", "姜", "黄", "唐"};
    private static int curFirstNameIndex = 0;
    private static Map<String, String> useridMap = new HashMap<>();
    private static Map<String, String> orderidMap = new HashMap<>();
    private static Map<String, String> deptidMap = new HashMap<>();
    private final static String USER_FILE = "init_user.sql";
    private final static String DEPARTMENT_FILE = "init_department.sql";
    private final static String WORKORDER_FILE = "init_workorder.sql";
    private final static String UUID_FILE = "uuid.csv";
    private String nextFirtName() {
        curFirstNameIndex ++;
        if (curFirstNameIndex >= firstNames.length) {
            curFirstNameIndex = 0;
        }
        return firstNames[curFirstNameIndex];
    }

    private void initDepartment(int count, String createUser, String parent, Output output) throws Exception {
        if (output != null) {
            int batchCount = 100;

            for (int i = 0; i < count; i ++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INSERT INTO sec_department (id, parent, domain, modify_time, index, display_name, type, attributes)")
                        .append(" VALUES \n");
                for (int j = 0; j < batchCount && i < count; j ++) {
                    String uuid = UUID.randomUUID().toString();
                    deptidMap.put(String.valueOf(i), uuid);
                    stringBuilder.append(" (")
                            .append("'").append(uuid).append("', ")                 // id
                            .append("'").append(parent).append("', ")               // parent
                            .append("'d0ef761d-1391-4b9c-9b96-a1634deda104', ")     // domain
                            .append("'2019-09-16 21:52:52.647', ")                  // modify_time
                            .append("'6261', ")                                     // index
                            .append(" '部门-").append(getDateString())
                            .append("-").append(i).append("', ")               // display_name
                            .append("'a93aae82-a21d-494c-91fe-e7e25de20122', ")     // type
                            .append("'{\"description\": \"\"}' ")           // attributes
                            .append(") ");
                    if (j < batchCount-1 && i < count-1) {
                        stringBuilder.append(", \n");
                    }
                    i ++;
                }
                stringBuilder.append("; \n");
                System.out.println("insert 100 departments " + stringBuilder.toString());
                output.write(stringBuilder.toString());
                i --;
            }
        }
    }

    private void initWorkorder(int count, String serviceCatalog, Output output) throws Exception {
        if (output != null) {
            int batchCount = 100;

            for (int i = 0; i < count; i ++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INSERT INTO wf_workorder_request (id, service_catalog, classify, process, code, modify_time, create_user, type, priority, attributes)")
                        .append(" VALUES \n");
                for (int j = 0; j < batchCount && i < count; j ++) {
                    String uuid = UUID.randomUUID().toString();
                    orderidMap.put(String.valueOf(i), uuid);
                    stringBuilder.append(" (")
                            .append("'").append(uuid).append("', ")                  // id
                            .append("'").append(serviceCatalog).append("', ")        // service_catalog
                            .append("'0', ")                                         // classify
                            .append("'90d20bd8-d7cd-11e9-888b-13289ad1a515', ")      // process
                            .append("'RE1909150001', ")                              // code
                            .append(" '2019-09-15 23:30:46.151', ")                   // modify_time
                            .append("'3027f8f7-bae5-434f-a6e2-58ae1f3fac89', ")      // create_user
                            .append("'11969064-3bbb-11e8-bf30-abaf0490cf1e', ")      // type
                            .append("NULL, ")                                        // priority
                            .append("'{\"relation_flag\": \"0\"}'")          // attributes
                            .append(") ");
                    if (j < batchCount-1 && i < count-1) {
                        stringBuilder.append(", \n");
                    }
                    i ++;
                }
                stringBuilder.append("; \n");
                System.out.println("insert 100 departments " + stringBuilder.toString());
                output.write(stringBuilder.toString());
                i --;
            }
        }
    }

    private void initUser(int count, String createUser, String department, Output output) throws Exception {
        if (output != null) {
            int batchCount = 100;
            for (int i = 0; i < count; i ++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INSERT INTO sec_user (id, code, modify_user, sex, modify_time, account_status, display_name, type, password, domain, department, account, email, attributes)")
                        .append(" VALUES \n");
                for (int j = 0; j < batchCount && i < count; j ++) {
                    String uuid = UUID.randomUUID().toString();
                    useridMap.put(String.valueOf(i), uuid);
                    stringBuilder.append(" (")
                            .append("'").append(uuid).append("', ")                        // id
                            .append("'',")                                                 // code
                            .append("'").append(createUser).append("', ")                  // modify_user：
                            .append("'male', ")                                            // 男性
                            .append("'2019-08-20 18:48:52.894', ")                         // modify_time
                            .append("'1', ")                                               // account_status：有效
                            .append("'").append(nextFirtName()).append("-test").append(i).append("', ")
                            .append("'fa09796e-762d-4240-be51-a7e1ba3d23b7',")             // type：用户
                            .append("'c740e88b3872826126933bfd44f21a91', ")                // password：1qqqqq
                            .append("'d0ef761d-1391-4b9c-9b96-a1634deda104', ")            // domain：
                            .append("'").append(department).append("', ")                  // department：
                            .append("'account").append("-test-")
                            .append("-").append(i).append("', ")    // account：account-test-xxxx
                            .append("'', ")
                            .append("'{\"icon\":\"\",\"level\":\"vip\",\"title\":\"\",\"leader\":null,\"mobile\":\"\",\"wechat\":\"\",\"location\":null,\"platform\":\"relax\",\"telephone\":\"\",\"user_type\":\"0\",\"expriation\":1660953600000,\"callcenterstate\":\"close\"}'")
                            .append(") ");
                    if (j < batchCount-1 && i < count-1) {
                        stringBuilder.append(", \n");
                    }
                    i ++;
                }
                stringBuilder.append("; \n");
                System.out.println("insert 100 users " + stringBuilder.toString());
                output.write(stringBuilder.toString());
                i --;
            }
        }
    }

    private void initHeadthCard() {

    }

    private void initHeadthClock() {

    }

    private void initDepartRelation(int count) {
        DbConnection dbConnection = new DbConnection();
        try (
                Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement()
        ){
            int batchCount = 100;
            for (int i = 0; i < count; i ++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INSERT INTO ci_relation (type, source, destination) VALUES \n ");
                for (int j = 0; j < batchCount && i < count; j ++) {
                    stringBuilder.append(" (")
                            .append("'a9466471-77e9-4d1e-8a86-0c4636abb248', ")
                            .append("'7502f38e-2e97-11e9-ae39-372f008d05da',")
                            .append("'").append(deptidMap.get(String.valueOf(i))).append("'")
                            .append(") ");
                    if (j < batchCount-1 && i < count-1) {
                        stringBuilder.append(", \n");
                    }
                    i ++;
                }
                stringBuilder.append("; \n");
                System.out.println("insert 100 relations " + stringBuilder.toString());
                statement.execute(stringBuilder.toString());
                i --;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initRelation(int count, String type, String source, Map uuidMap, Output output)
            throws Exception {
        if (output != null) {
            int batchCount = count > 100 ? 100 : count;
            for (int i = 0; i < count; i ++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("INSERT INTO ci_relation (type, source, destination) VALUES \n");
                for (int j = 0; j < batchCount && i < count; j ++) {
                    stringBuilder.append(" (")
                            .append("'").append(type).append("', ")
                            .append("'").append(source).append("',")
                            .append("'").append(uuidMap.get(String.valueOf(i))).append("'")
                            .append(") ");
                    if (j < batchCount -1) {
                        stringBuilder.append(", \n");
                    }
                    i ++;
                }
                stringBuilder.append("; \n");
                System.out.println("insert 100 relations " + stringBuilder.toString());
                output.write(stringBuilder.toString());
                i --;
            }

        }
    }

    private static void generateWorkorder(int count, String createUser, Output output) throws Exception {

        DataInitialize dataInitialize = new DataInitialize();

        String serviceCatalog = "0c09fdea-d7c6-11e9-9bf1-af3dfc8bb7eb";
        String process        = "90d20bd8-d7cd-11e9-888b-13289ad1a515";

        dataInitialize.initWorkorder(count, serviceCatalog, output);

        String[][] relationTypeSource = {
            {String.valueOf(count), "db679266-e33a-477d-b8cc-24e9d42506e9", createUser},         // 服务目录关联工单
            {String.valueOf(count), "47fded07-dccd-4628-ad44-f5f1223f8956", process},            // 服务单关联流程定义
            {String.valueOf(count), "eea70b6b-f9c3-40fa-b9da-a07e049b9479", serviceCatalog}      // 用户-创建-CI
        };

        // 初始化工单关联
        for (int i = 0; i < relationTypeSource.length; i ++){
            dataInitialize.initRelation(Integer.valueOf(relationTypeSource[i][0]),
                                        relationTypeSource[i][1], relationTypeSource[i][2], orderidMap, output);
        }
    }

    private static void generateUser(int count, String createUser, String department, Output output) throws Exception {
        DataInitialize dataInitialize = new DataInitialize();
        // 初始化用户
        dataInitialize.initUser(count, createUser, department, output);

        String[][] relationTypeSource = {
//                {"1000", "70eeb06a-9619-11e8-9c12-630109b78531", "7f0d2f48-c35c-11e9-b752-f79ed0dfac8b"},   // 部门角色-用户  支持部-业务数据角色
//                {"50000", "a9466471-77e9-4d1e-8a86-0c4636abb248", "7502f38e-2e97-11e9-ae39-372f008d05da"},  // 部门关联用户   支持部
//                {"1000", "62278b70-4b7d-4d30-88c1-ff818bea6919", "9c9f7bbc-9727-11e9-b6bb-6b7395fae101"}    // 角色关联用户   业务数据角色

            {String.valueOf(count), "a9466471-77e9-4d1e-8a86-0c4636abb248", department},                               // 部门关联用户   生成的第一个部门
            {String.valueOf(count), "62278b70-4b7d-4d30-88c1-ff818bea6919", "f86c756a-96f3-11e8-9be8-e3f3723038da"}    // 角色关联用户   学生
        };

        // 初始化用户关联
        for (int i = 0; i < relationTypeSource.length; i ++){
            dataInitialize.initRelation(Integer.valueOf(relationTypeSource[i][0]),
                                        relationTypeSource[i][1], relationTypeSource[i][2], useridMap, output);
        }
    }

    private static void generateDepartment(int count, String createUser, String parent, Output output) throws Exception {
        DataInitialize dataInitialize = new DataInitialize();
        dataInitialize.initDepartment(count, createUser, parent, output);
    }

    private static void generateUUID(int count, Output output) throws Exception {
        for (int i = 0; i < count; i ++) {
            String uuid = UUID.randomUUID().toString();
            output.write(uuid + "\n");
        }
    }

    private String getDateString() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String strDate = format.format(now);
        return strDate;
    }

    /**
     *
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
    public static void main (String [] argv) throws Exception {
        Arguments arguments = new Arguments();
        arguments.parse(argv);

        Properties properties = arguments.toProperties();
        String type = arguments.getOutputFile() != null ? OUTPUT_TYPE_FILE : OUTPUT_TYPE_DB;
        Output output = OutputFactory.createOutput(type, properties);

        String createUser = "0b5f6f02-340a-11e9-8d46-4774242101ba";     //安全保密员
        String parent = "c6457916-26b6-4318-bb27-57f8f77ab80e";         //集团

        if (arguments.isCreateUser()) {
            // 先创建部门
            //generateDepartment(1, createUser, parent, output);
            //Set keySet = deptidMap.keySet();
            // department = deptidMap.get(keySet.toArray()[0]);
            String department = "c6457916-26b6-4318-bb27-57f8f77ab80e";  // 集团
            generateUser(arguments.getCount(), createUser, department, output);
        }

        if (arguments.isCreateDepartment()) {
            generateDepartment(arguments.getCount(), createUser, parent, output);
        }

        if (arguments.isCreateWorkorder()) {
            generateWorkorder(arguments.getCount(), createUser, output);
        }

        if (arguments.isCreateUuid()) {
            generateUUID(arguments.getCount(), output);
        }

        /*
        int deptCount = 10;
        int userCount = 50000;
        int workorderCount = 100000;
        int uuidCount = 20000;

        // 生成部门
        SqlOutput departmentOutput = new SqlOutput();
        departmentOutput.init(DEPARTMENT_FILE);
        generateDepartment(deptCount, createUser, parent, departmentOutput);

        Set keySet = deptidMap.keySet();

        // 生成用户
        SqlOutput userOutput = new SqlOutput();
        userOutput.init(USER_FILE);
        generateUser(userCount, createUser, deptidMap.get(keySet.toArray()[0]), userOutput);

        // 生成工单
        SqlOutput workorderOutput = new SqlOutput();
        workorderOutput.init(WORKORDER_FILE);
        generateWorkorder(workorderCount, createUser, workorderOutput);

        // 生成uuid
        SqlOutput uuidOutput = new SqlOutput();
        uuidOutput.init(UUID_FILE);
        generateUUID(uuidCount, uuidOutput);
        */
    }

}
