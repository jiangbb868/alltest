package com.alkaid.alltest.data;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateData4Test {

    public static void main(String[] argv) throws IOException {
    //    createUserArguments();
    //    createCity();

        String[][] info = {
            {
                "hdonestu","hdtwostu","hdthreestu","hdfourstu","hdfivestu"
                        ,"hnonestu","hntwostu","hnthreestu","hnfivestu","hnsixstu"
                        ,"hbonestu","hbtwostu","hbthreestu","hbfourstu","hbfivestu"
                        ,"hzonestu","hztwostu","hzthreestu","hzfourstu","hzfivestu"
            },
            {
                "华东一疫情压测","华东二疫情压测","华东三疫情压测","华东四疫情压测","华东五疫情压测"
                        ,"华南一疫情压测","华南二疫情压测","华南三疫情压测","华南五疫情压测","华南六疫情压测"
                        ,"华北一疫情压测","华北二疫情压测","华北三疫情压测","华北四疫情压测","华北五疫情压测"
                        ,"华中一疫情压测","华中二疫情压测","华中三疫情压测","华中四疫情压测","华中五疫情压测"
            }
        };

        for (int i = 0; i < info[0].length; i ++) {
            createUserInfo(info[0][i], info[1][i], "123456", 60000);
        }
    }

    private static void createCity() throws IOException {
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://172.17.162.93:3306/ws_lightapp";
        String USER = "ws_lightapp";
        String PSWD = "Golm1YT0f";
        Connection conn = null;
        Statement stmt = null;
        List<String> cityList = new ArrayList();
        List<String> subList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER).newInstance();
            conn = DriverManager.getConnection(DB_URL, USER, PSWD);

            stmt = conn.createStatement();
            String sql = " select a.id as ppid, b.`id` as pid, c.id from tb_city a "
                         + " join tb_city b on b.`pid`=a.`id` "
                         + " join tb_city c on c.`pid`=b.`id` ";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String ppid = rs.getString("ppid");
                String pid = rs.getString("pid");
                String id = rs.getString("id");
                String city = ppid.concat(",").concat(pid).concat(",").concat(id).concat("\n");
                subList.add(city);
            }
        } catch (Exception e) {
            System.out.print("DB/SQL ERROR:" + e.getMessage());
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.out.print("Can't close stmt/conn because of " + e.getMessage());
            }

        }
        for (int i = 0; i < 100; i ++) {
            cityList.addAll(subList);
        }
        Collections.shuffle(cityList);
        String fileName = "D:\\citys.cvs";
        File file = new File(fileName);
        FileWriter out = new FileWriter(file);
        for (int i = 0; i < cityList.size(); i ++) {
            out.write(cityList.get(i));
        }
        out.flush();
        out.close();
    }

    private static void createUserArguments()  throws IOException {
        String[] files = {
                "D:\\xinjianxiao_15823614371.xls"
                , "D:\\cuowchuli_15823612791.xls"
                , "D:\\yacexuexiao_15823612191.xls"
                , "D:\\shangxueyuan_15823613381.xls"
                , "D:\\xueyuan_15823613791.xls"
                , "D:\\ceshi.xls"
                , "D:\\ruidaxue.xls"
                , "D:\\ruijiedaxue1.xls"
        };

        String[] schools = {"xinjianxiao"
                ,"cuowchuli"
                ,"yacexuexiao"
                ,"shangxueyuan"
                ,"xueyuan"
                ,"ceshi"
                ,"ruidaxue"
                ,"ruijiedaxue1"
        };
        String[] activityids = {
                "3652"
                ,"3653","3654","3655","3656","3657","3658","3659"
        };

        String[][] questionids = {
                {"38196","38197","38198","38199","38200","38202","38203","38204"}   //xinjianxiao
                , {"38205","38206","38207","38208","38209","38210","38211","38212"},  //cuowchuli
                {"38213","38214","38215","38216","38217","38218","38219","38220"},  //yacexuexiao
                {"38221","38222","38223","38224","38225","38226","38227","38278"},  //shangxueyuan
                {"38229","38230","38231","38232","38233","38234","38235","38236"},  //xueyuan
                {"38237","38238","38239","38240","38241","38242","38243","38244"},  //ceshi
                {"38245","38246","38247","38248","38249","38250","38251","38252"},  //ruidaxue
                {"38253","38254","38255","38256","38257","38258","38259","38260"}   //ruijiedaxue1
        };
        // 安卓答题
        String[][] optionids = {
                {"53199","53202"}
                , {"53205","53208"},
                {"53211","53214"},
                {"53217","53220"},
                {"53241","53244"},
                {"53223","53226"},
                {"53229","53232"},
                {"53235","53238"}
        };
        List<List<List<String>>> allList = new ArrayList<>();
        int maxSize = 0;
        for (int i = 0; i < files.length; i ++) {
            List<List<String>> list = readExcel(files[i]);
            allList.add(list);
            maxSize = maxSize < list.size() ? list.size() : maxSize;
        }
        String fileName = "D:\\8_shool_20w.csv";
        // String fileName = "D:\\ios_stu_code.csv";
        File file = new File(fileName);
        FileWriter out = new FileWriter(file);
        int n = 2;
        int index = 0;
        List<String> codeList = new ArrayList<>();
        for (; ; index ++) {
            for (int i = 0; i < allList.size(); i ++) {
                if (index >= allList.get(i).size()) {
                    continue;
                }
                // domain(school),student_number,activityid,question1id,question2id,question3id,question4id,question5id,question6id,question7id,question8id,question1answer,question2answer
                codeList.add(schools[i]+","+
                             allList.get(i).get(index).get(n) + "," +
                             activityids[i] + "," +
                             questionids[i][0] + "," +
                             questionids[i][1] + "," +
                             questionids[i][2] + "," +
                             questionids[i][3] + "," +
                             questionids[i][4] + "," +
                             questionids[i][5] + "," +
                             questionids[i][6] + "," +
                             questionids[i][7] + "," +
                             optionids[i][0] + "," +
                             optionids[i][1] + "," +
                             "\n");
            }
            if (index >= maxSize) {
                break;
            }
        }
        // 乱序
        Collections.shuffle(codeList);
        // 写入文件
        for (int k = 0; k < codeList.size(); k ++) {
            out.write(codeList.get(k));
        }
        out.flush();
        out.close();
    }

    private static void createUserInfo(String domain, String depart2, String password, int count) {
        String[] title = {"身份","姓名","学工号","性别","证件类型","证件号码","登录密码","1级组织-编号",
                          "1级组织-名称","2级组织-编号","2级组织-名称","3级组织-编号","3级组织-名称",
                          "4级组织-编号","4级组织-名称","5级组织-编号","5级组织-名称","6级组织-编号",
                          "6级组织-名称","7级组织-编号","7级组织-名称","Email","手机1","固定电话","民族",
                          "出生日期","籍贯省份","籍贯市","籍贯地区（县）","家庭住址","考生号","准考证号",
                          "所在年级","入学年份","学制","学历","职务","政治面貌","微信同步字段","QQ同步字段",
                          "手机1短号","固话短号","手机2","手机2短号","手机3","手机3短号","状态","录取类别"};
        String[] shenfen = {"教职工","学生","学生","学生","其他"};
        String[] xing = {"张","王","赵","李","周","刘","陈","高"};
        String[] ming0 = {"小","老","乐","志"};
        String[] ming = {"博","高","强","四","单","芬","兵","丽","培","东","华","长","宝","京","池","龙","能"};
        String school = domain; //"ruijiedaxue1";
        String[] depart = {"土木工程学院","社科院","机电工程学院","管理学院","外语系","教导处","后勤部"};
        String[] depart3 = {"一年级","二年级","三年级","四年级","研一","研二","研三"};
        String[] depart4 = {"实习组","教研组","学工组","劳务组","物理组","化学组"};
        String[] sex = {"男","男","女"};
        String[] shen = {"北京","上海","天津","山东","河南","四川","广东","广西","河北","黑龙江","辽宁","吉林","新疆","西藏","福建"};

        List<List<String>> contentList  = new ArrayList();
        List<String> titleList = Arrays.asList(title);
        contentList.add(titleList);
        for (int i = 0; i < count; i ++) {
            List<String> row = new ArrayList<>();
            row.add(shenfen[i%shenfen.length]);
            row.add(xing[i%xing.length]+ming0[i%ming0.length]+ming[i%ming.length]);
            row.add(school + i);
            row.add(sex[i%sex.length]);
            row.add("其他");
            row.add(school + i);
            row.add(password);
            row.add("");
            row.add("行政组织架构");
            row.add("");
            //row.add(depart[i%depart.length]);
            row.add(depart2);
            row.add("");
            row.add(depart3[i%depart3.length]);
            row.add("");
            row.add(depart4[i%depart4.length]);
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            row.add(school + "_" + i + "@"+school+".com");   //email
            row.add("13800138000");
            row.add("");
            row.add("汉族");
            row.add("1990-09-09");
            row.add(shen[i%shen.length]);
            row.add("");
            row.add("");
            row.add(shen[i%shen.length]);
            row.add(school + i);
            row.add(school + i);
            row.add(depart3[i%depart3.length]);
            row.add("2018-09-09");
            row.add("四");
            row.add("本科");
            row.add("学生");
            row.add("团员");
            row.add("weixin_nihao");
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            row.add("");
            row.add("有效");
            row.add("统招");
            contentList.add(row);
        }

        writeExcel(contentList, "D:\\120w\\"+school+".xls");
    }

    public static List<List<String>> readExcel(String filePath) {
        List<List<String>> dataLst = null;
        InputStream is = null;
        try {
            //验证文件是否合法
            if (!validateExcel(filePath)) {
                return null;
            }
            // 判断文件的类型，是2003还是2007
            boolean isExcel2003 = true;
            if (isExcel2007(filePath)) {
                isExcel2003 = false;
            }
            // 调用本类提供的根据流读取的方法
            File file = new File(filePath);
            is = new FileInputStream(file);
            dataLst = read(is, isExcel2003);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }

        return dataLst;
    }

    private static List<List<String>> read(InputStream inputStream, boolean isExcel2003) {
        List<List<String>> dataLst = null;
        try {
            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataLst;
    }

    private static List<List<String>> read(Workbook wb) {
        int totalRows = 0;
        int totalCells = 0;
        List<List<String>> dataLst = new ArrayList<List<String>>();
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数
        if (totalRows >= 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        //遍历行
        for (int r = 0; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            //遍历列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                            cellValue = cell.getNumericCellValue() + "";
                            break;
                        case HSSFCell.CELL_TYPE_STRING: // 字符串
                            cellValue = cell.getStringCellValue();
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                            cellValue = cell.getBooleanCellValue() + "";
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA: // 公式
                            cellValue = cell.getCellFormula() + "";
                            break;
                        case HSSFCell.CELL_TYPE_BLANK: // 空值
                            cellValue = "";
                            break;
                        case HSSFCell.CELL_TYPE_ERROR: // 故障
                            cellValue = "非法字符";
                            break;
                        default:
                            cellValue = "未知类型";
                            break;
                    }
                }
                rowLst.add(cellValue);
            }
            // 保存第r行的第c列
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    private static boolean validateExcel(String filePath) {
        // 检查文件名是否为空或者是否是Excel格式的文件
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        // 检查文件是否存在
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    private static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static void writeExcel(List<List<String>> dataList, String finalXlsxPath){
        OutputStream out = null;
        try {
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);

            //删除原有数据，除了属性列
            int rowNumber = sheet.getLastRowNum();
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }

            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);

            //写一行
            for (int j = 0; j < dataList.size(); j++) {
                Row row = sheet.createRow(j);

                //写一列
                List<String> datas = dataList.get(j);
                for (int k=0; k<datas.size(); k++) {
                    row.createCell(k).setCellValue(datas.get(k));
                }
            }

            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            System.out.println("数据导出成功");
        } catch (Exception e) {
            System.out.println("请创建一个空文件");
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    private static Workbook getWorkbok(File file) throws IOException{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

}
