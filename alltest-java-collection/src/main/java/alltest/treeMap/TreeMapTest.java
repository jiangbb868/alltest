package alltest.treeMap;

import java.util.*;

public class TreeMapTest {

    public static void main(String[] args) {

        int n = 300000;
        testTreeMapSort(n);
        testTreeMapSort1(n);
        testListSort(n);

        //testNumber(151410, 315, 380);
    }

    public static void testTreeMapSort(int n ) {
        long begin = System.currentTimeMillis();
        System.out.println("=========TreeMap=============");
        TreeMap treeMap = new TreeMap();
        for (int i = 0; i < n; i ++) {
            treeMap.put("key"+i, "value"+ i);
        }
        long timeInsert = System.currentTimeMillis();
        System.out.println("TreeMap插入耗时" + (timeInsert - begin));
        Collection values =  treeMap.values();
        long timeGetValues = System.currentTimeMillis();
        System.out.println("TreeMap获取values耗时" + (timeGetValues - timeInsert));
        ArrayList valueList = new ArrayList(values);
        long timeArrayList = System.currentTimeMillis();
        System.out.println("构造ArrayList耗时" + (timeArrayList - timeGetValues));
        long end = System.currentTimeMillis();
        //System.out.println("结束时间" +  end);
        System.out.println("treeMap总耗时：" + (end - begin));
    }

    public static void testTreeMapSort1(int n ) {
        long begin = System.currentTimeMillis();
        System.out.println("=========HashMap=============");
        //System.out.println("开始时间" + begin);
        Map map = new HashMap();
        for (int i = 0; i < n; i ++) {
            map.put("key"+i, "value"+ i);
        }
        long timeInsert = System.currentTimeMillis();
        System.out.println("HashMap插入耗时" + (timeInsert - begin));
        TreeMap treeMap = new TreeMap(map);
        long timeConstract = System.currentTimeMillis();
        System.out.println("TreeMap构造耗时" + (timeConstract - timeInsert));
        Collection values =  treeMap.values();
        long timeGetValues = System.currentTimeMillis();
        System.out.println("TreeMap获取values构造耗时" + (timeGetValues - timeConstract));
        ArrayList valueList = new ArrayList(values);

        long end = System.currentTimeMillis();
        //System.out.println("结束时间" +  end);
        System.out.println("总耗时：" + (end - begin));
    }

    public static void testListSort(int n ) {
        long begin = System.currentTimeMillis();
        System.out.println("=========ArrayList=============");
        List list = new ArrayList();
        for (int i = 0; i < n; i ++) {
            list.add("value"+ i);
        }
        long timeInsert = System.currentTimeMillis();
        System.out.println("ArrayList插入耗时" + (timeInsert - begin));
        Collections.sort(list);
        long timeSort = System.currentTimeMillis();
        System.out.println("ArrayList排序耗时" + (timeSort - timeInsert));
        long end = System.currentTimeMillis();
        //System.out.println("结束时间" +  end);
        System.out.println("总耗时：" + (end - begin));
    }

    private static void testNumber(int n, int a, int b) {
        int countA = n / a;
        int countB = n / b;
        for (int i = 0; i < countA; i ++) {
            if ((n - i*a) % b == 0) {
                System.out.println("i = " + i + "; countB = " + (n-i*a)/b);
            }
        }
    }
}
