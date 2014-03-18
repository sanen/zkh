package com.language.java.map;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class DataOpertion {

    final static String devDataFile = "C:\\Users\\zhangkeh\\Desktop\\Person\\Coding\\devData.csv";
    final static String proDataFile = "C:\\Users\\zhangkeh\\Desktop\\Person\\Coding\\proData.csv";
    final static String devDataFileTemp = "C:\\Users\\zhangkeh\\Desktop\\Person\\Coding\\devDataTemp.csv";
    final static String proDataFileTemp = "C:\\Users\\zhangkeh\\Desktop\\Person\\Coding\\proDataTemp.csv";

    /**
     * read file content
     * 
     * @param fileName
     * @return
     */
    @SuppressWarnings("unused")
    private static HashMap<String, String> readFileContent(String fileName) {
        final String methodName = "readFileContent";

        long startTime = System.currentTimeMillis();
        HashMap<String, String> map = new HashMap<String, String>();

        int i = 1;
        try {
            final FileInputStream inputStream = new FileInputStream(fileName);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();

            while (line != null) {
                String lineData = line;
                String[] userPhone = lineData.split(",");
                if (userPhone.length > 1) {
                    map.put(userPhone[0], userPhone[1]);
                }

                line = reader.readLine();
                i++;
            }
            inputStream.close();
        } catch (final FileNotFoundException e) {
            System.out.println("{}(): FileNotFoundException caught: {}" + methodName);
        } catch (final IOException e) {
            System.out.println("{}(): IOException caught: {}" + methodName);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("use time : " + ((endTime - startTime) / 1000.0) + "s");
        return map;
    }

    /**
     * 256*256-1=65535
     * 
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Section1: load cvs file then print file data then get key list for same value
         */
        // HashMap<String, String> map = readFileContent(proDataFileTemp);

        /**
         * Section2: sort map by key
         */
        // HashMap<String, String> map = createMap();

        // System.out.println("************************** sort map by key   ***********************************\n");
        // sortMapByKey(hashMap);

        // System.out.println("************************** sort map by value   ***********************************\n");
        // sortMapByValue(hashMap);

        // loopMap(map);

        // getDataByLikeValue(map, "5");

    }

    public static HashMap<String, String> createMap() {
        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("abc", "123");
        dataMap.put("abcd", "123");
        dataMap.put("abcdefg", "12334567");
        dataMap.put("abcde", "1233");
        dataMap.put("abcdee", "1233");
        dataMap.put("ghi", "1233");
        dataMap.put("de", "1233");
        dataMap.put("abcdeegh", "1233");
        dataMap.put("abcdeeghi", "1233");
        dataMap.put("abcdeeghie", "1233");
        dataMap.put("abcdef", "1233456");
        dataMap.put("1", "11");
        dataMap.put("2", "22");
        dataMap.put("3", "33");
        dataMap.put("4", "44");
        dataMap.put("5", "55");
        dataMap.put("6", "66");
        dataMap.put("8", "65");
        dataMap.put("9", "abde5976");
        dataMap.put("7", "77");
        return dataMap;
    }

    /**
     * loop map data, print key and value
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void loopMap(HashMap<String, String> map) {

        Iterator it1 = map.keySet().iterator();
        Iterator it2 = map.entrySet().iterator();

        System.out.println("****************** 1: get map entry object list, becase entry is contains key and value, you could use the entry.getkey() and get value to get each key and value for map*********************************");
        while (it2.hasNext()) {

            Entry<String, String> entry = (Entry<String, String>)it2.next();
            System.out.println(entry.getKey() + " : " + entry.getValue());

        }

        System.out.println("********** 2: second to print map key and vlaue by map.keySet(), use the key to get value from map ***************");
        while (it1.hasNext()) {
            String key = (String)it1.next();
            System.out.println(key + ": " + map.get(key));

        }

        System.out.println("******************* 3: thrid loop way, we must use the genericity for this way ***********************");
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }

        System.out.println("************************** sort map data by using TreeMap **************************");
        TreeMap<String, String> treeMap = new TreeMap<String, String>(map);
        System.out.println(treeMap);

    }

    /**
     * sort hashmap by key
     * 
     * @param dataMap
     */
    public static void sortMapByKey(HashMap<String, String> dataMap) {

        /**
         * 1. loop map data and print it
         */
        System.out.println("*************************** before sort ********************");
        for (String key : dataMap.keySet()) {
            String value = dataMap.get(key);
            System.out.println(key + ":" + value);
        }

        /**
         * 2. map order by/map sort
         */
        TreeMap<String, String> result = new TreeMap<String, String>();
        Object[] unsort_key = dataMap.keySet().toArray();
        Arrays.sort(unsort_key);
        for (int i = 0; i < unsort_key.length; i++) {
            result.put(unsort_key[i].toString(), dataMap.get(unsort_key[i]));
        }

        /**
         * 3. loop sorted map and print it
         */
        System.out.println("*************************** after sort ***********************");
        for (String key : result.keySet()) {
            String value = result.get(key);
            System.out.println(key + ":" + value);
        }

    }

    /**
     * better to sort map by key becase TreeMap self sort a map when it created
     * 
     * @param dataMap
     */
    public static void sortMapByKeyBetter(HashMap<String, String> dataMap) {
        TreeMap<String, String> result1 = new TreeMap<String, String>(dataMap);
        for (String key : result1.keySet()) {
            String value = result1.get(key);
            System.out.println(key + ":" + value);
        }

    }

    /**
     * sort map by value
     * 
     * @param hashMap
     */
    public static void sortMapByValue(Map<String, String> dataMap) {

        ArrayList<Entry<String, String>> l = new ArrayList<Entry<String, String>>(dataMap.entrySet());

        Collections.sort(l, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o2.getValue().compareToIgnoreCase(o2.getValue()));
            }
        });

        for (Entry<String, String> e : l) {
            System.out.println(e.getKey() + "::::" + e.getValue());
        }
    }

    /**
     * remove one elemetn from hashmap
     * 
     * @param map
     */
    public static void removeElementFromMap(HashMap<String, String> map, String likeValue) {

        System.out.println("size is: " + map.size());

        Iterator<Entry<String, String>> it = map.entrySet().iterator();
        int i = 1;
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            if (likeValue.equalsIgnoreCase(value)) {
                it.remove();
            }

            System.out.println(i + " key is " + key);
            i++;
        }

    }

    /**
     * get key list for value is same , iterator map to check value whether same
     * 
     * @param map
     */
    public static void getSameValueMapKey(HashMap<String, String> map) {

        HashMap<String, String> newmap = new HashMap<String, String>();
        newmap.putAll(map);

        Set<String> kset2 = newmap.keySet();

        for (String key : kset2) {
            int index = 1;
            String value = map.get(key);
            if (value != null) {

                for (String key2 : kset2) {
                    if (key2 != null) {
                        if (value.equals(newmap.get(key2)) && !key.equals(key2)) {
                            System.out.println("key=" + key2);
                            index++;
                            map.remove(key2);
                        }
                    }
                }

            }

            if (index > 1) {
                System.out.println("key=" + key);
                System.out.println("value:" + value);
                System.out.println("key size is " + index + "\n");
            }
        }
    }

    /**
     * @param map
     * @param likeValue
     */
    public static void getDataByLikeValue(Map<String, String> map, String likeValue) {

        for (String key : map.keySet()) {
            if (map.get(key).contains(likeValue)) {
                System.out.println(key + " : " + map.get(key));
            }
        }

    }
}
