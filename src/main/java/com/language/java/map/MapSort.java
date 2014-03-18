package com.language.java.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapSort {

    /**
     * @param h
     * @return 实现对map按照value升序排序
     */
    public static ArrayList<Entry<String, List<String>>> getSortedHashtableByValue(Map<String, List<String>> h) {
        ArrayList<Map.Entry<String, List<String>>> l = new ArrayList<Map.Entry<String, List<String>>>(h.entrySet());
        Collections.sort(l, new Comparator<Map.Entry<String, List<String>>>() {
            public int compare(Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) {
                return (o2.getValue().size() - o1.getValue().size());
            }
        });
        return l;
    }

    public static void main(String[] args) {
        Map<String, List<String>> maps = new java.util.TreeMap<String, List<String>>();
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("d");
        list.add("d");
        maps.put("1", list);

        list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        maps.put("2", list);

        list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        maps.put("3", list);

        list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("b");
        maps.put("4", list);

        ArrayList<Map.Entry<String, List<String>>> entitys = getSortedHashtableByValue(maps);

        for (Map.Entry<String, List<String>> entity : entitys) {
            System.out.println("key: "
                               + entity.getKey()
                               + " value: "
                               + entity.getValue()
                               + " list size: "
                               + entity.getValue().size());
        }

    }
}
