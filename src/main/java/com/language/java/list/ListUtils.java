package com.language.java.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.language.java.file.infosys.DataOpertion;

/**
 * @author zhangkeh
 */
public class ListUtils {

    static Logger logger = LoggerFactory.getLogger(ListUtils.class);

    /**
     * map to list and loop list data by two ways
     */
    public static List<String> mapToList() {

        HashMap<String, String> map = DataOpertion.createMap();
        logger.info("map size: {}", map.size());
        List<String> list = new ArrayList<String>(map.values());

        System.out.println("************* first way for loop list   **************");

        for (int i = 0; i < list.size(); i++) {

            System.out.println(i + " : " + list.get(i));
            // logger.info("{} : {}", i, list.get(i));
        }

        System.out.println("************* second way for loop list   **************");

        for (String value : list) {
            System.out.println("value is " + value);
            // logger.info("value is {}", value);
        }
        return list;
    }

    public static void listToMap() {
        List<String> list = mapToList();
        Map<String, String> map = new TreeMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " : " + list.get(i));
            map.put(String.valueOf(i), list.get(i));
        }

        for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " :" + entry.getValue());
        }

    }

    public static void main(String[] args) {
        // mapToList();

        listToMap();
    }
}
