package com.language.java.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Testing {
    public static void main(String[] args) {

        // System.out.println("\n***************** sort map that value is double *****************");
        // HashMap<String, Double> doubleMap = createValueDoubleMap();
        // System.out.println("unsorted map: " + doubleMap);
        // sortDoubleValueMap(doubleMap, 0);
        // sortDoubleValueMap(doubleMap, 1);
        //
        // System.out.println("\n***************** sort map that value is string *****************");
        // HashMap<String, String> stringMap = createValueStringMap();
        // System.out.println("unsorted map: " + stringMap);
        // sortStringValueMap(stringMap, 0);
        // sortStringValueMap(stringMap, 1);
        //
        // System.out.println("\n***************** sort map that value is list *****************");
        // Map<String, List> listMap = createValueListMap();
        // System.out.println("unsorted map: " + stringMap);
        // sortListValueMap(listMap, 0);
        // sortListValueMap(listMap, 1);

        Map<String, List<String>> listMap = createValueListMap();
        sortListValueMap(listMap, 0);
        sortListValueMap(listMap, 1);

    }

    public static HashMap<String, String> createValueStringMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("A", "yb");
        map.put("B", "ag");
        map.put("C", "af");
        map.put("D", "cd");
        return map;
    }

    public static HashMap<String, Double> createValueDoubleMap() {
        HashMap<String, Double> map = new HashMap<String, Double>();
        map.put("A", 99.5);
        map.put("B", 67.4);
        map.put("C", 67.4);
        map.put("D", 67.3);
        return map;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, List<String>> createValueListMap() {
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
        return maps;
    }

    /**
     * compare string by match or not match case
     * 
     * @param one
     *            first string
     * @param two
     *            second string
     */
    public static void compareString(String one, String two) {

        System.out.println("************* match case  *************");
        System.out.println(one.compareTo(two));
        System.out.println(two.compareTo(one));

        System.out.println("************* not match case  *************");
        System.out.println(one.compareToIgnoreCase(two));
        System.out.println(two.compareToIgnoreCase(one));
    }

    public static void sortStringValueMap(HashMap<String, String> map, int orderFlag) {

        ValueStringComparator bvc = new ValueStringComparator(map, orderFlag);
        TreeMap<String, String> sorted_map = new TreeMap<String, String>(bvc);

        sorted_map.putAll(map);

        if (orderFlag == 0) {
            System.out.println("asc results: " + sorted_map);
        } else if (orderFlag == 1) {
            System.out.println("desc results: " + sorted_map);
        }

    }

    /**
     * if orderFlag=0 is stand for asc if orderFlag=1 is stand for desc
     * 
     * @param orderFlag
     */
    public static void sortDoubleValueMap(HashMap<String, Double> map, int orderFlag) {
        ValueDoubleComparator bvc = new ValueDoubleComparator(map, orderFlag);
        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);

        sorted_map.putAll(map);
        if (orderFlag == 0) {
            System.out.println("asc results: " + sorted_map);
        } else if (orderFlag == 1) {
            System.out.println("desc results: " + sorted_map);
        }

    }

    public static void sortListValueMap(Map<String, List<String>> listMap, int orderFlag) {
        ValueListComparator bvc = new ValueListComparator(listMap, orderFlag);
        TreeMap<String, List<String>> sorted_map = new TreeMap<String, List<String>>(bvc);

        sorted_map.putAll(listMap);
        if (orderFlag == 0) {
            System.out.println("asc results: " + sorted_map);
        } else if (orderFlag == 1) {
            System.out.println("desc results: " + sorted_map);
        }

    }

    /**
     * 1) create a map 2) get a list from map.entryset data 3) sort this list
     * 
     * @param map
     * @param orderFlag
     */
    // public static void sortListValueMap(Map map, final int orderFlag) {
    //
    // ArrayList<Map.Entry<String, List>> l = new ArrayList<Map.Entry<String, List>>(map.entrySet());
    //
    // Collections.sort(l, new Comparator<Map.Entry<String, List>>() {
    // public int compare(Map.Entry<String, List> o1, Map.Entry<String, List> o2) {
    // if (orderFlag == 0) {
    // if ((o2.getValue().size() - o1.getValue().size()) >= 0) {
    // return -1;
    // } else {
    // return 1;
    // }
    //
    // } else if (orderFlag == 1) {
    // if ((o2.getValue().size() - o1.getValue().size()) < 0) {
    // return -1;
    // } else {
    // return 1;
    // }
    // }
    // return (o2.getValue().size() - o1.getValue().size());
    // }
    // });
    //
    // for (Map.Entry<String, List> entity : l) {
    // System.out.println(entity.getKey() + " = " + entity.getValue().size());
    // }
    //
    // System.out.println("asc results: " + l);
    // }

}

class ValueListComparator implements Comparator<String> {

    Map<String, List<String>> base;
    int opertion = 0;

    public ValueListComparator(Map<String, List<String>> listMap, int opertion) {
        this.base = listMap;
        this.opertion = opertion;
    }

    public int compare(String listKey1, String listKey2) {
        List<String> list1 = (List<String>)base.get(listKey1);
        List<String> list2 = (List<String>)base.get(listKey2);

        int returnFlag = 0;

        if (opertion == 0) {
            if (list1.size() >= list2.size()) {
                returnFlag = 1;
            } else {
                returnFlag = -1;
            }
        } else if (opertion == 1) {
            if (list1.size() < list2.size()) {
                returnFlag = 1;
            } else {
                returnFlag = -1;
            }
        }

        return returnFlag;

    }
}

class ValueDoubleComparator implements Comparator<String> {

    Map<String, Double> base;
    int opertion = 0;

    public ValueDoubleComparator(Map<String, Double> base, int opertion) {
        this.base = base;
        this.opertion = opertion;
    }

    // opertion=0 desc
    // opertion=1 asc
    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
        int flag = 0;
        if (opertion == 0) {
            if (base.get(a) <= base.get(b)) {
                flag = -1;
            } else {
                flag = 1;
            }
        } else if (opertion == 1) {
            if (base.get(a) >= base.get(b)) {
                flag = -1;
            } else {
                flag = 1;
            }
        }
        return flag;
    }
}

class ValueStringComparator implements Comparator<String> {

    Map<String, ?> base;
    int opertion = 0;

    public ValueStringComparator(Map<String, ?> base, int opertion) {
        this.base = base;
        this.opertion = opertion;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
        int compareFlag = 0;
        if (opertion == 0) {
            if (String.valueOf(base.get(a)).compareTo(String.valueOf(base.get(b))) < 0) {
                compareFlag = -1;
            } else {
                compareFlag = 1;
            }
        } else if (opertion == 1) {
            if (String.valueOf(base.get(a)).compareTo(String.valueOf(base.get(b))) >= 0) {
                compareFlag = -1;
            } else {
                compareFlag = 1;
            }
        }
        return compareFlag;
    }
}

// class ValueListComparator implements Comparator<List<String>> {
//
// Map<String, ?> base;
// int opertion = 0;
//
// public ValueListComparator(Map<String, ?> base, int opertion) {
// this.base = base;
// this.opertion = opertion;
// }
//
// // Note: this comparator imposes orderings that are inconsistent with equals.
// public int compare(List<String> list1, List<String> list2) {
// int compareFlag = 0;
// if (opertion == 0) {
// if (list2.size() - list1.size() < 0) {
// compareFlag = -1;
// } else {
// compareFlag = 1;
// }
// } else if (opertion == 1) {
// if (list2.size() - list1.size() >= 0) {
// compareFlag = -1;
// } else {
// compareFlag = 1;
// }
// }
// return compareFlag;
// }
//
// }